package Principal;

import java.awt.Color;
import java.awt.Graphics;

public class ThreadPuissanceTir extends GereComposable implements Runnable {
	private Position position;
	private int larg;
	private int haut;
	private double puissance = 0;
	private final double maxPuissance = 700f;
	private final double pas = 5;
	private Thread thread=null;
	
	/**
	 * 
	 * @param composable Permet d'indiquer quand il faut redessiner.
	 * @param position Position du coin supérieur gauche pour l'affichage.
	 * @param larg Largeur pour l'affichage.
	 * @param haut Hauteur pour l'affichage.
	 */
	public ThreadPuissanceTir(ComposableElementsGraphiques composable, Position position, int larg, int haut) {
		super(composable);
		this.position = position;
		this.larg = larg;
		this.haut = haut;
	}
	
	public void start() {
		(thread = new Thread(this)).start();
	}
	
	public void run() {
		try {
			while(puissance <= maxPuissance) {
				puissance += pas;
				recComposable().marquerInvalide();
				Thread.sleep(15);
			}
		} catch(InterruptedException e) {
			
		}
	}
	
	public void interrupt() {
		thread.interrupt();
	}

	@Override
	public void afficher(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect(position.recX(), position.recY(), (int)(larg*puissance/maxPuissance), haut);
	}
	
	public double recPuissance() {
		return puissance;
	}
}
