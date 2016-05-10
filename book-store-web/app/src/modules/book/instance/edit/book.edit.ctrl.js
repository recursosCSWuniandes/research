(function (ng) {

    var mod = ng.module("bookModule");

    mod.controller("bookEditCtrl", ["$scope", "$state", "book", "model",
        function ($scope, $state, book, model) {
            $scope.currentRecord = book;
            $scope.fields = model.fields;
            this.saveRecord = function (record) {
                record.put().then(function () {
                    $state.go('book.list', null, {reload: true});
                });
            };
            var self = this;
            $scope.actions.save = {
                displayName: 'Save',
                icon: 'save',
                fn: function () {
                    self.saveRecord($scope.currentRecord);
                },
                show: function () {
                    return true;
                }
            };
        }]);
})(window.angular);
