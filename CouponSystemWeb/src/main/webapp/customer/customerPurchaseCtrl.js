
module.controller("CustomerPurchaseCtrl", CustomerPurchaseCtrlCtor)

function CustomerPurchaseCtrlCtor(CustomerService) {
	
	this.allAvailableCoupons=[]
	
	var self = this
	
	this.getAllAvailableCoupons = function() {
		CustomerService.getAllAvailableCoupons().then(
				function(resp) {
					self.allAvailableCoupons=resp.data
					for (var i = 0; i < self.allAvailableCoupons.length; i++) {
						self.allAvailableCoupons[i].startDateShort = self.allAvailableCoupons[i].startDate.substring(0,10)
						self.allAvailableCoupons[i].endDateShort = self.allAvailableCoupons[i].endDate.substring(0,10)
					}
					self.isError = false
					alert('Available coupons list refreshed')
				},
				function(err) {
					self.isError = true
					self.errMessage = err.data
					alert('Failed to get available coupons list')
				}
				)
	}
	
	this.purchaseCoupon = function(coupId) {
		
		CustomerService.purchaseCoupon(coupId).then(
				function(resp) {
					self.isError = false
					alert('Coupon purchased')
					
					// find the coupon in the array according to id
					var index;
					for (var i=0; i < self.allAvailableCoupons.length; i++) {
						if (self.allAvailableCoupons[i].id == coupId) {
							index=i
						}
					}
					self.allAvailableCoupons.splice(index,1)
				},
				function(err) {
					self.isError = true
					self.errMessage = err.data
					alert('Coupon NOT purchased')
				}
				)
	}
}