package accessoECredenziali;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;


import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import dominio.TipoUtente;
import dominio.UtenteRegistrato;
import hibernate.util.HibernateUtil;

public class ControllerLogin implements IControllerLogin {

	private final String RETRIEVE_UTENTE_REGISTRATO_BY_USERNAME = "SELECT * FROM UTENTI_REGISTRATI WHERE Username = ? ";

	@Override
	public boolean autentica(TipoUtente tipoUtente, String username, String password) {
		boolean result = false;

		// validazione input
		if (username.length() > 32 || password.length() < 8 || password.length() > 32 || tipoUtente == null) {
			return false;
		}

		// variabili
		Session session = null;
		Transaction tx = null;
		List<UtenteRegistrato> retrieved = new ArrayList<>();

		// interrogazioni all'RDBMS
		session = HibernateUtil.getSessionFactory().openSession();
		tx = session.beginTransaction();
		if (tipoUtente.equals(TipoUtente.UTENTE_REGISTRATO)) {
			Query query = session.createSQLQuery(RETRIEVE_UTENTE_REGISTRATO_BY_USERNAME)
					.addEntity(UtenteRegistrato.class);
			query.setParameter(0, username);
			retrieved = query.list();
			if (retrieved.size() == 1) {
				UtenteRegistrato u = retrieved.get(0);
				byte[] passwordBytes = password.getBytes(StandardCharsets.ISO_8859_1);
				byte[] saltBytes = u.getSalt().getBytes(StandardCharsets.ISO_8859_1);
				byte[] passwordHashBytes = calcolaHash(passwordBytes, saltBytes, u.getHashAlgorithm());
				String passwordHash = new String(passwordHashBytes, StandardCharsets.ISO_8859_1);
				if (passwordHash.equals(u.getPasswordHash())) {
					result = true;
				}
			}
		}

		tx.commit();
		session.close();
		return result;
	}

	private byte[] calcolaHash(byte[] passwordBytes, byte[] saltBytes, String hashAlgorithm) {
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
