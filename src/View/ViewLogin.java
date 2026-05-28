package View;

import Controller.LoginController;
import Model.User.ModelUser;
import Model.Rekening.ModelRekening;
import javax.swing.JOptionPane;

public class ViewLogin extends javax.swing.JFrame {

    /**
     * Constructor Utama ViewLogin
     */
    public ViewLogin() {
        // PERBAIKAN: Memanggil method dengan benar (tanpa deklarasi tipe data)
        initComponents();
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {
        // KODE INI WAJIB ADA: Berfungsi membangun visual tombol dan teks di layar Anda
        lblTitelAplikasi = new javax.swing.JLabel();
        lblRole = new javax.swing.JLabel();
        cbRole = new javax.swing.JComboBox<>();
        lblUsername = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        lblPassword = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();
        btnLogin = new javax.swing.JButton();
        btnKeluar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Gerbang Masuk - Sistem Perbankan");
        setResizable(false);

        lblTitelAplikasi.setFont(new java.awt.Font("Segoe UI", 1, 18)); 
        lblTitelAplikasi.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitelAplikasi.setText("SISTEM APLIKASI PERBANKAN");

        lblRole.setFont(new java.awt.Font("Segoe UI", 1, 12)); 
        lblRole.setText("Masuk Sebagai :");

        cbRole.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Admin / Teller", "Nasabah" }));
        cbRole.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbRoleActionPerformed(evt);
            }
        });

        lblUsername.setText("Username Admin :");

        lblPassword.setText("Password Admin :");

        btnLogin.setFont(new java.awt.Font("Segoe UI", 1, 12)); 
        btnLogin.setText("LOGIN");
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });

        btnKeluar.setText("Keluar");
        btnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKeluarActionPerformed(evt);
            }
        });

        // Tata Letak Layout Window Form
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblPassword)
                    .addComponent(lblUsername)
                    .addComponent(lblRole)
                    .addComponent(lblTitelAplikasi, javax.swing.GroupLayout.DEFAULT_SIZE, 320, Short.MAX_VALUE)
                    .addComponent(cbRole, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtUsername)
                    .addComponent(txtPassword)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnKeluar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(40, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(lblTitelAplikasi)
                .addGap(25, 25, 25)
                .addComponent(lblRole)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbRole, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblUsername)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblPassword)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnKeluar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null); 
    }// </editor-fold>                        

    // ==== EVENT LISTENERS ACTION PERFORMED ====

    /**
     * Logika Utama saat Tombol LOGIN ditekan
     */
    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {                                         
        String roleSelected = cbRole.getSelectedItem().toString();
        String inputUser = txtUsername.getText();
        String inputPass = new String(txtPassword.getPassword());
        
        LoginController loginCtrl = new LoginController();
        
        if (roleSelected.equalsIgnoreCase("Admin / Teller")) {
            ModelUser admin = loginCtrl.prosesLoginAdmin(inputUser, inputPass);
            
            if (admin != null) {
                ViewUtama mainView = new ViewUtama("ADMIN", null);
                mainView.setVisible(true);
                this.dispose();
            }
            
        } else {
            ModelRekening nasabah = loginCtrl.prosesLoginNasabah(inputUser, inputPass);
            
            if (nasabah != null) {
                ViewUtama mainView = new ViewUtama("NASABAH", nasabah);
                mainView.setVisible(true);
                this.dispose();
            }
        }
    }                                        

    /**
     * Mengubah teks placeholder label input secara dinamis saat dropdown role diganti
     */
    private void cbRoleActionPerformed(java.awt.event.ActionEvent evt) {                                       
        String roleSelected = cbRole.getSelectedItem().toString();
        if (roleSelected.equalsIgnoreCase("Admin / Teller")) {
            lblUsername.setText("Username Admin :");
            lblPassword.setText("Password Admin :");
        } else {
            lblUsername.setText("Nomor Rekening Nasabah :");
            lblPassword.setText("6 Digit PIN Nasabah :");
        }
    }                                      

    /**
     * Menutup jalannya aplikasi secara aman
     */
    private void btnKeluarActionPerformed(java.awt.event.ActionEvent evt) {                                          
        System.exit(0);
    }                                         

    /**
     * Method Main Utama untuk menjalankan form login pertama kali
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ViewLogin().setVisible(true);
            }
        });
    }

    // ==== DEKLARASI KOMPONEN VISUAL SWING ====
    private javax.swing.JButton btnKeluar;
    private javax.swing.JButton btnLogin;
    private javax.swing.JComboBox<String> cbRole;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lblRole;
    private javax.swing.JLabel lblTitelAplikasi;
    private javax.swing.JLabel lblUsername;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtUsername;
}