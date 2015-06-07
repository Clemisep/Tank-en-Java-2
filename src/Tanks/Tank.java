package Tanks;

import java.awt.Graphics;

import Principal.Affichable;
import Principal.Equipe;

public interface Tank extends Affichable, CanonBougeable {
	
	/**
	 * Fait déplacer le tank si possible.
	 * @param sens Sens de déplacement : -1, vers la gauche, 1, vers la droite.
	 * @return Temps demandé pour réaliser l'action en ms.
	 */
	public int deplacer(int sens);
	
	public void tirer(double force);
	
	/**
	 * Bouge le canon pour viser.
	 * @param sens Sens de rotation : 1, vers le bas, -1, vers le haut.
	 * @return Temps demandé pour réaliser l'action en ms.
	 */
	public long bougerCanon(int sens);
	
	public void afficher(Graphics g);
	
	public void frapper(double force);
	
	public Equipe recEquipe();
}
