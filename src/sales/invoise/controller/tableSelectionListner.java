
package sales.invoise.controller;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import sales.invoise.model.invoiceData;
import sales.invoise.model.linesTableModel;
import sales.invoise.view.SalesinvoiseFrame;

public class tableSelectionListner implements ListSelectionListener {
    private SalesinvoiseFrame Frame;

    public tableSelectionListner (SalesinvoiseFrame Frame){
        this.Frame = Frame;
    }
    
    @Override
    public void valueChanged(ListSelectionEvent e) {
        int selectedIndex = Frame.getInvoiceTable().getSelectedRow();
        if (selectedIndex != -1){
        invoiceData currentInvoice = Frame.getInvoices().get(selectedIndex);
        Frame.getInvoiceNumberLabel().setText(""+currentInvoice.getNumber());
        Frame.getInvoiceDateLabel().setText(currentInvoice.getDate());
        Frame.getCustomerNameLabel().setText(currentInvoice.getCustomer());
        Frame.getInvoiceTotalLabel().setText(""+currentInvoice.getInvoiceTotal());
        linesTableModel LinesTableModel = new linesTableModel(currentInvoice.getLines());
        Frame.getLineTable().setModel(LinesTableModel);
        LinesTableModel.fireTableDataChanged();
    }}
    
}
