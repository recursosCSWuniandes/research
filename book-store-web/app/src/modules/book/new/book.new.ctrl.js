(function (ng) {

    var mod = ng.module("bookModule");

    mod.controller("bookNewCtrl", ["$scope", "$state", "books", "references",
        function ($scope, $state, books, references) {
            $scope.editorials = references.editorials;
            this.saveRecord = function (record) {
                books.post(record).then(function () {
                    $state.go('book.list', null, {reload: true});
                });
            };
        }]);
})(window.angular);
