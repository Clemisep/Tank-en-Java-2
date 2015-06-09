package Tanks;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Ensemble.Ensemble;
import Missiles.Armes;
import Principal.ComposableElementsGraphiques;
import Principal.Equipe;
import Principal.GereComposable;
import Principal.Position;
import Principal.Sol;

public class TankBasic extends GereComposable implements Tank {
	
	public static final String[] tableauLienTank = {"src/Images/tank1.png", "src/Images/tank2.png"};
	public static final int[] tableauAbscisseMilieuAppui = {38, 45};
	public static final Position[] tableauAccrocheTank = {new Position(61, 45), new Position(17, 45)};
	private static final int vieParDefaut = 100;
	
	private Position position;
	private int vie;
	private Equipe equipe;
	private final BufferedImage imageTank;
	private boolean focalise = false;
	
	private final int abscisseMilieuAppui;
	private final Sol sol;
	
	private Position accrocheTank;
	
	private Ensemble<Position> ensembleContour;
	
	private Armes armes;
	
	/**
	 * 
	 * @param equipe Équipe dans laquelle se trouve le joueur contrôlant le tank.
	 * @param position Position du tank sur la carte.
	 * @param vie Barre de vie initiale.
	 * @param imageTank Image du tank à afficher sur la carte.
	 * @param abscisseMilieuAppui Abscisse du milieu du tank dans sa propre image.
	 * @param accrocheTank Point d’accroche entre le tank et le canon dans l’image du tank.
	 * @param composable Utilisé pour ajouter ou supprimer des éléments graphiques à afficher.
	 * @param armes Armes présentes sur le tank (canons et munitions).
	 */
	public TankBasic(Sol sol, Equipe equipe, int abscisse, int vie, BufferedImage imageTank, int abscisseMilieuAppui,
			Position accrocheTank, ComposableElementsGraphiques composable, Armes armes) {
		
		// ---------- INITIALISATIONS ------------------
		
		super(composable);
		this.equipe = equipe;
		this.vie = vie;
		this.imageTank = imageTank;
		this.abscisseMilieuAppui = abscisseMilieuAppui;
		this.sol = sol;
		
		this.accrocheTank = accrocheTank;
		this.armes = armes;
		
		
		// ----------- CALCUL DU CONTOUR -----------------
		
		int x,y=0;
		
		externe:
		for(x=0 ; x<imageTank.getWidth() ; x++)
			for(y=0 ; y<imageTank.getHeight() ; y++)
				if(appartientAuTank(x, y))
					break externe;
		
		Position premier = new Position(x, y);
		ensembleContour = new Ensemble<Position>();
		ensembleContour.ajouter(premier);
		
		int x0 = x, y0 = y;
		int dx = 0, dy = -1; // direction initiale vers le haut correspondant à une case vide c’est-à-dire ne faisant pas partie du tank
		
		
		int n=0;
		
		do {
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
			
			n++;
			
		} while(x != x0 || y != y0 || n<4);
		
		
		// ----------- PLACEMENT DU TANK -----------------
		
		placer(abscisse);
	}
	
	public TankBasic(Sol sol, Equipe equipe, int abscisse, int numeroTank, ComposableElementsGraphiques composable, Armes armes) throws IOException {
		this(sol, equipe, abscisse, vieParDefaut, ImageIO.read(new File(tableauLienTank[numeroTank])), tableauAbscisseMilieuAppui[numeroTank], tableauAccrocheTank[numeroTank], composable, armes);
	}
	
	/**
	 * Permet de savoir si le point de coordonnées (x,y) appartient à la matière du tank.
	 * @param x
	 * @param y
	 * @return Vrai si le point y appartient, faux sinon.
	 */
	private boolean appartientAuTank(int x, int y) {
		
		// Si l’on est en dehors de l’image, l’on n’est pas dans le tank
		if(x<0 || x>=imageTank.getWidth() || y<0 || y>=imageTank.getHeight())
			return false;
		
		Color couleur =  new Color(imageTank.getRGB(x, y), true); // Extraction de la couleur avec transparence
		int transparence = couleur.getAlpha(); // Extraction de la transparence
		return transparence == 255; // Renvoie vrai si la couleur est opaque
	}
	
	
	/**
	 * Teste une position du tank pour voir si elle est possible.
	 * @param position Position sur la carte à tester, correspondant au coin supérieur gauche de l'image.
	 * @return Vrai si la position est possible, faux sinon.
	 */
	private boolean positionValide(Position position) {
		
		for(Position p : ensembleContour)
			if(!sol.estLibre(position.recX()+p.recX(), position.recY()+p.recY()))
				return false;
		return true;
	}
	
	
	public void placer(int abscisseMilieu) {
		int x = abscisseMilieu - abscisseMilieuAppui;
		int y=0;
		
		// On descend le tank tant que la position est valide
		for(y=0 ; y<sol.recYTaille() && positionValide(new Position(x, y)) ; y++);
		
		position = new Position(x, y);
		
		marquerInvalide();
		
		// TODO Optimiser en commençant la recherche plus bas.
	}
	
