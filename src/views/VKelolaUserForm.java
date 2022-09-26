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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import models.ItemBarang;

/**
 *
 * @author Chandra
 */
public class VKelolaUserForm extends javax.swing.JFrame {

    VKasir vKasir = VKasir.getInstance();
    private String satuanId;
    private String userId;
    private int tipeForm;
    private Map<String, String> map;

    /**
     * Creates new form VKasir
     */
    public VKelolaUserForm(int type) {
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
        tfNama = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jbTombol = new javax.swing.JButton();
        tfUsername = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jpfPassword = new javax.swing.JPasswordField();
        jpfPasswordConfirm = new javax.swing.JPasswordField();

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
        jLabel1.setText("NAMA USER");

        tfNama.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("PASSWORD");

        jbTombol.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jbTombol.setText("TAMBAH");
        jbTombol.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jbTombolMouseClicked(evt);
            }
        });

        tfUsername.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("USERNAME");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("PASSWORD USER LOGIN");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1)
                            .addComponent(tfNama)
                            .addComponent(jLabel2)
                            .addComponent(jbTombol, javax.swing.GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE)
                            .addComponent(jLabel4)
                            .addComponent(tfUsername)
                            .addComponent(jLabel5))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jpfPassword)
                    .addComponent(jpfPasswordConfirm))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tfNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tfUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpfPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(64, 64, 64)
                .addComponent(jLabel5)
                .addGap(5, 5, 5)
                .addComponent(jpfPasswordConfirm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jbTombol)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tfNama.getAccessibleContext().setAccessibleName("");

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
        try {
            VKelolaUser vKelolaUser = vKasir.getKelolaUser();
            String idLogin = vKasir.getAuthEncryptor().getIdLogin();

//            cek password user login dulu
            String passwd = new String(jpfPasswordConfirm.getPassword());
            String encryptedPasswd
                    = vKasir.getAuthEncryptor().hashPassword(passwd);

            String userPassPlain = new String(jpfPassword.getPassword());
            String userPass = vKasir.getAuthEncryptor().hashPassword(
                    userPassPlain);

            java.sql.ResultSet rs;
            java.sql.Connection connCheck = vKasir.getDBConn();
            java.sql.PreparedStatement psCheck;

            psCheck = connCheck.prepareStatement("SELECT "
                    + "id, username, password, nama"
                    + " FROM user"
                    + " WHERE user.id = '" + idLogin + "' "
            );
            rs = psCheck.executeQuery();

            boolean isPass = false;
            while (rs.next()) {
                if (vKasir.getAuthEncryptor().checkPassword(
                        passwd, rs.getString("password"))) {
                    isPass = true;
                }
            }

            if (!isPass) {
                JOptionPane.showMessageDialog(null, "Maaf, password User Login "
                        + "SALAH! Mohon ulangi kembali");
                return;
            }

            if (this.tipeForm == 1) { //tambah
                String sql
                        = "insert into user "
                        + "(nama, username, password) values('"
                        + tfNama.getText() + "','" + tfUsername.getText() 
                        + "','" + userPass
                        + "')";

                java.sql.Connection conn = vKasir.getDBConn();
                java.sql.PreparedStatement pst = conn.prepareStatement(sql);
                pst.execute();

                JOptionPane.showMessageDialog(null, "Berhasil disimpan");
            } else {
                java.sql.Connection conn = vKasir.getDBConn();
                java.sql.PreparedStatement ps;
                if (userPassPlain.equalsIgnoreCase("")) {
                    ps = conn.prepareStatement("UPDATE "
                            + " user"
                            + " SET nama = '" + tfNama.getText() + "'"
                            + ", username = '" + tfUsername.getText() + "'"
                            + " WHERE id = '" + vKelolaUser.getSelectedRowId() + "' "
                    );
                } else {
                    ps = conn.prepareStatement("UPDATE "
                            + " user"
                            + " SET nama = '" + tfNama.getText() + "'"
                            + ", username = '" + tfUsername.getText() + "'"
                            + ", password = '" + userPass + "'"
                            + " WHERE id = '" + vKelolaUser.getSelectedRowId() + "' "
                    );
                }

                ps.executeUpdate();
            }

            tfNama.setText("");
            tfUsername.setText("");
            jpfPassword.setText("");
            jpfPasswordConfirm.setText("");

            vKelolaUser.getData("");

        } catch (SQLException | HeadlessException e) {
            JOptionPane.showMessageDialog(null, "Gagal menyimpan data : "+e);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_jbTombolMouseClicked

    public void setTfNama(String text) {
        tfNama.setText(text);
    }

    public void setTfUsername(String text) {
        tfUsername.setText(text);
    }

    public void setUserId(String text) {
        this.userId = text;
    }

    public void setType(int type) {
        this.tipeForm = type;
        if (type == 1) {
            this.setTitle("TANI JAYA - Tambah User");
            jbTombol.setText("TAMBAH");
        } else {
            this.setTitle("TANI JAYA - Edit User");
            jbTombol.setText("UPDATE");
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JButton jbTombol;
    private javax.swing.JPasswordField jpfPassword;
    private javax.swing.JPasswordField jpfPasswordConfirm;
    private javax.swing.JTextField tfNama;
    private javax.swing.JTextField tfUsername;
    // End of variables declaration//GEN-END:variables
}
