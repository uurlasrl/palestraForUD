sap.ui.define([
	"org/uurla/reportsReports/controller/Base.controller"
], function(Base) {
	"use strict";

	return Base.extend("org.uurla.reportsReports.controller.NewPropostaCap", {
		onInit: function() {
		   sap.ui.core.UIComponent.getRouterFor(this).getRoute("newpropostacap").attachPatternMatched(this.onDetailRouteHit, this);
		},
		onDetailRouteHit: function(oEvent) {
			
		},
		onGoToBackView: function() {
			var oRouter = sap.ui.core.UIComponent.getRouterFor(this);
			oRouter.navTo("proporre");
		}
	});
});