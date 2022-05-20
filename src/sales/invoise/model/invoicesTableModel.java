
package sales.invoise.model;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;


public class invoicesTableModel extends AbstractTableModel{
    private ArrayList<invoiceData> invoices;
    private String[] Columns = {"Number","Date","Customer Name","Total"};

    public invoicesTableModel(ArrayList<invoiceData> invoices) {
        this.invoices = invoices;
    }

    @Override
    public int getRowCount() {
        return invoices.size();
    }

    @Override
    public int getColumnCount() {
        return Columns.length;
    }

    @Override
    public String getColumnName(int column) {
        return Columns[column]; 
    }
    
    

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        invoiceData invoice = invoices.get(rowIndex);
        
        switch (columnIndex){
            case 0 : return invoice.getNumber();
            case 1 : return invoice.getDate();
            case 2 : return invoice.getCustomer();
            case 3 : return invoice.getInvoiceTotal();
            
            default : return "";
    }
    }
    
    
}
