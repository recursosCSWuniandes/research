(function (ng) {

    var mod = ng.module("editorialModule");

    mod.controller("editorialEditCtrl", ["$scope", "$state", "editorial", "model",
        function ($scope, $state, editorial, model) {
            $scope.currentRecord = editorial;
            $scope.fields = model.fields;
            $scope.go = function (child) {
                $state.go('editorial.instance.edit.' + child + '.list');
            };
            $scope.actions = {
                save: {
                    displayName: 'Save',
                    icon: 'save',
                    fn: function () {
                        if ($scope.editorialForm.$valid) {
                            $scope.currentRecord.put().then(function () {
                                $state.go('editorial.list', null, {reload: true});
                            });
                        }
                    }
                },
                cancel: {
                    displayName: 'Cancel',
                    icon: 'remove',
                    fn: function () {
                        $state.go('editorial.list');
                    }
                }
            };
        }]);
})(window.angular);
