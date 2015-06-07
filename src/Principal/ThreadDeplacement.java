package Principal;

import Tanks.Tank;

public class ThreadDeplacement extends Thread {
	
	private Tank tank;
	private int sensDeplacement;
	
	/**
	 * 
	 * @param tank Le tank � essayer de d�placer.
	 * @param sensDeplacement 1 pour se d�placer � droite, -1 pour se d�placer � gauche.
	 */
	public ThreadDeplacement(Tank tank, int sensDeplacement) {
		this.tank = tank;
		this.sensDeplacement = sensDeplacement;
	}
	
	public void run() {
		try {
			// On dit au tank de se d�placer en boucle en attendant � chaque fois le temps requis.
			while(true) sleep(tank.deplacer(sensDeplacement));
			
		} catch(InterruptedException e) {
			// On interrompt la boucle infinie si on nous demande de nous arr�ter.
		}
	}
}
