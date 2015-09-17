var memo_app = angular.module('memo', ['ngRoute', 'ngResource']);

memo_app
    .config(function ($routeProvider) {
      $routeProvider
          .when('/', {
            templateUrl : 'templates/top.html',
            controller : 'MemoController',
            controllerAs: 'app'
          })
          .when('/:memoId/page', {
            templateUrl : 'templates/page.html',
            controller : 'PageController',
            controllerAs: 'app'
          })
          .when('/:memoId/page/new', {
            templateUrl : 'templates/page_new.html',
            controller : 'PageController',
            controllerAs: 'app'
          })
          .otherwise({
            redirectTo: '/'
          })
    })
    .controller('MemoController', function ($scope, $http, $location) {

      var vm = this;

      // list memo
      vm.list = function () {
        $http({
          method: 'get',
          url   : 'app/memo'
        }).success(function (data, status, headers, config) {
          vm.memos = data;
        });
        componentHandler.upgradeAllRegistered();
      };

      // create memo
      vm.create = function () {
        $http({
          method     : 'post',
          url   : 'app/memo',
          data  : JSON.stringify(vm.memo),
          contentType: 'application/json'
        }).success(function (data, status, headers, config) {
          vm.memo = {};
          vm.list();
        });
      };

      // delete memo
      vm.delete = function (memoId) {
        var remove = function () {
          $http({
            method: 'delete',
            url   : 'app/memo/' + memoId
          }).success(function (data, status, headers, config) {
            vm.list();
          });
        };
        var $remove = $('#memo_' + memoId);
        $remove.addClass('remove');
        $remove.get(0).addEventListener('transitionend', function () {
          remove();
        });
      };

      vm.listPage = function (memoId) {
        $location.path('/' + memoId + '/page')
      };

      vm.list();
    })
    .controller('PageController', function ($scope, $http, $routeParams, $location) {
      var vm = this;

      vm.list = function () {
        $http({
          method: 'get',
          url   : 'app/page/list/' + memoId
        }).success(function (data, status, headers, config) {
          vm.pages = data;
        });
      };

      vm.new = function () {
        $location.path('/' + vm.memoId + '/page/new');
      };

      vm.new_page = function () {
        var $preview = $('#preview');
        console.log($preview);
        $('#page_text').keyup(function () {
          var src = $(this).val();
          var html = marked(src);
          $preview.html(html);
        });
      };

      vm.create = function () {
        vm.page.memoId = vm.memoId;
        $http({
          method     : 'post',
          url   : 'app/page',
          data  : JSON.stringify(vm.page),
          contentType: 'application/json'
        }).success(function (data, status, headers, config) {
          $location.path('/' + vm.memoId + '/page')
        });
        dialog.close();
      };

      vm.memoId = $routeParams.memoId;
      if (!$location.path().indexOf('/new')) {
        vm.list();
      } else {
        vm.new_page();
      }
      componentHandler.upgradeAllRegistered();
    });
//memo_app
//  .controller('MemoController', ['$scope', '$http', '$location', function($scope, $http, $location) {
//
//    var $title = $('#title');
//    var $description = $('#description');
//
//
//
//    // find all page
//    $scope.listPage = function (memoId) {
//      $('.is-active').removeClass('is-active');
//      $('#page-list').addClass('is-active');
//    };
//
//
//
//    $scope.closeDialog = function (id) {
//      var dialog = document.getElementById(id);
//      dialog.close();
//      $scope.memoId = null;
//    };
//
//
//    $scope.showPage = function (page) {
//      var dialog = document.getElementById("show_page");
//      var html = marked(page.text);
//      console.log(page);
//      $('#show_page_text').html(html);
//      dialog.showModal();
//    }
//  }]);
//
//

