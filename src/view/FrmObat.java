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
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnKembali = new javax.swing.JButton();

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

        btnSimpan.setBackground(new java.awt.Color(102, 255, 102));
        btnSimpan.setText("Simpan");
        btnSimpan.addActionListener(this::btnSimpanActionPerformed);

        btnUbah.setBackground(new java.awt.Color(102, 255, 102));
        btnUbah.setText("Edit");
        btnUbah.addActionListener(this::btnUbahActionPerformed);

        btnHapus.setBackground(new java.awt.Color(255, 102, 102));
        btnHapus.setText("Hapus");
        btnHapus.addActionListener(this::btnHapusActionPerformed);

        jLabel7.setText("Cari Obat");

        txtCari.addActionListener(this::txtCariActionPerformed);
        txtCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCariKeyReleased(evt);
            }
        });

        tblObat.setBackground(new java.awt.Color(255, 255, 102));
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
        tblObat.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
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
        jLabel8.setText("DATA OBAT");

        btnReset.setBackground(new java.awt.Color(255, 102, 102));
        btnReset.setText("Reset");
        btnReset.addActionListener(this::btnResetActionPerformed);

        cmbKategori.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbKategori.addActionListener(this::cmbKategoriActionPerformed);

        jPanel1.setBackground(new java.awt.Color(255, 255, 51));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/obat_24.png"))); // NOI18N
        jLabel1.setText("FORM OBAT");

        btnKembali.setText("Kembali");
        btnKembali.addActionListener(this::btnKembaliActionPerformed);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnKembali)
                .addGap(141, 141, 141)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(btnKembali))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(btnSimpan)
                                .addGap(18, 18, 18))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6))
                                .addGap(49, 49, 49)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnUbah)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnHapus)
                                .addGap(18, 18, 18)
                                .addComponent(btnReset))
                            .addComponent(txtNamaObat, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtIdObat, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbKategori, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtHarga, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtStok, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(215, 215, 215)
                        .addComponent(jLabel8)))
                .addContainerGap(116, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
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
                    .addComponent(txtHarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtStok, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(37, 37, 37)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUbah, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnReset, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                    .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
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

    private void btnKembaliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKembaliActionPerformed
        // TODO add your handling code here:
        new FrmDashboard().setVisible(true);
    dispose();
    }//GEN-LAST:event_btnKembaliActionPerformed

    public static void main(String args[]) {
          java.awt.EventQueue.invokeLater(() -> new FrmObat().setVisible(true));
    }
        

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnKembali;
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
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblObat;
    private javax.swing.JTextField txtCari;
    private javax.swing.JTextField txtHarga;
    private javax.swing.JTextField txtIdObat;
    private javax.swing.JTextField txtNamaObat;
    private javax.swing.JTextField txtStok;
    // End of variables declaration//GEN-END:variables
}
