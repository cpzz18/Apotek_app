/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import config.Koneksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.NumberFormat;
import java.util.Locale;

/**
 *
 * @author chand
 */
public class FrmPenjualan extends javax.swing.JFrame {

    private double hargaObat = 0;

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(FrmPenjualan.class.getName());

    /**
     * Creates new form FrmPenjualan
     */
    public FrmPenjualan() {
        initComponents();

        loadPelanggan();
        loadObat();

        txtHarga.setEditable(false);
        txtStok.setEditable(false);
        txtSubtotal.setEditable(false);

        tampilDataPenjualan();
        loadRingkasan();
        loadStokMenipis();
        tampilDataObat();

        resetForm();

        txtJumlah.addKeyListener(new KeyAdapter() {

            @Override
            public void keyTyped(KeyEvent e) {

                char c = e.getKeyChar();

                if (!Character.isDigit(c)
                        && c != KeyEvent.VK_BACK_SPACE
                        && c != KeyEvent.VK_DELETE) {

                    e.consume();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

                hitungSubtotal();
            }
        });
    }

    private void loadPelanggan() {

        cmbPelanggan.removeAllItems();

        try (
                Connection conn = Koneksi.getConnection(); PreparedStatement ps
                = conn.prepareStatement(
                        "SELECT nama_pelanggan FROM pelanggan")) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                cmbPelanggan.addItem(
                        rs.getString("nama_pelanggan"));

            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage());

        }
    }

    private void loadObat() {

        cmbObat.removeAllItems();

        try (
                Connection conn = Koneksi.getConnection(); PreparedStatement ps
                = conn.prepareStatement(
                        "SELECT nama_obat FROM obat")) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                cmbObat.addItem(
                        rs.getString("nama_obat"));

            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage());

        }
    }

    private void tampilDataObat() {
        if (cmbObat.getSelectedItem() == null) {
            return;
        }

        String namaObat
                = cmbObat.getSelectedItem().toString();

        try (
                Connection conn = Koneksi.getConnection(); PreparedStatement ps
                = conn.prepareStatement(
                        "SELECT * FROM obat WHERE nama_obat=?")) {

            ps.setString(1, namaObat);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {

                hargaObat = rs.getDouble("harga");

                NumberFormat rupiah = NumberFormat.getInstance(new Locale("id", "ID"));
                rupiah.setMaximumFractionDigits(0);

                txtHarga.setText("Rp " + rupiah.format(hargaObat));

                txtStok.setText(
                        rs.getString("stok"));
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage());

        }
    }

    private void hitungSubtotal() {

        String input = txtJumlah.getText().trim();

        if (input.isEmpty()) {
            txtSubtotal.setText("");
            return;
        }

        try {

            int jumlah = Integer.parseInt(input);

            if (jumlah <= 0) {
                txtSubtotal.setText("");
                return;
            }

            double subtotal = hargaObat * jumlah;

            NumberFormat rupiah = NumberFormat.getInstance(new Locale("id", "ID"));
            rupiah.setMaximumFractionDigits(0);
            txtSubtotal.setText("Rp " + rupiah.format(subtotal));

        } catch (NumberFormatException e) {

            txtSubtotal.setText("");

        }
    }

    private void loadStokMenipis() {

        try (
                Connection conn
                = Koneksi.getConnection(); PreparedStatement ps
                = conn.prepareStatement(
                        "SELECT COUNT(*) total FROM obat WHERE stok <= 5")) {

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                lblStokMenipis.setText(
                        rs.getString("total"));
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage());

        }
    }

    private void simpanPenjualan() {

        // Validasi input jumlah
        if (txtJumlah.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Jumlah belum diisi!");

            return;
        }

        int jumlah;

        try {

            jumlah = Integer.parseInt(
                    txtJumlah.getText().trim());

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Jumlah harus berupa angka!");

            return;
        }

        if (jumlah <= 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Jumlah harus lebih dari 0!");

            return;
        }

        try {

            Connection conn
                    = Koneksi.getConnection();

            String pelanggan
                    = cmbPelanggan.getSelectedItem().toString();

            String obat
                    = cmbObat.getSelectedItem().toString();

            double harga = hargaObat;

            double subtotal
                    = harga * jumlah;

            // Ambil ID Pelanggan
            PreparedStatement ps1
                    = conn.prepareStatement(
                            "SELECT id_pelanggan FROM pelanggan WHERE nama_pelanggan=?");

            ps1.setString(1, pelanggan);

            ResultSet rs1
                    = ps1.executeQuery();

            if (!rs1.next()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Data pelanggan tidak ditemukan!");

                return;
            }

            int idPelanggan
                    = rs1.getInt("id_pelanggan");

            // Ambil ID Obat dan Stok
            PreparedStatement ps2
                    = conn.prepareStatement(
                            "SELECT id_obat, stok FROM obat WHERE nama_obat=?");

            ps2.setString(1, obat);

            ResultSet rs2
                    = ps2.executeQuery();

            if (!rs2.next()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Data obat tidak ditemukan!");

                return;
            }

            int idObat
                    = rs2.getInt("id_obat");

            int stok
                    = rs2.getInt("stok");

            // Validasi stok
            if (jumlah > stok) {

                JOptionPane.showMessageDialog(
                        this,
                        "Stok tidak mencukupi!");

                return;
            }

            // Simpan transaksi
            PreparedStatement ps3
                    = conn.prepareStatement(
                            """
                INSERT INTO penjualan
                (
                    id_pelanggan,
                    id_obat,
                    jumlah,
                    harga_satuan,
                    subtotal
                )
                VALUES (?,?,?,?,?)
                """
                    );

            ps3.setInt(1, idPelanggan);
            ps3.setInt(2, idObat);
            ps3.setInt(3, jumlah);
            ps3.setDouble(4, harga);
            ps3.setDouble(5, subtotal);

            ps3.executeUpdate();

            // Update stok obat
            PreparedStatement ps4
                    = conn.prepareStatement(
                            "UPDATE obat SET stok = stok - ? WHERE id_obat = ?");

            ps4.setInt(1, jumlah);
            ps4.setInt(2, idObat);

            ps4.executeUpdate();

            JOptionPane.showMessageDialog(
                    this,
                    "Transaksi berhasil disimpan!");

            tampilDataPenjualan();
            loadRingkasan();
            loadStokMenipis();
            tampilDataObat();
            resetForm();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Gagal menyimpan transaksi:\n"
                    + e.getMessage());

        }
    }

    private void tampilDataPenjualan() {

        DefaultTableModel model = new DefaultTableModel() {

            @Override
            public boolean isCellEditable(int row, int column) {

                return false;
            }
        };

        model.addColumn("ID");
        model.addColumn("Pelanggan");
        model.addColumn("Obat");
        model.addColumn("Jumlah");
        model.addColumn("Harga");
        model.addColumn("Subtotal");
        model.addColumn("Tanggal");

        try (
                Connection conn = Koneksi.getConnection(); PreparedStatement ps
                = conn.prepareStatement(
                        """
                SELECT p.id_penjualan,
                       pl.nama_pelanggan,
                       o.nama_obat,
                       p.jumlah,
                       p.harga_satuan,
                       p.subtotal,
                       p.tanggal
                FROM penjualan p
                JOIN pelanggan pl
                ON p.id_pelanggan = pl.id_pelanggan
                JOIN obat o
                ON p.id_obat = o.id_obat
                ORDER BY p.id_penjualan DESC
                """
                )) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                NumberFormat rupiah = NumberFormat.getInstance(new Locale("id", "ID"));
                rupiah.setMaximumFractionDigits(0);

                model.addRow(new Object[]{
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getInt(4),
                    "Rp " + rupiah.format(rs.getDouble(5)),
                    "Rp " + rupiah.format(rs.getDouble(6)),
                    rs.getTimestamp(7)
                });

            }

            tblPenjualan.setModel(model);

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage());

        }
    }

    private void loadRingkasan() {

        try (
                Connection conn
                = Koneksi.getConnection()) {

            PreparedStatement ps1
                    = conn.prepareStatement(
                            "SELECT COUNT(*) total FROM penjualan");

            ResultSet rs1
                    = ps1.executeQuery();

            if (rs1.next()) {

                lblTotalTransaksi.setText(
                        rs1.getString("total"));
            }

            PreparedStatement ps2
                    = conn.prepareStatement(
                            "SELECT IFNULL(SUM(subtotal),0) total FROM penjualan");

            ResultSet rs2
                    = ps2.executeQuery();

            if (rs2.next()) {

                double totalPendapatan
                        = rs2.getDouble("total");

                NumberFormat rupiah = NumberFormat.getInstance(new Locale("id", "ID"));
                rupiah.setMaximumFractionDigits(0);

                lblPendapatan.setText("Rp " + rupiah.format(totalPendapatan));
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage());

        }
    }

    private void resetForm() {

        txtJumlah.setText("");
        txtSubtotal.setText("");

        if (cmbObat.getItemCount() > 0) {
            cmbObat.setSelectedIndex(0);
        }

        if (cmbPelanggan.getItemCount() > 0) {
            cmbPelanggan.setSelectedIndex(0);
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

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        cmbPelanggan = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        cmbObat = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        txtHarga = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtStok = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtJumlah = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtSubtotal = new javax.swing.JTextField();
        btnSimpan = new javax.swing.JButton();
        btnBatal = new javax.swing.JButton();
        cardRingkasan = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        lblTotalTransaksi = new javax.swing.JLabel();
        lblPendapatan = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblPenjualan = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        lblStokMenipis = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnKembali = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel2.setText("Pelanggan: ");

        cmbPelanggan.addActionListener(this::cmbPelangganActionPerformed);

        jLabel3.setText("Obat:");

        cmbObat.addActionListener(this::cmbObatActionPerformed);

        jLabel4.setText("Harga: ");

        txtHarga.addActionListener(this::txtHargaActionPerformed);

        jLabel5.setText("Stok: ");

        jLabel7.setText("Jumlah: ");

        txtJumlah.addActionListener(this::txtJumlahActionPerformed);

        jLabel9.setText("Subtotal");

        btnSimpan.setBackground(new java.awt.Color(102, 255, 102));
        btnSimpan.setText("Simpan");
        btnSimpan.addActionListener(this::btnSimpanActionPerformed);

        btnBatal.setBackground(new java.awt.Color(255, 51, 51));
        btnBatal.setText("Batal");
        btnBatal.addActionListener(this::btnBatalActionPerformed);

        cardRingkasan.setBackground(new java.awt.Color(204, 204, 204));

        jLabel11.setText("Total Transaksi:");

        jLabel12.setText("Pendapatan:");

        lblTotalTransaksi.setText("0");

        lblPendapatan.setText("0");

        javax.swing.GroupLayout cardRingkasanLayout = new javax.swing.GroupLayout(cardRingkasan);
        cardRingkasan.setLayout(cardRingkasanLayout);
        cardRingkasanLayout.setHorizontalGroup(
            cardRingkasanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cardRingkasanLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(cardRingkasanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(cardRingkasanLayout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblTotalTransaksi))
                    .addGroup(cardRingkasanLayout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblPendapatan)))
                .addContainerGap(69, Short.MAX_VALUE))
        );
        cardRingkasanLayout.setVerticalGroup(
            cardRingkasanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cardRingkasanLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(cardRingkasanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(lblTotalTransaksi))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(cardRingkasanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(lblPendapatan)))
        );

        tblPenjualan.setBackground(new java.awt.Color(255, 255, 102));
        tblPenjualan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Pelanggan", "Obat", "Jumlah", "Harga", "Sub Total", "Tanggal"
            }
        ));
        jScrollPane2.setViewportView(tblPenjualan);

        jPanel1.setBackground(new java.awt.Color(255, 51, 51));

        jLabel13.setBackground(new java.awt.Color(0, 0, 0));
        jLabel13.setText("Stok Menipis");

        lblStokMenipis.setText("0");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel13))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addComponent(lblStokMenipis)))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblStokMenipis)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jLabel14.setFont(new java.awt.Font("Liberation Sans", 0, 18)); // NOI18N
        jLabel14.setText("Riwayat Penjualan");

        jPanel2.setBackground(new java.awt.Color(255, 255, 51));

        jLabel1.setFont(new java.awt.Font("Liberation Sans", 1, 24)); // NOI18N
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/store_24.png"))); // NOI18N
        jLabel1.setText("PENJUALAN OBAT");

        btnKembali.setText("Kembali");
        btnKembali.addActionListener(this::btnKembaliActionPerformed);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(btnKembali, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(248, 248, 248)
                .addComponent(jLabel1)
                .addContainerGap(323, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(btnKembali, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(33, Short.MAX_VALUE))
        );

        jLabel10.setText("Ringkasan");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel5)
                                            .addComponent(jLabel7)
                                            .addComponent(jLabel9))
                                        .addGap(45, 45, 45)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(txtJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtStok, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtSubtotal, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel2)
                                            .addComponent(jLabel3))
                                        .addGap(23, 23, 23)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(cmbPelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(cmbObat, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtHarga, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addGap(37, 37, 37)
                                        .addComponent(btnSimpan)
                                        .addGap(29, 29, 29)
                                        .addComponent(btnBatal)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(89, 89, 89)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(cardRingkasan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel10))
                                        .addGap(18, 18, 18)
                                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(37, 37, 37)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel14)
                                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 548, javax.swing.GroupLayout.PREFERRED_SIZE))))))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jLabel2)
                                            .addGap(71, 71, 71)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(jLabel4)
                                                .addComponent(txtHarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(cardRingkasan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(cmbPelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                    .addComponent(cmbObat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel10)
                                        .addGap(107, 107, 107)))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(txtStok, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(txtJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(txtSubtotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(39, 39, 39)
                                .addComponent(jLabel6))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnBatal, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(66, 66, 66))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel14)
                        .addGap(6, 6, 6)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)))
                .addComponent(jLabel8)
                .addContainerGap(10, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmbPelangganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbPelangganActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbPelangganActionPerformed

    private void txtHargaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtHargaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHargaActionPerformed

    private void txtJumlahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtJumlahActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtJumlahActionPerformed

    private void btnKembaliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKembaliActionPerformed
        // TODO add your handling code here:
        new FrmDashboard().setVisible(true);
        dispose();
    }//GEN-LAST:event_btnKembaliActionPerformed

    private void cmbObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbObatActionPerformed
        // TODO add your handling code here:
        tampilDataObat();
    }//GEN-LAST:event_cmbObatActionPerformed


    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        // TODO add your handling code here:

        simpanPenjualan();
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void btnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalActionPerformed
        // TODO add your handling code here:
        resetForm();
    }//GEN-LAST:event_btnBatalActionPerformed

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
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new FrmPenjualan().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBatal;
    private javax.swing.JButton btnKembali;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JPanel cardRingkasan;
    private javax.swing.JComboBox<String> cmbObat;
    private javax.swing.JComboBox<String> cmbPelanggan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblPendapatan;
    private javax.swing.JLabel lblStokMenipis;
    private javax.swing.JLabel lblTotalTransaksi;
    private javax.swing.JTable tblPenjualan;
    private javax.swing.JTextField txtHarga;
    private javax.swing.JTextField txtJumlah;
    private javax.swing.JTextField txtStok;
    private javax.swing.JTextField txtSubtotal;
    // End of variables declaration//GEN-END:variables
}
