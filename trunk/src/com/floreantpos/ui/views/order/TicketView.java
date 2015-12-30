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
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.miginfocom.swing.MigLayout;

import org.hibernate.Session;
import org.hibernate.StaleObjectStateException;
import org.jdesktop.swingx.JXCollapsiblePane;

import com.floreantpos.IconFactory;
import com.floreantpos.Messages;
import com.floreantpos.POSConstants;
import com.floreantpos.PosException;
import com.floreantpos.config.TerminalConfig;
import com.floreantpos.customer.CustomerSelectionDialog;
import com.floreantpos.extension.ExtensionManager;
import com.floreantpos.extension.FloorLayoutPlugin;
import com.floreantpos.main.Application;
import com.floreantpos.model.CookingInstruction;
import com.floreantpos.model.ITicketItem;
import com.floreantpos.model.MenuCategory;
import com.floreantpos.model.MenuGroup;
import com.floreantpos.model.MenuItem;
import com.floreantpos.model.OrderType;
import com.floreantpos.model.ShopTable;
import com.floreantpos.model.Ticket;
import com.floreantpos.model.TicketItem;
import com.floreantpos.model.TicketItemCookingInstruction;
import com.floreantpos.model.TicketItemModifier;
import com.floreantpos.model.dao.CookingInstructionDAO;
import com.floreantpos.model.dao.MenuItemDAO;
import com.floreantpos.model.dao.ShopTableDAO;
import com.floreantpos.model.dao.TicketDAO;
import com.floreantpos.report.ReceiptPrintService;
import com.floreantpos.swing.POSToggleButton;
import com.floreantpos.swing.PosButton;
import com.floreantpos.swing.PosScrollPane;
import com.floreantpos.ui.dialog.BeanEditorDialog;
import com.floreantpos.ui.dialog.MiscTicketItemDialog;
import com.floreantpos.ui.dialog.NumberSelectionDialog2;
import com.floreantpos.ui.dialog.POSMessageDialog;
import com.floreantpos.ui.views.CashierSwitchBoardView;
import com.floreantpos.ui.views.CookingInstructionSelectionView;
import com.floreantpos.ui.views.SwitchboardView;
import com.floreantpos.ui.views.order.actions.ItemSelectionListener;
import com.floreantpos.ui.views.order.actions.OrderListener;
import com.floreantpos.util.NumberUtil;
import com.floreantpos.util.POSUtil;
import com.floreantpos.util.PosGuiUtil;

/**
 * 
 * @author MShahriar
 */
public class TicketView extends JPanel {
	private java.util.Vector<OrderListener> orderListeners = new java.util.Vector<OrderListener>();
	private Ticket ticket;
	private com.floreantpos.swing.TransparentPanel ticketActionPanel = new com.floreantpos.swing.TransparentPanel();
	private com.floreantpos.swing.PosButton btnCancel;
	private com.floreantpos.swing.PosButton btnDecreaseAmount;
	private com.floreantpos.swing.PosButton btnDelete = new PosButton(IconFactory.getIcon("/ui_icons/", "delete.png")); //$NON-NLS-1$ //$NON-NLS-2$
	private com.floreantpos.swing.PosButton btnDone;
	private com.floreantpos.swing.PosButton btnSend;
	private com.floreantpos.swing.PosButton btnIncreaseAmount = new PosButton(IconFactory.getIcon("/ui_icons/", "add_user.png")); //$NON-NLS-1$ //$NON-NLS-2$
	private com.floreantpos.swing.PosButton btnEdit = new PosButton("..."); //$NON-NLS-1$ //$NON-NLS-2$
	private com.floreantpos.swing.POSToggleButton btnMore = new POSToggleButton(POSConstants.MORE_ACTIVITY_BUTTON_TEXT);
	private com.floreantpos.swing.PosButton btnScrollDown;
	private com.floreantpos.swing.PosButton btnScrollUp = new PosButton(IconFactory.getIcon("/ui_icons/", "up.png")); //$NON-NLS-1$ //$NON-NLS-2$
	private com.floreantpos.swing.TransparentPanel ticketItemActionPanel;
	private javax.swing.JScrollPane ticketScrollPane;
	private PosButton btnTotal;
	private com.floreantpos.ui.ticket.TicketViewerTable ticketViewerTable;
	private ExtraTicketActionPanel extraActionPanel = new ExtraTicketActionPanel();

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

