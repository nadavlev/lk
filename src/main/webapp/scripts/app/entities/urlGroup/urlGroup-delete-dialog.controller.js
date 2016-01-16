'use strict';

angular.module('lkApp')
	.controller('UrlGroupDeleteController', function($scope, $uibModalInstance, entity, UrlGroup) {

        $scope.urlGroup = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            UrlGroup.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
