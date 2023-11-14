package com.example.tsp;

import com.example.model.Carte;
import com.example.model.Intersection;
import com.example.model.Livraison;
import com.example.utils.Astar;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class CompleteGraph implements Graph{
	int nbVertices;
	double [][] cost;
	HashMap<Integer, Long> indexToId;

	/**
	 * Create a complete directed graph such that each edge is the shortest path between two intersections
	 * @param carte the map
	 * @param livraisons the list of intersections
	 */
	public CompleteGraph(Carte carte, ArrayList<Livraison> livraisons) {
		Intersection entrepot = carte.getEntrepot();
		int entrepotIndex = livraisons.size();
		this.nbVertices = livraisons.size() + 1;
		this.cost = new double[nbVertices][nbVertices];

		this.indexToId = new HashMap<>(nbVertices);
		for(int i = 0; i < livraisons.size() ; i++) {
			indexToId.put(i, livraisons.get(i).getDestination().getId());
		}
		indexToId.put(entrepotIndex, entrepot.getId());

		cost[livraisons.size()][livraisons.size()] = -1;
		for(int i=0; i < livraisons.size(); i++)
		{
			cost[livraisons.size()][i] = Astar.calculDistance(carte, entrepot, livraisons.get(i).getDestination());
			cost[i][livraisons.size()] = Astar.calculDistance(carte, livraisons.get(i).getDestination(), entrepot);
		}

		for(int i=0 ; i<livraisons.size() ; i++)
		{
			for(int j=0 ; j < livraisons.size() ; j++)
			{
				if(i == j)
				{
					cost[i][j] = -1;
					continue;
				}
				LocalTime debutCreneauHoraire1 = livraisons.get(i).getCrenauHoraire();
				LocalTime finCreneauHoraire1 = livraisons.get(i).getCrenauHoraire().plus(Livraison.DUREE_CRENEAU_HORAIRE);
				LocalTime debutCreneauHoraire2 = livraisons.get(j).getCrenauHoraire();
				if(debutCreneauHoraire1 == debutCreneauHoraire2 || finCreneauHoraire1.isBefore(debutCreneauHoraire2) || finCreneauHoraire1.equals(debutCreneauHoraire2)) {
					cost[i][j] = Astar.calculDistance(carte, livraisons.get(i).getDestination(), livraisons.get(j).getDestination());
				}else{
					cost[i][j] = -1;
				}
			}
		}
	}

	@Override
	public int getNbVertices() {
		return nbVertices;
	}

	@Override
	public double getCost(int i, int j) {
		return cost[i][j];
	}

	@Override
	public boolean isArc(int i, int j) {
		return cost[i][j] != -1;
	}

	@Override
	public Long getId(int i) {
		return indexToId.get(i);
	}
}
