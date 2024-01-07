package gestioneUtenteRegistrato;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletContext;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import dominio.BloccoDiAppunti;
import dominio.Etichetta;
import dominio.Nota;
import dominio.UtenteRegistrato;
import filesystem.util.ControllerFile;
import filesystem.util.FileSystemUtil;
import hibernate.util.HibernateUtil;

public class ControllerBloccoDiAppunti implements IControllerBloccoDiAppunti {

	private final String RETRIEVE_UTENTE_REGISTRATO_BY_USERNAME = "SELECT * FROM UTENTI_REGISTRATI WHERE Username = ? ";

	@Override
	public boolean creaBloccoDiAppunti(String username, String nomeBlocco, List<Etichetta> etichette) {
		// parameter check
		if (username == null)
			throw new NullPointerException("username is null");
		if (nomeBlocco == null)
			throw new NullPointerException("nomeBlocco is null");
		if (etichette == null)
			throw new NullPointerException("etichette is null");

		// declaration and initialization
		boolean result = false;
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

		// retrieving utenteRegistrato by username
		Session session = sessionFactory.openSession();
		Transaction transaction=session.beginTransaction();
		Query query = session.createSQLQuery(RETRIEVE_UTENTE_REGISTRATO_BY_USERNAME).addEntity(UtenteRegistrato.class);
		query.setParameter(0, username);
		List<UtenteRegistrato> retrieved = query.list();
		if (retrieved.size() == 1) {
			UtenteRegistrato u = retrieved.get(0);
			// creating bloccoDiAppunti
			result = u.creaBloccoDiAppunti(nomeBlocco, etichette);
			if(result)
			{
				// persisting bloccoDiAppunti
				session.persist(u.getBloccoDiAppunti(nomeBlocco).get());
				session.persist(u);
			}
			

		}
		// releasing resources
		transaction.commit();
		session.close();

		// return
		return result;
	}

	@Override
	public List<BloccoDiAppunti> elencoBlocchiDiAppunti(String username) {
		// parameter check
		if (username == null)
			throw new NullPointerException("username is null");

		// declaration and initialization
		List<BloccoDiAppunti> result = new ArrayList<>();
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

		// retrieving utenteRegistrato by username
		Session session = sessionFactory.openSession();
		Transaction transaction=session.beginTransaction();
		Query query = session.createSQLQuery(RETRIEVE_UTENTE_REGISTRATO_BY_USERNAME).addEntity(UtenteRegistrato.class);
		query.setParameter(0, username);
		List<UtenteRegistrato> retrieved = query.list();
		if (retrieved.size() == 1) {
			UtenteRegistrato u = retrieved.get(0);
			// retrieving blocchiDiAppunti
			result = u.getBlocchiDiAppunti();
		}
		// releasing resources
		transaction.commit();
		session.close();

		// return
		return result;
	}

	@Override
	public boolean eliminaBloccoDiAppunti(ServletContext context, String username, String nomeBlocco) {
		// parameter check
		if (context == null)
			throw new NullPointerException("context is null");
		if (username == null)
			throw new NullPointerException("username is null");
		if (nomeBlocco == null)
			throw new NullPointerException("nomeBlocco is null");

		// declaration and initialization
		boolean result = false;
		ControllerFile controllerFile = FileSystemUtil.getControllerFile() ;
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

			// deleting notes inside bloccoDiAppunti
			if (b.isPresent()) {
				result=u.eliminaBloccoDiAppunti(nomeBlocco);
				if(result)
				{
					 b.get().getNote().stream().forEach(n -> {
						 controllerFile.elimina(context, n.getPercorsoFile());
					 });
					session.delete(b.get());
				}				
			}

		}
		// releasing resources
		transaction.commit();
		session.close();
		// return
		return result;
	}

}
