/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.AbstractCellEditor;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import models.ItemBarang;

/**
 *
 * @author Chandra
 */
public class VKelolaBarang extends javax.swing.JFrame {

    /**
     * Creates new form VKasir
     */
    VKasir vKasir = VKasir.getInstance();
    private DefaultTableModel model;
    private String selectedRowId;

    public VKelolaBarang() {
        Toolkit kit = Toolkit.getDefaultToolkit();
        Image img = kit.createImage(vKasir.getLogo());
        setIconImage(img);
        
        setIconImage(img);
        this.selectedRowId = "0";
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
        
        this.setTitle(vKasir.getAppConfig()
                .getConfig("APP_NAME")+" - Kelola Barang");

//        table
        model = new DefaultTableModel();
        jtBarang.setModel(model);
        model.addColumn("KODE");
        model.addColumn("NAMA");
        model.addColumn("SATUAN");
        model.addColumn("HARGA MODAL");
        model.addColumn("HARGA JUAL");
        model.addColumn("ACTION");

        jtBarang.getColumnModel().getColumn(5).setMinWidth(0);
        jtBarang.getColumnModel().getColumn(5).setMaxWidth(0);
        jtBarang.getColumnModel().getColumn(5).setWidth(0);
//        getData("");

//        set event ke search bar
        jSearch.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    try {
                        String searchText = (String) jSearch.getText();
                        searchText = searchText.trim().toLowerCase();

                        System.out.println(searchText);
                        getData(searchText);
                    } catch (Exception ex) {
                        System.out.println("Ex log " + ex.getMessage());
                    }
                }
            }
        });

