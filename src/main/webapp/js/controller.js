var memo_app = angular.module('memo', []);

memo_app
  .controller('MemoController', ['$scope', '$http', function($scope, $http) {
    $scope.list = list;
  }])
  .controller('ListController', ['$scope', '$http', function ($scope, $http) {
    $scope.init = list;
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

var list = function() {
  $http({
    method: 'get',
    url: 'app/memo'
  }).success(function(data, status, headers, config) {
    console.log(data);
    $scope.memoList = data;
  });
};
