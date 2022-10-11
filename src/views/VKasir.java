/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import controllers.AuthEncryptor;
import controllers.ConfigManagement;
import controllers.FocusTextField;
import controllers.MyIntFilter;
import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;
import javax.swing.text.PlainDocument;

/**
 *
 * @author Chandra
 */
public class VKasir extends javax.swing.JFrame {

    /**
     * Creates new form VKasir
     */
    static VKelolaBarang vKelolaBarang;
    static VKelolaBarangForm vKelolaBarangForm;
    static VKelolaBarangForm vKelolaBarangFormTambah;
    static VKelolaHutangForm vKelolaHutangForm;
    static VReportTransaksi vReportTransaksi;
    static VKelolaHutang vKelolaHutang;
    static VLoginForm vLoginForm;
    static VKelolaUser vKelolaUser;
    static VKelolaUserForm vKelolaUserForm;
    static VKelolaPengeluaran vKelolaPengeluaran;
    static VKelolaPengeluaranForm vKelolaPengeluaranForm;
    static VGantiPassword vGantiPassword;
    static Connection conn;
    static Statement stm;
    private Map<String, String> map;
    private String searchId;
    private DefaultTableModel dtmModel;
    private static int totalHarga = 0;
    private static int kembalian = 0;
    private static int instantiationCounter = 0;
    private static Locale locale = new Locale("id", "ID");
    private static volatile VKasir instance;
    private java.net.URL urlImage = ClassLoader
            .getSystemResource("main/resources/images/logo.png");
    private static ConfigManagement conf = new ConfigManagement();
    private static AuthEncryptor ae = new AuthEncryptor();

    private VKasir() {
        initComponents();
        instantiationCounter++; //singleton counter
        System.out.println("CREATE" + instantiationCounter);

        jlAppName.setText(conf.getConfig("APP_NAME"));
        this.setTitle(conf.getConfig("APP_NAME") + " - Kasir");

//        set color
        jPanel2.setBackground(
                Color.decode(conf.getConfig("APP_MAIN_COLOR")));
        lTotalHarga.setForeground(
                Color.decode(conf.getConfig("APP_SECONDARY_COLOR")));

        Toolkit kit = Toolkit.getDefaultToolkit();
        Image img = kit.createImage(urlImage);
        setIconImage(img);

        lTotalHarga.setText("Rp. " + totalHarga);

        //init combobox
//        cbSearch = new JComboBox(new Object[]{"Ester", "Jordi", "Sergi"});
        cbSearch.setEditable(true);
        cbSearch.getEditor().getEditorComponent().addKeyListener(new KeyListener() {
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
                        String searchText = (String) cbSearch.getEditor().getItem();
                        searchText = searchText.trim().toLowerCase();

                        System.out.println(searchText);
                        updateSearch(searchText);
                    } catch (Exception ex) {
                        System.out.println("Ex log " + ex.getMessage());
                    }
                }
            }

        });

        cbSearch.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                System.out.println("state " + e.getStateChange());
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    Object item = cbSearch.getSelectedItem();
                    try {
                        String value = ((models.ItemBarang) item).getId();
                        searchId = value;
                    } catch (Exception ex) {
                        System.out.println("Log ex " + ex.getMessage());
                    }
                }
            }

        });

//        Table
//        dtmModel = new DefaultTableModel() {
//            @Override
//            public boolean isCellEditable(int row, int column) {
//                switch (column) {
//                    case 4:
//                        return true;
//                    case 6:
//                        return true;
//                    default:
//                        return false;
//                }
//            }
//        };
        dtmModel = (DefaultTableModel) tKasir.getModel();

