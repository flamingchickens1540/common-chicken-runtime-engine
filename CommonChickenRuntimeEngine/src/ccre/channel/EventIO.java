/*
 * Copyright 2015-2016 Cel Skeggs
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

import ccre.verifier.SetupPhase;

/**
 * A EventIO is both a EventInput and a EventOutput.
 * 
 * @author skeggsc
 */
public interface EventIO extends EventInput, EventOutput {

    /**
     * Returns the output side of this EventIO. This is equivalent to upcasting
     * to EventOutput.
     *
     * @return this io, as an output.
     */
    @SetupPhase
    public default EventOutput asOutput() {
        return this;
    }

    /**
     * Returns the input side of this EventIO. This is equivalent to upcasting
     * to EventInput.
     *
     * @return this io, as an input.
     */
    @SetupPhase
    public default EventInput asInput() {
        return this;
    }

    /**
     * Compose an EventInput and an EventOutput into a single EventIO, which
     * dispatches to the two implementations.
     *
     * @param input the input to dispatch to.
     * @param output the output to dispatch to.
     * @return the composed EventIO.
     */
    @SetupPhase
    public static EventIO compose(EventInput input, EventOutput output) {
        return new EventIO() {
            @Override
            public CancelOutput onUpdate(EventOutput notify) {
                return input.onUpdate(notify);
            }

            @Override
            public void event() {
                output.event();
            }
        };
    }

    @Override
    @SetupPhase
    public default EventIO cell() {
        return this;
    }
}
