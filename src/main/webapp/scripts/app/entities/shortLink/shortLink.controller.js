'use strict';

angular.module('lkApp')
    .controller('ShortLinkController', function ($scope, $state, ShortLink, ParseLinks) {

        $scope.shortLinks = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            ShortLink.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.shortLinks = result;
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
            $scope.shortLink = {
                name: null,
                destination: null,
                creationDate: null,
                updateDate: null,
                description: null,
                state: null,
                shortLink: null,
                totalClicks: null,
                lastClickDate: null,
                indexOfKeys: null,
                timeout: null,
                id: null
            };
        };
    });
