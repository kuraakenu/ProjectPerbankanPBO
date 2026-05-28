package Model.Transaksi;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class ModelTableTransaksi extends AbstractTableModel {

    private List<ModelTransaksi> listTransaksi = new ArrayList<>();
    private final String[] columnNames = {"ID", "Jenis", "Jumlah", "Tanggal", "Rek. Asal", "Rek. Tujuan"};

    public void setList(List<ModelTransaksi> listTransaksi) {
        this.listTransaksi = listTransaksi;
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return listTransaksi.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ModelTransaksi trx = listTransaksi.get(rowIndex);
        switch (columnIndex) {
            case 0: return trx.getId();
            case 1: return trx.getJenis();
            case 2: return trx.getJumlah();
            case 3: return trx.getTanggal();
            case 4: return trx.getRekAsal() == 0 ? "-" : trx.getRekAsal(); // jika 0 (setor tunai) tampilkan "-"
            case 5: return trx.getRekTujuan() == 0 ? "-" : trx.getRekTujuan(); // jika 0 (tarik tunai) tampilkan "-"
            default: return null;
        }
    }
}