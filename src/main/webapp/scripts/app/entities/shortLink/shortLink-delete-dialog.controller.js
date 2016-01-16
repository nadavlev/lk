'use strict';

angular.module('lkApp')
	.controller('ShortLinkDeleteController', function($scope, $uibModalInstance, entity, ShortLink) {

        $scope.shortLink = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            ShortLink.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
