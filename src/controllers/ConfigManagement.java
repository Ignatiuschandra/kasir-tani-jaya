/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import javax.swing.JOptionPane;

/**
 *
 * @author ignas
 */
public class ConfigManagement {
   public static Properties prop = new Properties();
   
   public String getConfig(String cfgName){
       String value = "";
       
       try {
           prop.load(new FileInputStream("src/config/config.iccm"));
       } catch (FileNotFoundException ex) {
           JOptionPane.showMessageDialog(null, "File konfigurasi tidak ditemukan ~");
           System.exit(0);
       } catch (IOException ex) {
           JOptionPane.showMessageDialog(null, "Tidak bisa membuka file konfigurasi ~");
           System.exit(0);
       }
       value = prop.getProperty(cfgName);
       return value;
   }
}
