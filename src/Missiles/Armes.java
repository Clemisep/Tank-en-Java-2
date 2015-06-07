package Missiles;

import java.awt.Dimension;
import java.awt.Graphics;

import Automatitem.RepaintListener;
import Principal.Position;
import Tanks.CanonBougeable;

/**
 * Gestion des canons, munitions et leur affichage.
 *
 */
public class Armes implements CanonBougeable {
	private Canon[] canons;
	private Canon canonActuel;
	private RepaintListener repaintListener;
	
	public Armes(RepaintListener repaintListener, Canon[] canons) {
		this.repaintListener = repaintListener;
	}
	
	public void repaint() {
		repaintListener.repaint();
	}
	
	/**
	 * Affiche le menu de sélection d'un canon.
	 * @param g Contexte graphique (tout est dessiné en prenant (0,0) comme le coin supérieur gauche).
	 * @param dimension Dimensions du menu.
	 */
	public void afficherMenu(Graphics g, Dimension dimension) {
		// TODO Afficher le menu de sélection pour changer de canon.
	}
	
	/**
	 * Affiche le canon du tank.
	 * @param g Contexte graphique.
	 * @param position Position du point d'accroche sur la carte.
	 */
	public void afficherCanon(Graphics g, Position position) {
		g.translate(position.recX(), position.recY());
		canonActuel.afficherCanon(g);
		g.translate(-position.recX(), -position.recY());
	}

	@Override
	public long bougerCanon(int sens) {
		long temps = canonActuel.bougerCanon(sens);
		repaint();
		return temps;
	}
}
