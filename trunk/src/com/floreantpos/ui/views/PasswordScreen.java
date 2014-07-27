/*
 * PasswordScreen.java
 *
 * Created on August 14, 2006, 11:01 PM
 */

package com.floreantpos.ui.views;

import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.apache.commons.logging.LogFactory;

import net.miginfocom.swing.MigLayout;

import com.floreantpos.POSConstants;
import com.floreantpos.config.ui.DatabaseConfigurationDialog;
import com.floreantpos.config.ui.TerminalSetupDialog;
import com.floreantpos.main.Application;
import com.floreantpos.model.AttendenceHistory;
import com.floreantpos.model.Shift;
import com.floreantpos.model.User;
import com.floreantpos.model.dao.AttendenceHistoryDAO;
import com.floreantpos.model.dao.UserDAO;
import com.floreantpos.swing.MessageDialog;
import com.floreantpos.swing.POSPasswordField;
import com.floreantpos.swing.PosButton;
import com.floreantpos.ui.dialog.POSMessageDialog;
import com.floreantpos.util.ShiftException;
import com.floreantpos.util.ShiftUtil;
import com.floreantpos.util.UserNotFoundException;

/**
 * 
 * @author MShahriar
 */
public class PasswordScreen extends JPanel {

	/** Creates new form PasswordScreen */
	public PasswordScreen() {
		setMinimumSize(new Dimension(320, 10));
		initComponents();

		btnConfigureDatabase.setAction(goAction);
		btnConfigureDatabase.setActionCommand("DBCONFIG");

		applyComponentOrientation(ComponentOrientation.getOrientation(Locale.getDefault()));
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	// <editor-fold defaultstate="collapsed"
	// desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {
		buttonPanel = new javax.swing.JPanel();
		btn7 = new com.floreantpos.swing.PosButton();
		btn8 = new com.floreantpos.swing.PosButton();
		btn9 = new com.floreantpos.swing.PosButton();
		btn4 = new com.floreantpos.swing.PosButton();
		btn5 = new com.floreantpos.swing.PosButton();
		btn6 = new com.floreantpos.swing.PosButton();
		btn1 = new com.floreantpos.swing.PosButton();
		btn2 = new com.floreantpos.swing.PosButton();
		btn3 = new com.floreantpos.swing.PosButton();
		posButton3 = new com.floreantpos.swing.PosButton();
		posButton1 = new com.floreantpos.swing.PosButton();
		jPanel2 = new javax.swing.JPanel();
		jLabel4 = new javax.swing.JLabel();
		jPanel3 = new javax.swing.JPanel();
		btnConfigureDatabase = new com.floreantpos.swing.PosButton();
		btnShutdown = new com.floreantpos.swing.PosButton();

		setPreferredSize(new Dimension(360, 593));
		setLayout(new MigLayout("ins 0", "[400px,grow]", "[116px][270px][grow][fill]"));

		buttonPanel.setOpaque(false);
		buttonPanel.setPreferredSize(new java.awt.Dimension(280, 180));
		buttonPanel.setLayout(new MigLayout("", "[111px][111px][111px,grow]", "[60px][60px][60px][60px]"));

		btn7.setAction(loginAction);
		btn7.setIcon(com.floreantpos.IconFactory.getIcon("7_32.png"));
		btn7.setActionCommand("7");
		btn7.setFocusable(false);
		buttonPanel.add(btn7, "cell 0 0,grow");

		btn8.setAction(loginAction);
		btn8.setIcon(com.floreantpos.IconFactory.getIcon("8_32.png"));
		btn8.setActionCommand("8");
		btn8.setFocusable(false);
		buttonPanel.add(btn8, "cell 1 0,grow");

		btn9.setAction(loginAction);
		btn9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/9_32.png"))); // NOI18N
		btn9.setActionCommand("9");
		btn9.setFocusable(false);
		buttonPanel.add(btn9, "cell 2 0,grow");

		btn4.setAction(loginAction);
		btn4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/4_32.png"))); // NOI18N
		btn4.setActionCommand("4");
		btn4.setFocusable(false);
		buttonPanel.add(btn4, "cell 0 1,grow");

		btn5.setAction(loginAction);
		btn5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/5_32.png"))); // NOI18N
		btn5.setActionCommand("5");
		btn5.setFocusable(false);
		buttonPanel.add(btn5, "cell 1 1,grow");

		btn6.setAction(loginAction);
		btn6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/6_32.png"))); // NOI18N
		btn6.setActionCommand("6");
		btn6.setFocusable(false);
		buttonPanel.add(btn6, "cell 2 1,grow");

		btn1.setAction(loginAction);
		btn1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/1_32.png"))); // NOI18N
		btn1.setActionCommand("1");
		btn1.setFocusable(false);
		buttonPanel.add(btn1, "cell 0 2,grow");

		btn2.setAction(loginAction);
		btn2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/2_32.png"))); // NOI18N
		btn2.setActionCommand("2");
		btn2.setFocusable(false);
		buttonPanel.add(btn2, "cell 1 2,grow");

		btn3.setAction(loginAction);
		btn3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/3_32.png"))); // NOI18N
		btn3.setActionCommand("3");
		btn3.setFocusable(false);
		buttonPanel.add(btn3, "cell 2 2,grow");

		posButton3.setAction(goAction);
		posButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/0_32.png"))); // NOI18N
		posButton3.setActionCommand("0");
		posButton3.setFocusable(false);
		buttonPanel.add(posButton3, "cell 0 3,grow");

		posButton1.setAction(goAction);
		posButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/clear_32.png"))); // NOI18N
		posButton1.setText(com.floreantpos.POSConstants.CLEAR);
		posButton1.setFocusable(false);
		posButton1.setPreferredSize(new java.awt.Dimension(90, 50));
		buttonPanel.add(posButton1, "cell 1 3,grow");
		add(buttonPanel, "cell 0 1,grow");

		psbtnLogin = new PosButton();
		psbtnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doLogin();
			}
		});
		psbtnLogin.setText("LOGIN");
		buttonPanel.add(psbtnLogin, "cell 2 3,grow");

		jPanel2.setOpaque(false);

		jLabel4.setFont(new java.awt.Font("DejaVu Sans", 1, 24));
		jLabel4.setForeground(new java.awt.Color(204, 102, 0));
		jLabel4.setText(com.floreantpos.POSConstants.USER_TYPE + ":");
		add(jPanel2, "cell 0 0,growx,aligny top");
		jPanel2.setLayout(new MigLayout("", "[343px]", "[][22px][31px][30px]"));

		lblTerminalId = new JLabel("TERMINAL ID:");
		lblTerminalId.setHorizontalAlignment(SwingConstants.CENTER);
		jPanel2.add(lblTerminalId, "cell 0 0,growx");
		jLabel2 = new javax.swing.JLabel();
		jLabel2.setHorizontalAlignment(SwingConstants.CENTER);

		jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18));
		jLabel2.setForeground(new java.awt.Color(204, 102, 0));
		jLabel2.setBackground(new java.awt.Color(204, 102, 0));
		jLabel2.setText(com.floreantpos.POSConstants.ENTER_YOUR_PASSWORD);
		jPanel2.add(jLabel2, "cell 0 1,growx,aligny top");
		tfPassword = new POSPasswordField();
		tfPassword.setFocusCycleRoot(true);
		tfPassword.setFont(new java.awt.Font("Courier", 1, 18));
		tfPassword.setHorizontalAlignment(SwingConstants.CENTER);
		tfPassword.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					doLogin();
				}
				else {
					checkLogin(String.valueOf(e.getKeyChar()));
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}
		});
		jPanel2.add(tfPassword, "cell 0 2,growx,aligny top");

		msgLabel = new JLabel("");
		msgLabel.setHorizontalAlignment(SwingConstants.CENTER);
		jPanel2.add(msgLabel, "cell 0 3,grow");

		jPanel3.setPreferredSize(new java.awt.Dimension(100, 105));
		jPanel3.setLayout(new MigLayout("", "[grow,fill]", "[][]"));

		btnConfigureDatabase.setAction(goAction);
		btnConfigureDatabase.setText(com.floreantpos.POSConstants.CONFIGURE_DATABASE);
		btnConfigureDatabase.setFocusable(false);
		jPanel3.add(btnConfigureDatabase, "cell 0 0,grow");

		panel = new JPanel();
		add(panel, "cell 0 2,grow");

		btnShutdown.setAction(goAction);
		btnShutdown.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/shut_down_32.png"))); // NOI18N
		btnShutdown.setText(com.floreantpos.POSConstants.SHUTDOWN);
		btnShutdown.setFocusable(false);
		jPanel3.add(btnShutdown, "cell 0 1,grow");

		add(jPanel3, "cell 0 3,growx,aligny bottom");

		lblTerminalId.setText("");
	}// </editor-fold>//GEN-END:initComponents

	public synchronized void doLogin() {
		try {
			tfPassword.setEnabled(false);
			
			Application application = Application.getInstance();
			application.initializeSystem();

			String secretKey = capturePassword();

			UserDAO dao = new UserDAO();
			User user = dao.findUserBySecretKey(secretKey);

			if (user == null) {
				throw new UserNotFoundException();
			}

			Shift currentShift = ShiftUtil.getCurrentShift();
			if (currentShift == null) {
				throw new ShiftException(POSConstants.NO_SHIFT_CONFIGURED);
			}

			adjustUserShift(user, currentShift);

			application.setCurrentUser(user);
			application.setCurrentShift(currentShift);

			tfPassword.setText("");
			application.getRootView().showView(SwitchboardView.VIEW_NAME);

		} catch (UserNotFoundException e) {
			LogFactory.getLog(Application.class).error(e);
			POSMessageDialog.showError("User not found");
			msgLabel.setText("Login failed, please try again...");
		} catch (ShiftException e) {
			LogFactory.getLog(Application.class).error(e);
			MessageDialog.showError(e.getMessage());
		} catch (Exception e1) {
			LogFactory.getLog(Application.class).error(e1);
			String message = e1.getMessage();

			if (message != null && message.contains("Cannot open connection")) {
				MessageDialog.showError("Cannot open database connection, please check database configuration.");
				DatabaseConfigurationDialog.show(Application.getPosWindow());
			}
			else {
				MessageDialog.showError("We are sorry, and unexpected error has occuered");
			}
		} finally {
			tfPassword.setEnabled(true);
			tfPassword.requestFocus();
		}
	}

	private void adjustUserShift(User user, Shift currentShift) {
		Application application = Application.getInstance();
		Calendar currentTime = Calendar.getInstance();

		if (user.isClockedIn() != null && user.isClockedIn().booleanValue()) {
			Shift userShift = user.getCurrentShift();
			Date userLastClockInTime = user.getLastClockInTime();
			long elaspedTimeSinceLastLogin = Math.abs(currentTime.getTimeInMillis() - userLastClockInTime.getTime());

			if (userShift != null) {
				if (!userShift.equals(currentShift)) {
					reClockInUser(currentTime, user, currentShift);
				}
				else if (userShift.getShiftLength() != null && (elaspedTimeSinceLastLogin >= userShift.getShiftLength())) {
					reClockInUser(currentTime, user, currentShift);
				}
			}
			else {
				user.doClockIn(application.getTerminal(), currentShift, currentTime);
			}
		}
		else {
			user.doClockIn(application.getTerminal(), currentShift, currentTime);
		}
	}

	private String capturePassword() {
		char[] password = tfPassword.getPassword();
		String newPass = new String(password);
		return newPass;
	}

	public void setTerminalId(int terminalId) {
		lblTerminalId.setText("TERMINAL ID: " + terminalId);
	}

	private void reClockInUser(Calendar currentTime, User user, Shift currentShift) {
		POSMessageDialog.showMessage("You will be clocked out from previous Shift");

		Application application = Application.getInstance();
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
			attendenceHistory.setTerminal(application.getTerminal());
			attendenceHistory.setShift(user.getCurrentShift());
		}
		user.doClockOut(attendenceHistory, currentShift, currentTime);
		user.doClockIn(application.getTerminal(), currentShift, currentTime);
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private com.floreantpos.swing.PosButton btnConfigureDatabase;
	private com.floreantpos.swing.PosButton btnShutdown;
	private javax.swing.JPanel buttonPanel;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel4;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JPanel jPanel3;
	private com.floreantpos.swing.PosButton posButton1;
	private com.floreantpos.swing.PosButton btn5;
	private com.floreantpos.swing.PosButton btn6;
	private com.floreantpos.swing.PosButton btn9;
	private com.floreantpos.swing.PosButton btn8;
	private com.floreantpos.swing.PosButton posButton3;
	private com.floreantpos.swing.PosButton btn7;
	private com.floreantpos.swing.PosButton btn3;
	private com.floreantpos.swing.PosButton btn2;
	private com.floreantpos.swing.PosButton btn1;
	private com.floreantpos.swing.PosButton btn4;
	private POSPasswordField tfPassword;
	// End of variables declaration//GEN-END:variables

	Action goAction = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {

			String command = e.getActionCommand();
			if (com.floreantpos.POSConstants.CLEAR.equals(command)) {
				if (tfPassword.hasFocus()) {
					tfPassword.setText("");
				}
			}
			else if (com.floreantpos.POSConstants.LOGIN.equals(command)) {
				doLogin();
			}
			else if (com.floreantpos.POSConstants.SHUTDOWN.equals(command)) {
				Application.getInstance().shutdownPOS();
			}
			else if ("DBCONFIG".equalsIgnoreCase(command)) {
				DatabaseConfigurationDialog.show(Application.getPosWindow());
			}
			else if (POSConstants.TERMINAL_SETUP.equals(command)) {
				TerminalSetupDialog dialog = new TerminalSetupDialog();
				dialog.pack();
				dialog.setLocationRelativeTo(Application.getPosWindow());
				dialog.setVisible(true);
			}
			else {
				String newPass = capturePassword();
				newPass += command;
				tfPassword.setText(newPass);
			}
		}
	};

	Action loginAction = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			tfPassword.setText(capturePassword() + e.getActionCommand());
			checkLogin(e.getActionCommand());

		}
	};
	private JPanel panel;
	private JLabel msgLabel;
	private PosButton psbtnLogin;
	private JLabel lblTerminalId;

	public void setFocus() {
		tfPassword.setText("");
		tfPassword.requestFocus();
	}

	private void checkLogin(String key) {
		msgLabel.setText("");

		String secretKey = capturePassword();
		if (secretKey != null && secretKey.length() == 4) {
			Thread loginThread = new Thread(new Runnable() {
				@Override
				public void run() {
					doLogin();
					tfPassword.setText("");
				}
			});

			loginThread.start();
		}
	}
}
