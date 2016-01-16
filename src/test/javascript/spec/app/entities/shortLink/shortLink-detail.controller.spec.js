'use strict';

describe('Controller Tests', function() {

    describe('ShortLink Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockShortLink, MockUser, MockUrlGroup;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockShortLink = jasmine.createSpy('MockShortLink');
            MockUser = jasmine.createSpy('MockUser');
            MockUrlGroup = jasmine.createSpy('MockUrlGroup');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'ShortLink': MockShortLink,
                'User': MockUser,
                'UrlGroup': MockUrlGroup
            };
            createController = function() {
                $injector.get('$controller')("ShortLinkDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'lkApp:shortLinkUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
