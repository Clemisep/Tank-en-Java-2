package Principal;

public class Equipe {
	
	private int nombreDeTanks;
	private String nomEquipe;
	
	public Equipe(int nombreDeTanks, String nomEquipe) {
		this.nombreDeTanks = nombreDeTanks;
		this.nomEquipe = nomEquipe;
	}
	
	public void modNombreDeTanks(int nombreDeTanks) {
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

	public String recNomEquipe() {
		return nomEquipe;
	}
}
