'use strict';

angular.module('lkApp')
	.controller('LkAccountDeleteController', function($scope, $uibModalInstance, entity, LkAccount) {

        $scope.lkAccount = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            LkAccount.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
