package Principal;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import Tanks.EcouteurTir;
import Tanks.Tank;

public class PanneauJeu extends PanneauBase implements ComposableElementsGraphiques {
	private static final long serialVersionUID = 1L;
	private JFrame fen;
	private Tank[] tanks;
	private Tank tankEnCours;
	private int indiceTankEnCours = 0;
	
	private final int hauteurRuban = 50; // hauteur du panneau inférieur pour les divers affichages
	private final int largeurTir = 150; // largeur du panneau de sélection de la force de tir (panneau tout à droite)
	private final int largeurVent = 150; // largeur du panneau affichant la force du vent (2e à partir de la droite)
	
	public PanneauJeu(JFrame fen, Dimension taille, Tank[] tanks, Sol sol) {
		super(taille);
		setFocusable(true);
		requestFocusInWindow();
		
		this.fen = fen;
		ajouterElementGraphique(sol);
		
		this.tanks = tanks;
		
		for(Tank tank: tanks) {
			ajouterElementGraphique(tank);
			tank.changerComposableElementsGraphiques(this);
		}
		
		tankEnCours = tanks[0];
		
		addKeyListener(new KeyListener() {
			
			private ThreadDeplacement threadDeplacement;
			private ThreadBougerCanon threadBougerCanon;
			private ThreadPuissanceTir threadPuissanceTir;
			
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
						threadBougerCanon = new ThreadBougerCanon(tankEnCours, sens, PanneauJeu.this);
						threadBougerCanon.start();
					}
				} else if(keyCode == KeyEvent.VK_SPACE) {
					if(threadPuissanceTir == null) {
						threadPuissanceTir = new ThreadPuissanceTir(
								PanneauJeu.this,
								new Position(getWidth()-largeurTir, getHeight()-hauteurRuban),
								largeurTir,
								hauteurRuban
						);
						
						threadPuissanceTir.start();
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
				} else if(threadPuissanceTir != null && keyCode == KeyEvent.VK_SPACE) {
					
					threadPuissanceTir.interrupt();
					double puissance = threadPuissanceTir.recPuissance();
					
					tankEnCours.tirer(puissance, new EcouteurTir() {
						// Quand on a fini de tirer, on efface la barre de tir.
						@Override
						public void tirTermine() {
							threadPuissanceTir.supprimerComposable();
							threadPuissanceTir = null;
							joueurSuivant();
						}
						
					});
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {}
			
		});
	}
	
	private void joueurSuivant() {
		if(++indiceTankEnCours >= tanks.length)
			indiceTankEnCours = 0;
		tankEnCours = tanks[indiceTankEnCours];
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
