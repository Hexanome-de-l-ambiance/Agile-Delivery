package com.example.tsp;

import com.example.model.Carte;
import com.example.model.Intersection;
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
	 * @param intersections the list of intersections
	 */
	public CompleteGraph(Carte carte, ArrayList<Intersection> intersections) {
		this.nbVertices = intersections.size();
		this.cost = new double[nbVertices][nbVertices];
		this.indexToId = new HashMap<>();
		for (int i = 0; i < nbVertices; i++) {
			indexToId.put(i, intersections.get(i).getId());
		}
		for(int i=0 ; i<intersections.size() ; i++)
		{
			for(int j=0 ; j < intersections.size() ; j++)
			{
				if(Objects.equals(intersections.get(i).getId(), intersections.get(j).getId()))
				{
					cost[i][j] = -1;
				}
				else
				{
					cost[i][j] = Astar.calculDistance(carte, intersections.get(i), intersections.get(j));
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
