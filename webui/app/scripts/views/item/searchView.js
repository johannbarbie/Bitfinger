define([
	'backbone',
	'hbs!tmpl/item/searchView_tmpl'
],
function( Backbone, SearchTmpl  ) {
    'use strict';

	/* Return a ItemView class definition */
	return Backbone.Marionette.ItemView.extend({
		template: SearchTmpl,
		initialize: function(opt) {
			console.log("initialize a searchView");
			this.socket = opt.socket;
		},
		events: {
            'click button':'handleClick'
        },
        handleClick: function(e){
			e.preventDefault();
			var hash = $('#phoneInput').val();
			console.log(hash);
			var obj = { '@class' : 'org.bitfinger.pojo.Subscribe',
                'room' : hash
            };
            this.socket.json.send(obj);
        },
	});

});
