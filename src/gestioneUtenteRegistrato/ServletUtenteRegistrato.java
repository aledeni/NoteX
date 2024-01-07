package gestioneUtenteRegistrato;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.google.gson.Gson;

import dominio.BloccoDiAppunti;
import dominio.Etichetta;
import dominoFrontend.BloccoDiAppuntiFrontend;
import dominoFrontend.NotaFrontend;
import filesystem.util.ControllerFile;
import filesystem.util.FileSystemUtil;
import filesystem.util.IllegalFileTypeException;
import gestioneUtenteRegistrato.ControllerUtenteRegistrato;
import hibernate.util.HibernateUtil;

public class ServletUtenteRegistrato extends HttpServlet {

	private ControllerUtenteRegistrato controllerUtenteRegistrato;
	private Gson gson;
	private ServletContext servletContext;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void init() {
		this.controllerUtenteRegistrato = new ControllerUtenteRegistrato();
		this.gson = new Gson();
		this.servletContext = this.getServletContext();
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) {
		String username = "";
		HttpSession session = req.getSession();
		synchronized (session) {
			username = (String) session.getAttribute("username");
		}
		if (username != null) {
			String action = req.getParameter("action");
			if (action != null) {
				if (action.equals("creaNota")) {
					try {
						// parsing
						String nomeBlocco = req.getParameter("nomeBlocco");
						String nomeNota = req.getParameter("nomeNota");
						String[][] arrayEtichette = gson.fromJson(req.getParameter("etichette"), String[][].class);
						List<Etichetta> etichette = new ArrayList<>();
						for (String[] e : arrayEtichette) {
							Etichetta etichetta = new Etichetta();
							etichetta.setNome(e[0]);
							etichetta.setValore(e[1]);
						}
						Part file = req.getPart("file");
						
						// creation of Nota
						boolean result = controllerUtenteRegistrato.creaNota(servletContext, username, nomeBlocco,
								nomeNota, etichette, file);
						res.getWriter().print(result);

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}

	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) {
		String username = "";
		HttpSession session = req.getSession();
		synchronized (session) {
			username = (String) session.getAttribute("username");
		}
		if (username != null) {
			String action = req.getParameter("action");
			if (action != null) {
				if (action.equals("creaBlocco")) {
					try {
						// parsing
						String nomeBlocco = req.getParameter("nomeBlocco");
						String[][] arrayEtichette = gson.fromJson(req.getParameter("etichette"), String[][].class);
						List<Etichetta> etichette = new ArrayList<>();
						for (String[] e : arrayEtichette) {
							Etichetta etichetta = new Etichetta();
							etichetta.setNome(e[0]);
							etichetta.setValore(e[1]);
						}

						// creation of Blocco
						boolean result = controllerUtenteRegistrato.creaBloccoDiAppunti(username, nomeBlocco,
								etichette);
						res.getWriter().print(result);

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if (action.equals("elencoBlocchi")) {
					try {
						// parsing

						// retrieving blocchi
						List<BloccoDiAppuntiFrontend> result = controllerUtenteRegistrato.elencoBlocchiDiAppunti(username).stream()
								.map(b -> {
									return new BloccoDiAppuntiFrontend(b);
								}).collect(Collectors.toList());
						res.getWriter().print(gson.toJson(result));

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if (action.equals("eliminaBlocco")) {
					try {
						// parsing
						String nomeBlocco = req.getParameter("nomeBlocco");
						// retrieving blocchi
						boolean result = controllerUtenteRegistrato.eliminaBloccoDiAppunti(servletContext, username,
								nomeBlocco);
						res.getWriter().print(result);

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				if (action.equals("elencoNote")) {
					try {
						// parsing
						String nomeBlocco = req.getParameter("nomeBlocco");
						// retrieving blocchi
						List<NotaFrontend> result = controllerUtenteRegistrato.elencoNote(username,nomeBlocco).stream()
								.map(n -> {
									return new NotaFrontend(n);
								}).collect(Collectors.toList());
						res.getWriter().print(gson.toJson(result));

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			
			if (action.equals("eliminaNota")) {
				try {
					// parsing
					String nomeBlocco = req.getParameter("nomeBlocco");
					String nomeNota = req.getParameter("nomeNota");
					// retrieving blocchi
					boolean result = controllerUtenteRegistrato.eliminaNota(servletContext, username, nomeBlocco, nomeNota);
					res.getWriter().print(result);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
