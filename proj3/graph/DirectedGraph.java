package graph;

/* See restrictions in Graph.java. */

/** Represents a general unlabeled directed graph whose vertices are denoted by
 *  positive integers. Graphs may have self edges.
 *
 *  @author Amol Pant
 */
public class DirectedGraph extends GraphObj {

    @Override
    public boolean isDirected() {
        return true;
    }

    @Override
    public int inDegree(int v) {
        return preds(v).size();
    }

    @Override
    public Iteration<Integer> predecessors(int v) {
        return Iteration.iteration(preds(v));
    }
}
