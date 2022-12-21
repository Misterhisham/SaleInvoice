package sig.model;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class InvoiceLinesTable extends AbstractTableModel {

    private ArrayList<LineModel> _Lines;
    private String[] _Columns = {"Item Name", "Unit Price", "Count", "Line Total"};

    public InvoiceLinesTable(ArrayList<LineModel> linesArray) {
        this._Lines = linesArray;
    }

    @Override
    public int getRowCount() {
        return _Lines == null ? 0 : _Lines.size();
    }

    @Override
    public int getColumnCount() {
        return _Columns.length;
    }

    @Override
    public Object getValueAt(int getRow, int getCol) {
        if (_Lines == null) {
            return "";
        } else {
            LineModel line = _Lines.get(getRow);
            switch (getCol) {
                case 0:
                    return line.getItem();
                case 1:
                    return line.getPrice();
                case 2:
                    return line.getCount();
                case 3:
                    return line.getLineTotal();
                default:
                    return "";
            }
        }
    }

    @Override
    public String getColumnName(int col) {
        return _Columns[col];
    }

}


