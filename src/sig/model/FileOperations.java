package sig.model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFileChooser;
import sig.view.UI;
import javax.swing.JOptionPane;
import java.util.List;
import java.util.Date;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import sig.view.HeaderFrameView;
import sig.view.LinesFrameView;





 public class FileOperations implements ActionListener{
         private UI _UIFrame;
         private HeaderFrameView _InvoiceheaderPopup;
         private LinesFrameView _InvoiceLinePopup;

    public FileOperations(UI frame) {
         
        this._UIFrame = frame;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(e.getActionCommand());
        switch(e.getActionCommand()){
            
            case "Create New Invoice" : 
            
                CreateNewInvoice();
            break;
    
          case "Delete Invoice" : 
         
            DeleteInvoice();
              break;
    
              case "Save Change" : 
         
                 SaveChanges();
              break;
    
                 case "Cancel" : 
           
                    cancel();
                  break;
             case "newInvoiceSave":
                newInvoiceDialogOK();
                break;

            case "newInvoiceCancel":
                newInvoiceDialogCancel();
                break;

            case "newLineCancel":
                newLineDialogCancel();
                break;

            case "newLineSave":
                newLineDialogOK();
                break;
    
              case "load file" : 
          
                     loadfile();
                  break;
    
                 case "save file" : 
                  System.out.println("Done");
                     savefile();
                    break;

       
}
    }
 
 
    
      private void loadfile() {
        JFileChooser _ChooserFile = new JFileChooser();
        try {
            int _res = _ChooserFile.showOpenDialog(_UIFrame);
            if (_res == JFileChooser.APPROVE_OPTION) {
                File _InvoiceHeaderFile = _ChooserFile.getSelectedFile();
                Path _InvoiceHeaderPath = Paths.get(_InvoiceHeaderFile.getAbsolutePath());
                if(!_InvoiceHeaderFile.getAbsolutePath().endsWith(".csv")){
                     JOptionPane.showMessageDialog(_UIFrame, "Wrong file format", "Error", JOptionPane.ERROR_MESSAGE);
                     return;
                }
                   
                List<String> headerLines = Files.readAllLines(_InvoiceHeaderPath);
                ArrayList<HeaderModel> Headers = new ArrayList<>();
                for (String headerLine : headerLines) {
                    String[] Strings = headerLine.split(",");
                    String StringNum1 = Strings[0];
                    String StringNum2 = Strings[1];
                    String StringNum3 = Strings[2];
                    int code = Integer.parseInt(StringNum1);
                   try{
                       SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                
                         Date invoiceDate = dateFormat.parse(StringNum2);     
                         
                         HeaderModel header = new HeaderModel(code, StringNum3, invoiceDate);
                         Headers.add(header);
                   }
                   catch(Exception e ){
                       System.out.println(e);
                            
                        JOptionPane.showMessageDialog(_UIFrame, "Wrong date format", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                   }
                   
                  
                }
                _UIFrame.setInvoicesArray(Headers);

                _res = _ChooserFile.showOpenDialog(_UIFrame);
                if (_res == JFileChooser.APPROVE_OPTION) {
                    File lineFile = _ChooserFile.getSelectedFile();
                    Path linePath = Paths.get(lineFile.getAbsolutePath());
                    
                    if(!_InvoiceHeaderFile.getAbsolutePath().endsWith(".csv")){
                     JOptionPane.showMessageDialog(_UIFrame, "Wrong file format", "Error", JOptionPane.ERROR_MESSAGE);
                     return;
                }
                    
                    List<String> lineLines = Files.readAllLines(linePath);
                    ArrayList<LineModel> invoiceLines = new ArrayList<>();
                    for (String line : lineLines) {
                        String[] Strings = line.split(",");
                        String arr1 = Strings[0];    // invoice num (int)
                        String arr2 = Strings[1];    // item name   (String)
                        String arr3 = Strings[2];    // price       (double)
                        String arr4 = Strings[3];    // count       (int)
                        int invCode = Integer.parseInt(arr1);
                        double price = Double.parseDouble(arr3);
                        int count = Integer.parseInt(arr4);
                        HeaderModel inv = _UIFrame.getInvObject(invCode);
                        LineModel invoiceLine = new LineModel(arr2, price, count, inv);
                        inv.getLines().add(invoiceLine);
                    }
                }
                InoviceHeaderTable headerTable = new InoviceHeaderTable(Headers);
                _UIFrame.setInvoiceheaderTable(headerTable);
                _UIFrame.getheaderTable().setModel(headerTable);
                System.out.println("files read");
            }

        } 
        
      
      
        catch (Exception ex) {
            JOptionPane.showMessageDialog(_UIFrame, "File not found", "Error", JOptionPane.ERROR_MESSAGE);
        } 
    }

    private void CreateNewInvoice() {
        _InvoiceheaderPopup = new HeaderFrameView(_UIFrame);
        _InvoiceheaderPopup.setVisible(true);
    }

    private void DeleteInvoice() {
        int selectedInvoiceIndex = _UIFrame.getheaderTable().getSelectedRow();
        if (selectedInvoiceIndex != -1) {
            _UIFrame.getInvoicesArray().remove(selectedInvoiceIndex);
            _UIFrame.getInvoiceheaderTable().fireTableDataChanged();

            _UIFrame.getlineTable().setModel(new InvoiceLinesTable(null));
            _UIFrame.setLinesArray(null);
            _UIFrame.getCustNameLbl().setText("");
            _UIFrame.getInvNumLbl().setText("");
            _UIFrame.getInvTotalIbl().setText("");
            _UIFrame.getDateLbl().setText("");
        }
    }

    private void SaveChanges() {
        _InvoiceLinePopup = new LinesFrameView(_UIFrame);
        _InvoiceLinePopup.setVisible(true);
    }

    private void cancel() {
        int selectedLineIndex = _UIFrame.getlineTable().getSelectedRow();
        int selectedInvoiceIndex = _UIFrame.getheaderTable().getSelectedRow();
        if (selectedLineIndex != -1) {
            _UIFrame.getLinesArray().remove(selectedLineIndex);
            InvoiceLinesTable lineTableModel = (InvoiceLinesTable) _UIFrame.getlineTable().getModel();
            lineTableModel.fireTableDataChanged();
            _UIFrame.getInvTotalIbl().setText("" + _UIFrame.getInvoicesArray().get(selectedInvoiceIndex).getItemTotal());
            _UIFrame.getInvoiceheaderTable().fireTableDataChanged();
            _UIFrame.getheaderTable().setRowSelectionInterval(selectedInvoiceIndex, selectedInvoiceIndex);
        }
    }

    private void savefile() {
        ArrayList<HeaderModel> invoicesArray = _UIFrame.getInvoicesArray();
        JFileChooser fc = new JFileChooser();
        try {
            int _res = fc.showSaveDialog(_UIFrame);
            if (_res == JFileChooser.APPROVE_OPTION) {
                File _InvoiceHeaderFile = fc.getSelectedFile();
                if(!_InvoiceHeaderFile.getAbsolutePath().endsWith(".csv")){
                     JOptionPane.showMessageDialog(_UIFrame, "Wrong file format", "Error", JOptionPane.ERROR_MESSAGE);
                     return;
                }
                   
                FileWriter hfw = new FileWriter(_InvoiceHeaderFile);
                String headers = "";
                String lines = "";
                for (HeaderModel invoice : invoicesArray) {
                    headers += invoice.toString();
                    headers += "\n";
                    for (LineModel line : invoice.getLines()) {
                        lines += line.toString();
                        lines += "\n";
                    }
                }
                
                headers = headers.substring(0, headers.length()-1);
                lines = lines.substring(0, lines.length()-1);
                _res = fc.showSaveDialog(_UIFrame);
                File lineFile = fc.getSelectedFile();
                 if(!lineFile.getAbsolutePath().endsWith(".csv")){
                     JOptionPane.showMessageDialog(_UIFrame, "Wrong file format", "Error", JOptionPane.ERROR_MESSAGE);
                     return;
                }
                FileWriter lfw = new FileWriter(lineFile);
                hfw.write(headers);
                lfw.write(lines);
                hfw.close();
                lfw.close();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(_UIFrame, "Folder/File path is not found", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void  newLineDialogCancel() {
        _InvoiceLinePopup.setVisible(false);
        _InvoiceLinePopup.dispose();
        _InvoiceLinePopup = null;
    }
  

    private void newInvoiceDialogOK() {
        _InvoiceheaderPopup.setVisible(false);

        String custName = _InvoiceheaderPopup.getCustomerNameTextField().getText();
        String str = _InvoiceheaderPopup.getDateTextField().getText();
        Date d = new Date();
        
 
        try {
             SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
             d = dateFormat.parse(str);
             int invNum = 0;
        for (HeaderModel inv : _UIFrame.getInvoicesArray()) {
            if (inv.getNum() > invNum) {
                invNum = inv.getNum();
            }
        }
        invNum++;
        HeaderModel newInv = new HeaderModel(invNum, custName, d);
        _UIFrame.getInvoicesArray().add(newInv);
       _UIFrame.getInvoiceheaderTable().fireTableDataChanged();
       _InvoiceheaderPopup.dispose();
        _InvoiceheaderPopup = null;
        } 
        catch (ParseException ex) {
            JOptionPane.showMessageDialog(_UIFrame, "Wrong date format", "Invalid date format", JOptionPane.ERROR_MESSAGE);
        }

        
    }

    private void newInvoiceDialogCancel() {
        _InvoiceheaderPopup.setVisible(false);
        _InvoiceheaderPopup.dispose();
        _InvoiceheaderPopup = null;
    }

    private void newLineDialogOK() {
        _InvoiceLinePopup.setVisible(false);

        String name = _InvoiceLinePopup.getItemNameTextField().getText();
        String str1 = _InvoiceLinePopup.getItemCountTextField().getText();
        String StringNum2 = _InvoiceLinePopup.getItemPriceTextField().getText();
        int count = 1;
        double price = 1;
        try {
            count = Integer.parseInt(str1);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(_UIFrame, "Cannot convert number", "Invalid number format", JOptionPane.ERROR_MESSAGE);
        }

        try {
            price = Double.parseDouble(StringNum2);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(_UIFrame, "Cannot convert price", "Invalid number format", JOptionPane.ERROR_MESSAGE);
        }
        int selectedInvHeader = _UIFrame.getheaderTable().getSelectedRow();
                 if (selectedInvHeader != -1) {
            HeaderModel invHeader = _UIFrame.getInvoicesArray().get(selectedInvHeader);
            LineModel line = new LineModel(name, price, count, invHeader);
            //invHeader.getLines().add(line);
            _UIFrame.getLinesArray().add(line);
            InvoiceLinesTable lineTable = (InvoiceLinesTable) _UIFrame.getlineTable().getModel();
            lineTable.fireTableDataChanged();
            _UIFrame.getInvoiceheaderTable().fireTableDataChanged();
        }
        _UIFrame.getheaderTable().setRowSelectionInterval(selectedInvHeader, selectedInvHeader);
        _InvoiceLinePopup.dispose();
        _InvoiceLinePopup = null;
    }

    

  

}