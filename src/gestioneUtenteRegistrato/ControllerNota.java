package gestioneUtenteRegistrato;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletContext;
import javax.servlet.http.Part;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.ibm.db2.jcc.am.n;

import dominio.BloccoDiAppunti;
import dominio.Etichetta;
import dominio.Nota;
import dominio.UtenteRegistrato;
import filesystem.util.ControllerFile;
import filesystem.util.FileSystemUtil;
import hibernate.util.HibernateUtil;

public class ControllerNota implements IControllerNota {

	private final String RETRIEVE_UTENTE_REGISTRATO_BY_USERNAME = "SELECT * FROM UTENTI_REGISTRATI WHERE Username = ? ";

	@Override
	public boolean creaNota(ServletContext context, String username, String nomeBlocco, String nomeNota,
			List<Etichetta> etichette, Part file) {
		// parameter check
		if (context == null)
			throw new NullPointerException("context is null");
		if (username == null)
			throw new NullPointerException("username is null");
		if (nomeBlocco == null)
			throw new NullPointerException("nomeBloccoIsNull");
		if (nomeNota == null)
			throw new NullPointerException("nomeNota is null");
		if (etichette == null)
			throw new NullPointerException("etichette is null");
		if (file == null)
			throw new NullPointerException("file is null");

		// declaration and initialization
		boolean result = false;
		ControllerFile controllerFile = FileSystemUtil.getControllerFile();
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

		// retrieving utenteRegistrato by username
		Session session = sessionFactory.openSession();
		Transaction transaction=session.beginTransaction();
		Query query = session.createSQLQuery(RETRIEVE_UTENTE_REGISTRATO_BY_USERNAME).addEntity(UtenteRegistrato.class);
		query.setParameter(0, username);
		List<UtenteRegistrato> retrieved = query.list();
		if (retrieved.size() == 1) {
			UtenteRegistrato u = retrieved.get(0);
			Optional<BloccoDiAppunti> b = u.getBloccoDiAppunti(nomeBlocco);

			if (b.isPresent()) {
				// persiting fileDiAppunti
				String percorsoFile = controllerFile.persist(context, file);
				// creating nota
				result = b.get().creaNota(nomeNota, percorsoFile, etichette);
				if(result)
				{
					// persisting nota
					session.persist(b.get().getNota(nomeNota).get());
					session.persist(b.get());
					session.persist(u);
				}
			}
		}

		// releasing resources
		transaction.commit();
		session.close();

		// return
		return result;
	}

	@Override
	public List<Nota> elencoNote(String username, String nomeBlocco) {
		// parameter check
		if (username == null)
			throw new NullPointerException();
		if (nomeBlocco == null)
			throw new NullPointerException();

		// declaration and initialization
		List<Nota> result = new ArrayList<Nota>();
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

		// retrieving utenteRegisttrato by username
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		Query query = session.createSQLQuery(RETRIEVE_UTENTE_REGISTRATO_BY_USERNAME).addEntity(UtenteRegistrato.class);
		query.setParameter(0, username);
		List<UtenteRegistrato> retrieved = query.list();

		if (retrieved.size() == 1) {
			UtenteRegistrato u = retrieved.get(0);
			Optional<BloccoDiAppunti> b = u.getBloccoDiAppunti(nomeBlocco);

			if (b.isPresent()) {
				// retrieving note
				result = b.get().getNote();
			}
		}
		// releasing resources
		transaction.commit();
		session.close();
		// return
		return result;
	}

	@Override
	public boolean eliminaNota(ServletContext context, String username, String nomeBlocco, String nomeNota) {
		// parameter check
		if (context == null)
			throw new NullPointerException("context is null");
		if (username == null)
			throw new NullPointerException("username is null");
		if (nomeBlocco == null)
			throw new NullPointerException("nomeBlocco is null");
		if (nomeNota == null)
			throw new NullPointerException("nomeNota is null");

		// declaration and initialization
		boolean result = false;
		ControllerFile controllerFile = FileSystemUtil.getControllerFile();
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

		// retrieving utenteRegistrato by username
		Session session = sessionFactory.openSession();
		Transaction transaction=session.beginTransaction();
		Query query = session.createSQLQuery(RETRIEVE_UTENTE_REGISTRATO_BY_USERNAME).addEntity(UtenteRegistrato.class);
		query.setParameter(0, username);
		List<UtenteRegistrato> retrieved = query.list();
		if (retrieved.size() == 1) {
			UtenteRegistrato u = retrieved.get(0);
			Optional<BloccoDiAppunti> b = u.getBloccoDiAppunti(nomeBlocco);

			if (b.isPresent()) {
				Optional<Nota> n=b.get().getNota(nomeNota);
				if(n.isPresent())
				{
					// deleting fileDiAppunti
					String percorsoFile = b.get().getNota(nomeNota).get().getPercorsoFile();
					result = controllerFile.elimina(context, percorsoFile);
					if (result) {
						b.get().eliminaNota(nomeNota);
						session.delete(n.get());
						
					}
					
				}
				
			}
		}
		transaction.commit();
		session.close();
		return result;
	}

}
