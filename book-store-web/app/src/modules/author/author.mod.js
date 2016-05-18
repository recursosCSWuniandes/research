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
    var mod = ng.module('authorModule', ['ngCrud', 'ui.router']);

    mod.constant('authorContext', 'authors');

    mod.constant('authorModel', {
        name: 'author',
        displayName: 'Author',
        url: 'authors',
        fields: {
            name: {
                name: 'name',
                displayName: 'Name',
                type: 'String',
                required: true
            },
            birthDate: {
                name: 'birthDate',
                displayName: 'Birth Date',
                type: 'Date',
                required: true
            }},
        childs: [{
                name: 'books',
                displayName: 'Books',
                //template: '', //override generic template
                ctrl: 'AuthorsauthorsCtrl',
                owned: false
            }]});

    mod.config(['$stateProvider',
        function ($stateProvider) {
            var basePath = 'src/modules/author/';
            $stateProvider.state('author', {
                url: '/author',
                abstract: true,
                views: {
                    mainView: {
                        templateUrl: basePath + 'author.tpl.html',
                        controller: 'authorCtrl'
                    }
                },
                resolve: {
                    model: 'authorModel',
                    authors: ['Restangular', 'model', function (r, model) {
                            return r.all(model.url).getList();
                        }]
                }
            }).state('authorList', {
                url: '/list',
                parent: 'author',
                views: {
                    authorView: {
                        templateUrl: basePath + 'list/author.list.tpl.html',
                        controller: 'authorListCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            }).state('authorNew', {
                url: '/new',
                parent: 'author',
                views: {
                    authorView: {
                        templateUrl: basePath + 'new/author.new.tpl.html',
                        controller: 'authorNewCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            }).state('authorInstance', {
                url: '/{authorId:int}',
                abstract: true,
                parent: 'author',
                views: {
                    authorView: {
                        template: '<div ui-view="authorDetailsView"></div>'
                    }
                },
                resolve: {
                    author: ['authors', '$stateParams', function (authors, $params) {
                            return authors.get($params.authorId);
                        }]
                }
            }).state('authorDetails', {
                url: '/',
                parent: 'authorInstance',
                views: {
                    authorDetailsView: {
                        templateUrl: basePath + 'instance/details/author.detail.tpl.html',
                        controller: 'authorDetailCtrl'
                    }
                }
            }).state('authorEdit', {
                url: '/edit',
                sticky: true,
                parent: 'authorInstance',
                views: {
                    authorDetailsView: {
                        templateUrl: basePath + 'instance/edit/author.edit.tpl.html',
                        controller: 'authorEditCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            }).state('authorDelete', {
                url: '/delete',
                parent: 'authorInstance',
                views: {
                    authorDetailsView: {
                        templateUrl: basePath + 'instance/delete/author.delete.tpl.html',
                        controller: 'authorDeleteCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            });
        }]);
})(window.angular);
