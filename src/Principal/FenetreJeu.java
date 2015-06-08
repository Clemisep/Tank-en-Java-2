package Principal;


import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class FenetreJeu extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	public FenetreJeu() {
		// Place et maximise la fenêtre
		Toolkit kit =  Toolkit.getDefaultToolkit(); 
		Insets insets = kit.getScreenInsets(getGraphicsConfiguration()); 
		Dimension screen = kit.getScreenSize(); 
		
		int w = (int)(screen.getWidth()-insets.left-insets.right); 
		int h = (int)(screen.getHeight()-insets.top-insets.bottom); 
		int x = (int)(insets.left); 
		int y = (int)(insets.top); 
		Dimension dimension = new Dimension(w,h); 
		setSize(dimension); 
		setLocation(x,y);
		
		// Ajout du panneau
		Container contenu = getContentPane();
		contenu.add(new PanneauMenu(dimension, this));
	}
	
	/**
	 * Enlève tous les composants de la fenêtre pour les remplacer par un panneau.
	 * @param panneau Le panneau remplaçant tous les composants.
	 */
	public void remplacerPar(JPanel panneau) {
		Container contenu = getContentPane();
		contenu.removeAll();
		contenu.add(panneau);
		contenu.validate();
		panneau.requestFocusInWindow();
		repaint();
	}
	
}
