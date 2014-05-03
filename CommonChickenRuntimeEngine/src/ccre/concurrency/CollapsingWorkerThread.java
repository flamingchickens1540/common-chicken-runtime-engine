/*
 * Copyright 2013-2014 Colby Skeggs
 * 
 * This file is part of the CCRE, the Common Chicken Runtime Engine.
 * 
 * The CCRE is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * 
 * The CCRE is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with the CCRE.  If not, see <http://www.gnu.org/licenses/>.
 */
package ccre.concurrency;

import ccre.channel.BooleanInput;
import ccre.channel.BooleanStatus;
import ccre.channel.EventOutput;
import ccre.channel.EventInput;
import ccre.log.LogLevel;
import ccre.log.Logger;

/**
 * A worker thread that will allow other threads to trigger a predefined action
 * in this thread. Multiple triggerings will be collapsed into a single trigger.
 * If the action is executed while the thread is working, and
 * shouldIgnoreWhileRunning was not set to true in the constructor, the trigger
 * is ignored.
 *
 * This is an EventConsumer - when it is fired, it will trigger this thread's
 * work.
 *
 * @author skeggsc
 */
public abstract class CollapsingWorkerThread extends ReporterThread implements EventOutput {

    /**
     * Does this thread need to run its work?
     */
    private volatile boolean needsToRun = false;
    /**
     * Should this thread ignore any triggers while it is working? This is set
     * by the constructor.
     *
     * @see #CollapsingWorkerThread(java.lang.String, boolean)
     */
    private final boolean shouldIgnoreWhileRunning;
    /**
     * The internal object to use for notification and synchronization.
     */
    private final Object lockObject = new Object();

    /**
     * Create a new CollapsingWorkerThread with the given name. Will ignore any
     * triggers while the work is running.
     *
     * @param name the thread's name
     */
    public CollapsingWorkerThread(String name) {
        super(name);
        shouldIgnoreWhileRunning = true;
    }

    /**
     * Create a new CollapsingWorkerThread with the given name. If
     * shouldIgnoreWhileRunning is true, this will ignore any triggers while the
     * work is running.
     *
     * @param name the thread's name
     * @param shouldIgnoreWhileRunning should the thread ignore triggers while
     * the work is running.
     */
    public CollapsingWorkerThread(String name, boolean shouldIgnoreWhileRunning) {
        super(name);
        this.shouldIgnoreWhileRunning = shouldIgnoreWhileRunning;
    }

    /**
     * Trigger the work. When possible, the thread will run its doWork method.
     * This method exists for using this thread as an EventConsumer. You may
     * prefer trigger() instead, although there is no functional difference.
     *
     * @see #trigger()
     */
    public void event() {
        trigger();
    }

    /**
     * When the given event is fired, trigger this thread's work as in the
     * eventFired() method. This is equivalent to adding this object as a
     * listener to the given EventSource.
     *
     * @param event when to trigger the work.
     * @see #event()
     */
    public void triggerWhen(EventInput event) {
        event.send(this);
    }

    /**
     * Trigger the work. When possible, the thread will run its doWork method.
     */
    public void trigger() {
        synchronized (lockObject) {
            needsToRun = true;
            if (!isAlive()) {
                start();
            } else {
                lockObject.notifyAll();
            }
        }
    }
    /**
     * A BooleanStatus that represents if work is currently running.
     */
    private BooleanStatus runningStatus;

    /**
     * Get a BooleanInput that represents whether or not this thread's work is
     * currently running.
     *
     * @return a BooleanInput reflecting if work is happening.
     */
    public BooleanInput getRunningStatus() {
        if (runningStatus == null) {
            runningStatus = new BooleanStatus();
        }
        return runningStatus;
    }

    @Override
    protected final void threadBody() throws InterruptedException {
        while (true) {
            synchronized (lockObject) {
                while (!needsToRun) {
                    lockObject.wait();
                }
                needsToRun = false;
            }
            try {
                if (runningStatus != null) {
                    runningStatus.set(true);
                    try {
                        doWork();
                    } finally {
                        runningStatus.set(false);
                    }
                } else {
                    doWork();
                }
            } catch (Throwable t) {
                Logger.log(LogLevel.SEVERE, "Uncaught exception in worker thread: " + this.getName(), t);
            }
            if (shouldIgnoreWhileRunning) {
                needsToRun = false;
            }
        }
    }

    /**
     * The implementation of the work to do when this worker is triggered.
     *
     * @throws Throwable if any error occurs. this will not end the thread, but
     * just the current execution of work.
     */
    protected abstract void doWork() throws Throwable;
}
