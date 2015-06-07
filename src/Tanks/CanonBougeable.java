package Tanks;

/**
 * Élément dont on peut faire pivoter le canon.
 *
 */
public interface CanonBougeable {
	/**
	 * Bouge le canon pour viser.
	 * @param sens Sens de rotation : 1, vers le bas, -1, vers le haut.
	 * @return Temps demandé pour réaliser l'action en ms.
	 */
	public long bougerCanon(int sens);
}
