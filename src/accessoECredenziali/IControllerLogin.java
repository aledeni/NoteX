package accessoECredenziali;

import dominio.TipoUtente;

public interface IControllerLogin {

	public boolean autentica(TipoUtente tipoUtente, String username, String password);
	
}
