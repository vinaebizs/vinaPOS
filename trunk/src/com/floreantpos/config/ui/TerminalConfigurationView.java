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
package com.floreantpos.config.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.miginfocom.swing.MigLayout;

import org.apache.commons.lang.StringUtils;

import com.floreantpos.Messages;
import com.floreantpos.config.TerminalConfig;
import com.floreantpos.demo.KitchenDisplayView;
import com.floreantpos.main.Application;
import com.floreantpos.model.OrderType;
import com.floreantpos.model.Terminal;
import com.floreantpos.model.dao.TerminalDAO;
import com.floreantpos.swing.DoubleTextField;
import com.floreantpos.swing.IntegerTextField;
import com.floreantpos.ui.dialog.POSMessageDialog;
import com.floreantpos.ui.views.SwitchboardOtherFunctionsView;
import com.floreantpos.ui.views.SwitchboardView;

public class TerminalConfigurationView extends ConfigurationView {
	private IntegerTextField tfTerminalNumber;
	private IntegerTextField tfSecretKeyLength;

	private JCheckBox cbTranslatedName = new JCheckBox(Messages.getString("TerminalConfigurationView.2")); //$NON-NLS-1$
	private JCheckBox cbFullscreenMode = new JCheckBox(Messages.getString("TerminalConfigurationView.3")); //$NON-NLS-1$
	private JCheckBox cbUseSettlementPrompt = new JCheckBox(Messages.getString("TerminalConfigurationView.4")); //$NON-NLS-1$
	private JCheckBox cbShowDbConfiguration = new JCheckBox(Messages.getString("TerminalConfigurationView.5")); //$NON-NLS-1$
	private JCheckBox cbShowBarCodeOnReceipt = new JCheckBox(Messages.getString("TerminalConfigurationView.21")); //$NON-NLS-1$
	private JCheckBox cbGroupKitchenReceiptItems = new JCheckBox("Group by Categories in kitchen Receipt"); //$NON-NLS-1$

	private JComboBox<String> cbFonts = new JComboBox<String>();
	private JComboBox<String> cbDefaultView;

	private IntegerTextField tfButtonHeight;
	private DoubleTextField tfScaleFactor;

	private IntegerTextField tfFontSize;
	private JCheckBox cbAutoLogoff = new JCheckBox(Messages.getString("TerminalConfigurationView.7")); //$NON-NLS-1$
	private IntegerTextField tfLogoffTime = new IntegerTextField(4);

	private JTextField tfDrawerName = new JTextField(10);
	private JTextField tfDrawerCodes = new JTextField(15);
	DoubleTextField tfDrawerInitialBalance = new DoubleTextField(6);
	private JSlider jsResize;

	public TerminalConfigurationView() {
		super();

		initComponents();
	}

