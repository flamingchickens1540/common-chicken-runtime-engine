/*
 * Copyright 2016 Cel Skeggs
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
package ccre.scheduler;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import ccre.channel.EventOutput;
import ccre.time.FakeTime;
import ccre.time.SharedTimeSecrets;
import ccre.time.Time;

public class VirtualTime {
    private static Time oldProvider;
    private static FakeTime fake;

    public static synchronized void startFakeTime() {
        assertNull(oldProvider);
        oldProvider = Time.getTimeProvider();
        fake = new FakeTime();
        Time.setTimeProvider(fake);
        Scheduler.__UNSAFE_reset(new IRunLoop() {
            @Override
            public void terminate() {
                // nothing
            }

            @Override
            public void start() {
                // nothing
            }

            @Override
            public void add(String tag, EventOutput event, long time) {
                SharedTimeSecrets.scheduleFakeLoop(fake, event, time);
                // and discard tag
            }
        });
    }

    public static synchronized void endFakeTime() {
        assertNotNull(oldProvider);
        Time.setTimeProvider(oldProvider);
        oldProvider = null;
        fake = null;
    }

    public static void forward(long l) throws InterruptedException {
        fake.forward(l);
    }
}
