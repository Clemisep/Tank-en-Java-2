package Principal;

import Tanks.Tank;

public class ThreadBougerCanon extends Thread {
	
	private Tank tank;
	private int sens;
	
	/**
	 * 
	 * @param tank Le tank de canon à bouger
	 * @param sens Sens de rotation : 1, vers le bas, -1, vers le haut.
	 */
	public ThreadBougerCanon(Tank tank, int sens) {
		this.tank = tank;
		this.sens = sens;
	}
	
	public void run() {
		try {
			// On dit au canon de se déplacer en boucle en attendant à chaque fois le temps requis.
			while(true) sleep(tank.bougerCanon(sens));
			
		} catch(InterruptedException e) {
			// On interrompt la boucle infinie si on nous demande de nous arrêter.
		}
	}
}
