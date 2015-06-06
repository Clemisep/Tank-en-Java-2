package Automatitem;

import java.awt.Color;
import java.awt.Graphics;

public class ItemText extends ItemBasic {
	
	private String text;
	private Color textColor = Color.BLACK, backColor = Color.LIGHT_GRAY, hoverTextColor = Color.WHITE, hoverBackColor = Color.DARK_GRAY;
	
	public ItemText(int xMin, int xMax, int yMin, int yMax, String text) {
		super(xMin, xMax, yMin, yMax);
		this.text = text;
	}
	
	public void paintComponent(Graphics g) {
		
		g.setColor(isSelected() ? hoverBackColor : backColor);
		g.fillRect(getXMin(), getYMin(), getXMax()-getXMin(), getYMax()-getYMin());
		g.setColor(isSelected() ? hoverTextColor : textColor);
		g.drawString(text, getXMin()+5, (getYMin()+getYMax())/2);
	}
}
