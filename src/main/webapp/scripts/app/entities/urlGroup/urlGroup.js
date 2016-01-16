'use strict';

angular.module('lkApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('urlGroup', {
                parent: 'entity',
                url: '/urlGroups',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'lkApp.urlGroup.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/urlGroup/urlGroups.html',
                        controller: 'UrlGroupController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('urlGroup');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('urlGroup.detail', {
                parent: 'entity',
                url: '/urlGroup/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'lkApp.urlGroup.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/urlGroup/urlGroup-detail.html',
                        controller: 'UrlGroupDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('urlGroup');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'UrlGroup', function($stateParams, UrlGroup) {
                        return UrlGroup.get({id : $stateParams.id});
                    }]
                }
            })
            .state('urlGroup.new', {
                parent: 'urlGroup',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/urlGroup/urlGroup-dialog.html',
                        controller: 'UrlGroupDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    creationDate: null,
                                    updateDate: null,
                                    description: null,
                                    state: null,
                                    mainAddress: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('urlGroup', null, { reload: true });
                    }, function() {
                        $state.go('urlGroup');
                    })
                }]
            })
            .state('urlGroup.edit', {
                parent: 'urlGroup',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/urlGroup/urlGroup-dialog.html',
                        controller: 'UrlGroupDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['UrlGroup', function(UrlGroup) {
                                return UrlGroup.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('urlGroup', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('urlGroup.delete', {
                parent: 'urlGroup',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/urlGroup/urlGroup-delete-dialog.html',
                        controller: 'UrlGroupDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['UrlGroup', function(UrlGroup) {
                                return UrlGroup.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('urlGroup', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
