/*
 * Copyright 2014 Colby Skeggs.
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
package treeframe;

import ccre.channel.FloatInput;
import ccre.channel.FloatOutput;
import ccre.channel.FloatStatus;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;

public class FloatControlComponent extends DraggableBoxComponent implements FloatInput {

    private final String name;
    private final FloatStatus stat = new FloatStatus();

    public FloatControlComponent(int cx, int cy, String name) {
        super(cx, cy);
        this.name = name;
    }

    public FloatControlComponent(int cx, int cy, String name, FloatOutput num) {
        this(cx, cy, name);
        stat.send(num);
    }

    @Override
    protected boolean containsForInteract(int x, int y) {
        return x >= centerX - width + 10 && x <= centerX + width - 10 && y >= centerY - height / 2 && y <= centerY + height / 2;
    }
    
    public boolean wantsDragSelect() {
        return true;
    }

    @Override
    public void render(Graphics2D g, int screenWidth, int screenHeight, FontMetrics fontMetrics, int mouseX, int mouseY) {
        width = Math.max(70, g.getFontMetrics().stringWidth(name) / 2);
        height = width * 2 / 3;
        GradientPaint gp = new GradientPaint(centerX, centerY, Color.YELLOW, centerX + height, centerY - height, Color.ORANGE);
        ((Graphics2D) g).setPaint(gp);
        Shape s = new RoundRectangle2D.Float(centerX - width, centerY - height, width * 2, height * 2, 15, 15);
        ((Graphics2D) g).fill(s);
        g.setColor(Color.BLACK);
        g.drawString(name, centerX - width + 5, centerY - height + 1 + g.getFontMetrics().getAscent());
        g.setColor(Color.WHITE);
        g.fillRect(centerX - width + 10, centerY - height / 2, 2 * width - 19, height);
        g.setColor(Color.BLACK);
        g.drawRect(centerX - width + 10, centerY - height / 2, 2 * width - 20, height - 1);
        g.drawLine(centerX, centerY + height / 2 - 1, centerX, centerY + 5);
        g.drawLine(centerX + width * 2 / 3, centerY + height / 2 - 1, centerX + width * 2 / 3, centerY + 5);
        g.drawLine(centerX - width * 2 / 3, centerY + height / 2 - 1, centerX - width * 2 / 3, centerY + 5);
        g.drawLine(centerX - width / 6, centerY + height / 2 - 1, centerX - width / 6, centerY + 15);
        g.drawLine(centerX + width / 6, centerY + height / 2 - 1, centerX + width / 6, centerY + 15);
        g.drawLine(centerX - width / 3, centerY + height / 2 - 1, centerX - width / 3, centerY + 10);
        g.drawLine(centerX + width / 3, centerY + height / 2 - 1, centerX + width / 3, centerY + 10);
        g.drawLine(centerX - 3 * width / 6, centerY + height / 2 - 1, centerX - 3 * width / 6, centerY + 15);
        g.drawLine(centerX + 3 * width / 6, centerY + height / 2 - 1, centerX + 3 * width / 6, centerY + 15);
        float value = this.stat.get();
        int ptrCtr = centerX + (int) (width * 2 / 3 * value);
        if (value < 0) {
            g.setColor(value == -1 ? Color.RED : Color.RED.darker().darker());
        } else if (value > 0) {
            g.setColor(value == 1 ? Color.GREEN : Color.GREEN.darker().darker());
        } else {
            g.setColor(Color.ORANGE);
        }
        g.drawPolygon(new int[]{ptrCtr - 12, ptrCtr - 8, ptrCtr - 12}, new int[]{centerY - 8, centerY - 4, centerY}, 3);
        g.drawPolygon(new int[]{ptrCtr + 12, ptrCtr + 8, ptrCtr + 12}, new int[]{centerY - 8, centerY - 4, centerY}, 3);
        g.fillRect(ptrCtr - 5, centerY - height / 2 + 1, 11, height / 2 - 4);
        g.fillPolygon(new int[]{ptrCtr - 5, ptrCtr, ptrCtr + 6}, new int[]{centerY - 3, centerY + 3, centerY - 3}, 3);
    }

    @Override
    public boolean onInteract(int x, int y) {
        float value = Math.min(1, Math.max(-1, (x - centerX) / (float) (width * 2 / 3)));
        if (-0.1 < value && value < 0.1) {
            value = 0;
        }
        stat.set(value);
        return true;
    }

    public String toString() {
        return name;
    }

    @Override
    public void send(FloatOutput output) {
        stat.send(output);
    }

    @Override
    public void unsend(FloatOutput output) {
        stat.unsend(output);
    }

    @Override
    public float get() {
        return stat.get();
    }
}
