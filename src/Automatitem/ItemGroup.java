package Automatitem;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import Ensemble.Ensemble;
import Ensemble.Maillon;

public class ItemGroup implements KeyListener, MouseListener, MouseMotionListener {
	
	private int xCoord = 0, yCoord = 0;
	private Ensemble<Item> items = new Ensemble<Item>();
	private Maillon<Item> maillonSelectedItem = null;
	private RepaintListener repaintListener;
	
	public void changeRepaintListener(RepaintListener repaintListener) {
		this.repaintListener = repaintListener;
	}
	
	private void repaint() {
		if(repaintListener != null)
			repaintListener.repaint();
	}

	/**
	 * Modifie les coordonnées du coin supérieur gauche 
	 * @param xCoord
	 * @param yCoord
	 * @return
	 */
	public void setCoords(int xCoord, int yCoord) {
		this.xCoord = xCoord;
		this.yCoord = yCoord;
		repaint();
	}
	
	public int getXCoord() {
		return xCoord;
	}
	
	public int getYCoord() {
		return yCoord;
	}
	
	public Maillon<Item> addItem(Item item) {
		Maillon<Item> maillonItem = items.ajouter(item);
		repaint();
		return maillonItem;
	}
	
	public void delItem(Maillon<Item> maillonItem) {
		items.supprimer(maillonItem);
		repaint();
	}
	
	/**
	 * Dessine tous les items du groupe.
	 * @param g
	 */
	public void paintComponent(Graphics g) {
		g.translate(xCoord, yCoord);
		
		for(Item item: items)
			item.paintComponent(g);
		
		g.translate(-xCoord, -yCoord);
	}
	
	
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int keyCode = e.getKeyCode();
		
		if(keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_UP) {
			if(maillonSelectedItem == null) {
				selectItem(items.recMaillonDernier());
			} else if(maillonSelectedItem.recPrecedent() != null) {
				selectItem(maillonSelectedItem.recPrecedent());
			}
			
		} else if(keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_DOWN) {
			if(maillonSelectedItem == null) {
				selectItem(items.recMaillonPremier());
			} else if(maillonSelectedItem.recSuivant() != null) {
				selectItem(maillonSelectedItem.recSuivant());
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void mouseClicked(MouseEvent mouseEvent) {
		Maillon<Item> maillonOldSelectedItem = maillonSelectedItem;
		if(maillonSelectedItem != null) maillonSelectedItem.recValeur().click();
		unSelectItem();
		mouseMoved(mouseEvent);
		if(maillonSelectedItem != maillonOldSelectedItem)
			repaint();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent mouseEvent) {
		unSelectItem();
		repaint();
	}
	
	private void unSelectItem() {
		if(maillonSelectedItem != null) {
			maillonSelectedItem.recValeur().unHover();
			maillonSelectedItem = null;
		}
	}
	
	private void selectItem(Maillon<Item> maillonItem) {
		unSelectItem();
		maillonSelectedItem = maillonItem;
		maillonItem.recValeur().hover();
		repaint();
	}
	
	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent mouseEvent) {
		int x = mouseEvent.getX() - xCoord;
		int y = mouseEvent.getY() - yCoord;
		
		for(Maillon<Item> maillonItem: items.recIterableMaillon()) {
			Item item = maillonItem.recValeur();
			if(x <= item.getXMax() && x >= item.getXMin() && y <= item.getYMax() && y >= item.getYMin() && item.isHovered(x, y)) {
				unSelectItem();
				selectItem(maillonItem);
				break;
			}	
		}
		repaint();
	}

}
