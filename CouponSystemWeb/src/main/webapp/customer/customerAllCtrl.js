
module.controller("CustomerAllCtrl", CustomerAllCtrlCtor)

function CustomerAllCtrlCtor(CustomerService) {
	
	this.allPurchasedCoupons=[]
	
	var self = this
	
	this.getAllPurchasedCoupons = function() {
		CustomerService.getAllPurchasedCoupons().then(
				function(resp) {
					self.allPurchasedCoupons=resp.data
					for (var i = 0; i < self.allPurchasedCoupons.length; i++) {
						self.allPurchasedCoupons[i].startDateShort = self.allPurchasedCoupons[i].startDate.substring(0,10)
						self.allPurchasedCoupons[i].endDateShort = self.allPurchasedCoupons[i].endDate.substring(0,10)
					}
					self.isError = false
					alert('Purchased coupons list refreshed')
				},
				function(err) {
					self.isError = true
					self.errMessage = err.data
					alert('Failed to get purchased coupons list')
				}
				)
	}
	
}