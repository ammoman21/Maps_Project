package graph;

import java.util.ArrayList;

/** Represents a Vertice. I dont know why I named it Node though.
 * I should have named it Vertice.
 * @author Amol Pant
 * */
class Node {

    /** Comment. */
    private int value;

    /** Comment. */
    private ArrayList<Edge> edges;

    /** Comment.
     * @param v The value.
     * */
    Node(int v) {
        value = v;
        edges = new ArrayList<>();
    }

    /** Comment.
     * @param v The value.
     * @param e Edge list.
     */
    Node(int v, ArrayList<Edge> e) {
        value = v;
        edges = e;
    }

    /** Comment.
     * @return value the value.*/
    int value() {
        return value;
    }

    /** Comment.
     * @return Edges.
     * */
    ArrayList<Edge> edges() {
        return edges;
    }

    /** Comment.
     * @return The number of Edges.
     * */
    int edgesNumber() {
        return edges.size();
    }

    /** Adds an edge.
     * @param e Node.
     * @param id Id.
     * */
    void addEdge(Node e, int id) {
        edges.add(new Edge(this, e, id));
    }

    /** Adds an edge.
     * @param e Edge.
     * */
    void addEdge(Edge e) {
        edges.add(e);
    }
}
