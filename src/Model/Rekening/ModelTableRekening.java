package Model.Rekening;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class ModelTableRekening extends AbstractTableModel {

    // Kita buat list untuk menampung data rekening yang akan ditampilkan di tabel
    private List<ModelRekening> listRekening = new ArrayList<>();
    
    // Nama-nama kolom yang akan muncul di header tabel UI
    private final String[] columnNames = {"ID", "No. Rekening", "Jenis", "ID Nasabah", "Saldo"};

    public void setList(List<ModelRekening> listRekening) {
        this.listRekening = listRekening;
        fireTableDataChanged(); // Memberitahu tabel untuk refresh tampilan saat data berubah
    }

    @Override
    public int getRowCount() {
        return listRekening.size(); // Jumlah baris sesuai banyaknya data di list
    }

    @Override
    public int getColumnCount() {
        return columnNames.length; // Jumlah kolom sesuai array nama kolom
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column]; // Mengatur nama header kolom
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        // Mengambil 1 baris objek rekening berdasarkan indeks baris tabel
        ModelRekening rek = listRekening.get(rowIndex);
        
        // Memetakan data properti objek ke kolom tabel yang sesuai
        switch (columnIndex) {
            case 0: return rek.getId();
            case 1: return rek.getNoRek();
            case 2: return rek.getJenis();
            case 3: return rek.getIdNasabah();
            case 4: return rek.getSaldo();
            default: return null;
        }
    }
}