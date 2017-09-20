package webservices;

import java.util.Collection;
import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
//import javax.servlet.http.HttpSession;
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

import beans.Company;
import beans.Customer;
import core.CouponSystem;
import exceptions.CouponSystemDatabaseConnectionException;
import exceptions.CouponSystemException;
import facades.AdminFacade;
import facades.ClientType;
import webbeans.WebCompany;
import webbeans.WebCustomer;

@Path("admin")
public class WebServiceAdmin {
	
	@Context private HttpServletRequest request;
	
	@POST
	@Path("createCompany")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createCompany(WebCompany webCompany) {
		
		AdminFacade facade = getFacade();
		if (facade == null) {
			return Response.ok("You are not logged as administrator").status(500).build();
		}
		
		if (webCompany == null) {
			return Response.ok("Null object received, probably one of the fields is incorrect").status(500).build();
		}
		
		if (webCompany.getId() == 0) {
			return Response.ok("ID is incorrect").status(500).build();
		} else if (webCompany.getCompName() == null || webCompany.getCompName().equals("")) {
			return Response.ok("Company name is incorrect").status(500).build();
		} else if (webCompany.getPassword() == null || webCompany.getPassword().equals("")) {
			return Response.ok("Password is incorrect").status(500).build();
		} else if (webCompany.getEmail() == null || webCompany.getEmail().equals("")) {
			return Response.ok("E-mail is incorrect").status(500).build();
		}
		
		try {
			facade.createCompany(webCompany.convertToCompany());
			return Response.ok("Company created").status(200).build();
		} catch (CouponSystemException e) {
			e.printStackTrace();
			return Response.ok(e.getMessage()).status(500).build();
		}
	}
	
	@GET
	@Path("readCompany/{parId}")
	//@Produces(MediaType.APPLICATION_JSON)
	public Response readCompany(@PathParam("parId") long id) {
		
		AdminFacade facade = getFacade();
		if (facade == null) {
			return Response.ok("You are not logged as administrator").status(500).build();
		}
		
		try {
			Company c = new Company();
			c.setId(id);
			WebCompany wc = new WebCompany(facade.readCompany(c));
			GenericEntity<WebCompany> ge = new GenericEntity<WebCompany>(wc) {};
			return Response.ok(ge).status(200).build();
		} catch (CouponSystemException e) {
			e.printStackTrace();
			return Response.ok(e.getMessage()).status(500).build();
		}
	}
	
	@GET
	@Path("getAllCompanies")
	//@Produces(MediaType.APPLICATION_JSON)
	public Response getAllCompanies() {
		
		AdminFacade facade = getFacade();
		if (facade == null) {
			return Response.ok("You are not logged as administrator").status(500).build();
		}
		
		Collection<WebCompany> webCompanies = new HashSet<>();
		
		Collection<Company> companies=null;
		try {
			companies = facade.getAllCompanies();
		} catch (CouponSystemException e) {
			e.printStackTrace();
			return Response.ok(e.getMessage()).status(500).build();
		}
		
		for (Company cur: companies) {
			webCompanies.add(new WebCompany(cur));
		}
		
		GenericEntity<Collection<WebCompany>> ge = new GenericEntity<Collection<WebCompany>>(webCompanies) {};
		return Response.ok(ge).status(200).build();
	}
	
	@PUT
	@Path("updateCompany")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateCompany(WebCompany webCompany) {
		
		AdminFacade facade = getFacade();
		if (facade == null) {
			return Response.ok("You are not logged as administrator").status(500).build();
		}
		
		if (webCompany == null) {
			return Response.ok("Null object received, probably one of the fields is incorrect").status(500).build();
		}
		
		if (webCompany.getId() == 0) {
			return Response.ok("ID is incorrect").status(500).build();
		} else if (webCompany.getCompName() == null || webCompany.getCompName().equals("")) {
			return Response.ok("Company name is incorrect").status(500).build();
		} else if (webCompany.getPassword() == null || webCompany.getPassword().equals("")) {
			return Response.ok("Password is incorrect").status(500).build();
		} else if (webCompany.getEmail() == null || webCompany.getEmail().equals("")) {
			return Response.ok("E-mail is incorrect").status(500).build();
		}
		
		try {
			facade.updateCompany(webCompany.convertToCompany());
			return Response.ok("Company updated").status(200).build();
		} catch (CouponSystemException e) {
			e.printStackTrace();
			return Response.ok(e.getMessage()).status(500).build();
		}
	}
	
	@DELETE
	@Path("deleteCompany/{parId}")
	public Response deleteCompany(@PathParam("parId") long id) {
		
		AdminFacade facade = getFacade();
		if (facade == null) {
			return Response.ok("You are not logged as administrator").status(500).build();
		}
		
		try {
			Company c = new Company();
			c.setId(id);
			facade.deleteCompany(c);
			return Response.ok("Company deleted").status(200).build();
		} catch (CouponSystemException e) {
			e.printStackTrace();
			return Response.ok(e.getMessage()).status(500).build();
		}
	}
	
