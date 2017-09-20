
module.controller("CompanyMainCtrl", CompanyMainCtrlCtor)

function CompanyMainCtrlCtor(CompanyService) {
	
	this.allCoupons=[]
	
	var self=this
	
	this.getAllCoupons = function() {
		CompanyService.getAllCoupons().then(
				function(resp) {
					self.allCoupons=resp.data
					for (var i = 0; i < self.allCoupons.length; i++) {
						self.allCoupons[i].startDateShort = self.allCoupons[i].startDate.substring(0,10)
						self.allCoupons[i].endDateShort = self.allCoupons[i].endDate.substring(0,10)
					}
					self.isError = false
					alert('Coupons list refreshed')
				},
				function(err) {
					self.isError = true
					self.errMessage = err.data
					alert('Failed to get all coupons list')
				}
				)
	}
	
	this.updateCoupon = function(coupId) {
		// find the coupon in the array according to id
		var index;
		for (var i=0; i < this.allCoupons.length; i++) {
			if (this.allCoupons[i].id == coupId) {
				index=i
			}
		}
		
		if (this.allCoupons[index].edit) { // pressed Update in edit mode
			this.allCoupons[index].endDate = this.allCoupons[index].endDateShort + 'T12:00:00.0'
			CompanyService.updateCoupon(this.allCoupons[index]).then(
					function(resp) {
						self.isError = false
						alert('Coupon updated')
						self.allCoupons[index].edit=false // exit edit mode
															// only if update
															// was successful
					},
					function(err) {
						self.isError = true
						self.errMessage = err.data
						alert('Coupon NOT updated')
					}
					)
		} else {
			this.allCoupons[index].edit=true
		}
		
	}
	
	this.deleteCoupon = function(coupId) {
		
		CompanyService.deleteCoupon(coupId).then(
				function(resp) {
					self.isError = false
					alert('Coupon deleted')
					
					// find the coupon in the array according to id
					var index;
					for (var i=0; i < self.allCoupons.length; i++) {
						if (self.allCoupons[i].id == coupId) {
							index=i
						}
					}
					self.allCoupons.splice(index,1)
				},
				function(err) {
					self.isError = true
					self.errMessage = err.data
					alert('Coupon NOT deleted')
				}
				)
	}
	
	
}