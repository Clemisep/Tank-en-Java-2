package Principal;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.io.IOException;


import Automatitem.ClickListener;
import Automatitem.ItemGroup;
import Automatitem.ItemText;
import Automatitem.RepaintListener;
import Missiles.Armes;
import Missiles.Canon;
import Missiles.CanonBarrage;
import Missiles.CanonNormal;
import Missiles.CanonVertical;
import Tanks.Tank;
import Tanks.TankBasic;

public class PanneauMenu extends PanneauBase implements RepaintListener {
	
	private static final long serialVersionUID = 1L;
	
	private final int ecartTanks = 70;
	
	private ItemGroup itemGroup;
	private Sol sol;

	public PanneauMenu(Dimension taille, FenetreJeu fen) {
		super(taille);
		setFocusable(true);
		requestFocusInWindow();
		itemGroup = new ItemGroup();
		
		addKeyListener(itemGroup);
		addMouseListener(itemGroup);
		addMouseMotionListener(itemGroup);
		
		sol = new Sol(taille, this);
		Equipe equipe1 = new Equipe(1, "équipe de gauche");
		Equipe equipe2 = new Equipe(1, "équipe de droite");
		Equipe[] equipes = {equipe1, equipe2};
		
		try {
			
			Canon canons1[] = {new CanonNormal(1), new CanonBarrage(1), new CanonVertical(1)};
			Armes armes1 = new Armes(this, canons1);
			
			Tank tank1 = new TankBasic(sol, equipe1, ecartTanks, 0, this, armes1);
			
			Canon canons2[] = {new CanonNormal(-1), new CanonBarrage(-1), new CanonVertical(-1)};
			Armes armes2 = new Armes(this, canons2);
			
			Tank tank2 = new TankBasic(sol, equipe2, getWidth() - ecartTanks, 1, this, armes2);
			
			ItemText itemJouer1 = new ItemText(0, 100, 0, 30, "Un tank par joueur");
			itemJouer1.changeClickListener(new ClickListener() {
				
				@Override
				public void click() {

					Tank[] tanks = {tank1, tank2};
					sol.changeTanks(tanks);
					
					fen.remplacerPar(new PanneauJeu(fen, PanneauMenu.this.getSize(), tanks, equipes, sol));
				}
				
			});
			itemGroup.addItem(itemJouer1);
			
			ItemText itemJouer2 = new ItemText(0, 100, 40, 70, "Deux tanks par joueur");
			itemJouer2.changeClickListener(new ClickListener() {

				@Override
				public void click() {
					
					try {
						Canon canons3[] = {new CanonNormal(1), new CanonBarrage(1), new CanonVertical(1)};
						Armes armes3 = new Armes(PanneauMenu.this, canons3);
						
						Tank tank3 = new TankBasic(sol, equipe1, ecartTanks*2, 0, PanneauMenu.this, armes3);
						
						Canon canons4[] = {new CanonNormal(-1), new CanonBarrage(-1), new CanonVertical(-1)};
						Armes armes4 = new Armes(PanneauMenu.this, canons4);
						
						Tank tank4 = new TankBasic(sol, equipe2, getWidth() - ecartTanks*2, 1, PanneauMenu.this, armes4);
						
						Tank[] tanks = {tank1, tank2, tank3, tank4};
						sol.changeTanks(tanks);
						
						equipe1.modNombreDeTanks(2);
						equipe2.modNombreDeTanks(2);
						
						fen.remplacerPar(new PanneauJeu(fen, PanneauMenu.this.getSize(), tanks, equipes, sol));
						
					} catch(IOException e) {
						e.printStackTrace();
					}
				}
			});
			itemGroup.addItem(itemJouer2);
			
			itemGroup.setCoords(getWidth()/2, 20);
			
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
