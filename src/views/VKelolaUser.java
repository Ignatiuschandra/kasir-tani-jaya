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
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import javax.swing.AbstractAction;
import javax.swing.AbstractCellEditor;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import models.ItemBarang;
import models.StandardItem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Chandra
 */
public class VKelolaUser extends javax.swing.JFrame {

    /**
     * Creates new form VKasir
     */
    VKasir vKasir = VKasir.getInstance();
    private DefaultTableModel model;
    private String selectedRowId;
    private int currMonth;
    private String currYear;
    private final static Logger log = LogManager.getLogger(VKelolaUser.class);

    public VKelolaUser() {
        Toolkit kit = Toolkit.getDefaultToolkit();
        Image img = kit.createImage(vKasir.getLogo());
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
            java.util.logging.Logger.getLogger(VKelolaUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VKelolaUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VKelolaUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VKelolaUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        initComponents();

        this.currMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
        this.currYear = String.valueOf(Calendar.getInstance()
                .get(Calendar.YEAR));

        //        set color
        jPanel2.setBackground(
                Color.decode(vKasir.getAppConfig().getConfig("APP_MAIN_COLOR")));

        this.setTitle(vKasir.getAppConfig()
                .getConfig("APP_NAME") + " - Kelola User");

//        table
        model = new DefaultTableModel();
        jtBarang.setModel(model);
        model.addColumn("NAMA");
        model.addColumn("USERNAME");
        model.addColumn("ACTION");

        jtBarang.getColumnModel().getColumn(2).setMinWidth(0);
        jtBarang.getColumnModel().getColumn(2).setMaxWidth(0);
        jtBarang.getColumnModel().getColumn(2).setWidth(0);

//        set align
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
//        jtBarang.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
//        jtBarang.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
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
                        jtBarang.getSelectedRow(), 2);
                System.out.println(selectedCellValue);

//                set id selected
                selectedRowId = selectedCellValue;
            }

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

    public void getData(String keyword) throws ParseException {
        //menghapus isi table
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();

        try {
            java.sql.ResultSet rs;
            java.sql.Connection conn = vKasir.getDBConn();
            java.sql.PreparedStatement ps = conn.prepareStatement("SELECT "
                    + "id, nama, username"
                    + " FROM user"
                    + " WHERE deleted_at IS NULL"
            );
            rs = ps.executeQuery();

            models.ItemBarang ib;

            while (rs.next()) {
                Object[] obj = new Object[3];
                obj[0] = rs.getString("nama");
                obj[1] = rs.getString("username");
                obj[2] = rs.getString("id");

                model.addRow(obj);
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
        jbEdit = new javax.swing.JButton();
        jbDelete = new javax.swing.JButton();
        jbTambah = new javax.swing.JButton();

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

        jbDelete.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jbDelete.setText("HAPUS");
        jbDelete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jbDeleteMouseClicked(evt);
            }
        });
        jbDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbDeleteActionPerformed(evt);
            }
        });

        jbTambah.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jbTambah.setText("TAMBAH");
        jbTambah.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jbTambahMouseClicked(evt);
            }
        });
        jbTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbTambahActionPerformed(evt);
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
                        .addGap(5, 5, 5)
                        .addComponent(jbTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                    .addComponent(jbEdit)
                    .addComponent(jbDelete)
                    .addComponent(jbTambah))
                .addGap(11, 11, 11)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE)
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

    private void jbEditMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbEditMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jbEditMouseClicked

    private void jbEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbEditActionPerformed
        // TODO add your handling code here:
        VKelolaUserForm kelolaUserForm = vKasir.getFormUser();
        if (!kelolaUserForm.isVisible()) {
            kelolaUserForm.pack();
            kelolaUserForm.setLocationRelativeTo(null);
            kelolaUserForm.setVisible(true);
            kelolaUserForm.setDefaultCloseOperation(VKelolaBarangForm.DISPOSE_ON_CLOSE);

//            set typenya jadi edit mode
            kelolaUserForm.setType(2);

//            get data
            try {
                java.sql.ResultSet rs;
                java.sql.Connection conn = vKasir.getDBConn();
                java.sql.PreparedStatement ps = conn.prepareStatement("SELECT "
                        + "id, nama, username"
                        + " FROM user"
                        + " WHERE user.id = " + this.selectedRowId + " "
                        + " AND user.deleted_at IS NULL "
                );
                rs = ps.executeQuery();

                while (rs.next()) {
                    models.ItemBarang ib;

                    kelolaUserForm.setTfNama(rs.getString("nama"));
                    kelolaUserForm.setTfUsername(rs.getString("username"));
                    kelolaUserForm.setUserId(rs.getString("id"));
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR load data " + e.getMessage());
            }
        }
    }//GEN-LAST:event_jbEditActionPerformed

    private void jbDeleteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbDeleteMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jbDeleteMouseClicked

    private void jbDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbDeleteActionPerformed
        try {
            // TODO add your handling code here:
            deleteData();
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_jbDeleteActionPerformed

    private void jbTambahMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbTambahMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jbTambahMouseClicked

    private void jbTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbTambahActionPerformed
        // TODO add your handling code here:
        VKelolaUserForm kelolaUserForm = vKasir.getFormUser();
        if (!kelolaUserForm.isVisible()) {
            kelolaUserForm.pack();
            kelolaUserForm.setLocationRelativeTo(null);
            kelolaUserForm.setVisible(true);
            kelolaUserForm.setDefaultCloseOperation(VKelolaBarangForm.DISPOSE_ON_CLOSE);

//            set typenya jadi edit mode
            kelolaUserForm.setType(1);
        }
    }//GEN-LAST:event_jbTambahActionPerformed

//    method delete
    private void deleteData() throws ParseException {
        try {
            int n = JOptionPane.showConfirmDialog(
                            null, "Apakah Anda yakin ingin menghapus user ini?",
                            "Hapus User",
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
                        + " user"
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

    public String getSelectedRowId() {
        return this.selectedRowId;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jSearch;
    private javax.swing.JButton jbDelete;
    private javax.swing.JButton jbEdit;
    private javax.swing.JButton jbTambah;
    private javax.swing.JTable jtBarang;
    // End of variables declaration//GEN-END:variables
}
