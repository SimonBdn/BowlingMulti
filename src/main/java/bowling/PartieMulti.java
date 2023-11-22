package bowling;

import java.util.TreeMap;
import java.util.Map;

public class PartieMulti implements IPartieMultiJoueurs{
	TreeMap<String, PartieMonoJoueur> partieMonoJoueurs;
	

	@Override
	public String demarreNouvellePartie(String[] nomsDesJoueurs) throws IllegalArgumentException{
		partieMonoJoueurs = new TreeMap<String, PartieMonoJoueur>();
		if (nomsDesJoueurs.length == 0) {
			throw new IllegalArgumentException("Pas de joueur dans la partie");
		}
		for (String s : nomsDesJoueurs) {
			partieMonoJoueurs.put(s, new PartieMonoJoueur());
		}

		return this.getProchainLanceEtBoulePour(partieMonoJoueurs.firstKey());
	}

	@Override
	public String enregistreLancer(int nombreDeQuillesAbattues) throws IllegalStateException {
		if (partieMonoJoueurs == null) {
			throw new IllegalArgumentException("La partie n'est pas démarrée");
		}
		if (lesPartiesSontTermine()) {
			return "Partie terminée";
		}
		partieMonoJoueurs.get(this.getProchainJoueur()).enregistreLancer(nombreDeQuillesAbattues);
		return getProchainLanceEtBoulePour(getProchainJoueur());
	}

	@Override
	public int scorePour(String nomDuJoueur) throws IllegalArgumentException {
		if (partieMonoJoueurs.get(nomDuJoueur) == null) {
			throw new IllegalArgumentException("Le joueur n'existe pas");
		}
		return partieMonoJoueurs.get(nomDuJoueur).score();
	}
	
	public String getProchainJoueur() {
		String nb = partieMonoJoueurs.firstKey();
		for (Map.Entry<String, PartieMonoJoueur> joueurEntry : partieMonoJoueurs.entrySet()) {
			if (joueurEntry.getValue().numeroTourCourant() < partieMonoJoueurs.get(nb).numeroTourCourant()) {
				nb = joueurEntry.getKey();
			}
		}
		return nb;
	}

	public String getProchainLanceEtBoulePour(String s) {
		return "Prochain tir : joueur " + s
			+ ", tour n° " + partieMonoJoueurs.get(s).numeroTourCourant()
			+ ", boule n° " + partieMonoJoueurs.get(s).numeroProchainLancer();
	}

	public Boolean lesPartiesSontTermine() {
		for (Map.Entry<String, PartieMonoJoueur> joueurEntry : partieMonoJoueurs.entrySet()) {
			if (joueurEntry.getValue().estTerminee()) {
				return true;
			}
		}
		return false;
	}
}
