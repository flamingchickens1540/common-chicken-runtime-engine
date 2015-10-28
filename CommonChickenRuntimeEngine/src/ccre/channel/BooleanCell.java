/*
 * Copyright 2013-2015 Colby Skeggs
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
package ccre.channel;

import java.io.Serializable;

/**
 * A virtual node that is both a BooleanOutput and a BooleanInput. You can
 * modify its value, read its value, and subscribe to changes in its value.
 * BooleanStatus also provides a number of useful helper functions.
 *
 * @author skeggsc
 */
public class BooleanCell extends AbstractUpdatingInput implements BooleanOutput, BooleanInput, Serializable {

    private static final long serialVersionUID = 4843658543933463459L;

    /**
     * The current state (true or false) of this BooleanStatus. Do not directly
     * modify this field. Use the writeValue method instead.
     *
     * @see #set(boolean)
     */
    private boolean value;

    /**
     * Create a new BooleanStatus with the value of false.
     */
    public BooleanCell() {
    }

    /**
     * Create a new BooleanStatus with a specified value.
     *
     * @param default_ The default value.
     */
    public BooleanCell(boolean default_) {
        this.value = default_;
    }

    /**
     * Create a new BooleanStatus with the value of false that automatically
     * updates all of the specified BooleanOutputs with the current state of
     * this BooleanStatus. This is the same as creating a new BooleanStatus and
     * then adding all of the BooleanOutputs as targets.
     *
     * @see BooleanCell#send(ccre.channel.BooleanOutput)
     * @param targets The BooleanOutputs to automatically update.
     */
    public BooleanCell(BooleanOutput... targets) {
        for (BooleanOutput out : targets) {
            send(out);
        }
    }

    /**
     * When the specified event occurs, toggle the status.
     *
     * @param event When to toggle the status.
     * @see #getToggleEvent()
     * @see #toggle()
     */
    public final void toggleWhen(EventInput event) {
        event.send(getToggleEvent());
    }

    /**
     * Get an EventOutput that, when fired, will toggle the state.
     *
     * @return the EventOutput.
     * @see #toggleWhen(ccre.channel.EventInput)
     * @see #toggle()
     */
    public final EventOutput getToggleEvent() {
        return this::toggle;
    }

    /**
     * Toggle the value. True to false, and false to true.
     *
     * @see #toggleWhen(EventInput)
     * @see #getToggleEvent()
     */
    public void toggle() {
        set(!get());
    }

    public final synchronized void set(boolean value) {
        if (this.value != value) {
            this.value = value;
            perform();
        }
    }

    public final synchronized boolean get() {
        return value;
    }

    /**
     * Returns a version of this status as an output. This is equivalent to
     * upcasting to BooleanOutput.
     *
     * @return this status, as an output.
     */
    public BooleanOutput asOutput() {
        return this;
    }

    /**
     * Returns a version of this status as an input. This is equivalent to
     * upcasting to BooleanInput.
     *
     * @return this status, as an input.
     */
    public BooleanInput asInput() {
        return this;
    }
}