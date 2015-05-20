package Principal;

import java.awt.Dimension;
import java.awt.Graphics;

import Ensemble.Ensemble;
import Ensemble.Maillon;

public class PanneauJeu extends PanneauBase {
	private static final long serialVersionUID = 1L;

	public PanneauJeu(Dimension taille, Tank[] tanks, Sol sol) {
		super(taille);
		
		ensembleDesElementsGraphiques = new Ensemble<Affichable>();
		
		ensembleDesElementsGraphiques.ajouter(sol);
		
		for(Affichable tank: tanks) {
			ensembleDesElementsGraphiques.ajouter(tank);
		}
	}
	
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
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for(Affichable element: ensembleDesElementsGraphiques) {
			element.afficher(g);
		}
	}
	
	private Ensemble<Affichable> ensembleDesElementsGraphiques;
}