//        tKasir.setModel(dtmModel);
        dtmModel.addColumn("KODE");
        dtmModel.addColumn("NAMA BARANG");
        dtmModel.addColumn("SATUAN");
        dtmModel.addColumn("HARGA");
        dtmModel.addColumn("QTY");
        dtmModel.addColumn("TOTAL HARGA");
        dtmModel.addColumn("ACTION");
        dtmModel.addColumn("ID");
        dtmModel.addColumn("HARGA_BELI");
        dtmModel.addColumn("HARGA_JUAL");

        tKasir.getColumnModel().getColumn(7).setMinWidth(0);
        tKasir.getColumnModel().getColumn(7).setMaxWidth(0);
        tKasir.getColumnModel().getColumn(7).setWidth(0);

        tKasir.getColumnModel().getColumn(8).setMinWidth(0);
        tKasir.getColumnModel().getColumn(8).setMaxWidth(0);
        tKasir.getColumnModel().getColumn(8).setWidth(0);

        tKasir.getColumnModel().getColumn(9).setMinWidth(0);
        tKasir.getColumnModel().getColumn(9).setMaxWidth(0);
        tKasir.getColumnModel().getColumn(9).setWidth(0);

        dtmModel.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent tme) {
                if (tme.getType() == TableModelEvent.UPDATE
                        && tme.getColumn() == 4) { //QTY updated
                    System.out.println("");
                    System.out.println("Cell " + tme.getFirstRow() + ", "
                            + tme.getColumn() + " changed. The new value: "
                            + dtmModel.getValueAt(tme.getFirstRow(),
                                    tme.getColumn()));

                    updateTotalHarga();
                }
            }

        });

//        set default kembalian ke 0
        tfKembalian.setText(Integer.toString(kembalian));

//        set restriction number only
        PlainDocument docTfDiserahkan = (PlainDocument) tfDiserahkan.getDocument();
        docTfDiserahkan.setDocumentFilter(new MyIntFilter());

        PlainDocument docTfKembalian = (PlainDocument) tfKembalian.getDocument();
        docTfKembalian.setDocumentFilter(new MyIntFilter());

