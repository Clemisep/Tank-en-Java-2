package Automatitem;

import java.awt.Color;
import java.awt.Graphics;

public class ItemBasic implements Item {
	
	private int xMin, xMax, yMin, yMax;
	private boolean selected;
	private ClickListener clickListener;

	/**
	 * Spécifie le plus petit rectangle contenant la forme de l’item.
	 * @param xMin
	 * @param xMax
	 * @param yMin
	 * @param yMax
	 */
	public ItemBasic(int xMin, int xMax, int yMin, int yMax) {
		this.xMin = xMin;
		this.xMax = xMax;
		this.yMin = yMin;
		this.yMax = yMax;
	}
	
	public int getXMin() {
		return xMin;
	}
	
	public void setXMin(int xMin) {
		this.xMin = xMin;
	}

	public int getXMax() {
		return xMax;
	}
	
	public void setXMax(int xMax) {
		this.xMax = xMax;
	}

	public int getYMin() {
		return yMin;
	}
	
	public void setYMin(int yMin) {
		this.yMin = yMin;
	}
	
	public int getYMax() {
		return yMax;
	}

	@Override
	public void paintComponent(Graphics g) {
		if(selected)
			g.setColor(Color.BLACK);
		else
			g.setColor(Color.GRAY);
		
		g.fillRect(xMin, yMin, xMax-xMin, yMax-yMin);
	}

	@Override
	public void hover() {
		selected = true;
	}

	@Override
	public boolean isHovered(int x, int y) {
		return true;
	}

	@Override
	public void unHover() {
		selected = false;
	}
	
	public boolean isSelected() {
		return selected;
	}

	@Override
	public void click() {
		if(clickListener != null)
			clickListener.click();
	}

	@Override
	public void changeClickListener(ClickListener clickListener) {
		this.clickListener = clickListener;
	}
}
