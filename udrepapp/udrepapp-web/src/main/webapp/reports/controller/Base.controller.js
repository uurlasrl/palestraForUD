sap.ui.define([
	"sap/ui/core/mvc/Controller"
], function(Controller) {
	"use strict";

	return Controller.extend("org.uurla.reportsReports.controller.Base", {
		onInit: function() {
				
		},
		onGoToDashboard: function() {
			var oRouter = sap.ui.core.UIComponent.getRouterFor(this);
			oRouter.navTo("dashboard");
		}
	});
});