package Main;

import View.ViewLogin;
import javax.swing.UIManager;

public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // 1. Mengatur 'Look and Feel' UI agar mengikuti tema sistem operasi (UX modern)
        try {
            // Mengubah tampilan standar Java Swing yang jadul menjadi rapi sesuai OS (Windows/Mac/Linux)
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println("Gagal memuat tema Look and Feel: " + e.getMessage());
        }

        // 2. Menjalankan Thread UI Utama (Event Dispatch Thread) secara aman
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Membuat objek gerbang login dan memunculkannya ke layar
                ViewLogin loginFrame = new ViewLogin();
                loginFrame.setVisible(true);
            }
        });
    }
}