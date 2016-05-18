(function (ng) {

    var mod = ng.module("reviewModule");

    mod.controller("reviewListCtrl", ["$scope", 'reviews', '$state', 'reviewModel', 'book',
        function ($scope, reviews, $state, model, book) {
            $scope.records = reviews;
            $scope.fields = model.fields;
            $scope.actions = {
                create: {
                    displayName: 'Create',
                    icon: 'plus',
                    fn: function () {
                        $state.go('book.instance.edit.reviews.new');
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
            $scope.recordActions = {
                edit: {
                    displayName: 'Edit',
                    icon: 'edit',
                    fn: function (rc) {
                        $state.go('book.instance.edit.reviews.instance.edit', {bookId: book.id, reviewId: rc.id});
                    },
                    show: function () {
                        return true;
                    }
                },
                delete: {
                    displayName: 'Delete',
                    icon: 'minus',
                    fn: function (rc) {
                        $state.go('book.instance.edit.reviews.instance.delete', {bookId: book.id, reviewId: rc.id});
                    },
                    show: function () {
                        return true;
                    }
                }
            };
        }]);

})(window.angular);