	private void initComponents() {
		setLayout(new BorderLayout()); 

		JPanel contentPanel = new JPanel(new MigLayout("gap 5px 10px", "[][][grow]", "")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

		JLabel lblTerminalNumber = new JLabel(Messages.getString("TerminalConfigurationView.TERMINAL_NUMBER")); //$NON-NLS-1$
		contentPanel.add(lblTerminalNumber, "alignx left,aligny center"); //$NON-NLS-1$

		tfTerminalNumber = new IntegerTextField();
		tfTerminalNumber.setColumns(10);
		contentPanel.add(tfTerminalNumber, "aligny top, wrap"); //$NON-NLS-1$

		contentPanel.add(new JLabel(Messages.getString("TerminalConfigurationView.9"))); //$NON-NLS-1$
		tfSecretKeyLength = new IntegerTextField(3);
		contentPanel.add(tfSecretKeyLength, "wrap"); //$NON-NLS-1$

		contentPanel.add(cbShowDbConfiguration, "spanx 3"); //$NON-NLS-1$

		cbAutoLogoff.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (cbAutoLogoff.isSelected()) {
					tfLogoffTime.setEnabled(true);
				}
				else {
					tfLogoffTime.setEnabled(false);
				}
			}
		});
		contentPanel.add(cbAutoLogoff, "newline"); //$NON-NLS-1$
		contentPanel.add(new JLabel(Messages.getString("TerminalConfigurationView.16"))); //$NON-NLS-1$
		contentPanel.add(tfLogoffTime, "wrap"); //$NON-NLS-1$

		contentPanel.add(cbTranslatedName, "span 2"); //$NON-NLS-1$
		contentPanel.add(cbFullscreenMode, "newline, span"); //$NON-NLS-1$
		contentPanel.add(cbUseSettlementPrompt, "newline, span"); //$NON-NLS-1$
		contentPanel.add(cbShowBarCodeOnReceipt, "newline,span"); //$NON-NLS-1$
		contentPanel.add(cbGroupKitchenReceiptItems, "newline,span"); //$NON-NLS-1$

		contentPanel.add(new JLabel(Messages.getString("TerminalConfigurationView.17")), "newline"); //$NON-NLS-1$//$NON-NLS-2$
		contentPanel.add(cbFonts, "span 2, wrap"); //$NON-NLS-1$

		Vector<String> defaultViewList = new Vector<String>();

		List<OrderType> orderTypes = Application.getInstance().getOrderTypes();
		if (orderTypes != null) {
			for (OrderType orderType : orderTypes) {
				defaultViewList.add(orderType.getName());
			}
		}
		defaultViewList.add(SwitchboardOtherFunctionsView.VIEW_NAME);
		defaultViewList.add(KitchenDisplayView.VIEW_NAME);
		defaultViewList.add(SwitchboardView.VIEW_NAME);

		cbDefaultView = new JComboBox<String>(defaultViewList);

		contentPanel.add(new JLabel("Default View"), "newline"); //$NON-NLS-1$//$NON-NLS-2$
		contentPanel.add(cbDefaultView, "span 2, wrap"); //$NON-NLS-1$

		JPanel touchConfigPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
		touchConfigPanel.setBorder(BorderFactory.createTitledBorder("-")); //$NON-NLS-1$
		touchConfigPanel.add(new JLabel(Messages.getString("TerminalConfigurationView.18"))); //$NON-NLS-1$
		tfButtonHeight = new IntegerTextField(5);
		//touchConfigPanel.add(tfButtonHeight);

		int FPS_MIN = 10;
		int FPS_MAX = 50;
		int FPS_INIT = 10;
		jsResize = new JSlider(JSlider.HORIZONTAL, FPS_MIN, FPS_MAX, FPS_INIT);
		jsResize.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				if (!source.getValueIsAdjusting()) {
					double fps = (int) source.getValue();
					fps = fps / 10;
					tfScaleFactor.setText(String.valueOf(fps));
				}
			}
		});
		touchConfigPanel.add(jsResize);

		//touchConfigPanel.add(new JLabel("Menu item button height"));
		tfScaleFactor = new DoubleTextField(5);
		touchConfigPanel.add(tfScaleFactor);

		//touchConfigPanel.add(new JLabel(Messages.getString("TerminalConfigurationView.20"))); //$NON-NLS-1$
		tfFontSize = new IntegerTextField(5);
		//touchConfigPanel.add(tfFontSize);

		//contentPanel.add(touchConfigPanel, "span 3, wrap"); //$NON-NLS-1$

		addCashDrawerConfig();

		JScrollPane scrollPane = new JScrollPane(contentPanel);
		scrollPane.setBorder(null);
		add(scrollPane);
	}

	private void addCashDrawerConfig() {
		Integer[] hours = new Integer[24];
		Integer[] minutes = new Integer[60];

		for (int i = 0; i < 24; i++) {
			hours[i] = Integer.valueOf(i);
		}
		for (int i = 0; i < 60; i++) {
			minutes[i] = Integer.valueOf(i);
		}
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.getContentPane().add(new TerminalConfigurationView());
		frame.setSize(500, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	public boolean canSave() {
		return true;
	}

	@Override
	public boolean save() {
		int terminalNumber = 0;
		double scaleFactor = tfScaleFactor.getDouble();
		int fontSize = (int) (scaleFactor * 12);
		int menuItemButtonWidth = (int) (scaleFactor * 80);
		int buttonHeight = (int) (scaleFactor * 80);

		//int menuItemButtonHeight = (int) (80*fontSize)/12;

		/*if (buttonHeight < 20) {
			POSMessageDialog.showError(com.floreantpos.util.POSUtil.getFocusedWindow(), Messages.getString("TerminalConfigurationView.38")); //$NON-NLS-1$
			return false;
		}

		if (menuItemButtonHeight < 20) {
			POSMessageDialog.showError(com.floreantpos.util.POSUtil.getFocusedWindow(), Messages.getString("TerminalConfigurationView.38")); //$NON-NLS-1$
			return false;
		}

		if (fontSize < 8) {
			POSMessageDialog.showError(com.floreantpos.util.POSUtil.getFocusedWindow(), Messages.getString("TerminalConfigurationView.39")); //$NON-NLS-1$
			return false;
		}*/

		if (scaleFactor > 5) {
			POSMessageDialog.showError(com.floreantpos.util.POSUtil.getFocusedWindow(), Messages.getString("TerminalConfigurationView.23")); //$NON-NLS-1$
			return false;
		}

		try {
			terminalNumber = Integer.parseInt(tfTerminalNumber.getText());
		} catch (Exception x) {
			POSMessageDialog.showError(Application.getPosWindow(), Messages.getString("TerminalConfigurationView.14")); //$NON-NLS-1$
			return false;
		}

		int defaultPassLen = tfSecretKeyLength.getInteger();
		if (defaultPassLen == 0)
			defaultPassLen = 4;

		TerminalConfig.setTerminalId(terminalNumber);
		TerminalConfig.setDefaultPassLen(defaultPassLen);
		TerminalConfig.setFullscreenMode(cbFullscreenMode.isSelected());
		TerminalConfig.setShowDbConfigureButton(cbShowDbConfiguration.isSelected());
		TerminalConfig.setUseTranslatedName(cbTranslatedName.isSelected());

		TerminalConfig.setTouchScreenButtonHeight(buttonHeight);
		TerminalConfig.setMenuItemButtonWidth(menuItemButtonWidth);
		TerminalConfig.setMenuItemButtonHeight(buttonHeight);
		TerminalConfig.setTouchScreenFontSize(fontSize);
		TerminalConfig.setScreenScaleFactor(scaleFactor);

		TerminalConfig.setAutoLogoffEnable(cbAutoLogoff.isSelected());
		TerminalConfig.setAutoLogoffTime(tfLogoffTime.getInteger() <= 0 ? 10 : tfLogoffTime.getInteger());
		TerminalConfig.setUseSettlementPrompt(cbUseSettlementPrompt.isSelected());
		TerminalConfig.setShowBarcodeOnReceipt(cbShowBarCodeOnReceipt.isSelected());
		TerminalConfig.setGroupKitchenReceiptItems(cbGroupKitchenReceiptItems.isSelected());

		POSMessageDialog.showMessage(com.floreantpos.util.POSUtil.getFocusedWindow(), Messages.getString("TerminalConfigurationView.40")); //$NON-NLS-1$

		String selectedFont = (String) cbFonts.getSelectedItem();
		if ("<select>".equals(selectedFont)) { //$NON-NLS-1$
			selectedFont = null;
		}

		String selectedView = (String) cbDefaultView.getSelectedItem();

		TerminalConfig.setDefaultView(selectedView);
		TerminalConfig.setUiDefaultFont(selectedFont);
		TerminalConfig.setDrawerPortName(tfDrawerName.getText());
		TerminalConfig.setDrawerControlCodes(tfDrawerCodes.getText());

		TerminalDAO terminalDAO = TerminalDAO.getInstance();
		Terminal terminal = terminalDAO.get(terminalNumber);
		if (terminal == null) {
			terminal = new Terminal();
			terminal.setId(terminalNumber);
			terminal.setCurrentBalance(tfDrawerInitialBalance.getDouble());
			terminal.setName(String.valueOf(terminalNumber));
		}

		terminal.setOpeningBalance(tfDrawerInitialBalance.getDouble());

		terminalDAO.saveOrUpdate(terminal);

		return true;
	}

	@Override
	public void initialize() throws Exception {

		tfTerminalNumber.setText(String.valueOf(TerminalConfig.getTerminalId()));
		tfSecretKeyLength.setText(String.valueOf(TerminalConfig.getDefaultPassLen()));
		cbFullscreenMode.setSelected(TerminalConfig.isFullscreenMode());
		cbShowDbConfiguration.setSelected(TerminalConfig.isShowDbConfigureButton());
		cbUseSettlementPrompt.setSelected(TerminalConfig.isUseSettlementPrompt());
		cbShowBarCodeOnReceipt.setSelected(TerminalConfig.isShowBarcodeOnReceipt());
		cbGroupKitchenReceiptItems.setSelected(TerminalConfig.isGroupKitchenReceiptItems());

		tfButtonHeight.setText("" + TerminalConfig.getTouchScreenButtonHeight()); //$NON-NLS-1$
		tfScaleFactor.setText("" + TerminalConfig.getScreenScaleFactor()); //$NON-NLS-1$
		tfFontSize.setText("" + TerminalConfig.getTouchScreenFontSize()); //$NON-NLS-1$
		jsResize.setValue((int) (TerminalConfig.getScreenScaleFactor() * 10));

		cbTranslatedName.setSelected(TerminalConfig.isUseTranslatedName());
		cbAutoLogoff.setSelected(TerminalConfig.isAutoLogoffEnable());
		tfLogoffTime.setText("" + TerminalConfig.getAutoLogoffTime()); //$NON-NLS-1$
		tfLogoffTime.setEnabled(cbAutoLogoff.isSelected());

		initializeFontConfig();

		cbDefaultView.setSelectedItem(TerminalConfig.getDefaultView());

		Terminal terminal = Application.getInstance().refreshAndGetTerminal();
		tfDrawerName.setText(TerminalConfig.getDrawerPortName());
		tfDrawerCodes.setText(TerminalConfig.getDrawerControlCodes());
		tfDrawerInitialBalance.setText("" + terminal.getOpeningBalance()); //$NON-NLS-1$

		setInitialized(true);
	}

	private void initializeFontConfig() {
		GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Font[] fonts = e.getAllFonts(); // Get the fonts
		DefaultComboBoxModel<String> model = (DefaultComboBoxModel<String>) cbFonts.getModel();
		model.addElement("<select>"); //$NON-NLS-1$

		for (Font f : fonts) {
			model.addElement(f.getFontName());
		}

		String uiDefaultFont = TerminalConfig.getUiDefaultFont();
		if (StringUtils.isNotEmpty(uiDefaultFont)) {
			cbFonts.setSelectedItem(uiDefaultFont);
		}
	}

	@Override
	public String getName() {
		return Messages.getString("TerminalConfigurationView.47"); //$NON-NLS-1$
	}
}
