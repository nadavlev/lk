'use strict';

describe('Controller Tests', function() {

    describe('UrlGroup Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockUrlGroup, MockUser, MockLkAccount, MockShortLink;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockUrlGroup = jasmine.createSpy('MockUrlGroup');
            MockUser = jasmine.createSpy('MockUser');
            MockLkAccount = jasmine.createSpy('MockLkAccount');
            MockShortLink = jasmine.createSpy('MockShortLink');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'UrlGroup': MockUrlGroup,
                'User': MockUser,
                'LkAccount': MockLkAccount,
                'ShortLink': MockShortLink
            };
            createController = function() {
                $injector.get('$controller')("UrlGroupDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'lkApp:urlGroupUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
