package gestioneUtenteRegistrato;

import java.util.Hashtable;
import java.util.List;

import javax.naming.NamingException;
import javax.naming.spi.InitialContextFactory;
import javax.naming.spi.InitialContextFactoryBuilder;
import javax.servlet.ServletContext;
import javax.servlet.http.Part;

import dominio.BloccoDiAppunti;
import dominio.Etichetta;
import dominio.Nota;

public class ControllerUtenteRegistrato implements IControllerBloccoDiAppunti, IControllerNota,
		IControllerUtenteRegistrato {
	
	private IControllerNota controllerNota;
	private IControllerBloccoDiAppunti controllerBloccoDiAppunti;
	
	

	public ControllerUtenteRegistrato() {
		super();
		this.controllerNota = new ControllerNota();
		this.controllerBloccoDiAppunti = new ControllerBloccoDiAppunti();
	}

	@Override
	public boolean modificaPassword(String username, String oldPassword, String newPassword) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean creaNota(ServletContext context, String username, String nomeBlocco, String nomeNota,
			List<Etichetta> etichette, Part file) {
		return this.controllerNota.creaNota(context, username, nomeBlocco, nomeNota, etichette, file);
	}

	@Override
	public List<Nota> elencoNote(String username, String nomeBlocco) {
		return this.controllerNota.elencoNote(username, nomeBlocco);
	}

	@Override
	public boolean eliminaNota(ServletContext context, String username, String nomeBlocco, String nomeNota) {
		return this.controllerNota.eliminaNota(context, username, nomeBlocco, nomeNota);
	}

	@Override
	public boolean creaBloccoDiAppunti(String username, String nomeBlocco, List<Etichetta> etichette) {
		return this.controllerBloccoDiAppunti.creaBloccoDiAppunti(username, nomeBlocco, etichette);
	}

	@Override
	public List<BloccoDiAppunti> elencoBlocchiDiAppunti(String username) {
		return this.controllerBloccoDiAppunti.elencoBlocchiDiAppunti(username);
	}

	@Override
	public boolean eliminaBloccoDiAppunti(ServletContext context, String username, String nomeBlocco) {
		return this.controllerBloccoDiAppunti.eliminaBloccoDiAppunti(context, username, nomeBlocco);
	}

}
