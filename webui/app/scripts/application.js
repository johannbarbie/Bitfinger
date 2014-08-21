define([
	'backbone',
	'communicator',
	'views/headerView',
	'views/item/searchView',
	'socketio'
],

function( Backbone, Communicator, HeaderView, SearchView) {
    'use strict';

	var App = new Backbone.Marionette.Application();

	/* Add application regions here */
	App.addRegions({
		headerRegion: '#header',
		contentRegion: '#content'
	});

	/* Add initializers here */
	App.addInitializer( function () {
		App.headerRegion.show(new HeaderView());
		//initialize views

		App.contentRegion.show(new SearchView({model:new Backbone.Model({resPath:window.opt.resPath})}));

		//start router
		Communicator.mediator.trigger("APP:START");
	});

	return App;
});