	public void placer(Position position) {
		this.position = position;
		marquerInvalide();
	}
	
	public void positionDescendre(Position position) {
		int x = position.recX(), y = position.recY();
		while(positionValide(new Position(x, y+1))) y++;
		placer(new Position(x, y));
	}
	
	public void descendre() {
		positionDescendre(position);
	}
	
	@Override
	public int deplacer(int sens) {
		
		int x = position.recX() + sens;
		int y0 = position.recY();
		
		for(int y=y0 ; y>y0-4 ; y--) {
			if(positionValide(new Position(x, y))) {
				positionDescendre(new Position(x, y));
				return (int)(8*Math.exp(y0-y));
			}
		}
		return 200;
	}

	@Override
	public void tirer(double force, EcouteurTir ecouteurTir, double vent) {
		armes.recCanonActuel().tirer(sol, vent, new Position(accrocheTank.recX()+position.recX(), accrocheTank.recY()+position.recY()), force, recComposable(), ecouteurTir);
	}
	
	@Override
	public long bougerCanon(int sens) {
		return armes.recCanonActuel().bougerCanon(sens);
	}

	@Override
	public void afficher(Graphics g) {
		// Affichage du tank
		g.drawImage(imageTank, position.recX(), position.recY(), null);
		
		// Affichage du canon
		armes.recCanonActuel().afficherCanon(g, new Position(position.recX()+accrocheTank.recX(), position.recY()+accrocheTank.recY()));
		
		// Affichage de la barre de vie.
		g.setColor(Color.ORANGE);
		int milieu = position.recX()+abscisseMilieuAppui;
		g.fillRect(milieu-vie/2, position.recY()-10, vie, 5);
		
		// Affichage du nombre de munitions.
		g.setColor(Color.RED);
		int nombreMunitions = armes.recCanonActuel().recNombreMunitions();
		
		int largeur = 10;
		int ecart = 4;
		int hauteur = 8;
		
		if(nombreMunitions >= 0) {
			int largeurTotale = (largeur+ecart)*nombreMunitions;
			
			for(int x=0 ; x<nombreMunitions ; x++) {
				g.fillRect(milieu - largeurTotale/2 + x*(largeur+ecart), position.recY() - 15 - hauteur, largeur, hauteur);
			}
		} else {
			int largeurTotale = (ecart+largeur)*5;
			g.fillRect(milieu - largeurTotale/2, position.recY() - 15 - hauteur, largeurTotale, hauteur);
		}
		
		// Si le tank activé est celui-ci, on affiche des pointillés en dessous.
		if(focalise) {
			g.setColor(Color.BLACK);
			int y = position.recY()+imageTank.getWidth();
			for(int i=-imageTank.getWidth()/2 ; i<imageTank.getWidth()/2 ; i+=5)
				g.fillRect(milieu+i, y, 3, 3);
		}
	}

	@Override
	public void frapper(Position c, int rayon, int force) {
		if(vie != 0) {
			for(Position p : ensembleContour) {
				int dx = position.recX() + p.recX() - c.recX();
				int dy = position.recY() + p.recY() - c.recY();
				if(dx*dx+dy*dy <= rayon*rayon) {
					vie = Math.max(0, vie-force);
					if(vie == 0) {
						TankBasic.this.supprimerComposable();
						equipe.perdreTank();
					} else {
						descendre();
					}
					marquerInvalide();
					return;
				}
			}
		}
	}
	
	public boolean estVivant() {
		return vie > 0;
	}

	@Override
	public Equipe recEquipe() {
		return equipe;
	}
	
	@Override
	public void canonSuivant() {
		armes.canonSuivant();
		marquerInvalide();
	}
	
	@Override
	public void focaliser() {
		focalise = true;
		marquerInvalide();
	}
	
	@Override
	public void defocaliser() {
		focalise = false;
		marquerInvalide();
	}
	
	@Override
	public boolean resteMunitions() {
		return armes.recCanonActuel().recNombreMunitions() != 0;
	}

}
