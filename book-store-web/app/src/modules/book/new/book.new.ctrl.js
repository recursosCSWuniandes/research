(function (ng) {

    var mod = ng.module("bookModule");

    mod.controller("bookNewCtrl", ["$scope", "$state", "books", 'model',
        function ($scope, $state, books, model) {
            $scope.currentRecord = {};
            $scope.fields = model.fields;
            $scope.actions = {
                save: {
                    displayName: 'Save',
                    icon: 'save',
                    fn: function () {
                        if ($scope.form.$valid) {
                            books.post($scope.currentRecord).then(function () {
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
                }
            };
        }]);
})(window.angular);
