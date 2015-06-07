package Principal;

import Ensemble.Ensemble;
import Ensemble.Maillon;

public interface ComposableElementsGraphiques {
	/**
	 * Ajoute un élément affichable pour qu’il soit affiché chaque fois que nécessaire.
	 * @param element
	 * @return Un maillon à renseigner dans la méthode de suppression pour retirer l’élément.
	 */
	public Maillon<Affichable> ajouterElementGraphique(Affichable element);
	
	/**
	 * Retire l’élément pour qu’il ne s’affiche plus.
	 * @param maillonElement Maillon fourni en retour de l’ajout.
	 */
	public void supprimerElementGraphique(Maillon<Affichable> maillonElement);
	
	public Ensemble<Affichable> recEnsembleDesElementsGraphiques();
	
	/**
	 * Indique que l’élément graphique est à redessiner.
	 */
	public void marquerInvalide();
}
