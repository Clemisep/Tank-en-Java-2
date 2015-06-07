package Principal;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Automatitem.ClickListener;
import Automatitem.ItemGroup;
import Automatitem.ItemText;
import Automatitem.RepaintListener;
import Missiles.Armes;
import Missiles.Canon;
import Missiles.CanonBasic;
import Tanks.Tank;
import Tanks.TankBasic;

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
		
		sol = new Sol(taille, this);
		equipe1 = new Equipe(1);
		equipe2 = new Equipe(1);
		
		try {
			Canon canons1[] = {new CanonBasic(ImageIO.read(new File(CanonBasic.tableauLienCanon[0])),
								ImageIO.read(new File(CanonBasic.tableauLienCanon[0])),
								CanonBasic.tableauAccrocheCanon[0], -1)};
			Armes armes1 = new Armes(this, canons1);
			
			Tank tank1 = new TankBasic(sol, equipe1, 40, 100, ImageIO.read(new File(TankBasic.tableauLienTank[0])), TankBasic.tableauAbscisseMilieuAppui[0],
					TankBasic.tableauAccrocheTank[0], this, armes1);
			
			Canon canons2[] = {new CanonBasic(ImageIO.read(new File(CanonBasic.tableauLienCanon[0])),
					ImageIO.read(new File(CanonBasic.tableauLienCanon[0])),
					CanonBasic.tableauAccrocheCanon[0], -1)};
			Armes armes2 = new Armes(this, canons1);
			
			Tank tank2 = new TankBasic(sol, equipe1, taille.width-40, 100, ImageIO.read(new File(TankBasic.tableauLienTank[0])), TankBasic.tableauAbscisseMilieuAppui[0],
					TankBasic.tableauAccrocheTank[0], this, armes1);
			
			Tank[] tanks = {tank1, tank2};
			
			this.tanks = tanks;
			
			ItemText itemJouer = new ItemText(0, 100, 0, 30, "Jouer");
			itemJouer.changeClickListener(new ClickListener() {
	
				@Override
				public void click() {
					// TODO Remettre les instructions correctes
					fen.remplacerPar(new PanneauJeu(fen, taille, tanks, sol));
					//JFrame fen = new JFrame();
					//fen.getContentPane().add(new PanneauJeu(fen, taille, tanks, sol));
					//fen.setVisible(true);
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
