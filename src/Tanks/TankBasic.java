package Tanks;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

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
	
	private Position position;
	private int vie;
	private Equipe equipe;
	private final BufferedImage imageTank;
	
	private final int abscisseMilieuAppui;
	private final Sol sol;
	
	private Position accrocheTank;
	
	private Ensemble<Position> ensembleContour;
	
	private Armes armes;
	
	/**
	 * 
	 * @param equipe �quipe dans laquelle se trouve le joueur contr�lant le tank.
	 * @param position Position du tank sur la carte.
	 * @param vie Barre de vie initiale.
	 * @param imageTank Image du tank � afficher sur la carte.
	 * @param abscisseMilieuAppui Abscisse du milieu du tank dans sa propre image.
	 * @param accrocheTank Point d�accroche entre le tank et le canon dans l�image du tank.
	 * @param composable Utilis� pour ajouter ou supprimer des �l�ments graphiques � afficher.
	 * @param armes Armes pr�sentes sur le tank (canons et munitions).
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
		int dx = 0, dy = -1; // direction initiale vers le haut correspondant � une case vide c�est-�-dire ne faisant pas partie du tank
		
		
		int n=0;
		
		do {
			int vx = -dy, vy = dx; // direction vers laquelle nous allons � priori
			
			// si elle est vide, elle devient notre nouvelle direction sommet vide
			if(!appartientAuTank(x+vx, y+vy)) {
				dx = vx;
				dy = vy;
			
			// si elle est pleine et qu�il s�agit d�un virage de l�autre sens, on ajoute les deux cases et on fait la rotation
			} else if(appartientAuTank(x+dx+vx, y+dy+vy)) {
				ensembleContour.ajouter(new Position(x+vx, y+vy));
				x = x+dx+vx;
				y = y+dy+vy;
				ensembleContour.ajouter(new Position(x, y));
				dx = -vx;
				dy = -vy;
			
			// si elle est pleine et qu�il s�agit d�un tout droit, nous poursuivons notre route
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
	
	/**
	 * Permet de savoir si le point de coordonn�es (x,y) appartient � la mati�re du tank.
	 * @param x
	 * @param y
	 * @return Vrai si le point y appartient, faux sinon.
	 */
	private boolean appartientAuTank(int x, int y) {
		
		// Si l�on est en dehors de l�image, l�on n�est pas dans le tank
		if(x<0 || x>=imageTank.getWidth() || y<0 || y>=imageTank.getHeight())
			return false;
		
		Color couleur =  new Color(imageTank.getRGB(x, y), true); // Extraction de la couleur avec transparence
		int transparence = couleur.getAlpha(); // Extraction de la transparence
		return transparence == 255; // Renvoie vrai si la couleur est opaque
	}
	
	
	/**
	 * Teste une position du tank pour voir si elle est possible.
	 * @param position Position sur la carte � tester, correspondant au coin sup�rieur gauche de l'image.
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
		
		// TODO Optimiser en commen�ant la recherche plus bas.
	}
	
	public void placer(Position position) {
		this.position = position;
		marquerInvalide();
	}
	
	@Override
	public int deplacer(int sens) {
		
		int x = position.recX() + sens;
		int y0 = position.recY();
		
		for(int y=y0 ; y>y0-4 ; y--) {
			if(positionValide(new Position(x, y))) {
				while(positionValide(new Position(x, y+1))) y++;
				
				placer(new Position(x, y));
				return (int)(8*Math.exp(y0-y));
			}
		}
		return 200;
	}

	@Override
	public void tirer(double force, EcouteurTir ecouteurTir) {
		armes.recCanonActuel().tirer(sol, new Position(accrocheTank.recX()+position.recX(), accrocheTank.recY()+position.recY()), force, recComposable(), ecouteurTir);
	}
	
	@Override
	public long bougerCanon(int sens) {
		return armes.recCanonActuel().bougerCanon(sens);
	}

	@Override
	public void afficher(Graphics g) {
		
		g.drawImage(imageTank, position.recX(), position.recY(), null); // Affichage du tank
		armes.recCanonActuel().afficherCanon(g, new Position(position.recX()+accrocheTank.recX(), position.recY()+accrocheTank.recY())); // Affichage du canon
		
		g.setColor(Color.ORANGE);
		g.fillRect(position.recX()+abscisseMilieuAppui-vie/2, position.recY()-10, vie, 5);
	}

	@Override
	public void frapper(Position c, int rayon) {
		for(Position p : ensembleContour) {
			int dx = position.recX() + p.recX() - c.recX();
			int dy = position.recY() + p.recY() - c.recY();
			if(dx*dx+dy*dy <= rayon*rayon) {
				vie = Math.max(0, vie-30);
				marquerInvalide();
				return;
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

}
