package Principal;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.IndexColorModel;
import java.util.Random;

public class Sol implements Affichable {
	
	private final int xTaille, yTaille;
	private BufferedImage image;
	private Color couleurTerrain = Color.GREEN, couleurPlan = Color.BLUE;
	
	private final int minAltitudePointeBord;
	private final int maxAltitudePointeBord;
	private final int minAltitudePointeMilieu;
	private final int maxAltitudePointeMilieu;
	private final int minAltitudeColBord;
	private final int maxAltitudeColBord;
	private final int minAltitudeColMilieu;
	private final int maxAltitudeColMilieu;
	
	//private final int minAltitudePointeBord;
	
	public Sol(Dimension dimension) {
		
		xTaille = dimension.width;
		yTaille = dimension.height;
		
		{
			byte[] R = {(byte)couleurTerrain.getRed(),   (byte)couleurPlan.getRed()},
				   G = {(byte)couleurTerrain.getGreen(), (byte)couleurPlan.getGreen()},
				   B = {(byte)couleurTerrain.getBlue(),  (byte)couleurPlan.getBlue()};
			image = new BufferedImage(xTaille, yTaille, BufferedImage.TYPE_BYTE_BINARY, new IndexColorModel(2, 2, R, G, B));
		}
		
		dessinerRectangle(0, xTaille, 0, yTaille, false);
		
		minAltitudePointeBord = yTaille/6;
		maxAltitudePointeBord = yTaille/3;
		minAltitudePointeMilieu = 2*yTaille/3;
		maxAltitudePointeMilieu = 3*yTaille/4;
		minAltitudeColBord = yTaille/10;
		maxAltitudeColBord = yTaille/8;
		minAltitudeColMilieu = yTaille/2;
		maxAltitudeColMilieu = 3*yTaille/5;
		
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
			
			// TODO Supprimer ce debug.
			System.out.println("x "+x+", y "+y+ ", R "+r+", h "+h+", dy "+dy+", ddy "+ddy);
			
			while(x-x0 < 2*r && x<xTaille) {
				
				// TODO Différentes hauteurs de cols
				
				dessinerVerticaleTerrain(x, (int)y);
				
				y += dy;
				dy += ddy;
				x++;
			}
		}
	}
	
	private boolean estAuCentre(int x) {
		return x >= xTaille/3 && x <= xTaille*2/3;
	}
	
	private int recMinAltitudeCol(int x) {
		return estAuCentre(x) ? minAltitudeColMilieu : minAltitudeColBord;
	}
	
	private int recMaxAltitudeCol(int x) {
		return estAuCentre(x) ? maxAltitudeColMilieu : maxAltitudeColBord;
	}
	
	private int randInt(Random hasard, int min, int max) {
		return min + hasard.nextInt(max-min);
	}
	
	private void dessinerVerticaleTerrain(int x, int hauteur) {
		//System.out.println("Verticale " + x + ", " + hauteur);
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
		image.setRGB(x, y, couleurPlan.getRGB());
	}
	
	public void afficher(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(image, null, null);
	}
	
	/**
	 * Gère automatiquement la destruction du terrain provenant de l’explosion d’un projectile.
	 * @param position Position du projectile.
	 * @param force Puissance du projectile.
	 */
	public void exploser(Position position, int force) {
		int x0 = position.recX(), y0 = position.recY();
		
		for(int x = x0-force ; x<=x0+force ; x++) {
			int hauteur = (int) (force * Math.sin(Math.acos(((double)(x-x0))/(double)force)));
			for(int y = y0-hauteur ; y <= y0+hauteur ; y++)
				liberer(x, y);
		}
		
		// TODO Mettre ici le processus de destruction du sol
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
