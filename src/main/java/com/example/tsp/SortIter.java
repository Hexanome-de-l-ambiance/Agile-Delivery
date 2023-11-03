package com.example.tsp;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Arrays;
public class SortIter implements Iterator<Integer> {
    private Integer[] candidates;
    private int nbCandidates;

    /**
     * Create an iterator to traverse the set of vertices in <code>unvisited</code>
     * which are successors of <code>currentVertex</code> in <code>g</code>
     * Vertices are traversed in the same order as in <code>unvisited</code>
     * @param unvisited
     * @param currentVertex
     * @param g
     */
    public SortIter(Collection<Integer> unvisited, int currentVertex, Graph g) {
        this.candidates = new Integer[unvisited.size()];
        for (Integer s : unvisited) {
            if (g.isArc(currentVertex, s))
                candidates[nbCandidates++] = s;
        }
        Arrays.sort(candidates, 0, nbCandidates, Comparator.comparingDouble((Integer i) -> g.getCost(currentVertex, i)).reversed());
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
