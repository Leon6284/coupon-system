package webbeans;

import java.io.Serializable;
import java.util.Collection;

import javax.xml.bind.annotation.XmlRootElement;

import beans.Coupon;
import beans.Customer;

@XmlRootElement
public class WebCustomer implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private long id;
	private String custName;
	private String password;
	private Collection<Coupon> coupons;
	
	public WebCustomer() {
		
	}
	
	public WebCustomer(Customer customer) {
		this(customer.getId(), customer.getCustName(), customer.getPassword());
	}

	public WebCustomer(long id, String custName, String password) {
		super();
		this.id = id;
		this.custName = custName;
		this.password = password;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Collection<Coupon> getCoupons() {
		return coupons;
	}

	public void setCoupons(Collection<Coupon> coupons) {
		this.coupons = coupons;
	}

	@Override
	public String toString() {
		return "WebCustomer [id=" + id + ", custName=" + custName + ", password=" + password + ", coupons=" + coupons
				+ "]";
	}
	
	public Customer convertToCustomer() {
		return new Customer(id, custName, password);
	}
	
}
