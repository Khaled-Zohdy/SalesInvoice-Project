
package sales.invoise.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import sales.invoise.model.invoiceData;
import sales.invoise.model.invoiceLine;
import sales.invoise.model.invoicesTableModel;
import sales.invoise.model.linesTableModel;
import sales.invoise.view.SalesinvoiseFrame;
import sales.invoise.view.invoiceDialog;
import sales.invoise.view.lineDialog;



public class invoiceController implements ActionListener {
    
    private SalesinvoiseFrame Frame;
    private invoiceDialog invDialog;
    private lineDialog lineDialog;
    
    public invoiceController (SalesinvoiseFrame Frame){
        this.Frame = Frame;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String actioncommand = e.getActionCommand();
        System.err.println("Action : " + actioncommand);
        switch (actioncommand){
            case "Load File":
                loadFile();
                break;
                
            case "Save File":
                saveFile();
                break;
                
            case "Create New Invoice": 
                createNewInvoice();
                break;
                
            case "Delete Invoice": 
                deleteInvoice();
                break;
                
            case "Create New Item": 
                createNewItem();
                break;
            
            case "Delete Item":
                deleteItem();
                break;
                
            case "createInvoiceCancel":
                createInvoiceCancel();
                break;
            case "createInvoiceOK":
                createInvoiceOK();
                break;
            case "createLineOK":
                createLineOK();
                break;
            case "createLineCancel":
                createLineCancel();
                break;    
            
                
        }
            
    }

    private void loadFile() {
        JFileChooser fc = new JFileChooser();
        try{
        int Result = fc.showOpenDialog(Frame);
        if (Result == JFileChooser.APPROVE_OPTION){
            File headerFile = fc.getSelectedFile();
            Path headerPath = Paths.get(headerFile.getAbsolutePath());
            List<String> headerLines =Files.readAllLines(headerPath);
            ArrayList<invoiceData> invoicesArray = new ArrayList<>();
            for(String headerLine : headerLines){
                String[]headerDetails = headerLine.split(",");
                int invoiceNumber = Integer.parseInt(headerDetails[0]);
                String invoiceDate = headerDetails[1];
                String customerName = headerDetails[2];
                
                invoiceData Invoice = new invoiceData(invoiceNumber , invoiceDate, customerName);
                invoicesArray.add(Invoice);
            }
            Result = fc.showOpenDialog(Frame);
            if (Result == JFileChooser.APPROVE_OPTION){
                File lineFile = fc.getSelectedFile();
                Path linePath = Paths.get(lineFile.getAbsolutePath());
                List<String> lineLines = Files.readAllLines(linePath);
                for(String lineLine : lineLines){
                    String[]lineDetails = lineLine.split(",");
                    int invoiceNumber = Integer.parseInt(lineDetails[0]);
                    String itemName = lineDetails[1];
                    double itemPrice = Double.parseDouble(lineDetails[2]);
                    int count = Integer.parseInt(lineDetails[3]);
                    invoiceData inv = null;
                    for (invoiceData invoice : invoicesArray){
                        if(invoice.getNumber()== invoiceNumber){
                            inv = invoice;
                            break;
                        }
                    }
                    
                    invoiceLine line = new invoiceLine(itemName, itemPrice, count, inv);
                    inv.getLines().add(line);
                }
            }
            
            Frame.setInvoices(invoicesArray);
            invoicesTableModel InvoicesTableModel = new invoicesTableModel(invoicesArray);
            Frame.setInvoicesTableModel(InvoicesTableModel);
            Frame.getInvoiceTable().setModel(InvoicesTableModel);
            Frame.getInvoicesTableModel().fireTableDataChanged();
        }
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
        
    }

    private void saveFile() {
        ArrayList<invoiceData> Invoices = Frame.getInvoices();
        String Headers = "";
        String Lines = "";
        for (invoiceData Invoice : Invoices){
            String invoiceCSV = Invoice.getAsCSV();
            Headers += invoiceCSV;
            Headers += "\n";
            
            for (invoiceLine Line : Invoice.getLines()){
                String lineCSV = Line.getAsCSV();
                Lines += lineCSV;
                Lines += "\n";
            }
        }
        try {
        JFileChooser FC = new JFileChooser();
        int Result = FC.showSaveDialog(Frame);
        if (Result == JFileChooser.APPROVE_OPTION){
            File HeaderFile = FC.getSelectedFile();
            FileWriter HeadersFW = new FileWriter(HeaderFile);
            HeadersFW.write(Headers);
            HeadersFW.flush();
            HeadersFW.close();
            
            Result = FC.showSaveDialog(Frame);
            if (Result == JFileChooser.APPROVE_OPTION){
            File LineFile = FC.getSelectedFile();
            FileWriter LinesFW = new FileWriter(LineFile);
            LinesFW.write(Lines);
            LinesFW.flush();
            LinesFW.close();
            }
        }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            }
        
   
    }

    private void createNewInvoice() {
        invDialog = new invoiceDialog(Frame);
        invDialog.setVisible(true);
    }

    private void deleteInvoice() {
        int SelectedRow = Frame.getInvoiceTable().getSelectedRow();
        if (SelectedRow != -1){
            Frame.getInvoices().remove(SelectedRow);
            Frame.getInvoicesTableModel().fireTableDataChanged();
        }
        
    }

    private void createNewItem() {
        lineDialog = new lineDialog(Frame);
        lineDialog.setVisible(true);
    }

    private void deleteItem() {
        int SelectedInv = Frame.getInvoiceTable().getSelectedRow();
        int SelectedRow = Frame.getLineTable().getSelectedRow();
        if (SelectedInv != -1 && SelectedRow != -1){
            invoiceData invoice = Frame.getInvoices().get(SelectedInv);
            invoice.getLines().remove(SelectedRow);
            linesTableModel LinesTabelModel = new linesTableModel(invoice.getLines());
            Frame.getLineTable().setModel(LinesTabelModel);
            LinesTabelModel.fireTableDataChanged();
            Frame.getInvoicesTableModel().fireTableDataChanged();
        }
    }

    private void createInvoiceCancel() {
        invDialog.setVisible(false);
        invDialog.dispose();
        invDialog = null ;
    }

    private void createInvoiceOK() {
        String date = invDialog.getInvDateField().getText();
        String customer = invDialog.getCustNameField().getText();
        int number = Frame.getNextInvoiceNumber();     
        invoiceData Invoice = new invoiceData(number, date, customer);
        Frame.getInvoices().add(Invoice);
        Frame.getInvoicesTableModel().fireTableDataChanged();
        invDialog.setVisible(false);
        invDialog.dispose();
        invDialog = null;
        
    }

    private void createLineCancel() {
        lineDialog.setVisible(false);
        lineDialog.dispose();
        lineDialog = null;
    }

    private void createLineOK() {
        String item = lineDialog.getItemNameField().getText();
        int count = Integer.parseInt(lineDialog.getItemCountField().getText());
        double price = Double.parseDouble(lineDialog.getItemPriceField().getText());
        int selectedInvoice = Frame.getInvoiceTable().getSelectedRow();
        if (selectedInvoice != -1){
        invoiceData invoice = Frame.getInvoices().get(selectedInvoice);
        invoiceLine line = new invoiceLine(item, price, count, invoice);
        invoice.getLines().add(line);
        linesTableModel LinesTableModel = (linesTableModel) Frame.getLineTable().getModel();
        LinesTableModel.fireTableDataChanged();
        Frame.getInvoicesTableModel().fireTableDataChanged();
        }
        lineDialog.setVisible(false);
        lineDialog.dispose();
        lineDialog = null;
    }
    
}
