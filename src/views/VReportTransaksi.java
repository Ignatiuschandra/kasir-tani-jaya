/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

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
public class VReportTransaksi extends javax.swing.JFrame {

    /**
     * Creates new form VKasir
     */
    VKasir vKasir = VKasir.getInstance();
    private DefaultTableModel model;
    private String selectedRowId;
    private int currMonth;
    private String currYear;
    private final static Logger log = LogManager.getLogger(VReportTransaksi.class);

    public VReportTransaksi() {
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
            java.util.logging.Logger.getLogger(VReportTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VReportTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VReportTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VReportTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        initComponents();

        this.currMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
        this.currYear = String.valueOf(Calendar.getInstance()
                .get(Calendar.YEAR));

        initFilter();

        this.setTitle(vKasir.getAppConfig()
                .getConfig("APP_NAME")+" - Rekap Penjualan");

//        table
        model = new DefaultTableModel();
        jtBarang.setModel(model);
        model.addColumn("TGL TRANSAKSI");
        model.addColumn("PELANGGAN");
        model.addColumn("TOTAL (Rp.)");
        model.addColumn("MASIH HUTANG");
        model.addColumn("ACTION");

        jtBarang.getColumnModel().getColumn(4).setMinWidth(0);
        jtBarang.getColumnModel().getColumn(4).setMaxWidth(0);
        jtBarang.getColumnModel().getColumn(4).setWidth(0);

//        set align
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        jtBarang.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
        jtBarang.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
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
                        jtBarang.getSelectedRow(), 4);
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

//        set event kalau filter diganti
        cbBulan.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
//                    currMonth = cbBulan.getSelectedIndex() + 1;
                    StandardItem si = (StandardItem) cbBulan.getSelectedItem();
                    currMonth = Integer.parseInt(si.getValue());
                    getData("");
                } catch (ParseException ex) {
                    log.error(ex.getStackTrace());
                }
            }
        });

        cbTahun.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    currYear = cbTahun.getSelectedItem().toString();
                    getData("");
                } catch (ParseException ex) {
                    log.error(ex.getStackTrace());
                }
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
                    + "id, pelanggan, total, is_hutang, created_at, uang_diserahkan"
                    + " FROM transaksi"
                    + " WHERE MONTH(created_at) = " + this.currMonth + " "
                    + " AND YEAR(created_at) = '" + this.currYear + "'"
            //                    + " AND deleted_at IS NULL "
            );
            rs = ps.executeQuery();

            models.ItemBarang ib;

            while (rs.next()) {
                Object[] obj = new Object[5];
                String oldstring = rs.getString("created_at");
                Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                        .parse(oldstring);
                String tanggal = new SimpleDateFormat("EEEE, dd-MMM-yyyy HH:mm",
                        vKasir.getAppLocale()).format(date);
                obj[0] = tanggal;
                obj[1] = rs.getString("pelanggan");

                obj[2] = String.format(vKasir.getAppLocale(), "%,.0f",
                        Double.parseDouble(rs.getString("total")));
                String hutang = "TIDAK";
                
                if(rs.getString("is_hutang").equalsIgnoreCase("1")){
                    hutang = "YA ";
                    String hutangJml = String.format(vKasir.getAppLocale(), "%,.0f",
                        Double.parseDouble(rs.getString("total")) -
                        Double.parseDouble(rs.getString("uang_diserahkan")));;
                    
                    hutang += "("+hutangJml+")";
                }
                
                obj[3] = hutang;
                obj[4] = rs.getString("id");

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
        cbBulan = new javax.swing.JComboBox<>();
        cbTahun = new javax.swing.JComboBox<>();
        jbCetak = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel2.setBackground(new java.awt.Color(0, 204, 0));

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

        cbBulan.setMaximumSize(new java.awt.Dimension(49, 22));

        cbTahun.setMaximumSize(new java.awt.Dimension(49, 22));

        jbCetak.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jbCetak.setText("EXCEL");
        jbCetak.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jbCetakMouseClicked(evt);
            }
        });
        jbCetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbCetakActionPerformed(evt);
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
                        .addComponent(cbBulan, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbTahun, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(51, 51, 51)
                        .addComponent(jbCetak, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                    .addComponent(cbBulan, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbTahun, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbCetak))
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

    private void jbCetakMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbCetakMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jbCetakMouseClicked

    private void jbCetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbCetakActionPerformed
        // TODO add your handling code here:
        JFileChooser excelFileChooser = new JFileChooser();
        excelFileChooser.setDialogTitle("Simpan Sebagai");
        FileNameExtensionFilter fnef
                = new FileNameExtensionFilter("EXCEL FILES", "xls", "xlsx");
        
        excelFileChooser.setSelectedFile(
                new File("Report-Warung Satwa-"
                        +String.format("%02d", currMonth)+"-"+currYear));
        excelFileChooser.setFileFilter(fnef);
        int excelChooser = excelFileChooser.showSaveDialog(null);

        FileOutputStream excelFOU = null;
        BufferedOutputStream excelBOS = null;
        XSSFWorkbook excelJTableExporter = null;

        if (excelChooser == JFileChooser.APPROVE_OPTION) {
            excelJTableExporter = new XSSFWorkbook();
            XSSFSheet excelSheet = 
                    excelJTableExporter.createSheet("Laporan Bulanan");

//            Header
            XSSFRow excelRowHeader = excelSheet.createRow(0);
            excelRowHeader.createCell(0).setCellValue("ID");
            excelRowHeader.createCell(1).setCellValue("TANGGAL");
            excelRowHeader.createCell(2).setCellValue("NAMA");
            excelRowHeader.createCell(3).setCellValue("TOTAL TRANSAKSI");
            excelRowHeader.createCell(4).setCellValue("HUTANG");

            for (int i = 1; i < model.getRowCount() + 1; i++) {
                XSSFRow excelRow = excelSheet.createRow(i);
                for (int j = 0; j < model.getColumnCount(); j++) {
                    int imin1 = i-1;
                    excelRow.createCell(0).setCellValue(model.getValueAt(imin1, 4).toString());
                    excelRow.createCell(1).setCellValue(model.getValueAt(imin1, 0).toString());
                    excelRow.createCell(2).setCellValue(model.getValueAt(imin1, 1).toString());
                    excelRow.createCell(3).setCellValue(model.getValueAt(imin1, 2).toString());
                    excelRow.createCell(4).setCellValue(model.getValueAt(imin1, 3).toString());
                }
            }

            try {
                excelFOU = new FileOutputStream(
                        excelFileChooser.getSelectedFile() + ".xlsx");

//                excelBOS = new BufferedOutputStream(excelFOU);
                excelJTableExporter.write(excelFOU);
                JOptionPane.showMessageDialog(null, "Berhasil export Excel!");
            } catch (FileNotFoundException ex) {
                log.error(ex.getStackTrace());
            } catch (IOException ex) {
                log.error(ex.getStackTrace());
            } finally {
                try {
                    if (excelFOU != null) {
                        excelFOU.close();
                    }

//                    if (excelBOS != null) {
//                        excelBOS.close();
//                    }
                    excelJTableExporter.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }//GEN-LAST:event_jbCetakActionPerformed

//    method delete
    private void deleteData() throws ParseException {
        try {
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
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR load data " + e.getMessage());
        }
    }

    public String getSelectedRowId() {
        return this.selectedRowId;
    }

    private void initFilter() {
        cbBulan.removeAllItems();
        cbTahun.removeAllItems();

//        filter bulan
        cbBulan.addItem(new StandardItem("Januari", "1"));
        cbBulan.addItem(new StandardItem("Februari", "2"));
        cbBulan.addItem(new StandardItem("Maret", "3"));
        cbBulan.addItem(new StandardItem("April", "4"));
        cbBulan.addItem(new StandardItem("Mei", "5"));
        cbBulan.addItem(new StandardItem("Juni", "6"));
        cbBulan.addItem(new StandardItem("Juli", "7"));
        cbBulan.addItem(new StandardItem("Agustus", "8"));
        cbBulan.addItem(new StandardItem("September", "9"));
        cbBulan.addItem(new StandardItem("Oktober", "10"));
        cbBulan.addItem(new StandardItem("November", "11"));
        cbBulan.addItem(new StandardItem("Desember", "12"));

        //        filter tahun
        for (int i = 2020; i <= Calendar.getInstance().get(Calendar.YEAR); i++) {
            cbTahun.addItem(
                    new StandardItem(
                            String.valueOf(i),
                            String.valueOf(i)
                    )
            );
        }

//        set selected 
        cbBulan.setSelectedIndex(this.currMonth - 1);
        setCbTahun(new StandardItem(this.currYear, this.currYear));
    }

    public void setCbTahun(StandardItem si) {
        int cbSize = cbTahun.getItemCount();

        for (int i = 0; i < cbSize; i++) {
            models.StandardItem isi = cbTahun.getItemAt(i);

//            bandingkan isinya
            if (isi.getValue().equalsIgnoreCase(si.getValue())) {
//                System.out.println("bener");
                cbTahun.setSelectedIndex(i);
            }
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<models.StandardItem> cbBulan;
    private javax.swing.JComboBox<models.StandardItem> cbTahun;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jSearch;
    private javax.swing.JButton jbCetak;
    private javax.swing.JTable jtBarang;
    // End of variables declaration//GEN-END:variables
}
