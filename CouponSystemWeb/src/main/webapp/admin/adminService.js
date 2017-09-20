
module.service("AdminService", AdminServiceCtor)

function AdminServiceCtor($http) {
	
	this.createCompany = function(newCompany) {
		return $http.post('http://localhost:8080/CouponSystemWeb/webapi/admin/createCompany/', newCompany)
	}
	
	this.getAllCompanies = function() {
		return $http.get('http://localhost:8080/CouponSystemWeb/webapi/admin/getAllCompanies')
	}
	
	this.updateCompany = function(companyToUpdate) {
		return $http.put('http://localhost:8080/CouponSystemWeb/webapi/admin/updateCompany/', companyToUpdate)
	}
	
	this.deleteCompany = function(compId) {
		return $http.delete('http://localhost:8080/CouponSystemWeb/webapi/admin/deleteCompany/' + compId)
	}
	
	this.createCustomer = function(newCustomer) {
		return $http.post('http://localhost:8080/CouponSystemWeb/webapi/admin/createCustomer/', newCustomer)
	}
	
	this.getAllCustomers = function() {
		return $http.get('http://localhost:8080/CouponSystemWeb/webapi/admin/getAllCustomers')
	}
	
	this.updateCustomer = function(customerToUpdate) {
		return $http.put('http://localhost:8080/CouponSystemWeb/webapi/admin/updateCustomer/', customerToUpdate)
	}
	
	this.deleteCustomer = function(custId) {
		return $http.delete('http://localhost:8080/CouponSystemWeb/webapi/admin/deleteCustomer/' + custId)
	}
}