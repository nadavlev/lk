'use strict';

angular.module('lkApp')
    .controller('UrlGroupDetailController', function ($scope, $rootScope, $stateParams, entity, UrlGroup, User, LkAccount, ShortLink) {
        $scope.urlGroup = entity;
        $scope.load = function (id) {
            UrlGroup.get({id: id}, function(result) {
                $scope.urlGroup = result;
            });
        };
        var unsubscribe = $rootScope.$on('lkApp:urlGroupUpdate', function(event, result) {
            $scope.urlGroup = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
