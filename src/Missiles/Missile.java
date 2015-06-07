package Missiles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import Ensemble.Maillon;
import Principal.Affichable;
import Principal.ComposableElementsGraphiques;
import Principal.GereComposable;
import Principal.Position;
import Principal.Sol;

/**
 * Objet qui a pour vocation d’être lancé afin de détruire l’ennemi.
 *
 */
public class Missile extends GereComposable implements Affichable {
	
	private Sol sol;
	private Position position;
	private int angle;
	private double vitesse;
	
	private Maillon<Affichable> maillon;
	private BufferedImage image;
	
	public Missile(ComposableElementsGraphiques composable, Sol sol, Position position, int angle, double vitesse, BufferedImage image) {
		super(composable);
		this.sol = sol;
		this.position = position;
		this.angle = angle;
		this.vitesse = vitesse;
		this.image = image;
		
		composable.ajouterElementGraphique(this);
		marquerInvalide();
	}
	
	public void disparaitre() {
		supprimerComposable();
	}

	@Override
	public void afficher(Graphics g) {
		// TODO Auto-generated method stub
		
	}
}
