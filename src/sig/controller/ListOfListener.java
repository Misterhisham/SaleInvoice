
package sig.controller;
    
import sig.model.HeaderModel;
import sig.model.LineModel;
import sig.model.InvoiceLinesTable;
import sig.view.UI;
import java.util.ArrayList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ListOfListener implements ListSelectionListener {

    private  UI UIFrame;

    public ListOfListener(UI frame) {
        this.UIFrame = frame;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        int selectedInvIndex = UIFrame.getheaderTable().getSelectedRow();
        System.out.println("Invoice selected: " + selectedInvIndex);
        if (selectedInvIndex != -1) {
            HeaderModel selectedInv = UIFrame.getInvoicesArray().get(selectedInvIndex);
            ArrayList<LineModel> lines = selectedInv.getLines();
            InvoiceLinesTable lineTable = new InvoiceLinesTable(lines);
            UIFrame.setLinesArray(lines);
            UIFrame.getlineTable().setModel(lineTable);
            UIFrame.getCustNameLbl().setText(selectedInv.getCustomer());
            UIFrame.getInvNumLbl().setText("" + selectedInv.getNum());
            UIFrame.getInvTotalIbl().setText("" + selectedInv.getItemTotal());
            UIFrame.getDateLbl().setText(UI.dateFormat.format(selectedInv.getDate()));
        }
    }

}

    

