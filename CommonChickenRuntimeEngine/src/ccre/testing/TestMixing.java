/*
 * Copyright 2013 Colby Skeggs
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
package ccre.testing;

import ccre.chan.FloatFilter;
import ccre.ctrl.Mixing;
import ccre.util.CArrayList;
import ccre.util.CArrayUtils;
import ccre.util.CList;
import ccre.util.Utils;
import java.util.Iterator;

/**
 * Tests the Mixing class, a tiny amount.
 *
 * @author skeggsc
 */
public class TestMixing extends BaseTest {

    @Override
    public String getName() {
        return "Minimal Mixing Test";
    }

    @Override
    protected void runTest() throws TestingException {
        // Added to as I need to test things, not fleshed out yet.
        FloatFilter limit1 = Mixing.limit(Float.NEGATIVE_INFINITY, 0);
        assertEqual(limit1.filter(-10000), -10000f, "Bad limit!");
        assertEqual(limit1.filter(0), 0f, "Bad limit!");
        assertEqual(limit1.filter(1), 0f, "Bad limit!");
        FloatFilter limit2 = Mixing.limit(0, Float.POSITIVE_INFINITY);
        assertEqual(limit2.filter(-1), 0f, "Bad limit!");
        assertEqual(limit2.filter(0), 0f, "Bad limit!");
        assertEqual(limit2.filter(10000), 10000f, "Bad limit!");
    }
}