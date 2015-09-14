angular.module('crm', ['ngRoute'])
.config(function($routeProvider, $httpProvider) {

	$routeProvider.when('/', {
		templateUrl : 'home.html'
	}).when('/addClient', {
		templateUrl : 'AddClientDetails.html',
		controller : 'addClient'
	}).when('/getClient', {
		templateUrl : 'ClientDetails.html',
		controller : 'getClient'
	}).when('/clients', {
		templateUrl : 'ClientList.html',
		controller : 'clients'
	}).otherwise('/');

    $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';

  })
.controller('addClient', function($scope, $http) {
	$scope.submit = function(){
		$http.post('clients/' ,{}, {params:{firstName:$scope.newClient.firstName, lastName:$scope.newClient.lastName}}).success(function(data) {
			$scope.newClient.id = data;
		})
	}
})
.controller('getClient', function($scope, $http) {
	$scope.submit = function(){
		$http.get('clients/' + $scope.id).success(function(data) {
			$scope.client = data;
		})
	}
})
.controller('clients', function($scope, $http) {
	$http.get('clients').success(function(data) {
		$scope.clients = data;
	})
});