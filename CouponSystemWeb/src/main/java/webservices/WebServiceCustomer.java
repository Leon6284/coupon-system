package webservices;

import java.util.Collection;
import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
import facades.CustomerFacade;
import webbeans.WebCoupon;

@Path("customer")
public class WebServiceCustomer {
	
	@Context private HttpServletRequest request;

	@POST
	@Path("purchaseCoupon/{parId}")
	public Response purchaseCoupon(@PathParam("parId") long id) {
		
		CustomerFacade facade = getFacade();
		if (facade == null) {
			return Response.ok("You are not logged as customer").status(500).build();
		}
		
		try {
			Coupon c = new Coupon();
			c.setId(id);
			facade.purchaseCoupon(c);
			return Response.ok("Coupon purchased").status(200).build();
		} catch (CouponSystemException e) {
			e.printStackTrace();
			return Response.ok(e.getMessage()).status(500).build();
		}
	}
	
	@GET
	@Path("getAllAvailableCoupons")
	//@Produces(MediaType.APPLICATION_JSON)
	public Response getAllAvailableCoupons() {
		
		CustomerFacade facade = getFacade();
		if (facade == null) {
			return Response.ok("You are not logged as customer").status(500).build();
		}
		
		Collection<WebCoupon> webCoupons = new HashSet<>();
		
		Collection<Coupon> coupons=null;
		try {
			coupons = facade.getAllAvailableCoupons();
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
	@Path("getAllPurchasedCoupons")
	//@Produces(MediaType.APPLICATION_JSON)
	public Response getAllPurchasedCoupons() {
		
		CustomerFacade facade = getFacade();
		if (facade == null) {
			return Response.ok("You are not logged as customer").status(500).build();
		}
		
		Collection<WebCoupon> webCoupons = new HashSet<>();
		
		Collection<Coupon> coupons=null;
		try {
			coupons = facade.getAllPurchasedCoupons();
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
	@Path("getAllPurchasedCouponsByType/{parCouponType}")
	//@Produces(MediaType.APPLICATION_JSON)
	public Response getAllPurchasedCouponsByType(@PathParam("parCouponType") String parCouponType) {
		
		CustomerFacade facade = getFacade();
		if (facade == null) {
			return Response.ok("You are not logged as customer").status(500).build();
		}
		
		Collection<WebCoupon> webCoupons = new HashSet<>();
		
		Collection<Coupon> coupons=null;
		try {
			coupons = facade.getAllPurchasedCouponsByType(CouponType.valueOf(parCouponType));
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
	@Path("getAllPurchasedCouponsByPrice/{parPrice}")
	//@Produces(MediaType.APPLICATION_JSON)
	public Response getAllPurchasedCouponsByPrice(@PathParam("parPrice") double price) {
		
		CustomerFacade facade = getFacade();
		if (facade == null) {
			return Response.ok("You are not logged as customer").status(500).build();
		}
		
		Collection<WebCoupon> webCoupons = new HashSet<>();
		
		Collection<Coupon> coupons=null;
		try {
			coupons = facade.getAllPurchasedCouponsByPrice(price);
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
	@Path("getCustomerName")
	public String getCustomerName() {
		
		CustomerFacade facade = getFacade();
		if (facade == null) return "not logged as customer";
		return facade.getCustomerName();
	}
	
	
	private CustomerFacade getFacade() {
//		CouponSystem cs = null;
		CustomerFacade facade = null;

//		try {
//			cs = CouponSystem.getInstance();
//			facade = (CustomerFacade) cs.login("C", "C3", ClientType.CUSTOMER);
//		} catch (CouponSystemDatabaseConnectionException e) {
//			System.out.println(e.getMessage());
//		} catch (CouponSystemException e) {
//			System.out.println(e.getMessage());
//		}
		
		try {
		HttpSession session = request.getSession();
    	facade = (CustomerFacade) session.getAttribute("facade");
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
