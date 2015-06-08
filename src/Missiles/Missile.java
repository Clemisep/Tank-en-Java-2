package Missiles;

import java.awt.Graphics;

import Missiles.CanonBasic.Type;
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
	final long pas = 10; // Nombre de millisecondes entre chaque cran.
	
	private Type type;
	
	private double gravite = 3;
	
	/**
	 * 
	 * @param composable
	 * @param sol
	 * @param position
	 * @param angle
	 * @param vitesse
	 * @param ecouteurTir
	 * @param type 
	 */
	public Missile(ComposableElementsGraphiques composable, Sol sol, Position position, double angle, double vitesse, EcouteurTir ecouteurTir, Type type) {
		super(composable);
		this.sol = sol;
		this.position = position;
		this.angle = angle;
		this.vitesse = vitesse;
		this.type = type;
		
		marquerInvalide();
		
		Thread thread = new Thread() {
			
			double xVitesse = vitesse*Math.cos(angle);
			double yVitesse = vitesse*Math.sin(angle);
			
			double x = position.recX(), y = position.recY();
			
			public void run() {
				while(y<0 || sol.estLibre(Missile.this.position)) {
					
					if(yVitesse >= 0 && (Missile.this.type == CanonBasic.Type.BARRAGE || Missile.this.type == CanonBasic.Type.VERTICAL)) {
						
						// Si on est à l'apogée et que c'est un missile barrage, on le divise.
						if(Missile.this.type == CanonBasic.Type.BARRAGE) {
							
							EcouteurTir ecouteurTirInterne = new EcouteurTir() {
								
								private int nombreRestant = 3;
								
								@Override
								public void tirTermine() {
									if(--nombreRestant == 0)
										ecouteurTir.tirTermine();
								}
								
							};
							
							new Missile(composable, sol, Missile.this.position, 0, xVitesse, ecouteurTirInterne, CanonBasic.Type.TIERS);
							new Missile(composable, sol, Missile.this.position, Math.PI/12f, xVitesse, ecouteurTirInterne, CanonBasic.Type.TIERS);
							new Missile(composable, sol, Missile.this.position, Math.PI/6f, xVitesse, ecouteurTirInterne, CanonBasic.Type.TIERS);
							
							supprimerComposable();
							marquerInvalide();
							return;
							
						// Si on est à l'apogée et que c'est un missile vertical, on annule sa vitesse en x.
						} else {
							
							xVitesse = 0;
							Missile.this.type = CanonBasic.Type.VERTICAL_ACTIF;
							
						}
					
					} else {
						
						x += xVitesse*pas/400;
						y += yVitesse*pas/400;
						Missile.this.position = new Position((int)x, (int)y);
						
						yVitesse += gravite;
					}
					
					composable.marquerInvalide();
					
					try {
						sleep(pas);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				supprimerComposable();
				
				sol.exploser(new Position((int)x, (int)y), type.recRayon(), type.recForce());
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
