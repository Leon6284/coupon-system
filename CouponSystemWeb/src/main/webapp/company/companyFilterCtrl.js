
module.controller("CompanyFilterCtrl", CompanyFilterCtrlCtor)

function CompanyFilterCtrlCtor(CompanyService) {
	
	this.allCouponsFiltered=[]
	
	this.filterType='RESTAURANTS'
	this.filterDate={}
	this.optionFilter='type'
		
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
		} else if (this.optionFilter == 'date') {
				this.applyFilterDate() 
		}
	}
		
	this.applyFilterType = function() {
		CompanyService.applyFilterType(this.filterType).then(
				function(resp) {
					self.allCouponsFiltered=resp.data
					for (var i = 0; i < self.allCouponsFiltered.length; i++) {
						self.allCouponsFiltered[i].startDateShort = self.allCouponsFiltered[i].startDate.substring(0,10)
						self.allCouponsFiltered[i].endDateShort = self.allCouponsFiltered[i].endDate.substring(0,10)
					}
					self.isError = false
					alert('Coupons list refreshed')
				},
				function(err) {
					self.isError = true
					self.errMessage = err.data
					alert('Failed to get coupons list')
				}
				)
	}
	
	this.applyFilterPrice = function() {
		CompanyService.applyFilterPrice(this.filterPrice).then(
				function(resp) {
					self.allCouponsFiltered=resp.data
					for (var i = 0; i < self.allCouponsFiltered.length; i++) {
						self.allCouponsFiltered[i].startDateShort = self.allCouponsFiltered[i].startDate.substring(0,10)
						self.allCouponsFiltered[i].endDateShort = self.allCouponsFiltered[i].endDate.substring(0,10)
					}
					self.isError = false
					alert('Coupons list refreshed')
				},
				function(err) {
					self.isError = true
					self.errMessage = err.data
					alert('Failed to get coupons list')
				}
				)
	}
	
	this.applyFilterDate = function() {
		this.filterDate.date = this.filterDate.dateShort + 'T12:00:00.0'
		CompanyService.applyFilterDate(this.filterDate).then(
				function(resp) {
					self.allCouponsFiltered=resp.data
					for (var i = 0; i < self.allCouponsFiltered.length; i++) {
						self.allCouponsFiltered[i].startDateShort = self.allCouponsFiltered[i].startDate.substring(0,10)
						self.allCouponsFiltered[i].endDateShort = self.allCouponsFiltered[i].endDate.substring(0,10)
					}
					self.isError = false
					alert('Coupons list refreshed')
				},
				function(err) {
					self.isError = true
					self.errMessage = err.data
					alert('Failed to get coupons list')
				}
				)
	}
}