package Test;

import static org.junit.Assert.*;
import org.junit.Test;

import Main.VertexSet;

import org.junit.After;
import org.junit.Before;

public class VertexSetTest {
	private VertexSet vs;
	private VertexSet vs1;
	
	@Before
	public void setUp() throws Exception {
		vs = new VertexSet();
		for (int i = 0; i < 20; i++) {
			vs.add(i);
		}
		vs1 = new VertexSet();

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testVertexSet() {
		assertNotNull(vs);
		assertNotNull(vs1);
		assertTrue(vs.size() == 20);
		assertTrue(vs1.size() == 0);
	}

	@Test
	public void testContains() {
		assertTrue(vs.contains(17));
	}

	@Test
	public void testVertexSetVertexSet() {
	VertexSet expectedV=new VertexSet(vs);
	assertArrayEquals(expectedV.getSet(), vs.getSet());
	}

	@Test
	public void testAdd() {
		vs.add(21);
		assertTrue(vs.contains(21));
	}

	@Test
	public void testSize() {
		assertTrue(vs.size() == 20);
	}

	public void testResize() {
		for (int i = 0; i < 30; i++) {
			vs.add(i);
		}
		assertTrue(vs.size() == 40);
	}

	@Test
	public void testAt() {
		assertTrue(vs.at(3) == 3);
	}

	@Test
	public void testToString() {
		String expected = "Set: |0| ";
		assertEquals(expected, vs1.toString());
	}

	@Test
	public void testIntersection() {
		for (int i = 0; i < 3; i++) {
			vs1.add(i);
		}
		VertexSet vsResult = new VertexSet();
		vsResult = vs.intersection(vs1);
		assertTrue(vsResult.contains(0) && vsResult.contains(1) && vsResult.contains(2));
	}

}
