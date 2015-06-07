package Principal;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import Tanks.Tank;
import Automatitem.RepaintListener;
import Ensemble.Ensemble;
import Ensemble.Maillon;

public class PanneauJeu extends PanneauBase {
	private static final long serialVersionUID = 1L;
	private JFrame fen;
	private Tank tankEnCours;
	
	public PanneauJeu(JFrame fen, Dimension taille, Tank[] tanks, Sol sol) {
		super(taille);
		setFocusable(true);
		requestFocusInWindow();
		
		this.fen = fen;
		
		ensembleDesElementsGraphiques = new Ensemble<Affichable>();
		ensembleDesElementsGraphiques.ajouter(sol);
		
		RepaintListener repaintListener = new RepaintListener() {

			@Override
			public void repaint() {
				PanneauJeu.this.repaint();
			}
			
		};
		
		for(Tank tank: tanks) {
			ensembleDesElementsGraphiques.ajouter(tank);
			tank.changeRepaintListener(repaintListener);
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
				// TODO Interdire le bourrinage permettant d��viter le d�lai d�attente
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
	
	/**
	 * Ajoute au panneau un �l�ment graphique � afficher.
	 * @param element L��l�ment � ajouter pour l�affichage.
	 * @return Maillon qui est � donner en argument lors de la suppression.
	 */
	public Maillon<Affichable> ajouterElementGraphique(Affichable element) {
		return this.ensembleDesElementsGraphiques.ajouter(element);
	}
	
	public void supprimerElementGraphique(Maillon<Affichable> maillonElement) {
		this.ensembleDesElementsGraphiques.supprimer(maillonElement);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for(Affichable element: ensembleDesElementsGraphiques) {
			element.afficher(g);
		}
	}
	
	private Ensemble<Affichable> ensembleDesElementsGraphiques;
}
