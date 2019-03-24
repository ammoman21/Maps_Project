package graph;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestingDirected {

    @Test
    public void graph() {
        DirectedGraph g = new DirectedGraph();
        g.add();
        g.add();
        g.add();
        g.add();
        g.add();
        g.add(1, 3);
        g.add(1, 4);
        g.add(4, 5);
        g.add(3, 4);
        assertTrue(g.edgeSize() == 4);
        assertTrue(g.contains(1));
        assertFalse(g.contains(6));
        g.add();
        g.add();
        assertTrue(g.contains(6));
    }

    @Test
    public void idCheck() {
        DirectedGraph g = new DirectedGraph();
        g.add();
        g.add();
        g.add();
        g.add();
        g.add();
        g.add(1, 3);
        g.add(1, 4);
        g.add(4, 5);
        g.add(3, 4);
        assertEquals(g.edgeId(1, 4), 2);
        g.remove(1);
        assertEquals(g.edgeId(3, 4), 4);
    }

    @Test
    public void iteratorsCheck() {
        DirectedGraph g = new DirectedGraph();
        g.add();
        g.add();
        g.add();
        g.add();
        g.add();
        g.add(1, 3);
        g.add(1, 4);
        g.add(4, 5);
        g.add(3, 4);
        assertTrue(g.vertices().next() == 1);
        g.remove(1);
        assertTrue(g.vertices().next() == 2);
        assertTrue(g.successors(3).next() == 4);
        assertFalse(g.predecessors(3).hasNext());
    }

    @Test
    public void otherGeneralStuff() {
        DirectedGraph g = new DirectedGraph();
        g.add();
        g.add();
        g.add();
        g.add();
        g.add();
        g.add(1, 3);
        g.add(1, 4);
        g.add(4, 5);
        g.add(3, 4);
        assertTrue(g.maxVertex() == 5);
        assertTrue(g.vertexSize() == 5);
        assertTrue(g.inDegree(1) == 0);
        assertTrue(g.successors(1).next() == 3);
    }
}
