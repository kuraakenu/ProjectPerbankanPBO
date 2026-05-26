package View;

import Controller.TransaksiController;
import Model.Rekening.ModelRekening;
import Model.Transaksi.DAOTransaksi;
import Model.Transaksi.ModelTableTransaksi;
import javax.swing.JOptionPane;

public class ViewUtama extends javax.swing.JFrame {

    // ==== VARIABEL GLOBAL UTAMA (SESI & CONTROLLER) ====
    private String roleSesi;
    private ModelRekening akunNasabahSesi;
    private TransaksiController trxCtrl;
    private ModelTableTransaksi tableModelTrx;
    private DAOTransaksi daoTransaksi;

    /**
     * Constructor Utama ViewUtama
     * @param role "ADMIN" atau "NASABAH"
     * @param akunNasabah objek rekening jika yang login adalah nasabah (null jika admin)
     */
    public ViewUtama(String role, ModelRekening akunNasabah) {
        // 1. Inisialisasi komponen visual Swing (Bawaan NetBeans)
        initComponents();
        
        // 2. Simpan data sesi login
        this.roleSesi = role;
        this.akunNasabahSesi = akunNasabah;
        
        // 3. Inisialisasi Controller dan Data Layer
        this.trxCtrl = new TransaksiController();
        this.daoTransaksi = new DAOTransaksi();
        this.tableModelTrx = new ModelTableTransaksi();
        
        // 4. Hubungkan Model Tabel ke JTable di UI
        tblRiwayatTransaksi.setModel(tableModelTrx);
        
        // 5. Eksekusi Hak Akses Dinamis
        aturHakAksesUI();
    }

    /**
     * Mengatur tampilan komponen UI berdasarkan siapa yang login
     */
    private void aturHakAksesUI() {
        if (roleSesi.equalsIgnoreCase("ADMIN")) {
            lblSelamatDatang.setText("Mode: Administrator Bank (Teller)");
            
            // Tampilkan panel kontrol khusus Admin, sembunyikan fitur transaksi mandiri
            panelMenuAdmin.setVisible(true);
            panelMenuNasabah.setVisible(false);
            
            // Admin berhak melihat seluruh transaksi yang ada di database
            tableModelTrx.setList(daoTransaksi.getAllTransaksi());
            
        } else if (roleSesi.equalsIgnoreCase("NASABAH")) {
            // Tampilkan informasi rekening dan saldo nasabah yang sedang login
            lblSelamatDatang.setText("Selamat Datang! No. Rekening: " + akunNasabahSesi.getNoRek());
            txtTampilSaldo.setText("Rp " + akunNasabahSesi.getSaldo());
            
            // Sembunyikan panel pendaftaran admin, tampilkan panel transaksi nasabah
            panelMenuAdmin.setVisible(false);
            panelMenuNasabah.setVisible(true);
            
            // Nasabah hanya diizinkan melihat riwayat transaksinya sendiri
            tableModelTrx.setList(daoTransaksi.getTransaksiByRekening(akunNasabahSesi.getId()));
        }
    }

