package Ensemble;

import java.util.Iterator;

public class Ensemble<T> implements Iterable<T> {
	
	private Maillon<T> tete, queue;
	private int longueur;
	
	public Ensemble() {
		longueur = 0;
	}
	
	/**
	 * Ajoute un élément à l’ensemble.
	 * @param element Élément à ajouter à l’ensemble.
	 * @return Référence à fournir en argument de la méthode supprimer.
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
	 * Supprime un élément de l’ensemble. Ne supprimer un élément d’un ensemble que lorsqu’il a été ajouté au même ensemble et ne le supprimer qu’une fois.
	 * @param maillonElement Référence renvoyée par la méthode lors de l’ajout de l’élément.
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
	 * Renvoie un itérateur qui, au lieu de donner un à un les contenus, donne les maillons.
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
