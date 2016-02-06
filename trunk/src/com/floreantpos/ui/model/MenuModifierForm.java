/**
 * ************************************************************************
 * * The contents of this file are subject to the MRPL 1.2
 * * (the  "License"),  being   the  Mozilla   Public  License
 * * Version 1.1  with a permitted attribution clause; you may not  use this
 * * file except in compliance with the License. You  may  obtain  a copy of
 * * the License at http://www.floreantpos.org/license.html
 * * Software distributed under the License  is  distributed  on  an "AS IS"
 * * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * * License for the specific  language  governing  rights  and  limitations
 * * under the License.
 * * The Original Code is FLOREANT POS.
 * * The Initial Developer of the Original Code is OROCUBE LLC
 * * All portions are Copyright (C) 2015 OROCUBE LLC
 * * All Rights Reserved.
 * ************************************************************************
 */
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.AbstractTableModel;

import net.miginfocom.swing.MigLayout;

import com.floreantpos.Messages;
import com.floreantpos.model.MenuModifier;
import com.floreantpos.model.MenuModifierGroup;
import com.floreantpos.model.Tax;
import com.floreantpos.model.dao.MenuModifierDAO;
import com.floreantpos.model.dao.ModifierDAO;
import com.floreantpos.model.dao.ModifierGroupDAO;
import com.floreantpos.model.dao.TaxDAO;
import com.floreantpos.swing.ComboBoxModel;
import com.floreantpos.swing.DoubleTextField;
import com.floreantpos.swing.FixedLengthTextField;
import com.floreantpos.swing.IntegerTextField;
import com.floreantpos.swing.MessageDialog;
import com.floreantpos.ui.BeanEditor;
import com.floreantpos.ui.dialog.BeanEditorDialog;
import com.floreantpos.ui.dialog.POSMessageDialog;
import com.floreantpos.util.POSUtil;

/**
 *
 * @author  MShahriar
 */
public class MenuModifierForm extends BeanEditor {
	private MenuModifier modifier;
	private PriceByOrderType priceTableModel;

	/** Creates new form ModifierEditor */
	public MenuModifierForm() throws Exception {
		this(new MenuModifier());
	}