    /**
     * Method untuk menyegarkan komponen teks saldo di layar nasabah
     */
    public void refreshTampilanSesiNasabah() {
        if (akunNasabahSesi != null) {
            txtTampilSaldo.setText("Rp " + akunNasabahSesi.getSaldo());
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        lblSelamatDatang = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblRiwayatTransaksi = new javax.swing.JTable();
        lblRiwayat = new javax.swing.JLabel();
        panelMenuNasabah = new javax.swing.JPanel();
        lblTitelNasabah = new javax.swing.JLabel();
        lblSaldoUser = new javax.swing.JLabel();
        txtTampilSaldo = new javax.swing.JTextField();
        txtNorekTujuan = new javax.swing.JTextField();
        lblKeRek = new javax.swing.JLabel();
        txtNominalTransfer = new javax.swing.JTextField();
        lblNominalTrf = new javax.swing.JLabel();
        btnTransfer = new javax.swing.JButton();
        txtNominalSetorTarik = new javax.swing.JTextField();
        lblNominalSetorTarik = new javax.swing.JLabel();
        btnSetorTunai = new javax.swing.JButton();
        btnTarikTunai = new javax.swing.JButton();
        panelMenuAdmin = new javax.swing.JPanel();
        lblTitelAdmin = new javax.swing.JLabel();
        lblInfoAdmin = new javax.swing.JLabel();
        btnBuatRekeningBaru = new javax.swing.JButton();
        btnLogOut = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sistem Aplikasi Perbankan Utama");

        lblSelamatDatang.setFont(new java.awt.Font("Segoe UI", 1, 14)); 
        lblSelamatDatang.setText("Selamat Datang, [User]");

        tblRiwayatTransaksi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {}
        ));
        jScrollPane1.setViewportView(tblRiwayatTransaksi);

        lblRiwayat.setFont(new java.awt.Font("Segoe UI", 1, 12)); 
        lblRiwayat.setText("Log Riwayat Transaksi:");

        panelMenuNasabah.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblTitelNasabah.setFont(new java.awt.Font("Segoe UI", 1, 12)); 
        lblTitelNasabah.setForeground(new java.awt.Color(0, 102, 204));
        lblTitelNasabah.setText("PANEL TRANSAKSI NASABAH");

        lblSaldoUser.setText("Saldo Anda:");

        txtTampilSaldo.setEditable(false);
        txtTampilSaldo.setFont(new java.awt.Font("Segoe UI", 1, 12)); 

        lblKeRek.setText("No. Rekening Tujuan Transfer:");

        lblNominalTrf.setText("Nominal Transfer (Rp):");

