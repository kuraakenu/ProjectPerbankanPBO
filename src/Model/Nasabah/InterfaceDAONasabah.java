/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.Nasabah;

import java.util.List;

/**
 *
 * @author mzida
 */
public interface InterfaceDAONasabah {
    List<ModelNasabah> getAllNasabah();
    ModelNasabah getNasabahById(int id);
    void tambahNasabah(ModelNasabah nasabah);
    void editNasabah(ModelNasabah nasabah);
    void hapusNasabah(int id);
    
    // yang ini buat controller di method insert
    boolean cekDuplicateNoTelp(String noTelp);
    // yang ini buat controller di method edit
    boolean cekDuplicateNoTelpEdit(String noTelp, int id);
}
