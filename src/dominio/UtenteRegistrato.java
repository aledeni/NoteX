package dominio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UtenteRegistrato extends Account implements Serializable {

	private StatoUtente stato;
	private List<Segnalazione> segnalazioni;
	private List<BloccoDiAppunti> blocchiDiAppunti;
	private double punteggioMedio;

	public UtenteRegistrato() {
		super();
		this.stato = StatoUtente.NON_SEGNALATO;
		this.segnalazioni = new ArrayList<>();
		this.blocchiDiAppunti = new ArrayList<>();
		this.punteggioMedio = -1;
	}

	public StatoUtente getStato() {
		return stato;
	}

	public void setStato(StatoUtente stato) {
		this.stato = stato;
	}

	public List<Segnalazione> getSegnalazioni() {
		return segnalazioni;
	}

	public void setSegnalazioni(List<Segnalazione> segnalazioni) {
		this.segnalazioni = segnalazioni;
	}

	public List<BloccoDiAppunti> getBlocchiDiAppunti() {
		return blocchiDiAppunti;
	}

	public Optional<BloccoDiAppunti> getBloccoDiAppunti(String nomeBlocco) {
		return this.blocchiDiAppunti.stream().filter(b -> {
			return b.getNome().equals(nomeBlocco);
		}).findFirst();
	}

	public boolean creaBloccoDiAppunti(String nomeBlocco, List<Etichetta> etichette) {
		boolean result = false;
		if (!this.blocchiDiAppunti.stream().anyMatch(b -> {
			return b.getNome().equals(nomeBlocco);
		})) {
			BloccoDiAppunti b = new BloccoDiAppunti();
			b.setNome(nomeBlocco);
			b.setEtichette(etichette);
			b.setUtenteRegistrato(this);
			this.blocchiDiAppunti.add(b);
			result = true;
		}
		return result;
	}

	public boolean eliminaBloccoDiAppunti(String nomeBlocco) {
		boolean result = false;
		Optional<BloccoDiAppunti> b = this.blocchiDiAppunti.stream().filter(bl -> {
			return bl.getNome().equals(nomeBlocco);
		}).findFirst();
		if (b.isPresent()) {
			result = this.blocchiDiAppunti.remove(b.get());
		}
		return result;
	}

	public void setBlocchiDiAppunti(List<BloccoDiAppunti> blocchiDiAppunti) {
		this.blocchiDiAppunti = blocchiDiAppunti;
	}

	public double getPunteggioMedio() {
		return punteggioMedio;
	}

	public void setPunteggioMedio(double punteggioMedio) {
		this.punteggioMedio = punteggioMedio;
	}

	public List<Segnalazione> getSegnalazioniAttive() {
		List<Segnalazione> attive = new ArrayList<Segnalazione>();
		for (Segnalazione s : segnalazioni) {
			if (s.getStato().equals(StatoSegnalazione.DA_GESTIRE)) {
				attive.add(s);
			}
		}
		return attive;
	}

}
