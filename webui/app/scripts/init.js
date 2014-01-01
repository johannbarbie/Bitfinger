
require.config({

    

    /* starting point for application */
    deps: ['backbone.marionette', 'bootstrap', 'main'],


    shim: {
        'socketio': {
          exports: 'io'
        },
        backbone: {
            deps: [
                'underscore',
                'jquery'
            ],
            exports: 'Backbone'
        },
        lightbox: {
            deps: [
                'jquery'
            ],
            exports: 'Lightbox'
        },
        bootstrap: {
            deps: ['jquery'],
            exports: 'jquery'
        }
    },

    paths: {
        jquery: '../bower_components/jquery/jquery',
        backbone: '../bower_components/backbone-amd/backbone',
        underscore: '../bower_components/underscore-amd/underscore',

        /* alias all marionette libs */
        'backbone.marionette': '../bower_components/backbone.marionette/lib/core/amd/backbone.marionette',
        'backbone.wreqr': '../bower_components/backbone.wreqr/lib/amd/backbone.wreqr',
        'backbone.babysitter': '../bower_components/backbone.babysitter/lib/amd/backbone.babysitter',

        /* alias the bootstrap js lib */
        bootstrap: 'vendor/bootstrap',

        /* Alias text.js for template loading and shortcut the templates dir to tmpl */
        text: '../bower_components/requirejs-text/text',
        tmpl: "../templates",

        /* handlebars from the require handlerbars plugin below */
        handlebars: '../bower_components/require-handlebars-plugin/Handlebars',

        /* require handlebars plugin - Alex Sexton */
        i18nprecompile: '../bower_components/require-handlebars-plugin/hbs/i18nprecompile',
        json2: '../bower_components/require-handlebars-plugin/hbs/json2',
        hbs: '../bower_components/require-handlebars-plugin/hbs',
        lightbox: 'vendor/jquery.lightbox',
        socketio: '../bower_components/socket.io-client/dist/socket.io'
    },

    hbs: {
        disableI18n: true
    }
});
