package Principal;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				int keyCode = e.getKeyCode();
				
				if(keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_RIGHT) {
					if(threadDeplacement == null) {
						int sensDeDeplacement = (keyCode == KeyEvent.VK_LEFT) ? -1 : 1;
						threadDeplacement = new ThreadDeplacement(tankEnCours, sensDeDeplacement);
						threadDeplacement.start();
					}
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				int keyCode = e.getKeyCode();
				
				if(threadDeplacement != null && (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_RIGHT)) {
					threadDeplacement.interrupt();
					threadDeplacement = null;
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {}
			
		});
	}
	
	/**
	 * Ajoute au panneau un élément graphique à afficher.
	 * @param element L’élément à ajouter pour l’affichage.
	 * @return Maillon qui est à donner en argument lors de la suppression.
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
