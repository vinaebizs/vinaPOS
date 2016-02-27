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
 * Discount.java
 *
 * Created on August 5, 2006, 9:29 PM
 */

package com.floreantpos.ui.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import net.miginfocom.swing.MigLayout;

import org.apache.commons.collections.CollectionUtils;

import com.floreantpos.Messages;
import com.floreantpos.POSConstants;
import com.floreantpos.config.TerminalConfig;
import com.floreantpos.main.Application;
import com.floreantpos.model.Discount;
import com.floreantpos.model.MenuItem;
import com.floreantpos.model.Ticket;
import com.floreantpos.model.TicketDiscount;
import com.floreantpos.model.TicketItem;
import com.floreantpos.model.TicketItemDiscount;
import com.floreantpos.model.dao.DiscountDAO;
import com.floreantpos.model.dao.MenuItemDAO;
import com.floreantpos.swing.POSToggleButton;
import com.floreantpos.swing.PosButton;
import com.floreantpos.swing.PosScrollPane;
import com.floreantpos.swing.ScrollableFlowPanel;
import com.floreantpos.ui.TitlePanel;

/**
 * 
 * @author MShahriar
 */
public class DiscountSelectionDialog extends POSDialog implements ActionListener {

	private ScrollableFlowPanel buttonsPanel;

	private HashMap<Integer, TicketItemDiscount> addedTicketItemDiscounts = new HashMap<Integer, TicketItemDiscount>();
	private HashMap<Integer, TicketDiscount> addedTicketDiscounts = new HashMap<Integer, TicketDiscount>();

	private HashMap<Integer, DiscountButton> buttonMap = new HashMap<Integer, DiscountButton>();

	private TicketItem ticketItem;
	private Ticket ticket;

	private JPanel itemSearchPanel;
	private JTextField txtSearchItem;

	private ButtonGroup btnGroup;
	private POSToggleButton btnItem;
	private POSToggleButton btnOrder;

	public DiscountSelectionDialog(TicketItem ticketItem, Ticket ticket) {
		this.ticketItem = ticketItem;
		this.ticket = ticket;

		initializeComponent();

		if (ticketItem != null && ticketItem.getDiscounts() != null) {
			for (TicketItemDiscount ticketItemDiscount : ticketItem.getDiscounts()) {
				addedTicketItemDiscounts.put(ticketItemDiscount.getDiscountId(), ticketItemDiscount);
			}
		}

		if (ticket.getDiscounts() != null) {
			for (TicketDiscount ticketDiscount : ticket.getDiscounts()) {
				addedTicketDiscounts.put(ticketDiscount.getDiscountId(), ticketDiscount);
			}
		}
	}

