define([
	'backbone',
	'communicator',
	'views/headerView',
	'views/item/searchView',
	'socketio'
],

function( Backbone, Communicator, HeaderView, SearchView ) {
    'use strict';

	var App = new Backbone.Marionette.Application();

	/* Add application regions here */
	App.addRegions({
		headerRegion: '#header',
		contentRegion: '#content'
	});

	/* Add initializers here */
	App.addInitializer( function () {
		var socket = io.connect(window.opt.basePath.split(':8')[0]+':8081');
		App.headerRegion.show(new HeaderView({socket: socket}));
		//initialize views

		socket.on('connect', function() {
			console.log('connected');
            // var obj = { '@class' : 'org.bitfinger.pojo.Subscribe',
            //             'room' : account
            //           };
            // socket.json.send(obj);

        });
		socket.on('message', function (data) {
			console.log(data);
		});

		App.contentRegion.show(new SearchView({socket: socket}));

		//start router
		Communicator.mediator.trigger("APP:START");
	});

	return App;
});
