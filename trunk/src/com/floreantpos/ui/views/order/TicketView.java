/*
 * TicketView.java
 *
 * Created on August 4, 2006, 3:42 PM
 */

package com.floreantpos.ui.views.order;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
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
import com.floreantpos.POSConstants;
import com.floreantpos.PosException;
import com.floreantpos.config.TerminalConfig;
import com.floreantpos.customer.CustomerSelectionDialog;
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
import com.floreantpos.util.PosGuiUtil;

/**
 * 
 * @author MShahriar
 */
public class TicketView extends JPanel {
	private java.util.Vector<OrderListener> orderListeners = new java.util.Vector<OrderListener>();
	private Ticket ticket;

	public final static String VIEW_NAME = "TICKET_VIEW";

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

		btnPay = new com.floreantpos.swing.PosButton();
		btnCancel = new com.floreantpos.swing.PosButton();
		btnSave = new com.floreantpos.swing.PosButton();
		ticketItemActionPanel = new com.floreantpos.swing.TransparentPanel();
		btnDecreaseAmount = new com.floreantpos.swing.PosButton();
		btnScrollDown = new com.floreantpos.swing.PosButton();
		ticketViewerTable = new com.floreantpos.ui.ticket.TicketViewerTable();
		ticketScrollPane = new PosScrollPane(ticketViewerTable);
		ticketScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		ticketScrollPane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		ticketScrollPane.setPreferredSize(new java.awt.Dimension(180, 200));

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

		ticketViewerTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {

				Object selected = ticketViewerTable.getSelected();
				if (!(selected instanceof ITicketItem)) {
					return;
				}

				ITicketItem item = (ITicketItem) selected;

				Boolean printedToKitchen = item.isPrintedToKitchen();

				btnCookingInstruction.setEnabled(item.canAddCookingInstruction());
				btnIncreaseAmount.setEnabled(!printedToKitchen);
				btnDecreaseAmount.setEnabled(!printedToKitchen);
				btnDelete.setEnabled(!printedToKitchen);
			}

		});

		setPreferredSize(new java.awt.Dimension(480, 463));

	}// </editor-fold>//GEN-END:initComponents

	private void createTicketActionPanel() {
		ticketActionPanel.setLayout(new GridLayout(1, 0, 5, 5));

		btnCancel.setText(POSConstants.CANCEL_BUTTON_TEXT);
		btnCancel.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				doCancelOrder(evt);
			}
		});

		btnSave.setText(com.floreantpos.POSConstants.SAVE_BUTTON_TEXT);
		btnSave.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				doFinishOrder(evt);
			}
		});

		btnPay.setText(com.floreantpos.POSConstants.PAY_BUTTON_TEXT);
		btnPay.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				doPayNow(evt);
			}
		});

		btnMore.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				extraActionPanel.setCollapsed(!btnMore.isSelected());
			}
		});

		if (Application.getInstance().getTerminal().isHasCashDrawer()) {
			ticketActionPanel.add(btnPay);
		}
		ticketActionPanel.add(btnSave);
		ticketActionPanel.add(btnMore);
		ticketActionPanel.add(btnCancel);

	}

	private JPanel createTotalViewerPanel() {
		lblSubtotal = new javax.swing.JLabel();
		lblSubtotal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblSubtotal.setText(com.floreantpos.POSConstants.SUBTOTAL + ":");

		tfSubtotal = new javax.swing.JTextField(10);
		tfSubtotal.setHorizontalAlignment(SwingConstants.TRAILING);
		tfSubtotal.setEditable(false);

		lblTax = new javax.swing.JLabel();
		lblTax.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblTax.setText(com.floreantpos.POSConstants.TAX + ":");

		tfTax = new javax.swing.JTextField();
		tfTax.setEditable(false);
		tfTax.setHorizontalAlignment(SwingConstants.TRAILING);

		lblTotal = new javax.swing.JLabel();
		lblTotal.setFont(lblTotal.getFont().deriveFont(Font.BOLD, 16));
		lblTotal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblTotal.setText(com.floreantpos.POSConstants.TOTAL + ":");
		tfTotal = new javax.swing.JTextField(10);
		tfTotal.setFont(tfTotal.getFont().deriveFont(Font.BOLD, 16));
		tfTotal.setHorizontalAlignment(SwingConstants.TRAILING);
		tfTotal.setEditable(false);

		JPanel ticketAmountPanel = new com.floreantpos.swing.TransparentPanel(new MigLayout("ins 2 2 3 2,alignx trailing,fill", "[grow][]", ""));

		ticketAmountPanel.add(lblSubtotal, "cell 0 1,growx,aligny center");
		ticketAmountPanel.add(tfSubtotal, "cell 1 1,growx,aligny center");
		ticketAmountPanel.add(lblTax, "cell 0 3,growx,aligny center");
		ticketAmountPanel.add(tfTax, "cell 1 3,growx,aligny center");
		ticketAmountPanel.add(lblTotal, "cell 0 5,growx,aligny center");
		ticketAmountPanel.add(tfTotal, "cell 1 5,growx,aligny center");

		return ticketAmountPanel;
	}

	private void createTicketItemControlPanel() {
		ticketItemActionPanel.setLayout(new GridLayout(0, 1, 5, 5));

		btnScrollUp.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				doScrollUp(evt);
			}
		});

		btnIncreaseAmount.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				doIncreaseAmount(evt);
			}
		});

		btnDecreaseAmount.setIcon(IconFactory.getIcon("/ui_icons/", "minus.png"));
		btnDecreaseAmount.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				doDecreaseAmount(evt);
			}
		});

		btnScrollDown.setIcon(IconFactory.getIcon("/ui_icons/", "down.png"));
		btnScrollDown.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				doScrollDown(evt);
			}
		});

		btnDelete.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				doDeleteSelection(evt);
			}
		});

		btnCookingInstruction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doAddCookingInstruction();
			}
		});

		ticketItemActionPanel.add(btnScrollUp);
		ticketItemActionPanel.add(btnIncreaseAmount);
		ticketItemActionPanel.add(btnDecreaseAmount);
		ticketItemActionPanel.add(btnDelete);
		ticketItemActionPanel.add(btnCookingInstruction);
		ticketItemActionPanel.add(btnScrollDown);

		ticketItemActionPanel.setPreferredSize(new Dimension(70, 360));
	}

	protected void doAddCookingInstruction() {

		try {
			Object object = ticketViewerTable.getSelected();
			if (!(object instanceof TicketItem)) {
				POSMessageDialog.showError("Please select and item");
				return;
			}

			TicketItem ticketItem = (TicketItem) object;

			if (ticketItem.isPrintedToKitchen()) {
				POSMessageDialog.showError("Cooking instruction cannot be added to item already printed to kitchen");
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

	private synchronized void doFinishOrder(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_doFinishOrder
		try {

			updateModel();

			TicketDAO ticketDAO = TicketDAO.getInstance();

			if (ticket.getId() == null) {
				// save ticket first. ticket needs to save so that it
				// contains an id.
				OrderController.saveOrder(ticket);
				ticketDAO.refresh(ticket);
			}

			if (ticket.needsKitchenPrint()) {
				ReceiptPrintService.printToKitchen(ticket);
				ticketDAO.refresh(ticket);
			}

			OrderController.saveOrder(ticket);

			closeView(false);

		} catch (StaleObjectStateException e) {
			POSMessageDialog.showError("It seems the ticket has been modified by some other person or terminal. Save failed.");
			return;
		} catch (PosException x) {
			POSMessageDialog.showError(x.getMessage());
		} catch (Exception e) {
			POSMessageDialog.showError(Application.getPosWindow(), POSConstants.ERROR_MESSAGE, e);
		}
	}// GEN-LAST:event_doFinishOrder

	private void closeView(boolean orderCanceled) {
		if (TerminalConfig.isCashierMode()) {
			// String message = "Order canceled. What do you want to do next?";
			// if(!orderCanceled) {
			// message = "Ticket no " + getTicket().getId() +
			// " saved. What do you want to do next?";
			// }
			//
			// Window ancestor = SwingUtilities.getWindowAncestor(this);
			// CashierModeNextActionDialog dialog = new
			// CashierModeNextActionDialog((Frame) ancestor, message);
			// dialog.open();
			RootView.getInstance().showView(CashierSwitchBoardView.VIEW_NAME);
		}
		else {
			RootView.getInstance().showView(SwitchboardView.VIEW_NAME);
		}
	}

	private void doCancelOrder(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_doCancelOrder
		closeView(true);
	}// GEN-LAST:event_doCancelOrder

	private synchronized void updateModel() {
		if (ticket.getTicketItems() == null || ticket.getTicketItems().size() == 0) {
			throw new PosException(com.floreantpos.POSConstants.TICKET_IS_EMPTY_);
		}

		ticket.calculatePrice();
	}

	private void doPayNow(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_doPayNow
		try {
			if (!Application.getInstance().getTerminal().isCashDrawerAssigned()) {
				POSMessageDialog.showError("Unable to accept payment. Configuration error or Drawer has not been assigned.");
				return;
			}

			updateModel();

			OrderController.saveOrder(ticket);

			firePayOrderSelected();
		} catch (PosException e) {
			POSMessageDialog.showError(e.getMessage());
		}
	}// GEN-LAST:event_doPayNow

	private void doDeleteSelection(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_doDeleteSelection
		Object object = ticketViewerTable.deleteSelectedItem();
		if (object != null) {
			updateView();

			// if (object instanceof TicketItemModifier) {
			// ModifierView modifierView =
			// OrderView.getInstance().getModifierView();
			// if (modifierView.isVisible()) {
			// modifierView.updateVisualRepresentation();
			// }
			// }
		}

	}// GEN-LAST:event_doDeleteSelection

	private void doIncreaseAmount(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_doIncreaseAmount
		if (ticketViewerTable.increaseItemAmount()) {
			// ModifierView modifierView =
			// OrderView.getInstance().getModifierView();
			// if (modifierView.isVisible()) {
			// modifierView.updateVisualRepresentation();
			// }
			updateView();
		}

	}// GEN-LAST:event_doIncreaseAmount

	private void doDecreaseAmount(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_doDecreaseAmount
		if (ticketViewerTable.decreaseItemAmount()) {
			// ModifierView modifierView =
			// OrderView.getInstance().getModifierView();
			// if (modifierView.isVisible()) {
			// modifierView.updateVisualRepresentation();
			// }
			updateView();
		}
	}// GEN-LAST:event_doDecreaseAmount

	private void doScrollDown(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_doScrollDown
		ticketViewerTable.scrollDown();
	}// GEN-LAST:event_doScrollDown

	private void doScrollUp(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_doScrollUp
		ticketViewerTable.scrollUp();
	}// GEN-LAST:event_doScrollUp

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private com.floreantpos.swing.TransparentPanel ticketActionPanel = new com.floreantpos.swing.TransparentPanel();
	private com.floreantpos.swing.PosButton btnCancel;
	private com.floreantpos.swing.PosButton btnDecreaseAmount;
	private com.floreantpos.swing.PosButton btnDelete = new PosButton(IconFactory.getIcon("/ui_icons/", "delete.png"));
	private com.floreantpos.swing.PosButton btnSave;
	private com.floreantpos.swing.PosButton btnIncreaseAmount = new PosButton(IconFactory.getIcon("/ui_icons/", "add_user.png"));
	private com.floreantpos.swing.PosButton btnPay;
	private com.floreantpos.swing.POSToggleButton btnMore = new POSToggleButton(POSConstants.MORE_ACTIVITY_BUTTON_TEXT);
	private com.floreantpos.swing.PosButton btnScrollDown;
	private com.floreantpos.swing.PosButton btnScrollUp = new PosButton(IconFactory.getIcon("/ui_icons/", "up.png"));
	private javax.swing.JLabel lblTax;
	private javax.swing.JLabel lblSubtotal;
	private javax.swing.JLabel lblTotal;
	private com.floreantpos.swing.TransparentPanel ticketItemActionPanel;
	private javax.swing.JScrollPane ticketScrollPane;
	private javax.swing.JTextField tfSubtotal;
	private javax.swing.JTextField tfTax;
	private javax.swing.JTextField tfTotal;
	private com.floreantpos.ui.ticket.TicketViewerTable ticketViewerTable;
	private ExtraTicketActionPanel extraActionPanel = new ExtraTicketActionPanel();
	private PosButton btnCookingInstruction = new PosButton(IconFactory.getIcon("/ui_icons/", "cooking-instruction.png"));
	private TitledBorder titledBorder = new TitledBorder("");
	private Border border = new CompoundBorder(titledBorder, new EmptyBorder(5, 5, 5, 5));

	// End of variables declaration//GEN-END:variables

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
		modifier.setModifierType(TicketItemModifier.MODIFIER_NOT_INITIALIZED);
		ticketViewerTable.removeModifier(parent, modifier);
	}

	public void updateAllView() {
		ticketViewerTable.updateView();
		updateView();
	}

	public void selectRow(int rowIndex) {
		ticketViewerTable.selectRow(rowIndex);
	}

	public void updateView() {
		if (ticket == null) {
			tfSubtotal.setText("");
			tfTax.setText("");
			tfTotal.setText("");
			titledBorder.setTitle("Ticket [ NEW ]");
			return;
		}

		ticket.calculatePrice();

		tfSubtotal.setText(NumberUtil.formatNumber(ticket.getSubtotalAmount()));

		if (Application.getInstance().isPriceIncludesTax()) {
			tfTax.setText("INCLUDED");
		}
		else {
			tfTax.setText(NumberUtil.formatNumber(ticket.getTaxAmount()));
		}
		tfTotal.setText(NumberUtil.formatNumber(ticket.getTotalAmount()));

		if (ticket.getId() == null) {
			titledBorder.setTitle("Ticket [ NEW ]");
		}
		else {
			titledBorder.setTitle("Ticket #" + ticket.getId());
		}

		if (ticket.getType() != null && ticket.getType().getProperties() != null) {
			btnSave.setVisible(ticket.getType().getProperties().isPostPaid());
		}
		else {
			btnSave.setVisible(true);
		}
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

			ModifierView modifierView = orderView.getModifierView();

			if (selectedTicketItem.isHasModifiers()) {
				MenuItemDAO dao = new MenuItemDAO();
				MenuItem menuItem = dao.get(selectedTicketItem.getItemId());
				if (!menuItem.equals(modifierView.getMenuItem())) {
					menuItem = dao.initialize(menuItem);
					modifierView.setMenuItem(menuItem, selectedTicketItem);
				}

				MenuCategory menuCategory = menuItem.getParent().getParent();
				orderView.getCategoryView().setSelectedCategory(menuCategory);

				modifierView.clearSelection();
				orderView.showView(ModifierView.VIEW_NAME);
			}
			else {
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
			}
		}
		else if (selectedObject instanceof TicketItemModifier) {
			selectedTicketItem = ((TicketItemModifier) selectedObject).getParent().getParent();
			if (selectedTicketItem == null)
				return;

			ModifierView modifierView = orderView.getModifierView();

			if (selectedTicketItem.isHasModifiers()) {
				MenuItemDAO dao = new MenuItemDAO();
				MenuItem menuItem = dao.get(selectedTicketItem.getItemId());
				if (!menuItem.equals(modifierView.getMenuItem())) {
					menuItem = dao.initialize(menuItem);
					modifierView.setMenuItem(menuItem, selectedTicketItem);
				}

				MenuCategory menuCategory = menuItem.getParent().getParent();
				orderView.getCategoryView().setSelectedCategory(menuCategory);

				TicketItemModifier ticketItemModifier = (TicketItemModifier) selectedObject;
				ticketItemModifier.setSelected(true);
				modifierView.select(ticketItemModifier);

				orderView.showView(ModifierView.VIEW_NAME);
			}
		}
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

			setBorder(new CompoundBorder(new EmptyBorder(10, 2, 2, 1), new TitledBorder("")));
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
			dialog.open();
			if (!dialog.isCanceled()) {
				TicketItem ticketItem = dialog.getTicketItem();
				ticketItem.setTicket(ticket);
				ticketItem.calculatePrice();
				RootView.getInstance().getOrderView().getTicketView().addTicketItem(ticketItem);
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

				FloorLayoutPlugin floorLayoutPlugin = Application.getPluginManager().getPlugin(FloorLayoutPlugin.class);
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
					shopTable.setOccupied(true);
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
			List<ShopTable> tables2 = ShopTableDAO.getInstance().getTables(thisTicket);

			if (tables2 == null)
				return;

			for (ShopTable shopTable : tables2) {
				shopTable.setOccupied(false);
				shopTable.setBooked(false);

				session.saveOrUpdate(shopTable);
			}

			tables2.clear();
		}

		private com.floreantpos.swing.PosButton btnGuestNo;
		private com.floreantpos.swing.PosButton btnMisc;
		private com.floreantpos.swing.PosButton btnOrderType;
		private com.floreantpos.swing.PosButton btnTableNumber;
		private com.floreantpos.swing.PosButton btnCustomer;
		private com.floreantpos.swing.PosButton btnSearchItem;
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

		public ItemSelectionListener getItemSelectionListener() {
			return itemSelectionListener;
		}

		public void setItemSelectionListener(ItemSelectionListener itemSelectionListener) {
			this.itemSelectionListener = itemSelectionListener;
		}

		public void searchItem() {
			int itemId = NumberSelectionDialog2.takeIntInput("Enter or scan item id");

			if (itemId == -1) {
				return;
			}

			MenuItem menuItem = MenuItemDAO.getInstance().get(itemId);
			if (menuItem == null) {
				POSMessageDialog.showError("Item not found");
				return;
			}
			itemSelectionListener.itemSelected(menuItem);
		}
	}

}
