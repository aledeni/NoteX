package dominio;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.HibernateException;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.usertype.UserType;


public class TipoStatoUtente implements UserType {

    private static final int[] SQL_TYPES = {Types.VARCHAR};

	@Override
	public Object assemble(Serializable arg0, Object arg1) throws HibernateException {
		return arg0;
	}

	@Override
	public Object deepCopy(Object arg0) throws HibernateException {
		return arg0;
	}

	@Override
	public Serializable disassemble(Object arg0) throws HibernateException {
		return (Serializable) arg0;
	}

	@Override
	public boolean equals(Object x, Object y) throws HibernateException {
		if (x == y) {
            return true;
        }
        if (x == null || y == null) {
            return false;
        }
        return x.equals(y);
	}

	@Override
	public int hashCode(Object arg0) throws HibernateException {
		return arg0.hashCode();
	}

	@Override
	public boolean isMutable() {
		return false;
	}

	@Override
	public Object nullSafeGet(ResultSet arg0, String[] arg1, Object arg2) throws HibernateException, SQLException {
		String statusValue = arg0.getString(arg1[0]);
        return arg0.wasNull() ? null : StatoUtente.valueOf(statusValue);
	}

	@Override
	public void nullSafeSet(PreparedStatement arg0, Object arg1, int arg2) throws HibernateException, SQLException {
		if (arg1 == null) {
            arg0.setNull(arg2, Types.VARCHAR);
        } else {
            arg0.setString(arg2, ((StatoUtente) arg1).name());
        }		
	}

	@Override
	public Object replace(Object arg0, Object arg1, Object arg2) throws HibernateException {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public Class returnedClass() {
		return StatoUtente.class;
	}

	@Override
	public int[] sqlTypes() {
		return SQL_TYPES;
	}
    
}
