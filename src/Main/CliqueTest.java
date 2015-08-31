/*
 * Test class for the class Clique. It is in Main package because of 
 * visibility of the class Graph.
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

	@After
	public void tearDown() throws Exception {
	}
	@Test
	public void testCommonNi() {
		VertexSet vs = new VertexSet();
		vs.add(6);
		assertEquals(vs.toString(), c.commonNi().toString());
	}
	
	@Test
	public void testAddVertex() {
		VertexSet vs=new VertexSet();
		vs.add(6);
		assertEquals(vs.toString(), c.commonNi().toString());
		c.addVertex(2);
		VertexSet vs1=new VertexSet();
		assertEquals(vs1.toString(), c.commonNi().toString());
		
	}
	@Test
	public void testInit() {

	}

	@Test
	public void testCliqueIntInt() {
	}

	@Test
	public void testCliqueClique() {
	}

	@Test
	public void testCliqueCliqueInt() {
	}

	@Test
	public void testToFile() {
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

	

	

}
