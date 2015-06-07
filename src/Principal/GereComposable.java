package Principal;

import Ensemble.Maillon;

/**
 * G�re le fait de pouvoir �tre ajout� en tant qu��l�ment affichable dans des conteneurs ComposableElementsGraphiques.
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
	 * Marque l��l�ment comme ayant chang� d�aspect aupr�s du composable.
	 */
	public void marquerInvalide() {
		composable.marquerInvalide();
	}
	
	/**
	 * Retire l��l�ment du composable.
	 */
	public void supprimerComposable() {
		this.composable.supprimerElementGraphique(maillon);
		maillon = null;
		marquerInvalide();
	}
	
	/**
	 * Retire l��l�ment du composable actiel pour l�ajouter au nouveau.
	 */
	public void changerComposableElementsGraphiques(ComposableElementsGraphiques composable) {
		if(maillon != null)
			this.composable.supprimerElementGraphique(maillon);
		this.composable = composable;
		composable.ajouterElementGraphique(this);
	}
}
