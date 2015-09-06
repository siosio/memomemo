var memo_app = angular.module('memo', []);

memo_app
  .controller('MemoController', ['$scope', '$http', function($scope, $http) {
    $scope.list = list($scope, $http);
  }])
  .controller('ListController', ['$scope', '$http', function ($scope, $http) {
    $scope.init = list($scope, $http);
  }])
  .controller('AddMemoController', ['$scope', '$http', function ($scope, $http) {
    $scope.create = function() {
      var data = {
        title: $scope.title,
        detail: $scope.detail
      };
      console.log(data);
      $http({
        method: 'post',
        url: 'app/memo',
        data: JSON.stringify(data),
        contentType: 'application/json',
      });
    };
  }]);

var list = function($scope, $http) {
  return function() {
    $http({
      method: 'get',
      url: 'app/memo'
    }).success(function(data, status, headers, config) {
      console.log(data);
      $scope.memoList = data;
    });
  };
};
