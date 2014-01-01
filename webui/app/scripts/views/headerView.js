define([
	'backbone',
	'hbs!tmpl/item/headerView'
],
function(Backbone, HeaderViewTmpl ){
    'use strict';

	return Backbone.Marionette.ItemView.extend({
		template: HeaderViewTmpl
	});
});
