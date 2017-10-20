sap.ui.define([
	"org/uurla/reportsReports/controller/Base.controller"
], function(Base) {
	"use strict";

	return Base.extend("org.uurla.reportsReports.controller.Proporre", {
		onInit: function() {
		   sap.ui.core.UIComponent.getRouterFor(this).getRoute("proporre").attachPatternMatched(this.onDetailRouteHit, this);
		},
		onDetailRouteHit: function(oEvent) {
			
		},
		onAddProposta: function(oEvent) {
			var popoverAddProposta = this.byId("popoverAddProposta");
			
			popoverAddProposta.openBy(oEvent.getSource());
		},
		handlePopoverAddPropostaBtn: function(oEvent) {
			var typeOfButtonPressed = oEvent.getSource().getProperty("text");
			var oRouter = sap.ui.core.UIComponent.getRouterFor(this);
			
			switch (typeOfButtonPressed) {
				case "da Accertamento":
					oRouter.navTo("newpropostaacc");
				break;
					case "da Capitolo":
					oRouter.navTo("newpropostacap");
				break;
				default:
					sap.m.MessageToast.show("Errore, la pagina non è stata trovata.");
			}		
			
		},
		onGoToBackView: function() {
			this.onGoToDashboard();
		}
	});
});