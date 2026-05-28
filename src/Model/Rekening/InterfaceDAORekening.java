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
}