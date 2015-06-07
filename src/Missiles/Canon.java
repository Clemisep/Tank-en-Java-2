package Missiles;

import java.awt.Graphics;

import Automatitem.RepaintListener;
import Principal.Position;
import Principal.Sol;
import Tanks.CanonBougeable;

public interface Canon extends CanonBougeable {
	/**
	 * Affiche en (0,0) l’icône correspondant au canon.
	 * @param g
	 */
	public void afficherIcone(Graphics g);
	
	/**
	 * Affiche en (0,0) le canon.
	 * @param g
	 */
	public void afficherCanon(Graphics g);
	
	/**
	 * Lance un projectile correspondant au canon.
	 * @param sol
	 * @param position Position d’accroche du canon sur la carte.
	 * @param vitesse
	 * @param repaintListener
	 * @return Vrai si un projectile a pu être lancé, faux sinon.
	 */
	public boolean tirer(Sol sol, Position position, double vitesse, RepaintListener repaintListener);
}
