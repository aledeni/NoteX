package gestioneUtente;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import dominio.BloccoDiAppunti;
import dominio.Etichetta;
import dominio.Nota;
import dominio.UtenteRegistrato;
import hibernate.util.HibernateUtil;

public class ControllerRicerca {
	
	private final String RETRIEVE_UTENTE_REGISTRATO_BY_STR = "SELECT * FROM UTENTI_REGISTRATI WHERE Username LIKE ? ";
	private final String RETRIEVE_BLOCCHI_DI_APPUNTI_BY_STR = "SELECT * FROM BLOCCHI_DI_APPUNTI WHERE nome LIKE ? ";
	private final String RETRIEVE_NOTE_BY_STR = "SELECT * FROM NOTE WHERE nome LIKE ? ";
	
	public List<UtenteRegistrato> ricercaUtentiRegistrati(String stringaRicerca)
	{
		if(stringaRicerca==null)
			throw new NullPointerException("stringaRicerca is null");
		
		// declaration and initialization
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

		// retrieving utenteRegistrato by username
		Session session = sessionFactory.openSession();
		Transaction transaction=session.beginTransaction();
		Query query = session.createSQLQuery(RETRIEVE_UTENTE_REGISTRATO_BY_STR).addEntity(UtenteRegistrato.class);
		stringaRicerca+="%";
		query.setParameter(0, stringaRicerca);
		List<UtenteRegistrato> result = query.list();

		// releasing resources
		transaction.commit();
		session.close();

		// return
		return result;
	}
	
	public List<BloccoDiAppunti> ricercaBlocchiDiAppunti(String stringaRicerca)
	{
		if(stringaRicerca==null)
			throw new NullPointerException("stringaRicerca is null");
		
		// declaration and initialization
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

		// retrieving utenteRegistrato by username
		Session session = sessionFactory.openSession();
		Transaction transaction=session.beginTransaction();
		Query query = session.createSQLQuery(RETRIEVE_BLOCCHI_DI_APPUNTI_BY_STR).addEntity(BloccoDiAppunti.class);
		stringaRicerca+="%";
		query.setParameter(0, stringaRicerca);
		List<BloccoDiAppunti> result = query.list();

		// releasing resources
		transaction.commit();
		session.close();

		// return
		return result;
	}
	
	public List<Nota> ricercaNote(String stringaRicerca)
	{
		if(stringaRicerca==null)
			throw new NullPointerException("stringaRicerca is null");
		
		// declaration and initialization
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

		// retrieving utenteRegistrato by username
		Session session = sessionFactory.openSession();
		Transaction transaction=session.beginTransaction();
		Query query = session.createSQLQuery(RETRIEVE_NOTE_BY_STR).addEntity(Nota.class);
		stringaRicerca+="%";
		query.setParameter(0, stringaRicerca);
		List<Nota> result = query.list();

		// releasing resources
		transaction.commit();
		session.close();

		// return
		return result;
	}
	

}
