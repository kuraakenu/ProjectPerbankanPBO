package Controller;

import Model.User.DAOUser;
import Model.User.ModelUser;
import Model.Rekening.DAORekening;
import Model.Rekening.ModelRekening;
import javax.swing.JOptionPane;

public class LoginController {
    
    private DAOUser daoUser;
    private DAORekening daoRekening;

    public LoginController() {
        // Inisialisasi mesin DAO yang dibutuhkan
        this.daoUser = new DAOUser();
        this.daoRekening = new DAORekening();
    }

    /**
     * Fungsi untuk memproses login Admin
     * @param username input dari JTextField
     * @param password input dari JPasswordField
     * @return ModelUser jika sukses, null jika gagal
     */
    public ModelUser prosesLoginAdmin(String username, String password) {
        // Validasi input kosong (Error Handling di tingkat Controller)
        if (username.trim().isEmpty() || password.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Username dan Password tidak boleh kosong!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return null;
        }

        // Cek ke database lewat DAO
        ModelUser admin = daoUser.loginAdmin(username, password);

        if (admin != null) {
            JOptionPane.showMessageDialog(null, "Selamat Datang Admin, " + admin.getUsername() + "!", "Login Sukses", JOptionPane.INFORMATION_MESSAGE);
            return admin;
        } else {
            JOptionPane.showMessageDialog(null, "Username atau Password Admin Salah!", "Login Gagal", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    /**
     * Fungsi untuk memproses login Nasabah (Menggunakan No Rekening & PIN)
     * @param noRek input dari JTextField
     * @param pin input dari JPasswordField / JTextField
     * @return ModelRekening jika sukses, null jika gagal
     */
    public ModelRekening prosesLoginNasabah(String noRek, String pin) {
        // Validasi input kosong
        if (noRek.trim().isEmpty() || pin.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nomor Rekening dan PIN tidak boleh kosong!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return null;
        }

        // Validasi format PIN harus 6 digit angka (Penerapan aturan bisnis perbankan)
        if (pin.length() != 6 || !pin.matches("[0-9]+")) {
            JOptionPane.showMessageDialog(null, "PIN harus berupa 6 digit angka!", "Format Salah", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        // Cek ke database lewat DAO
        ModelRekening nasabah = daoRekening.loginNasabah(noRek, pin);

        if (nasabah != null) {
            JOptionPane.showMessageDialog(null, "Login Berhasil!\nNomor Rekening: " + nasabah.getNoRek(), "Selamat Datang", JOptionPane.INFORMATION_MESSAGE);
            return nasabah;
        } else {
            JOptionPane.showMessageDialog(null, "Nomor Rekening atau PIN Salah!", "Login Gagal", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
}