//        select all when focus
        tfDiserahkan.addFocusListener(new FocusTextField(tfDiserahkan));
        tfDiserahkan.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent event) {

            }

            @Override
            public void keyReleased(KeyEvent event) {
                int diserahkan = Integer.parseInt(tfDiserahkan.getText());
                int kembalian = diserahkan - totalHarga;
                tfKembalian.setText(Integer.toString(kembalian));
            }

            @Override
            public void keyTyped(KeyEvent event) {

            }
        });
    }

    public static VKasir getInstance() {
        if (instance == null) {
            instance = new VKasir();
        }

        return instance;
    }

    public int getInstantiationCounter() {
        return instantiationCounter;
    }

    public VKelolaBarang getKelolaBarang() {
        return vKelolaBarang;
    }

    public VKelolaBarangForm getKelolaBarangForm() {
        return vKelolaBarangForm;
    }

    public VKelolaBarangForm getFormTambah() {
        return vKelolaBarangFormTambah;
    }

    public VKelolaHutang getKelolaHutang() {
        return vKelolaHutang;
    }

    public VKelolaHutangForm getFormHutang() {
        return vKelolaHutangForm;
    }
    
    public VKelolaUser getKelolaUser() {
        return vKelolaUser;
    }
    
    public VKelolaUserForm getFormUser() {
        return vKelolaUserForm;
    }
    
    public VKelolaPengeluaran getKelolaPengeluaran() {
        return vKelolaPengeluaran;
    }
    
    public VKelolaPengeluaranForm getKelolaPengeluaranForm() {
        return vKelolaPengeluaranForm;
    }

    public Connection getDBConn() {
        return conn;
    }

    public java.net.URL getLogo() {
        return urlImage;
    }

    public void removeRow(int currentRow) {
        System.out.println(dtmModel.getRowCount());
        dtmModel.removeRow(currentRow);
        this.updateTotalHarga();
    }

    public void setTotalHarga(int total) {
        this.totalHarga = total;
        lTotalHarga.setText("Rp. " + Integer.toString(this.totalHarga));
        System.out.println("Total new : " + lTotalHarga.getText());
    }

    public Locale getAppLocale() {
        return this.locale;
    }

    public ConfigManagement getAppConfig() {
        return this.conf;
    }
    
    public AuthEncryptor getAuthEncryptor() {
        return this.ae;
    }

    public void updateSearch(String keyword) {
        cbSearch.removeAllItems();
        try {
            java.sql.ResultSet rs;
            java.sql.PreparedStatement ps
                    = conn.prepareStatement("SELECT barang.id, nama, satuan, "
                            + "barang.harga_beli, barang.harga_jual "
                            + "FROM barang "
                            + "JOIN satuan ON satuan.id = barang.satuan_id "
                            + "WHERE nama LIKE '%" + keyword + "%' "
                            + "OR kode LIKE '%" + keyword + "%'");
            rs = ps.executeQuery();

            models.ItemBarang ib;

            while (rs.next()) {
                ib = new models.ItemBarang(rs.getString(1), rs.getString(2) + " | " + rs.getString(3));
                cbSearch.addItem(ib);
            }
            cbSearch.showPopup();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR load barang " + e.getMessage());
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

        jPanel1 = new javax.swing.JPanel();
        jlAppName = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jbKelolaUser = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jbGantiPassword = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        bTambah = new javax.swing.JButton();
        cbSearch = new javax.swing.JComboBox<>();
        jPanel6 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        lTotalHarga = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        tfDiserahkan = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        tfKembalian = new javax.swing.JTextField();
        bBayar = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tKasir = new javax.swing.JTable();
        tfPelanggan = new javax.swing.JTextField();
        tfPotongan = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        cbHutang = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));

        jlAppName.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        jlAppName.setForeground(new java.awt.Color(255, 255, 255));
        jlAppName.setText("APP NAME");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlAppName)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlAppName)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

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

        jButton3.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jButton3.setText("KELOLA BARANG");
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton3MouseClicked(evt);
            }
        });
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jButton4.setText("MASTER TRANSAKSI");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jButton5.setText("KELOLA HUTANG");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jButton6.setText("LOG OUT");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jbKelolaUser.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jbKelolaUser.setText("KELOLA USER");
        jbKelolaUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbKelolaUserActionPerformed(evt);
            }
        });

        jButton7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton7.setText("KELOLA PENGELUARAN");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jbGantiPassword.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jbGantiPassword.setText("GANTI PASSWORD");
        jbGantiPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbGantiPasswordActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jbKelolaUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jbGantiPassword, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbKelolaUser, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jbGantiPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("BARANG");

        bTambah.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        bTambah.setText("TAMBAH");
        bTambah.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bTambahMouseClicked(evt);
            }
        });

        cbSearch.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(bTambah, javax.swing.GroupLayout.DEFAULT_SIZE, 484, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(cbSearch, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addComponent(bTambah)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setText("TOTAL BELANJA");

        lTotalHarga.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        lTotalHarga.setForeground(new java.awt.Color(255, 255, 255));
        lTotalHarga.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lTotalHarga.setText("Rp 0");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lTotalHarga, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addGap(0, 193, Short.MAX_VALUE)
                        .addComponent(jLabel3)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lTotalHarga)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("UANG DISERAHKAN (Rp.)");

        tfDiserahkan.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        tfDiserahkan.setText("0");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("KEMBALIAN (Rp.)");

        tfKembalian.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        tfKembalian.setText("0");

        bBayar.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        bBayar.setText("BAYAR");
        bBayar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bBayarMouseClicked(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("NAMA PELANGGAN (Opt.)");

        tKasir.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tKasir.setMaximumSize(new java.awt.Dimension(0, 340));
        tKasir.setMinimumSize(new java.awt.Dimension(0, 340));
        jScrollPane1.setViewportView(tKasir);

        tfPotongan.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        tfPotongan.setText("0");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setText("POTONGAN (Rp.)");

        cbHutang.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        cbHutang.setText("Hutang");
        cbHutang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbHutangActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                    .addComponent(cbHutang)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel5)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(tfDiserahkan, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                    .addGap(0, 0, Short.MAX_VALUE)
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                            .addComponent(jLabel8)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(tfPotongan, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                            .addComponent(jLabel6)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(tfKembalian, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                            .addComponent(jLabel7)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(tfPelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(bBayar, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(bBayar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel5)
                                    .addComponent(tfDiserahkan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cbHutang))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(tfPotongan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel8))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(tfKembalian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(tfPelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7))
                                .addGap(5, 5, 5)))))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void bBayarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bBayarMouseClicked
        // TODO add your handling code here:
        try {
            TableModel t = tKasir.getModel();

//            buat ID untuk transaksi
            Calendar calendar = Calendar.getInstance();
            String idTrx = "TRX" + calendar.getTimeInMillis();

            java.sql.PreparedStatement ps
                    = conn.prepareStatement("INSERT INTO transaksi "
                            + "(id, pelanggan, uang_diserahkan, "
                            + "total, is_hutang, potongan) "
                            + "VALUES ('" + idTrx
                            + "', '" + tfPelanggan.getText()
                            + "', " + tfDiserahkan.getText()
                            + ", " + totalHarga
                            + ", " + cbHutang.isSelected()
                            + ", " + tfPotongan.getText() + ")");

            ps.execute();

            for (int i = 0; i < tKasir.getRowCount(); i++) {
                java.sql.PreparedStatement psDetail
                        = conn.prepareStatement("INSERT INTO transaksi_detail "
                                + "(transaksi_id, barang_id, qty, "
                                + "harga_beli, harga_jual) "
                                + "VALUES ('" + idTrx
                                + "', " + t.getValueAt(i, 7)
                                + ", " + t.getValueAt(i, 4)
                                + ", " + t.getValueAt(i, 8)
                                + ", " + t.getValueAt(i, 9) + ")");

                psDetail.execute();
            }

            JOptionPane.showMessageDialog(null, "Transaksi Selesai");
            //menghapus isi table tblGaji
            dtmModel.getDataVector().removeAllElements();
            dtmModel.fireTableDataChanged();
            tfPelanggan.setText("");
            totalHarga = 0;
            tfDiserahkan.setText("0");
            tfKembalian.setText("0");
            tfPotongan.setText("0");
            cbHutang.setSelected(false);
            lTotalHarga.setText("Rp. 0");
            cbSearch.getEditor().setItem("");
            cbSearch.removeAllItems();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR log " + e.getMessage());
        }
    }//GEN-LAST:event_bBayarMouseClicked

    private void bTambahMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bTambahMouseClicked
        // TODO add your handling code here:
        try {
            java.sql.ResultSet rs;
            java.sql.PreparedStatement ps
                    = conn.prepareStatement("SELECT barang.id, kode, "
                            + "nama, satuan, harga_beli, harga_jual "
                            + "FROM barang "
                            + "JOIN satuan ON satuan.id = barang.satuan_id "
                            + "WHERE barang.id = " + searchId);
            rs = ps.executeQuery();

            models.ItemBarang ib;

            while (rs.next()) {
                Object[] obj = new Object[10];
                obj[0] = rs.getString("kode");
                obj[1] = rs.getString("nama");
                obj[2] = rs.getString("satuan");
                obj[3] = rs.getInt("harga_jual");
                obj[4] = 1;
                obj[5] = (Integer) obj[3] * (Integer) obj[4];
                obj[6] = "X";
                obj[7] = rs.getString("id");
                obj[8] = rs.getInt("harga_beli");
                obj[9] = rs.getInt("harga_jual");

//                totalHarga += (Integer) obj[5];
//                System.out.println(totalHarga);
//                lTotalHarga.setText("Rp. " + totalHarga);
                System.out.println("hoho");
                dtmModel.addRow(obj);
            }

            System.out.println(dtmModel.getRowCount());

            updateTotalHarga();

            tKasir.getColumn("ACTION").setCellRenderer(new ButtonRenderer());
            tKasir.getColumn("ACTION").setCellEditor(new ButtonEditor(new JCheckBox()));

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR load barang " + e.getMessage());
        }
    }//GEN-LAST:event_bTambahMouseClicked

    private void jButton3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseClicked
        // TODO add your handling code here:
        if (!vKelolaBarang.isVisible()) {
            vKelolaBarang.pack();
            vKelolaBarang.setLocationRelativeTo(null);
            vKelolaBarang.setVisible(true);
            vKelolaBarang.setDefaultCloseOperation(VKelolaBarang.DISPOSE_ON_CLOSE);
        } else {
            vKelolaBarang.setVisible(true);
        }
        vKelolaBarang.getData("");
    }//GEN-LAST:event_jButton3MouseClicked

    private void cbHutangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbHutangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbHutangActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        if (!vReportTransaksi.isVisible()) {
            vReportTransaksi.pack();
            vReportTransaksi.setLocationRelativeTo(null);
            vReportTransaksi.setVisible(true);
            vReportTransaksi.setDefaultCloseOperation(VReportTransaksi.DISPOSE_ON_CLOSE);
        } else {
            vReportTransaksi.setVisible(true);
        }
        try {
            vReportTransaksi.getData("");
        } catch (ParseException ex) {
            Logger.getLogger(VKasir.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        if (!vKelolaHutang.isVisible()) {
            vKelolaHutang.pack();
            vKelolaHutang.setLocationRelativeTo(null);
            vKelolaHutang.setVisible(true);
            vKelolaHutang.setDefaultCloseOperation(VReportTransaksi.DISPOSE_ON_CLOSE);
        } else {
            vKelolaHutang.setVisible(true);
        }
        try {
            vKelolaHutang.getData("");
        } catch (ParseException ex) {
            Logger.getLogger(VKasir.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
//        hide all
        this.setVisible(false);
        vKelolaBarang.setVisible(false);
        vKelolaBarangForm.setVisible(false);
        vKelolaBarangFormTambah.setVisible(false);
        vReportTransaksi.setVisible(false);
        vKelolaHutang.setVisible(false);
        vKelolaHutangForm.setVisible(false);
        vKelolaUser.setVisible(false);
        vKelolaUserForm.setVisible(false);
        vKelolaPengeluaran.setVisible(false);
        vKelolaPengeluaranForm.setVisible(false);
        vGantiPassword.setVisible(false);
        
        vLoginForm.resetForm();
        vLoginForm.setVisible(true);
        ae.setIdLogin("");
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jbKelolaUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbKelolaUserActionPerformed
        // TODO add your handling code here:
        if (!vKelolaUser.isVisible()) {
            vKelolaUser.pack();
            vKelolaUser.setLocationRelativeTo(null);
            vKelolaUser.setVisible(true);
            vKelolaUser.setDefaultCloseOperation(VReportTransaksi.DISPOSE_ON_CLOSE);
        } else {
            vKelolaUser.setVisible(true);
        }
        try {
            vKelolaUser.getData("");
        } catch (ParseException ex) {
            Logger.getLogger(VKasir.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jbKelolaUserActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        if (!vKelolaPengeluaran.isVisible()) {
            vKelolaPengeluaran.pack();
            vKelolaPengeluaran.setLocationRelativeTo(null);
            vKelolaPengeluaran.setVisible(true);
            vKelolaPengeluaran.setDefaultCloseOperation(VReportTransaksi.DISPOSE_ON_CLOSE);
        } else {
            vKelolaPengeluaran.setVisible(true);
        }
        try {
            vKelolaPengeluaran.getData("");
        } catch (ParseException ex) {
            Logger.getLogger(VKasir.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jbGantiPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbGantiPasswordActionPerformed
        // TODO add your handling code here:
        if (!vGantiPassword.isVisible()) {
            vGantiPassword.pack();
            vGantiPassword.setLocationRelativeTo(null);
            vGantiPassword.setVisible(true);
            vGantiPassword.setDefaultCloseOperation(VReportTransaksi.DISPOSE_ON_CLOSE);
        } else {
            vGantiPassword.setVisible(true);
        }
    }//GEN-LAST:event_jbGantiPasswordActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VKasir.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VKasir.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VKasir.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VKasir.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        // Connect DB
        try {
            String url = "jdbc:mysql://localhost:3306/" + conf.getConfig("DB_NAME");
            String user = conf.getConfig("DB_USN");
            String pass = conf.getConfig("DB_PWD");
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, pass);
            stm = conn.createStatement();
            System.out.println("Koneksi berhasil;");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Koneksi Gagal " + e.getMessage());
            System.exit(0);
        }

        VKasir vKasir = VKasir.getInstance();
        vLoginForm = new VLoginForm();
        vKelolaBarang = new VKelolaBarang();
        vKelolaBarangForm = new VKelolaBarangForm(1);
        vKelolaBarangFormTambah = new VKelolaBarangForm(1);
        vReportTransaksi = new VReportTransaksi();
        vKelolaHutang = new VKelolaHutang();
        vKelolaHutangForm = new VKelolaHutangForm();
        vKelolaUser = new VKelolaUser();
        vKelolaUserForm = new VKelolaUserForm(1);
        vKelolaPengeluaran = new VKelolaPengeluaran();
        vKelolaPengeluaranForm = new VKelolaPengeluaranForm(1);
        vGantiPassword = new VGantiPassword();
        
//        System.out.println("how many : "+vKasir.getInstantiationCounter());

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
//                new VKasir().setVisible(true);
//                vKasir.setVisible(true);
                vLoginForm.pack();
                vLoginForm.setLocationRelativeTo(null);
                vLoginForm.setVisible(true);
            }
        });
    }

    public void updateTotalHarga() {
        int size = dtmModel.getRowCount();
        System.out.println("TH : " + size);
        int total = 0;
        for (int i = 0; i < size; i++) {
            int subtotal = Integer.parseInt(dtmModel.getValueAt(i, 3).toString())
                    * Integer.parseInt(dtmModel.getValueAt(i, 4).toString());
            System.out.println(subtotal);
            dtmModel.setValueAt(subtotal, i, 5);
            total += subtotal;
        }

        totalHarga = total;
        lTotalHarga.setText("Rp. " + Integer.toString(totalHarga));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bBayar;
    private javax.swing.JButton bTambah;
    private javax.swing.JCheckBox cbHutang;
    private javax.swing.JComboBox<models.ItemBarang> cbSearch;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbGantiPassword;
    private javax.swing.JButton jbKelolaUser;
    private javax.swing.JLabel jlAppName;
    private javax.swing.JLabel lTotalHarga;
    private javax.swing.JTable tKasir;
    private javax.swing.JTextField tfDiserahkan;
    private javax.swing.JTextField tfKembalian;
    private javax.swing.JTextField tfPelanggan;
    private javax.swing.JTextField tfPotongan;
    // End of variables declaration//GEN-END:variables
}

class ButtonRenderer extends JButton implements TableCellRenderer {

    public ButtonRenderer() {
        setOpaque(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        if (isSelected) {
            setForeground(table.getSelectionForeground());
            setBackground(table.getSelectionBackground());
        } else {
            setForeground(table.getForeground());
            setBackground(UIManager.getColor("Button.background"));
        }
        setText((value == null) ? "" : value.toString());
        return this;
    }
}

class ButtonEditor extends DefaultCellEditor {

    protected JButton button;
    private String label;
    private boolean isPushed;
    private int currentRow;
    private DefaultTableModel beDTM;

    public ButtonEditor(JCheckBox checkBox) {
        super(checkBox);
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped();
            }
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        if (isSelected) {
            button.setForeground(table.getSelectionForeground());
            button.setBackground(table.getSelectionBackground());
        } else {
            button.setForeground(table.getForeground());
            button.setBackground(table.getBackground());
        }
        label = (value == null) ? "" : value.toString();
        button.setText(label);
        isPushed = true;
        currentRow = row;
//        beDTM = (DefaultTableModel) table.getModel();
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        if (isPushed) {
//            beDTM.removeRow(currentRow);

            VKasir kasirClass = VKasir.getInstance();
            kasirClass.removeRow(currentRow);
        }
        isPushed = false;
        return label;
    }

    @Override
    public boolean stopCellEditing() {
        isPushed = false;
        return super.stopCellEditing();
    }
}
