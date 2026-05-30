/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.Nasabah;

import Model.Connector;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mzida
 */
public class DAONasabah implements InterfaceDAONasabah{

    @Override
    public List<ModelNasabah> getAllNasabah() {
        List<ModelNasabah> listNasabah = null;
        
        try{
            listNasabah = new ArrayList<>();
            Statement stmt = Connector.Connect().createStatement();
            String query = "SELECT * FROM nasabah;";
            ResultSet rs = stmt.executeQuery(query);
            
            while(rs.next()){
                ModelNasabah ns = new ModelNasabah();
                ns.setId(rs.getInt("id"));
                ns.setNama(rs.getString("nama"));
                ns.setAlamat(rs.getString("alamat"));
                ns.setNoTelp(rs.getString("no_telp"));
                listNasabah.add(ns);
            }
            stmt.close();
        }catch(SQLException e){
            System.out.println("Error: " + e.getLocalizedMessage());
        }
        return listNasabah;
    }

    @Override
    public ModelNasabah getNasabahById(int id) {
        ModelNasabah ns = null;
        
        try{
            String query = "SELECT * FROM nasabah WHERE id=?;";
            PreparedStatement ps;
            ps = Connector.Connect().prepareStatement(query);
            ps.setInt(1, id);
            
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()){
                ns = new ModelNasabah();
                ns.setId(rs.getInt("id"));
                ns.setNama(rs.getString("nama"));
                ns.setAlamat(rs.getString("alamat"));
                ns.setNoTelp(rs.getString("no_telp"));
            }
        }catch(SQLException e){
            System.out.println("Error: " + e.getLocalizedMessage());
        }
        return ns;
    }

    @Override
    public void tambahNasabah(ModelNasabah nasabah) {
        try{
            String query = "INSERT INTO nasabah (nama, alamat, no_telp) VALUES (?,?,?);";
            
            PreparedStatement ps;
            ps = Connector.Connect().prepareStatement(query);
            ps.setString(1, nasabah.getNama());
            ps.setString(2, nasabah.getAlamat());
            ps.setString(3, nasabah.getNoTelp());
            
            ps.executeUpdate();
            ps.close();
        }catch(SQLException e){
            System.out.println("Gagal Menambahkan Nasabah: " + e.getLocalizedMessage());
        }
    }

    @Override
    public void editNasabah(ModelNasabah nasabah) {
        try{
            String query = "UPDATE nasabah SET nama=?, alamat=?, no_telp=? WHERE id=?;";
            PreparedStatement ps;
            ps = Connector.Connect().prepareStatement(query);
            ps.setString(1, nasabah.getNama());
            ps.setString(2, nasabah.getAlamat());
            ps.setString(3, nasabah.getNoTelp());
            ps.setInt(4, nasabah.getId());
            
            ps.executeUpdate();
            ps.close();
        }catch(SQLException e){
            System.out.println("Gagal Mengupdate Data Nasabah: " + e.getLocalizedMessage());
        }
    }

    @Override
    public void hapusNasabah(int id) {
        try{
            String query = "DELETE FROM nasabah WHERE id=?;";
            PreparedStatement ps;
            ps = Connector.Connect().prepareStatement(query);
            ps.setInt(1, id);
            ps.executeUpdate();
            ps.close();
        }catch(SQLException e){
            System.out.println("Gagal Menghapus Data Nasabah: " + e.getLocalizedMessage());
        }
    }

    @Override
    public boolean cekDuplicateNoTelp(String noTelp) {
        try{
            String query = "SELECT * FROM nasabah WHERE no_telp=?;";
            PreparedStatement ps = Connector.Connect().prepareStatement(query);
            ps.setString(1, noTelp);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return true;
            }
        }catch(SQLException e){
            System.out.println("Gagal Mengecek No Telp: " + e.getLocalizedMessage());
        }
        return false;
    }
    
    public boolean cekDuplicateNoTelpEdit(String noTelp, int id) {
        try{
            String query = "SELECT * FROM nasabah WHERE no_telp=? AND id<>?;";
            PreparedStatement ps = Connector.Connect().prepareStatement(query);
            ps.setString(1, noTelp);
            ps.setInt(2, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return true;
            }
        }catch(SQLException e){
            System.out.println("Gagal Mengecek No Telp: " + e.getLocalizedMessage());
        }
        return false;
    }
    
}
