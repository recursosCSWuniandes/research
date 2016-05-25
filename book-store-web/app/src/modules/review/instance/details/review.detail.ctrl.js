(function (ng) {

    var mod = ng.module("reviewModule");

    mod.controller("reviewDetailCtrl", ["$scope", "review", '$state', function ($scope, review, $state) {
            $scope.currentRecord = review;
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
                }, scores: {
                    displayName: 'Scores',
                    icon: 'star',
                    fn: function () {
                        $state.go('scoreList');
                    }
                },
                cancel: {
                    displayName: 'Go back',
                    icon: 'arrow-left',
                    fn: function () {
                        $state.go('reviewList');
                    }
                }
            };
        }]);
})(window.angular);
