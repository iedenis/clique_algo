package Main;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Vector;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class GraphTest {

	private static Graph G;
	private VertexSet vs;
	Vector<VertexSet> V = new Vector<VertexSet>();

	@Before
	public void setUp() throws Exception {
		vs = new VertexSet();
		for (int i = 0; i < 10; i++) {
			vs.add(i);
		}
	}

	@BeforeClass
	public static void setUpBeforeClass() throws IOException {
		G = new Graph(System.getProperty("user.dir") + "/src/tinyEWG.txt", 0.3, false);

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test(expected = Exception.class)
	public void testGraph() throws IOException {
		System.setErr(null);
		try {
			G = new Graph("/src/tinyEWG.txt", 0.3, true);
		} catch (IOException e) {
			throw e;
		}
	}

	public void testGraphNotNull() {
		try {
			G = new Graph(System.getProperty("user.dir") + "/src/tinyEWG.txt", 0.3, false);
			assertNotNull(G);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testPrint() {
	}

	@Test
	public void testPrint1() {
	}

	@Test
	public void testAll_Cliques_DFS_2() {
	}

	@Test
	public void testWrite2file_new() {
		V.add(vs);
		assertNotNull(V.elementAt(0));
	}

	@Test
	public void testAll_Cliques_DFS() {
	}

	@Test
	public void testAllC_seed() {
	}

	@Test
	public void testWrite2file() {
	}

}
