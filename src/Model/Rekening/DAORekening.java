package Model.Rekening;

import Model.Connector;
import Model.Nasabah.ModelNasabah;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DAORekening implements InterfaceDAORekening {

    private Connection connection;

    public DAORekening() {
        // Mengambil koneksi database terpusat
        this.connection = Connector.Connect();
    }

    @Override
    public ModelRekening loginNasabah(String noRek, String pin) {
        ModelRekening rek = null;
        String query = "SELECT * FROM rekening WHERE no_rekening = ? AND pin = ?";
        
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, noRek);
            ps.setString(2, pin);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    rek = new ModelRekening();
                    rek.setId(rs.getInt("id"));
                    rek.setNoRek(rs.getString("no_rekening"));
                    rek.setSaldo(rs.getDouble("saldo"));
                    rek.setJenis(rs.getString("jenis"));
                    rek.setIdNasabah(rs.getInt("id_nasabah"));
                    rek.setPin(rs.getString("pin"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error pada loginNasabah: " + e.getMessage());
        }
        return rek; // Return objek rekening jika PIN benar, return null jika salah
    }

    @Override
    public void tambahRekening(ModelRekening rek) {
        String query = "INSERT INTO rekening (no_rekening, saldo, jenis, id_nasabah, pin) VALUES (?, ?, ?, ?, ?)";
        
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, rek.getNoRek());
            ps.setDouble(2, rek.getSaldo());
            ps.setString(3, rek.getJenis());
            ps.setInt(4, rek.getIdNasabah());
            ps.setString(5, rek.getPin()); // Menyimpan PIN awal nasabah
            
            ps.executeUpdate();
            System.out.println("Rekening baru berhasil dibuat oleh Admin!");
        } catch (SQLException e) {
            System.out.println("Error saat tambahRekening: " + e.getMessage());
        }
    }

    @Override
    public ModelRekening cariRekening(String noRek) {
        ModelRekening rek = null;
        String query = "SELECT * FROM rekening WHERE no_rekening = ?";
        
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, noRek);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    rek = new ModelRekening();
                    rek.setId(rs.getInt("id"));
                    rek.setNoRek(rs.getString("no_rekening"));
                    rek.setSaldo(rs.getDouble("saldo"));
                    rek.setJenis(rs.getString("jenis"));
                    rek.setIdNasabah(rs.getInt("id_nasabah"));
                    rek.setPin(rs.getString("pin"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error saat cariRekening: " + e.getMessage());
        }
        return rek; // Dipakai Controller untuk cek: kalau null berarti rekening tidak ada
    }

    @Override
    public void updateSaldo(String noRek, double saldoBaru) {
        String query = "UPDATE rekening SET saldo = ? WHERE no_rekening = ?";
        
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setDouble(1, saldoBaru);
            ps.setString(2, noRek);
            
            ps.executeUpdate();
            System.out.println("Saldo rekening " + noRek + " berhasil diperbarui!");
        } catch (SQLException e) {
            System.out.println("Error saat updateSaldo: " + e.getMessage());
        }
    }

    
    @Override
    public List<ModelRekening> getAllRekening() {
        List<ModelRekening> listRekening = null;
        
        try{
            listRekening = new ArrayList<>();
            Statement stmt = Connector.Connect().createStatement();
            String query = "SELECT * FROM nasabah;";
            ResultSet rs = stmt.executeQuery(query);
            
            while(rs.next()){
                ModelRekening rk = new ModelRekening();
                rk.setId(rs.getInt("id"));
                rk.setNoRek(rs.getString("no_rekening"));
                rk.setPin(rs.getString("pin"));
                rk.setSaldo(rs.getDouble("saldo"));
                rk.setJenis(rs.getString("jenis"));
                rk.setIdNasabah(rs.getInt("id_nasabah"));
                listRekening.add(rk);
            }
            stmt.close();
        }catch(SQLException e){
            System.out.println("Error: " + e.getLocalizedMessage());
        }
        return listRekening;
    }

    @Override
    public List<ModelRekening> getRekeningByNasabah(int idNasabah) {
        List<ModelRekening> listRekening = null;
        
        try{
            listRekening = new ArrayList<>();
            String query = "SELECT * FROM rekening WHERE id_nasabah=?;";
            PreparedStatement ps;
            ps = Connector.Connect().prepareStatement(query);
            ps.setInt(1, idNasabah);
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                ModelRekening rk = new ModelRekening();
                rk.setId(rs.getInt("id"));
                rk.setNoRek(rs.getString("no_rekening"));
                rk.setPin(rs.getString("pin"));
                rk.setSaldo(rs.getDouble("saldo"));
                rk.setJenis(rs.getString("jenis"));
                rk.setIdNasabah(rs.getInt("id_nasabah"));
                listRekening.add(rk);
            }
        }catch(SQLException e){
            System.out.println("Error: " + e.getLocalizedMessage());
        }
        return listRekening;
    }

    @Override
    public void hapusRekening(int id) {
        try{
            String query = "DELETE FROM rekening WHERE id=?;";
            PreparedStatement ps;
            ps = Connector.Connect().prepareStatement(query);
            ps.setInt(1, id);
            ps.executeUpdate();
            ps.close();
        }catch(SQLException e){
            System.out.println("Gagal Menghapus Rekening: " + e.getLocalizedMessage());
        }
    }

    @Override
    public boolean editPin(String noRek, String pinLama, String pinBaru) {
        try{
            String query = "SELECT * FROM rekening WHERE no_rekening=? AND pin=?;";
            PreparedStatement ps;
            ps = Connector.Connect().prepareStatement(query);
            ps.setString(1, noRek);
            ps.setString(2, pinLama);
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()){
                String qUpdate = "UPDATE rekening SET pin=? WHERE no_rekening=?;";
                ps = Connector.Connect().prepareStatement(qUpdate);
                ps.setString(1, pinBaru);
                ps.setString(2, noRek);
                
                ps.executeUpdate();
                ps.close();
                return true;
            }else{
                return false;
            }
            
            
        }catch(SQLException e){
            System.out.println("Gagal Mengubah Pin Rekening: " + e.getLocalizedMessage());
        }
        
        return false;
    }


}