package accessoECredenziali;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dominio.TipoUtente;

public class ServletLogin extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private ControllerLogin controllerLogin;
	
	@Override
	public void init() {
		controllerLogin = new ControllerLogin();
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		boolean result = controllerLogin.autentica(TipoUtente.UTENTE_REGISTRATO, username, password);
		
		if(result)
		{
			HttpSession session=request.getSession();
			synchronized(session)
			{
				session.setAttribute("tipoUtente", TipoUtente.UTENTE_REGISTRATO);
				session.setAttribute("username", username);
			}
		}
		
		
		response.getWriter().println(result);
	}

}
