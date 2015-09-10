var memo_app = angular.module('memo', []);

memo_app
  .controller('MemoController', ['$scope', '$http', '$location', function($scope, $http, $location) {
    
    var $title = $('#title');
    var $description = $('#description');

    // find memo list
    $scope.list = function () {
      $('.is-active').removeClass('is-active');
      $('#index').addClass('is-active');
      $location.url('');
      $http({
        method: 'get', url: 'app/memo'
      }).success(function (data, status, headers, config) {
        $scope.memos = data;
      });
    };

    // create memo
    $scope.create = function () {
      $http({
        method: 'post', url: 'app/memo', data: JSON.stringify($scope.memo), contentType: 'application/json'
      }).success(function (data, status, headers, config) {
        $scope.memo = {};
        $scope.list();
        $title.parent().addClass('is-invalid');
        $description.parent().addClass('is-invalid');
      });
    };

    // delete memo
    $scope.delete = function (memoId) {
      $http({
        method: 'delete', url: 'app/memo/' + memoId
      }).success(function (data, status, headers, config) {
        $scope.list();
      });
    };

    // find all page
    $scope.listPage = function (memoId) {
      $('.is-active').removeClass('is-active');
      $('#page-list').addClass('is-active');
      $scope.memoId = memoId;
      $location.url('list');
      $scope.findAllPage(memoId);
    };

    $scope.findAllPage = function (memoId) {
      $http({
        method: 'get', url: 'app/page/list/' + memoId
      }).success(function (data, status, headers, config) {
        $scope.pages = data;
      });
    };

    $scope.newPage = function () {
      var dialog = document.getElementById('page');
      $scope.page = {};
      dialog.showModal();
    };

    $scope.closeDialog = function (id) {
      var dialog = document.getElementById(id);
      dialog.close();
      $scope.memoId = null;
    };

    $scope.createPage = function () {
      var dialog = document.getElementById('page');
      $scope.page.memoId = $scope.memoId;
      $http({
        method: 'post',
        url: 'app/page',
        data: JSON.stringify($scope.page),
        contentType: 'application/json'
      }).success(function (data, status, headers, config) {
        $scope.listPage($scope.page.memoId)
      });
      dialog.close();
    };

    $scope.showPage = function (page) {
      var dialog = document.getElementById("show_page");
      var html = marked(page.text);
      console.log(page);
      $('#show_page_text').html(html);
      dialog.showModal();
    }
  }]);


$(function () {
  $('#page_text').keyup(function () {
    var src = $(this).val();
    var html = marked(src);
    $('#preview').html(html);
  });
});
