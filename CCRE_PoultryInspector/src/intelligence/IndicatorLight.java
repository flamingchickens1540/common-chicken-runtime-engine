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
package intelligence;

import ccre.channel.BooleanOutput;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import javax.swing.JComponent;

/**
 * An Indicator Light for use on a form.
 *
 * @author skeggsc
 */
public class IndicatorLight extends JComponent implements BooleanOutput {

    private boolean value;

    public boolean getValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                repaint();
            }
        });
    }

    public Dimension getPreferredSize() {
        if (isPreferredSizeSet()) {
            return super.getPreferredSize();
        }
        return new Dimension(20, 20);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        //create a new graphics2D instance
        Graphics g2 = graphics;

        //determine the actual x, y, width and height
        int x = getInsets().left;
        int y = getInsets().top;
        int w = getWidth() - getInsets().left - getInsets().right;
        int h = getHeight() - getInsets().top - getInsets().bottom;

        g2.setColor(value ? Color.YELLOW : Color.GRAY);
        g2.fillOval(x, y, w, h);
    }

    public void set(boolean b) {
        setValue(b);
    }
}
