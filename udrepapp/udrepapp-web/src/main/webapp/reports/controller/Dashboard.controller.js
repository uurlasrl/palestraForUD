sap.ui.define([
	"org/uurla/reportsReports/controller/Base.controller"
], function(Base) {
	"use strict";

	return Base.extend("org.uurla.reportsReports.controller.Dashboard", {
		onInit: function() {
		   if (sap.ui.Device.support.touch === false) {
                this.getView().addStyleClass("sapUiSizeCompact");
            }
		},
		onNavigateToPage: function(oEvent) {
			var pageToNavigate = oEvent.getSource().getProperty("title");
			var oRouter = sap.ui.core.UIComponent.getRouterFor(this);

			switch (pageToNavigate) {
				case "GESTIRE MIF":
					oRouter.navTo("gestiremif");
					break;
				case "PROPORRE":
					oRouter.navTo("proporre");
					break;
				case "REGOLARIZZARE":
					oRouter.navTo("regolarizzare");
					break;
				default:
					sap.m.MessageToast.show("Errore, la pagina non è stata trovata.");
			}
		}
	});
});