		btnCancel = new com.floreantpos.swing.PosButton();
		btnDone = new com.floreantpos.swing.PosButton();
		btnSend = new com.floreantpos.swing.PosButton();
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

		createTicketActionPanel();

		createTicketItemControlPanel();

		JPanel centerPanel = new JPanel(new BorderLayout(5, 5));
		centerPanel.add(ticketScrollPane);
		centerPanel.add(totalViewPanel, BorderLayout.SOUTH);

		add(centerPanel);
		add(ticketActionPanel, BorderLayout.SOUTH);
		add(ticketItemActionPanel, BorderLayout.EAST);
		ticketViewerTable.getRenderer().setInTicketScreen(true);
		ticketViewerTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					updateSelectionView();
				}
			}
		});
		ticketViewerTable.getSelectionModel().addListSelectionListener(new TicketItemSelectionListener());

		getExtraActionPanel().updateView(null);
		setPreferredSize(new java.awt.Dimension(360, 463));

	}// </editor-fold>//GEN-END:initComponents

	private void createTicketActionPanel() {
		ticketActionPanel.setLayout(new GridLayout(1, 0, 5, 5));

		btnCancel.setText(POSConstants.CANCEL_BUTTON_TEXT);
		btnCancel.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				doCancelOrder();
			}
		});

		btnDone.setText(com.floreantpos.POSConstants.SAVE_BUTTON_TEXT);
		btnDone.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				doFinishOrder();
			}
		});

		btnMore.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				extraActionPanel.setCollapsed(!btnMore.isSelected());
			}
		});
		btnSend.setText(com.floreantpos.POSConstants.SEND_TO_KITCHEN);
		btnSend.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				sendTicketToKitchen();
				ticketViewerTable.updateView();
				POSMessageDialog.showMessage("Items sent to kitchen");
			}
		});
		ticketActionPanel.add(btnSend);
		ticketActionPanel.add(btnDone);
		ticketActionPanel.add(btnMore);
		ticketActionPanel.add(btnCancel);
	}

	private JPanel createTotalViewerPanel() {
		JPanel ticketAmountPanel = new com.floreantpos.swing.TransparentPanel(new MigLayout("ins 2 2 3 2,alignx trailing,fill", "[grow][]", "")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

		btnTotal = new PosButton("TOTAL");
		ticketAmountPanel.add(btnTotal, "growx,aligny center"); //$NON-NLS-1$
		
		if(!Application.getInstance().getTerminal().isHasCashDrawer()) {
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

	protected void doAddCookingInstruction() {

		try {
			Object object = ticketViewerTable.getSelected();
			if (!(object instanceof TicketItem)) {
				POSMessageDialog.showError(Application.getPosWindow(), Messages.getString("TicketView.20")); //$NON-NLS-1$
				return;
			}

			TicketItem ticketItem = (TicketItem) object;

			if (ticketItem.isPrintedToKitchen()) {
				POSMessageDialog.showError(Application.getPosWindow(), Messages.getString("TicketView.21")); //$NON-NLS-1$
				return;
			}

			List<CookingInstruction> list = CookingInstructionDAO.getInstance().findAll();
			CookingInstructionSelectionView cookingInstructionSelectionView = new CookingInstructionSelectionView();
			BeanEditorDialog dialog = new BeanEditorDialog(cookingInstructionSelectionView);
			dialog.setBean(list);
			dialog.setSize(800, 600);
			dialog.setLocationRelativeTo(Application.getPosWindow());
			dialog.setVisible(true);

			if (dialog.isCanceled()) {
				return;
			}

			List<TicketItemCookingInstruction> instructions = cookingInstructionSelectionView.getTicketItemCookingInstructions();
			ticketItem.addCookingInstructions(instructions);

			ticketViewerTable.updateView();
		} catch (Exception e) {
			e.printStackTrace();
			POSMessageDialog.showError(e.getMessage());
		}
	}

	private synchronized void doFinishOrder() {// GEN-FIRST:event_doFinishOrder
		try {
			
			sendTicketToKitchen();
			closeView(false);

		} catch (StaleObjectStateException e) {
			POSMessageDialog.showError(Application.getPosWindow(), Messages.getString("TicketView.22")); //$NON-NLS-1$
			return;
		} catch (PosException x) {
			POSMessageDialog.showError(x.getMessage());
		} catch (Exception e) {
			POSMessageDialog.showError(Application.getPosWindow(), POSConstants.ERROR_MESSAGE, e);
		}
	}// GEN-LAST:event_doFinishOrder
	
	private synchronized void sendTicketToKitchen() {// GEN-FIRST:event_doFinishOrder
		try {
			saveTicketIfNeeded();

			if (ticket.needsKitchenPrint()) {
				ReceiptPrintService.printToKitchen(ticket);
				TicketDAO.getInstance().refresh(ticket);
			}
			
			OrderController.saveOrder(ticket);
			
		} catch (StaleObjectStateException e) {
			POSMessageDialog.showError(Application.getPosWindow(), Messages.getString("TicketView.22")); //$NON-NLS-1$
			return;
		} catch (PosException x) {
			POSMessageDialog.showError(x.getMessage());
		} catch (Exception e) {
			POSMessageDialog.showError(Application.getPosWindow(), POSConstants.ERROR_MESSAGE, e);
		}
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

	private void doCancelOrder() {// GEN-FIRST:event_doCancelOrder
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
		extraActionPanel.updateView();
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

		if (ticket.getType() != null && ticket.getType().getProperties() != null) {
			btnDone.setVisible(ticket.getType().getProperties().isPostPaid());
		}
		else {
			btnDone.setVisible(true);
		}

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
			ticketActionPanel.setVisible(true);
			btnIncreaseAmount.setEnabled(true);
			btnDecreaseAmount.setEnabled(true);
			btnDelete.setEnabled(true);
		}
		else {
			ticketActionPanel.setVisible(false);
			btnIncreaseAmount.setEnabled(false);
			btnDecreaseAmount.setEnabled(false);
			btnDelete.setEnabled(false);
		}
	}

	private void updateSelectionView() {
		Object selectedObject = ticketViewerTable.getSelected();

		OrderView orderView = OrderView.getInstance();

		TicketItem selectedTicketItem = null;
		if (selectedObject instanceof TicketItem) {
			selectedTicketItem = (TicketItem) selectedObject;

			//			ModifierView modifierView = orderView.getModifierView();

			//			if (selectedTicketItem.isHasModifiers()) {
			//				MenuItemDAO dao = new MenuItemDAO();
			//				MenuItem menuItem = dao.get(selectedTicketItem.getItemId());
			//				if (!menuItem.equals(modifierView.getMenuItem())) {
			//					menuItem = dao.initialize(menuItem);
			//					modifierView.setMenuItem(menuItem, selectedTicketItem);
			//				}
			//
			//				MenuCategory menuCategory = menuItem.getParent().getParent();
			//				orderView.getCategoryView().setSelectedCategory(menuCategory);
			//
			//				modifierView.clearSelection();
			//				//orderView.showView(ModifierView.VIEW_NAME);
			//			}
			//			else {
			MenuItemDAO dao = new MenuItemDAO();
			MenuItem menuItem = dao.get(selectedTicketItem.getItemId());

			if (menuItem != null) {
				MenuGroup menuGroup = menuItem.getParent();
				MenuItemView itemView = OrderView.getInstance().getItemView();
				if (!menuGroup.equals(itemView.getMenuGroup())) {
					itemView.setMenuGroup(menuGroup);
				}

				orderView.showView(MenuItemView.VIEW_NAME);
				itemView.selectItem(menuItem);

				MenuCategory menuCategory = menuGroup.getParent();
				orderView.getCategoryView().setSelectedCategory(menuCategory);
			}
			//			}
		}
		//		else if (selectedObject instanceof TicketItemModifier) {
		//			selectedTicketItem = ((TicketItemModifier) selectedObject).getParent().getParent();
		//			if (selectedTicketItem == null)
		//				return;
		//
		//			ModifierView modifierView = orderView.getModifierView();
		//
		//			if (selectedTicketItem.isHasModifiers()) {
		//				MenuItemDAO dao = new MenuItemDAO();
		//				MenuItem menuItem = dao.get(selectedTicketItem.getItemId());
		//				if (!menuItem.equals(modifierView.getMenuItem())) {
		//					menuItem = dao.initialize(menuItem);
		//					modifierView.setMenuItem(menuItem, selectedTicketItem);
		//				}
		//
		//				MenuCategory menuCategory = menuItem.getParent().getParent();
		//				orderView.getCategoryView().setSelectedCategory(menuCategory);
		//
		//				TicketItemModifier ticketItemModifier = (TicketItemModifier) selectedObject;
		//				ticketItemModifier.setSelected(true);
		//				modifierView.select(ticketItemModifier);
		//
		//				orderView.showView(ModifierView.VIEW_NAME);
		//			}
		//		}
	}

	public ExtraTicketActionPanel getExtraActionPanel() {
		return extraActionPanel;
	}

	public com.floreantpos.ui.ticket.TicketViewerTable getTicketViewerTable() {
		return ticketViewerTable;
	}

	public static void main(String[] args) {
		TicketView ticketView = new TicketView();
		JFrame frame = new JFrame();
		frame.add(ticketView);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

	public class ExtraTicketActionPanel extends JXCollapsiblePane {
		private ItemSelectionListener itemSelectionListener;

		/** Creates new form OthersView */
		private ExtraTicketActionPanel() {
			initComponents();

			setAnimated(false);
		}

		// public ExtraTicketActionPanel(ItemSelectionListener
		// itemSelectionListener) {
		// initComponents();
		//
		// setItemSelectionListener(itemSelectionListener);
		// }

		/**
		 * This method is called from within the constructor to initialize the
		 * form. WARNING: Do NOT modify this code. The content of this method is
		 * always regenerated by the Form Editor.
		 */
		// <editor-fold defaultstate="collapsed"
		// desc=" Generated Code ">//GEN-BEGIN:initComponents
		private void initComponents() {
			setCollapsed(true);
			setAnimated(false);

			buttonPanel = new JPanel();
			btnOrderType = new com.floreantpos.swing.PosButton();
			btnMisc = new com.floreantpos.swing.PosButton();
			btnGuestNo = new com.floreantpos.swing.PosButton();
			btnTableNumber = new com.floreantpos.swing.PosButton();

			setBorder(new CompoundBorder(new EmptyBorder(10, 2, 2, 1), new TitledBorder(""))); //$NON-NLS-1$
			setLayout(new BorderLayout());

			btnSearchItem = new PosButton(POSConstants.SEARCH_ITEM_BUTTON_TEXT);
			btnSearchItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					searchItem();
				}
			});

			buttonPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
			buttonPanel.setLayout(new java.awt.GridLayout(2, 0, 5, 5));

			btnOrderType.setText(com.floreantpos.POSConstants.ORDER_TYPE_BUTTON_TEXT);
			btnOrderType.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					// doViewOrderInfo();
					doChangeOrderType();
				}
			});

			btnCustomer = new PosButton(POSConstants.CUSTOMER_SELECTION_BUTTON_TEXT);
			btnCustomer.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					doAddEditCustomer();
				}
			});

			btnMisc.setText(com.floreantpos.POSConstants.MISC_BUTTON_TEXT);
			btnMisc.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					doInsertMisc(evt);
				}
			});

			btnGuestNo.setText(com.floreantpos.POSConstants.GUEST_NO_BUTTON_TEXT);
			btnGuestNo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					btnCustomerNumberActionPerformed(evt);
				}
			});

			btnTableNumber.setText(com.floreantpos.POSConstants.TABLE_NO_BUTTON_TEXT);
			btnTableNumber.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					btnTableNumberActionPerformed(evt);
				}
			});

			btnCookingInstruction.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					doAddCookingInstruction();
				}
			});

			btnDiscount.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ITicketItem selectedObject = (ITicketItem) ticketViewerTable.getSelected();
					if (selectedObject == null || !selectedObject.canAddDiscount()) {
						return;
					}

					double d = NumberSelectionDialog2.takeDoubleInput(
							Messages.getString("TicketView.39"), Messages.getString("TicketView.40"), selectedObject.getDiscountAmount()); //$NON-NLS-1$ //$NON-NLS-2$
					if (Double.isNaN(d)) {
						return;
					}

					if (selectedObject instanceof TicketItem) {
						((TicketItem) selectedObject).setDiscountRate(-1.0);
					}

					selectedObject.setDiscountAmount(d);
					ticketViewerTable.repaint();
					TicketView.this.updateView();
				}
			});

			buttonPanel.add(btnCookingInstruction);
			buttonPanel.add(btnDiscount);
			//buttonPanel.add(btnAddOn);
			//buttonPanel.add(btnVoid);

			buttonPanel.add(btnMisc);
			buttonPanel.add(btnSearchItem);
			buttonPanel.add(btnOrderType);
			buttonPanel.add(btnCustomer);
			buttonPanel.add(btnTableNumber);
			buttonPanel.add(btnGuestNo);

			add(buttonPanel);
		}// </editor-fold>//GEN-END:initComponents

		public void setOrderType(OrderType orderType) {
			ticket.setType(orderType);

			btnGuestNo.setEnabled(orderType == OrderType.DINE_IN);
			btnTableNumber.setEnabled(orderType == OrderType.DINE_IN);
		}

		protected void doChangeOrderType() {
			OrderTypeSelectionDialog dialog = new OrderTypeSelectionDialog();
			dialog.open();

			if (dialog.isCanceled())
				return;

			OrderType selectedOrderType = dialog.getSelectedOrderType();
			setOrderType(selectedOrderType);
		}

		protected void doAddEditCustomer() {
			CustomerSelectionDialog dialog = new CustomerSelectionDialog(getTicket());
			dialog.setSize(800, 650);
			dialog.open();
		}

		private void doInsertMisc(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_doInsertMisc
			MiscTicketItemDialog dialog = new MiscTicketItemDialog();
			dialog.setSize(900, 580);
			dialog.open();
			if (!dialog.isCanceled()) {
				TicketItem ticketItem = dialog.getTicketItem();
				ticketItem.setTicket(ticket);
				ticketItem.calculatePrice();
				OrderView.getInstance().getTicketView().addTicketItem(ticketItem);
			}
		}// GEN-LAST:event_doInsertMisc

		private void btnCustomerNumberActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnCustomerNumberActionPerformed
			updateGuestNumber();
		}// GEN-LAST:event_btnCustomerNumberActionPerformed

		private void updateGuestNumber() {
			Ticket thisTicket = getTicket();
			int guestNumber = thisTicket.getNumberOfGuests();

			NumberSelectionDialog2 dialog = new NumberSelectionDialog2();
			dialog.setTitle(com.floreantpos.POSConstants.NUMBER_OF_GUESTS);
			dialog.setValue(guestNumber);
			dialog.pack();
			dialog.open();

			if (dialog.isCanceled()) {
				return;
			}

			guestNumber = (int) dialog.getValue();
			if (guestNumber == 0) {
				POSMessageDialog.showError(Application.getPosWindow(), com.floreantpos.POSConstants.GUEST_NUMBER_CANNOT_BE_0);
				return;
			}

			thisTicket.setNumberOfGuests(guestNumber);
			updateView();

		}

		private void btnTableNumberActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnTableNumberActionPerformed
			updateTableNumber();
		}// GEN-LAST:event_btnTableNumberActionPerformed

		private void updateTableNumber() {
			Session session = null;
			org.hibernate.Transaction transaction = null;

			try {

				Ticket thisTicket = getTicket();

				FloorLayoutPlugin floorLayoutPlugin = (FloorLayoutPlugin) ExtensionManager.getPlugin(FloorLayoutPlugin.class);
				List<ShopTable> tables = null;

				if (floorLayoutPlugin != null) {
					tables = floorLayoutPlugin.captureTableNumbers(thisTicket);
				}
				else {
					tables = PosGuiUtil.captureTable(thisTicket);
				}

				if (tables == null) {
					return;
				}

				session = TicketDAO.getInstance().createNewSession();
				transaction = session.beginTransaction();

				clearShopTable(session, thisTicket);
				session.saveOrUpdate(thisTicket);

				for (ShopTable shopTable : tables) {
					shopTable.setServing(true);
					session.merge(shopTable);

					thisTicket.addTable(shopTable.getTableNumber());
				}

				session.merge(thisTicket);
				transaction.commit();

				updateView();

			} catch (Exception e) {
				e.printStackTrace();
				transaction.rollback();
			} finally {
				if (session != null) {
					session.close();
				}
			}
		}

		private void clearShopTable(Session session, Ticket thisTicket) {
			ShopTableDAO shopTableDao = ShopTableDAO.getInstance();
			List<ShopTable> tables2 = shopTableDao.getTables(thisTicket);

			if (tables2 == null)
				return;

			shopTableDao.releaseAndDeleteTicketTables(thisTicket);

			tables2.clear();
		}

		private com.floreantpos.swing.PosButton btnGuestNo;
		private com.floreantpos.swing.PosButton btnMisc;
		private com.floreantpos.swing.PosButton btnOrderType;
		private com.floreantpos.swing.PosButton btnTableNumber;
		private com.floreantpos.swing.PosButton btnCustomer;
		private com.floreantpos.swing.PosButton btnSearchItem;

		private PosButton btnCookingInstruction = new PosButton(IconFactory.getIcon("/ui_icons/", "cooking-instruction.png")); //$NON-NLS-1$ //$NON-NLS-2$
		private PosButton btnDiscount = new PosButton(Messages.getString("TicketView.43")); //$NON-NLS-1$
		//private PosButton btnAddOn = new PosButton("ADD ON");
		//private PosButton btnVoid = new PosButton("VOID");
		private JPanel buttonPanel;

		// End of variables declaration//GEN-END:variables

		public void updateView() {
			if (ticket != null) {
				if (ticket.getType() != OrderType.DINE_IN) {
					btnGuestNo.setEnabled(false);
					btnTableNumber.setEnabled(false);
				}
				else {
					btnGuestNo.setEnabled(true);
					btnTableNumber.setEnabled(true);

					// btnGuestNo.setText(currentTicket.getNumberOfGuests() +
					// " " + POSConstants.GUEST + "s");
					// btnTableNumber.setText(POSConstants.RECEIPT_REPORT_TABLE_NO_LABEL
					// + ": " + currentTicket.getTableNumbers());
				}
			}
		}

		public void updateView(ITicketItem item) {
			if (item == null) {
				btnCookingInstruction.setEnabled(false);
				btnDiscount.setEnabled(false);
				//				btnVoid.setEnabled(false);
				//				btnAddOn.setEnabled(false);

				return;
			}

			btnCookingInstruction.setEnabled(item.canAddCookingInstruction());
			btnDiscount.setEnabled(item.canAddDiscount());
			//			btnVoid.setEnabled(item.canAddAdOn());
			//			btnAddOn.setEnabled(item.canVoid());
		}

		public ItemSelectionListener getItemSelectionListener() {
			return itemSelectionListener;
		}

		public void setItemSelectionListener(ItemSelectionListener itemSelectionListener) {
			this.itemSelectionListener = itemSelectionListener;
		}

		public void searchItem() {
			int itemId = NumberSelectionDialog2.takeIntInput(Messages.getString("TicketView.44")); //$NON-NLS-1$

			if (itemId == -1) {
				return;
			}

			MenuItem menuItem = MenuItemDAO.getInstance().get(itemId);
			if (menuItem == null) {
				POSMessageDialog.showError(Application.getPosWindow(), Messages.getString("TicketView.45")); //$NON-NLS-1$
				return;
			}
			itemSelectionListener.itemSelected(menuItem);
		}
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
