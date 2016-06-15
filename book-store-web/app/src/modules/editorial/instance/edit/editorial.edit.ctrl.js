(function (ng) {

    var mod = ng.module("editorialModule");

    mod.controller("editorialEditCtrl", ["$scope", "$state", "editorial", "model",
        function ($scope, $state, editorial, model) {
            $scope.currentRecord = editorial;
            $scope.fields = model.fields;
            $scope.actions = {
                save: {
                    displayName: 'Save',
                    icon: 'save',
                    fn: function () {
                        if ($scope.editorialForm.$valid) {
                            $scope.currentRecord.put().then(function () {
                                $state.go('editorialList', null, {reload: true});
                            });
                        }
                    }
                },
                cancel: {
                    displayName: 'Cancel',
                    icon: 'remove',
                    fn: function () {
                        $state.go('editorialList');
                    }
                }
            };
        }]);
})(window.angular);
