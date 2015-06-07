package Principal;

import Ensemble.Ensemble;
import Ensemble.Maillon;

public interface ComposableElementsGraphiques {
	/**
	 * Ajoute un �l�ment affichable pour qu�il soit affich� chaque fois que n�cessaire.
	 * @param element
	 * @return Un maillon � renseigner dans la m�thode de suppression pour retirer l��l�ment.
	 */
	public Maillon<Affichable> ajouterElementGraphique(Affichable element);
	
	/**
	 * Retire l��l�ment pour qu�il ne s�affiche plus.
	 * @param maillonElement Maillon fourni en retour de l�ajout.
	 */
	public void supprimerElementGraphique(Maillon<Affichable> maillonElement);
	
	public Ensemble<Affichable> recEnsembleDesElementsGraphiques();
	
	/**
	 * Indique que l��l�ment graphique est � redessiner.
	 */
	public void marquerInvalide();
}
