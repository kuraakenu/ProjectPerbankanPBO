package Model.Rekening;

import java.util.List;

public interface InterfaceDAORekening {
    // Kontrak untuk login nasabah menggunakan No Rekening dan PIN
    public ModelRekening loginNasabah(String noRek, String pin);
    
    // Kontrak untuk Admin membuat rekening baru
    public void tambahRekening(ModelRekening rek);
    
    // Kontrak untuk mencari rekening berdasarkan nomornya (dipakai saat transfer)
    public ModelRekening cariRekening(String noRek);
    
    // Kontrak untuk memperbarui saldo (dipakai saat setor/tarik/transfer)
    public void updateSaldo(String noRek, double saldoBaru);
    
    // Kontrak untuk menampilkan seluruh pemilik rekening di interface admin(user)
    List<ModelRekening> getAllRekening();
    
    // Kontrak untuk mendapatkan rekening berdasarkan id nasabah
    List<ModelRekening> getRekeningByNasabah(int idNasabah);
    
    // Kontrak untuk admin untuk menghapus rekening
    public void hapusRekening(int id);
    
    // Kontrak untuk nasabah/admin mengedit pin
    public boolean editPin(String noRek, String pinLama, String pinBaru);
}