package Principal;


import java.awt.Container;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class FenetreJeu extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	//private CardLayout gestionnaire;
	
	public FenetreJeu() {
		setLocation(0, 0);
		setSize(Toolkit.getDefaultToolkit().getScreenSize());
		getContentPane().add(new PanneauMenu(getSize(), this));
		
		/*
		Container contenu = getContentPane();
		gestionnaire = new CardLayout();
		contenu.setLayout(new CardLayout());
		gestionnaire.addLayoutComponent(new PanneauMenu(getSize(), this), "menu");
		gestionnaire.show(contenu, "menu");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);*/
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
		
		/*gestionnaire.addLayoutComponent(panneau, "jeu");
		gestionnaire.show(getContentPane(), "jeu");*/
	}
	
}
