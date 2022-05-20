
package sales.invoise.model;

import java.util.ArrayList;


public class invoiceData {
    private int Number;
    private String Date;
    private String Customer;
    private ArrayList<invoiceLine> Lines;

    public invoiceData() {
    }

    public invoiceData(int Number, String Date, String Customer) {
        this.Number = Number;
        this.Date = Date;
        this.Customer = Customer;
    }
    
    public double getInvoiceTotal(){
        double total = 0.0;
        for(invoiceLine line : getLines()){
            total += line.getLineTotal();
        }
        return total;
    }

    public ArrayList<invoiceLine> getLines() {
        if (Lines == null){
            Lines = new ArrayList<>();
        }
        return Lines;
    }

    public String getCustomer() {
        return Customer;
    }

    public void setCustomer(String Customer) {
        this.Customer = Customer;
    }

    public int getNumber() {
        return Number;
    }

    public void setNumber(int Number) {
        this.Number = Number;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }
    
    public String getAsCSV(){
     return Number + "," + Date + "," + Customer;
            
    }
    
}
