package hibernate.util;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	private static SessionFactory sessionFactory = initHibernateUtil();
	private static SessionFactory initHibernateUtil() {
		try {
			return new Configuration().configure().buildSessionFactory();
		} catch (HibernateException e) {
			throw new ExceptionInInitializerError(e);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
}
