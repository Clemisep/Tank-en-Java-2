package Principal;

public class Equipe {
	
	private int nombreDeTanks;
	private String nomEquipe;
	
	public Equipe(int nombreDeTanks, String nomEquipe) {
		this.nombreDeTanks = nombreDeTanks;
		this.nomEquipe = nomEquipe;
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

	public String recNomEquipe() {
		return nomEquipe;
	}
}
