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

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * A SuperCanvasComponent can be displayed and interacted with inside a
 * SuperCanvas.
 *
 * @author skeggsc
 */
public abstract class SuperCanvasComponent {

    private SuperCanvasPanel panel;

    /**
     * Called to render this component.
     *
     * @param g The graphics pen to use to render.
     * @param screenWidth The width of the screen.
     * @param screenHeight The height of the screen.
     * @param fontMetrics The metrics of the (monospaced) font.
     * @param mouseX The mouse X position.
     * @param mouseY The mouse Y position.
     */
    public abstract void render(Graphics2D g, int screenWidth, int screenHeight, FontMetrics fontMetrics, int mouseX, int mouseY);

    /**
     * Check if the specified point is on this component.
     *
     * @param x The mouse X coordinate.
     * @param y The mouse Y coordinate.
     * @return if the point is contained in this component's area.
     */
    public abstract boolean contains(int x, int y);

    /**
     * Called when the component is right-clicked.
     *
     * @param x The mouse X coordinate.
     * @param y The mouse Y coordinate.
     * @return true if the interaction event should not be sent to more
     * components.
     */
    public abstract boolean onInteract(int x, int y);

    /**
     * Called when the component is left-clicked if it's not dragged.
     *
     * @param x The mouse X coordinate.
     * @param y The mouse Y coordinate.
     * @return true if the selection event should not be sent to more
     * components.
     */
    public abstract boolean onSelect(int x, int y);

    /**
     * Called when the mouse enters this component.
     *
     * @param x The mouse X position.
     * @param y The mouse Y position.
     * @return true if the screen should be repainted because of this
     * transition.
     */
    public boolean onMouseEnter(int x, int y) {
        return false;
    }

    /**
     * Called when the mouse moves within this component.
     *
     * @param x The mouse X position.
     * @param y The mouse Y position.
     * @return true if the screen should be repainted because of this change.
     */
    public boolean onMouseMove(int x, int y) {
        return false;
    }

    /**
     * Called when the mouse exits this component.
     *
     * @param x The mouse X position.
     * @param y The mouse Y position.
     * @return true if the screen should be repainted because of this
     * transition.
     */
    public boolean onMouseExit(int x, int y) {
        return false;
    }

    /**
     * Called when the mouse scroll wheel is changed while over this component.
     *
     * @param x The mouse X position.
     * @param y The mouse Y position.
     * @param wheelRotation The change in wheel rotation.
     * @return true if the scrolling event should not be sent to more
     * components.
     */
    public boolean onScroll(int x, int y, int wheelRotation) {
        return false;
    }

    /**
     * Called when this component starts to be dragged to calculate the offset
     * used later - which will be added to the mouse position to get the new
     * position given to moveForDrag.
     *
     * @param x The mouse X position.
     * @return the relative X offset.
     */
    public int getDragRelX(int x) {
        throw new UnsupportedOperationException("Dragging not supported!");
    }

    /**
     * Called when this component starts to be dragged to calculate the offset
     * used later - which will be added to the mouse position to get the new
     * position given to moveForDrag.
     *
     * @param y The mouse Y position.
     * @return the relative Y offset.
     */
    public int getDragRelY(int y) {
        throw new UnsupportedOperationException("Dragging not supported!");
    }

    /**
     * Called when the component needs to be moved as part of a drag operation.
     * The relative difference between this position and the mouse position is
     * calculated by getDragRelX() and getDragRelY().
     *
     * @param x The new X position.
     * @param y The new Y position.
     */
    public void moveForDrag(int x, int y) {
        throw new UnsupportedOperationException("Dragging not supported!");
    }

    /**
     * Called when the dragged entity is dropped onto this component.
     *
     * @param x The mouse X position.
     * @param y The mouse Y position.
     * @param activeEntity The entity that was dropped.
     * @return true if the drop has been received and shouldn't be sent to any
     * other components.
     */
    public boolean onReceiveDrop(int x, int y, SuperCanvasComponent activeEntity) {
        return false;
    }

    void setPanel(SuperCanvasPanel npanel) {
        if (npanel == null) {
            throw new NullPointerException();
        }
        if (panel != null) {
            throw new IllegalStateException("This already has a panel!");
        }
        panel = npanel;
        onChangePanel(npanel);
    }

    void unsetPanel(SuperCanvasPanel npanel) {
        if (npanel == null) {
            throw new NullPointerException();
        }
        if (panel != npanel) {
            throw new IllegalStateException("That's not the panel here!");
        }
        panel = null;
        onChangePanel(null);
    }

    public SuperCanvasPanel getPanel() {
        return panel;
    }

    protected void onChangePanel(SuperCanvasPanel panel) {
    }

    public boolean wantsDragSelect() {
        return false;
    }
}
