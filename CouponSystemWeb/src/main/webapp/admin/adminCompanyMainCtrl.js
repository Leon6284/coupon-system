
module.controller("AdminCompanyMainCtrl", AdminCompanyMainCtrlCtor)

function AdminCompanyMainCtrlCtor(AdminService) {
	
	this.allCompanies=[]
	var self=this
	
	
	this.getAllCompanies=function() {
		AdminService.getAllCompanies().then(
		function(resp) {
			self.allCompanies=resp.data
			self.isError = false
			alert('Companies list refreshed')
		},
		function(err) {
			self.isError = true
			self.errMessage = err.data
			alert('Failed to get all companies list')
		}
		)
		
	}
	
	
	this.updateCompany=function(compId) {
		// find the company in the array according to id
		var index;
		for (var i=0; i < this.allCompanies.length; i++) {
			if (this.allCompanies[i].id == compId) {
				index=i
			}
		}
		
		
		if (this.allCompanies[index].edit) { // pressed Update in edit mode
			AdminService.updateCompany(this.allCompanies[index]).then(
					function(resp) {
						self.isError = false
						alert('Company updated')
						self.allCompanies[index].edit=false //exit edit mode only if update was successful
					},
					function(err) {
						self.isError = true
						self.errMessage = err.data
						alert('Company NOT updated')
					}
					)
		} else {
			this.allCompanies[index].edit=true
		}
		
	}
	
	this.deleteCompany = function(compId) {
		
		AdminService.deleteCompany(compId).then(
				function(resp) {
					self.isError = false
					alert('Company deleted')
					
					// find the company in the array according to id
					var index;
					for (var i=0; i < self.allCompanies.length; i++) {
						if (self.allCompanies[i].id == compId) {
							index=i
						}
					}
					self.allCompanies.splice(index,1)
				},
				function(err) {
					self.isError = true
					self.errMessage = err.data
					alert('Company NOT deleted')
				}
				)
	}
	
}

