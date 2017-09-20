
module.config(['$locationProvider', function($locationProvider) {
	  $locationProvider.hashPrefix('');
	}]);

module.config(function($stateProvider, $urlRouterProvider) {
	
    $stateProvider
    .state("all", {
        url : "/all",
    	templateUrl : "customerall.htm",
        controller : "CustomerAllCtrl as ctrl"
    })
    .state("purchase", {
        url : "/purchase",
    	templateUrl : "customerpurchase.htm",
        controller : "CustomerPurchaseCtrl as ctrl"
    })
    .state("filter", {
        url : "/filter",
    	templateUrl : "customerfilter.htm",
        controller : "CustomerFilterCtrl as ctrl"
    })
    .state("logout", {
    	 url : "/logout",
    	 templateUrl : "../logout.htm",
    	 controller : "LogoutCtrl as ctrl"
    });
    $urlRouterProvider.when("", "/all");
    $urlRouterProvider.otherwise('/all');
});