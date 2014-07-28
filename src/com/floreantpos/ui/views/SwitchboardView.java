/*
 * SwitchboardView.java
 *
 * Created on August 14, 2006, 11:45 PM
 */

package com.floreantpos.ui.views;

import java.awt.ComponentOrientation;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import org.apache.commons.logging.LogFactory;

import com.floreantpos.POSConstants;
import com.floreantpos.PosException;
import com.floreantpos.bo.ui.BackOfficeWindow;
import com.floreantpos.extension.OrderServiceExtension;
import com.floreantpos.main.Application;
import com.floreantpos.model.AttendenceHistory;
import com.floreantpos.model.Shift;
import com.floreantpos.model.Ticket;
import com.floreantpos.model.User;
import com.floreantpos.model.UserPermission;
import com.floreantpos.model.UserType;
import com.floreantpos.model.dao.AttendenceHistoryDAO;
import com.floreantpos.model.dao.TicketDAO;
import com.floreantpos.services.TicketService;
import com.floreantpos.swing.PosButton;
import com.floreantpos.ui.dialog.ManagerDialog;
import com.floreantpos.ui.dialog.NumberSelectionDialog2;
import com.floreantpos.ui.dialog.POSMessageDialog;
import com.floreantpos.ui.dialog.PaymentTypeSelectionDialog;
import com.floreantpos.ui.dialog.PayoutDialog;
import com.floreantpos.ui.dialog.VoidTicketDialog;
import com.floreantpos.ui.views.order.DefaultOrderServiceExtension;
import com.floreantpos.ui.views.order.OrderView;
import com.floreantpos.ui.views.order.RootView;
import com.floreantpos.util.NumberUtil;
import com.floreantpos.util.TicketAlreadyExistsException;

import foxtrot.Job;
import foxtrot.Worker;

/**
 * 
 * @author MShahriar
 */
public class SwitchboardView extends JPanel implements ActionListener {
	public final static String VIEW_NAME = com.floreantpos.POSConstants.SWITCHBOARD;
	
	private OrderServiceExtension orderServiceExtension;
	
//	private Timer ticketListUpdater;

	/** Creates new form SwitchboardView */
	public SwitchboardView() {
		initComponents();

		btnBackOffice.addActionListener(this);
		btnClockOut.addActionListener(this);
		btnEditTicket.addActionListener(this);
		btnGroupSettle.addActionListener(this);
		btnLogout.addActionListener(this);
		btnManager.addActionListener(this);
		btnNewTicket.addActionListener(this);
		btnPayout.addActionListener(this);
		btnOrderInfo.addActionListener(this);
		btnReopenTicket.addActionListener(this);
		btnSettleTicket.addActionListener(this);
		btnShutdown.addActionListener(this);
		btnSplitTicket.addActionListener(this);
		btnTakeout.addActionListener(this);
		btnVoidTicket.addActionListener(this);

		orderServiceExtension = Application.getPluginManager().getPlugin(OrderServiceExtension.class);

		if (orderServiceExtension == null) {
			btnHomeDelivery.setEnabled(false);
			btnPickup.setEnabled(false);
			btnDriveThrough.setEnabled(false);
			btnAssignDriver.setEnabled(false);
			btnFinishOrder.setEnabled(false);
			
			orderServiceExtension = new DefaultOrderServiceExtension();
		}
//		ticketListUpdater = new Timer(30 * 1000, new TicketListUpdaterTask());

		applyComponentOrientation(ComponentOrientation.getOrientation(Locale.getDefault()));
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	// <editor-fold defaultstate="collapsed" desc=" Generated Code
	// <editor-fold defaultstate="collapsed"
	// desc=" Generated Code ">//GEN-BEGIN:initComponents
	private void initComponents() {

		javax.swing.JPanel statusPanel = new javax.swing.JPanel();
		lblUserName = new javax.swing.JLabel();
		javax.swing.JPanel bottomPanel = new javax.swing.JPanel();
		javax.swing.JPanel bottomLeftPanel = new javax.swing.JPanel();
		openTicketList = new com.floreantpos.ui.TicketListView();
		javax.swing.JPanel activityPanel = new javax.swing.JPanel();
		btnNewTicket = new com.floreantpos.swing.PosButton();
		btnEditTicket = new com.floreantpos.swing.PosButton();
		btnVoidTicket = new com.floreantpos.swing.PosButton();
		btnPayout = new com.floreantpos.swing.PosButton();
		btnOrderInfo = new com.floreantpos.swing.PosButton();
		javax.swing.JPanel bottomRightPanel = new javax.swing.JPanel();
		btnShutdown = new com.floreantpos.swing.PosButton();
		btnLogout = new com.floreantpos.swing.PosButton();
		btnBackOffice = new com.floreantpos.swing.PosButton();
		btnManager = new com.floreantpos.swing.PosButton();
		btnClockOut = new com.floreantpos.swing.PosButton();

		setLayout(new java.awt.BorderLayout(10, 10));

		lblUserName.setFont(new java.awt.Font("Tahoma", 1, 18));
		lblUserName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		statusPanel.add(lblUserName, java.awt.BorderLayout.PAGE_START);

		add(statusPanel, java.awt.BorderLayout.NORTH);

		bottomPanel.setLayout(new java.awt.BorderLayout(5, 5));

		bottomLeftPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, POSConstants.OPEN_TICKETS_AND_ACTIVITY,
				javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));
		bottomLeftPanel.setLayout(new java.awt.BorderLayout(5, 5));
		bottomLeftPanel.add(openTicketList, java.awt.BorderLayout.CENTER);

