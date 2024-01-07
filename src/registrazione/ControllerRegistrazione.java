package registrazione;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import dominio.StatoUtente;
import dominio.UtenteRegistrato;
import hibernate.util.HibernateUtil;

public class ControllerRegistrazione implements IControllerRegistrazione {
	
	private final String RETRIEVE_UTENTE_REGISTRATO_BY_USERNAME = "SELECT * FROM UTENTI_REGISTRATI WHERE Username = ? ";
	
	@Override
	public boolean registraUtente(String username, String password) {
		if(username==null)
			throw new NullPointerException("username is null");
		if(password==null)
			throw new NullPointerException("password is null");
		
		
		
		boolean result = false;		
		// validazione input
		if(username.length() > 32 || password.length() < 8 || password.length() > 32) {
			return false;
		}
		
		// variabili
		Session session = null;
		Transaction tx = null;
		List<UtenteRegistrato> retrieved = null;
		
		// interrogazioni all'RDBMS
		session = HibernateUtil.getSessionFactory().openSession();
		tx = session.beginTransaction();
		// verifico che l'utente non sia presente nella tabella UTENTI_REGISTRATI
		Query query = session.createSQLQuery(RETRIEVE_UTENTE_REGISTRATO_BY_USERNAME).addEntity(UtenteRegistrato.class);
		query.setParameter(0, username);
		retrieved = query.list();
		if(retrieved.size() == 0) {
			
			UtenteRegistrato u = new UtenteRegistrato();
			u.setUsername(username);
			u.setHashAlgorithm("SHA-256");
			u.setStato(StatoUtente.NON_SEGNALATO);
			setPasswordHashAndSalt(u,password, u.getHashAlgorithm());
			
			session.persist(u);
			result = true;
		}	
		tx.commit();
		session.close();
		return result;
	}
	
	private void setPasswordHashAndSalt(UtenteRegistrato user,String password, String hashAlgorithm) {
		SecureRandom random = new SecureRandom();
		
		byte[] saltBytes = new byte[16];
		random.nextBytes(saltBytes);

		byte[] passwordBytes = password.getBytes(StandardCharsets.ISO_8859_1);

		byte[] passwordHashBytes = calcolaHash(passwordBytes,saltBytes,hashAlgorithm);

		String passwordHash = new String(passwordHashBytes,StandardCharsets.ISO_8859_1);
		String salt = new String(saltBytes,StandardCharsets.ISO_8859_1);
		user.setPasswordHash(passwordHash);
		user.setSalt(salt);
 	}
	
	private byte[] calcolaHash(byte[] passwordBytes, byte[] saltBytes, String hashAlgorithm)
	{

		byte[] saltedPasswordBytes = new byte[saltBytes.length + passwordBytes.length];
		System.arraycopy(saltBytes, 0, saltedPasswordBytes, 0, saltBytes.length);
		System.arraycopy(passwordBytes, 0, saltedPasswordBytes, saltBytes.length, passwordBytes.length);

		MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance(hashAlgorithm);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		byte[] passwordHashBytes = digest.digest(saltedPasswordBytes);
		return passwordHashBytes;
	}
	
}
