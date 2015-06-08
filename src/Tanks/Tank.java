package Tanks;

import java.awt.Graphics;

import Principal.Affichable;
import Principal.Equipe;
import Principal.Position;

public interface Tank extends Affichable, CanonBougeable {
	
	/**
	 * Fait d�placer le tank si possible.
	 * @param sens Sens de d�placement : -1, vers la gauche, 1, vers la droite.
	 * @return Temps demand� pour r�aliser l'action en ms.
	 */
	public int deplacer(int sens);
	
	public void tirer(double force, EcouteurTir ecouteurTir);
	
	/**
	 * Bouge le canon pour viser.
	 * @param sens Sens de rotation : 1, vers le bas, -1, vers le haut.
	 * @return Temps demand� pour r�aliser l'action en ms.
	 */
	public long bougerCanon(int sens);
	
	public void afficher(Graphics g);
	
	/**
	 * Informe le tank qu'un projectile a �clat� pour qu'il regarde s'il est touch�.
	 * @param position Centre de l'explosion.
	 * @param rayon Rayon d'action.
	 * @param force Force de frappe du missile.
	 */
	public void frapper(Position position, int rayon, int force);
	
	public Equipe recEquipe();

	public void canonSuivant();
}
