
package sales.invoise.model;


public class invoiceLine {
    private String Item;
    private double Price;
    private int Count;
    private invoiceData Invoice;

    public invoiceLine() {
    }

    public invoiceLine(String Item, double Price, int Count, invoiceData Invoice) {
        this.Item = Item;
        this.Price = Price;
        this.Count = Count;
        this.Invoice = Invoice;
    }
    
    public double getLineTotal(){
        return Price*Count;
    }

    public int getCount() {
        return Count;
    }

    public void setCount(int Count) {
        this.Count = Count;
    }

    public String getItem() {
        return Item;
    }

    public void setItem(String Item) {
        this.Item = Item;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double Price) {
        this.Price = Price;
    }

    public invoiceData getInvoice() {
        return Invoice;
    }
    
    public String getAsCSV(){
        return Invoice.getNumber() + "," + Item + "," + Price + "," + Count ;
    }
}
