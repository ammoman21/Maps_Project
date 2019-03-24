package graph;

import org.junit.Test;

import static org.junit.Assert.*;

public class TestingUndirected {

    @Test
    public void graph() {
        UndirectedGraph g = new UndirectedGraph();
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
        UndirectedGraph g = new UndirectedGraph();
        g.add();
        g.add();
        g.add();
        g.add();
        g.add();
        g.add(1, 3);
        g.add(1, 4);
        g.add(4, 5);
        g.add(3, 4);
        assertEquals(g.edgeId(1, 4), 3);
        g.remove(1);
        assertEquals(g.edgeId(3, 4), 5);
    }

    @Test
    public void iteratorsCheck() {
        UndirectedGraph g = new UndirectedGraph();
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
        assertTrue(g.predecessors(3).next() == 4);
    }

    @Test
    public void otherGeneralStuff() {
        UndirectedGraph g = new UndirectedGraph();
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
        assertTrue(g.inDegree(1) == 2);
        assertTrue(g.successors(1).next() == 3);
        g.remove(1);
        System.out.println(g.edgeSize());
        g.add();
        System.out.println(g.edgeSize());
    }
}
