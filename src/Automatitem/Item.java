package Automatitem;

import java.awt.Graphics;

public interface Item {

	public int getYMax();
	
	public int getXMin();
	
	public void setXMin(int xMin);

	public int getXMax();
	
	public void setXMax(int xMax);

	public int getYMin();
	
	public void setYMin(int yMin);

	public void paintComponent(Graphics g);
	
	/**
	 * Appelée lorsque l’item est survolé ou sélectionné.
	 */
	public void hover();
	
	/**
	 * Méthode à appeler lorsque la souris est dans le rectangle pour vérifier si elle atteint bien l’objet.
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean isHovered(int x, int y);
	
	/**
	 * Appelée lorsque l’item n’est plus survolé ou sélectionné.
	 */
	public void unHover();
	
	/**
	 * Appelée lorsque l’item est actionné.
	 */
	public void click();
	
	public void changeClickListener(ClickListener clickListener);
}
