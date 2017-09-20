
module.controller("AdminCompanyCreateCtrl", AdminCompanyCreateCtrlCtor)

function AdminCompanyCreateCtrlCtor(AdminService) {
	
	this.newCompany={}
	
	var self=this
	
	this.createCompany=function() {
		AdminService.createCompany(this.newCompany).then(
				function(resp) {
					self.isError = false
					alert('Company created')
				},
				function(err) {
					self.isError = true
					self.errMessage = err.data
					alert('Company NOT created')
				}
				)
	}
	
}