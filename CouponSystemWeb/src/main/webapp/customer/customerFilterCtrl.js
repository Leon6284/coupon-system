
module.controller("CustomerFilterCtrl", CustomerFilterCtrlCtor)

function CustomerFilterCtrlCtor(CustomerService) {
	
	this.allPurchasedCouponsFiltered=[]
	
	this.optionFilter='type'
	this.filterType='RESTAURANTS'
		
	var self = this
	
	this.applyFilter = function() {
		if (this.optionFilter == 'type') {
				this.applyFilterType()
		} else if (this.optionFilter == 'price') {
			if (this.filterPrice == null) {
				alert('Please enter price')
			} else {
				this.applyFilterPrice()
			}
		}
	}
	
	this.applyFilterType = function() {
		CustomerService.applyFilterType(this.filterType).then(
				function(resp) {
					self.allPurchasedCouponsFiltered=resp.data
					for (var i = 0; i < self.allPurchasedCouponsFiltered.length; i++) {
						self.allPurchasedCouponsFiltered[i].startDateShort = self.allPurchasedCouponsFiltered[i].startDate.substring(0,10)
						self.allPurchasedCouponsFiltered[i].endDateShort = self.allPurchasedCouponsFiltered[i].endDate.substring(0,10)
					}
					self.isError = false
					alert('Coupons list refreshed')
				},
				function(err) {
					self.isError = true
					self.errMessage = err.data
					alert('Failed to get purchased coupons list')
				}
				)
	}
	
	this.applyFilterPrice = function() {
		CustomerService.applyFilterPrice(this.filterPrice).then(
				function(resp) {
					self.allPurchasedCouponsFiltered=resp.data
					for (var i = 0; i < self.allPurchasedCouponsFiltered.length; i++) {
						self.allPurchasedCouponsFiltered[i].startDateShort = self.allPurchasedCouponsFiltered[i].startDate.substring(0,10)
						self.allPurchasedCouponsFiltered[i].endDateShort = self.allPurchasedCouponsFiltered[i].endDate.substring(0,10)
					}
					self.isError = false
					alert('Coupons list refreshed')
				},
				function(err) {
					self.isError = true
					self.errMessage = err.data
					alert('Failed to get purchased coupons list')
				}
				)
	}
}