(function (ng) {

    var mod = ng.module("bookModule");

    mod.controller("bookEditCtrl", ["$scope", "$state", "book", "model",
        function ($scope, $state, book, model) {
            $scope.currentRecord = book;
            $scope.fields = model.fields;
            $scope.go = function (child) {
                $state.go('book.instance.edit.' + child + '.list');
            };
            $scope.actions = {
                save: {
                    displayName: 'Save',
                    icon: 'save',
                    fn: function () {
                        if ($scope.bookForm.$valid) {
                            $scope.currentRecord.put().then(function () {
                                $state.go('book.list', null, {reload: true});
                            });
                        }
                    }
                },
                cancel: {
                    displayName: 'Cancel',
                    icon: 'remove',
                    fn: function () {
                        $state.go('book.list');
                    }
                }
            };
        }]);
})(window.angular);
