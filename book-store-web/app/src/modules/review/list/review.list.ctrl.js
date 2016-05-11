(function (ng) {

    var mod = ng.module("reviewModule");

    mod.controller("reviewListCtrl", ["$scope", 'reviews', '$state', 'reviewModel',
        function ($scope, reviews, $state, model) {
            $scope.records = reviews;
            $scope.fields = model.fields;
            $scope.actions = {
                create: {
                    displayName: 'Create',
                    icon: 'plus',
                    fn: function () {
                        $state.go('book.edit.review.new');
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
