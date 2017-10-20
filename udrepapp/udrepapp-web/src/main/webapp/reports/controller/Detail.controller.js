sap.ui.define([
	"org/uurla/reportsReports/controller/Base.controller"
], function(Base) {
	"use strict";

	return Base.extend("org.uurla.reportsReports.controller.Detail", {
		onInit: function() {
		   sap.ui.core.UIComponent.getRouterFor(this).getRoute("detail").attachPatternMatched(this.onDetailRouteHit, this);
		},
		onDetailRouteHit: function(oEvent) {
			console.log("test");
		},
		onGoToBackView: function() {
			var oRouter = sap.ui.core.UIComponent.getRouterFor(this);
			oRouter.navTo("gestiremif");
		}
	});
});