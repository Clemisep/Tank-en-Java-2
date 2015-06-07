package Missiles;

import java.awt.Graphics;

import Principal.ComposableElementsGraphiques;
import Principal.Position;
import Principal.Sol;
import Tanks.CanonBougeable;
import Tanks.EcouteurTir;

public interface Canon extends CanonBougeable {
	/**
	 * Affiche l�ic�ne correspondant au canon.
	 * @param position Position o� afficher l'ic�ne.
	 * @param g
	 */
	public void afficherIcone(Graphics g, Position position);
	
	/**
	 * Affiche le canon.
	 * @param position Position du point d'accroche sur la carte du canon � afficher.
	 * @param g
	 */
	public void afficherCanon(Graphics g, Position position);
	
	/**
	 * Lance un projectile correspondant au canon.
	 * @param sol
	 * @param position Position d�accroche du canon sur la carte.
	 * @param vitesse Vitesse en pixels par seconde.
	 * @param repaintListener
	 * @return Vrai si un projectile a pu �tre lanc�, faux sinon.
	 */
	public boolean tirer(Sol sol, Position position, double vitesse, ComposableElementsGraphiques composable, EcouteurTir ecouteurTir);
}
