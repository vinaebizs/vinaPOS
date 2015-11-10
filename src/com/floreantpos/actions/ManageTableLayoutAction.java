package com.floreantpos.actions;

import com.floreantpos.POSConstants;
import com.floreantpos.extension.ExtensionManager;
import com.floreantpos.extension.FloorLayoutPlugin;
import com.floreantpos.model.UserPermission;

public class ManageTableLayoutAction extends PosAction {
	FloorLayoutPlugin floorLayoutPlugin;
	
	public ManageTableLayoutAction() {
		super(POSConstants.TABLE_MANAGE_BUTTON_TEXT, UserPermission.MANAGE_TABLE_LAYOUT); //$NON-NLS-1$
		
		floorLayoutPlugin = (FloorLayoutPlugin) ExtensionManager.getPlugin(FloorLayoutPlugin.class);
		if(floorLayoutPlugin == null) {
			setVisible(false);
		}
	}

	@Override
	public void execute() {
		if(floorLayoutPlugin != null) {
			floorLayoutPlugin.openTicketsAndTablesDisplay();
		}
	}
}
