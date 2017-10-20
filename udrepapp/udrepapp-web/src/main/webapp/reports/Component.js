sap.ui.define([
	"sap/ui/core/UIComponent",

	"org/uurla/reportsReports/model/models"
], function(UIComponent, Device, models) {
	"use strict";

	return UIComponent.extend("org.uurla.reportsReports.Component", {

		metadata: {
			manifest: "json"
		},
		
		init: function() {
			// call the init function of the parent
			UIComponent.prototype.init.apply(this, arguments);

			// create the views based on the url/hash
			this.getRouter().initialize();
		}
	});
});