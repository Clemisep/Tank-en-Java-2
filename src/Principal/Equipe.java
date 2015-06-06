package Principal;

public class Equipe {
	
	private int nombreDeTanks;
	
	public Equipe(int nombreDeTanks) {
		this.nombreDeTanks = nombreDeTanks;
	}
	
	/**
	 * À appeler lorsque l’équipe vient de voir un de ses joueurs mourir.
	 */
	public void perdreTank() {
		nombreDeTanks--;
	}
	
	/**
	 * @return Le nombre de joueurs de l’équipe encore vivants.
	 */
	public int recNombreDeTanks() {
		return nombreDeTanks;
	}
}
