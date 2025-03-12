package helper;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Session {
	
	public interface Continuation {
		public void run();
	}

	public static void startSession(HttpSession session, Object object) {
		session.setAttribute("user", object);
	}
	
	public static void clearSession(HttpSession session) {
		session.setAttribute("user", null);
	}
	
	public static void checkSession(HttpServletRequest request, HttpServletResponse response, Continuation continuation) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		if (session.getAttribute("user") != null) {

			continuation.run();
		} else {

			response.sendRedirect("employee-login");
		}
	}
}
