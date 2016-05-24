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
    var mod = ng.module('scoreModule', ['ngCrud', 'ui.router', 'scoreModule']);

    mod.constant('scoreContext', 'scores');

    mod.constant('scoreModel', {
        name: 'score',
        displayName: 'Score',
        url: 'scores',
        fields: {
            score: {
                displayName: 'Score',
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
            var basePath = 'src/modules/score/';
            $stateProvider.state('score', {
                url: '/scores',
                abstract: true,
                parent: 'reviewEdit',
                views: {
                    reviewChildView: {
                        templateUrl: basePath + 'score.tpl.html',
                        controller: 'scoreCtrl'
                    }
                },
                resolve: {
                    scores: ['review', function (review) {
                            return review.getList('scores');
                        }],
                    model: 'scoreModel'
                }
            }).state('scoreList', {
                url: '/list',
                parent: 'score',
                views: {
                    scoreView: {
                        templateUrl: basePath + 'list/score.list.tpl.html',
                        controller: 'scoreListCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            }).state('scoreNew', {
                url: '/new',
                parent: 'score',
                views: {
                    scoreView: {
                        templateUrl: basePath + 'new/score.new.tpl.html',
                        controller: 'scoreNewCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            }).state('scoreInstance', {
                url: '/{scoreId:int}',
                abstract: true,
                parent: 'score',
                views: {
                    scoreView: {
                        template: '<div ui-view="scoreDetailsView"></div>'
                    }
                },
                resolve: {
                    score: ['scores', '$stateParams', function (scores, $params) {
                            return scores.get($params.scoreId);
                        }]
                }
            }).state('scoreDetails', {
                url: '/',
                parent: 'scoreInstance',
                views: {
                    scoreDetailsView: {
                        templateUrl: basePath + 'instance/details/score.detail.tpl.html',
                        controller: 'scoreDetailCtrl'
                    }
                }
            }).state('scoreEdit', {
                url: '/edit',
                parent: 'scoreInstance',
                views: {
                    scoreDetailsView: {
                        templateUrl: basePath + 'instance/edit/score.edit.tpl.html',
                        controller: 'scoreEditCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            }).state('scoreDelete', {
                url: '/delete',
                parent: 'scoreInstance',
                views: {
                    scoreDetailsView: {
                        templateUrl: basePath + 'instance/delete/score.delete.tpl.html',
                        controller: 'scoreDeleteCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            });
        }]);
})(window.angular);
