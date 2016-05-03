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

    mod.constant('bookContext', 'books');

    mod.constant('bookModel', {
        name: 'book',
        displayName: 'Book',
        url: 'books',
        fields: [{
                name: 'name',
                displayName: 'Name',
                type: 'String',
                required: true
            }, {
                name: 'description',
                displayName: 'Description',
                type: 'String',
                required: true
            }, {
                name: 'isbn',
                displayName: 'Isbn',
                type: 'String',
                required: true
            }, {
                name: 'image',
                displayName: 'Image',
                type: 'String',
                required: true
            }, {
                name: 'publishDate',
                displayName: 'Publish Date',
                type: 'Date',
                required: true
            }, {
                name: 'editorial',
                displayName: 'Editorial',
                type: 'Reference',
                url: 'editorialContext',
                options: [],
                required: true
            }],
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

    mod.config(['$stateProvider', 'CrudTemplateURL', 'CrudCtrlAlias',
        function ($stateProvider, tplUrl, alias) {
            $stateProvider.state('book', {
                url: '/book',
                views: {
                    mainView: {
                        templateUrl: tplUrl,
                        controller: 'bookCtrl',
                        controllerAs: alias
                    }
                }
            });
        }]);
})(window.angular);
