package dominoFrontend;

import java.util.List;
import java.util.stream.Collectors;

import dominio.Nota;

public class NotaFrontend {
	private String nome;
	private double punteggioMedio;
	private List<String[]> etichette;
	private String percorsoFile;

	public NotaFrontend(Nota nota) {
		super();
		this.nome = nota.getNome();
		this.punteggioMedio = nota.getPunteggioMedio();
		this.etichette = nota.getEtichette().stream().map(e -> {
			String[] et = new String[2];
			et[0] = e.getNome();
			et[1] = e.getValore();
			return et;
		}).collect(Collectors.toList());
		this.percorsoFile = nota.getPercorsoFile();
	}

}
