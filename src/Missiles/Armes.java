package Missiles;

import java.awt.Dimension;
import java.awt.Graphics;

import Principal.ComposableElementsGraphiques;
import Principal.GereComposable;

/**
 * Gestion des canons, munitions et leur affichage.
 *
 */
public class Armes extends GereComposable {
	private Canon[] canons;
	private Canon canonActuel;
	
	public Armes(ComposableElementsGraphiques composable, Canon[] canons) {
		super(composable);
		this.canons = canons;
		canonActuel = canons[0];
	}
	
	/**
	 * Affiche le menu de sélection d'un canon.
	 * @param g Contexte graphique (tout est dessiné en prenant (0,0) comme le coin supérieur gauche).
	 * @param dimension Dimensions du menu.
	 */
	public void afficherMenu(Graphics g, Dimension dimension) {
		// TODO Afficher le menu de sélection pour changer de canon.
	}
	
	public Canon recCanonActuel() {
		return canonActuel;
	}

	@Override
	public void afficher(Graphics g) {
		// TODO Auto-generated method stub
	}
}
