
module.service("CustomerService", CustomerServiceCtor)

function CustomerServiceCtor($http) {
	
	this.getAllPurchasedCoupons = function() {
		return $http.get('http://localhost:8080/CouponSystemWeb/webapi/customer/getAllPurchasedCoupons')
	}
	
	this.getAllAvailableCoupons = function() {
		return $http.get('http://localhost:8080/CouponSystemWeb/webapi/customer/getAllAvailableCoupons')
	}
	
	this.purchaseCoupon = function(coupId) {
		return $http.post('http://localhost:8080/CouponSystemWeb/webapi/customer/purchaseCoupon/' + coupId)
	}
	
	this.applyFilterType = function(filterType) {
		return $http.get('http://localhost:8080/CouponSystemWeb/webapi/customer/getAllPurchasedCouponsByType/' + filterType)
	}
	
	this.applyFilterPrice = function(filterPrice) {
		return $http.get('http://localhost:8080/CouponSystemWeb/webapi/customer/getAllPurchasedCouponsByPrice/' + filterPrice)
	}
}