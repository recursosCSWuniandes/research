(function (ng) {

    var mod = ng.module("bookModule");

    mod.controller("bookInstanceCtrl", ['$scope', 'references', function ($scope, references) {
            $scope.editorials = references.editorials;
        }]);

})(window.angular);
