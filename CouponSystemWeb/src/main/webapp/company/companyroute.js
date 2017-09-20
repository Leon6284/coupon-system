
module.config(['$locationProvider', function($locationProvider) {
	  $locationProvider.hashPrefix('');
	}]);

module.config(function($stateProvider, $urlRouterProvider) {
	
    $stateProvider
    .state("main", {
        url : "/main",
    	templateUrl : "companymain.htm",
        controller : "CompanyMainCtrl as ctrl"
    })
    .state("create", {
        url : "/create",
    	templateUrl : "companycreate.htm",
        controller : "CompanyCreateCtrl as ctrl"
    })
    .state("filter", {
        url : "/filter",
    	templateUrl : "companyfilter.htm",
        controller : "CompanyFilterCtrl as ctrl"
    })
    .state("logout", {
    	 url : "/logout",
    	 templateUrl : "../logout.htm",
    	 controller : "LogoutCtrl as ctrl"
    });
    $urlRouterProvider.when("", "/main");
    $urlRouterProvider.otherwise('/main');
});