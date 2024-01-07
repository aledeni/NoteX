package myTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import dominio.Etichetta;
import filesystem.util.ControllerFile;
import filesystem.util.FileSystemUtil;
import filesystem.util.IllegalFileTypeException;
import gestioneUtenteRegistrato.ControllerUtenteRegistrato;
import hibernate.util.HibernateUtil;

public class Test extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) {
		Part part = null;
		try {
			part = req.getPart("file");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ControllerUtenteRegistrato controllerUtente = new ControllerUtenteRegistrato();
		try {
			List<Etichetta> etichette=new ArrayList<>();
			res.getWriter().print(controllerUtente.creaNota(req.getServletContext(), "Alessio", "Blocco1", "nota1",etichette, part));
		} catch (IllegalFileTypeException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) {
		ControllerUtenteRegistrato controllerUtente = new ControllerUtenteRegistrato();
		List<Etichetta> etichette = new ArrayList<>();
		try {
			if (req.getParameter("azione").equals("creaBlocco"))
				res.getWriter().print(controllerUtente.creaBloccoDiAppunti("Alessio", "Blocco1", etichette));
				
			else
				res.getWriter().print(controllerUtente.eliminaBloccoDiAppunti(req.getServletContext(),"Alessio", "Blocco1"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
