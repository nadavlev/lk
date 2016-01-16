'use strict';

angular.module('lkApp')
    .controller('LkAccountDetailController', function ($scope, $rootScope, $stateParams, entity, LkAccount, User) {
        $scope.lkAccount = entity;
        $scope.load = function (id) {
            LkAccount.get({id: id}, function(result) {
                $scope.lkAccount = result;
            });
        };
        var unsubscribe = $rootScope.$on('lkApp:lkAccountUpdate', function(event, result) {
            $scope.lkAccount = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
