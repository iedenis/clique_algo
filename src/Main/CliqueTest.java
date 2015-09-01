/*
 * Test class for the class Clique. It is in Main package because of 
 * visibility of the class Graph.
 * Using tinyEwg.txt graph for testing (for initialization)
 */

package Main;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import Main.Clique;

public class CliqueTest {
	Clique c;
	Clique c1;
	static Graph G;

	@BeforeClass
	public static void setUpBeforeClass() throws IOException {
		G = new Graph(System.getProperty("user.dir") + "/src/tinyEWG.txt", 0.3, false);
		Clique.init(G);
	}

	@Before
	public void setUp() throws Exception {
		c = new Clique(0, 3);
	}

	@Test
	public void testCommonNi() {
		VertexSet vs = new VertexSet();
		vs.add(6);
		assertEquals(vs.toString(), c.commonNi().toString());
	}

	@Test
	public void testAddVertex() {
		VertexSet vs = new VertexSet();
		vs.add(6);
		assertEquals(vs.toString(), c.commonNi().toString());
		c.addVertex(2);
		VertexSet vs1 = new VertexSet();
		assertEquals(vs1.toString(), c.commonNi().toString());
	}

	@Test
	public void testCliqueIntInt() {
		c1 = new Clique(0, 3);
		VertexSet vs = new VertexSet();
		vs.add(6);
		assertEquals(vs.toString(), c1.commonNi().toString());
		VertexSet vs1 = new VertexSet();
		vs1.add(0);
		vs1.add(3);
		assertEquals(vs1.toString(), c1.clique().toString());
	}

	@Test
	public void testCliqueClique() {
		c1 = new Clique(c);
		assertEquals(c1.commonNi().toString(), c.commonNi().toString());
		assertEquals(c1.clique().toString(), c.clique().toString());
	}

	@Test
	public void testSize() {
		if (c.size() != 2)
			fail();
	}

	@Test
	public void testClique() {
		if (c == null)
			fail();
	}

	@Test
	public void testToFile() {
		assertNotNull(c.clique());
	}

}
