package Principal;

import java.awt.Component;
import java.awt.Container;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class FenetreJeu extends JFrame {
	
	public FenetreJeu() {
		setLocation(0, 0);
		setSize(Toolkit.getDefaultToolkit().getScreenSize());
		
		getContentPane().add(new PanneauMenu(getSize(), this));
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void remplacerPar(JPanel panneau) {
		Container contenu = getContentPane();
		for(Component component: contenu.getComponents())
			contenu.remove(component);
		contenu.add(panneau);
		contenu.validate();
	}
	
	private static final long serialVersionUID = 1L;
}
