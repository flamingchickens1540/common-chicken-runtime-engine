package poultryinspector.interactor;

import ccre.event.EventConsumer;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class EventConsumerForm extends javax.swing.JFrame {

    private final EventConsumer consumer;

    public static void create(final String name, final EventConsumer es) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new EventConsumerForm(name, es).setVisible(true);
            }
        });
    }

    private EventConsumerForm(String name, EventConsumer consumer) {
        initComponents();
        labName.setText(name);
        this.consumer = consumer;
        this.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
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

        labName = new javax.swing.JLabel();
        labProduce = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);

        labName.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        labName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labName.setText("<NAME>");

        labProduce.setText("Produce");
        labProduce.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                labProduceActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(labProduce, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(labName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labProduce))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void labProduceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_labProduceActionPerformed
        consumer.eventFired();
    }//GEN-LAST:event_labProduceActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel labName;
    private javax.swing.JButton labProduce;
    // End of variables declaration//GEN-END:variables
}
