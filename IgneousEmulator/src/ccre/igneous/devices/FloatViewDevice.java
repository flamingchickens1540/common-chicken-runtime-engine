/*
 * Copyright 2014 Colby Skeggs
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
package ccre.igneous.devices;

import ccre.channel.FloatOutput;
import ccre.igneous.Device;
import ccre.igneous.components.FillBarComponent;
import ccre.igneous.components.SpacingComponent;
import ccre.igneous.components.TextComponent;

public class FloatViewDevice extends Device implements FloatOutput {

    private final FillBarComponent value = new FillBarComponent();
    private final float minInput, maxInput;

    public FloatViewDevice(String label) {
        this(label, -1, 1);
    }

    public FloatViewDevice(String label, float minInput, float maxInput) {
        add(new SpacingComponent(20));
        add(new TextComponent(label));
        add(value);
        this.minInput = minInput;
        this.maxInput = maxInput;
    }

    public void set(float value) {
        this.value.set(2 * (value - minInput) / (maxInput - minInput) - 1);
    }
}