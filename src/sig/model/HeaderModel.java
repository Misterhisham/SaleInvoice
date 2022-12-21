
package sig.model;

import java.util.ArrayList;
import java.util.Date;

        public class HeaderModel {
                private int _InvoiceNumber;
                private String _CustomerName;
                private Date _InvoiceDate;
                private ArrayList<LineModel> _Lines;
               

public  HeaderModel() {

}

    public HeaderModel(int num, String customer, Date date) {
        this._InvoiceNumber = num;
        this._CustomerName = customer;
        this._InvoiceDate = date;
        }
    public int getNum() {
        return _InvoiceNumber;
    }

    public void setNum(int num) {
        this._InvoiceNumber = num;
    }

    public String getCustomer() {
        return _CustomerName;
    }

    public void setCustomer(String customer) {
        this._CustomerName = customer;
    }

    public Date getDate() {
        return _InvoiceDate;
    }

    public void setDate(Date date) {
        this._InvoiceDate = date;
    }
    
  public ArrayList<LineModel> getLines() {
        if (_Lines != null) {
            return _Lines;
            
        }
        return _Lines = new ArrayList<>();
    }

    public void setLines(ArrayList<LineModel> lines) {
        this._Lines = lines;
    }
    
    
    
    public double getItemTotal(){
        double sum = 0.0;
        int i =0;
        while(i < getLines().size() ){
            sum += getLines().get(i).getLineTotal();
            i++;
       }
        
       
        return sum;
    }
   
        }

  


    
