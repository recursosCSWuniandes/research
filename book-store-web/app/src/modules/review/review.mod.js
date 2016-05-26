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
    var mod = ng.module('reviewModule', ['ngCrud', 'ui.router', 'reviewModule']);

    mod.constant('reviewContext', 'reviews');

    mod.constant('reviewModel', {
        name: 'review',
        displayName: 'Review',
        url: 'reviews',
        fields: {
            name: {
                displayName: 'Name',
                type: 'String',
                required: true
            },
            source: {
                displayName: 'Source',
                type: 'String',
                required: true
            },
            description: {
                displayName: 'Description',
                type: 'String',
                required: true
            }
        }
    });

    mod.config(['$stateProvider', function ($stateProvider) {
            var basePath = 'src/modules/review/';
            $stateProvider.state('review', {
                url: '/reviews',
                abstract: true,
                parent: 'bookDetails',
                views: {
                    bookChildView: {
                        templateUrl: basePath + 'review.tpl.html',
                        controller: 'reviewCtrl'
                    }
                },
                resolve: {
                    reviews: ['book', function (book) {
                            return book.getList('reviews');
                        }],
                    model: 'reviewModel'
                }
            }).state('reviewList', {
                url: '/list',
                parent: 'review',
                views: {
                    reviewView: {
                        templateUrl: basePath + 'list/review.list.tpl.html',
                        controller: 'reviewListCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            }).state('reviewNew', {
                url: '/new',
                parent: 'review',
                views: {
                    reviewView: {
                        templateUrl: basePath + 'new/review.new.tpl.html',
                        controller: 'reviewNewCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            }).state('reviewInstance', {
                url: '/{reviewId:int}',
                abstract: true,
                parent: 'review',
                views: {
                    reviewView: {
                        template: '<div ui-view="reviewDetailsView"></div>'
                    }
                },
                resolve: {
                    review: ['reviews', '$stateParams', function (reviews, $params) {
                            return reviews.get($params.reviewId);
                        }]
                }
            }).state('reviewDetails', {
                url: '/details',
                parent: 'reviewInstance',
                views: {
                    reviewDetailsView: {
                        templateUrl: basePath + 'instance/details/review.detail.tpl.html',
                        controller: 'reviewDetailCtrl'
                    }
                }
            }).state('reviewEdit', {
                url: '/edit',
                parent: 'reviewInstance',
                views: {
                    reviewDetailsView: {
                        templateUrl: basePath + 'instance/edit/review.edit.tpl.html',
                        controller: 'reviewEditCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            }).state('reviewDelete', {
                url: '/delete',
                parent: 'reviewInstance',
                views: {
                    reviewDetailsView: {
                        templateUrl: basePath + 'instance/delete/review.delete.tpl.html',
                        controller: 'reviewDeleteCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            });
        }]);
})(window.angular);
