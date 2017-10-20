sap.ui.define([
	"org/uurla/reportsReports/controller/Base.controller"
], function(Base) {
	"use strict";

	return Base.extend("org.uurla.reportsReports.controller.Gestiremif", {
		onInit: function() {
				
		},
		countTableValues: function() {
			/* formatter
			var idGestireMifTable = this.byId("idGestireMifTable");
			
			var tableItemsLength = idGestireMifTable.getItems().length;
			return "Sospesi (" + tableItemsLength + ")"; */
		},
		onUploadFile: function(oEvent) {
			var popoverUploadFile = this.byId("popoverUploadFile");
			popoverUploadFile.openBy(oEvent.getSource());
		},
		onNavigateToDetail: function(oEvent) {
			var oRouter = sap.ui.core.UIComponent.getRouterFor(this);			
			oRouter.navTo("detail");
		}
	});
});