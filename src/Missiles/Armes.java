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
	private int indiceCanonActuel;
	
	public Armes(ComposableElementsGraphiques composable, Canon[] canons) {
		super(composable);
		this.canons = canons;
		indiceCanonActuel = 0;
	}
	
	/**
	 * Affiche le menu de s�lection d'un canon.
	 * @param g Contexte graphique (tout est dessin� en prenant (0,0) comme le coin sup�rieur gauche).
	 * @param dimension Dimensions du menu.
	 */
	public void afficherMenu(Graphics g, Dimension dimension) {
		// TODO Afficher le menu de s�lection pour changer de canon.
	}
	
	public Canon recCanonActuel() {
		return canons[indiceCanonActuel];
	}
	
	public void canonSuivant() {
		if(++indiceCanonActuel >= canons.length)
			indiceCanonActuel = 0;
	}

	@Override
	public void afficher(Graphics g) {
		// TODO Auto-generated method stub
	}
}
