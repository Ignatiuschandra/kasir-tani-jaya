/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import models.ItemBarang;

/**
 *
 * @author Chandra
 */
public class VKelolaPengeluaranForm extends javax.swing.JFrame {

    VKasir vKasir = VKasir.getInstance();
    private String satuanId;
    private int tipeForm;
    private Map<String, String> map;

    /**
     * Creates new form VKasir
     */
    public VKelolaPengeluaranForm(int type) {
        Toolkit kit = Toolkit.getDefaultToolkit();
        Image img = kit.createImage(vKasir.getLogo());
        setIconImage(img);

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VKelolaBarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VKelolaBarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VKelolaBarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VKelolaBarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        initComponents();
        //        set color
        jPanel2.setBackground(
                Color.decode(vKasir.getAppConfig().getConfig("APP_MAIN_COLOR")));

        this.tipeForm = type;

        setType(tipeForm);
        jdcTanggal.setDateFormatString("dd MMMM yyyy");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jlHargaBeli = new javax.swing.JLabel();
        tfPengeluaran = new javax.swing.JTextField();
        jbTombol = new javax.swing.JButton();
        tfKeterangan = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jdcTanggal = new com.toedter.calendar.JDateChooser();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        jPanel3.setBackground(new java.awt.Color(204, 204, 204));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setText("TGL TRANSAKSI");

        jlHargaBeli.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jlHargaBeli.setText("PENGELUARAN (Rp.)");

        tfPengeluaran.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jbTombol.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jbTombol.setText("TAMBAH");
        jbTombol.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jbTombolMouseClicked(evt);
            }
        });
        jbTombol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbTombolActionPerformed(evt);
            }
        });

        tfKeterangan.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("KETERANGAN PENGELUARAN");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1)
                    .addComponent(jlHargaBeli)
                    .addComponent(tfPengeluaran)
                    .addComponent(jbTombol, javax.swing.GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE)
                    .addComponent(jLabel4)
                    .addComponent(tfKeterangan)
                    .addComponent(jdcTanggal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(2, 2, 2)
                .addComponent(jdcTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tfKeterangan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jlHargaBeli)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tfPengeluaran, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jbTombol)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbTombolMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbTombolMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jbTombolMouseClicked

    private void jbTombolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbTombolActionPerformed
        // TODO add your handling code here:
        this.action();
    }//GEN-LAST:event_jbTombolActionPerformed

    private void action() {
        try {
            VKelolaPengeluaran vKelolaPengeluaran = vKasir.getKelolaPengeluaran();

            SimpleDateFormat dcn = new SimpleDateFormat("yyyy-MM-dd");
            String tgl_transaksi = dcn.format(jdcTanggal.getDate());

            if (this.tipeForm == 1) { //tambah
//                 buat ID untuk transaksi
                Calendar calendar = Calendar.getInstance();
                String idTrx = "TRXP" + calendar.getTimeInMillis();

                String sql
                        = "insert into pengeluaran "
                        + "(id, keterangan, total, user_id, tgl_transaksi) values('"
                        + idTrx
                        + "','" + tfKeterangan.getText()
                        + "'," + Integer.parseInt(tfPengeluaran.getText())
                        + "," + vKasir.getAuthEncryptor().getIdLogin()
                        + ",'" + tgl_transaksi + "')";
                
                System.out.println(sql);

                java.sql.Connection conn = vKasir.getDBConn();
                java.sql.PreparedStatement pst = conn.prepareStatement(sql);
                pst.execute();
            } else {
                java.sql.Connection conn = vKasir.getDBConn();
                java.sql.PreparedStatement ps = conn.prepareStatement("UPDATE "
                        + " pengeluaran SET"
                        + " keterangan = '" + tfKeterangan.getText() + "'"
                        + ", total = " + Integer.parseInt(tfPengeluaran.getText()) + ""
                        + ", user_id = " + vKasir.getAuthEncryptor().getIdLogin() + ""
                        + ", tgl_transaksi = '" + tgl_transaksi + "'"
                        + " WHERE id = '" + vKelolaPengeluaran.getSelectedRowId() + "' "
                );
                ps.executeUpdate();
            }

            tfKeterangan.setText("");
            tfPengeluaran.setText("");
            jdcTanggal.setCalendar(null);
            vKelolaPengeluaran.getData("");
            this.setVisible(false);
            JOptionPane.showMessageDialog(null, "Berhasil disimpan");

        } catch (SQLException | HeadlessException e) {
            JOptionPane.showMessageDialog(null, e);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
    }

    public void setTfKeterangan(String text) {
        tfKeterangan.setText(text);
    }

    public void setTfTotal(String text) {
        double d = Double.parseDouble(text);
        int i = (int)d;  
        tfPengeluaran.setText(Integer.toString(i));
    }
    
    public void setjdcTanggal(String text) {
   java.util.Date date2;
        try {
            date2 = new SimpleDateFormat("yyyy-MM-dd").parse(text);
            jdcTanggal.setDate(date2);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
    }

    public void setType(int type) {
        this.tipeForm = type;
        if (type == 1) {
            this.setTitle(vKasir.getAppConfig()
                    .getConfig("APP_NAME") + " - Tambah Barang");
            jbTombol.setText("TAMBAH");
        } else {
            this.setTitle(vKasir.getAppConfig()
                    .getConfig("APP_NAME") + " - Edit Barang");
            jbTombol.setText("UPDATE");
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JButton jbTombol;
    private com.toedter.calendar.JDateChooser jdcTanggal;
    private javax.swing.JLabel jlHargaBeli;
    private javax.swing.JTextField tfKeterangan;
    private javax.swing.JTextField tfPengeluaran;
    // End of variables declaration//GEN-END:variables
}
