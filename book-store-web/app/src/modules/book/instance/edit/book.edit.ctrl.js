(function (ng) {

    var mod = ng.module("bookModule");

    mod.controller("bookEditCtrl", ["$scope", "$state", "book", "model",
        function ($scope, $state, book, model) {
            $scope.currentRecord = book;
            $scope.fields = model.fields;
            $scope.go = function (child) {
                $state.go(child);
            };
            $scope.actions = {
                save: {
                    displayName: 'Save',
                    icon: 'save',
                    fn: function () {
                        if ($scope.bookForm.$valid) {
                            $scope.currentRecord.put().then(function () {
                                $state.go('bookList', null, {reload: true});
                            });
                        }
                    }
                },
                cancel: {
                    displayName: 'Cancel',
                    icon: 'remove',
                    fn: function () {
                        $state.go('bookList');
                    }
                },
                authors: {
                    displayName: 'Authors',
                    icon: 'user',
                    fn: function () {
                        $state.go('bookAuthorsList');
                    }
                }, reviews: {
                    displayName: 'Reviews',
                    icon: 'star',
                    fn: function () {
                        $state.go('reviewList');
                    }
                }
            };
        }]);
})(window.angular);
