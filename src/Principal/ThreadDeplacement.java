package Principal;

import Tanks.Tank;

public class ThreadDeplacement extends Thread {
	
	private Tank tank;
	private int sensDeplacement;
	
	/**
	 * 
	 * @param tank Le tank à essayer de déplacer.
	 * @param sensDeplacement 1 pour se déplacer à droite, -1 pour se déplacer à gauche.
	 */
	public ThreadDeplacement(Tank tank, int sensDeplacement) {
		this.tank = tank;
		this.sensDeplacement = sensDeplacement;
	}
	
	public void run() {
		try {
			// On dit au tank de se déplacer en boucle en attendant à chaque fois le temps requis.
			while(true) sleep(tank.deplacer(sensDeplacement));
			
		} catch(InterruptedException e) {
			// On interrompt la boucle infinie si on nous demande de nous arrêter.
		}
	}
}
