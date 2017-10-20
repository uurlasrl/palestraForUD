sap.ui.define([
	"org/uurla/reportsReports/controller/Base.controller"
], function(Base) {
	"use strict";

	return Base.extend("org.uurla.reportsReports.controller.Regolarizzare", {
		onInit: function() {
		   sap.ui.core.UIComponent.getRouterFor(this).getRoute("regolarizzare").attachPatternMatched(this.onDetailRouteHit, this);
		},
		onDetailRouteHit: function(oEvent) {
			
		},		
		onGoToBackView: function() {
			this.onGoToDashboard();
		}
	});
});