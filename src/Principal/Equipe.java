package Principal;

public class Equipe {
	
	private int nombreDeTanks;
	
	public Equipe(int nombreDeTanks) {
		this.nombreDeTanks = nombreDeTanks;
	}
	
	/**
	 * � appeler lorsque l��quipe vient de voir un de ses joueurs mourir.
	 */
	public void perdreTank() {
		nombreDeTanks--;
	}
	
	/**
	 * @return Le nombre de joueurs de l��quipe encore vivants.
	 */
	public int recNombreDeTanks() {
		return nombreDeTanks;
	}
}
