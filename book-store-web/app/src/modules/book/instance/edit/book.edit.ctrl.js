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
                                $state.go('bookDetails', {bookId: book.id}, {reload: true});
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
                }
            };
        }]);
})(window.angular);