        btnTransfer.setText("Kirim Transfer");
        btnTransfer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTransferActionPerformed(evt);
            }
        });

        lblNominalSetorTarik.setText("Nominal Setor / Tarik Tunai (Rp):");

        btnSetorTunai.setText("Setor Tunai");
        btnSetorTunai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSetorTunaiActionPerformed(evt);
            }
        });

        btnTarikTunai.setText("Tarik Tunai");
        btnTarikTunai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTarikTunaiActionPerformed(evt);
            }
        });

        panelMenuNasabah.setLayout(new java.awt.GridLayout(0, 1, 5, 5));
        panelMenuNasabah.add(lblTitelNasabah);
        panelMenuNasabah.add(lblSaldoUser);
        panelMenuNasabah.add(txtTampilSaldo);
        panelMenuNasabah.add(lblKeRek);
        panelMenuNasabah.add(txtNorekTujuan);
        panelMenuNasabah.add(lblNominalTrf);
        panelMenuNasabah.add(txtNominalTransfer);
        panelMenuNasabah.add(btnTransfer);
        panelMenuNasabah.add(lblNominalSetorTarik);
        panelMenuNasabah.add(txtNominalSetorTarik);
        panelMenuNasabah.add(btnSetorTunai);
        panelMenuNasabah.add(btnTarikTunai);

        panelMenuAdmin.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblTitelAdmin.setFont(new java.awt.Font("Segoe UI", 1, 12)); 
        lblTitelAdmin.setForeground(new java.awt.Color(204, 0, 51));
        lblTitelAdmin.setText("PANEL MANAGEMENT TELLER / ADMIN");

        lblInfoAdmin.setText("Akses penuh: Pembuatan akun nasabah baru & audit seluruh mutasi bank.");

        btnBuatRekeningBaru.setText("Pendaftaran & Pembuatan Rekening Baru");
        btnBuatRekeningBaru.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuatRekeningBaruActionPerformed(evt);
            }
        });

        panelMenuAdmin.setLayout(new java.awt.GridLayout(0, 1, 5, 5));
        panelMenuAdmin.add(lblTitelAdmin);
        panelMenuAdmin.add(lblInfoAdmin);
        panelMenuAdmin.add(btnBuatRekeningBaru);

        btnLogOut.setText("Log Out");
        btnLogOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogOutActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblSelamatDatang)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnLogOut))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblRiwayat)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(panelMenuNasabah, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(panelMenuAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSelamatDatang)
                    .addComponent(btnLogOut))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelMenuNasabah, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelMenuAdmin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(lblRiwayat)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>                        

    // ==== EVENT LISTENERS ACTION PERFORMED ====

    private void btnTransferActionPerformed(java.awt.event.ActionEvent evt) {                                            
        try {
            String norekTujuan = txtNorekTujuan.getText();
            double nominal = Double.parseDouble(txtNominalTransfer.getText());

            trxCtrl.prosesTransfer(akunNasabahSesi, norekTujuan, nominal, tableModelTrx);

            txtNorekTujuan.setText("");
            txtNominalTransfer.setText("");
            
            refreshTampilanSesiNasabah();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Nominal transfer harus berupa angka valid!", "Input Gagal", JOptionPane.ERROR_MESSAGE);
        }
    }                                           

    private void btnSetorTunaiActionPerformed(java.awt.event.ActionEvent evt) {                                              
        try {
            double nominal = Double.parseDouble(txtNominalSetorTarik.getText());
            
            trxCtrl.prosesSetorTunai(akunNasabahSesi, nominal, tableModelTrx);
            
            txtNominalSetorTarik.setText("");
            refreshTampilanSesiNasabah();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Nominal setoran harus berupa angka!", "Input Gagal", JOptionPane.ERROR_MESSAGE);
        }
    }                                             

    private void btnTarikTunaiActionPerformed(java.awt.event.ActionEvent evt) {                                              
        try {
            double nominal = Double.parseDouble(txtNominalSetorTarik.getText());
            
            trxCtrl.prosesTarikTunai(akunNasabahSesi, nominal, tableModelTrx);
            
            txtNominalSetorTarik.setText("");
            refreshTampilanSesiNasabah();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Nominal penarikan harus berupa angka!", "Input Gagal", JOptionPane.ERROR_MESSAGE);
        }
    }                                             

    private void btnBuatRekeningBaruActionPerformed(java.awt.event.ActionEvent evt) {                                                    
        JOptionPane.showMessageDialog(this, "Membuka Form Pembuatan Rekening Baru (TabRekening)...");
    }                                                   

    private void btnLogOutActionPerformed(java.awt.event.ActionEvent evt) {                                          
        new ViewLogin().setVisible(true);
        this.dispose();
    }                                         

    // ==== PERBAIKAN UTAMA: DEKLARASI VARIABEL KOMPONEN VISUAL SWING ====
    private javax.swing.JButton btnBuatRekeningBaru;
    private javax.swing.JButton btnLogOut;
    private javax.swing.JButton btnSetorTunai;
    private javax.swing.JButton btnTarikTunai;
    private javax.swing.JButton btnTransfer;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblKeRek;
    private javax.swing.JLabel lblNominalSetorTarik;
    private javax.swing.JLabel lblNominalTrf;
    private javax.swing.JLabel lblRiwayat;
    private javax.swing.JLabel lblSaldoUser;
    private javax.swing.JLabel lblSelamatDatang;
    private javax.swing.JLabel lblTitelAdmin;
    private javax.swing.JLabel lblTitelNasabah;
    private javax.swing.JLabel lblInfoAdmin;
    private javax.swing.JPanel panelMenuAdmin;
    private javax.swing.JPanel panelMenuNasabah;
    private javax.swing.JTable tblRiwayatTransaksi;
    private javax.swing.JTextField txtNominalSetorTarik;
    private javax.swing.JTextField txtNominalTransfer;
    private javax.swing.JTextField txtNorekTujuan;
    private javax.swing.JTextField txtTampilSaldo;
}