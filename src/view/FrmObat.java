package view;

import config.Koneksi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class FrmObat extends javax.swing.JFrame {
    
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(FrmObat.class.getName());
    public FrmObat() {
       initComponents();
        loadKategori();
        loadTable();
        resetForm();
        txtIdObat.setEditable(false);
    }

    @SuppressWarnings("unchecked")
    
    private boolean validasiInput() {

      if (txtNamaObat.getText().trim().isEmpty()) {

        JOptionPane.showMessageDialog(this,
                "Nama obat wajib diisi!");

        txtNamaObat.requestFocus();
        return false;
    }

    if (txtHarga.getText().trim().isEmpty()) {

        JOptionPane.showMessageDialog(this,
                "Harga wajib diisi!");

        txtHarga.requestFocus();
        return false;
    }

    if (txtStok.getText().trim().isEmpty()) {

        JOptionPane.showMessageDialog(this,
                "Stok wajib diisi!");

        txtStok.requestFocus();
        return false;
    }

    try {

        double harga =
                Double.parseDouble(txtHarga.getText());

        if (harga <= 0) {

            JOptionPane.showMessageDialog(this,
                    "Harga harus lebih dari 0");

            return false;
        }

    } catch (NumberFormatException e) {

        JOptionPane.showMessageDialog(this,
                "Harga harus berupa angka!");

        txtHarga.requestFocus();
        return false;
    }

    try {

        int stok =
                Integer.parseInt(txtStok.getText());

        if (stok < 0) {

            JOptionPane.showMessageDialog(this,
                    "Stok tidak boleh negatif");

            return false;
        }

    } catch (NumberFormatException e) {

        JOptionPane.showMessageDialog(this,
                "Stok harus berupa angka!");

        txtStok.requestFocus();
        return false;
    }

    return true;
}
    private void resetForm() {

  txtIdObat.setText("");
    txtNamaObat.setText("");

    cmbKategori.setSelectedIndex(0);

    txtHarga.setText("");
    txtStok.setText("");
    txtCari.setText("");

    txtNamaObat.requestFocus();
    }
    private void loadKategori() {

    cmbKategori.removeAllItems();

    cmbKategori.addItem("Tablet");
    cmbKategori.addItem("Kapsul");
    cmbKategori.addItem("Sirup");
    cmbKategori.addItem("Vitamin");
    cmbKategori.addItem("Salep");
    cmbKategori.addItem("Tetes Mata");

}
     
    private void loadTable() {

    
    DefaultTableModel model = new DefaultTableModel(
        new Object[][]{},
        new String[]{
            "ID",
            "Nama Obat",
            "Kategori",
            "Harga",
            "Stok"
        }
    ) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    try {

        String sql = "SELECT * FROM obat";

        Connection conn = Koneksi.getConnection();

        Statement st = conn.createStatement();

        ResultSet rs = st.executeQuery(sql);

        while (rs.next()) {

            model.addRow(new Object[]{
                rs.getInt("id_obat"),
                rs.getString("nama_obat"),
                rs.getString("kategori"),
                String.format("Rp %,d", rs.getInt("harga")),
                rs.getInt("stok")
            });

        }

        tblObat.setModel(model);
        tblObat.setRowHeight(25);

    } catch (Exception e) {

        JOptionPane.showMessageDialog(this,
                "Gagal memuat data : " + e.getMessage());

    }
}
    
    private void cariObat() {

        DefaultTableModel model = new DefaultTableModel();

    model.addColumn("ID");
    model.addColumn("Nama Obat");
    model.addColumn("Kategori");
    model.addColumn("Harga");
    model.addColumn("Stok");

    try {

        String sql = """
            SELECT *
            FROM obat
            WHERE nama_obat LIKE ?
               OR kategori LIKE ?
        """;

        Connection conn = Koneksi.getConnection();

        PreparedStatement pst =
                conn.prepareStatement(sql);

        String keyword =
                "%" + txtCari.getText().trim() + "%";

        pst.setString(1, keyword);
        pst.setString(2, keyword);

        ResultSet rs = pst.executeQuery();

        while (rs.next()) {

            model.addRow(new Object[]{
                rs.getInt("id_obat"),
                rs.getString("nama_obat"),
                rs.getString("kategori"),
               String.format("Rp %,d", rs.getInt("harga")),
                rs.getInt("stok")
            });

        }

        tblObat.setModel(model);

    } catch (Exception e) {

        JOptionPane.showMessageDialog(this,
                e.getMessage());

    }
}
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFrame1 = new javax.swing.JFrame();
        canvas1 = new java.awt.Canvas();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtIdObat = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtNamaObat = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtHarga = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtStok = new javax.swing.JTextField();
        btnSimpan = new javax.swing.JButton();
        btnUbah = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        txtCari = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblObat = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        btnReset = new javax.swing.JButton();
        cmbKategori = new javax.swing.JComboBox<>();

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Data Obat Apotek");

        jLabel2.setText("ID Obat ");

        jLabel3.setText("Nama Obat");

        txtNamaObat.addActionListener(this::txtNamaObatActionPerformed);

        jLabel4.setText("Kategori");

        jLabel5.setText("Harga");

        txtHarga.addActionListener(this::txtHargaActionPerformed);
        txtHarga.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtHargaKeyTyped(evt);
            }
        });

        jLabel6.setText("Stok");

        txtStok.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtStokKeyTyped(evt);
            }
        });

        btnSimpan.setText("Simpan");
        btnSimpan.addActionListener(this::btnSimpanActionPerformed);

        btnUbah.setText("Edit");
        btnUbah.addActionListener(this::btnUbahActionPerformed);

        btnHapus.setText("Hapus");
        btnHapus.addActionListener(this::btnHapusActionPerformed);

        jLabel7.setText("Cari Obat");

        txtCari.addActionListener(this::txtCariActionPerformed);
        txtCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCariKeyReleased(evt);
            }
        });

        tblObat.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Nama Obat", "Kategori", "Harga", "Stok"
            }
        ));
        tblObat.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                tblObatAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        tblObat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblObatMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblObat);

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel8.setText("Data Obat");

        btnReset.setText("Reset");
        btnReset.addActionListener(this::btnResetActionPerformed);

        cmbKategori.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbKategori.addActionListener(this::cmbKategoriActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(187, 187, 187)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(187, 187, 187)
                                .addComponent(jLabel8))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel7)
                                        .addGap(85, 85, 85)
                                        .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel2)
                                            .addComponent(jLabel3)
                                            .addComponent(jLabel4)
                                            .addComponent(jLabel5)
                                            .addComponent(jLabel6))
                                        .addGap(71, 71, 71)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtIdObat)
                                            .addComponent(txtNamaObat)
                                            .addComponent(txtHarga)
                                            .addComponent(txtStok)
                                            .addComponent(cmbKategori, 0, 320, Short.MAX_VALUE))))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(btnSimpan)
                                    .addGap(29, 29, 29)
                                    .addComponent(btnUbah)
                                    .addGap(26, 26, 26)
                                    .addComponent(btnHapus)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnReset))))))
                .addContainerGap(62, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel1)
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtIdObat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtNamaObat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(cmbKategori, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(txtHarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(txtStok, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnUbah, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnReset, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void txtHargaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtHargaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHargaActionPerformed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
    if (!validasiInput()) {
        return;
    }

    try {

        String sql = "INSERT INTO obat "
                + "(nama_obat, kategori, harga, stok) "
                + "VALUES (?, ?, ?, ?)";

        Connection conn = Koneksi.getConnection();

        PreparedStatement pst =
                conn.prepareStatement(sql);

        pst.setString(1,
                txtNamaObat.getText().trim());

        pst.setString(2,
                cmbKategori.getSelectedItem().toString());

        pst.setDouble(3,
                Double.parseDouble(txtHarga.getText().trim()));

        pst.setInt(4,
                Integer.parseInt(txtStok.getText().trim()));

        pst.executeUpdate();

        JOptionPane.showMessageDialog(this,
                "Data obat berhasil disimpan");

        loadTable();
        resetForm();

    } catch (Exception e) {

        JOptionPane.showMessageDialog(this,
                "Gagal menyimpan data : "
                + e.getMessage());

    }
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void btnUbahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUbahActionPerformed
         
     if (txtIdObat.getText().trim().isEmpty()) {

        JOptionPane.showMessageDialog(this,
                "Pilih data obat terlebih dahulu!");

        return;
    }

    if (!validasiInput()) {
        return;
    }

    try {

        int konfirmasi =
                JOptionPane.showConfirmDialog(
                        this,
                        "Yakin ingin mengubah data obat?",
                        "Konfirmasi",
                        JOptionPane.YES_NO_OPTION
                );

        if (konfirmasi != JOptionPane.YES_OPTION) {
            return;
        }

        String sql = "UPDATE obat "
                + "SET nama_obat=?, "
                + "kategori=?, "
                + "harga=?, "
                + "stok=? "
                + "WHERE id_obat=?";

        Connection conn = Koneksi.getConnection();

        PreparedStatement pst =
                conn.prepareStatement(sql);

        pst.setString(1,
                txtNamaObat.getText().trim());

        pst.setString(2,
                cmbKategori.getSelectedItem().toString());

        pst.setDouble(3,
                Double.parseDouble(txtHarga.getText().trim()));

        pst.setInt(4,
                Integer.parseInt(txtStok.getText().trim()));

        pst.setInt(5,
                Integer.parseInt(txtIdObat.getText()));

        int berhasil = pst.executeUpdate();

        if (berhasil > 0) {

            JOptionPane.showMessageDialog(this,
                    "Data obat berhasil diubah");

            loadTable();
            resetForm();

        } else {

            JOptionPane.showMessageDialog(this,
                    "Data tidak ditemukan");

        }

    } catch (Exception e) {

        JOptionPane.showMessageDialog(this,
                "Gagal mengubah data : "
                + e.getMessage());

    }
    }//GEN-LAST:event_btnUbahActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
         if (txtIdObat.getText().trim().isEmpty()) {

        JOptionPane.showMessageDialog(this,
                "Pilih data yang akan dihapus!");
        return;
    }

    int konfirmasi = JOptionPane.showConfirmDialog(
            this,
            "Yakin ingin menghapus data obat?",
            "Konfirmasi Hapus",
            JOptionPane.YES_NO_OPTION
    );

    if (konfirmasi != JOptionPane.YES_OPTION) {
        return;
    }

    try {

        String sql = "DELETE FROM obat WHERE id_obat=?";

        Connection conn = Koneksi.getConnection();

        PreparedStatement pst = conn.prepareStatement(sql);

        pst.setInt(1,
                Integer.parseInt(txtIdObat.getText()));

        pst.executeUpdate();

        JOptionPane.showMessageDialog(this,
                "Data obat berhasil dihapus");

        loadTable();
        resetForm();

    } catch (Exception e) {

        JOptionPane.showMessageDialog(this,
                "Gagal menghapus data : " + e.getMessage());

    }
    }//GEN-LAST:event_btnHapusActionPerformed

    private void tblObatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblObatMouseClicked
        int row = tblObat.getSelectedRow();

    txtIdObat.setText(
            tblObat.getValueAt(row, 0).toString());

    txtNamaObat.setText(
            tblObat.getValueAt(row, 1).toString());

    cmbKategori.setSelectedItem(
            tblObat.getValueAt(row, 2).toString());

    String harga = tblObat.getValueAt(row, 3)
            .toString()
            .replace("Rp", "")
            .replace(",", "")
            .trim();

    txtHarga.setText(harga);

    txtStok.setText(
            tblObat.getValueAt(row, 4).toString());
    }//GEN-LAST:event_tblObatMouseClicked

    
    private void txtCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCariActionPerformed
    if(txtCari.getText().trim().isEmpty()){
        loadTable();
        return;
    }

    cariObat();
    }//GEN-LAST:event_txtCariActionPerformed
   
    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
    resetForm();
    loadTable();
    }//GEN-LAST:event_btnResetActionPerformed

    private void cmbKategoriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbKategoriActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbKategoriActionPerformed

    private void tblObatAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_tblObatAncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_tblObatAncestorAdded

    private void txtCariKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCariKeyReleased
        // TODO add your handling code here:
        if(txtCari.getText().trim().isEmpty()){
        loadTable();
        return;
    }

    cariObat();
    }//GEN-LAST:event_txtCariKeyReleased

    private void txtHargaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtHargaKeyTyped
        // TODO add your handling code here:
           char c = evt.getKeyChar();

    if (!Character.isDigit(c)) {
        evt.consume();
    }
    }//GEN-LAST:event_txtHargaKeyTyped

    private void txtStokKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtStokKeyTyped
        // TODO add your handling code here:
         char c = evt.getKeyChar();

    if (!Character.isDigit(c)) {
        evt.consume();
    }
    }//GEN-LAST:event_txtStokKeyTyped

    private void txtNamaObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNamaObatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNamaObatActionPerformed

    public static void main(String args[]) {
          java.awt.EventQueue.invokeLater(() -> new FrmObat().setVisible(true));
    }
        

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JButton btnUbah;
    private java.awt.Canvas canvas1;
    private javax.swing.JComboBox<String> cmbKategori;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblObat;
    private javax.swing.JTextField txtCari;
    private javax.swing.JTextField txtHarga;
    private javax.swing.JTextField txtIdObat;
    private javax.swing.JTextField txtNamaObat;
    private javax.swing.JTextField txtStok;
    // End of variables declaration//GEN-END:variables
}
