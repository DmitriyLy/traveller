'use strict';

var app = angular.module('app', ['ngResource', 'pascalprecht.translate']);

app.config(function($translateProvider) {
    $translateProvider.useStaticFilesLoader({
        prefix: 'l10n/locale-',
        suffix: '.json'
    });
    $translateProvider.preferredLanguage('en');
    $translateProvider.useSanitizeValueStrategy('escape');
});