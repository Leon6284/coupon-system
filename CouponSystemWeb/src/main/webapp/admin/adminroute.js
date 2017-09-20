
module.config(['$locationProvider', function($locationProvider) {
	  $locationProvider.hashPrefix('');
	}]);

module.config(function($stateProvider, $urlRouterProvider) {
	
    $stateProvider
    .state("companyMain", {
        url : "/companymain",
    	templateUrl : "admincompanymain.htm",
        controller : "AdminCompanyMainCtrl as ctrl"
    })
    .state("companyCreate", {
    	 url : "/companycreate",
    	 templateUrl : "admincompanycreate.htm",
    	 controller : "AdminCompanyCreateCtrl as ctrl"
    })
    .state("customerMain", {
        url : "/customermain",
    	templateUrl : "admincustomermain.htm",
        controller : "AdminCustomerMainCtrl as ctrl"
    })
    .state("customerCreate", {
    	 url : "/customercreate",
    	 templateUrl : "admincustomercreate.htm",
    	 controller : "AdminCustomerCreateCtrl as ctrl"
    })
    .state("logout", {
    	 url : "/logout",
    	 templateUrl : "../logout.htm",
    	 controller : "LogoutCtrl as ctrl"
    });
    $urlRouterProvider.when("", "/companymain");
    $urlRouterProvider.otherwise('/companymain');
});