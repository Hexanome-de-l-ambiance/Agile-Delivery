package com.example.tsp;

import java.util.Collection;
import java.util.Iterator;

public class TSP1 extends TemplateTSP {

	public TSP1(){
		super();
	}

	public TSP1(int startVertex){
		super(startVertex);
	}

	@Override
	protected double bound(Integer currentVertex, Collection<Integer> unvisited) {
		return 0;
	}

	@Override
	protected Iterator<Integer> iterator(Integer currentVertex, Collection<Integer> unvisited, Graph g) {
		return new SeqIter(unvisited, currentVertex, g);
	}

}
