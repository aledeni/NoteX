package gestioneUtente;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.google.gson.Gson;

import dominio.BloccoDiAppunti;
import dominio.Etichetta;
import dominoFrontend.BloccoDiAppuntiFrontend;
import dominoFrontend.NotaFrontend;
import dominoFrontend.UtenteFrontend;
import filesystem.util.ControllerFile;
import filesystem.util.FileSystemUtil;
import filesystem.util.IllegalFileTypeException;
import gestioneUtenteRegistrato.ControllerUtenteRegistrato;
import hibernate.util.HibernateUtil;

public class ServletUtente extends HttpServlet {

	private ControllerRicerca controllerRicerca;
	private Gson gson;
	private ServletContext servletContext;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void init() {
		this.controllerRicerca = new ControllerRicerca();
		this.gson = new Gson();
		this.servletContext = this.getServletContext();
		synchronized (this.servletContext) {
			this.servletContext.setAttribute("username", "Alessio");
		}
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) {
		String username = (String) req.getServletContext().getAttribute("username");
		if (username != null) {
			String tipoRicerca = req.getParameter("tipoRicerca");
			if (tipoRicerca != null) {
				if (tipoRicerca.equals("utentiRegistrati")) {
					try {
						// parsing
						String stringaRicerca = req.getParameter("stringaRicerca");

						// retrieving result
						List<UtenteFrontend> result = controllerRicerca.ricercaUtentiRegistrati(stringaRicerca).stream()
								.map(u -> {
									return new UtenteFrontend(u);
								}).collect(Collectors.toList());
						res.getWriter().print(gson.toJson(result));

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if (tipoRicerca.equals("blocchiDiAppunti")) {
					try {
						// parsing
						String stringaRicerca = req.getParameter("stringaRicerca");

						// retrieving result
						List<BloccoDiAppuntiFrontend> result = controllerRicerca.ricercaBlocchiDiAppunti(stringaRicerca).stream()
								.map(u -> {
									return new BloccoDiAppuntiFrontend(u);
								}).collect(Collectors.toList());
						res.getWriter().print(gson.toJson(result));

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if (tipoRicerca.equals("note")) {
					try {
						// parsing
						String stringaRicerca = req.getParameter("stringaRicerca");

						// retrieving result
						List<NotaFrontend> result = controllerRicerca.ricercaNote(stringaRicerca).stream()
								.map(u -> {
									return new NotaFrontend(u);
								}).collect(Collectors.toList());
						res.getWriter().print(gson.toJson(result));

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}