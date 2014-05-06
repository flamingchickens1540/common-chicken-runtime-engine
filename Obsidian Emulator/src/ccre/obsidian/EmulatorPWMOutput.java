/*
 * Copyright 2013 Vincent Miller
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

package ccre.obsidian;

import ccre.channel.FloatOutput;

/**
 *
 * @author millerv
 */
public class EmulatorPWMOutput implements FloatOutput {
    private final EmulatorPin pin;
    
    public EmulatorPWMOutput(EmulatorPin pin) {
        this.pin = pin;
    }

    @Override
    public void set(float value) {
        if (pin.getMode() == EmulatorPin.Mode.PWM) {
            pin.set(value);
        } else {
            throw new UnsupportedOperationException("Cannot write float in mode: " + pin.getMode().name());
        }
    }
    
}
