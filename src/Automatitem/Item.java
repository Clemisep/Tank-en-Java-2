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
	 * Appel�e lorsque l�item est survol� ou s�lectionn�.
	 */
	public void hover();
	
	/**
	 * M�thode � appeler lorsque la souris est dans le rectangle pour v�rifier si elle atteint bien l�objet.
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean isHovered(int x, int y);
	
	/**
	 * Appel�e lorsque l�item n�est plus survol� ou s�lectionn�.
	 */
	public void unHover();
	
	/**
	 * Appel�e lorsque l�item est actionn�.
	 */
	public void click();
	
	public void changeClickListener(ClickListener clickListener);
}
