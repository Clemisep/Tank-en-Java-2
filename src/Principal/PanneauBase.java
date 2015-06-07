package Principal;

import java.awt.Dimension;
import javax.swing.JPanel;

import Ensemble.Ensemble;
import Ensemble.Maillon;

public class PanneauBase extends JPanel implements ComposableElementsGraphiques {
	
	private Ensemble<Affichable> ensembleDesElementsGraphiques;
	
	public PanneauBase(Dimension taille) {
		setSize((int)taille.getWidth(), (int)taille.getHeight());
		
		ensembleDesElementsGraphiques = new Ensemble<Affichable>();
	}
	
	
	private static final long serialVersionUID = 1L;

	/**
	 * Ajoute au panneau un élément graphique à afficher.
	 * @param element L’élément à ajouter pour l’affichage.
	 * @return Maillon qui est à donner en argument lors de la suppression.
	 */
	public Maillon<Affichable> ajouterElementGraphique(Affichable element) {
		return this.ensembleDesElementsGraphiques.ajouter(element);
	}
	
	public void supprimerElementGraphique(Maillon<Affichable> maillonElement) {
		this.ensembleDesElementsGraphiques.supprimer(maillonElement);
	}
	
	public Ensemble<Affichable> recEnsembleDesElementsGraphiques() {
		return ensembleDesElementsGraphiques;
	}

	@Override
	public void marquerInvalide() {
		// TODO Auto-generated method stub
		
	}
}
