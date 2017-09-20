package webbeans;

import java.io.Serializable;
import java.util.Collection;

import javax.xml.bind.annotation.XmlRootElement;

import beans.Company;

@XmlRootElement
public class WebCompany implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private long id;
	private String compName;
	private String password;
	private String email;
	private Collection<WebCoupon> coupons;
	
	public WebCompany() {
		
	}
	
	public WebCompany(Company company) {
		this(company.getId(), company.getCompName(), company.getPassword(), company.getEmail());
	}

	public WebCompany(long id, String compName, String password, String email) {
		super();
		this.id = id;
		this.compName = compName;
		this.password = password;
		this.email = email;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCompName() {
		return compName;
	}

	public void setCompName(String compName) {
		this.compName = compName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Collection<WebCoupon> getCoupons() {
		return coupons;
	}

	public void setCoupons(Collection<WebCoupon> coupons) {
		this.coupons = coupons;
	}

	@Override
	public String toString() {
		return "WebCompany [id=" + id + ", compName=" + compName + ", password=" + password + ", email=" + email
				+ ", coupons=" + coupons + "]";
	}
	
	public Company convertToCompany() {
		return new Company(id, compName, password, email);
	}
	
	
}
