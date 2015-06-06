package Principal;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

//import java.io.File;
//import java.io.IOException;
//import javax.imageio.ImageIO;



import java.io.IOException;

import Automatitem.ClickListener;
import Automatitem.ItemGroup;
import Automatitem.ItemText;
import Automatitem.RepaintListener;
import Tanks.Tank1;
import Tanks.Tank;

public class PanneauMenu extends PanneauBase implements RepaintListener {
	
	private static final long serialVersionUID = 1L;
	
	private ItemGroup itemGroup;
	private Sol sol;
	private Equipe equipe1, equipe2;
	private Tank[] tanks;

	public PanneauMenu(Dimension taille, FenetreJeu fen) {
		super(taille);
		setFocusable(true);
		requestFocusInWindow();
		
		itemGroup = new ItemGroup();
		
		addKeyListener(itemGroup);
		addMouseListener(itemGroup);
		addMouseMotionListener(itemGroup);
		
		sol = new Sol(taille);
		equipe1 = new Equipe(1);
		equipe2 = new Equipe(1);
		
		try {
			Tank[] tanks = {new Tank1(sol, equipe1, 40, 100), new Tank1(sol, equipe2, 800, 100)};
			
			this.tanks = tanks;
			
			ItemText itemJouer = new ItemText(0, 100, 0, 30, "Jouer");
			itemJouer.changeClickListener(new ClickListener() {
	
				@Override
				public void click() {
					fen.remplacerPar(new PanneauJeu(fen, taille, tanks, sol));
				}
				
			});
			
			itemGroup.addItem(itemJouer);
			
			String textes[] = {"Jouer", "Paramètres", "Quitter"};
			
			for(int i=1 ; i<textes.length ; i++)
				itemGroup.addItem(new ItemText(0, 100, 40*i, 40*i+30, textes[i]));
			
			itemGroup.setCoords(30, 20);
			
			itemGroup.changeRepaintListener(this);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.BLUE);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		itemGroup.paintComponent(g);
	}
}