//        set event kalau column di select
        jtBarang.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //
            }

            @Override
            public void mousePressed(MouseEvent e) {
                String selectedCellValue = (String) jtBarang.getValueAt(
                        jtBarang.getSelectedRow(), 5);
                System.out.println(selectedCellValue);

//                set id selected
                selectedRowId = selectedCellValue;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                //
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                //
            }

            @Override
            public void mouseExited(MouseEvent e) {
                //
            }

        });
    }

    public void getData(String keyword) {
        //menghapus isi table
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();

        try {
            java.sql.ResultSet rs;
            java.sql.Connection conn = vKasir.getDBConn();
            java.sql.PreparedStatement ps = conn.prepareStatement("SELECT "
                    + "barang.id, kode, nama, satuan, harga_beli, harga_jual, satuan"
                    + " FROM barang"
                    + " LEFT JOIN satuan ON satuan.id = barang.satuan_id"
                    + " WHERE (nama LIKE '%" + keyword + "%' "
                    + " OR kode LIKE '%" + keyword + "%')"
                    + " AND deleted_at IS NULL "
            );
            rs = ps.executeQuery();

            models.ItemBarang ib;

            while (rs.next()) {
                Object[] obj = new Object[6];
                obj[0] = rs.getString("kode");
                obj[1] = rs.getString("nama");
                obj[2] = rs.getString("satuan");
                obj[3] = rs.getString("harga_beli");
                obj[4] = rs.getString("harga_jual");
                obj[5] = rs.getString("id");

                model.addRow(obj);
                System.out.println(rs.getString("nama"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR load data " + e.getMessage());
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
        jScrollPane1 = new javax.swing.JScrollPane();
        jtBarang = new javax.swing.JTable();
        jSearch = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jbEdit = new javax.swing.JButton();
        jbHapus = new javax.swing.JButton();

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

        jtBarang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(jtBarang);

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setText("Search");

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton2.setText("TAMBAH BARANG");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });

        jbEdit.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jbEdit.setText("EDIT");
        jbEdit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jbEditMouseClicked(evt);
            }
        });
        jbEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbEditActionPerformed(evt);
            }
        });

        jbHapus.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jbHapus.setText("HAPUS");
        jbHapus.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jbHapusMouseClicked(evt);
            }
        });
        jbHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbHapusActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 720, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jbEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jbHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(jButton2)
                    .addComponent(jbEdit)
                    .addComponent(jbHapus))
                .addGap(9, 9, 9)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 377, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        // TODO add your handling code here:
        VKelolaBarangForm formTambah = vKasir.getFormTambah();
        if (!formTambah.isVisible()) {
            formTambah.pack();
            formTambah.setLocationRelativeTo(null);
            formTambah.setVisible(true);
            formTambah.setDefaultCloseOperation(VKelolaBarangForm.DISPOSE_ON_CLOSE);
        }
    }//GEN-LAST:event_jButton2MouseClicked

    private void jbEditMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbEditMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jbEditMouseClicked

    private void jbEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbEditActionPerformed
        // TODO add your handling code here:
        VKelolaBarangForm kelolaBarangForm = vKasir.getKelolaBarangForm();
        if (!kelolaBarangForm.isVisible()) {
            kelolaBarangForm.pack();
            kelolaBarangForm.setLocationRelativeTo(null);
            kelolaBarangForm.setVisible(true);
            kelolaBarangForm.setDefaultCloseOperation(VKelolaBarangForm.DISPOSE_ON_CLOSE);

//            set typenya jadi edit mode
            kelolaBarangForm.setType(2);

//            get data
            try {
                java.sql.ResultSet rs;
                java.sql.Connection conn = vKasir.getDBConn();
                java.sql.PreparedStatement ps = conn.prepareStatement("SELECT "
                        + "barang.id, kode, nama, satuan, harga_beli, harga_jual, satuan"
                        + " FROM barang"
                        + " LEFT JOIN satuan ON satuan.id = barang.satuan_id"
                        + " WHERE barang.id = " + this.selectedRowId + " "
                        + " AND barang.deleted_at IS NULL "
                );
                rs = ps.executeQuery();

                while (rs.next()) {
                    models.ItemBarang ib;

                    kelolaBarangForm.setTfNamaBarang(rs.getString("nama"));
                    kelolaBarangForm.setTfKode(rs.getString("kode"));
                    kelolaBarangForm.setTfNamaBarang(rs.getString("nama"));
                    kelolaBarangForm.setTfHargaBeli(rs.getString("harga_beli"));
                    kelolaBarangForm.setTfHargaJual(rs.getString("harga_jual"));

//                get item
                    java.sql.ResultSet iId;
                    java.sql.PreparedStatement iIdPs = conn.prepareStatement("SELECT "
                            + "id, satuan"
                            + " FROM satuan"
                            + " WHERE satuan = '" + rs.getString("satuan") + "' "
                    );
                    iId = iIdPs.executeQuery();
                    
                    if (iId.next()) {
                        ItemBarang item
                                = new models.ItemBarang(iId.getString(1), 
                                        iId.getString(2));
                        
                        kelolaBarangForm.setCbSatuan(item);
                    }
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR load data " + e.getMessage());
            }
        }
    }//GEN-LAST:event_jbEditActionPerformed

    private void jbHapusMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbHapusMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jbHapusMouseClicked

    private void jbHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbHapusActionPerformed
        // TODO add your handling code here:
        deleteData();
    }//GEN-LAST:event_jbHapusActionPerformed

//    method delete
    private void deleteData() {
        try {
            int n = JOptionPane.showConfirmDialog(
                            null, "Apakah Anda yakin ingin menghapus data barang ini?",
                            "Hapus Barang",
                            JOptionPane.YES_NO_OPTION);
            
            if (n == JOptionPane.NO_OPTION) {
                return;
            }
            
            String sId = this.selectedRowId;
            if (sId == "0") {
                JOptionPane.showMessageDialog(null, "Silahkan pilih data terlebih dahulu!");
            } else {
                java.sql.Connection conn = vKasir.getDBConn();
                java.sql.PreparedStatement ps = conn.prepareStatement("UPDATE "
                        + " barang"
                        + " SET deleted_at = NOW()"
                        + " WHERE id = '" + this.selectedRowId + "' "
                );
                ps.executeUpdate();

                jSearch.setText("");
                getData("");
                JOptionPane.showMessageDialog(null, "Berhasil menghapus data!");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR load data " + e.getMessage());
        }
    }
    
    public String getSelectedRowId(){
        return this.selectedRowId;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jSearch;
    private javax.swing.JButton jbEdit;
    private javax.swing.JButton jbHapus;
    private javax.swing.JTable jtBarang;
    // End of variables declaration//GEN-END:variables
}
