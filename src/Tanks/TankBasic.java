package Tanks;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Automatitem.RepaintListener;
import Ensemble.Ensemble;
import Principal.Equipe;
import Principal.Position;
import Principal.Sol;

public abstract class TankBasic implements Tank {
	
	private Position position;
	private int vie;
	private Equipe equipe;
	private BufferedImage image;
	
	private final Position coordInfGauche, coordInfDroit, milieuAppui;
	private Sol sol;
	private RepaintListener repaintListener;
	
	Ensemble<Position> ensembleContour;
	
	/**
	 * 
	 * @param equipe Équipe dans laquelle se trouve le joueur contrôlant le tank.
	 * @param position Position du tank sur la carte.
	 * @param vie Barre de vie initiale.
	 * @param image Image du tank à afficher sur la carte.
	 * @param coordInfGauche Premier point d’appui du tank (coïncide avec sa position sur la carte).
	 * @param coordInfDroit Deuxième point d’appui du tank.
	 */
	public TankBasic(Sol sol, Equipe equipe, int abscisse, int vie, BufferedImage image, Position coordInfGauche, Position coordInfDroit, RepaintListener repaintListener) {
		
		// ---------- INITIALISATIONS ------------------
		
		this.equipe = equipe;
		/**
		 * Position du tank correspondant au coin supérieur gauche de l'image sur la carte.
		 */
		this.vie = vie;
		this.image = image;
		this.coordInfGauche = coordInfGauche;
		this.coordInfDroit = coordInfDroit;
		this.milieuAppui = new Position((coordInfGauche.recX()+coordInfDroit.recX())/2, (coordInfGauche.recY()+coordInfDroit.recY())/2);
		this.sol = sol;
		this.repaintListener = repaintListener;
		
		
		// ----------- CALCUL DU CONTOUR -----------------
		// TODO
		int x,y=0;
		
		externe:
		for(x=0 ; x<image.getWidth() ; x++)
			for(y=0 ; y<image.getHeight() ; y++)
				if(appartientAuTank(x, y))
					break externe;
		
		Position premier = new Position(x, y);
		ensembleContour = new Ensemble<Position>();
		ensembleContour.ajouter(premier);
		
		int x0 = x, y0 = y;
		int dx = 0, dy = -1; // direction initiale vers le haut correspondant à une case vide c’est-à-dire ne faisant pas partie du tank
		
		/*
		 * DEBUG
		 * 
		JFrame fen = new JFrame() {
			private static final long serialVersionUID = 1L;
			private JPanel panneau = new JPanel() {
				public void paintComponent(Graphics g) {
					for(Position p : ensembleContour)
						g.fillRect(p.recX(), p.recY(), 1, 1);
				}
			};
			
			{
				getContentPane().add(panneau);
			}
		};
		
		fen.setVisible(true);*/
		
		int n=0;
		
		do {
			System.out.println("Contour en "+x+","+y+" vers "+dx+","+dy);
			int vx = -dy, vy = dx; // direction vers laquelle nous allons à priori
			
			// si elle est vide, elle devient notre nouvelle direction sommet vide
			if(!appartientAuTank(x+vx, y+vy)) {
				dx = vx;
				dy = vy;
			
			// si elle est pleine et qu’il s’agit d’un virage de l’autre sens, on ajoute les deux cases et on fait la rotation
			} else if(appartientAuTank(x+dx+vx, y+dy+vy)) {
				ensembleContour.ajouter(new Position(x+vx, y+vy));
				x = x+dx+vx;
				y = y+dy+vy;
				ensembleContour.ajouter(new Position(x, y));
				dx = -vx;
				dy = -vy;
			
			// si elle est pleine et qu’il s’agit d’un tout droit, nous poursuivons notre route
			} else {
				x = x+dx;
				y = y+dy;
				ensembleContour.ajouter(new Position(x, y));
			}
			
			//fen.repaint();
			
			n++;
			
		} while(x != x0 || y != y0 || n<4);
		
		
		// ----------- PLACEMENT DU TANK -----------------
		
		placer(abscisse);
	}
	
	public void changeRepaintListener(RepaintListener repaintListener) {
		this.repaintListener = repaintListener;
	}
	
	
	private void redessiner() {
		repaintListener.repaint();
	}
	
	/**
	 * Permet de savoir si le point de coordonnées (x,y) appartient à la matière du tank.
	 * @param x
	 * @param y
	 * @return Vrai si le point y appartient, faux sinon.
	 */
	private boolean appartientAuTank(int x, int y) {
		
		// Si l’on est en dehors de l’image, l’on n’est pas dans le tank
		if(x<0 || x>=image.getWidth() || y<0 || y>=image.getHeight())
			return false;
		
		Color couleur =  new Color(image.getRGB(x, y), true); // Extraction de la couleur avec transparence
		int transparence = couleur.getAlpha(); // Extraction de la transparence
		return transparence == 255; // Renvoie vrai si la couleur est opaque
	}
	
	
	/**
	 * Teste une position du tank pour voir si elle est possible.
	 * @param position Position sur la carte à tester, correspondant au coin supérieur gauche de l'image.
	 * @return Vrai si la position est possible, faux sinon.
	 */
	private boolean positionValide(Position position) {
		
		// TODO
		
		int x0 = position.recX(), y0 = position.recY();
		/*
		// On regarde tous les points du tank déplacé sur la position et on voit s'il vont dans le décors.
		for(int x=0 ; x<image.getWidth() ; x++)
			for(int y=0 ; y<image.getHeight() ; y++) {
				
				// S'il y a du décors à cet endroit
				if(!sol.estLibre(x0+x, y0+y)) {
					
					
					// S'il y a en plus un morceau de tank, il y a un conflit donc on renvoie une réponse négative.
					if(appartientAuTank(x, y)) {
						System.out.println("positionValide() dit : ça coince en "+(x0+x)+","+(y0+y)+" ; "+x+","+y);
						return false;
					}
				}
			}*/
		
		for(Position p : ensembleContour)
			if(!sol.estLibre(position.recX()+p.recX(), position.recY()+p.recY()))
				return false;
		return true;
	}
	
	
	public void placer(int abscisseMilieu) {
		int x = abscisseMilieu - milieuAppui.recX();
		int y=0;
		
		// On descend le tank tant que la position est valide
		for(y=0 ; y<sol.recYTaille() && positionValide(new Position(x, y)) ; y++);
		
		position = new Position(x, y);
		
		redessiner();
		
		// TODO Optimiser en commençant la recherche plus bas.
	}
	
	public void placer(Position position) {
		this.position = position;
		redessiner();
	}
	
	@Override
	public int deplacer(int sens) {
		
		int x = position.recX() + sens;
		
		for(int y=0 ; y>-15 ; y--) {
			if(positionValide(new Position(x, y))) {
				while(positionValide(new Position(x, y+1))) y++;
				
				int y0 = position.recY();
				
				placer(new Position(x, y));
				
				return 0; //10*Math.abs(y-y0);
			}
		}
		
		// TODO Auto-generated method stub
		return 200;
	}

	@Override
	public void tirer(double force) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public int bougerCanon(int sens) {
		// TODO Auto-generated method stub
		return 10;
	}

	@Override
	public void afficher(Graphics g) {
		g.drawImage(image, position.recX(), position.recY(), null);
	}

	@Override
	public void frapper(double force) {
		vie -= force;
	}
	
	public boolean estVivant() {
		return vie > 0;
	}

	@Override
	public Equipe recEquipe() {
		return equipe;
	}

}
