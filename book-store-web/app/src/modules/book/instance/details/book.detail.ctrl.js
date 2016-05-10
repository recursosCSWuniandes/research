(function (ng) {

    var mod = ng.module("bookModule");

    mod.controller("bookDetailCtrl", ["$scope", "book", function ($scope, book) {
            $scope.currentRecord = book;
        }]);
})(window.angular);
