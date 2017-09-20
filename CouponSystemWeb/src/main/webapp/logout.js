
module.controller("LogoutCtrl", LogoutCtrlCtor)

function LogoutCtrlCtor() {
	
	this.logout = function() {
		
		window.location = 'http://localhost:8080/CouponSystemWeb/logout'
	}
}