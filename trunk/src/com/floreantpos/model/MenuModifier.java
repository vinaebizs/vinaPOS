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
package com.floreantpos.model;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.floreantpos.config.TerminalConfig;
import com.floreantpos.model.base.BaseMenuModifier;
import com.floreantpos.util.POSUtil;

public class MenuModifier extends BaseMenuModifier {
	private static final long serialVersionUID = 1L;

	/*[CONSTRUCTOR MARKER BEGIN]*/
	public MenuModifier() {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public MenuModifier(java.lang.Integer id) {
		super(id);
	}

	/*[CONSTRUCTOR MARKER END]*/

	private transient MenuItemModifierGroup menuItemModifierGroup;

	public MenuItemModifierGroup getMenuItemModifierGroup() {
		return menuItemModifierGroup;
	}

	public void setMenuItemModifierGroup(MenuItemModifierGroup menuItemModifierGroup) {
		this.menuItemModifierGroup = menuItemModifierGroup;
	}

	@Override
	public Integer getSortOrder() {
		return sortOrder == null ? Integer.MAX_VALUE : sortOrder;
	}

	@Override
	public Integer getButtonColor() {
		return buttonColor;
	}

	@Override
	public Integer getTextColor() {
		return textColor;
	}

	public String getDisplayName() {
		if (TerminalConfig.isUseTranslatedName() && StringUtils.isNotEmpty(getTranslatedName())) {
			return getTranslatedName();
		}

		return super.getName();
	}

	@Override
	public String toString() {
		return getName();
	}

	public String getUniqueId() {
		return ("menu_modifier_" + getName() + "_" + getId()).replaceAll("\\s+", "_"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
	}

	//

	public void addProperty(String name, String value) {
		if (getProperties() == null) {
			setProperties(new HashMap<String, String>());
		}
		getProperties().put(name, value);
	}

	public boolean hasProperty(String key) {
		return getProperty(key) != null;
	}

	public String getProperty(String key) {
		if (getProperties() == null) {
			return null;
		}

		return getProperties().get(key);
	}

	public String getProperty(String key, String defaultValue) {
		if (getProperties() == null) {
			return null;
		}

		String string = getProperties().get(key);
		if (StringUtils.isEmpty(string)) {
			return defaultValue;
		}

		return string;
	}

	public void removeProperty(String typeProperty, String taxProperty) {
		Map<String, String> properties = getProperties();
		if (properties == null) {
			return;
		}
		properties.remove(typeProperty);
		properties.remove(taxProperty);
	}

	public double getPriceByOrderType(OrderType type) {
		double defaultPrice = this.getPrice();
		if (type == null) {
			return defaultPrice;
		}

		String priceProp = getProperty(type.name() + "_PRICE"); //$NON-NLS-1$
		if (priceProp == null)
			return defaultPrice;

		try {
			return Double.parseDouble(priceProp);
		} catch (Exception e) {
			return defaultPrice;
		}
	}

	public double getTaxByOrderType(OrderType type) {
		if (this.getTax() == null) {
			return 0;
		}
		double defaultTax = this.getTax().getRate();
		if (type == null) {
			return defaultTax;
		}

		String taxProp = getProperty(type.name() + "_TAX"); //$NON-NLS-1$
		if (taxProp == null)
			return defaultTax;

		try {
			return Double.parseDouble(taxProp);
		} catch (Exception e) {
			return defaultTax;
		}
	}

	public boolean isPropertyValueTrue(String propertyName) {
		String property = getProperty(propertyName);

		return POSUtil.getBoolean(property);
	}

	public void setPriceByOrderType(String type, double price) {
		type = type.replaceAll(" ", "_"); //$NON-NLS-1$ //$NON-NLS-2$
		addProperty(type + "_PRICE", String.valueOf(price)); //$NON-NLS-1$
	}

	public void setTaxByOrderType(String type, double price) {
		type = type.replaceAll(" ", "_"); //$NON-NLS-1$ //$NON-NLS-2$
		addProperty(type + "_TAX", String.valueOf(price)); //$NON-NLS-1$
	}
}