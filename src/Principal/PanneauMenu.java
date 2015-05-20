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
		
		itemGroup.addItem(new ItemBasic(5,10,5,10));
		itemGroup.addItem(new ItemBasic(20,40,30,40));
		itemGroup.addItem(new ItemBasic(70,90,100,130));
		
		itemGroup.changeRepaintListener(this);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.BLUE);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		itemGroup.paintComponent(g);
	}
}
