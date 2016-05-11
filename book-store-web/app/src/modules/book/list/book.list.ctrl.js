(function (ng) {

    var mod = ng.module("bookModule");

    mod.controller("bookListCtrl", ["$scope", 'books', '$state', function ($scope, books, $state) {
            $scope.records = books;
            $scope.actions = {
                create: {
                    displayName: 'Create',
                    icon: 'plus',
                    fn: function () {
                        $state.go('book.new');
                    }
                },
                refresh: {
                    displayName: 'Refresh',
                    icon: 'refresh',
                    fn: function () {
                        $state.reload();
                    }
                }
            };
        }]);

})(window.angular);
