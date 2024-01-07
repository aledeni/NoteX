package dominoFrontend;

import java.util.List;
import java.util.stream.Collectors;

import dominio.BloccoDiAppunti;
import dominio.Nota;

public class BloccoDiAppuntiFrontend {
	private String nome;
	private double punteggioMedio;
	private List<String[]> etichette;
	private List<NotaFrontend> note;
	
	public BloccoDiAppuntiFrontend(BloccoDiAppunti blocco) {
		super();
		this.nome = blocco.getNome();
		this.punteggioMedio = blocco.getPunteggioMedio();
		this.etichette = blocco.getEtichette().stream().map(e -> {
			String[] et = new String[2];
			et[0] = e.getNome();
			et[1] = e.getValore();
			return et;
		}).collect(Collectors.toList());
		this.note = blocco.getNote().stream().map(n->{return new NotaFrontend(n);}).collect(Collectors.toList());
	}

}
