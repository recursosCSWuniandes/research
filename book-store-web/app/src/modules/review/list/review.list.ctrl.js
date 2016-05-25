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
                        $state.go('reviewNew');
                    }
                },
                refresh: {
                    displayName: 'Refresh',
                    icon: 'refresh',
                    fn: function () {
                        $state.reload();
                    }
                },
                cancel: {
                    displayName: 'Go back',
                    icon: 'arrow-left',
                    fn: function () {
                        $state.go('bookDetails');
                    }
                }
            };
            $scope.recordActions = {
                edit: {
                    displayName: 'Edit',
                    icon: 'edit',
                    fn: function (rc) {
                        $state.go('reviewEdit', {bookId: book.id, reviewId: rc.id});
                    },
                    show: function () {
                        return true;
                    }
                },
                delete: {
                    displayName: 'Delete',
                    icon: 'minus',
                    fn: function (rc) {
                        $state.go('reviewDelete', {bookId: book.id, reviewId: rc.id});
                    },
                    show: function () {
                        return true;
                    }
                },
                details: {
                    displayName: 'Details',
                    icon: 'eye-open',
                    fn: function (rc) {
                        $state.go('reviewDetails', {bookId: book.id, reviewId: rc.id});
                    },
                    show: function () {
                        return true;
                    }
                }
            };
        }]);

})(window.angular);
