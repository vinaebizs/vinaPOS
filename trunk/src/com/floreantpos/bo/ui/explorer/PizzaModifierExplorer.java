package com.floreantpos.bo.ui.explorer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import net.miginfocom.swing.MigLayout;

import com.floreantpos.Messages;
import com.floreantpos.POSConstants;
import com.floreantpos.bo.ui.BOMessageDialog;
import com.floreantpos.model.MenuModifier;
import com.floreantpos.model.MenuModifierGroup;
import com.floreantpos.model.dao.MenuModifierGroupDAO;
import com.floreantpos.model.dao.ModifierDAO;
import com.floreantpos.swing.ListTableModel;
import com.floreantpos.swing.PosUIManager;
import com.floreantpos.swing.TransparentPanel;
import com.floreantpos.ui.dialog.BeanEditorDialog;
import com.floreantpos.ui.dialog.ConfirmDeleteDialog;
import com.floreantpos.ui.model.MenuModifierForm;
import com.floreantpos.ui.model.PizzaModifierForm;
import com.floreantpos.util.CurrencyUtil;

public class PizzaModifierExplorer extends TransparentPanel {

	private String currencySymbol;
	private JTable table;
	private PizzaModifierExplorerModel tableModel;

	public PizzaModifierExplorer() {
		setLayout(new BorderLayout(5, 5));

		currencySymbol = CurrencyUtil.getCurrencySymbol();
		tableModel = new PizzaModifierExplorerModel();
		table = new JTable(tableModel);
		table.setRowHeight(PosUIManager.getSize(table.getRowHeight()));

		add(new JScrollPane(table));

		createActionButtons();
		add(buildSearchForm(), BorderLayout.NORTH);

		updateModifierList();

		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent me) {
				if (me.getClickCount() == 2) {
					editSelectedRow();
				}
			}
		});
	}

	private void editSelectedRow() {
		try {
			int index = table.getSelectedRow();
			if (index < 0)
				return;

			index = table.convertRowIndexToModel(index);
			MenuModifier modifier = (MenuModifier) tableModel.getRowData(index);

			PizzaModifierForm editor = new PizzaModifierForm(modifier);
			BeanEditorDialog dialog = new BeanEditorDialog(editor);
			dialog.open();
			if (dialog.isCanceled())
				return;

			table.repaint();
		} catch (Throwable x) {
			BOMessageDialog.showError(com.floreantpos.POSConstants.ERROR_MESSAGE, x);
		}

	}

	private void createActionButtons() {
		ExplorerButtonPanel explorerButtonPanel = new ExplorerButtonPanel();
		explorerButtonPanel.getAddButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					PizzaModifierForm editor = new PizzaModifierForm();
					BeanEditorDialog dialog = new BeanEditorDialog(editor);
					dialog.open();
					if (dialog.isCanceled())
						return;
					MenuModifier modifier = (MenuModifier) editor.getBean();
					tableModel.addModifier(modifier);
				} catch (Throwable x) {
					BOMessageDialog.showError(com.floreantpos.POSConstants.ERROR_MESSAGE, x);
				}
			}

		});
		explorerButtonPanel.getEditButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editSelectedRow();
			}
		});


		explorerButtonPanel.getDeleteButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int index = table.getSelectedRow();
					if (index < 0)
						return;

					index = table.convertRowIndexToModel(index);

					if (ConfirmDeleteDialog.showMessage(PizzaModifierExplorer.this, com.floreantpos.POSConstants.CONFIRM_DELETE,
							com.floreantpos.POSConstants.DELETE) != ConfirmDeleteDialog.NO) {
						MenuModifier category = (MenuModifier) tableModel.getRowData(index);
						ModifierDAO modifierDAO = new ModifierDAO();
						modifierDAO.delete(category);
						tableModel.deleteModifier(category, index);
					}
				} catch (Throwable x) {
					BOMessageDialog.showError(com.floreantpos.POSConstants.ERROR_MESSAGE, x);
				}

			}

		});

		add(explorerButtonPanel, BorderLayout.SOUTH);
	}

	private JPanel buildSearchForm() {
		List<MenuModifierGroup> grpName;
		JPanel panel = new JPanel();
		panel.setLayout(new MigLayout("", "[][]30[][]30[]", "[]20[]")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

		JLabel nameLabel = new JLabel(Messages.getString("ModifierExplorer.3")); //$NON-NLS-1$
		JLabel groupLabel = new JLabel(Messages.getString("ModifierExplorer.4")); //$NON-NLS-1$
		final JTextField nameField = new JTextField(15);
		grpName = MenuModifierGroupDAO.getInstance().findAll();
		final JComboBox cbGroup = new JComboBox();
		cbGroup.addItem(Messages.getString("ModifierExplorer.5")); //$NON-NLS-1$
		for (MenuModifierGroup s : grpName) {
			cbGroup.addItem(s);
		}

		JButton searchBttn = new JButton(Messages.getString("ModifierExplorer.6")); //$NON-NLS-1$
		panel.add(nameLabel, "align label"); //$NON-NLS-1$
		panel.add(nameField);
		panel.add(groupLabel);
		panel.add(cbGroup);
		panel.add(searchBttn);

		TitledBorder title;
		Border loweredetched;
		loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		title = BorderFactory.createTitledBorder(loweredetched, Messages.getString("ModifierExplorer.8")); //$NON-NLS-1$
		title.setTitleJustification(TitledBorder.LEFT);
		panel.setBorder(title);
		searchBttn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				List<MenuModifier> modifierList;
				String txName = nameField.getText();
				Object selectedItem = cbGroup.getSelectedItem();
				if (selectedItem instanceof MenuModifierGroup) {
					modifierList = ModifierDAO.getInstance().findPizzaModifier(txName, (MenuModifierGroup) selectedItem);
				}
				else {
					modifierList = ModifierDAO.getInstance().findPizzaModifier(txName, null);
				}

				setModifierList(modifierList);
			}
		});
		return panel;
	}

	public synchronized void updateModifierList() {
		setModifierList(ModifierDAO.getInstance().getPizzaModifiers());

	}

	public void setModifierList(List<MenuModifier> modifierList) {
		tableModel.setRows(modifierList);

	}

	private class PizzaModifierExplorerModel extends ListTableModel {

		public PizzaModifierExplorerModel() {

			super(new String[] { com.floreantpos.POSConstants.ID, com.floreantpos.POSConstants.NAME, POSConstants.TRANSLATED_NAME,
					//com.floreantpos.POSConstants.PRICE + " (" + currencySymbol + ")", com.floreantpos.POSConstants.EXTRA_PRICE, //$NON-NLS-1$ //$NON-NLS-2$
					com.floreantpos.POSConstants.TAX + "(%)", com.floreantpos.POSConstants.MODIFIER_GROUP, POSConstants.BUTTON_COLOR, POSConstants.SORT_ORDER }); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

		}

		public Object getValueAt(int rowIndex, int columnIndex) {
			List<MenuModifier> modifierList = getRows();

			MenuModifier modifier = modifierList.get(rowIndex);

			switch (columnIndex) {
				case 0:
					return String.valueOf(modifier.getId());

				case 1:
					return modifier.getName();

				case 2:
					return modifier.getTranslatedName();

					/*	case 3:
							return Double.valueOf(modifier.getPrice());

						case 4:
							return Double.valueOf(modifier.getExtraPrice());*/

				case 3:
					if (modifier.getTax() == null) {
						return ""; //$NON-NLS-1$
					}
					return Double.valueOf(modifier.getTax().getRate());

				case 4:
					if (modifier.getModifierGroup() == null) {
						return ""; //$NON-NLS-1$
					}
					return modifier.getModifierGroup().getName();

				case 5:
					if (modifier.getButtonColor() != null) {
						return new Color(modifier.getButtonColor());
					}

					return null;

				case 6:
					return modifier.getSortOrder();
			}
			return null;
		}

		public void addModifier(MenuModifier category) {
			int size = getRows().size();
			getRows().add(category);
			fireTableRowsInserted(size, size);

		}

		public void deleteModifier(MenuModifier category, int index) {
			getRows().remove(category);
			fireTableRowsDeleted(index, index);
		}

	}

}