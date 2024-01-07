package registrazione;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletRegistrazione extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private ControllerRegistrazione controllerRegistrazione;
	
	@Override
	public void init() {
		controllerRegistrazione = new ControllerRegistrazione();
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		PrintWriter out = response.getWriter();
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		System.out.println("Registrazione di: " + username + " | " + password);
		
		boolean result = controllerRegistrazione.registraUtente(username, password);
		out.println(result);
	}

}
