'use strict';

angular.module('lkApp').controller('LkAccountDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'LkAccount', 'User',
        function($scope, $stateParams, $uibModalInstance, entity, LkAccount, User) {

        $scope.lkAccount = entity;
        $scope.users = User.query();
        $scope.load = function(id) {
            LkAccount.get({id : id}, function(result) {
                $scope.lkAccount = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('lkApp:lkAccountUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.lkAccount.id != null) {
                LkAccount.update($scope.lkAccount, onSaveSuccess, onSaveError);
            } else {
                LkAccount.save($scope.lkAccount, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.datePickerForCreationDate = {};

        $scope.datePickerForCreationDate.status = {
            opened: false
        };

        $scope.datePickerForCreationDateOpen = function($event) {
            $scope.datePickerForCreationDate.status.opened = true;
        };
        $scope.datePickerForUpdateDate = {};

        $scope.datePickerForUpdateDate.status = {
            opened: false
        };

        $scope.datePickerForUpdateDateOpen = function($event) {
            $scope.datePickerForUpdateDate.status.opened = true;
        };
}]);
