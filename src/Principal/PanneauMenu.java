package Principal;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Automatitem.ItemBasic;
import Automatitem.ItemGroup;
import Automatitem.ItemText;
import Automatitem.RepaintListener;

public class PanneauMenu extends PanneauBase implements RepaintListener {
	
	private static final long serialVersionUID = 1L;
	
	private ItemGroup itemGroup;

	public PanneauMenu(Dimension taille) {
		super(taille);
		setFocusable(true);
		requestFocusInWindow();
		
		itemGroup = new ItemGroup();
		
		addKeyListener(itemGroup);
		addMouseListener(itemGroup);
		addMouseMotionListener(itemGroup);
		
		String textes[] = {"Jouer", "Paramètres", "Quitter"};
		
		for(int i=0 ; i<textes.length ; i++)
			itemGroup.addItem(new ItemText(0, 100, 40*i, 40*i+30, textes[i]));
		
		itemGroup.setCoords(30, 20);
		
		itemGroup.changeRepaintListener(this);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.BLUE);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		itemGroup.paintComponent(g);
	}
}
