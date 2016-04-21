package com.floreantpos.config.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import jssc.SerialPort;
import net.miginfocom.swing.MigLayout;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.LogFactory;

import com.floreantpos.Messages;
import com.floreantpos.config.TerminalConfig;
import com.floreantpos.main.Application;
import com.floreantpos.model.Terminal;
import com.floreantpos.model.dao.TerminalDAO;
import com.floreantpos.swing.DoubleTextField;
import com.floreantpos.swing.FixedLengthTextField;
import com.floreantpos.ui.dialog.POSMessageDialog;
import com.floreantpos.util.DrawerUtil;

public class PeripheralConfigurationView extends ConfigurationView {
	public static final String CONFIG_TAB_PERIPHERAL = "Peripherals";
	private JCheckBox chkHasCashDrawer;
	private JTextField tfDrawerName = new JTextField(10);
	private JTextField tfDrawerCodes = new JTextField(15);
	private DoubleTextField tfDrawerInitialBalance = new DoubleTextField(6);
	private JCheckBox cbCustomerDisplay;

	private JTextField tfCustomerDisplayPort;
	private JTextField tfCustomerDisplayMessage;
	private JCheckBox cbScaleActive;
	private JTextField tfScalePort;
	private FixedLengthTextField tfScaleDisplayMessage;

	public PeripheralConfigurationView() {
		initComponents();
	}

	private void initComponents() {
		setLayout(new BorderLayout());

		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new MigLayout("", "[grow]", "[][]"));
		JPanel drawerConfigPanel = new JPanel(new MigLayout());
		drawerConfigPanel.setBorder(BorderFactory.createTitledBorder("CASH DRAWER"));

		chkHasCashDrawer = new JCheckBox(Messages.getString("TerminalConfigurationView.15")); //$NON-NLS-1$
		drawerConfigPanel.add(chkHasCashDrawer, "span 5, wrap"); //$NON-NLS-1$

		drawerConfigPanel.add(new JLabel(Messages.getString("TerminalConfigurationView.25"))); //$NON-NLS-1$
		drawerConfigPanel.add(tfDrawerName, ""); //$NON-NLS-1$

		drawerConfigPanel.add(new JLabel(Messages.getString("TerminalConfigurationView.27")), "newline"); //$NON-NLS-1$ //$NON-NLS-2$
		drawerConfigPanel.add(tfDrawerCodes, Messages.getString("TerminalConfigurationView.29")); //$NON-NLS-1$

		JButton btnDrawerTest = new JButton(Messages.getString("TerminalConfigurationView.11")); //$NON-NLS-1$
		btnDrawerTest.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String text = tfDrawerCodes.getText();
				if (StringUtils.isEmpty(text)) {
					text = TerminalConfig.getDefaultDrawerControlCodes();
				}

				String[] split = text.split(","); //$NON-NLS-1$
				char[] codes = new char[split.length];
				for (int i = 0; i < split.length; i++) {
					try {
						codes[i] = (char) Integer.parseInt(split[i]);
					} catch (Exception x) {
						codes[i] = '0';
					}
				}

