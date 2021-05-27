package Control;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Control_Database {

    public static Connection getConnection() {
        String host = "localhost",
                port = "1521",
                db = "xe",
                usr = "Benedictus",
                pwd = "155314099";
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Maaf driver class tidak ditemukan\nError : " + ex.getMessage(), "Error", 0);
        }

        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:oracle:thin:@" + host + ":" + port + ":" + db, usr, pwd);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Maaf, koneksi tidak berhasil\nError : " + ex.getMessage(), "Error", 0);
        }
        return conn;
    }

    public static void main(String[] args) {
        getConnection();
    }
}
