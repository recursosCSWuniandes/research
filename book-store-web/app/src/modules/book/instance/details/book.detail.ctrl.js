(function (ng) {

    var mod = ng.module("bookModule");

    mod.controller("bookDetailCtrl", ["$scope", "book", '$state', function ($scope, book, $state) {
            $scope.currentRecord = book;
            $scope.actions = {
                create: {
                    displayName: 'Create',
                    icon: 'plus',
                    fn: function () {
                        $state.go('bookNew');
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
