package Principal;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.IndexColorModel;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import Tanks.Tank;

public class Sol extends GereComposable implements Affichable {
	
	private final int xTaille, yTaille;
	private BufferedImage image;
	private Color couleurTerrain = new Color(39, 176, 55), couleurPlan = new Color(0,0,0,0);
	
	private final BufferedImage ciel;
	
	private final int minAltitudePointeBord;
	private final int maxAltitudePointeBord;
	private final int minAltitudePointeMilieu;
	private final int maxAltitudePointeMilieu;
	private Tank[] tanks = {};
	
	public Sol(Dimension dimension, ComposableElementsGraphiques composable) {
		
		super(composable);
		
		BufferedImage cielLocal;
		
		try {
			cielLocal = ImageIO.read(new File("src/Images/ciel.png"));
		} catch(IOException e) {
			cielLocal = null;
		}
		
		ciel = cielLocal;
		
		xTaille = dimension.width;
		yTaille = dimension.height;
		
		{
			byte[] R = {(byte)couleurTerrain.getRed(),   (byte)couleurPlan.getRed()},
				   G = {(byte)couleurTerrain.getGreen(), (byte)couleurPlan.getGreen()},
				   B = {(byte)couleurTerrain.getBlue(),  (byte)couleurPlan.getBlue()},
				   A = {(byte)couleurTerrain.getAlpha(), (byte)couleurPlan.getAlpha()};
			image = new BufferedImage(xTaille, yTaille, BufferedImage.TYPE_BYTE_BINARY, new IndexColorModel(2, 2, R, G, B, A));
		}
		
		dessinerRectangle(0, xTaille, 0, yTaille, false);
		
		minAltitudePointeBord = yTaille/6;
		maxAltitudePointeBord = yTaille/3;
		minAltitudePointeMilieu = 2*yTaille/3;
		maxAltitudePointeMilieu = 3*yTaille/4;
		
		Random hasard = new Random();
		
		float y = yTaille/8 + hasard.nextInt(yTaille/16); // Altitude initiale du terrain (tout à gauche).
		
		// On parcourt tout le terrain de gauche à droite
		
		for(int x=0 ; x<xTaille ; ) {
			
			float x0 = x; // Abscisse du début de la montagne
			
			int r = xTaille/16 + hasard.nextInt(xTaille/40); // Rayon de la montagne
			int minAltitude, maxAltitude; // Intervalle acceptable d'altitude pour le point culminant de la montagne
			
			// Définition de cet intervalle (en fonction de si l'absisse de la pointe sera ou non vers le centre de la carte,
			// pour qu'il y ait de plus hautes montagnes au milieu.
			if(estAuCentre(x+r)) {
				minAltitude = minAltitudePointeMilieu;
				maxAltitude = maxAltitudePointeMilieu;
			} else {
				minAltitude = minAltitudePointeBord;
				maxAltitude = maxAltitudePointeBord;
			}
			
			// Hauteur de la montagne (différence d'altitude entre le col et la pointe)
			int h = randInt(hasard, minAltitude-(int)y, (int)maxAltitude-(int)y);
			
			
			float dy = 2f*(float)h/((float)r+1f); // À ajouter à y à chaque étape ; correspond à la pente.
			float ddy = -dy / (float)r; // À ajouter à dy à chaque étape ; correspond à la courbure de la montagne.
			
			while(x-x0 < 2*r && x<xTaille) {
				
				// TODO Différentes hauteurs de cols
				
				dessinerVerticaleTerrain(x, (int)y);
				
				y += dy;
				dy += ddy;
				x++;
			}
		}
	}
	
	public void changeTanks(Tank[] tanks) {
		this.tanks = tanks;
	}
	
	private boolean estAuCentre(int x) {
		return x >= xTaille/3 && x <= xTaille*2/3;
	}
	
	private int randInt(Random hasard, int min, int max) {
		return min + hasard.nextInt(max-min);
	}
	
	private void dessinerVerticaleTerrain(int x, int hauteur) {
		try {
			int rgb = couleurTerrain.getRGB();
			for(int y=yTaille-hauteur ; y<yTaille ; y++)
				image.setRGB(x, y, rgb);
		} catch(java.lang.ArrayIndexOutOfBoundsException e) {
			
		}
	}
	
	private void dessinerRectangle(int xMin, int xMax, int yMin, int yMax, boolean estTerrain) {
		int rgb = (estTerrain ? couleurTerrain : couleurPlan).getRGB();
		for(int x=xMin ; x<xMax ; x++)
			for(int y=yMin ; y<yMax ; y++)
				image.setRGB(x, y, rgb);
	}
	
	/**
	 * Retire un morceau de terrain et le rend libre d’accès.
	 * @param x Abscisse du morceau.
	 * @param y Ordonnée du morceau.
	 */
	private void liberer(int x, int y) {
		if(x>=0 && x<recXTaille() && y>=0 && y<recYTaille())
			image.setRGB(x, y, couleurPlan.getRGB());
	}
	
	public void afficher(Graphics g) {
		
		g.drawImage(ciel, 0, 0, xTaille, yTaille, null);
		
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(image, null, null);
	}
	
	private void repaint() {
		recComposable().marquerInvalide();
	}
	
	/**
	 * Gère automatiquement la destruction du terrain provenant de l’explosion d’un projectile.
	 * @param position Position du projectile.
	 * @param rayon Rayon d'action du projectile.
	 * @param force Force de frappe du projectile.
	 */
	public void exploser(Position position, int rayon, int force) {
		int x0 = position.recX(), y0 = position.recY();
		
		for(int x = x0-rayon ; x<=x0+rayon ; x++) {
			int hauteur = (int) (rayon * Math.sin(Math.acos(((double)(x-x0))/(double)rayon)));
			for(int y = y0-hauteur ; y <= y0+hauteur ; y++)
				liberer(x, y);
		}
		
		for(Tank tank : tanks)
			tank.frapper(position, rayon, force);
		
		repaint();
	}
	
	/**
	 * Regarde si une position (pixel) est libre ou bien prise par le terrain.
	 * @param position
	 * @return Vrai si la position est libre, faux sinon.
	 */
	public boolean estLibre(Position position) {
		return estLibre(position.recX(), position.recY());
	}
	
	/**
	 * Regarde si une position (pixel) est libre ou bien prise par le terrain
	 * @param x Abscisse
	 * @param y Ordonnée
	 * @return Vrai si la position est libre, faux sinon.
	 */
	public boolean estLibre(int x, int y) {
		if(x < 0 || x>=recXTaille() || y<0 || y>=recYTaille())
			return false;
		int RGBLocal = image.getRGB(x, y);
		return RGBLocal == couleurPlan.getRGB();
	}
	
	/**
	 * 
	 * @param x
	 * @return Le morceau de sol le plus haut selon la verticale d’abscisse x.
	 */
	public int recAltitude(int x) {
		int y;
		
		for(y=0 ; y<yTaille ; y++)
			if(!estLibre(new Position(x, y)))
				break;
		return y;
	}
	
	/**
	 * 
	 * @return La largeur de la carte en pixels.
	 */
	public int recXTaille() {
		return xTaille;
	}
	
	/**
	 * 
	 * @return La hauteur de la carte en pixels.
	 */
	public int recYTaille() {
		return yTaille;
	}
}