		activityPanel.setPreferredSize(new java.awt.Dimension(655, 150));
		activityPanel.setLayout(new java.awt.GridLayout(3, 0, 5, 5));

		btnNewTicket.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/new_ticket_32.png")));
		btnNewTicket.setText("DINE IN");
		activityPanel.add(btnNewTicket);
		btnTakeout = new com.floreantpos.swing.PosButton();

		btnTakeout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/pay_32.png")));
		btnTakeout.setText(POSConstants.CAPITAL_TAKE_OUT);
		activityPanel.add(btnTakeout);

		btnPickup = new PosButton();
		btnPickup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doHomeDelivery(Ticket.PICKUP);
			}
		});
		btnPickup.setText("PICKUP");
		activityPanel.add(btnPickup);

		btnHomeDelivery = new PosButton();
		btnHomeDelivery.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doHomeDelivery(Ticket.HOME_DELIVERY);
			}
		});
		btnHomeDelivery.setText("HOME DELIVERY");
		activityPanel.add(btnHomeDelivery);

		btnDriveThrough = new PosButton();
		btnDriveThrough.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doTakeout(Ticket.DRIVE_THROUGH);
			}
		});
		btnDriveThrough.setText("DRIVE THRU");
		activityPanel.add(btnDriveThrough);

		btnEditTicket.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/edit_ticket_32.png")));
		btnEditTicket.setText(POSConstants.CAPITAL_EDIT);
		activityPanel.add(btnEditTicket);
		btnSettleTicket = new com.floreantpos.swing.PosButton();

		btnSettleTicket.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/settle_ticket_32.png")));
		btnSettleTicket.setText(POSConstants.CAPITAL_SETTLE);
		activityPanel.add(btnSettleTicket);
		btnGroupSettle = new com.floreantpos.swing.PosButton();

		btnGroupSettle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/settle_ticket_32.png")));
		btnGroupSettle.setText("<html><body>" + POSConstants.CAPITAL_GROUP + "<br>" + POSConstants.CAPITAL_SETTLE + "</body></html>");
		activityPanel.add(btnGroupSettle);
		btnSplitTicket = new com.floreantpos.swing.PosButton();

		btnSplitTicket.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/split_32.png")));
		btnSplitTicket.setText(POSConstants.CAPITAL_SPLIT);
		activityPanel.add(btnSplitTicket);
		btnReopenTicket = new com.floreantpos.swing.PosButton();

		btnReopenTicket.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/edit_ticket_32.png")));
		btnReopenTicket.setText(POSConstants.CAPITAL_RE_OPEN);
		activityPanel.add(btnReopenTicket);

		btnVoidTicket.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/void_ticket_32.png")));
		btnVoidTicket.setText(POSConstants.CAPITAL_VOID);
		activityPanel.add(btnVoidTicket);

		btnPayout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/pay_32.png")));
		btnPayout.setText(POSConstants.CAPITAL_PAY_OUT);
		activityPanel.add(btnPayout);

		btnOrderInfo.setText(POSConstants.ORDER_INFO);
		activityPanel.add(btnOrderInfo);

		bottomLeftPanel.add(activityPanel, java.awt.BorderLayout.SOUTH);
		
		btnAssignDriver = new PosButton();
		btnAssignDriver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doAssignDriver();
			}
		});
		btnAssignDriver.setText("<html>ASSIGN<br/>DRIVER</html>");
		activityPanel.add(btnAssignDriver);
		
		btnFinishOrder = new PosButton();
		btnFinishOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doFinishOrder();
			}
		});
		btnFinishOrder.setText("FINISH ORDER");
		activityPanel.add(btnFinishOrder);

		bottomPanel.add(bottomLeftPanel, java.awt.BorderLayout.CENTER);

		bottomRightPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "OTHERS", javax.swing.border.TitledBorder.CENTER,
				javax.swing.border.TitledBorder.DEFAULT_POSITION));
		bottomRightPanel.setPreferredSize(new java.awt.Dimension(180, 10));

		btnShutdown.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/shut_down_32.png")));
		btnShutdown.setText(POSConstants.CAPITAL_SHUTDOWN);

		btnLogout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/log_out_32.png")));
		btnLogout.setText(POSConstants.CAPITAL_LOGOUT);

		btnBackOffice.setText(POSConstants.CAPITAL_BACK_OFFICE);

		btnManager.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/user_32.png")));
		btnManager.setText(POSConstants.CAPITAL_MANAGER);

		btnClockOut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/log_out_32.png")));
		btnClockOut.setText(POSConstants.CAPITAL_CLOCK_OUT);

		bottomPanel.add(bottomRightPanel, java.awt.BorderLayout.EAST);
		bottomRightPanel.setLayout(new MigLayout("aligny bottom, insets 1 2 1 2, gapy 10", "[170px]", "[][][][][]"));
		bottomRightPanel.add(btnShutdown, "cell 0 4,grow");
		bottomRightPanel.add(btnLogout, "cell 0 3,grow");
		bottomRightPanel.add(btnClockOut, "cell 0 2,grow");
		bottomRightPanel.add(btnBackOffice, "cell 0 1,grow");
		bottomRightPanel.add(btnManager, "cell 0 0,grow");

		add(bottomPanel, java.awt.BorderLayout.CENTER);
	}// </editor-fold>//GEN-END:initComponents

	protected void doFinishOrder() {
		List<Ticket> selectedTickets = openTicketList.getSelectedTickets();
		if (selectedTickets.size() == 0 || selectedTickets.size() > 1) {
			POSMessageDialog.showMessage("Please select a ticket.");
			return;
		}
		
		Ticket ticket = selectedTickets.get(0);

		if(orderServiceExtension.finishOrder(ticket)) {
			updateTicketList();
		}
	}

	protected void doAssignDriver() {
		try {
			List<Ticket> selectedTickets = openTicketList.getSelectedTickets();
			if (selectedTickets.size() == 0 || selectedTickets.size() > 1) {
				POSMessageDialog.showMessage("Please select a ticket to assign");
				return;
			}

			Ticket ticket = selectedTickets.get(0);

			if (!Ticket.HOME_DELIVERY.equals(ticket.getTicketType())) {
				POSMessageDialog.showError("Driver can be assigned only for Home Delivery");
				return;
			}
			
			User assignedDriver = ticket.getAssignedDriver();
			if(assignedDriver != null) {
				int option = JOptionPane.showOptionDialog(Application.getPosWindow(), "Driver already assigned. Do you want to reassign?", "Confirm", 
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
				
				if(option != JOptionPane.YES_OPTION) {
					return;
				}
			}

			TicketDAO.getInstance().refresh(ticket);
			orderServiceExtension.assignDriver(ticket);
		} catch (Exception e) {
			e.printStackTrace();
			POSMessageDialog.showError(e.getMessage());
			LogFactory.getLog(SwitchboardView.class).error(e);
		}
	}

	private void doReopenTicket() {
		try {

			int ticketId = NumberSelectionDialog2.takeIntInput(POSConstants.ENTER_TICKET_ID);

			if (ticketId == -1) {
				return;
			}

			Ticket ticket = TicketService.getTicket(ticketId);

			if (ticket == null) {
				throw new PosException(POSConstants.NO_TICKET_WITH_ID + " " + ticketId + " " + POSConstants.FOUND);
			}

			if (!ticket.isClosed()) {
				throw new PosException(POSConstants.TICKET_IS_NOT_CLOSED);
			}

			String ticketTotalAmount = Application.getCurrencySymbol() + NumberUtil.formatNumber(ticket.getTotalAmount());
			String amountMessage = "<span style='color: red; font-weight: bold;'>" + ticketTotalAmount + "</span>";
			String message = "<html><body>Ticket amount is " + ticketTotalAmount
					+ ". To reopen ticket, you need to refund that amount to system.<br/>Please press <b>OK</b> after you refund amount " + amountMessage
					+ "</body></html>";

			int option = JOptionPane.showOptionDialog(this, message, "Alert!", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
			if (option != JOptionPane.OK_OPTION) {
				return;
			}

			TicketService.refundTicket(ticket);
			editTicket(ticket);

		} catch (PosException e) {
			POSMessageDialog.showError(this, e.getLocalizedMessage());
		} catch (Exception e) {
			POSMessageDialog.showError(this, POSConstants.ERROR_MESSAGE, e);
		}
	}

	private void doClockOut() {
		int option = JOptionPane.showOptionDialog(this, POSConstants.CONFIRM_CLOCK_OUT, POSConstants.CONFIRM, JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE, null, null, null);
		if (option != JOptionPane.YES_OPTION) {
			return;
		}

		User user = Application.getCurrentUser();
		AttendenceHistoryDAO attendenceHistoryDAO = new AttendenceHistoryDAO();
		AttendenceHistory attendenceHistory = attendenceHistoryDAO.findHistoryByClockedInTime(user);
		if (attendenceHistory == null) {
			attendenceHistory = new AttendenceHistory();
			Date lastClockInTime = user.getLastClockInTime();
			Calendar c = Calendar.getInstance();
			c.setTime(lastClockInTime);
			attendenceHistory.setClockInTime(lastClockInTime);
			attendenceHistory.setClockInHour(Short.valueOf((short) c.get(Calendar.HOUR)));
			attendenceHistory.setUser(user);
			attendenceHistory.setTerminal(Application.getInstance().getTerminal());
			attendenceHistory.setShift(user.getCurrentShift());
		}

		Shift shift = user.getCurrentShift();
		Calendar calendar = Calendar.getInstance();

		user.doClockOut(attendenceHistory, shift, calendar);

		Application.getInstance().logout();
	}

	private synchronized void doShowBackoffice() {
		BackOfficeWindow window = BackOfficeWindow.getInstance();
		if (window == null) {
			window = new BackOfficeWindow();
			Application.getInstance().setBackOfficeWindow(window);
		}
		window.setVisible(true);
		window.toFront();
	}

	private void doShutdown() {
		Application.getInstance().shutdownPOS();
	}

	private void doLogout() {
		Application.getInstance().logout();
	}

	private void doSettleTicket() {
		try {
			List<Ticket> selectedTickets = openTicketList.getSelectedTickets();
			if (selectedTickets.size() == 0 || selectedTickets.size() > 1) {
				POSMessageDialog.showMessage(POSConstants.SELECT_ONE_TICKET_TO_SETTLE);
				return;
			}

			Ticket ticket = selectedTickets.get(0);

			PaymentTypeSelectionDialog dialog = new PaymentTypeSelectionDialog();
			dialog.setSize(250, 400);
			dialog.open();

			if (!dialog.isCanceled()) {
				ticket = TicketDAO.getInstance().initializeTicket(ticket);

				SettleTicketView view = SettleTicketView.getInstance();
				view.setPaymentView(dialog.getSelectedPaymentView());
				view.setCurrentTicket(ticket);
				RootView.getInstance().showView(SettleTicketView.VIEW_NAME);
			}
		} catch (Exception e) {
			POSMessageDialog.showError(POSConstants.ERROR_MESSAGE, e);
		}
	}

	private void doPrintTicket() {
		try {
			List<Ticket> tickets = openTicketList.getSelectedTickets();
			if (tickets.size() == 0) {
				POSMessageDialog.showMessage(POSConstants.SELECT_ONE_TICKET_TO_PRINT);
				return;
			}
			
			for(int i = 0; i < tickets.size(); i++) {
				Ticket ticket = tickets.get(i);
				tickets.set(i, TicketDAO.getInstance().initializeTicket(ticket));
			}
			
			OrderInfoView view = new OrderInfoView(tickets);
			OrderInfoDialog dialog = new OrderInfoDialog(view);
			dialog.setSize(400, 600);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setLocationRelativeTo(Application.getPosWindow());
			dialog.setVisible(true);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

//		Ticket ticket = selectedTickets.get(0);
//		try {
//			ticket = TicketDAO.getInstance().initializeTicket(ticket);
//			ticket.calculateDefaultGratutity();
//
//			PosPrintService.printTicket(ticket, 0);
//
//			// PRINT ACTION
//			String actionMessage = "CHK#" + ":" + ticket.getId();
//			ActionHistoryDAO.getInstance().saveHistory(Application.getCurrentUser(), ActionHistory.PRINT_CHECK, actionMessage);
//		} catch (Exception e) {
//			POSMessageDialog.showError(this, e.getMessage(), e);
//		}
	}

	private void doVoidTicket() {
		try {
			List<Ticket> selectedTickets = openTicketList.getSelectedTickets();
			if (selectedTickets.size() == 0 || selectedTickets.size() > 1) {
				POSMessageDialog.showMessage(POSConstants.SELECT_ONE_TICKET_TO_VOID);
				return;
			}

			Ticket ticket = selectedTickets.get(0);

			if (!ticket.getTotalAmount().equals(ticket.getDueAmount())) {
				POSMessageDialog.showMessage(POSConstants.PARTIAL_PAID_VOID_ERROR);
				return;
			}

			// initialize the ticket.
			ticket = TicketDAO.getInstance().initializeTicket(ticket);

			VoidTicketDialog voidTicketDialog = new VoidTicketDialog(Application.getPosWindow(), true);
			voidTicketDialog.setTicket(ticket);
			voidTicketDialog.open();

			if (!voidTicketDialog.isCanceled()) {
				updateView();
			}
		} catch (Exception e) {
			POSMessageDialog.showError(POSConstants.ERROR_MESSAGE, e);
		}
	}

	private void doSplitTicket() {
		try {
			List<Ticket> selectedTickets = openTicketList.getSelectedTickets();
			if (selectedTickets.size() == 0 || selectedTickets.size() > 1) {
				POSMessageDialog.showMessage(POSConstants.SELECT_ONE_TICKET_TO_SPLIT);
				return;
			}

			Ticket ticket = selectedTickets.get(0);
			if (!ticket.getTotalAmount().equals(ticket.getDueAmount())) {
				POSMessageDialog.showMessage(POSConstants.PARTIAL_PAID_VOID_ERROR);
				return;
			}

			// initialize the ticket.
			ticket = TicketDAO.getInstance().initializeTicket(ticket);

			SplitTicketDialog dialog = new SplitTicketDialog();
			dialog.setTicket(ticket);
			dialog.open();

			updateView();
		} catch (Exception e) {
			POSMessageDialog.showError(POSConstants.ERROR_MESSAGE, e);
		}
	}

	private void doEditTicket() {
		List<Ticket> selectedTickets = openTicketList.getSelectedTickets();
		if (selectedTickets.size() == 0 || selectedTickets.size() > 1) {
			POSMessageDialog.showMessage(POSConstants.SELECT_ONE_TICKET_TO_EDIT);
			return;
		}

		Ticket ticket = selectedTickets.get(0);

		editTicket(ticket);
	}

	private void editTicket(Ticket ticket) {
		// initialize the ticket.
		ticket = TicketDAO.getInstance().initializeTicket(ticket);

		OrderView.getInstance().setCurrentTicket(ticket);
		RootView.getInstance().showView(OrderView.VIEW_NAME);
	}

	private void doCreateNewTicket(final String ticketType) {
		try {
			OrderServiceExtension orderService = new DefaultOrderServiceExtension();
			orderService.createNewTicket(ticketType);

		} catch (TicketAlreadyExistsException e) {

			int option = JOptionPane.showOptionDialog(Application.getPosWindow(), POSConstants.EDIT_TICKET_CONFIRMATION, POSConstants.CONFIRM,
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
			if (option == JOptionPane.YES_OPTION) {
				editTicket(e.getTicket());
				return;
			}
		}
	}
	
	protected void doHomeDelivery(String ticketType) {
		try {

			orderServiceExtension.createNewTicket(ticketType);

		} catch (TicketAlreadyExistsException e) {

			int option = JOptionPane.showOptionDialog(Application.getPosWindow(), POSConstants.EDIT_TICKET_CONFIRMATION, POSConstants.CONFIRM,
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
			if (option == JOptionPane.YES_OPTION) {
				editTicket(e.getTicket());
				return;
			}
		}
	}

	private void doTakeout(String titcketType) {
		Application application = Application.getInstance();

		Ticket ticket = new Ticket();
		ticket.setPriceIncludesTax(application.isPriceIncludesTax());
		ticket.setTableNumber(-1);
		ticket.setTicketType(titcketType);
		ticket.setTerminal(application.getTerminal());
		ticket.setOwner(Application.getCurrentUser());
		ticket.setShift(application.getCurrentShift());

		Calendar currentTime = Calendar.getInstance();
		ticket.setCreateDate(currentTime.getTime());
		ticket.setCreationHour(currentTime.get(Calendar.HOUR_OF_DAY));

		OrderView.getInstance().setCurrentTicket(ticket);
		RootView.getInstance().showView(OrderView.VIEW_NAME);
	}

	private void doPayout() {
		PayoutDialog dialog = new PayoutDialog(Application.getPosWindow(), true);
		dialog.open();
	}

	private void doShowManagerWindow() {
		ManagerDialog dialog = new ManagerDialog();
		dialog.open();
	}

	private void doGroupSettle() {
		List<Ticket> selectedTickets = openTicketList.getSelectedTickets();
		if (selectedTickets.size() < 2) {
			POSMessageDialog.showError(POSConstants.YOU_MUST_SELECT_TWO_OR_MORE_TICKET_FOR_GROUP_SETTLE);
			return;
		}

		PaymentTypeSelectionDialog dialog = new PaymentTypeSelectionDialog();
		dialog.setSize(250, 400);
		dialog.open();

		if (!dialog.isCanceled()) {

			for (int i = 0; i < selectedTickets.size(); i++) {
				Ticket ticket = selectedTickets.get(i);
				ticket = TicketDAO.getInstance().initializeTicket(ticket);
				selectedTickets.set(i, ticket);
			}

			SettleTicketView view = SettleTicketView.getInstance();
			view.setPaymentView(dialog.getSelectedPaymentView());
			view.setTicketsToSettle(selectedTickets);
			RootView.getInstance().showView(SettleTicketView.VIEW_NAME);
		}
	}

	public void updateView() {
		User user = Application.getCurrentUser();
		UserType userType = user.getNewUserType();
		if (userType != null) {
			Set<UserPermission> permissions = userType.getPermissions();
			if (permissions != null) {
				btnNewTicket.setEnabled(false);
				btnBackOffice.setEnabled(false);
				btnEditTicket.setEnabled(false);
				btnGroupSettle.setEnabled(false);
				btnManager.setEnabled(false);
				btnPayout.setEnabled(false);
				btnReopenTicket.setEnabled(false);
				btnSettleTicket.setEnabled(false);
				btnSplitTicket.setEnabled(false);
				btnTakeout.setEnabled(false);
				btnVoidTicket.setEnabled(false);

				for (UserPermission permission : permissions) {
					if (permission.equals(UserPermission.VOID_TICKET)) {
						btnVoidTicket.setEnabled(true);
					}
					else if (permission.equals(UserPermission.PAY_OUT)) {
						btnPayout.setEnabled(true);
					}
					else if (permission.equals(UserPermission.SETTLE_TICKET)) {
						btnSettleTicket.setEnabled(true);
						btnGroupSettle.setEnabled(true);
					}
					else if (permission.equals(UserPermission.REOPEN_TICKET)) {
						btnReopenTicket.setEnabled(true);
					}
					else if (permission.equals(UserPermission.PERFORM_MANAGER_TASK)) {
						btnManager.setEnabled(true);
					}
					else if (permission.equals(UserPermission.SPLIT_TICKET)) {
						btnSplitTicket.setEnabled(true);
					}
					else if (permission.equals(UserPermission.TAKE_OUT)) {
						btnTakeout.setEnabled(true);
					}
					else if (permission.equals(UserPermission.VIEW_BACK_OFFICE)) {
						btnBackOffice.setEnabled(true);
					}
					else if (permission.equals(UserPermission.PAY_OUT)) {
						btnPayout.setEnabled(true);
					}
					else if (permission.equals(UserPermission.EDIT_TICKET)) {
						btnEditTicket.setEnabled(true);
					}
					else if (permission.equals(UserPermission.CREATE_NEW_TICKET)) {
						btnNewTicket.setEnabled(true);
					}
				}
			}
		}

		updateTicketList();
	}

	private void updateTicketList() {
		User user = Application.getCurrentUser();

		TicketDAO dao = TicketDAO.getInstance();
		List<Ticket> openTickets = null;

		boolean showAllOpenTicket = false;
		if (user.getNewUserType() != null) {
			Set<UserPermission> permissions = user.getNewUserType().getPermissions();
			if (permissions != null) {
				for (UserPermission permission : permissions) {
					if (permission.equals(UserPermission.VIEW_ALL_OPEN_TICKET)) {
						showAllOpenTicket = true;
						break;
					}
				}
			}
		}

		if (showAllOpenTicket) {
			openTickets = dao.findOpenTickets();
		}
		else {
			openTickets = dao.findOpenTicketsForUser(user);
		}
		openTicketList.setTickets(openTickets);

		lblUserName.setText(POSConstants.WELCOME + " " + user.toString() + ". " + POSConstants.YOU + " " + POSConstants.HAVE + " " + openTickets.size() + " "
				+ POSConstants.OPEN.toLowerCase() + " " + POSConstants.TICKETS);
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private com.floreantpos.swing.PosButton btnBackOffice;
	private com.floreantpos.swing.PosButton btnClockOut;
	private com.floreantpos.swing.PosButton btnEditTicket;
	private com.floreantpos.swing.PosButton btnGroupSettle;
	private com.floreantpos.swing.PosButton btnLogout;
	private com.floreantpos.swing.PosButton btnManager;
	private com.floreantpos.swing.PosButton btnNewTicket;
	private com.floreantpos.swing.PosButton btnPayout;
	private com.floreantpos.swing.PosButton btnOrderInfo;
	private com.floreantpos.swing.PosButton btnReopenTicket;
	private com.floreantpos.swing.PosButton btnSettleTicket;
	private com.floreantpos.swing.PosButton btnShutdown;
	private com.floreantpos.swing.PosButton btnSplitTicket;
	private com.floreantpos.swing.PosButton btnTakeout;
	private com.floreantpos.swing.PosButton btnVoidTicket;
	private javax.swing.JLabel lblUserName;
	private com.floreantpos.ui.TicketListView openTicketList;
	private PosButton btnPickup;
	private PosButton btnHomeDelivery;
	private PosButton btnDriveThrough;
	private PosButton btnAssignDriver;
	private PosButton btnFinishOrder;

	// End of variables declaration//GEN-END:variables

	@Override
	public void setVisible(boolean aFlag) {
		super.setVisible(aFlag);
		
		if(aFlag)
		updateTicketList();
		
//		if (aFlag) {
//			updateView();
//			ticketListUpdater.start();
//		}
//		else {
//			ticketListUpdater.stop();
//		}
	}

	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == btnBackOffice) {
			doShowBackoffice();
		}
		if (source == btnClockOut) {
			doClockOut();
		}
		if (source == btnEditTicket) {
			doEditTicket();
		}
		if (source == btnGroupSettle) {
			doGroupSettle();
		}
		if (source == btnLogout) {
			doLogout();
		}
		if (source == btnManager) {
			doShowManagerWindow();
		}
		if (source == btnNewTicket) {
			doCreateNewTicket(Ticket.DINE_IN);
		}
		if (source == btnPayout) {
			doPayout();
		}
		if (source == btnOrderInfo) {
			Worker.post(new Job() {

				@Override
				public Object run() {
					doPrintTicket();
					return "SUCCESS";
				}
			});

		}
		if (source == btnReopenTicket) {
			doReopenTicket();
		}
		if (source == btnSettleTicket) {
			doSettleTicket();
		}
		if (source == btnShutdown) {
			doShutdown();
		}
		if (source == btnSplitTicket) {
			doSplitTicket();
		}
		if (source == btnTakeout) {
			doTakeout(Ticket.TAKE_OUT);
		}
		if (source == btnVoidTicket) {
			doVoidTicket();
		}
	}

//	private class TicketListUpdaterTask implements ActionListener {
//
//		public void actionPerformed(ActionEvent e) {
//			updateTicketList();
//		}
//
//	}
}
