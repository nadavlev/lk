'use strict';

angular.module('lkApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


