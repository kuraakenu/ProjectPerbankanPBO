/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.Nasabah.*;
import View.ViewFormNasabah;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author mzida
 */
public class NasabahController {
    ViewFormNasabah form;
    InterfaceDAONasabah daoNasabah;
    List<ModelNasabah> listNasabah;
    
    public NasabahController(ViewFormNasabah form){
        this.form = form;
        this.daoNasabah = new DAONasabah();
        
        showAllNasabah();
    }

    private void showAllNasabah() {
        listNasabah = daoNasabah.getAllNasabah();
        ModelTableNasabah table = new ModelTableNasabah();
        table.setList(listNasabah);
        form.getTableNasabah().setModel(table);
    }
    
    public void insertNasabah(){
        try{
            ModelNasabah newNs = new ModelNasabah();
            
            String nama = form.getInputNama();
            String alamat = form.getInputAlamat();
            String notelp = form.getInputNoHP();
            
            if("".equals(nama) || "".equals(alamat) || "".equals(notelp)){
                throw new Exception("Field Tidak Boleh Kosong!");
            }
            
            if(daoNasabah.cekDuplicateNoTelp(notelp)){
                throw new Exception("No HP sudah Terdaftar!");
            }
            
            newNs.setNama(nama);
            newNs.setAlamat(alamat);
            newNs.setNoTelp(notelp);
            
            daoNasabah.tambahNasabah(newNs);
            JOptionPane.showMessageDialog(null, "Nasabah baru berhasil ditambahkan.");
            showAllNasabah();
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }
    
    public void editNasabah(){
        try{
            if(form.getSelectedId() == -1){
                throw new Exception("Pilih baris yang ingin diedit terlebih dahulu!");
            }
            
            ModelNasabah editNs = new ModelNasabah();
            
            String nama = form.getInputNama();
            String alamat = form.getInputAlamat();
            String notelp = form.getInputNoHP();
            
            if("".equals(nama) || "".equals(alamat) || "".equals(notelp)){
                throw new Exception("Field Tidak Boleh Kosong!");
            }
            
            editNs.setId(form.getSelectedId());
            editNs.setNama(nama);
            editNs.setAlamat(alamat);
            editNs.setNoTelp(notelp);
            
            // ini dia ngecek notelpnya si objek, lalu sama idnya juga, kalau idnya ternyata pemilik no telp itu, ya tidak throw exception
            if(daoNasabah.cekDuplicateNoTelpEdit(editNs.getNoTelp(), editNs.getId())){
                throw new Exception("No HP sudah Terdaftar!");
            }
            
            daoNasabah.editNasabah(editNs);
            JOptionPane.showMessageDialog(null, "Nasabah baru berhasil diedit.");
            showAllNasabah();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }
    
    public void hapusNasabah(Integer baris){
        
        if(baris == null){
            JOptionPane.showMessageDialog(null, "Pilih baris yang ingin dihapus terlebih dahulu!");
            return;
        }

        Integer id = (int) form.getTableNasabah().getValueAt(baris, 0);
        String nama = form.getTableNasabah().getValueAt(baris, 1).toString();
        
        int input = JOptionPane.showConfirmDialog(
                null,
                "Hapus " + nama + "?" + "\n" +
                "SELURUH REKENING AKAN IKUT TERHAPUS!",
                "Hapus Nasabah",
                JOptionPane.YES_NO_OPTION
        );
        
        if(input == 0){
            daoNasabah.hapusNasabah(id);
            JOptionPane.showMessageDialog(null, "Berhasil menghapus nasabah beserta seluruh rekeningnya!");
            showAllNasabah();
        }
        
    }
}
