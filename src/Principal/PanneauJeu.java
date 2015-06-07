package Principal;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import Tanks.Tank;

public class PanneauJeu extends PanneauBase implements ComposableElementsGraphiques {
	private static final long serialVersionUID = 1L;
	private JFrame fen;
	private Tank tankEnCours;
	
	public PanneauJeu(JFrame fen, Dimension taille, Tank[] tanks, Sol sol) {
		super(taille);
		setFocusable(true);
		requestFocusInWindow();
		
		this.fen = fen;
		ajouterElementGraphique(sol);
		
		for(Tank tank: tanks) {
			ajouterElementGraphique(tank);
			tank.changerComposableElementsGraphiques(this);
		}
		
		tankEnCours = tanks[0];
		
		addKeyListener(new KeyListener() {
			
			private ThreadDeplacement threadDeplacement;
			private ThreadBougerCanon threadBougerCanon;
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Ajouter les touches haut et bas pour le canon
				int keyCode = e.getKeyCode();
				
				if(keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_RIGHT) {
					if(threadDeplacement == null) {
						int sensDeDeplacement = (keyCode == KeyEvent.VK_LEFT) ? -1 : 1;
						threadDeplacement = new ThreadDeplacement(tankEnCours, sensDeDeplacement);
						threadDeplacement.start();
					}
				} else if(keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_DOWN) {
					if(threadBougerCanon == null) {
						int sens = (keyCode == KeyEvent.VK_UP) ? -1 : 1;
						threadBougerCanon = new ThreadBougerCanon(tankEnCours, sens);
						threadBougerCanon.start();
					}
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Interdire le bourrinage permettant d’éviter le délai d’attente
				int keyCode = e.getKeyCode();
				
				if(threadDeplacement != null && (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_RIGHT)) {
					threadDeplacement.interrupt();
					threadDeplacement = null;
				} else if(threadBougerCanon != null && (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_DOWN)) {
					threadBougerCanon.interrupt();
					threadBougerCanon = null;
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {}
			
		});
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for(Affichable element: recEnsembleDesElementsGraphiques()) {
			element.afficher(g);
		}
	}

	@Override
	public void marquerInvalide() {
		repaint();
		// TODO Ne pas repeindre systématiquement mais à un certain intervalle minimal.
	}
}
