package dao;

import java.util.Collection;

import beans.Company;
import beans.Coupon;
import exceptions.CouponSystemException;

public interface CompanyDAO {
	
	public void createCompany(Company company) throws CouponSystemException;
	public Company readCompany(Company company) throws CouponSystemException;
	public void updateCompany(Company company) throws CouponSystemException;
	public void deleteCompany(Company company) throws CouponSystemException;
	
	public Collection<Company> getAllCompanies() throws CouponSystemException;
	public Collection<Coupon> getCoupons(Company company) throws CouponSystemException;
	
	boolean login(String compName, String password) throws CouponSystemException;
}
