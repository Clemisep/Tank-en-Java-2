package Principal;

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
		composable.ajouterElementGraphique(this);
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
		this.composable.supprimerElementGraphique(maillon);
		maillon = null;
		marquerInvalide();
	}
	
	/**
	 * Retire l’élément du composable actiel pour l’ajouter au nouveau.
	 */
	public void changerComposableElementsGraphiques(ComposableElementsGraphiques composable) {
		if(maillon != null)
			this.composable.supprimerElementGraphique(maillon);
		this.composable = composable;
		composable.ajouterElementGraphique(this);
	}
}
