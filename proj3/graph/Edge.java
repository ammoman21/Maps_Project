package graph;

/**
 * Represents and Edge.
 * @author Amol Pant
 */
class Edge {

    /** Comment. */
    private Node start;

    /** Comment. */
    private Node end;

    /** Comment. */
    private int edgeID;

    /**
     * Constructor.
     * @param s Node start.
     * @param e Node end.
     * @param id The ID.
     */
    Edge(Node s, Node e, int id) {
        start = s;
        end = e;
        edgeID = id;
    }

    /**
     * Comment.
     * @return Start.
     */
    Node start() {
        return start;
    }

    /**
     * Comment.
     * @return End.
     */
    Node end() {
        return end;
    }

    /**
     * ID.
     * @return Edge ID.
     */
    int id() {
        return edgeID;
    }

    /**
     * Sets ID.
     * @param i ID.
     */
    void setID(int i) {
        edgeID = i;
    }

    /**
     * Comment.
     * @return Start value.
     */
    int startVal() {
        return start.value();
    }

    /**
     * Comment.
     * @return End Value.
     */
    int endVal() {
        return end.value();
    }

}
