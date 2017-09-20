
module.controller("AdminCustomerCreateCtrl", AdminCustomerCreateCtrlCtor)

function AdminCustomerCreateCtrlCtor(AdminService) {
	
	this.newCustomer={}
	
	var self = this
	
	this.createCustomer=function() {
		AdminService.createCustomer(this.newCustomer).then(
				function(resp) {
					alert('Customer created')
					self.isError = false
				},
				function(err) {
					self.isError = true
					self.errMessage = err.data
					alert('Customer NOT created')
				}
				)
	}
	
}