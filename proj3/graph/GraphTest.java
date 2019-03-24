package graph;

import org.junit.Test;

import static org.junit.Assert.*;

/** Unit tests for the Graph class.
 *  @author Amol Pant
 */
public class GraphTest {

    @Test
    public void emptyGraph() {
        UndirectedGraph g = new UndirectedGraph();
        assertEquals("Initial graph has vertices", 0, g.vertexSize());
        assertEquals("Initial graph has edges", 0, g.edgeSize());
        g.add();
        g.add();
        g.add();
        g.add();
        g.add();
        g.add(1, 3);
        g.add(1, 4);
        g.add(4, 5);
        g.add(3, 4);
        for (int i = 0; i < 10; i += 1) {
            g.add();
        }
        g.add(2, 5);
        g.add(2, 3);
        g.add(2, 6);
        g.add(3, 7);
        g.add(3, 8);
        g.add(8, 1);
        g.add(8, 9);
        g.add(1, 1);
        g.add(8, 8);
        g.add(2, 2);
        g.add(1, 2);
        g.add(1, 3);
        g.add(1, 4);
        g.add(8, 10);
        g.add(10, 7);
    }

}
