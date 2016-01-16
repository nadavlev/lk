'use strict';

angular.module('lkApp')
    .controller('UrlGroupController', function ($scope, $state, UrlGroup, ParseLinks) {

        $scope.urlGroups = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            UrlGroup.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.urlGroups = result;
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
            $scope.urlGroup = {
                name: null,
                creationDate: null,
                updateDate: null,
                description: null,
                state: null,
                mainAddress: null,
                id: null
            };
        };
    });
