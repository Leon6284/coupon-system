
module.controller("FooterCtrl", FooterCtrlCtor)

function FooterCtrlCtor($scope, $http) {
	
	$scope.sayNameAdmin = function() {
		alert('Administrators do not have names...')
	}
	
	$scope.sayNameCompany = function() {
		
		$http.get('http://localhost:8080/CouponSystemWeb/webapi/company/getCompanyName').then(
			function(resp) {
				alert('Company name is: ' + resp.data)
			},
			function(err) {
				alert('Not logged in as company')
			}
			)
	}
	
	$scope.sayNameCustomer = function() {
		$http.get('http://localhost:8080/CouponSystemWeb/webapi/customer/getCustomerName').then(
				function(resp) {
					alert('Customer name is: ' + resp.data)
				},
				function(err) {
					alert('Not logged in as customer')
				}
				)
	}
	
}