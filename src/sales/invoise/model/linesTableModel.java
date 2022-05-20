
package sales.invoise.model;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;


public class linesTableModel extends AbstractTableModel{
    private ArrayList<invoiceLine> lines;
    private String[] Columns = {"Number","Item Name","Item Price","Count","Item Total"};

    public linesTableModel(ArrayList<invoiceLine> lines) {
        this.lines = lines;
    }
    
    @Override
    public int getRowCount() {
        return lines.size();
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
        invoiceLine line = lines.get(rowIndex);
        
        switch (columnIndex){
            case 0 : return line.getInvoice().getNumber();
            case 1 : return line.getItem();
            case 2 : return line.getPrice();
            case 3 : return line.getCount();
            case 4 : return line.getLineTotal();
            
            default : return "";
        }
    }
    
}
