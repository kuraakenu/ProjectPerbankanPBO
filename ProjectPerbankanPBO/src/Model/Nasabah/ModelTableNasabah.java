package Model.Nasabah;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class ModelTableNasabah extends AbstractTableModel {

    private List<ModelNasabah> listNasabah = new ArrayList<>();
    private final String[] columnNames = {"ID", "Nama", "Alamat", "No. Telepon"};

    public void setList(List<ModelNasabah> listNasabah) {
        this.listNasabah = listNasabah;
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return listNasabah.size();
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
        ModelNasabah nasabah = listNasabah.get(rowIndex);
        switch (columnIndex) {
            case 0: return nasabah.getId();
            case 1: return nasabah.getNama();
            case 2: return nasabah.getAlamat();
            case 3: return nasabah.getNoTelp();
            default: return null;
        }
    }
}