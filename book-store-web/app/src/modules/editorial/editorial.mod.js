/*
 The MIT License (MIT)

 Copyright (c) 2015 Los Andes University

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all
 copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 SOFTWARE.
 */
(function (ng) {
    var mod = ng.module('editorialModule', ['ngCrud', 'ui.router']);

    mod.constant('editorialContext', 'editorials');

    mod.constant('editorialModel', {
        name: 'editorial',
        displayName: 'Editorial',
        url: 'editorials',
        fields: {
            name: {
                displayName: 'Name',
                type: 'String',
                required: true
            }
        }});

    mod.config(['$stateProvider',
        function ($stateProvider) {
            var basePath = 'src/modules/editorial/';
            $stateProvider.state('editorial', {
                url: '/editorial',
                abstract: true,
                views: {
                    mainView: {
                        templateUrl: basePath + 'editorial.tpl.html',
                        controller: 'editorialCtrl'
                    }
                },
                resolve: {
                    model: 'editorialModel',
                    editorials: ['Restangular', 'model', function (r, model) {
                            return r.all(model.url).getList();
                        }]
                }
            }).state('editorialList', {
                url: '/list',
                parent: 'editorial',
                views: {
                    editorialView: {
                        templateUrl: basePath + 'list/editorial.list.tpl.html',
                        controller: 'editorialListCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            }).state('editorialNew', {
                url: '/new',
                parent: 'editorial',
                views: {
                    editorialView: {
                        templateUrl: basePath + 'new/editorial.new.tpl.html',
                        controller: 'editorialNewCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            }).state('editorialInstance', {
                url: '/{editorialId:int}',
                abstract: true,
                parent: 'editorial',
                views: {
                    editorialView: {
                        template: '<div ui-view="editorialDetailsView"></div>'
                    }
                },
                resolve: {
                    editorial: ['editorials', '$stateParams', function (editorials, $params) {
                            return editorials.get($params.editorialId);
                        }]
                }
            }).state('editorialDetails', {
                url: '/',
                parent: 'editorialInstance',
                views: {
                    editorialDetailsView: {
                        templateUrl: basePath + 'instance/details/editorial.detail.tpl.html',
                        controller: 'editorialDetailCtrl'
                    }
                }
            }).state('editorialEdit', {
                url: '/edit',
                sticky: true,
                parent: 'editorialInstance',
                views: {
                    editorialDetailsView: {
                        templateUrl: basePath + 'instance/edit/editorial.edit.tpl.html',
                        controller: 'editorialEditCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            }).state('editorialDelete', {
                url: '/delete',
                parent: 'editorialInstance',
                views: {
                    editorialDetailsView: {
                        templateUrl: basePath + 'instance/delete/editorial.delete.tpl.html',
                        controller: 'editorialDeleteCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            });
        }]);
})(window.angular);
