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
