package Model.Transaksi;

// PERBAIKAN IMPORT: Sesuaikan dengan lokasi class koneksi database Anda
// Jika nama class Anda adalah 'Koneksi' di dalam package 'Connection', ubah ke: Connection.Koneksi;
import Connection.Koneksi; 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
        
public class DAOTransaksi implements InterfaceDAOTransaksi {
    
    // PERBAIKAN: Memperbaiki typo dari Connecntion menjadi Connection
    private Connection connection;
    
    public DAOTransaksi(){
        // PERBAIKAN: Disesuaikan dengan pemanggilan class Koneksi utama Anda
        this.connection = Koneksi.Connect(); 
    }
    
    @Override
    public boolean tambahTransaksi(ModelTransaksi trx) {
        // Query untuk menyimpan log ke tabel transaksi
        String queryTrx = "INSERT INTO transaksi (jenis, jumlah, id_rekening_asal, id_rekening_tujuan) VALUES (?, ?, ?, ?)";
        
        // Query untuk mengubah saldo rekening terkait
        String queryUpdateSaldo = "UPDATE rekening SET saldo = saldo + ? WHERE id = ?";

        try {
            // MATIKAN AUTO-COMMIT: Kita kendalikan alur transaksi secara manual (ACID Property)
            connection.setAutoCommit(false);

            // 1. Eksekusi Pengurangan / Penambahan Saldo sesuai Jenis Transaksi
            if (trx.getJenis().equalsIgnoreCase("Transfer")) {
                // Potong saldo rekening asal (jumlah bernilai minus)
                try (PreparedStatement psAsal = connection.prepareStatement(queryUpdateSaldo)) {
                    psAsal.setDouble(1, -trx.getJumlah());
                    psAsal.setInt(2, trx.getRekAsal());
                    psAsal.executeUpdate();
                }
                
                // Tambah saldo rekening tujuan (jumlah bernilai plus)
                try (PreparedStatement psTujuan = connection.prepareStatement(queryUpdateSaldo)) {
                    psTujuan.setDouble(1, trx.getJumlah());
                    psTujuan.setInt(2, trx.getRekTujuan());
                    psTujuan.executeUpdate();
                }
                
            } else if (trx.getJenis().equalsIgnoreCase("Tarik Tunai")) {
                // Potong saldo rekening asal
                try (PreparedStatement psAsal = connection.prepareStatement(queryUpdateSaldo)) {
                    psAsal.setDouble(1, -trx.getJumlah());
                    psAsal.setInt(2, trx.getRekAsal());
                    psAsal.executeUpdate();
                }
                
            } else if (trx.getJenis().equalsIgnoreCase("Setor Tunai")) {
                // Tambah saldo rekening tujuan
                try (PreparedStatement psTujuan = connection.prepareStatement(queryUpdateSaldo)) {
                    psTujuan.setDouble(1, trx.getJumlah());
                    psTujuan.setInt(2, trx.getRekTujuan());
                    psTujuan.executeUpdate();
                }
            }

            // 2. Catat Riwayat ke Tabel Transaksi
            try (PreparedStatement psTrx = connection.prepareStatement(queryTrx)) {
                psTrx.setString(1, trx.getJenis());
                psTrx.setDouble(2, trx.getJumlah());
                
                // Handle jika rekening asal kosong (misal saat Setor Tunai)
                if (trx.getRekAsal() == 0) {
                    psTrx.setNull(3, Types.INTEGER);
                } else {
                    psTrx.setInt(3, trx.getRekAsal());
                }
                
                // Handle jika rekening tujuan kosong (misal saat Tarik Tunai)
                if (trx.getRekTujuan() == 0) {
                    psTrx.setNull(4, Types.INTEGER);
                } else {
                    psTrx.setInt(4, trx.getRekTujuan());
                }
                
                psTrx.executeUpdate();
            }

            // JIKA SEMUA PROSES BERHASIL TANPA ERROR -> SIMPAN PERMANEN KE DATABASE
            connection.commit();
            return true;

        } catch (SQLException e) {
            try {
                // JIKA ADA PROSES YANG GAGAL -> BATALKAN SEMUA TOTAL! (Prinsip Atomicity Mutasi Bank)
                System.out.println("Transaksi Gagal, Melakukan Rollback... " + e.getMessage());
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException ex) {
                System.out.println("Rollback Gagal: " + ex.getMessage());
            }
            return false;
        } finally {
            try {
                // Kembalikan ke mode default database setelah transaksi selesai
                if (connection != null) {
                    connection.setAutoCommit(true);
                }
            } catch (SQLException e) {
                System.out.println("Gagal mengembalikan auto-commit: " + e.getMessage());
            }
        }
    }

    @Override
    public List<ModelTransaksi> getAllTransaksi() {
        List<ModelTransaksi> list = new ArrayList<>();
        String query = "SELECT * FROM transaksi ORDER BY tanggal DESC";
        
        try (PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                ModelTransaksi trx = new ModelTransaksi();
                trx.setId(rs.getInt("id"));
                trx.setJenis(rs.getString("jenis"));
                trx.setJumlah(rs.getDouble("jumlah"));
                trx.setTanggal(rs.getString("tanggal"));
                trx.setRekAsal(rs.getInt("id_rekening_asal"));
                trx.setRekTujuan(rs.getInt("id_rekening_tujuan"));
                list.add(trx);
            }
        } catch (SQLException e) {
            System.out.println("Error getAllTransaksi: " + e.getMessage());
        }
        return list;
    }

    @Override
    public List<ModelTransaksi> getTransaksiByRekening(int idRekening) {
        List<ModelTransaksi> list = new ArrayList<>();
        String query = "SELECT * FROM transaksi WHERE id_rekening_asal = ? OR id_rekening_tujuan = ? ORDER BY tanggal DESC";
        
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, idRekening);
            ps.setInt(2, idRekening);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ModelTransaksi trx = new ModelTransaksi();
                    trx.setId(rs.getInt("id"));
                    trx.setJenis(rs.getString("jenis"));
                    trx.setJumlah(rs.getDouble("jumlah"));
                    trx.setTanggal(rs.getString("tanggal"));
                    trx.setRekAsal(rs.getInt("id_rekening_asal"));
                    trx.setRekTujuan(rs.getInt("id_rekening_tujuan"));
                    list.add(trx);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error getTransaksiByRekening: " + e.getMessage());
        }
        return list;
    }
}