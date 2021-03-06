(function (ng) {

    var mod = ng.module("bookModule");

    mod.controller("bookDeleteCtrl", ["$state", "book", function ($state, book) {
            this.confirmDelete = function () {
                book.remove().then(function () {
                    $state.go('bookList', null, {reload: true});
                });
            };
        }]);
})(window.angular);
