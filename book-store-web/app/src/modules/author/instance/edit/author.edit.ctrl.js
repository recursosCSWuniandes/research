(function (ng) {

    var mod = ng.module("authorModule");

    mod.controller("authorEditCtrl", ["$scope", "$state", "author", "model",
        function ($scope, $state, author, model) {
            $scope.currentRecord = author;
            $scope.fields = model.fields;
            $scope.go = function (child) {
                $state.go('author.instance.edit.' + child + '.list');
            };
            $scope.actions = {
                save: {
                    displayName: 'Save',
                    icon: 'save',
                    fn: function () {
                        if ($scope.authorForm.$valid) {
                            $scope.currentRecord.put().then(function () {
                                $state.go('author.list', null, {reload: true});
                            });
                        }
                    }
                },
                cancel: {
                    displayName: 'Cancel',
                    icon: 'remove',
                    fn: function () {
                        $state.go('author.list');
                    }
                }
            };
        }]);
})(window.angular);
