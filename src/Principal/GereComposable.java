package Principal;

import Ensemble.Ensemble;
import Ensemble.Maillon;

/**
 * Gère le fait de pouvoir être ajouté en tant qu’élément affichable dans des conteneurs ComposableElementsGraphiques.
 *
 */
public abstract class GereComposable implements Affichable {
	
	private ComposableElementsGraphiques composable;
	private Maillon<Affichable> maillon;
	
	public GereComposable(ComposableElementsGraphiques composable) {
		this.composable = composable;
		maillon = composable.ajouterElementGraphique(this);
		System.out.println("Ajout "+maillon.recValeur());
	}
	
	/**
	 * Marque l’élément comme ayant changé d’aspect auprès du composable.
	 */
	public void marquerInvalide() {
		composable.marquerInvalide();
	}
	
	/**
	 * Retire l’élément du composable.
	 */
	public void supprimerComposable() {
		System.out.println("Suppression "+maillon.recValeur());
		composable.supprimerElementGraphique(maillon);
		maillon = null;
		marquerInvalide();
	}
	
	//TODO enlever (debug)
	public Ensemble<Affichable> recEnsemble() {
		return composable.recEnsembleDesElementsGraphiques();
	}
	
	/**
	 * Retire l’élément du composable actuel pour l’ajouter au nouveau.
	 */
	public void changerComposableElementsGraphiques(ComposableElementsGraphiques composable) {
		if(maillon != null)
			this.composable.supprimerElementGraphique(maillon);
		this.composable = composable;
		maillon = composable.ajouterElementGraphique(this);
	}
	
	public ComposableElementsGraphiques recComposable() {
		return composable;
	}
}
