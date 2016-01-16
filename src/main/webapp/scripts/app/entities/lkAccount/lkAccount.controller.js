'use strict';

angular.module('lkApp')
    .controller('LkAccountController', function ($scope, $state, LkAccount, ParseLinks) {

        $scope.lkAccounts = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            LkAccount.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.lkAccounts = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.lkAccount = {
                name: null,
                creationDate: null,
                updateDate: null,
                description: null,
                state: null,
                isDefault: null,
                id: null
            };
        };
    });
