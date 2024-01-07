package gestioneUtenteRegistrato;

import java.util.List;

import javax.servlet.ServletContext;

import dominio.*;

public interface IControllerBloccoDiAppunti {
	public boolean creaBloccoDiAppunti(String username,String nomeBlocco, List<Etichetta> etichette);
	public List<BloccoDiAppunti> elencoBlocchiDiAppunti(String username);
	public boolean eliminaBloccoDiAppunti(ServletContext context, String username, String nomeBlocco);
}
