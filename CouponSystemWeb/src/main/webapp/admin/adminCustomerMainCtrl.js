
module.controller("AdminCustomerMainCtrl", AdminCustomerMainCtrlCtor)

function AdminCustomerMainCtrlCtor(AdminService) {
	
	this.allCustomers=[]
	var self = this
	
	this.getAllCustomers=function() {
		AdminService.getAllCustomers().then(
		function(resp) {
			self.allCustomers=resp.data
			self.isError = false
			alert('Customers list refreshed')
		},
		function(err) {
			self.isError = true
			self.errMessage = err.data
			alert('Failed to get all customers list')
		}
		)
		
	}
	
	this.updateCustomer=function(custId) {
		// find the customer in the array according to id
		var index;
		for (var i=0; i < this.allCustomers.length; i++) {
			if (this.allCustomers[i].id == custId) {
				index=i
			}
		}
		
		
		if (this.allCustomers[index].edit) { // pressed Update in edit mode
			AdminService.updateCustomer(this.allCustomers[index]).then(
					function(resp) {
						self.isError = false
						alert('Customer updated')
						self.allCustomers[index].edit=false //exit edit mode only if update was successful
					},
					function(err) {
						self.isError = true
						self.errMessage = err.data
						alert('Customer NOT updated')
					}
					)
		} else {
			this.allCustomers[index].edit=true
		}
	}
	
	this.deleteCustomer=function(custId) {
		AdminService.deleteCustomer(custId).then(
				function(resp) {
					self.isError = false
					alert('Customer deleted')
					
					// find the customer in the array according to id
					var index;
					for (var i=0; i < self.allCustomers.length; i++) {
						if (self.allCustomers[i].id == custId) {
							index=i
						}
					}
					self.allCustomers.splice(index,1)
				},
				function(err) {
					self.isError = true
					self.errMessage = err.data
					alert('Customer NOT deleted')
				}
				)
	}
	
}