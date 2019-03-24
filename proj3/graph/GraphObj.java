package graph;

import java.util.ArrayList;
import java.util.Iterator;

/* See restrictions in Graph.java. */

/**
 * A partial implementation of Graph containing elements common to
 * directed and undirected graphs.
 *
 * @author Amol Pant
 */
abstract class GraphObj extends Graph {

    /**
     * A new, empty Graph.
     */
    GraphObj() {
        vertices = new ArrayList<Node>();
        edgeSize = 0;
        edgeIDs = new ArrayList<Integer>();
        edges = new ArrayList<Edge>();
    }

    @Override
    public int vertexSize() {
        return vertices.size();
    }

    @Override
    public int maxVertex() {
        int maximum = 0;
        for (Node vertex : vertices) {
            if (vertex.value() > maximum) {
                maximum = vertex.value();
            }
        }
        return maximum;
    }

    @Override
    public int edgeSize() {
        return edgeSize;
    }

    @Override
    public abstract boolean isDirected();

    @Override
    public int outDegree(int v) {
        for (Node vertex : vertices) {
            if (vertex.value() == v) {
                return vertex.edgesNumber();
            }
        }
        return 0;
    }

    @Override
    public abstract int inDegree(int v);

    @Override
    public boolean contains(int u) {
        for (Node vertex : vertices) {
            if (vertex.value() == u) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean contains(int u, int v) {
        if (!contains(u)) {
            return false;
        }
        for (Node vertex : vertices) {
            if (vertex.value() == u) {
                for (Edge e : vertex.edges()) {
                    if (e.endVal() == v) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public int add() {
        int x = 1;
        while (this.contains(x)) {
            x += 1;
        }
        vertices.add(new Node(x));
        vertInts.add(x);
        while (x > succs.size() - 1) {
            succs.add(new ArrayList<>());
        }
        succs.add(x, new ArrayList<>());
        while (x > preds.size() - 1) {
            preds.add(new ArrayList<>());
        }
        preds.add(x, new ArrayList<>());
        return x;
    }

    @Override
    public int add(int u, int v) {
        assert (this.contains(u) && this.contains(v));
        int id = 1;
        for (Node vertex : vertices) {
            if (vertex.value() == u) {
                for (Edge e : vertex.edges()) {
                    if (e.endVal() == v) {
                        return e.id();
                    }
                }
                if (isDirected()) {
                    while (edgeIDs.contains(id)) {
                        id += 1;
                    }
                    Node opp = findVertex(v);
                    Edge adder = new Edge(vertex, opp, id);
                    vertex.addEdge(adder);
                    adder.setID(id);
                    edgeIDs.add(id);
                    edges.add(adder);
                    if (!succs.get(u).contains(v)) {
                        succs.get(u).add(v);
                    }
                    if (!preds.get(v).contains(u)) {
                        preds.get(v).add(u);
                    }
                } else {
                    id += 1;
                    while (edgeIDs.contains(id)) {
                        id += 1;
                    }
                    Node opp = findVertex(v);
                    Edge adder = new Edge(vertex, opp, id);
                    Edge adderTwo = new Edge(opp, vertex, id);
                    vertex.addEdge(adder);
                    if (u != v) {
                        opp.addEdge(adderTwo);
                    }
                    adder.setID(id);
                    if (u != v) {
                        adderTwo.setID(id);
                    }
                    edgeIDs.add(id);
                    edges.add(adder);
                    if (u != v) {
                        edges.add(adderTwo);
                    }
                    if (!succs.get(u).contains(v)) {
                        succs.get(u).add(v);
                    }
                    if (!succs.get(v).contains(u)) {
                        succs.get(v).add(u);
                    }
                }
                edgeSize += 1;
            }
        }
        doSomething(u, v);
        return id;
    }

    /**
     * Does something.
     * @param u Start value.
     * @param v End value.
     */
    private void doSomething(int u, int v) {
        int[] toAdd = new int[2];
        toAdd[0] = u;
        toAdd[1] = v;
        if (isDirected()) {
            if (!edgesPair.contains(toAdd)) {
                edgesPair.add(toAdd);
            }
        } else {
            int[] toAddRev = new int[2];
            toAddRev[0] = v;
            toAddRev[1] = u;
            if ((!edgesPair.contains(toAdd))
                    && (!edgesPair.contains(toAddRev))) {
                edgesPair.add(toAdd);
            }
        }
    }

    @Override
    public void remove(int v) {
        if (findVertex(v) == null) {
            return;
        } else {
            Node vertex = findVertex(v);
            ArrayList<Node> tempVert = new ArrayList<>();
            for (Edge edg : edges) {
                if (edg.startVal() == v) {
                    tempVert.add(findVertex(edg.endVal()));
                }
                if (edg.endVal() == v) {
                    tempVert.add((findVertex(edg.startVal())));
                }
            }
            for (Node ed : tempVert) {
                ed.edges().removeIf(e -> (e.endVal() == v));
            }
            Iterator<Edge> iter = edges.iterator();
            boolean undir = true;
            while (iter.hasNext()) {
                Edge e = iter.next();
                if ((e.startVal() == vertex.value())
                        || (e.endVal() == vertex.value())) {
                    edgeIDs.remove((Integer) (e.id()));
                    iter.remove();
                    if (isDirected()) {
                        edgeSize -= 1;
                    } else {
                        if (undir) {
                            edgeSize -= 1;
                        }
                    }
                }
                undir = !undir;
            }
            vertices.remove(vertex);
            vertInts.remove((Integer) vertex.value());
            ArrayList<Integer> toBeRemoved = new ArrayList<>();
            for (int x : succs.get(v)) {
                toBeRemoved.add(x);
            }
            for (int y : toBeRemoved) {
                removeSuccs(v, y);
            }
            toBeRemoved.clear();
            for (int x : preds.get(v)) {
                toBeRemoved.add(x);
            }
            for (int y : toBeRemoved) {
                removeSuccs(y, v);
            }
            ArrayList<int[]> toRemove = new ArrayList<>();
            for (int[] i : edgesPair) {
                if ((i[0] == v) || (i[1] == v)) {
                    toRemove.add(i);
                }
            }
            edgesPair.removeIf(e -> ((e[0] == v) || (e[1] == v)));
        }
    }



    @Override
    public void remove(int u, int v) {
        if (this.contains(u, v)) {
            if (!isDirected()) {
                Edge e = findEdge(u, v);
                edges.remove(e);
                edgeIDs.remove((Integer) (e.id()));
                Edge f = findEdge(v, u);
                edges.remove(f);
                Node n = findVertex(u);
                edgeSize -= 1;
                for (Edge g : n.edges()) {
                    if (g.endVal() == v) {
                        g = null;
                    }
                }
                Node m = findVertex(v);
                for (Edge h : m.edges()) {
                    if (h.endVal() == u) {
                        h = null;
                    }
                }

            } else {
                Edge e = findEdge(u, v);
                edges.remove(e);
                edgeIDs.remove((Integer) (e.id()));
                edgeSize -= 1;
                Node n = findVertex(u);
                for (Edge g : n.edges()) {
                    if (g.endVal() == v) {
                        g = null;
                    }
                }
            }

            if (isDirected()) {
                int[] toRemove = new int[2];
                toRemove[0] = u;
                toRemove[1] = v;
                edgesPair.remove(toRemove);
            } else {
                ArrayList<int[]> toRemove = new ArrayList<>();
                for (int[] i : edgesPair) {
                    if (((i[0] == u) && (i[1] == v))
                            || ((i[0] == v) && (i[1] == u))) {
                        toRemove.add(i);
                    }
                }
                edgesPair.removeAll(toRemove);
            }
            removeSuccs(u, v);
        }
    }

    /**
     * Removes the successors and/or the predecessors
     * of the edge between u and v.
     * @param u Start value.
     * @param v End value.
     */
    private void removeSuccs(int u, int v) {
        if (succs.get(u).contains(v)) {
            succs.get(u).remove((Integer) v);
        }
        if (!isDirected()) {
            if (succs.get(v).contains(u)) {
                succs.get(v).remove((Integer) u);
            }
        } else {
            if (preds.get(v).contains(u)) {
                preds.get(v).remove((Integer) u);
            }
        }
    }

    @Override
    public Iteration<Integer> vertices() {
        return Iteration.iteration(vertInts);
    }

    @Override
    public Iteration<Integer> successors(int v) {
        return Iteration.iteration(succs.get(v));
    }

    @Override
    public abstract Iteration<Integer> predecessors(int v);

    @Override
    public Iteration<int[]> edges() {
        return Iteration.iteration(edgesPair);
    }


    @Override
    protected int edgeId(int u, int v) {
        if (this.contains(u, v)) {
            return findEdge(u, v).id();
        }
        return 0;
    }

    /**
     * Returns the Node at y.
     *
     * @param y the value of the vertex.
     * @return The Node.
     */
    private Node findVertex(int y) {
        for (Node v : vertices) {
            if (v.value() == y) {
                return v;
            }
        }
        return null;
    }

    /**
     * Returns the Edge between u and v.
     *
     * @param u Start.
     * @param v End.
     * @return The Edge.
     */
    private Edge findEdge(int u, int v) {
        assert (this.contains(u, v));
        for (Node vert : vertices) {
            if ((vert.value() == u)) {
                for (Edge e : vert.edges()) {
                    if (e.endVal() == v) {
                        return e;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Returns the predecessors of v.
     *
     * @param v Vertex value.
     * @return The predecessors.
     */
    protected ArrayList<Integer> preds(int v) {
        return preds.get(v);
    }


    /**
     * Comment.
     */
    private ArrayList<Node> vertices;

    /**
     * Comment.
     */
    private ArrayList<Integer> vertInts = new ArrayList<>();

    /**
     * Comment.
     */
    private int edgeSize;

    /**
     * Comment.
     */
    private ArrayList<Integer> edgeIDs;

    /**
     * Comment.
     */
    private ArrayList<Edge> edges;

    /**
     * Comment.
     */
    private ArrayList<ArrayList<Integer>> succs = new ArrayList<>();

    /**
     * Comment.
     */
    private ArrayList<ArrayList<Integer>> preds = new ArrayList<>();

    /**
     * Comment.
     */
    private ArrayList<int[]> edgesPair = new ArrayList<>();

}
