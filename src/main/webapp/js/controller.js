var memo_app = angular.module('memo', []);

memo_app
  .controller('MemoController', ['$scope', '$http', function($scope, $http) {
    $scope.list = list($scope, $http);

    $scope.addPage = function() {
      $scope.memo = {};
      console.log('hogefuga');
      angular.element('#detail').parent().addClass('is-invalid');
      angular.element('#title').parent().addClass('is-invalid');
    };

    $scope.create = function() {
      $http({
        method: 'post',
        url: 'app/memo',
        data: JSON.stringify($scope.memo),
        contentType: 'application/json'
      }).success(function(data, status, headers, config) {
        $scope.memo = {};
        angular.element('header a.is-active').removeClass('is-active');
        angular.element('#index-link').addClass('is-active');
        angular.element('main > div.is-active').removeClass('is-active');
        angular.element('#index').addClass('is-active');
        $scope.list();
      });
    };

    $scope.edit = function() {
      alert('edit');
    }
  }]);


var list = function($scope, $http) {
  return function() {
    $http({
      method: 'get',
      url: 'app/memo'
    }).success(function(data, status, headers, config) {
      $scope.memoList = data;
    });
  };
};
