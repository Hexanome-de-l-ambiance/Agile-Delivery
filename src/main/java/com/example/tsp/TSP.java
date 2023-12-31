package com.example.tsp;

import java.util.ArrayList;

/**
 * L'interface TSP définit les méthodes pour résoudre le problème du voyageur de commerce (Traveling Salesman Problem).
 */
public interface TSP {
	/**
	 * Search for a shortest cost hamiltonian circuit in <code>g</code> within <code>timeLimit</code> milliseconds
	 * (returns the best found tour whenever the time limit is reached)
	 * Warning: The computed tour always start from vertex 0
	 * @param timeLimit
	 * @param g
	 */
	public Boolean searchSolution(int timeLimit, Graph g);
	
	/**
	 * @param i
	 * @return the ith visited vertex in the solution computed by <code>searchSolution</code> 
	 * (-1 if <code>searchSolution</code> has not been called yet, or if i &lt; 0 or i &gt;= g.getNbSommets()).
	 */
	public Long getSolution(int i);
	
	/** 
	 * @return the total cost of the solution computed by <code>searchSolution</code> 
	 * (-1 if <code>searchSolution</code> has not been called yet).
	 */
	public double getSolutionCost();

	public ArrayList<Long> getSolutions();
}
