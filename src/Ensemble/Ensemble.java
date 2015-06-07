package Ensemble;

import java.util.Iterator;

public class Ensemble<T> implements Iterable<T> {
	
	private Maillon<T> tete, queue;
	private int longueur;
	
	public Ensemble() {
		longueur = 0;
	}
	
	/**
	 * Ajoute un �l�ment � l�ensemble.
	 * @param element �l�ment � ajouter � l�ensemble.
	 * @return R�f�rence � fournir en argument de la m�thode supprimer.
	 */
	public Maillon<T> ajouter(T element) {
		if(tete == null)
			tete = queue = new Maillon<T>(element);
		else
			tete = tete.attacher(element);
		
		longueur++;
		
		return tete;
	}
	
	/**
	 * Supprime un �l�ment de l�ensemble. Ne supprimer un �l�ment d�un ensemble que lorsqu�il a �t� ajout� au m�me ensemble et ne le supprimer qu�une fois.
	 * @param maillonElement R�f�rence renvoy�e par la m�thode lors de l�ajout de l��l�ment.
	 */
	public void supprimer(Maillon<T> maillonElement) {
		if(longueur == 1)
			tete = queue = null;
		else {
			
			if(maillonElement == queue)
				queue = queue.recSuivant();
			else if(maillonElement == tete)
				tete = tete.recPrecedent();
			
			maillonElement.supprimer();
		}
		
		longueur--;
		
		if(longueur < 0) {
			throw new NegativeArraySizeException();
		}
	}
	
	public Maillon<T> recMaillonDernier() {
		return tete;
	}
	
	public Maillon<T> recMaillonPremier() {
		return queue;
	}
	
	public Iterator<T> iterator() {
		return new Iterator<T>() {
			
			private Maillon<T> maillon = Ensemble.this.queue;
			
			@Override
			public boolean hasNext() {
				return maillon != null;
			}
			
			@Override
			public T next() {
				Maillon<T> actuel = maillon;
				maillon = maillon.recSuivant();
				return actuel.recValeur();
			}
		};
	}
	
	/**
	 * Renvoie un it�rateur qui, au lieu de donner un � un les contenus, donne les maillons.
	 * @return
	 */
	public Iterable<Maillon<T>> recIterableMaillon() {
		return new Iterable<Maillon<T>>() {
			public Iterator<Maillon<T>> iterator() {
				return new Iterator<Maillon<T>>() {
					
					private Maillon<T> maillon = Ensemble.this.queue;
					
					@Override
					public boolean hasNext() {
						return maillon != null;
					}
					
					@Override
					public Maillon<T> next() {
						Maillon<T> actuel = maillon;
						maillon = maillon.recSuivant();
						return actuel;
					}
					
				};
			}
		};
	}
}
