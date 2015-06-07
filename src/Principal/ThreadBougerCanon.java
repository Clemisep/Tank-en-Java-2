package Principal;

import Tanks.CanonBougeable;

public class ThreadBougerCanon extends Thread {
	
	private CanonBougeable canonBougeable;
	private int sens;
	
	/**
	 * 
	 * @param canonBougeable Le tank de canon � bouger
	 * @param sens Sens de rotation : 1, vers le bas, -1, vers le haut.
	 */
	public ThreadBougerCanon(CanonBougeable canonBougeable, int sens) {
		this.canonBougeable = canonBougeable;
		this.sens = sens;
	}
	
	public void run() {
		try {
			// On dit au canon de se d�placer en boucle en attendant � chaque fois le temps requis.
			while(true) sleep(canonBougeable.bougerCanon(sens));
			
		} catch(InterruptedException e) {
			// On interrompt la boucle infinie si on nous demande de nous arr�ter.
		}
	}
}
