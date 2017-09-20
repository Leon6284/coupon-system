package dao;

import java.util.Collection;

import beans.Customer;
import exceptions.CouponSystemException;
import beans.Coupon;

public interface CustomerDAO {
	
	public void createCustomer(Customer customer) throws CouponSystemException;
	public Customer readCustomer(Customer customer) throws CouponSystemException;
	public void updateCustomer(Customer customer) throws CouponSystemException;
	public void deleteCustomer(Customer customer) throws CouponSystemException;
	
	public void purchaseCoupon(Coupon coupon, Customer customer) throws CouponSystemException;
	
	public Collection<Customer> getAllCustomers() throws CouponSystemException;
	public Collection<Coupon> getCoupons(Customer customer) throws CouponSystemException;
	
	boolean login(String compName, String password) throws CouponSystemException;
}
