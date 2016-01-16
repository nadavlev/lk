'use strict';

angular.module('lkApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('lkAccount', {
                parent: 'entity',
                url: '/lkAccounts',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'lkApp.lkAccount.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/lkAccount/lkAccounts.html',
                        controller: 'LkAccountController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('lkAccount');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('lkAccount.detail', {
                parent: 'entity',
                url: '/lkAccount/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'lkApp.lkAccount.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/lkAccount/lkAccount-detail.html',
                        controller: 'LkAccountDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('lkAccount');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'LkAccount', function($stateParams, LkAccount) {
                        return LkAccount.get({id : $stateParams.id});
                    }]
                }
            })
            .state('lkAccount.new', {
                parent: 'lkAccount',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/lkAccount/lkAccount-dialog.html',
                        controller: 'LkAccountDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    creationDate: null,
                                    updateDate: null,
                                    description: null,
                                    state: null,
                                    isDefault: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('lkAccount', null, { reload: true });
                    }, function() {
                        $state.go('lkAccount');
                    })
                }]
            })
            .state('lkAccount.edit', {
                parent: 'lkAccount',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/lkAccount/lkAccount-dialog.html',
                        controller: 'LkAccountDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['LkAccount', function(LkAccount) {
                                return LkAccount.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('lkAccount', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('lkAccount.delete', {
                parent: 'lkAccount',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/lkAccount/lkAccount-delete-dialog.html',
                        controller: 'LkAccountDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['LkAccount', function(LkAccount) {
                                return LkAccount.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('lkAccount', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
