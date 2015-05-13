package Principal;

import java.awt.Dimension;

import javax.swing.JPanel;

public class PanneauBase extends JPanel {

	public PanneauBase(Dimension taille) {
		setSize((int)taille.getWidth(), (int)taille.getHeight());
	}
	
	
	private static final long serialVersionUID = 1L;
}
