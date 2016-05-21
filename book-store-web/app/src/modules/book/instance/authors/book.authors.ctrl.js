/*
 * The MIT License
 *
 * Copyright 2016 asistente.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */


(function (ng) {
    var mod = ng.module('bookModule');

    mod.controller('bookAuthorsCtrl', ['$scope', 'authors', 'pool', 'model', '$state',
        function ($scope, authors, available, model, $state) {
            function move(src, dst, selected) {
                // If selected is undefined, all records from src are moved to dst
                if (!!selected) {
                    for (var i = 0; i < selected.length; i++) {
                        if (selected.hasOwnProperty(i)) {
                            var index = null;
                            for (var j = 0; j < src.length; j++) {
                                if (src.hasOwnProperty(j)) {
                                    if (src[j].id === selected[i].id) {
                                        index = j;
                                        break;
                                    }
                                }
                            }
                            if (index !== null) {
                                dst.push(src.splice(index, 1)[0]);
                            }
                        }
                    }
                } else {
                    dst.push.apply(dst, src);
                    src.splice(0, src.length);
                }
            }
            move(available, [], authors);
            $scope.records = authors;
            $scope.fields = model.fields;
            $scope.available = available.plain();
            $scope.availableSelected = [];
            $scope.selected = [];

            $scope.addSome = function () {
                move($scope.available, $scope.records, $scope.availableSelected);
                $scope.availableSelected = [];
            };
            $scope.addAll = function () {
                move($scope.available, $scope.records);
                $scope.availableSelected = [];
            };
            $scope.removeSome = function () {
                move($scope.records, $scope.available, $scope.selected);
                $scope.selected = [];
            };
            $scope.removeAll = function () {
                move($scope.records, $scope.available);
                $scope.selected = [];
            };
            $scope.actions = {
                cancel: {
                    displayName: 'Cancel',
                    icon: 'remove',
                    fn: function () {
                        $state.reload();
                    }
                }
            };
        }]);
})(window.angular);
