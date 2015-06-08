package Missiles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import Ensemble.Maillon;
import Principal.Affichable;
import Principal.ComposableElementsGraphiques;
import Principal.GereComposable;
import Principal.Position;
import Principal.Sol;
import Tanks.EcouteurTir;

/**
 * Objet qui a pour vocation d’être lancé afin de détruire l’ennemi.
 *
 */
public class Missile extends GereComposable implements Affichable {
	
	private Sol sol;
	private Position position;
	private double angle;
	private double vitesse;
	
	private double gravite = 3;
	
	/**
	 * 
	 * @param composable
	 * @param sol
	 * @param position
	 * @param angle
	 * @param vitesse
	 * @param ecouteurTir
	 */
	public Missile(ComposableElementsGraphiques composable, Sol sol, Position position, double angle, double vitesse, EcouteurTir ecouteurTir) {
		super(composable);
		this.sol = sol;
		this.position = position;
		this.angle = angle;
		this.vitesse = vitesse;
		
		marquerInvalide();
		
		Thread thread = new Thread() {
			
			double xVitesse = vitesse*Math.cos(angle);
			double yVitesse = vitesse*Math.sin(angle);
			final long pas = 20; // Nombre de millisecondes entre chaque cran.
			
			double x = position.recX(), y = position.recY();
			
			public void run() {
				while(sol.estLibre(Missile.this.position)) {
					x += xVitesse/pas;
					y += yVitesse/pas;
					Missile.this.position = new Position((int)x, (int)y);
					
					yVitesse += gravite;
					composable.marquerInvalide();
					
					try {
						sleep(pas);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				supprimerComposable();
				
				sol.exploser(new Position((int)x, (int)y), 50);
				marquerInvalide();
				ecouteurTir.tirTermine();
			}
		};
		
		thread.start();
		
	}

	@Override
	public void afficher(Graphics g) {
		g.fillOval(position.recX(), position.recY(), 5, 5);
	}
}
