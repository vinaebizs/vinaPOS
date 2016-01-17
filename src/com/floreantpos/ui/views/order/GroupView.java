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
 * GroupView.java
 *
 * Created on August 5, 2006, 9:29 PM
 */

package com.floreantpos.ui.views.order;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;

import com.floreantpos.model.MenuCategory;
import com.floreantpos.model.MenuGroup;
import com.floreantpos.model.dao.MenuGroupDAO;
import com.floreantpos.swing.MessageDialog;
import com.floreantpos.swing.POSToggleButton;
import com.floreantpos.ui.views.order.actions.GroupSelectionListener;

/**
 *
 * @author  MShahriar
 */
public class GroupView extends SelectionView {
	private Vector<GroupSelectionListener> listenerList = new Vector<GroupSelectionListener>();

	private MenuCategory menuCategory;

	public static final String VIEW_NAME = "GROUP_VIEW"; //$NON-NLS-1$

	private ButtonGroup buttonGroup;

	/** Creates new form GroupView */
	public GroupView() {
		super(com.floreantpos.POSConstants.GROUPS, 100, 60);

		//removeAll();
		//buttonsPanel.setLayout(new GridLayout(1, 0, 5, 5));
		remove(actionButtonPanel);

		btnPrev.setText("<");
		btnNext.setText(">");
		//btnPrev.setPreferredSize(new Dimension(50,40));
		//btnNext.setPreferredSize(new Dimension(50,40));

		add(btnPrev, BorderLayout.WEST);
		add(btnNext, BorderLayout.EAST);
	}

	public MenuCategory getMenuCategory() {
		return menuCategory;
	}

	public void setMenuCategory(MenuCategory menuCategory) {
		this.menuCategory = menuCategory;

		if (menuCategory == null) {
			return;
		}

		try {
			MenuGroupDAO dao = new MenuGroupDAO();
			List<MenuGroup> groups = dao.findEnabledByParent(menuCategory);
			for (Iterator iterator = groups.iterator(); iterator.hasNext();) {
				MenuGroup menuGroup = (MenuGroup) iterator.next();
				if(!dao.hasChildren(null, menuGroup)) {
					iterator.remove();
				}
			}
			setItems(groups);

			if (groups.size() > 0) {
				MenuGroup menuGroup = groups.get(0);
				GroupButton groupButton = (GroupButton) getFirstItemButton();
				if (groupButton != null) {
					groupButton.setSelected(true);
					fireGroupSelected(menuGroup);
				}
				return;
			}

		} catch (Exception e) {
			MessageDialog.showError(e);
		}
	}

	@Override
	protected void renderItems() {
		buttonGroup = new ButtonGroup();
		super.renderItems();
	}

	protected int getFitableButtonCount() {
		Dimension size = buttonPanelContainer.getSize();
		Dimension itemButtonSize = getButtonSize();

		int horizontalButtonCount = getButtonCount(size.width, itemButtonSize.width);

		return horizontalButtonCount;
	}
	
	@Override
	protected LayoutManager createButtonPanelLayout() {
		return new GridLayout(1, 0, 5, 0);
	}

	public void addGroupSelectionListener(GroupSelectionListener listener) {
		listenerList.add(listener);
	}

	public void removeGroupSelectionListener(GroupSelectionListener listener) {
		listenerList.remove(listener);
	}

	private void fireGroupSelected(MenuGroup foodGroup) {
		for (GroupSelectionListener listener : listenerList) {
			listener.groupSelected(foodGroup);
		}
	}

	@Override
	protected AbstractButton createItemButton(Object item) {
		MenuGroup menuGroup = (MenuGroup) item;
		GroupButton button = new GroupButton(menuGroup);
		buttonGroup.add(button);

		return button;
	}

	private class GroupButton extends POSToggleButton implements ActionListener {
		MenuGroup menuGroup;

		GroupButton(MenuGroup foodGroup) {
			this.menuGroup = foodGroup;

			setText("<html><body><center>" + foodGroup.getDisplayName() + "</center></body></html>"); //$NON-NLS-1$ //$NON-NLS-2$

			if (menuGroup.getButtonColorCode() != null) {
				setBackground(menuGroup.getButtonColor());
			}
			if (menuGroup.getTextColorCode() != null) {
				setForeground(menuGroup.getTextColor());
			}

			addActionListener(this);
		}

		public void actionPerformed(ActionEvent e) {
			fireGroupSelected(menuGroup);
		}
	}
}
