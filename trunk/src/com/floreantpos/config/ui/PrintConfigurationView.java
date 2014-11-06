/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PrintConfiguration.java
 *
 * Created on Apr 5, 2010, 4:31:47 PM
 */

package com.floreantpos.config.ui;

import java.awt.Component;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;

import net.miginfocom.swing.MigLayout;

import com.floreantpos.config.AppConfig;
import com.floreantpos.config.PrintConfig;
import com.floreantpos.main.Application;
import com.floreantpos.model.PosPrinters;
import com.floreantpos.ui.dialog.POSMessageDialog;
import javax.swing.JPanel;
import java.awt.GridLayout;

/**
 *
 * @author mshahriar
 */
public class PrintConfigurationView extends ConfigurationView {
	
	PosPrinters printers = Application.getPrinters();

	/** Creates new form PrintConfiguration */
	public PrintConfigurationView() {
		initComponents();
	}

	@Override
	public String getName() {
		return com.floreantpos.POSConstants.CONFIG_TAB_PRINT;
	}

	@Override
	public void initialize() throws Exception {
		PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);

		cbReportPrinterName.setModel(new DefaultComboBoxModel(printServices));
		cbReceiptPrinterName.setModel(new DefaultComboBoxModel(printServices));
		cbKitchenPrinterName.setModel(new DefaultComboBoxModel(printServices));

		PrintServiceComboRenderer comboRenderer = new PrintServiceComboRenderer();
		cbReportPrinterName.setRenderer(comboRenderer);
		cbReceiptPrinterName.setRenderer(comboRenderer);
		cbKitchenPrinterName.setRenderer(comboRenderer);

		setSelectedPrinter(cbReportPrinterName, PrintConfig.REPORT_PRINTER_NAME);
		setSelectedPrinter(cbReceiptPrinterName, PrintConfig.RECEIPT_PRINTER_NAME);
		setSelectedPrinter(cbKitchenPrinterName, PrintConfig.KITCHEN_PRINTER_NAME);

		setInitialized(true);

		if (printServices == null || printServices.length == 0) {
			POSMessageDialog.showMessage("No printer is installed on your operating system. Please install printer and come back again.");
		}
	}

	private void setSelectedPrinter(JComboBox whichPrinter, String propertyName) {
		PrintService osDefaultPrinter = PrintServiceLookup.lookupDefaultPrintService();

		if (osDefaultPrinter == null) {
			return;
		}

		String receiptPrinterName = AppConfig.getString(propertyName, osDefaultPrinter.getName());

		int printerCount = whichPrinter.getItemCount();
		for (int i = 0; i < printerCount; i++) {
			PrintService printService = (PrintService) whichPrinter.getItemAt(i);
			if (printService.getName().equals(receiptPrinterName)) {
				whichPrinter.setSelectedIndex(i);
				return;
			}
		}
	}

	@Override
	public boolean save() throws Exception {
		PrintService printService = (PrintService) cbReportPrinterName.getSelectedItem();
		AppConfig.put(PrintConfig.REPORT_PRINTER_NAME, printService == null ? null : printService.getName());
		
		printService = (PrintService) cbReceiptPrinterName.getSelectedItem();
		AppConfig.put(PrintConfig.RECEIPT_PRINTER_NAME, printService == null ? null : printService.getName());

		printService = (PrintService) cbKitchenPrinterName.getSelectedItem();
		AppConfig.put(PrintConfig.KITCHEN_PRINTER_NAME, printService == null ? null : printService.getName());

		Application.getPrinters().save();
		
		return true;
	}

	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {
		setLayout(new MigLayout("", "[][grow,fill]", "[][][][18px,grow]"));
		
		JLabel lblReportPrinter = new JLabel("Report Printer");
		add(lblReportPrinter, "cell 0 0,alignx trailing");
		
		cbReportPrinterName = new JComboBox();
		add(cbReportPrinterName, "cell 1 0,growx");
		javax.swing.JLabel jLabel1 = new javax.swing.JLabel();
		add(jLabel1, "cell 0 1,alignx right");

		jLabel1.setText("Receipt Printer:");
		cbReceiptPrinterName = new javax.swing.JComboBox();
		add(cbReceiptPrinterName, "cell 1 1,growx");
		javax.swing.JLabel jLabel2 = new javax.swing.JLabel();
		add(jLabel2, "cell 0 2,alignx right");

		jLabel2.setText("Kitchen Printer:");
		cbKitchenPrinterName = new javax.swing.JComboBox();
		add(cbKitchenPrinterName, "cell 1 2,growx");
		
		JPanel panel = new JPanel();
		add(panel, "cell 0 3 2 1,grow");
		panel.setLayout(new GridLayout(0, 2, 10, 0));
		
		MultiPrinterPane multiPrinterPane = new MultiPrinterPane("Receipt Printers", printers.getReceiptPrinters());
		panel.add(multiPrinterPane);
		
		MultiPrinterPane multiPrinterPane_1 = new MultiPrinterPane("Kitchen Printers", printers.getKitchenPrinters());
		panel.add(multiPrinterPane_1);

	}// </editor-fold>//GEN-END:initComponents

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JComboBox cbKitchenPrinterName;
	private javax.swing.JComboBox cbReceiptPrinterName;
	private JComboBox cbReportPrinterName;

	// End of variables declaration//GEN-END:variables

	private class PrintServiceComboRenderer extends DefaultListCellRenderer {
		@Override
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
			JLabel listCellRendererComponent = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			PrintService printService = (PrintService) value;

			if (printService != null) {
				listCellRendererComponent.setText(printService.getName());
			}

			return listCellRendererComponent;
		}
	}

}
