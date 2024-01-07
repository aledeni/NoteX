package dominoFrontend;

import java.util.List;
import java.util.stream.Collectors;

import dominio.UtenteRegistrato;

public class UtenteFrontend {
	private String username;
	private List<BloccoDiAppuntiFrontend> blocchiDiAppunti;
	
	public UtenteFrontend(UtenteRegistrato u)
	{
		this.username = u.getUsername();
		this.blocchiDiAppunti = u.getBlocchiDiAppunti().stream().map(b->{return new BloccoDiAppuntiFrontend(b);}).collect(Collectors.toList());
	}

}
