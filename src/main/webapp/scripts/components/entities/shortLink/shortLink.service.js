'use strict';

angular.module('lkApp')
    .factory('ShortLink', function ($resource, DateUtils) {
        return $resource('api/shortLinks/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.creationDate = DateUtils.convertLocaleDateFromServer(data.creationDate);
                    data.updateDate = DateUtils.convertLocaleDateFromServer(data.updateDate);
                    data.lastClickDate = DateUtils.convertLocaleDateFromServer(data.lastClickDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.creationDate = DateUtils.convertLocaleDateToServer(data.creationDate);
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                    data.lastClickDate = DateUtils.convertLocaleDateToServer(data.lastClickDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.creationDate = DateUtils.convertLocaleDateToServer(data.creationDate);
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                    data.lastClickDate = DateUtils.convertLocaleDateToServer(data.lastClickDate);
                    return angular.toJson(data);
                }
            }
        });
    });
