package Principal;

import java.awt.Dimension;
import java.awt.Graphics;

public class PanneauFin extends PanneauBase {

	private static final long serialVersionUID = 1L;
	private String texte;
	private Dimension taille;
	
	public PanneauFin(Dimension taille, String texte) {
		super(taille);
		this.texte = texte;
		this.taille = taille;
	}
	
	public void paintComponent(Graphics g) {
		g.drawString(texte, taille.width/2, taille.height/2);
	}
}