				DrawerUtil.kickDrawer(tfDrawerName.getText(), codes);
			}
		});
		drawerConfigPanel.add(btnDrawerTest);

		JButton btnRestoreDrawerDefault = new JButton(Messages.getString("TerminalConfigurationView.32")); //$NON-NLS-1$
		btnRestoreDrawerDefault.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tfDrawerName.setText("COM1"); //$NON-NLS-1$
				tfDrawerCodes.setText(TerminalConfig.getDefaultDrawerControlCodes());
			}
		});
		drawerConfigPanel.add(btnRestoreDrawerDefault);

		drawerConfigPanel.add(new JLabel(Messages.getString("TerminalConfigurationView.34")), "newline"); //$NON-NLS-1$ //$NON-NLS-2$
		drawerConfigPanel.add(tfDrawerInitialBalance, "span 4, wrap"); //$NON-NLS-1$

		contentPanel.add(drawerConfigPanel, "span 3, grow, wrap"); //$NON-NLS-1$

		chkHasCashDrawer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				doEnableDisableDrawerPull();
			}
		});

		JPanel customerDisplayPanel = new JPanel(new MigLayout());
		customerDisplayPanel.setBorder(BorderFactory.createTitledBorder("CUSTOMER DISPLAY"));

		cbCustomerDisplay = new JCheckBox("Active customer display");
		tfCustomerDisplayPort = new JTextField(20);
		tfCustomerDisplayMessage = new FixedLengthTextField(20);

		JButton btnTest = new JButton("Test");
		btnTest.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				TerminalConfig.setScaleDisplayPort(tfCustomerDisplayPort.getText());
			}
		});

		JButton btnRestoreCustomerDefault = new JButton(Messages.getString("TerminalConfigurationView.32")); //$NON-NLS-1$
		btnRestoreCustomerDefault.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tfCustomerDisplayPort.setText("COM 1");
				tfCustomerDisplayMessage.setText("1234567891234567891");
			}
		});

		customerDisplayPanel.add(cbCustomerDisplay, "wrap");
		customerDisplayPanel.add(new JLabel("Customer Display Port"));
		customerDisplayPanel.add(tfCustomerDisplayPort, "wrap");
		customerDisplayPanel.add(new JLabel("Text Message"));
		customerDisplayPanel.add(tfCustomerDisplayMessage);
		customerDisplayPanel.add(btnTest);
		customerDisplayPanel.add(btnRestoreCustomerDefault);

		contentPanel.add(customerDisplayPanel, "grow,wrap");

		JPanel scaleDisplayPanel = new JPanel(new MigLayout());
		scaleDisplayPanel.setBorder(BorderFactory.createTitledBorder("SCALE DISPLAY"));

		cbScaleActive = new JCheckBox("Active scale Input");
		tfScalePort = new JTextField(20);
		tfScaleDisplayMessage = new FixedLengthTextField(20);

		JButton btnTestScale = new JButton("Test");
		btnTestScale.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				TerminalConfig.setCustomerDisplayPort(tfScalePort.getText());
				testScaleMachine();
			}
		});

		JButton btnRestoreScaleDefault = new JButton(Messages.getString("TerminalConfigurationView.32")); //$NON-NLS-1$
		btnRestoreScaleDefault.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tfScalePort.setText("COM7");
			}
		});

		scaleDisplayPanel.add(cbScaleActive, "wrap");
		scaleDisplayPanel.add(new JLabel("Scale Port"));
		scaleDisplayPanel.add(tfScalePort);
		scaleDisplayPanel.add(btnTestScale);
		scaleDisplayPanel.add(btnRestoreScaleDefault);

		if (TerminalConfig.getScaleActivationValue().equals("cas10")) {
			contentPanel.add(scaleDisplayPanel, "grow");
		}

		JScrollPane scrollPane = new JScrollPane(contentPanel);
		scrollPane.setBorder(null);
		add(scrollPane);
	}

	protected void doEnableDisableDrawerPull() {
		boolean selected = chkHasCashDrawer.isSelected();
		tfDrawerName.setEnabled(selected);
		tfDrawerCodes.setEnabled(selected);
		tfDrawerInitialBalance.setEnabled(selected);
	}

	@Override
	public boolean save() throws Exception {

		TerminalConfig.setDrawerPortName(tfDrawerName.getText());
		TerminalConfig.setDrawerControlCodes(tfDrawerCodes.getText());

		TerminalConfig.setCustomerDisplay(cbCustomerDisplay.isSelected());
		TerminalConfig.setCustomerDisplayPort(tfCustomerDisplayPort.getText());
		TerminalConfig.setCustomerDisplayMessage(tfCustomerDisplayMessage.getText());

		TerminalConfig.setScaleDisplay(cbScaleActive.isSelected());
		TerminalConfig.setScaleDisplayPort(tfScalePort.getText());
		TerminalConfig.setScaleDisplayMessage(tfScaleDisplayMessage.getText());

		TerminalDAO terminalDAO = TerminalDAO.getInstance();
		Terminal terminal = terminalDAO.get(TerminalConfig.getTerminalId());
		if (terminal == null) {
			terminal = new Terminal();
			terminal.setId(TerminalConfig.getTerminalId());
			terminal.setCurrentBalance(tfDrawerInitialBalance.getDouble());
			terminal.setName(String.valueOf(TerminalConfig.getTerminalId()));
		}

		terminal.setHasCashDrawer(chkHasCashDrawer.isSelected());
		terminal.setOpeningBalance(tfDrawerInitialBalance.getDouble());

		terminalDAO.saveOrUpdate(terminal);
		return true;
	}

	@Override
	public void initialize() throws Exception {

		Terminal terminal = Application.getInstance().refreshAndGetTerminal();
		chkHasCashDrawer.setSelected(terminal.isHasCashDrawer());
		tfDrawerName.setText(TerminalConfig.getDrawerPortName());
		tfDrawerCodes.setText(TerminalConfig.getDrawerControlCodes());
		tfDrawerInitialBalance.setText("" + terminal.getOpeningBalance()); //$NON-NLS-1$

		cbCustomerDisplay.setSelected(TerminalConfig.isActiveCustomerDisplay());
		tfCustomerDisplayPort.setText(TerminalConfig.getCustomerDisplayPort());
		tfCustomerDisplayMessage.setText(TerminalConfig.getCustomerDisplayMessage());

		cbScaleActive.setSelected(TerminalConfig.isActiveScaleDisplay());
		tfScalePort.setText(TerminalConfig.getScaleDisplayPort());
		tfScaleDisplayMessage.setText(TerminalConfig.getScaleDisplayMessage());

		doEnableDisableDrawerPull();

		setInitialized(true);
	}

	private void testScaleMachine() {
		try {
			final SerialPort serialPort = new SerialPort(tfScalePort.getText());
			serialPort.openPort();//Open serial port
			serialPort.setParams(SerialPort.BAUDRATE_9600, SerialPort.DATABITS_7, SerialPort.STOPBITS_2, SerialPort.PARITY_EVEN);
			serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN | SerialPort.FLOWCONTROL_RTSCTS_OUT | SerialPort.FLOWCONTROL_XONXOFF_IN
					| SerialPort.FLOWCONTROL_XONXOFF_OUT);

			//setCharacterSet(USA);
			String value = serialPort.readString();
			POSMessageDialog.showMessage(value);
			serialPort.closePort();
		} catch (Exception ex) {
			POSMessageDialog.showError(this, ex.getMessage()); 
			LogFactory.getLog(PeripheralConfigurationView.class).error(ex);
		}
	}

	@Override
	public String getName() {
		return CONFIG_TAB_PERIPHERAL;
	}
}
