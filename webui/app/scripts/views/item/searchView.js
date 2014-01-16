define([
	'backbone',
	'hbs!tmpl/item/searchView_tmpl',
	'webfinger'
],
function( Backbone, SearchTmpl, webfinger) {
    'use strict';

	/* Return a ItemView class definition */
	return Backbone.Marionette.ItemView.extend({
		template: SearchTmpl,
		initialize: function() {
			console.log('initialize a searchView');
		},
		events: {
            'click button':'handleClick'
        },
        handleResponse: function(err, p){
			this.$('button').button('reset');
		    if (!err) {
		        var data = JSON.parse(p.JRD).links[0].href.split(':')[1];
		        this.$('#result').append('<p><img id="'+data+'" width="200px;" src="https://chart.googleapis.com/chart?cht=qr&chs=400x400&chl=bitcoin:'+data+'&chld=H|0" /></p>');
				this.$('#result').append( '<a href="bitcoin:'+data+'">bitcoin:'+data+'</a>');
		    }else{
				this.$('#result').append('<p>not found</p>');
		    }
        },
        handleClick: function(e){
			e.preventDefault();
			this.$('button').button('loading');
			$('#result').empty();
			var hash = $('#searchInput').val();
			if (hash.indexOf('@')==-1){
				hash = hash + '@www.37coins.com';
			}
			var self = this;
			webfinger(hash, {
			    webfist_fallback: true,  // defaults to true
			    tls_only: true,          // defaults to true
			    uri_fallback: false,     // defaults to false
			    debug: false             // defaults to false
			}, function(err, p){
				self.handleResponse(err,p);
			});
        },
        onShow: function(e){
			this.$('[name="search"]').focus();
			this.$('#logo').append('<img id="bitfingerLogo" src="'+window.opt.resPath+'/images/logo.png" />');
        }
	});

});
