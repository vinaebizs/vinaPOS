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
 * TicketView.java
 *
 * Created on August 4, 2006, 3:42 PM
 */

package com.floreantpos.ui.views.order;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.miginfocom.swing.MigLayout;

import com.floreantpos.IconFactory;
import com.floreantpos.Messages;
import com.floreantpos.PosException;
import com.floreantpos.config.TerminalConfig;
import com.floreantpos.main.Application;
import com.floreantpos.model.ITicketItem;
import com.floreantpos.model.MenuItem;
import com.floreantpos.model.Ticket;
import com.floreantpos.model.TicketItem;
import com.floreantpos.model.TicketItemModifier;
import com.floreantpos.model.dao.MenuItemDAO;
import com.floreantpos.model.dao.TicketDAO;
import com.floreantpos.report.ReceiptPrintService;
import com.floreantpos.swing.PosButton;
import com.floreantpos.swing.PosScrollPane;
import com.floreantpos.ui.dialog.ItemNumberSelectionDialog;
import com.floreantpos.ui.dialog.POSMessageDialog;
import com.floreantpos.ui.views.CashierSwitchBoardView;
import com.floreantpos.ui.views.SwitchboardView;
import com.floreantpos.ui.views.order.actions.OrderListener;
import com.floreantpos.util.NumberUtil;
import com.floreantpos.util.POSUtil;

/**
 * 
 * @author MShahriar
 */
public class TicketView extends JPanel {
	private java.util.Vector<OrderListener> orderListeners = new java.util.Vector<OrderListener>();
	private Ticket ticket;
	private com.floreantpos.swing.PosButton btnDecreaseAmount;
	private com.floreantpos.swing.PosButton btnDelete = new PosButton(IconFactory.getIcon("/ui_icons/", "delete.png")); //$NON-NLS-1$ //$NON-NLS-2$
	private com.floreantpos.swing.PosButton btnIncreaseAmount = new PosButton(IconFactory.getIcon("/ui_icons/", "add_user.png")); //$NON-NLS-1$ //$NON-NLS-2$
	private com.floreantpos.swing.PosButton btnEdit = new PosButton("..."); //$NON-NLS-1$ //$NON-NLS-2$
	private com.floreantpos.swing.PosButton btnScrollDown;
	private com.floreantpos.swing.PosButton btnScrollUp = new PosButton(IconFactory.getIcon("/ui_icons/", "up.png")); //$NON-NLS-1$ //$NON-NLS-2$
	private com.floreantpos.swing.TransparentPanel ticketItemActionPanel;
	private javax.swing.JScrollPane ticketScrollPane;
	private PosButton btnTotal;
	public com.floreantpos.ui.ticket.TicketViewerTable ticketViewerTable;
	private JPanel itemSearchPanel;
	
	private TitledBorder titledBorder = new TitledBorder(""); //$NON-NLS-1$
	private Border border = new CompoundBorder(titledBorder, new EmptyBorder(5, 5, 5, 5));

	public final static String VIEW_NAME = "TICKET_VIEW"; //$NON-NLS-1$

