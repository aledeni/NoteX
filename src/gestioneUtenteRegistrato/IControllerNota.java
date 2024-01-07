package gestioneUtenteRegistrato;

import java.io.File;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.Part;

import dominio.Etichetta;
import dominio.Nota;

public interface IControllerNota {
	public boolean creaNota(ServletContext context,String username, String nomeBlocco, String nomeNota, List<Etichetta> etichette, Part file);
	public List<Nota> elencoNote(String username, String nomeBlocco);
	public boolean eliminaNota(ServletContext context,String username, String nomeBlocco, String nomeNota);

}
