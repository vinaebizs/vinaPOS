/*
 * ModifierEditor.java
 *
 * Created on August 4, 2006, 12:03 AM
 */

package com.floreantpos.ui.model;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JLabel;

import net.miginfocom.swing.MigLayout;

import com.floreantpos.model.MenuModifier;
import com.floreantpos.model.MenuModifierGroup;
import com.floreantpos.model.Tax;
import com.floreantpos.model.dao.ModifierDAO;
import com.floreantpos.model.dao.ModifierGroupDAO;
import com.floreantpos.model.dao.TaxDAO;
import com.floreantpos.swing.ComboBoxModel;
import com.floreantpos.swing.FixedLengthTextField;
import com.floreantpos.swing.IntegerTextField;
import com.floreantpos.swing.MessageDialog;
import com.floreantpos.ui.BeanEditor;
import com.floreantpos.ui.dialog.BeanEditorDialog;
import com.floreantpos.util.POSUtil;

/**
 *
 * @author  MShahriar
 */
public class MenuModifierForm extends BeanEditor {

	/** Creates new form ModifierEditor */
	public MenuModifierForm() throws Exception {
		this(new MenuModifier());
	}

	public MenuModifierForm(MenuModifier modifier) throws Exception {
		setLayout(new BorderLayout(0, 0));

		initComponents();

		ModifierGroupDAO modifierGroupDAO = new ModifierGroupDAO();
		List<MenuModifierGroup> groups = modifierGroupDAO.findAll();
		cbModifierGroup.setModel(new DefaultComboBoxModel(new Vector<MenuModifierGroup>(groups)));

		TaxDAO taxDAO = new TaxDAO();
		List<Tax> taxes = taxDAO.findAll();
		cbTaxes.setModel(new ComboBoxModel(taxes));

		add(jTabbedPane1);

		setBean(modifier);
	}

	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
	// <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
	private void initComponents() {

		jTabbedPane1 = new javax.swing.JTabbedPane();
		jPanel1 = new com.floreantpos.swing.TransparentPanel();
		jLabel2 = new javax.swing.JLabel();
		jLabel1 = new javax.swing.JLabel();
		jLabel3 = new javax.swing.JLabel();
		jLabel5 = new javax.swing.JLabel();
		tfExtraPrice = new javax.swing.JFormattedTextField();
		jLabel6 = new javax.swing.JLabel();
		cbTaxes = new javax.swing.JComboBox();
		tfPrice = new javax.swing.JFormattedTextField();
		btnNewTax = new javax.swing.JButton();
		tfName = new javax.swing.JFormattedTextField();
		jLabel4 = new javax.swing.JLabel();
		cbModifierGroup = new javax.swing.JComboBox();
		btnPrintToKitchen = new javax.swing.JCheckBox();

		jLabel2.setText(com.floreantpos.POSConstants.PRICE + ":");

		jLabel1.setText(com.floreantpos.POSConstants.NAME + ":");

		jLabel3.setText(com.floreantpos.POSConstants.EXTRA_PRICE + ":");

		jLabel5.setText(com.floreantpos.POSConstants.TAX_RATE + ":");

		tfExtraPrice.setText("0");

		jLabel6.setText("%");

		tfPrice.setText("0");

		btnNewTax.setText("...");
		btnNewTax.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnNewTaxActionPerformed(evt);
			}
		});

		jLabel4.setText(com.floreantpos.POSConstants.GROUP + ":");

		btnPrintToKitchen.setText(com.floreantpos.POSConstants.PRINT_TO_KITCHEN);
		btnPrintToKitchen.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
		btnPrintToKitchen.setMargin(new java.awt.Insets(0, 0, 0, 0));

		jTabbedPane1.addTab(com.floreantpos.POSConstants.GENERAL, jPanel1);

		jPanel1.setLayout(new MigLayout("", "[80px][173px,grow][6px][49px][12px][59px]", "[19px][][24px][19px][19px][][25px][][][15px]"));

		lblTranslatedName = new JLabel("Translated name");
		jPanel1.add(lblTranslatedName, "cell 0 1,alignx trailing");

		tfTranslatedName = new FixedLengthTextField();
		jPanel1.add(tfTranslatedName, "cell 1 1 5 1,growx");
		
		lblSortOrder = new JLabel("Sort order");
		jPanel1.add(lblSortOrder, "cell 0 5");
		
		tfSortOrder = new IntegerTextField();
		jPanel1.add(tfSortOrder, "cell 1 5,growx");
		jPanel1.add(jLabel5, "cell 0 6,alignx left,aligny center");
		jPanel1.add(jLabel1, "cell 0 0,alignx left,aligny center");
		jPanel1.add(jLabel4, "cell 0 2,alignx left,aligny center");
		jPanel1.add(jLabel2, "cell 0 3,alignx left,aligny center");
		jPanel1.add(jLabel3, "cell 0 4,alignx left,aligny center");

		JLabel lblButtonColor = new JLabel("Button color");
		jPanel1.add(lblButtonColor, "cell 0 7");

		btnButtonColor = new JButton("");
		btnButtonColor.setPreferredSize(new Dimension(140, 40));
		jPanel1.add(btnButtonColor, "cell 1 7");

		JLabel lblTextColor = new JLabel("Text color");
		jPanel1.add(lblTextColor, "cell 0 8");

		btnTextColor = new JButton("SAMPLE TEXT");
		btnTextColor.setPreferredSize(new Dimension(140, 40));
		jPanel1.add(btnTextColor, "cell 1 8");
		jPanel1.add(btnPrintToKitchen, "cell 1 9,alignx left,aligny top");
		jPanel1.add(tfName, "cell 1 0 5 1,growx,aligny top");
		jPanel1.add(cbModifierGroup, "cell 1 2 5 1,growx,aligny top");
		jPanel1.add(cbTaxes, "cell 1 6,growx,aligny top");
		jPanel1.add(btnNewTax, "cell 3 6,alignx left,aligny top");
		jPanel1.add(jLabel6, "cell 5 6,alignx left,aligny center");
		jPanel1.add(tfExtraPrice, "cell 1 4,growx,aligny top");
		jPanel1.add(tfPrice, "cell 1 3,growx,aligny top");

		btnButtonColor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Color color = JColorChooser.showDialog(MenuModifierForm.this, "Select color", btnButtonColor.getBackground());
				btnButtonColor.setBackground(color);
				btnTextColor.setBackground(color);
			}
		});

		btnTextColor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Color color = JColorChooser.showDialog(MenuModifierForm.this, "Select color", btnTextColor.getForeground());
				btnTextColor.setForeground(color);
			}
		});
	}// </editor-fold>//GEN-END:initComponents

	private void btnNewTaxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewTaxActionPerformed
		try {
			TaxForm editor = new TaxForm();
			BeanEditorDialog dialog = new BeanEditorDialog(editor);
			dialog.open();
			if (!dialog.isCanceled()) {
				Tax tax = (Tax) editor.getBean();
				ComboBoxModel model = (ComboBoxModel) cbTaxes.getModel();
				model.addElement(tax);
				model.setSelectedItem(tax);
			}
		} catch (Exception x) {
			MessageDialog.showError(com.floreantpos.POSConstants.ERROR_MESSAGE, x);
		}
	}//GEN-LAST:event_btnNewTaxActionPerformed

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton btnNewTax;
	private javax.swing.JCheckBox btnPrintToKitchen;
	private javax.swing.JComboBox cbModifierGroup;
	private javax.swing.JComboBox cbTaxes;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JLabel jLabel4;
	private javax.swing.JLabel jLabel5;
	private javax.swing.JLabel jLabel6;
	private com.floreantpos.swing.TransparentPanel jPanel1;
	private javax.swing.JTabbedPane jTabbedPane1;
	private javax.swing.JFormattedTextField tfExtraPrice;
	private javax.swing.JFormattedTextField tfName;
	private javax.swing.JFormattedTextField tfPrice;
	private JLabel lblTranslatedName;
	private FixedLengthTextField tfTranslatedName;
	private JButton btnButtonColor;
	private JButton btnTextColor;
	private IntegerTextField tfSortOrder;
	private JLabel lblSortOrder;

	// End of variables declaration//GEN-END:variables
	@Override
	public boolean save() {
		try {
			if (!updateModel())
				return false;

			MenuModifier modifier = (MenuModifier) getBean();
			ModifierDAO dao = new ModifierDAO();
			dao.saveOrUpdate(modifier);
		} catch (Exception e) {
			MessageDialog.showError(com.floreantpos.POSConstants.SAVE_ERROR, e);
			return false;
		}
		return true;
	}

	@Override
	protected void updateView() {
		MenuModifier modifier = (MenuModifier) getBean();

		if (modifier == null) {
			tfName.setText("");
			tfPrice.setText("0");
			tfExtraPrice.setText("0");
			return;
		}

		tfName.setText(modifier.getName());
		tfTranslatedName.setText(modifier.getTranslatedName());
		tfPrice.setValue(new Double(modifier.getPrice()));
		tfExtraPrice.setValue(Double.valueOf(modifier.getExtraPrice()));
		cbModifierGroup.setSelectedItem(modifier.getModifierGroup());
		btnPrintToKitchen.setSelected(modifier.isShouldPrintToKitchen());
		
		if (modifier.getSortOrder() != null) {
			tfSortOrder.setText(modifier.getSortOrder().toString());
		}
		
		if (modifier.getButtonColor() != null) {
			Color color = new Color(modifier.getButtonColor());
			btnButtonColor.setBackground(color);
			btnTextColor.setBackground(color);
		}

		if (modifier.getTextColor() != null) {
			Color color = new Color(modifier.getTextColor());
			btnTextColor.setForeground(color);
		}

		if (modifier.getTax() != null) {
			cbTaxes.setSelectedItem(modifier.getTax());
		}
	}

	@Override
	protected boolean updateModel() {
		MenuModifier modifier = (MenuModifier) getBean();

		String name = tfName.getText();
		if (POSUtil.isBlankOrNull(name)) {
			MessageDialog.showError("Name is required");
			return false;
		}

		modifier.setName(name);
		modifier.setPrice(((Double) tfPrice.getValue()).doubleValue());
		modifier.setExtraPrice(((Double) tfExtraPrice.getValue()).doubleValue());
		modifier.setTax((Tax) cbTaxes.getSelectedItem());
		modifier.setModifierGroup((MenuModifierGroup) cbModifierGroup.getSelectedItem());
		modifier.setShouldPrintToKitchen(Boolean.valueOf(btnPrintToKitchen.isSelected()));
		
		modifier.setTranslatedName(tfTranslatedName.getText());
		modifier.setButtonColor(btnButtonColor.getBackground().getRGB());
		modifier.setTextColor(btnTextColor.getForeground().getRGB());
		modifier.setSortOrder(tfSortOrder.getInteger());

		return true;
	}

	public String getDisplayText() {
		MenuModifier modifier = (MenuModifier) getBean();
		if (modifier.getId() == null) {
			return "New menu modifier";
		}
		return "Edit menu modifier";
	}
}
