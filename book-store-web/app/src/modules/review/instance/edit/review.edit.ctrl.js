(function (ng) {

    var mod = ng.module("reviewModule");

    mod.controller("reviewEditCtrl", ["$scope", "$state", "review", "model",
        function ($scope, $state, review, model) {
            $scope.currentRecord = review;
            $scope.fields = model.fields;
            $scope.actions = {
                save: {
                    displayName: 'Save',
                    icon: 'save',
                    fn: function () {
                        if ($scope.reviewForm.$valid) {
                            $scope.currentRecord.put().then(function () {
                                $state.go('reviewList', null, {reload: true});
                            });
                        }
                    }
                },
                cancel: {
                    displayName: 'Cancel',
                    icon: 'remove',
                    fn: function () {
                        $state.go('reviewList');
                    }
                }
            };
        }]);
})(window.angular);
