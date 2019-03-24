package graph;

/* See restrictions in Graph.java. */

import java.util.TreeSet;
import java.util.Iterator;
import java.util.Comparator;
import java.util.AbstractQueue;
import java.util.List;
import java.util.LinkedList;


/**
 * The shortest paths through an edge-weighted graph.
 * By overrriding methods getWeight, setWeight, getPredecessor, and
 * setPredecessor, the client can determine how to represent the weighting
 * and the search results.  By overriding estimatedDistance, clients
 * can search for paths to specific destinations using A* search.
 *
 * @author Amol Pant
 */

@SuppressWarnings("unchecked")
public abstract class ShortestPaths {

    /**
     * The shortest paths in G from SOURCE.
     */
    public ShortestPaths(Graph G, int source) {
        this(G, source, 0);
    }

    /**
     * A shortest path in G from SOURCE to DEST.
     */
    public ShortestPaths(Graph G, int source, int dest) {
        _G = G;
        _source = source;
        _dest = dest;
    }

    /**
     * Initialize the shortest paths.  Must be called before using
     * getWeight, getPredecessor, and pathTo.
     */
    public void setPaths() {
        for (Integer i : _G.vertices()) {
            setPredecessor(i, 0);
            setWeight(i, Double.MAX_VALUE);
        }
        startTrav = new AStar(_G, new CustomPQ());
        setWeight(getSource(), 0);
        startTrav.traverse(getSource());
    }

    /**
     * Returns the starting vertex.
     */
    public int getSource() {
        return _source;
    }

    /**
     * Returns the target vertex, or 0 if there is none.
     */
    public int getDest() {
        return _dest;
    }

    /**
     * Returns the current weight of vertex V in the graph.  If V is
     * not in the graph, returns positive infinity.
     */
    public abstract double getWeight(int v);

    /**
     * Set getWeight(V) to W. Assumes V is in the graph.
     */
    protected abstract void setWeight(int v, double w);

    /**
     * Returns the current predecessor vertex of vertex V in the graph, or 0 if
     * V is not in the graph or has no predecessor.
     */
    public abstract int getPredecessor(int v);

    /**
     * Set getPredecessor(V) to U.
     */
    protected abstract void setPredecessor(int v, int u);

    /**
     * Returns an estimated heuristic weight of the shortest path from vertex
     * V to the destination vertex (if any).  This is assumed to be less
     * than the actual weight, and is 0 by default.
     */
    protected double estimatedDistance(int v) {
        return 0.0;
    }

    /**
     * Returns the current weight of edge (U, V) in the graph.  If (U, V) is
     * not in the graph, returns positive infinity.
     */
    protected abstract double getWeight(int u, int v);

    /**
     * Returns a list of vertices starting at _source and ending
     * at V that represents a shortest path to V.  Invalid if there is a
     * destination vertex other than V.
     */
    public List<Integer> pathTo(int v) {
        while (getSource() != v) {
            _path.addFirst(v);
            v = getPredecessor(v);
        }
        _path.addFirst(getSource());
        return _path;
    }

    /**
     * Returns a list of vertices starting at the source and ending at the
     * destination vertex. Invalid if the destination is not specified.
     */
    public List<Integer> pathTo() {
        return pathTo(getDest());
    }

    /**
     * The graph being searched.
     */
    protected final Graph _G;
    /**
     * The starting vertex.
     */
    private final int _source;
    /**
     * The target vertex.
     */
    private final int _dest;

    /**
     * An A* traversal.
     */
    private AStar startTrav;

    /**
     * Found path storage.
     */
    private LinkedList<Integer> _path = new LinkedList<>();

    /**
     * Custom traversal for A* implementation.
     */
    class AStar extends Traversal {
        /**
         * An A* traversal using GRAPH and FRINGE.
         */
        protected AStar(Graph graph, CustomPQ fringe) {
            super(graph, fringe);
            g = graph;
            f = fringe;
        }

        @Override
        protected boolean visit(int v) {
            if (v == getDest()) {
                f.clear();
                return true;
            }
            for (int succ : g.successors(v)) {
                double totalWeight = getWeight(v)
                        + getWeight(v, succ);
                if (totalWeight < getWeight(succ)) {
                    setWeight(succ, totalWeight);
                    setPredecessor(succ, v);
                    f.remove(succ);
                    f.add(succ);
                }
            }
            return false;
        }

        /**
         * My graph.
         */
        private Graph g;

        /**
         * My fringe.
         */
        private CustomPQ f;
    }

    /**
     * Custom built priority queue. Uses TreeSet at base level.
     */
    private class CustomPQ extends AbstractQueue<Integer> {

        /**
         * Constructor.
         */
        CustomPQ() {
            tree = new TreeSet<>(new CustomComp());
        }

        @Override
        public boolean offer(Integer v) {
            try {
                tree.add(v);
                return true;
            } catch (ClassCastException excp) {
                return false;
            } catch (NullPointerException excp) {
                return false;
            } catch (IllegalArgumentException excp) {
                return false;
            }
        }

        @Override
        public Integer poll() {
            return tree.pollFirst();
        }

        @Override
        public Integer peek() {
            if (tree.isEmpty()) {
                return null;
            } else {
                return tree.first();
            }
        }

        @Override
        public int size() {
            return tree.size();
        }

        @Override
        public Iterator<Integer> iterator() {
            return tree.iterator();
        }

        /**
         * My TreeSet.
         */
        private TreeSet<Integer> tree;

    }

    /**
     * My custom comparator.
     */
    private class CustomComp<Integer> implements Comparator<Integer> {
        @Override
        public int compare(Object u, Object v) {
            int first = (int) u;
            int second = (int) v;
            if (getWeight(first) + estimatedDistance(first)
                    < getWeight(second) + estimatedDistance(second)) {
                return -2;
            } else if (getWeight(first) + estimatedDistance(first)
                    > getWeight(second) + estimatedDistance(second)) {
                return 2;
            } else {
                return 1;
            }
        }
    }

}
