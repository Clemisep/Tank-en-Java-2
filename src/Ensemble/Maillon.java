package Ensemble;

public class Maillon<T> {
	public Maillon(T contenu) {
		this.contenu = contenu;
	}
	
	public Maillon<T> attacher(T contenuSuivant) {
		suivant = new Maillon<T>(contenuSuivant);
		suivant.modPrecedent(this);
		return suivant;
	}
	
	private void modPrecedent(Maillon<T> precedent) {
		this.precedent = precedent;
	}
	
	private void modSuivant(Maillon<T> suivant) {
		this.suivant = suivant;
	}
	
	public Maillon<T> recPrecedent() {
		return precedent;
	}
	
	public Maillon<T> recSuivant() {
		return suivant;
	}
	
	public T recValeur() {
		return contenu;
	}
	
	public void supprimer() {
		if(precedent != null) precedent.modSuivant(suivant);
		if(suivant != null) suivant.modPrecedent(precedent);
	}
	
	private T contenu;
	private Maillon<T> precedent, suivant;
}
