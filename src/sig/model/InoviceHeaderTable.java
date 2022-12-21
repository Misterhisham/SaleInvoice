
package sig.model;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import sig.view.UI;


public class InoviceHeaderTable extends AbstractTableModel {

    private ArrayList<HeaderModel> _InvoiceList;
    private String[] _Columns = {"Invoice Num", "Invoice Date", "Customer Name", "Invoice Total"};
    
    public InoviceHeaderTable(ArrayList<HeaderModel> _InvoiceList) {
        this._InvoiceList = _InvoiceList;
    }

    @Override
    public int getRowCount() {
        return _InvoiceList.size();
    }

    @Override
    public int getColumnCount() {
        return _Columns.length;
    }

    @Override
    public Object getValueAt(int getRow, int getCol) {
        HeaderModel inv = _InvoiceList.get(getRow);
        switch (getCol) {
        case 0: 
            return
                    inv.getNum();
        case 1: 
            return 
                    UI.dateFormat.format(inv.getDate());
        case 2: 
            return 
                    inv.getCustomer();
        case 3:
            return
                    inv.getItemTotal();
        }
        return "";
    }

    @Override
    public String getColumnName(int col) {
        return _Columns[col];
    }
}
