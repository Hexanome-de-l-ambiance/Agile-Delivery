package com.example.tsp;

import java.util.Collection;
import java.util.Iterator;

/**
 * La classe SeqIter implémente un itérateur pour parcourir les sommets non visités
 * dans le graphe qui sont les successeurs du sommet courant.
 */
public class SeqIter implements Iterator<Integer> {
	private Integer[] candidates;
	private int nbCandidates;

	/**
	 * Créez un itérateur pour parcourir l'ensemble des sommets dans <code>unvisited</code>
	 * qui sont les successeurs du <code>currentVertex</code> dans <code>g</code>
	 * Les sommets sont parcourus dans le même ordre que dans <code>unvisited</code>
	 * @param unvisited
	 * @param currentVertex
	 * @param g
	 */
	public SeqIter(Collection<Integer> unvisited, int currentVertex, Graph g){
		this.candidates = new Integer[unvisited.size()];
		for (Integer s : unvisited){
			if (g.isArc(currentVertex, s))
				candidates[nbCandidates++] = s;
		}
	}
	
	@Override
	public boolean hasNext() {
		return nbCandidates > 0;
	}

	@Override
	public Integer next() {
		nbCandidates--;
		return candidates[nbCandidates];
	}

	@Override
	public void remove() {}

}
