package webservices;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import beans.Coupon;
import beans.CouponType;
import core.CouponSystem;
import exceptions.CouponSystemDatabaseConnectionException;
import exceptions.CouponSystemException;
import facades.AdminFacade;
import facades.ClientType;
import facades.CompanyFacade;
import webbeans.WebCompany;
import webbeans.WebCoupon;
import webbeans.WebDate;

@Path("company")
public class WebServiceCompany {
	
	@Context private HttpServletRequest request;

	@POST
	@Path("createCoupon")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createCoupon(WebCoupon webCoupon) {
		
		CompanyFacade facade = getFacade();
		if (facade == null) {
			return Response.ok("You are not logged as company").status(500).build();
		}
		
		if (webCoupon == null) {
			return Response.ok("Null object received, probably one of the fields is incorrect").status(500).build();
		}
		
		if (webCoupon.getId() == 0) {
			return Response.ok("ID is incorrect").status(500).build();
		} else if (webCoupon.getTitle() == null || webCoupon.getTitle().equals("")) {
			return Response.ok("Title is incorrect").status(500).build();
		} else if (webCoupon.getStartDate() == null) {
			return Response.ok("Start date is incorrect. Date should be in format YYYY-MM-DD").status(500).build();
		} else if (webCoupon.getEndDate() == null) {
			return Response.ok("End date is incorrect. Date should be in format YYYY-MM-DD").status(500).build();
		} else if (webCoupon.getAmount() <= 0) {
			return Response.ok("Amount is incorrect").status(500).build();
		} else if (webCoupon.getType() == null) {
			return Response.ok("Coupon type is incorrect").status(500).build();
		} else if (webCoupon.getMessage() == null || webCoupon.getMessage().equals("")) {
			return Response.ok("Message is incorrect").status(500).build();
		} else if (webCoupon.getPrice() <= 0) {
			return Response.ok("Price is incorrect").status(500).build();
		} else if (webCoupon.getImage() == null) {
			return Response.ok("Image is incorrect").status(500).build();
		}
		
		try {
			facade.createCoupon(webCoupon.convertToCoupon());
			return Response.ok("Coupon created").status(200).build();
		} catch (CouponSystemException e) {
			e.printStackTrace();
			return Response.ok(e.getMessage()).status(500).build();
		}
	}
	
	@GET
	@Path("readCoupon/{parId}")
	//@Produces(MediaType.APPLICATION_JSON)
	public Response readCoupon(@PathParam("parId") long id) {
		
		CompanyFacade facade = getFacade();
		if (facade == null) {
			return Response.ok("You are not logged as company").status(500).build();
		}
		
		try {
			Coupon c = new Coupon();
			c.setId(id);
			WebCoupon wc = new WebCoupon(facade.readCoupon(c));
			GenericEntity<WebCoupon> ge = new GenericEntity<WebCoupon> (wc) {};
			return Response.ok(ge).status(200).build();
		} catch (CouponSystemException e) {
			e.printStackTrace();
			return Response.ok(e.getMessage()).status(500).build();
		}
	}
	
	@PUT
	@Path("updateCoupon")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateCoupon(WebCoupon webCoupon) {
		
		CompanyFacade facade = getFacade();
		if (facade == null) {
			return Response.ok("You are not logged as company").status(500).build();
		}
		
		if (webCoupon == null) {
			return Response.ok("Null object received, probably one of the fields is incorrect").status(500).build();
		}
		
		if (webCoupon.getId() == 0) {
			return Response.ok("ID is incorrect").status(500).build();
		} else if (webCoupon.getTitle() == null || webCoupon.getTitle().equals("")) {
			return Response.ok("Title is incorrect").status(500).build();
		} else if (webCoupon.getStartDate() == null) {
			return Response.ok("Start date is incorrect").status(500).build();
		} else if (webCoupon.getEndDate() == null) {
			return Response.ok("End date is incorrect").status(500).build();
		} else if (webCoupon.getAmount() <= 0) {
			return Response.ok("Amount is incorrect").status(500).build();
		} else if (webCoupon.getType() == null) {
			return Response.ok("Coupon type is incorrect").status(500).build();
		} else if (webCoupon.getMessage() == null || webCoupon.getMessage().equals("")) {
			return Response.ok("Message is incorrect").status(500).build();
		} else if (webCoupon.getPrice() <= 0) {
			return Response.ok("Price is incorrect").status(500).build();
		} else if (webCoupon.getImage() == null) {
			return Response.ok("Image is incorrect").status(500).build();
		}
		
		try {
			facade.updateCoupon(webCoupon.convertToCoupon());
			return Response.ok("Coupon updated").status(200).build();
		} catch (CouponSystemException e) {
			e.printStackTrace();
			return Response.ok(e.getMessage()).status(500).build();
		}
	}
	
	@DELETE
	@Path("deleteCoupon/{parId}")
	public Response deleteCoupon(@PathParam("parId") long id) {
		
		CompanyFacade facade = getFacade();
		if (facade == null) {
			return Response.ok("You are not logged as company").status(500).build();
		}
		
		try {
			Coupon c = new Coupon();
			c.setId(id);
			facade.deleteCoupon(c);
			return Response.ok("Coupon deleted").status(200).build();
		} catch (CouponSystemException e) {
			e.printStackTrace();
			return Response.ok(e.getMessage()).status(500).build();
		}
	}
	
