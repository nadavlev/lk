'use strict';

angular.module('lkApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('shortLink', {
                parent: 'entity',
                url: '/shortLinks',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'lkApp.shortLink.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/shortLink/shortLinks.html',
                        controller: 'ShortLinkController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('shortLink');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('shortLink.detail', {
                parent: 'entity',
                url: '/shortLink/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'lkApp.shortLink.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/shortLink/shortLink-detail.html',
                        controller: 'ShortLinkDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('shortLink');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'ShortLink', function($stateParams, ShortLink) {
                        return ShortLink.get({id : $stateParams.id});
                    }]
                }
            })
            .state('shortLink.new', {
                parent: 'shortLink',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/shortLink/shortLink-dialog.html',
                        controller: 'ShortLinkDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
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
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('shortLink', null, { reload: true });
                    }, function() {
                        $state.go('shortLink');
                    })
                }]
            })
            .state('shortLink.edit', {
                parent: 'shortLink',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/shortLink/shortLink-dialog.html',
                        controller: 'ShortLinkDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['ShortLink', function(ShortLink) {
                                return ShortLink.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('shortLink', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('shortLink.delete', {
                parent: 'shortLink',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/shortLink/shortLink-delete-dialog.html',
                        controller: 'ShortLinkDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['ShortLink', function(ShortLink) {
                                return ShortLink.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('shortLink', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
