package com.floreantpos.ui.forms;

import javax.swing.JCheckBox;
import javax.swing.JLabel;

import org.apache.commons.lang.StringUtils;
import org.hibernate.StaleObjectStateException;

import net.miginfocom.swing.MigLayout;

import com.floreantpos.bo.ui.BOMessageDialog;
import com.floreantpos.model.Customer;
import com.floreantpos.model.dao.CustomerDAO;
import com.floreantpos.model.util.IllegalModelStateException;
import com.floreantpos.swing.FixedLengthTextField;
import com.floreantpos.swing.FocusedTextField;
import com.floreantpos.ui.BeanEditor;
import com.floreantpos.ui.dialog.POSMessageDialog;
import com.floreantpos.util.PosGuiUtil;

public class CustomerForm extends BeanEditor {
	private FixedLengthTextField tfSSN;
	private FixedLengthTextField tfLoyaltyNo;
	private FixedLengthTextField tfAddress;
	private FixedLengthTextField tfCity;
	private FixedLengthTextField tfState;
	private FixedLengthTextField tfZip;
	private FixedLengthTextField tfCountry;
	private FixedLengthTextField tfCreditCardNo;
	private FocusedTextField tfCreditLimit;
	private FocusedTextField tfCreditSpent;
	private JCheckBox cbVip;
	private FixedLengthTextField tfName;
	private FixedLengthTextField tfPhone;
	private FixedLengthTextField tfEmail;
	
	public CustomerForm() {
		setLayout(new MigLayout("", "[45px][114px,grow]", "[19px][][][][][][][][][][]"));
		
		JLabel lblName = new JLabel("Name:");
		add(lblName, "cell 0 0,alignx trailing,aligny center");
		
		tfName = new FixedLengthTextField(60);
		add(tfName, "cell 1 0,growx,aligny top");
		
		JLabel lblPhone = new JLabel("Phone:");
		add(lblPhone, "cell 0 1,alignx trailing");
		
		tfPhone = new FixedLengthTextField(30);
		add(tfPhone, "cell 1 1,growx");
		
		JLabel lblEmail = new JLabel("EMail:");
		add(lblEmail, "cell 0 2,alignx trailing");
		
		tfEmail = new FixedLengthTextField(40);
		add(tfEmail, "flowx,cell 1 2,growx");
		
		JLabel lblLoyaltyNo = new JLabel("Loyalty No:");
		add(lblLoyaltyNo, "cell 0 3,alignx trailing");
		
		tfLoyaltyNo = new FixedLengthTextField(30);
		add(tfLoyaltyNo, "cell 1 3,growx");
		
		JLabel lblAddress = new JLabel("Address:");
		add(lblAddress, "cell 0 4,alignx trailing");
		
		tfAddress = new FixedLengthTextField(120);
		add(tfAddress, "cell 1 4,growx");
		
		JLabel lblCitytown = new JLabel("City/Town:");
		add(lblCitytown, "cell 0 5,alignx trailing");
		
		tfCity = new FixedLengthTextField(30);
		add(tfCity, "flowx,cell 1 5,growx");
		
		JLabel lblStatezipCode = new JLabel("State/ZIP Code:");
		add(lblStatezipCode, "cell 0 6,alignx trailing");
		
		tfZip = new FixedLengthTextField(10);
		add(tfZip, "flowx,cell 1 6,growx");
		
		JLabel lblCreditCardNo = new JLabel("Credit Card No:");
		add(lblCreditCardNo, "cell 0 7,alignx trailing");
		
		tfCreditCardNo = new FixedLengthTextField(30);
		add(tfCreditCardNo, "cell 1 7,growx");
		
		JLabel lblCreditLimit = new JLabel("Credit Limit:");
		add(lblCreditLimit, "cell 0 8,alignx trailing");
		
		tfCreditLimit = new FocusedTextField(true);
		add(tfCreditLimit, "cell 1 8,growx");
		
		JLabel lblCreditSpent = new JLabel("Credit Spent:");
		add(lblCreditSpent, "cell 0 9,alignx trailing");
		
		tfCreditSpent = new FocusedTextField(true);
		add(tfCreditSpent, "cell 1 9,growx");
		
		JLabel lblSsn = new JLabel("SSN:");
		add(lblSsn, "cell 1 2,alignx trailing");
		
		tfSSN = new FixedLengthTextField(30);
		add(tfSSN, "cell 1 2,growx,aligny top");
		
		JLabel lblStatecountry = new JLabel("State/Country:");
		add(lblStatecountry, "cell 1 5,alignx trailing");
		
		tfState = new FixedLengthTextField(30);
		add(tfState, "cell 1 5,growx");
		
		JLabel lblCountry = new JLabel("Country:");
		add(lblCountry, "cell 1 6,alignx trailing");
		
		tfCountry = new FixedLengthTextField(30);
		add(tfCountry, "cell 1 6,growx");
		
		cbVip = new JCheckBox("VIP");
		add(cbVip, "cell 1 10");
	}
	