	public TicketView() {
		initComponents();
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	// <editor-fold defaultstate="collapsed"
	// desc=" Generated Code ">//GEN-BEGIN:initComponents
	private void initComponents() {
		titledBorder.setTitleJustification(TitledBorder.CENTER);
		setBorder(border);
		setLayout(new java.awt.BorderLayout(5, 5));
		itemSearchPanel = new JPanel();
		
		ticketItemActionPanel = new com.floreantpos.swing.TransparentPanel();
		btnDecreaseAmount = new com.floreantpos.swing.PosButton();
		btnScrollDown = new com.floreantpos.swing.PosButton();
		ticketViewerTable = new com.floreantpos.ui.ticket.TicketViewerTable();
		ticketScrollPane = new PosScrollPane(ticketViewerTable);
		ticketScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		ticketScrollPane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		ticketScrollPane.setPreferredSize(new java.awt.Dimension(180, 200));

		btnEdit.setEnabled(false);

		JPanel totalViewPanel = createTotalViewerPanel();

		createTicketItemControlPanel();
		createItemSearchPanel();
		
		
		JPanel centerPanel = new JPanel(new BorderLayout(5, 5));
		centerPanel.add(ticketScrollPane);
		centerPanel.add(totalViewPanel, BorderLayout.SOUTH);

		add(itemSearchPanel, BorderLayout.NORTH);
		add(centerPanel);
		add(ticketItemActionPanel, BorderLayout.EAST);
		ticketViewerTable.getRenderer().setInTicketScreen(true);
		ticketViewerTable.getSelectionModel().addListSelectionListener(new TicketItemSelectionListener());
		//	OrderView.getInstance().actionUpdate(null);
		setPreferredSize(new java.awt.Dimension(360, 463));

	}// </editor-fold>//GEN-END:initComponents

	private void createItemSearchPanel() {
		
		itemSearchPanel.setLayout(new BorderLayout(5, 5));

		PosButton btnSearch = new PosButton("...");
		btnSearch.setPreferredSize(new Dimension(60, 40));

		final JTextField txtSearchItem = new JTextField();

		txtSearchItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (txtSearchItem.getText().equals("")) {
					POSMessageDialog.showMessage("Please enter item number or barcode ");
					return;
				}

				if (!addMenuItemByBarcode(txtSearchItem.getText())) {
					if (!addMenuItemByItemId(txtSearchItem.getText())) {
						POSMessageDialog.showError(Application.getPosWindow(), "Item not found");
					}
				}
				txtSearchItem.setText("");
			}
		});

		btnSearch.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				ItemNumberSelectionDialog dialog = new ItemNumberSelectionDialog(Application.getPosWindow());
				dialog.setTitle("Search item");
				dialog.setSize(600, 400);
				dialog.open();

				if (dialog.isCanceled()) {
					return;
				}

				if (!addMenuItemByBarcode(dialog.getValue())) {
					if (!addMenuItemByItemId(dialog.getValue())) {
						POSMessageDialog.showError(Application.getPosWindow(), "Item not found");
					}
				}
			}
		});
		itemSearchPanel.add(txtSearchItem);
		itemSearchPanel.add(btnSearch, BorderLayout.EAST);
	}
	private static boolean isParsable(String input) {
		boolean parsable = true;
		try {
			Integer.parseInt(input);
		} catch (NumberFormatException e) {
			parsable = false;
		}
		return parsable;
	}

	private boolean addMenuItemByItemId(String id) {

		if (!isParsable(id)) {
			return false;
		}

		Integer itemId = Integer.parseInt(id);

		MenuItem menuItem = MenuItemDAO.getInstance().get(itemId);

		if (menuItem == null) {
			return false;
		}
		
		OrderView.getInstance().getOrderController().itemSelected(menuItem);
		return true;
	}

	private boolean addMenuItemByBarcode(String barcode) {

		MenuItemDAO dao = new MenuItemDAO();

		MenuItem menuItem = dao.getMenuItemByBarcode(barcode);

		if (menuItem == null) {
			return false;
		}

		OrderView.getInstance().getOrderController().itemSelected(menuItem);
		return true;
	}
	
	private JPanel createTotalViewerPanel() {
		JPanel ticketAmountPanel = new com.floreantpos.swing.TransparentPanel(new MigLayout("ins 2 2 3 2,alignx trailing,fill", "[grow][]", "")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

		btnTotal = new PosButton("TOTAL");
		ticketAmountPanel.add(btnTotal, "growx,aligny center"); //$NON-NLS-1$

		if (!Application.getInstance().getTerminal().isHasCashDrawer()) {
			btnTotal.setEnabled(false);
			//btnTotal.set
		}

		btnTotal.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				doPayNow();
			}
		});
		return ticketAmountPanel;
	}

	private void createTicketItemControlPanel() {
		ticketItemActionPanel.setLayout(new GridLayout(0, 1, 5, 5));

		btnScrollUp.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				doScrollUp();
			}
		});

		btnIncreaseAmount.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				doIncreaseAmount();
			}
		});

		btnDecreaseAmount.setIcon(IconFactory.getIcon("/ui_icons/", "minus.png")); //$NON-NLS-1$ //$NON-NLS-2$
		btnDecreaseAmount.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				doDecreaseAmount();
			}
		});

		btnScrollDown.setIcon(IconFactory.getIcon("/ui_icons/", "down.png")); //$NON-NLS-1$ //$NON-NLS-2$
		btnScrollDown.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				doScrollDown();
			}
		});

		btnDelete.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				doDeleteSelection();
			}
		});

		btnEdit.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				doEditSelection();
			}
		});

		ticketItemActionPanel.add(btnScrollUp);
		ticketItemActionPanel.add(btnIncreaseAmount);
		ticketItemActionPanel.add(btnDecreaseAmount);
		ticketItemActionPanel.add(btnDelete);
		ticketItemActionPanel.add(btnEdit);
		ticketItemActionPanel.add(btnScrollDown);

		ticketItemActionPanel.setPreferredSize(new Dimension(60, 360));
	}

	public synchronized void doFinishOrder() {// GEN-FIRST:event_doFinishOrder
		sendTicketToKitchen();
		closeView(false);
	}// GEN-LAST:event_doFinishOrder

	public synchronized void sendTicketToKitchen() {// GEN-FIRST:event_doFinishOrder
		saveTicketIfNeeded();

		if (ticket.needsKitchenPrint()) {
			ReceiptPrintService.printToKitchen(ticket);
			TicketDAO.getInstance().refresh(ticket);
		}

		OrderController.saveOrder(ticket);
	}

	public void saveTicketIfNeeded() {
		updateModel();

		TicketDAO ticketDAO = TicketDAO.getInstance();

		if (ticket.getId() == null) {
			// save ticket first. ticket needs to save so that it
			// contains an id.
			OrderController.saveOrder(ticket);
			ticketDAO.refresh(ticket);
		}
	}

	private void closeView(boolean orderCanceled) {
		if (TerminalConfig.isCashierMode()) {
			RootView.getInstance().showView(CashierSwitchBoardView.VIEW_NAME);
		}
		else {
			RootView.getInstance().showView(SwitchboardView.VIEW_NAME);
		}
	}

	public void doCancelOrder() {// GEN-FIRST:event_doCancelOrder
		closeView(true);
	}// GEN-LAST:event_doCancelOrder

	private synchronized void updateModel() {
		if (ticket.getTicketItems() == null || ticket.getTicketItems().size() == 0) {
			throw new PosException(com.floreantpos.POSConstants.TICKET_IS_EMPTY_);
		}

		ticket.calculatePrice();
	}

	private void doPayNow() {// GEN-FIRST:event_doPayNow
		try {
			if (!POSUtil.checkDrawerAssignment()) {
				return;
			}

			updateModel();

			OrderController.saveOrder(ticket);

			firePayOrderSelected();
		} catch (PosException e) {
			POSMessageDialog.showError(e.getMessage());
		}
	}// GEN-LAST:event_doPayNow

	private void doDeleteSelection() {// GEN-FIRST:event_doDeleteSelection
		ticketViewerTable.deleteSelectedItem();
		updateView();

	}// GEN-LAST:event_doDeleteSelection

	private void doEditSelection() {// GEN-FIRST:event_doDeleteSelection
		Object object = ticketViewerTable.getSelected();

		if (object instanceof TicketItemModifier) {
			TicketItemModifier ticketItemModifier = (TicketItemModifier) object;
			OrderController.openModifierDialog(ticketItemModifier);
		}
		updateView();

	}// GEN-LAST:event_doDeleteSelection

	private void doIncreaseAmount() {// GEN-FIRST:event_doIncreaseAmount
		if (ticketViewerTable.increaseItemAmount()) {
			updateView();
		}

	}// GEN-LAST:event_doIncreaseAmount

	private void doDecreaseAmount() {// GEN-FIRST:event_doDecreaseAmount
		if (ticketViewerTable.decreaseItemAmount()) {
			updateView();
		}
	}// GEN-LAST:event_doDecreaseAmount

	private void doScrollDown() {// GEN-FIRST:event_doScrollDown
		ticketViewerTable.scrollDown();
	}// GEN-LAST:event_doScrollDown

	private void doScrollUp() {// GEN-FIRST:event_doScrollUp
		ticketViewerTable.scrollUp();
	}// GEN-LAST:event_doScrollUp

	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket _ticket) {
		this.ticket = _ticket;

		ticketViewerTable.setTicket(_ticket);

		updateView();
	}

	public void addTicketItem(TicketItem ticketItem) {
		ticketViewerTable.addTicketItem(ticketItem);
		updateView();
	}

	public void removeModifier(TicketItem parent, TicketItemModifier modifier) {
		modifier.setItemCount(0);
		//modifier.setModifierType(TicketItemModifier.MODIFIER_NOT_INITIALIZED);
		ticketViewerTable.removeModifier(parent, modifier);
	}

	public void selectRow(int rowIndex) {
		ticketViewerTable.selectRow(rowIndex);
	}

	public void updateView() {
		if (ticket == null) {
			btnTotal.setText("TOTAL ");
			titledBorder.setTitle(Messages.getString("TicketView.36")); //$NON-NLS-1$
			return;
		}

		ticket.calculatePrice();
		btnTotal.setText("TOTAL $" + NumberUtil.formatNumber(ticket.getTotalAmount()));

		if (ticket.getId() == null) {
			titledBorder.setTitle(Messages.getString("TicketView.36")); //$NON-NLS-1$
		}
		else {
			titledBorder.setTitle(Messages.getString("TicketView.37") + ticket.getId()); //$NON-NLS-1$
		}

		//		if (ticket.getType() != null && ticket.getType().getProperties() != null) {
		//			btnDone.setVisible(ticket.getType().getProperties().isPostPaid());
		//		}
		//		else {
		//			btnDone.setVisible(true);
		//		}

		ticketViewerTable.updateView();
	}

	public void addOrderListener(OrderListener listenre) {
		orderListeners.add(listenre);
	}

	public void removeOrderListener(OrderListener listenre) {
		orderListeners.remove(listenre);
	}

	public void firePayOrderSelected() {
		for (OrderListener listener : orderListeners) {
			listener.payOrderSelected(getTicket());
		}
	}

	public void setControlsVisible(boolean visible) {
		if (visible) {
			btnIncreaseAmount.setEnabled(true);
			btnDecreaseAmount.setEnabled(true);
			btnDelete.setEnabled(true);
		}
		else {
			btnIncreaseAmount.setEnabled(false);
			btnDecreaseAmount.setEnabled(false);
			btnDelete.setEnabled(false);
		}
	}

	public com.floreantpos.ui.ticket.TicketViewerTable getTicketViewerTable() {
		return ticketViewerTable;
	}

	private class TicketItemSelectionListener implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent e) {
			Object selected = ticketViewerTable.getSelected();
			if (!(selected instanceof ITicketItem)) {
				return;
			}
			if (selected instanceof TicketItemModifier) {
				btnIncreaseAmount.setEnabled(false);
				btnDecreaseAmount.setEnabled(false);
				btnEdit.setEnabled(true);
				btnDelete.setEnabled(false);
			}
			else {
				btnEdit.setEnabled(false);
				btnDelete.setEnabled(true);

				if (selected instanceof TicketItem) {
					TicketItem ticketItem = (TicketItem) selected;
					if (ticketItem.isHasModifiers()) {
						btnIncreaseAmount.setEnabled(false);
						btnDecreaseAmount.setEnabled(false);
						btnEdit.setEnabled(true);
					}
					else {
						btnIncreaseAmount.setEnabled(true);
						btnDecreaseAmount.setEnabled(true);
					}
				}
			}
		}
	}
}
