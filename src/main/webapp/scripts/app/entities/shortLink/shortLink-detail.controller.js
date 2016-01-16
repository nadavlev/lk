'use strict';

angular.module('lkApp')
    .controller('ShortLinkDetailController', function ($scope, $rootScope, $stateParams, entity, ShortLink, User, UrlGroup) {
        $scope.shortLink = entity;
        $scope.load = function (id) {
            ShortLink.get({id: id}, function(result) {
                $scope.shortLink = result;
            });
        };
        var unsubscribe = $rootScope.$on('lkApp:shortLinkUpdate', function(event, result) {
            $scope.shortLink = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
