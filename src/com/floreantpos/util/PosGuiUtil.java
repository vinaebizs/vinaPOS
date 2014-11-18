package com.floreantpos.util;

import java.awt.Dialog;
import java.awt.Window;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.text.JTextComponent;

import com.floreantpos.POSConstants;
import com.floreantpos.main.Application;
import com.floreantpos.model.ShopTable;
import com.floreantpos.ui.dialog.NumberSelectionDialog2;
import com.floreantpos.ui.dialog.POSMessageDialog;
import com.floreantpos.ui.dialog.TableSelectionDialog;

public class PosGuiUtil {
	public static List<ShopTable> captureTable() {
		TableSelectionDialog dialog = new TableSelectionDialog();
		dialog.pack();
		dialog.open();

		if (dialog.isCanceled()) {
			int option = JOptionPane.showOptionDialog(Application.getPosWindow(), "Proceed without table?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
			if(option != JOptionPane.YES_OPTION) {
				return null;
			}
		}

		return dialog.getTables();
	}
	
	public static int captureTableNumber() {
		NumberSelectionDialog2 dialog = new NumberSelectionDialog2();
		dialog.setTitle(POSConstants.ENTER_A_TABLE_NUMBER);
		dialog.pack();
		dialog.open();

		if (dialog.isCanceled()) {
			return -1;
		}

		int tableNumber = (int) dialog.getValue();
		if (tableNumber == 0) {
			POSMessageDialog.showError(Application.getPosWindow(), POSConstants.TABLE_NUMBER_CANNOT_BE_0);
			return -1;
		}

		return tableNumber;
	}

	public static int captureGuestNumber() {
		NumberSelectionDialog2 dialog = new NumberSelectionDialog2();
		dialog.setTitle(POSConstants.ENTER_NUMBER_OF_GUEST);
		dialog.pack();
		dialog.open();

		if (dialog.isCanceled()) {
			return -1;
		}

		int numberOfGuests = (int) dialog.getValue();
		if (numberOfGuests == 0) {
			POSMessageDialog.showError(Application.getPosWindow(), POSConstants.GUEST_NUMBER_CANNOT_BE_0);
			return -1;
		}

		return numberOfGuests;
	}

	public static Double parseDouble(JTextComponent textComponent) {
		String text = textComponent.getText();
		try {
			return Double.parseDouble(text);
		} catch (Exception e) {
			return 0.0;
		}
	}

	public static boolean isModalDialogShowing() {
		Window[] windows = Window.getWindows();
		if (windows != null) { // don't rely on current implementation, which at least returns [0].
			for (Window w : windows) {
				if (w.isShowing() && w instanceof Dialog && ((Dialog) w).isModal())
					return true;
			}
		}
		return false;
	}
}
