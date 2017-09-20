
module.controller("CompanyCreateCtrl", CompanyCreateCtrlCtor)

function CompanyCreateCtrlCtor(CompanyService) {
	
	this.newCoupon={}
	this.newCoupon.type='RESTAURANTS'
	this.newCoupon.image=''
		
	var self=this
	
	this.createCoupon=function() {
		this.newCoupon.startDate = this.newCoupon.startDateShort + 'T12:00:00.0'
		this.newCoupon.endDate = this.newCoupon.endDateShort + 'T12:00:00.0'
		CompanyService.createCoupon(this.newCoupon).then(
				function(resp) {
					self.isError = false
					alert('Coupon created')
				},
				function(err) {
					self.isError = true
					self.errMessage = err.data
					alert('Coupon NOT created')
				}
				)
	}
	
}