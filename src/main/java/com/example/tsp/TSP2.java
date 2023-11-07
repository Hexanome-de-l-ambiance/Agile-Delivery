package com.example.tsp;

import java.util.Collection;
import java.util.Iterator;

public class TSP2 extends TSP1{

    public TSP2() {
        super();
    }

    public TSP2(int startVertex) {
        super(startVertex);
    }

    @Override
    protected double bound(Integer currentVertex, Collection<Integer> unvisited) {
        double longueur = Double.MAX_VALUE;
        for (Integer i : unvisited) {
            if (g.getCost(currentVertex, i) < longueur) {
                longueur = g.getCost(currentVertex, i);
            }
        }

        for (Integer i : unvisited)
        {
            double arcMin = g.getCost(i,0);
            for (Integer j : unvisited)
            {
                if (j != i && g.getCost(i,j) < arcMin)
                {
                    arcMin = g.getCost(i,j);
                }
            }
            longueur += arcMin;
        }
        return longueur;
    }
}
