
module.service("CompanyService", CompanyServiceCtor)

function CompanyServiceCtor($http) {
	
	this.createCoupon = function(newCoupon) {
		return $http.post('http://localhost:8080/CouponSystemWeb/webapi/company/createCoupon', newCoupon)
	}
	
	this.getAllCoupons = function() {
		return $http.get('http://localhost:8080/CouponSystemWeb/webapi/company/getAllCoupons')
	}
	
	this.updateCoupon = function(couponToUpdate) {
		return $http.put('http://localhost:8080/CouponSystemWeb/webapi/company/updateCoupon/', couponToUpdate)
	} 
	
	this.deleteCoupon = function(coupId) {
		return $http.delete('http://localhost:8080/CouponSystemWeb/webapi/company/deleteCoupon/' + coupId)
	}
	
	this.applyFilterType = function(filterType) {
		return $http.get('http://localhost:8080/CouponSystemWeb/webapi/company/getCouponsByType/' + filterType)
	}
	
	this.applyFilterPrice = function(filterPrice) {
		return $http.get('http://localhost:8080/CouponSystemWeb/webapi/company/getCouponsBelowPrice/' + filterPrice)
	}
	
	this.applyFilterDate = function(filterDate) {
		return $http.post('http://localhost:8080/CouponSystemWeb/webapi/company/getCouponsBeforeDate/', filterDate)
	}
}