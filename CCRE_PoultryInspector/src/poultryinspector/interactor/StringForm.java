package poultryinspector.interactor;

import ccre.event.EventConsumer;
import ccre.event.EventSource;
import ccre.holders.StringHolder;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class StringForm extends javax.swing.JFrame {

    protected final StringHolder holder;

    public static void create(final String name, final StringHolder holder) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new StringForm(name, holder).setVisible(true);
            }
        });
    }

    private StringForm(String name, final StringHolder holder) {
        initComponents();
        this.holder = holder;
        final Runnable rn = new Runnable() {
            @Override
            public void run() {
                labValue.setText(StringForm.this.holder.get());
            }
        };
        final EventConsumer ec = new EventConsumer() {
            @Override
            public void eventFired() {
                java.awt.EventQueue.invokeLater(rn);
            }
        };
        final EventSource mod = holder.getModifiedEvent();
        mod.addListener(ec);
        rn.run();
        labName.setText(name);
        this.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
                mod.removeListener(ec);
                setVisible(false);
                dispose();
            }

            @Override
            public void windowClosed(WindowEvent e) {
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtValue = new javax.swing.JTextField();
        labValue = new javax.swing.JLabel();
        btnModify = new javax.swing.JButton();
        labName = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);

        txtValue.setText("Text");

        labValue.setText("<VALUE>");

        btnModify.setText("Modify");
        btnModify.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModifyActionPerformed(evt);
            }
        });

        labName.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        labName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labName.setText("<NAME>");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnModify, javax.swing.GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE)
            .addComponent(txtValue)
            .addComponent(labValue, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(labName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(labName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labValue)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnModify))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnModifyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModifyActionPerformed
        holder.set(txtValue.getText());
    }//GEN-LAST:event_btnModifyActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnModify;
    private javax.swing.JLabel labName;
    private javax.swing.JLabel labValue;
    private javax.swing.JTextField txtValue;
    // End of variables declaration//GEN-END:variables
}
