package log;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
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
		
		HttpSession session = req.getSession(false);
		if (session!=null && session.getAttribute("facade")!=null) {
			session.setAttribute("facade", null);
		}
		
		String url = resp.encodeRedirectURL("index.html");
		resp.sendRedirect(url);
		
	}
	
	
}
