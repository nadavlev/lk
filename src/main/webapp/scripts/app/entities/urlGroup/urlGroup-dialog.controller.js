'use strict';

angular.module('lkApp').controller('UrlGroupDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'UrlGroup', 'User', 'LkAccount', 'ShortLink',
        function($scope, $stateParams, $uibModalInstance, entity, UrlGroup, User, LkAccount, ShortLink) {

        $scope.urlGroup = entity;
        $scope.users = User.query();
        $scope.lkaccounts = LkAccount.query();
        $scope.shortlinks = ShortLink.query();
        $scope.load = function(id) {
            UrlGroup.get({id : id}, function(result) {
                $scope.urlGroup = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('lkApp:urlGroupUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.urlGroup.id != null) {
                UrlGroup.update($scope.urlGroup, onSaveSuccess, onSaveError);
            } else {
                UrlGroup.save($scope.urlGroup, onSaveSuccess, onSaveError);
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
