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
    var mod = ng.module('bookModule', ['ngCrud', 'ui.router']);

    mod.constant('bookModel', {
        name: 'book',
        displayName: 'Book',
        url: 'books',
        fields: {
            name: {
                name: 'name',
                displayName: 'Name',
                type: 'String',
                required: true
            },
            description: {
                name: 'description',
                displayName: 'Description',
                type: 'String',
                required: true
            },
            isbn: {
                name: 'isbn',
                displayName: 'Isbn',
                type: 'String',
                required: true
            },
            image: {
                name: 'image',
                displayName: 'Image',
                type: 'Image',
                required: true
            },
            publishDate: {
                name: 'publishDate',
                displayName: 'Publish Date',
                type: 'Date',
                required: true
            },
            editorial: {
                name: 'editorial',
                displayName: 'Editorial',
                type: 'Reference',
                url: 'editorialModel',
                options: [],
                required: true
            }
        },
        childs: [{
                name: 'authors',
                displayName: 'Authors',
                //template: '', //override generic template
                ctrl: 'BooksauthorsCtrl',
                owned: false
            }, {
                name: 'reviews',
                displayName: 'Reviews',
                //template: '', //override generic template
                ctrl: 'BookreviewsCtrl',
                owned: true
            }]});

    mod.config(['$stateProvider',
        function ($stateProvider) {
            var basePath = 'src/modules/book/';
            $stateProvider.state('book', {
                url: '/book',
                abstract: true,
                views: {
                    mainView: {
                        templateUrl: basePath + 'book.tpl.html',
                        controller: 'bookCtrl'
                    }
                },
                resolve: {
                    references: ['$q', 'Restangular', function ($q, r) {
                            return $q.all({
                                editorials: r.all('editorials').getList()
                            });
                        }],
                    model: ['bookModel', 'references', function (model, references) {
                            model.fields.editorial.options = references.editorials;
                            return model;
                        }],
                    books: ['Restangular', 'model', function (r, model) {
                            return r.all(model.url).getList();
                        }]
                }
            }).state('book.list', {
                url: '/list',
                views: {
                    bookView: {
                        templateUrl: basePath + 'list/book.list.tpl.html',
                        controller: 'bookListCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            }).state('book.new', {
                url: '/new',
                views: {
                    bookView: {
                        templateUrl: basePath + 'new/book.new.tpl.html',
                        controller: 'bookNewCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            }).state('book.instance', {
                url: '/{id:int}',
                abstract: true,
                views: {
                    bookView: {
                        template: '<div ui-view="bookDetailsView"></div>',
                        controller: 'bookInstanceCtrl'
                    }
                },
                resolve: {
                    book: ['books', '$stateParams', function (books, $params) {
                            return books.get($params.id);
                        }]
                }
            }).state('book.instance.details', {
                url: '/',
                views: {
                    bookDetailsView: {
                        templateUrl: basePath + 'instance/details/book.detail.tpl.html',
                        controller: 'bookDetailCtrl'
                    }
                }
            }).state('book.instance.edit', {
                url: '/edit',
                views: {
                    bookDetailsView: {
                        templateUrl: basePath + 'instance/edit/book.edit.tpl.html',
                        controller: 'bookEditCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            }).state('book.instance.delete', {
                url: '/delete',
                views: {
                    bookDetailsView: {
                        templateUrl: basePath + 'instance/delete/book.delete.tpl.html',
                        controller: 'bookDeleteCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            });
        }]);
})(window.angular);