	public MenuModifierForm(MenuModifier modifier) throws Exception {
		this.modifier = modifier;
		setLayout(new BorderLayout(0, 0));

		initComponents();

		ModifierGroupDAO modifierGroupDAO = new ModifierGroupDAO();
		List<MenuModifierGroup> groups = modifierGroupDAO.findAll();
		cbModifierGroup.setModel(new DefaultComboBoxModel(new Vector<MenuModifierGroup>(groups)));
		priceTable.setModel(priceTableModel = new PriceByOrderType(modifier.getProperties()));

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
		tfExtraPrice = new DoubleTextField();
		jLabel6 = new javax.swing.JLabel();
		cbTaxes = new javax.swing.JComboBox();
		tfPrice = new DoubleTextField();
		btnNewTax = new javax.swing.JButton();
		tfName = new javax.swing.JFormattedTextField();
		jLabel4 = new javax.swing.JLabel();
		cbModifierGroup = new javax.swing.JComboBox();
		btnPrintToKitchen = new javax.swing.JCheckBox();

		btnNewPrice = new javax.swing.JButton();
		btnUpdatePrice = new javax.swing.JButton();
		btnDeletePrice = new javax.swing.JButton();
		btnDefaultValue = new javax.swing.JButton();
		btnDeleteAll = new javax.swing.JButton();
		tabPrice = new javax.swing.JPanel();
		jScrollPane3 = new javax.swing.JScrollPane();
		priceTable = new javax.swing.JTable();

		jLabel2.setText(com.floreantpos.POSConstants.PRICE + ":"); //$NON-NLS-1$

		jLabel1.setText(com.floreantpos.POSConstants.NAME + ":"); //$NON-NLS-1$

		jLabel3.setText(com.floreantpos.POSConstants.EXTRA_PRICE + ":"); //$NON-NLS-1$

		jLabel5.setText(com.floreantpos.POSConstants.TAX_RATE + ":"); //$NON-NLS-1$

		tfExtraPrice.setText("0"); //$NON-NLS-1$

		jLabel6.setText("%"); //$NON-NLS-1$

		tfPrice.setText("0"); //$NON-NLS-1$

		btnNewTax.setText("..."); //$NON-NLS-1$
		btnNewTax.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnNewTaxActionPerformed(evt);
			}
		});

		jLabel4.setText(com.floreantpos.POSConstants.GROUP + ":"); //$NON-NLS-1$

		btnPrintToKitchen.setText(com.floreantpos.POSConstants.PRINT_TO_KITCHEN);
		btnPrintToKitchen.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
		btnPrintToKitchen.setMargin(new java.awt.Insets(0, 0, 0, 0));

		jTabbedPane1.addTab(com.floreantpos.POSConstants.GENERAL, jPanel1);

		jPanel1.setLayout(new MigLayout("", "[80px][173px,grow][6px][49px][12px][59px]", "[19px][][24px][19px][19px][][25px][][][15px]")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

		lblTranslatedName = new JLabel(Messages.getString("MenuModifierForm.0")); //$NON-NLS-1$
		jPanel1.add(lblTranslatedName, "cell 0 1,alignx trailing"); //$NON-NLS-1$

		tfTranslatedName = new FixedLengthTextField();
		jPanel1.add(tfTranslatedName, "cell 1 1 5 1,growx"); //$NON-NLS-1$

		lblSortOrder = new JLabel(Messages.getString("MenuModifierForm.15")); //$NON-NLS-1$
		jPanel1.add(lblSortOrder, "cell 0 5"); //$NON-NLS-1$

		tfSortOrder = new IntegerTextField();
		jPanel1.add(tfSortOrder, "cell 1 5,growx"); //$NON-NLS-1$
		jPanel1.add(jLabel5, "cell 0 6,alignx left,aligny center"); //$NON-NLS-1$
		jPanel1.add(jLabel1, "cell 0 0,alignx left,aligny center"); //$NON-NLS-1$
		jPanel1.add(jLabel4, "cell 0 2,alignx left,aligny center"); //$NON-NLS-1$
		jPanel1.add(jLabel2, "cell 0 3,alignx left,aligny center"); //$NON-NLS-1$
		jPanel1.add(jLabel3, "cell 0 4,alignx left,aligny center"); //$NON-NLS-1$

		JLabel lblButtonColor = new JLabel(Messages.getString("MenuModifierForm.1")); //$NON-NLS-1$
		jPanel1.add(lblButtonColor, "cell 0 7"); //$NON-NLS-1$

		btnButtonColor = new JButton(""); //$NON-NLS-1$
		btnButtonColor.setPreferredSize(new Dimension(140, 40));
		jPanel1.add(btnButtonColor, "cell 1 7"); //$NON-NLS-1$

		JLabel lblTextColor = new JLabel(Messages.getString("MenuModifierForm.27")); //$NON-NLS-1$
		jPanel1.add(lblTextColor, "cell 0 8"); //$NON-NLS-1$

		btnTextColor = new JButton(Messages.getString("MenuModifierForm.29")); //$NON-NLS-1$
		btnTextColor.setPreferredSize(new Dimension(140, 40));
		jPanel1.add(btnTextColor, "cell 1 8"); //$NON-NLS-1$
		jPanel1.add(btnPrintToKitchen, "cell 1 9,alignx left,aligny top"); //$NON-NLS-1$
		jPanel1.add(tfName, "cell 1 0 5 1,growx,aligny top"); //$NON-NLS-1$
		jPanel1.add(cbModifierGroup, "cell 1 2 5 1,growx,aligny top"); //$NON-NLS-1$
		jPanel1.add(cbTaxes, "cell 1 6,growx,aligny top"); //$NON-NLS-1$
		jPanel1.add(btnNewTax, "cell 3 6,alignx left,aligny top"); //$NON-NLS-1$
		jPanel1.add(jLabel6, "cell 5 6,alignx left,aligny center"); //$NON-NLS-1$
		jPanel1.add(tfExtraPrice, "cell 1 4,growx,aligny top"); //$NON-NLS-1$
		jPanel1.add(tfPrice, "cell 1 3,growx,aligny top"); //$NON-NLS-1$

		btnButtonColor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Color color = JColorChooser.showDialog(MenuModifierForm.this, Messages.getString("MenuModifierForm.39"), btnButtonColor.getBackground()); //$NON-NLS-1$
				btnButtonColor.setBackground(color);
				btnTextColor.setBackground(color);
			}
		});

		btnTextColor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Color color = JColorChooser.showDialog(MenuModifierForm.this, Messages.getString("MenuModifierForm.40"), btnTextColor.getForeground()); //$NON-NLS-1$
				btnTextColor.setForeground(color);
			}
		});

		btnNewPrice.setText(Messages.getString("MenuModifierForm.2")); //$NON-NLS-1$
		btnNewPrice.addActionListener(new ActionListener() {
			//TODO: handle exception
			@Override
			public void actionPerformed(ActionEvent e) {
				addNewPrice();
			}
		});
		btnUpdatePrice.setText(Messages.getString("MenuModifierForm.3")); //$NON-NLS-1$
		btnUpdatePrice.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				updatePrice();
			}
		});
		btnDeletePrice.setText(Messages.getString("MenuModifierForm.4")); //$NON-NLS-1$
		btnDeletePrice.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				deletePrice();
			}
		});
		btnDeleteAll.setText(Messages.getString("MenuModifierForm.5")); //$NON-NLS-1$
		btnDeleteAll.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				deleteAll();
			}
		});

		btnDefaultValue.setText(Messages.getString("MenuModifierForm.8")); //$NON-NLS-1$
		btnDefaultValue.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setDefaultValue();
			}
		});
		priceTable.setModel(new javax.swing.table.DefaultTableModel(new Object[][] { { null, null, null, null }, { null, null, null, null },
				{ null, null, null, null }, { null, null, null, null } }, new String[] { "Title 1", "Title 2", "Title 3", "Title 4" })); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$

		jScrollPane3.setViewportView(priceTable);

		tabPrice.setLayout(new BorderLayout());
		tabPrice.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		tabPrice.add(jScrollPane3, BorderLayout.CENTER);

		JPanel buttonPanel = new JPanel();

		buttonPanel.add(btnNewPrice);
		buttonPanel.add(btnUpdatePrice);
		//buttonPanel.add(btnDefaultValue);
		buttonPanel.add(btnDeletePrice);

		//buttonPanel.add(btnDeleteAll);

		tabPrice.add(buttonPanel, BorderLayout.SOUTH);
		jTabbedPane1.addTab(Messages.getString("MenuModifierForm.6"), tabPrice); //$NON-NLS-1$

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
	private DoubleTextField tfExtraPrice;
	private javax.swing.JFormattedTextField tfName;
	private DoubleTextField tfPrice;
	private JLabel lblTranslatedName;
	private FixedLengthTextField tfTranslatedName;
	private JButton btnButtonColor;
	private JButton btnTextColor;
	private IntegerTextField tfSortOrder;
	private JLabel lblSortOrder;

	private javax.swing.JButton btnNewPrice;
	private javax.swing.JButton btnUpdatePrice;
	private javax.swing.JButton btnDefaultValue;
	private javax.swing.JButton btnDeletePrice;
	private javax.swing.JButton btnDeleteAll;
	private javax.swing.JPanel tabPrice;
	private javax.swing.JScrollPane jScrollPane3;
	private javax.swing.JTable priceTable;

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
			tfName.setText(""); //$NON-NLS-1$
			tfPrice.setText("0"); //$NON-NLS-1$
			tfExtraPrice.setText("0"); //$NON-NLS-1$
			return;
		}

		tfName.setText(modifier.getName());
		tfTranslatedName.setText(modifier.getTranslatedName());
		tfPrice.setText(String.valueOf(modifier.getPrice()));
		tfExtraPrice.setText(String.valueOf(modifier.getExtraPrice()));
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
			MessageDialog.showError(Messages.getString("MenuModifierForm.44")); //$NON-NLS-1$
			return false;
		}

		modifier.setName(name);
		modifier.setPrice(tfPrice.getDouble());
		modifier.setExtraPrice(tfExtraPrice.getDouble());
		modifier.setTax((Tax) cbTaxes.getSelectedItem());
		modifier.setModifierGroup((MenuModifierGroup) cbModifierGroup.getSelectedItem());
		modifier.setShouldPrintToKitchen(Boolean.valueOf(btnPrintToKitchen.isSelected()));

		modifier.setTranslatedName(tfTranslatedName.getText());
		modifier.setButtonColor(btnButtonColor.getBackground().getRGB());
		modifier.setTextColor(btnTextColor.getForeground().getRGB());
		modifier.setSortOrder(tfSortOrder.getInteger());

		return true;
	}

	class PriceByOrderType extends AbstractTableModel {
		List<String> propertiesKey = new ArrayList<String>();

		String[] cn = { "MODIFIER", "ORDER TYPE", "PRICE", "TAX" }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$

		PriceByOrderType(Map<String, String> properties) {

			if (properties != null) {
				List<String> keys = new ArrayList(properties.keySet());
				setPropertiesToTable(keys);
			}
		}

		private void setPropertiesToTable(List<String> keys) {
			propertiesKey.clear();

			for (int i = 0; i < keys.size(); i++) {
				if (keys.get(i).contains("_PRICE")) { //$NON-NLS-1$
					this.propertiesKey.add(keys.get(i));
				}
			}
		}

		public String get(int index) {
			return propertiesKey.get(index);
		}

		public void add(MenuModifier modifier) {
			setPropertiesToTable(new ArrayList(modifier.getProperties().keySet()));
			fireTableDataChanged();
		}

		public void setDefaultValue() {
			int selectedRow = priceTable.getSelectedRow();
			if (selectedRow == -1) {
				POSMessageDialog.showMessage(Messages.getString("MenuModifierForm.9")); //$NON-NLS-1$
				return;
			}
			String modifiedKey = priceTableModel.propertiesKey.get(selectedRow);
			modifiedKey = modifiedKey.replaceAll("_PRICE", ""); //$NON-NLS-1$ //$NON-NLS-2$
			modifiedKey = modifiedKey.replaceAll("_", " ");//$NON-NLS-1$ //$NON-NLS-2$
			modifier.setPriceByOrderType(modifiedKey, modifier.getPrice());
			if (modifier.getTax() != null) {
				modifier.setTaxByOrderType(modifiedKey, modifier.getTax().getRate());
			}
			else {
				modifier.setTaxByOrderType(modifiedKey, 0.0);
			}
			MenuModifierDAO.getInstance().saveOrUpdate(modifier);
			add(modifier);
			fireTableDataChanged();
		}

		public void remove(int index) {
			if (propertiesKey == null) {
				return;
			}
			String typeProperty = propertiesKey.get(index);
			String taxProperty = typeProperty.replaceAll("_PRICE", "_TAX"); //$NON-NLS-1$ //$NON-NLS-2$

			modifier.removeProperty(typeProperty, taxProperty);
			MenuModifierDAO.getInstance().saveOrUpdate(modifier);

			propertiesKey.remove(index);
			fireTableDataChanged();
		}

		public void removeAll() {
			modifier.getProperties().clear();
			MenuModifierDAO.getInstance().saveOrUpdate(modifier);
			propertiesKey.clear();
			fireTableDataChanged();
		}

		public int getRowCount() {
			if (propertiesKey == null)
				return 0;

			return propertiesKey.size();
		}

		public int getColumnCount() {
			return cn.length;
		}

		@Override
		public String getColumnName(int column) {
			return cn[column];
		}

		public List<String> getProperties() {
			return propertiesKey;
		}

		public Object getValueAt(int rowIndex, int columnIndex) {
			String key = String.valueOf(propertiesKey.get(rowIndex));
			switch (columnIndex) {
				case 0:
					return modifier.getName();
				case 1:
					key = key.replaceAll("_PRICE", ""); //$NON-NLS-1$ //$NON-NLS-2$
					key = key.replaceAll("_", " "); //$NON-NLS-1$ //$NON-NLS-2$
					return key;
				case 2:
					return modifier.getProperty(key);
				case 3:
					key = key.replaceAll("_PRICE", "_TAX"); //$NON-NLS-1$ //$NON-NLS-2$
					return modifier.getProperty(key);
			}
			return null;
		}
	}

	public String getDisplayText() {
		MenuModifier modifier = (MenuModifier) getBean();
		if (modifier.getId() == null) {
			return Messages.getString("MenuModifierForm.45"); //$NON-NLS-1$
		}
		return Messages.getString("MenuModifierForm.46"); //$NON-NLS-1$
	}

	private void addNewPrice() {

		ModifierPriceByOrderTypeDialog dialog = new ModifierPriceByOrderTypeDialog(modifier);
		dialog.setSize(350, 220);
		dialog.open();
		if (!dialog.isCanceled()) {
			priceTableModel.add(dialog.getMenuModifier());
		}
	}

	private void deletePrice() {
		int selectedRow = priceTable.getSelectedRow();
		if (selectedRow == -1) {
			POSMessageDialog.showMessage(Messages.getString("MenuModifierForm.7")); //$NON-NLS-1$
			return;
		}
		int option = POSMessageDialog.showYesNoQuestionDialog(POSUtil.getBackOfficeWindow(),
				Messages.getString("MenuModifierForm.21"), Messages.getString("MenuModifierForm.22")); //$NON-NLS-1$ //$NON-NLS-2$
		if (option != JOptionPane.YES_OPTION) {
			return;
		}

		priceTableModel.remove(selectedRow);
	}

	private void deleteAll() {

		int option = POSMessageDialog.showYesNoQuestionDialog(POSUtil.getBackOfficeWindow(),
				Messages.getString("MenuModifierForm.23"), Messages.getString("MenuModifierForm.24")); //$NON-NLS-1$ //$NON-NLS-2$
		if (option != JOptionPane.YES_OPTION) {
			return;
		}
		priceTableModel.removeAll();
	}

	private void setDefaultValue() {
		priceTableModel.setDefaultValue();
	}

	private void updatePrice() {
		int selectedRow = priceTable.getSelectedRow();
		if (selectedRow == -1) {
			POSMessageDialog.showMessage(Messages.getString("MenuModifierForm.25")); //$NON-NLS-1$
			return;
		}

		priceTableModel.propertiesKey.get(selectedRow);
		ModifierPriceByOrderTypeDialog dialog = new ModifierPriceByOrderTypeDialog(modifier, String.valueOf(priceTableModel.propertiesKey.get(selectedRow)));
		dialog.setSize(350, 220);
		dialog.open();
		if (!dialog.isCanceled()) {
			priceTableModel.add(dialog.getMenuModifier());
		}
	}
}
