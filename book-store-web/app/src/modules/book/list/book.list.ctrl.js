(function (ng) {

    var mod = ng.module("bookModule");

    mod.controller("bookListCtrl", ["$scope", 'books', function ($scope, books) {
            $scope.records = books;
            this.refresh = function () {
                $scope.records = books.getList().$object;
            };
        }]);

})(window.angular);
