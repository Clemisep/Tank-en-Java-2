package Principal;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Tanks.EcouteurTir;
import Tanks.Tank;

public class PanneauJeu extends PanneauBase implements ComposableElementsGraphiques {
	private static final long serialVersionUID = 1L;
	private FenetreJeu fen;
	private Dimension taille;
	private Tank[] tanks;
	private Equipe[] equipes;
	private Tank tankEnCours;
	private int indiceTankEnCours = 0;
	private boolean aTire = false; // indique si le joueur a tiré (ou commencé à) pendant le tour actuel
	
	public static final int hauteurRuban = 50; // hauteur du panneau inférieur pour les divers affichages
	private final int largeurTir = 300; // largeur du panneau de sélection de la force de tir (panneau tout à droite)
	private final double largeurVent = 1; // largeur du panneau affichant la force du vent (2e à partir de la droite)
	private final double pasVent = 0.2;
	private double vent = 0;
	
	public PanneauJeu(FenetreJeu fen, Dimension taille, Tank[] tanks, Equipe[] equipes, Sol sol) {
		super(taille);
		setFocusable(true);
		requestFocusInWindow();
		
		this.fen = fen;
		this.taille = taille;
		ajouterElementGraphique(sol);
		
		ajouterElementGraphique(new Affichable() {

			@Override
			public void afficher(Graphics g) {
				try {
					BufferedImage fleche;
					if(vent > 0)
						fleche = ImageIO.read(new File("src/Images/flecheDroite.png"));
					else
						fleche = ImageIO.read(new File("src/Images/flecheGauche.png"));
					
					for(int i=0 ; i<(int)(Math.abs(vent)/pasVent) ; i++)
						g.drawImage(fleche, fleche.getWidth()*i, getHeight() - hauteurRuban, null);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void changerComposableElementsGraphiques(ComposableElementsGraphiques composable) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		this.tanks = tanks;
		this.equipes = equipes;
		
		changerVent();
		
		for(Tank tank: tanks) {
			tank.changerComposableElementsGraphiques(this);
		}
		
		tankEnCours = tanks[0];
		tankEnCours.focaliser();
		
		addKeyListener(new KeyListener() {
			
			private ThreadDeplacement threadDeplacement = null;
			private ThreadBougerCanon threadBougerCanon = null;
			private ThreadPuissanceTir threadPuissanceTir = null;
			
			@Override
			public void keyPressed(KeyEvent e) {
				int keyCode = e.getKeyCode();
				
				if(!aTire && threadPuissanceTir == null) {
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
					} else if(keyCode == KeyEvent.VK_SPACE && tankEnCours.resteMunitions()) {
						
						if(threadDeplacement != null) {
							threadDeplacement.interrupt();
							threadDeplacement = null;
						}
						if(threadBougerCanon != null) {
							threadBougerCanon.interrupt();
							threadBougerCanon = null;
						}
						
						threadPuissanceTir = new ThreadPuissanceTir(
								PanneauJeu.this,
								new Position(getWidth()-largeurTir, getHeight()-hauteurRuban),
								largeurTir,
								hauteurRuban
								);
						
						threadPuissanceTir.start();
						
					} else if(keyCode == KeyEvent.VK_ENTER) {
						System.out.println("Appui entrée");
						tankEnCours.canonSuivant();
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
				} else if(threadPuissanceTir != null && keyCode == KeyEvent.VK_SPACE && !aTire && tankEnCours.resteMunitions()) {
					
					threadPuissanceTir.interrupt();
					aTire = true;
					double puissance = threadPuissanceTir.recPuissance();
					tankEnCours.tirer(
							puissance,
							new EcouteurTir() {
								// Quand on a fini de tirer, on efface la barre de tir.
								@Override
								public void tirTermine() {
									threadPuissanceTir.supprimerComposable();
									threadPuissanceTir = null;
									joueurSuivant();
								}	
							},
							vent
							);
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {}
			
		});
	}
	
	private void changerVent() {
		vent = (int)((-largeurVent + Math.random()*largeurVent*2)/pasVent)*pasVent;
		marquerInvalide();
	}
	
	private void joueurSuivant() {
		
		for(Equipe e : equipes) {
			if(e.recNombreDeTanks() == 0)
				fen.remplacerPar(new PanneauFin(taille, "Équipe perdante : "+e.recNomEquipe()));
		}
		
		changerVent();
		
		tankEnCours.defocaliser();
		
		// Passe au tank vivant suivant
		do {
			if(++indiceTankEnCours >= tanks.length)
				indiceTankEnCours = 0;
			tankEnCours = tanks[indiceTankEnCours];
		} while(!tankEnCours.estVivant());
		
		tankEnCours.focaliser();
		aTire = false;
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