	private void initializeComponent() {
		setTitle(Messages.getString("DiscountSelectionDialog.0")); //$NON-NLS-1$
		setLayout(new BorderLayout());

		JPanel headerPanel = new JPanel(new BorderLayout());

		TitlePanel titlePanel = new TitlePanel();
		titlePanel.setTitle(Messages.getString("DiscountSelectionDialog.0")); //$NON-NLS-1$
		
		JPanel searchPanel=new JPanel(new BorderLayout()); 

		JPanel toggleBtnPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));

		btnItem = new POSToggleButton(POSConstants.ITEM);
		btnItem.setSelected(true);
		btnItem.addActionListener(this);

		btnOrder = new POSToggleButton(Messages.getString("DiscountSelectionDialog.2")); //$NON-NLS-1$
		btnOrder.addActionListener(this);

		btnGroup = new ButtonGroup();
		btnGroup.add(btnItem);
		btnGroup.add(btnOrder);

		itemSearchPanel = new JPanel();
		
		createCouponSearchPanel();

		toggleBtnPanel.add(btnItem);
		toggleBtnPanel.add(btnOrder);
		
		searchPanel.add(toggleBtnPanel, BorderLayout.WEST); 
		searchPanel.add(itemSearchPanel, BorderLayout.EAST);

		headerPanel.add(titlePanel, BorderLayout.NORTH);
		headerPanel.add(searchPanel, BorderLayout.SOUTH);

		add(headerPanel, BorderLayout.NORTH);

		JPanel buttonActionPanel = new JPanel(new MigLayout("fill")); //$NON-NLS-1$

		PosButton btnOk = new PosButton(Messages.getString("TicketSelectionDialog.3")); //$NON-NLS-1$
		btnOk.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				doFinishDiscountSelection();
			}
		});

		PosButton btnCancel = new PosButton(POSConstants.CANCEL.toUpperCase());
		btnCancel.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				addedTicketItemDiscounts.clear();
				addedTicketDiscounts.clear();
				setCanceled(true);
				dispose();
			}
		});

		buttonActionPanel.add(btnOk, "w 80!,split 2,align center"); //$NON-NLS-1$
		buttonActionPanel.add(btnCancel, "w 80!"); //$NON-NLS-1$

		JPanel footerPanel = new JPanel(new BorderLayout());
		footerPanel.setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));
		footerPanel.add(new JSeparator(), BorderLayout.NORTH);
		footerPanel.add(buttonActionPanel);

		add(footerPanel, BorderLayout.SOUTH);

		buttonsPanel = new ScrollableFlowPanel(FlowLayout.LEADING);

		JScrollPane scrollPane = new PosScrollPane(buttonsPanel, PosScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, PosScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(80, 0));
		scrollPane.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5), scrollPane.getBorder()));

		add(scrollPane, BorderLayout.CENTER);

		if (ticketItem == null) {
			btnOrder.setSelected(true);
			btnItem.setVisible(false);
			rendererTicketDiscounts();
		}
		else {
			btnItem.setSelected(true);
			rendererTicketItemDiscounts();
		}

		setSize(1024, 720);
		setResizable(true);
	}

	private void createCouponSearchPanel() {
		itemSearchPanel.setLayout(new BorderLayout(5, 5));
		itemSearchPanel.setPreferredSize(new Dimension(400, 30));
		PosButton btnSearch = new PosButton("...");
		btnSearch.setPreferredSize(new Dimension(60, 40));

		txtSearchItem = new JTextField("Enter Coupon Number");
		txtSearchItem.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				txtSearchItem.setText("Scan Coupon Number");
				txtSearchItem.setForeground(Color.gray);
			}

			@Override
			public void focusGained(FocusEvent e) {
				txtSearchItem.setForeground(Color.black);
				txtSearchItem.setText("");
			}
		});

		txtSearchItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (txtSearchItem.getText().equals("")) {
					POSMessageDialog.showMessage("Please enter coupon number or barcode ");
					return;
				}
				if (!addCouponByBarcode(txtSearchItem.getText())) {
					addCouponById(txtSearchItem.getText());
				}
				txtSearchItem.setText("");
			}
		});

		btnSearch.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ItemNumberSelectionDialog dialog = new ItemNumberSelectionDialog(Application.getPosWindow());
				dialog.setTitle("Search Coupon");
				dialog.setSize(600, 400);
				dialog.open();
				if (dialog.isCanceled()) {
					return;
				}

				txtSearchItem.requestFocus();

				if (!addCouponByBarcode(dialog.getValue())) {
					if (!addCouponById(dialog.getValue())) {
						POSMessageDialog.showError(Application.getPosWindow(), "Coupon not found");
					}
				}
			}
		});
		itemSearchPanel.add(new JLabel("Scan Coupon Number"), BorderLayout.WEST); 
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

	private boolean addCouponById(String id) {

		if (!isParsable(id)) {
			return false;
		}

		Integer itemId = Integer.parseInt(id);
		Discount discount = DiscountDAO.getInstance().get(itemId);

		if (discount == null) {
			return false;
		}

		if (discount.getQualificationType() == Discount.QUALIFICATION_TYPE_ITEM) {
			addedTicketItemDiscounts.put(discount.getId(), MenuItem.convertToTicketItemDiscount(discount, ticketItem));
		}
		else {
			if (discount.isModifiable()) {
				double newValue = getModifiedValue(discount);
				if (newValue <= 0) {
					newValue = discount.getValue();
				}
				discount.setValue(newValue);
			}
			addedTicketDiscounts.put(discount.getId(), Ticket.convertToTicketDiscount(discount, ticket));
		}

		DiscountButton button = buttonMap.get(discount.getId());
		if (button != null) {
			button.setSelected(true);
		}

		return true;
	}

	private boolean addCouponByBarcode(String barcode) {

		Discount discount = DiscountDAO.getInstance().getDiscountByBarcode(barcode);

		if (discount == null) {
			return false;
		}

		if (discount.getQualificationType() == Discount.QUALIFICATION_TYPE_ITEM) {
			addedTicketItemDiscounts.put(discount.getId(), MenuItem.convertToTicketItemDiscount(discount, ticketItem));
		}
		else {
			if (discount.isModifiable()) {
				double newValue = getModifiedValue(discount);
				if (newValue <= 0) {
					newValue = discount.getValue();
				}
				discount.setValue(newValue);
			}
			addedTicketDiscounts.put(discount.getId(), Ticket.convertToTicketDiscount(discount, ticket));
		}

		DiscountButton button = buttonMap.get(discount.getId());
		button.setSelected(true);

		return true;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (btnItem.isSelected()) {
			rendererTicketItemDiscounts();
		}
		else {
			rendererTicketDiscounts();
		}
	}

	private void rendererTicketItemDiscounts() {
		buttonMap.clear();
		buttonsPanel.getContentPane().removeAll();

		if (ticketItem == null) {
			return;
		}

		Integer itemId = Integer.parseInt(ticketItem.getItemCode());
		MenuItem menuItem = MenuItemDAO.getInstance().get(itemId);
		List<Discount> discounts = DiscountDAO.getInstance().getValidCoupon(menuItem);

		for (Discount discount : discounts) {
			DiscountButton btnDiscount = new DiscountButton(discount);
			btnDiscount.setSelected(false);
			buttonsPanel.add(btnDiscount);
			buttonMap.put(discount.getId(), btnDiscount);
		}

		if (ticketItem != null && ticketItem.getDiscounts() != null) {
			for (TicketItemDiscount ticketItemDiscount : ticketItem.getDiscounts()) {
				DiscountButton discountButton = buttonMap.get(ticketItemDiscount.getDiscountId());

				if (discountButton != null) {
					discountButton.setSelected(true);
				}
			}
		}

		buttonsPanel.repaint();
		buttonsPanel.revalidate();
	}

	private void rendererTicketDiscounts() {
		buttonMap.clear();
		buttonsPanel.getContentPane().removeAll();

		List<Discount> discounts = DiscountDAO.getInstance().getTicketValidCoupon();

		if (discounts.isEmpty() || discounts == null) {
			return;
		}
		for (Discount discount : discounts) {
			DiscountButton btnDiscount = new DiscountButton(discount);
			btnDiscount.setSelected(false);
			buttonsPanel.add(btnDiscount);
			buttonMap.put(discount.getId(), btnDiscount);
		}

		if (ticket.getDiscounts() != null) {
			for (TicketDiscount ticketCouponAndDiscount : ticket.getDiscounts()) {
				DiscountButton ticketDiscountButton = buttonMap.get(ticketCouponAndDiscount.getDiscountId());

				if (ticketDiscountButton != null) {
					ticketDiscountButton.setSelected(true);
				}
			}
		}
		buttonsPanel.repaint();
		buttonsPanel.revalidate();
	}

	protected void doFinishDiscountSelection() {
		if (ticketItem != null) {
			List<TicketItemDiscount> discounts = ticketItem.getDiscounts();
			if (discounts == null)
				discounts = new ArrayList<TicketItemDiscount>();
			if (!CollectionUtils.isEqualCollection(discounts, addedTicketItemDiscounts.values())) {
				discounts.clear();

				for (TicketItemDiscount ticketItemDiscount : addedTicketItemDiscounts.values()) {
					ticketItem.addTodiscounts(ticketItemDiscount);
				}
			}
		}
		List<TicketDiscount> couponAndDiscounts = ticket.getDiscounts();
		if (couponAndDiscounts == null)
			couponAndDiscounts = new ArrayList<TicketDiscount>();
		if (!CollectionUtils.isEqualCollection(couponAndDiscounts, addedTicketDiscounts.values())) {
			couponAndDiscounts.clear();

			for (TicketDiscount ticketDiscount : addedTicketDiscounts.values()) {
				ticket.addTodiscounts(ticketDiscount);
			}
		}

		setCanceled(false);
		dispose();
	}

	private double getModifiedValue(Discount discount) {
		Double newValue = NumberSelectionDialog2.takeDoubleInput("Enter Amount", "Enter Amount", discount.getValue()); //$NON-NLS-1$ //$NON-NLS-2$
		if (newValue > 0) {
			return newValue;
		}
		return 0;
	}

	private class DiscountButton extends POSToggleButton implements ActionListener {
		private static final int BUTTON_SIZE = 119;
		Discount discount;

		DiscountButton(Discount discount) {
			this.discount = discount;
			setFocusable(true);
			setFocusPainted(true);
			setVerticalTextPosition(SwingConstants.BOTTOM);
			setHorizontalTextPosition(SwingConstants.CENTER);
			setFont(getFont().deriveFont(18.0f));

			setText("<html><body><center>" + discount.getName() + "<br></center></body></html>"); //$NON-NLS-1$ //$NON-NLS-2$ 

			setPreferredSize(new Dimension(BUTTON_SIZE, TerminalConfig.getMenuItemButtonHeight()));
			addActionListener(this);
		}

		public void actionPerformed(ActionEvent e) {
			if (btnItem.isSelected()) {
				if (isSelected()) {
					addedTicketItemDiscounts.put(discount.getId(), MenuItem.convertToTicketItemDiscount(discount, ticketItem));
				}
				else {
					addedTicketItemDiscounts.remove(discount.getId());
				}
			}
			else {

				if (isSelected()) {
					if (discount.isModifiable()) {
						double newValue = getModifiedValue(discount);
						if (newValue <= 0) {
							newValue = discount.getValue();
						}
						discount.setValue(newValue);
					}
					addedTicketDiscounts.put(discount.getId(), Ticket.convertToTicketDiscount(discount, ticket));
				}
				else {
					addedTicketDiscounts.remove(discount.getId());
				}
			}

		}

	}
}
