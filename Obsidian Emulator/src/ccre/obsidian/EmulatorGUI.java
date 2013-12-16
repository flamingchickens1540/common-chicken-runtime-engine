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

import java.awt.Component;
import javax.swing.border.TitledBorder;

/**
 *
 * @author millerv
 */
public class EmulatorGUI extends javax.swing.JFrame {

    private final EmulatorPin[] pins = new EmulatorPin[92];

    /**
     * Creates new form EmulatorGUI
     * @param launcher the launcher to send pin update events.
     */
    public EmulatorGUI(EmulatorLauncher launcher) {
        initComponents();

        p9.setBorder(new TitledBorder("P9"));
        p8.setBorder(new TitledBorder("P8"));

        for (int i = 0; i < pins.length; i++) {
            if (i < pins.length / 2) {
                pins[i] = new EmulatorPin(launcher, i % (pins.length / 2) + 1, dragTarget, "P8");
                p8.add(pins[i]);
            } else {
                pins[i] = new EmulatorPin(launcher, i % (pins.length / 2) + 1, dragTarget, "P9");
                p9.add(pins[i]);
            }
        }
    }

    public EmulatorPin getPin(int id) {
        return pins[id - 1];
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        p8 = new javax.swing.JPanel();
        p9 = new javax.swing.JPanel();
        dragTarget = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Beaglebone Black Emulator");

        p8.setBackground(new java.awt.Color(204, 204, 204));
        p8.setLayout(new java.awt.GridLayout(23, 2));

        p9.setBackground(new java.awt.Color(204, 204, 204));
        p9.setLayout(new java.awt.GridLayout(23, 2));

        dragTarget.setBackground(new java.awt.Color(204, 204, 204));
        dragTarget.setPreferredSize(new java.awt.Dimension(300, 500));
        dragTarget.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                dragTargetComponentResized(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(p9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(dragTarget, javax.swing.GroupLayout.DEFAULT_SIZE, 344, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(p8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dragTarget, javax.swing.GroupLayout.DEFAULT_SIZE, 478, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(p9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(p8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void dragTargetComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_dragTargetComponentResized
        for (Component c : getComponents()) {
            if (c instanceof DragSliderPanel) {
                //((DragSliderPanel)c).reposition();
            }
        }
    }//GEN-LAST:event_dragTargetComponentResized


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel dragTarget;
    private javax.swing.JPanel p8;
    private javax.swing.JPanel p9;
    // End of variables declaration//GEN-END:variables
}