	@POST
	@Path("createCustomer")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createCustomer(WebCustomer webCustomer) {
		
		AdminFacade facade = getFacade();
		if (facade == null) {
			return Response.ok("You are not logged as administrator").status(500).build();
		}
		
		if (webCustomer == null) {
			return Response.ok("Null object received, probably one of the fields is incorrect").status(500).build();
		}
		
		if (webCustomer.getId() == 0) {
			return Response.ok("ID is incorrect").status(500).build();
		} else if (webCustomer.getCustName() == null || webCustomer.getCustName().equals("")) {
			return Response.ok("Customer name is incorrect").status(500).build();
		} else if (webCustomer.getPassword() == null || webCustomer.getPassword().equals("")) {
			return Response.ok("Password is incorrect").status(500).build();
		}
		
		try {
			facade.createCustomer(webCustomer.convertToCustomer());
			return Response.ok("Customer created").status(200).build();
		} catch (CouponSystemException e) {
			e.printStackTrace();
			return Response.ok(e.getMessage()).status(500).build();
		}
	}
	
	@GET
	@Path("readCustomer/{parId}")
	//@Produces(MediaType.APPLICATION_JSON)
	public Response readCustomer(@PathParam("parId") long id) {
		
		AdminFacade facade = getFacade();
		if (facade == null) {
			return Response.ok("You are not logged as administrator").status(500).build();
		}
		
		try {
			Customer c = new Customer();
			c.setId(id);
			WebCustomer wc = new WebCustomer(facade.readCustomer(c));
			GenericEntity<WebCustomer> ge = new GenericEntity<WebCustomer> (wc) {};
			return Response.ok(ge).status(200).build();
		} catch (CouponSystemException e) {
			e.printStackTrace();
			return Response.ok(e.getMessage()).status(500).build();
		}
	}
	
	@GET
	@Path("getAllCustomers")
	//@Produces(MediaType.APPLICATION_JSON)
	public Response getAllCustomers() {
		
		AdminFacade facade = getFacade();
		if (facade == null) {
			return Response.ok("You are not logged as administrator").status(500).build();
		}
		
		Collection<WebCustomer> webCustomers = new HashSet<>();
		
		Collection<Customer> customers=null;
		try {
			customers = facade.getAllCustomers();
		} catch (CouponSystemException e) {
			e.printStackTrace();
			return Response.ok(e.getMessage()).status(500).build();
		}
		
		for (Customer cur: customers) {
			webCustomers.add(new WebCustomer(cur));
		}
		
		GenericEntity<Collection<WebCustomer>> ge = new GenericEntity<Collection<WebCustomer>>(webCustomers) {};
		return Response.ok(ge).status(200).build();
	}
	
	@PUT
	@Path("updateCustomer")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateCustomer(WebCustomer webCustomer) {
		
		AdminFacade facade = getFacade();
		if (facade == null) {
			return Response.ok("You are not logged as administrator").status(500).build();
		}
		
		if (webCustomer == null) {
			return Response.ok("Null object received, probably one of the fields is incorrect").status(500).build();
		}
		
		if (webCustomer.getId() == 0) {
			return Response.ok("ID is incorrect").status(500).build();
		} else if (webCustomer.getCustName() == null || webCustomer.getCustName().equals("")) {
			return Response.ok("Customer name is incorrect").status(500).build();
		} else if (webCustomer.getPassword() == null || webCustomer.getPassword().equals("")) {
			return Response.ok("Password is incorrect").status(500).build();
		}
		
		try {
			facade.updateCustomer(webCustomer.convertToCustomer());
			return Response.ok("Company updated").status(200).build();
		} catch (CouponSystemException e) {
			e.printStackTrace();
			return Response.ok(e.getMessage()).status(500).build();
		}
	}
	
	@DELETE
	@Path("deleteCustomer/{parId}")
	public Response deleteCustomer(@PathParam("parId") long id) {
		
		AdminFacade facade = getFacade();
		if (facade == null) {
			return Response.ok("You are not logged as administrator").status(500).build();
		}
		
		try {
			Customer c = new Customer();
			c.setId(id);
			facade.deleteCustomer(c);
			return Response.ok("Customer deleted").status(200).build();
		} catch (CouponSystemException e) {
			e.printStackTrace();
			return Response.ok(e.getMessage()).status(500).build();
		}
	}
	
	/**
	 * return null in case the user is not logged as administrator
	 * */
	private AdminFacade getFacade() {
//		CouponSystem cs = null;
		AdminFacade facade = null;

//		try {
//			cs = CouponSystem.getInstance();
//			facade = (AdminFacade) cs.login("admin", "1234", ClientType.ADMIN);
//		} catch (CouponSystemDatabaseConnectionException e) {
//			System.out.println(e.getMessage());
//		} catch (CouponSystemException e) {
//			System.out.println(e.getMessage());
//		}
		
		try {
    	HttpSession session = request.getSession();
    	facade = (AdminFacade) session.getAttribute("facade");
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
