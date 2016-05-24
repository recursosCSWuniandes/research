(function (ng) {

    var mod = ng.module("scoreModule");

    mod.controller("scoreListCtrl", ["$scope", 'scores', '$state', 'scoreModel', 'review',
        function ($scope, scores, $state, model, review) {
            $scope.records = scores;
            $scope.fields = model.fields;
            $scope.actions = {
                create: {
                    displayName: 'Create',
                    icon: 'plus',
                    fn: function () {
                        $state.go('scoreNew');
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
                        $state.go('scoreEdit', {reviewId: review.id, scoreId: rc.id});
                    },
                    show: function () {
                        return true;
                    }
                },
                delete: {
                    displayName: 'Delete',
                    icon: 'minus',
                    fn: function (rc) {
                        $state.go('scoreDelete', {reviewId: review.id, scoreId: rc.id});
                    },
                    show: function () {
                        return true;
                    }
                }
            };
        }]);

})(window.angular);
