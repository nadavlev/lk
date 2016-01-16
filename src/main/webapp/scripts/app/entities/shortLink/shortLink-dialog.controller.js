'use strict';

angular.module('lkApp').controller('ShortLinkDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'ShortLink', 'User', 'UrlGroup',
        function($scope, $stateParams, $uibModalInstance, entity, ShortLink, User, UrlGroup) {

        $scope.shortLink = entity;
        $scope.users = User.query();
        $scope.urlgroups = UrlGroup.query();
        $scope.load = function(id) {
            ShortLink.get({id : id}, function(result) {
                $scope.shortLink = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('lkApp:shortLinkUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.shortLink.id != null) {
                ShortLink.update($scope.shortLink, onSaveSuccess, onSaveError);
            } else {
                ShortLink.save($scope.shortLink, onSaveSuccess, onSaveError);
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
        $scope.datePickerForLastClickDate = {};

        $scope.datePickerForLastClickDate.status = {
            opened: false
        };

        $scope.datePickerForLastClickDateOpen = function($event) {
            $scope.datePickerForLastClickDate.status.opened = true;
        };
}]);