	public void setFieldsEditable(boolean editable) {
		tfName.setEditable(editable);
		tfPhone.setEditable(editable);
		tfEmail.setEditable(editable);
		tfLoyaltyNo.setEditable(editable);
		tfAddress.setEditable(editable);
		tfCity.setEditable(editable);
		tfZip.setEditable(editable);
		tfCreditCardNo.setEditable(editable);
		tfCreditLimit.setEditable(editable);
		tfCreditSpent.setEditable(editable);
		tfSSN.setEditable(editable);
		tfState.setEditable(editable);
		tfCountry.setEditable(editable);
		cbVip.setEnabled(editable);
	}

	@Override
	public boolean save() {
		 try {
			if (!updateModel())
				return false;

			Customer customer = (Customer) getBean();
			CustomerDAO.getInstance().saveOrUpdate(customer);
			return true;
		} catch (IllegalModelStateException e) {
		} catch (StaleObjectStateException e) {
			BOMessageDialog.showError(this, "It seems this Customer is modified by some other person or terminal. Save failed.");
		}
		
		return false;
	}

	@Override
	public void dispose() {
	}

	@Override
	protected void updateView() {
		Customer customer = (Customer) getBean();
		
		if(customer != null) {
			tfName.setText(customer.getName());
			tfAddress.setText(customer.getAddress());
			tfCity.setText(customer.getCity());
			tfCountry.setText(customer.getCountry());
			tfCreditCardNo.setText(customer.getCreditCardNo());
			tfCreditLimit.setText(String.valueOf(customer.getCreditLimit()));
			tfCreditSpent.setText(String.valueOf(customer.getCreditSpent()));
			tfEmail.setText(customer.getEmail());
			tfLoyaltyNo.setText(customer.getLoyaltyNo());
			tfPhone.setText(customer.getTelephoneNo());
			tfSSN.setText(customer.getSsn());
			tfState.setText(customer.getState());
			tfZip.setText(customer.getZipCode());
			cbVip.setSelected(customer.isVip());
		}
		else {
			tfName.setText("");
			tfAddress.setText("");
			tfCity.setText("");
			tfCountry.setText("");
			tfCreditCardNo.setText("");
			tfCreditLimit.setText("");
			tfCreditSpent.setText("");
			tfEmail.setText("");
			tfLoyaltyNo.setText("");
			tfPhone.setText("");
			tfSSN.setText("");
			tfState.setText("");
			tfZip.setText("");
			cbVip.setSelected(false);
		}
	}

	@Override
	protected boolean updateModel() throws IllegalModelStateException {
		String phoneString = tfPhone.getText();
		String name = tfName.getText();
		String email = tfEmail.getText();
		
		if(StringUtils.isEmpty(phoneString) && StringUtils.isEmpty(name) && StringUtils.isEmpty(email)) {
			POSMessageDialog.showError("Please provide either name or phone or email");
			return false;
		}
		
		Customer customer = (Customer) getBean();
		
		if(customer == null) {
			customer = new Customer();
			setBean(customer, false);
		}
		
		customer.setName(tfName.getText());
		customer.setAddress(tfAddress.getText());
		customer.setCity(tfCity.getText());
		customer.setCountry(tfCountry.getText());
		customer.setCreditLimit(PosGuiUtil.parseDouble(tfCreditLimit));
		customer.setCreditSpent(PosGuiUtil.parseDouble(tfCreditSpent));
		customer.setEmail(tfEmail.getText());
		customer.setLoyaltyNo(tfLoyaltyNo.getText());
		customer.setTelephoneNo(tfPhone.getText());
		customer.setSsn(tfSSN.getText());
		customer.setState(tfState.getText());
		customer.setZipCode(tfZip.getText());
		customer.setVip(cbVip.isSelected());
		
		return true;
	}

	@Override
	public String getDisplayText() {
		if(editMode) {
			return "Edit customer";
		}
		return "Create customer";
	}
}
