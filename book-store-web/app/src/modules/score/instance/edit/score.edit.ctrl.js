(function (ng) {

    var mod = ng.module("scoreModule");

    mod.controller("scoreEditCtrl", ["$scope", "$state", "score", "model",
        function ($scope, $state, score, model) {
            $scope.currentRecord = score;
            $scope.fields = model.fields;
            $scope.actions = {
                save: {
                    displayName: 'Save',
                    icon: 'save',
                    fn: function () {
                        if ($scope.scoreForm.$valid) {
                            $scope.currentRecord.put().then(function () {
                                $state.go('scoreList', null, {reload: true});
                            });
                        }
                    }
                },
                cancel: {
                    displayName: 'Cancel',
                    icon: 'remove',
                    fn: function () {
                        $state.go('scoreList');
                    }
                }
            };
        }]);
})(window.angular);
