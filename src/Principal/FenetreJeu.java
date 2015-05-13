package Principal;

import java.awt.Toolkit;

import javax.swing.JFrame;

public class FenetreJeu extends JFrame {
	
	public FenetreJeu() {
		setLocation(0, 0);
		setSize(Toolkit.getDefaultToolkit().getScreenSize());
		
		getContentPane().add(new PanneauMenu(getSize()));
	}
	
	private static final long serialVersionUID = 1L;
}
