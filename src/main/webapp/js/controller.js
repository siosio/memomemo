var memo_app = angular.module('memo', []);

memo_app
  .controller('MemoController', ['$scope', '$http', function($scope, $http) {
    $scope.list = list($scope, $http);

    var $e = angular.element;

    $scope.addPage = function() {
      $scope.memo = {};
      console.log('hogefuga');
      $e('#description').parent().addClass('is-invalid');
      $e('#title').parent().addClass('is-invalid');
    };

    $scope.create = function() {
      $http({
        method: 'post',
        url: 'app/memo',
        data: JSON.stringify($scope.memo),
        contentType: 'application/json'
      }).success(function(data, status, headers, config) {
        $scope.memo = {};
        $e('header a.is-active').removeClass('is-active');
        $e('#index-link').addClass('is-active');
        $e('main > div.is-active').removeClass('is-active');
        $e('#index').addClass('is-active');
        $scope.list();
      });
    };

    $scope.delete = function(memoId) {
      console.log(memoId);
      $http({
        method: 'delete',
        url: 'app/memo/' + memoId
      }).success(function(data, status, headers, config) {
        $scope.list();
      });
    };

    $scope.edit = function(memoId) {
      $e('main > div.is-active').removeClass('is-active');
      $e('#edit').addClass('is-active');
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