	@GET
	@Path("getAllCoupons")
	//@Produces(MediaType.APPLICATION_JSON)
	public Response getAllCoupons() {
		
		CompanyFacade facade = getFacade();
		if (facade == null) {
			return Response.ok("You are not logged as company").status(500).build();
		}
		
		Collection<WebCoupon> webCoupons = new HashSet<>();
		
		Collection<Coupon> coupons=null;
		try {
			coupons = facade.getAllCoupons();
		} catch (CouponSystemException e) {
			e.printStackTrace();
			return Response.ok(e.getMessage()).status(500).build();
		}
		
		for (Coupon cur: coupons) {
			webCoupons.add(new WebCoupon(cur));
		}
		
		GenericEntity<Collection<WebCoupon>> ge = new GenericEntity<Collection<WebCoupon>>(webCoupons) {};
		return Response.ok(ge).status(200).build();
	}
	
	
	@GET
	@Path("getCouponsByType/{parCouponType}")
	//@Produces(MediaType.APPLICATION_JSON)
	public Response getCouponsByType(@PathParam("parCouponType") String parCouponType) {
		
		CompanyFacade facade = getFacade();
		if (facade == null) {
			return Response.ok("You are not logged as company").status(500).build();
		}
		
		Collection<WebCoupon> webCoupons = new HashSet<>();
		
		Collection<Coupon> coupons=null;
		try {
			coupons = facade.getCouponsByType(CouponType.valueOf(parCouponType));
		} catch (CouponSystemException e) {
			e.printStackTrace();
			return Response.ok(e.getMessage()).status(500).build();
		}
		
		for (Coupon cur: coupons) {
			webCoupons.add(new WebCoupon(cur));
		}
		
		GenericEntity<Collection<WebCoupon>> ge = new GenericEntity<Collection<WebCoupon>>(webCoupons) {};
		return Response.ok(ge).status(200).build();
	}
	
	@GET
	@Path("getCouponsBelowPrice/{parPrice}")
	//@Produces(MediaType.APPLICATION_JSON)
	public Response getCouponsBelowPrice(@PathParam("parPrice") double price) {
		
		CompanyFacade facade = getFacade();
		if (facade == null) {
			return Response.ok("You are not logged as company").status(500).build();
		}
		
		Collection<WebCoupon> webCoupons = new HashSet<>();
		
		Collection<Coupon> coupons=null;
		try {
			coupons = facade.getCouponsBelowPrice(price);
		} catch (CouponSystemException e) {
			e.printStackTrace();
			return Response.ok(e.getMessage()).status(500).build();
		}
		
		for (Coupon cur: coupons) {
			webCoupons.add(new WebCoupon(cur));
		}
		
		GenericEntity<Collection<WebCoupon>> ge = new GenericEntity<Collection<WebCoupon>>(webCoupons) {};
		return Response.ok(ge).status(200).build();
	}
	
	@POST
	@Path("getCouponsBeforeDate")
	@Consumes(MediaType.APPLICATION_JSON)
	//@Produces(MediaType.APPLICATION_JSON)
	public Response getCouponsBeforeDate(WebDate webDate) {
		
		CompanyFacade facade = getFacade();
		if (facade == null) {
			return Response.ok("You are not logged as company").status(500).build();
		}
		
		if (webDate == null || webDate.getDate() == null) {
			return Response.ok("The date is incorrect").status(500).build();
		}
		
		Collection<WebCoupon> webCoupons = new HashSet<>();
		
		Collection<Coupon> coupons=null;
		try {
			coupons = facade.getCouponsBeforeDate(webDate.getDate());
		} catch (CouponSystemException e) {
			e.printStackTrace();
			return Response.ok(e.getMessage()).status(500).build();
		}
		
		for (Coupon cur: coupons) {
			webCoupons.add(new WebCoupon(cur));
		}
		
		GenericEntity<Collection<WebCoupon>> ge = new GenericEntity<Collection<WebCoupon>>(webCoupons) {};
		return Response.ok(ge).status(200).build();
	}
	
	@GET
	@Path("getCompanyName")
	public String getCompanyName() {
		
		CompanyFacade facade = getFacade();
		if (facade == null) return "not logged as company";
		return facade.getCompanyName();
	}
	
	private CompanyFacade getFacade() {
//		CouponSystem cs = null;
		CompanyFacade facade = null;

//		try {
//			cs = CouponSystem.getInstance();
//			facade = (CompanyFacade) cs.login("D", "D4", ClientType.COMPANY);
//		} catch (CouponSystemDatabaseConnectionException e) {
//			System.out.println(e.getMessage());
//		} catch (CouponSystemException e) {
//			System.out.println(e.getMessage());
//		}
		
		try {
		HttpSession session = request.getSession();
    	facade = (CompanyFacade) session.getAttribute("facade");
		} catch (Exception e) {}
    	
		/*if (cs != null) {
			try {
				cs.shutdown();
			} catch (CouponSystemException e) {
				System.out.println(e.getMessage());
			}
		}*/
		
		return facade;
	}
}
