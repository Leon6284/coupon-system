package log;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import core.CouponSystem;
import exceptions.CouponSystemDatabaseConnectionException;
import exceptions.CouponSystemException;
import facades.ClientType;
import facades.CouponClientInterface;

public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		PrintWriter out = resp.getWriter();
		out.println("<html><body style=\"color:red\">"); // in case of error

		// delete cookies - in case of unsuccessful login
		Cookie[] cookies = req.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("userType") || cookie.getName().equals("userName")
						|| cookie.getName().equals("password")) {
					cookie.setMaxAge(0);
					resp.addCookie(cookie);
				}
			}
		}


		String clientType = req.getParameter("type");
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		if (clientType == null) {
			out.println("<h3>Error: client type is null</h3>");
			out.println("</body></html>");
			return;
		}
		if (username == null) {
			out.println("<h3>Error: username is null</h3>");
			out.println("</body></html>");
			return;
		}
		if (password == null) {
			out.println("<h3>Error: password is null</h3>");
			out.println("</body></html>");
			return;
		}

		CouponSystem cs = null;
		try {
			cs = CouponSystem.getInstance();
		} catch (CouponSystemDatabaseConnectionException e) {
			out.println("<h3>Error: " + e.getMessage() + "</h3>");
			out.println("</body></html>");
			return;
		}

		CouponClientInterface facade = null;
		HttpSession session = req.getSession(true);
		String url = "";

		try {
			switch (clientType) {
			case "Admin":
				facade = cs.login(username, password, ClientType.ADMIN);
				session.setAttribute("facade", facade);
				setCookies(resp, clientType, username, password); // set login cookies in case of successful login 
				url = resp.encodeRedirectURL("admin/adminindex.html");
				resp.sendRedirect(url);
				break;
			case "Company":
				facade = cs.login(username, password, ClientType.COMPANY);
				session.setAttribute("facade", facade);
				setCookies(resp, clientType, username, password);
				url = resp.encodeRedirectURL("company/companyindex.html");
				resp.sendRedirect(url);
				break;
			case "Customer":
				facade = cs.login(username, password, ClientType.CUSTOMER);
				session.setAttribute("facade", facade);
				setCookies(resp, clientType, username, password);
				url = resp.encodeRedirectURL("customer/customerindex.html");
				resp.sendRedirect(url);
				break;
			default:
				out.println("<h3>Unknown client type</h3>");
				out.println("</body></html>");
				return;
			}
		} catch (CouponSystemException e) {
			out.println("<h3>Error: " + e.getMessage() + "</h3>");
			out.println("</body></html>");
			return;
		}

		out.println("</body></html>");
	}
	
	private void setCookies(HttpServletResponse resp, String clientType, String username, String password) {
		
		int maxAge = 900;
		
		Cookie cookieUserType = new Cookie("userType", clientType);
		Cookie cookieUserName = new Cookie("userName", username);
		Cookie cookiePassword = new Cookie("password", password);
		
		cookieUserType.setMaxAge(maxAge);
		cookieUserName.setMaxAge(maxAge);
		cookiePassword.setMaxAge(maxAge);
		
		resp.addCookie(cookieUserType);
		resp.addCookie(cookieUserName);
		resp.addCookie(cookiePassword);
		
	}

}
