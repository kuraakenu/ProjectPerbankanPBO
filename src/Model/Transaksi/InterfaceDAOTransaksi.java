package Model.Transaksi;

import java.util.List;

public interface InterfaceDAOTransaksi {
    //Kontrak u/ catat transaksi baru (TF Setor Tarik)
    public boolean tambahTransaksi(ModelTransaksi trx);
    
    //kontrak utk Admin : Melihat SEMUA transaksi dari seluruh nasabah
    public List<ModelTransaksi> getAllYtansaksi();
    
    //Kontrak utk Nasabah : Hanya melihat transaksi milik ID rekening pribadi
    public List<ModelTransaksi> getTransaksiByRekening(int idRekening);
}
