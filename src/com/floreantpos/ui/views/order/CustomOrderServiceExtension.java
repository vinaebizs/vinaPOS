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
package com.floreantpos.ui.views.order;

import java.util.Calendar;
import java.util.List;

import javax.swing.JMenu;
import javax.swing.JOptionPane;

import com.floreantpos.Messages;
import com.floreantpos.config.TerminalConfig;
import com.floreantpos.extension.ExtensionManager;
import com.floreantpos.extension.FloorLayoutPlugin;
import com.floreantpos.extension.OrderServiceExtension;
import com.floreantpos.main.Application;
import com.floreantpos.model.OrderType;
import com.floreantpos.model.ShopTable;
import com.floreantpos.model.Ticket;
import com.floreantpos.model.dao.ShopTableDAO;
import com.floreantpos.model.dao.TicketDAO;
import com.floreantpos.ui.dialog.NumberSelectionDialog2;
import com.floreantpos.ui.dialog.POSMessageDialog;
import com.floreantpos.util.POSUtil;
import com.floreantpos.util.PosGuiUtil;
import com.floreantpos.util.TicketAlreadyExistsException;

public class CustomOrderServiceExtension implements OrderServiceExtension {
	
	List<ShopTable> selectedTables; 
	
	

	public CustomOrderServiceExtension(List<ShopTable> selectedTables) {
		super();
		this.selectedTables = selectedTables;
	}

	@Override
	public String getName() {
		return Messages.getString("DefaultOrderServiceExtension.0"); //$NON-NLS-1$
	}

	@Override
	public String getDescription() {
		return Messages.getString("DefaultOrderServiceExtension.1"); //$NON-NLS-1$
	}

	@Override
	public void init() {
	}

	@Override
	public void createNewTicket(OrderType ticketType) throws TicketAlreadyExistsException {

		FloorLayoutPlugin floorLayoutPlugin = (FloorLayoutPlugin) ExtensionManager.getPlugin(FloorLayoutPlugin.class);

		List<ShopTable> allTables = ShopTableDAO.getInstance().findAll();
		
		if((allTables == null || allTables.isEmpty()) && floorLayoutPlugin == null) {

			int userInput = 0;

			int result = POSMessageDialog.showYesNoQuestionDialog(Application.getPosWindow(),
					Messages.getString("DefaultOrderServiceExtension.6"), Messages.getString("DefaultOrderServiceExtension.7")); //$NON-NLS-1$ //$NON-NLS-2$

			if(result == JOptionPane.YES_OPTION) {

				userInput = NumberSelectionDialog2.takeIntInput(Messages.getString("DefaultOrderServiceExtension.8")); //$NON-NLS-1$

				if(userInput == 0) {
					POSMessageDialog.showError(Application.getPosWindow(), Messages.getString("DefaultOrderServiceExtension.9")); //$NON-NLS-1$
					return;
				}

				if(userInput != -1) {
					ShopTableDAO.getInstance().createNewTables(userInput);
				}
			}

			if(result != JOptionPane.YES_OPTION || userInput == -1) {
				int option = POSMessageDialog.showYesNoQuestionDialog(Application.getPosWindow(),
						Messages.getString("DefaultOrderServiceExtension.10"), Messages.getString("DefaultOrderServiceExtension.11")); //$NON-NLS-1$ //$NON-NLS-2$
				if(option != 0) {
					return;
				}
			}
		}

		List<ShopTable> selectedTables = null;

		if(TerminalConfig.isShouldShowTableSelection()) {

			if(floorLayoutPlugin != null) {

				selectedTables = floorLayoutPlugin.captureTableNumbers(null);
				
				if(selectedTables == null) {
					return;
				}
				
				if(selectedTables.isEmpty()) {

					int option = POSMessageDialog.showYesNoQuestionDialog(Application.getPosWindow(),
							Messages.getString("DefaultOrderServiceExtension.12"), Messages.getString("DefaultOrderServiceExtension.13")); //$NON-NLS-1$ //$NON-NLS-2$
					if(option != 0) {
						return;
					}
				}
			}

			List<ShopTable> shopTables = ShopTableDAO.getInstance().findAll();

			if(shopTables != null && !shopTables.isEmpty() && floorLayoutPlugin == null) {
				
				selectedTables = this.selectedTables;
				
				if(selectedTables == null) {
					return;
				}
			}
		}

		int numberOfGuests = 0;
		if(TerminalConfig.isShouldShowGuestSelection()) {
			numberOfGuests = PosGuiUtil.captureGuestNumber();
			if(numberOfGuests == -1) {
				return;
			}
		}

		Application application = Application.getInstance();

		Ticket ticket = new Ticket();
		ticket.setPriceIncludesTax(application.isPriceIncludesTax());
		ticket.setType(ticketType);
		ticket.setNumberOfGuests(numberOfGuests);
		ticket.setTerminal(application.getTerminal());
		ticket.setOwner(Application.getCurrentUser());
		ticket.setShift(application.getCurrentShift());

		if(selectedTables != null) {
			for (ShopTable shopTable : selectedTables) {
				shopTable.setServing(true);
				ticket.addTable(shopTable.getTableNumber());
			}
		}

		Calendar currentTime = Calendar.getInstance();
		ticket.setCreateDate(currentTime.getTime());
		ticket.setCreationHour(currentTime.get(Calendar.HOUR_OF_DAY));

		OrderView.getInstance().setCurrentTicket(ticket);
		RootView.getInstance().showView(OrderView.VIEW_NAME);
	}

	@Override
	public void setCustomerToTicket(int ticketId) {
	}

	public void setDeliveryDate(int ticketId) {
	}

	@Override
	public void assignDriver(int ticketId) {

	};

	@Override
	public boolean finishOrder(int ticketId) {
		Ticket ticket = TicketDAO.getInstance().get(ticketId);

		//		if (ticket.getType() == TicketType.DINE_IN) {
		//			POSMessageDialog.showError(Application.getPosWindow(), "Please select tickets of type HOME DELIVERY or PICKUP or DRIVE THRU");
		//			return false;
		//		}

		int due = (int) POSUtil.getDouble(ticket.getDueAmount());
		if(due != 0) {
			POSMessageDialog.showError(Application.getPosWindow(), Messages.getString("DefaultOrderServiceExtension.2")); //$NON-NLS-1$
			return false;
		}

		int option = JOptionPane
				.showOptionDialog(
						Application.getPosWindow(),
						Messages.getString("DefaultOrderServiceExtension.3") + ticket.getId() + Messages.getString("DefaultOrderServiceExtension.4"), Messages.getString("DefaultOrderServiceExtension.5"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
						JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);

		if(option != JOptionPane.OK_OPTION) {
			return false;
		}

		OrderController.closeOrder(ticket);

		return true;
	}

	@Override
	public void createCustomerMenu(JMenu menu) {
	}

	@Override
	public void initBackoffice() {

	}

	@Override
	public String getId() {
		return String.valueOf("DefaultOrderServiceExtension".hashCode()); //$NON-NLS-1$
	}
}