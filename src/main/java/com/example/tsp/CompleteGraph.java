package com.example.tsp;

import com.example.model.Carte;
import com.example.model.Intersection;
import com.example.model.Livraison;
import com.example.utils.Astar;

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
		this.nbVertices = livraisons.size();
		this.cost = new double[nbVertices][nbVertices];
		this.indexToId = new HashMap<>();
		for (int i = 0; i < nbVertices; i++) {
			indexToId.put(i, livraisons.get(i).getDestination().getId());
		}
		for(int i=0 ; i<livraisons.size() ; i++)
		{
			for(int j=0 ; j < livraisons.size() ; j++)
			{
				Intersection intersection1 = livraisons.get(i).getDestination();
				Intersection intersection2 = livraisons.get(j).getDestination();
				if(Objects.equals(intersection1.getId(), intersection2.getId()))
				{
					cost[i][j] = -1;
				}
				else
				{
					cost[i][j] = Astar.calculDistance(carte, intersection1, intersection2);
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
		return i != j;
	}

	@Override
	public Long getId(int i) {
		return indexToId.get(i);
	}
}
