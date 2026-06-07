/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package config;
import java.sql.Connection;
import javax.swing.JOptionPane;

/**
 *
 * @author chand
 */
public class TestConn {
    public static void main(String[] args){
        Connection conn = Koneksi.getConnection();
        if (conn != null) {
           JOptionPane.showMessageDialog(null, "Koneksi Berhasil");
        }else {
           JOptionPane.showMessageDialog(null, "Koneksi gagal");
        }
    }
    
}
