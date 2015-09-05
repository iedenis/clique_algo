package Main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;
import java.util.Vector;

/*
import clique_algo.Clique;
import clique_algo.Clique_Tester;
import clique_algo.VertexSet;


import clique_algo.Clique;
import clique_algo.VertexSet;
*/
/**
 * this class represents an undirected 0/1 sparse Graph
 * 
 * @author Denis Ievlev, Samer Hadeed, Meir Pariente, Michal Abraham.
 *
 */
class Graph {
	private String _file_name;
	private static Vector<VertexSet> _V;
	int numOfVertices;
	int numOfEdges;
	private double _TH; // the threshold value
	private int _E_size = 0;
	private static boolean _mat_flag = true;
	private String fileName;
	private Vector<VertexSet> _AllEdges;
	private Vector<VertexSet> C0; // All edges of the graph
	private Vector<Clique> _VC;
	
	/**filds for testing the improovment in All_Cliques_DFS*/
	public static int
		/*allC_seed*/
		EnterAllCSeed = 0, 
		whileEnter = 0,
		ifEnter = 0, /*if(curr.size()<max_size)*/
		forNi = 0,
		/*All_Cliques_DFS*/
		forLen = 0,
		forC1 = 0,
		ifCount = 0;

	Graph(String file, double th, boolean type) throws IOException {
		this._file_name = file;
		_TH = th;
		_V = new Vector<VertexSet>();
		if (type)
			init();
		else
			parseNewGraph();
	}
	
	public void All_Cliques_DFS(String out_file, int min_size, int max_size) {
		Clique.init(this);
		Vector<VertexSet>C0 = allEdges(); // all edges – all cliques of size 2/
		int len = C0.size();
		System.out.println("|E|= "+len);
		int count = 0;

		FileWriter fw=null;
		try {fw = new FileWriter(out_file);} 
		catch (IOException e) {e.printStackTrace();}
		PrintWriter os = new PrintWriter(fw);
		//os.println("A");

		String ll = "0%   20%   40%   60%   80%   100%";
		int t = Math.max(1,len/ll.length());
		if(Clique_Tester.Debug){
			System.out.println("Computing all cliques of size["+min_size+","+max_size+"] based on "+len+" edges graph, this may take a while");
			System.out.println(ll);
		}
		os.println("All Cliques: file [min max] TH,"+this._file_name+","+min_size+", "+max_size+", "+this._TH);
		os.println("index, edge, clique size, c0, c1, c2, c3, c4,  c5, c6, c7, c8, c9");
		for(int i=0;i<len;i++) {
			forLen++;
			
			VertexSet curr_edge = C0.elementAt(i);
			Clique edge = new Clique(curr_edge.at(0),curr_edge.at(1));
			Vector<Clique> C1 = allC_seed(edge, min_size, max_size);

			for(int b=0;b<C1.size();b++) {
				forC1++;
				Clique c = C1.elementAt(b);
				if (c.size()>=min_size) {
					ifCount++;
					os.println(count+", "+i+","+c.size()+", "+c.toFile());
					count++;
				}
			}
			if(count > Clique_Tester.MAX_CLIQUE) {
				os.println("ERROR: too many cliques! - cutting off at "+Clique_Tester.MAX_CLIQUE+" for larger files change the default Clique_Tester.MAX_CLIQUE param");
				i=len;
			}
			if(i%t==0) {
				System.out.print(".");
			}
		} // for
		System.out.println();

		os.close();
		try {
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	Vector<Clique> allC_seed(Clique edge, int min_size, int max_size) {
		EnterAllCSeed++;
		Vector<Clique> ans = new Vector<Clique>();
		ans.add(edge);
		int i=0;
		//	int size = 2;
		while (ans.size()>i) {
			whileEnter++;
			Clique curr = ans.elementAt(i);
			if(curr.size()<max_size) {
				ifEnter++;
				VertexSet Ni = curr.commonNi();
				for(int a=0;a<Ni.size();a++) {
					forNi++;
					Clique c = new Clique(curr,Ni.at(a));
					ans.add(c);
				}
			}
			else {i=ans.size();} // speedup trick 
			i++;
		}
		return ans;
	}
	
	public void All_Cliques_DFS_2(String out_file, int min_size, int max_size) {
		Clique.init(this);
		Vector<VertexSet>C0 = allEdges(); // all edges – all cliques of size 2/
		int len = C0.size();
		System.out.println("|E|= "+len);
		int count = 0;

		FileWriter fw=null;
		try {fw = new FileWriter(out_file);} 
		catch (IOException e) {e.printStackTrace();}
		PrintWriter os = new PrintWriter(fw);
		//os.println("A");

		String ll = "0%   20%   40%   60%   80%   100%";
		int t = Math.max(1,len/ll.length());
		if(Clique_Tester.Debug){
			System.out.println("Computing all cliques of size["+min_size+","+max_size+"] based on "+len+" edges graph, this may take a while");
			System.out.println(ll);
		}
		os.println("All Cliques: file [min max] TH,"+this._file_name+","+min_size+", "+max_size+", "+this._TH);
		os.println("index, edge, clique size, c0, c1, c2, c3, c4,  c5, c6, c7, c8, c9");
		for(int i=0;i<len;i++) {
			forLen++;
			
			VertexSet curr_edge = C0.elementAt(i);
			Clique edge = new Clique(curr_edge.at(0),curr_edge.at(1) );
			
			if ((edge.clique().size()/*2*/ + edge.commonNi().size()) >= min_size) {/*Michal: changed num 1*/
				Vector<Clique> C1 = allC_seed_2(edge, min_size, max_size);
				for(int b=0;b<C1.size();b++) {
					forC1++;
					Clique c = C1.elementAt(b);
					if (c.size()>=min_size) {
						ifCount++;
						os.println(count+", "+i+","+c.size()+", "+c.toFile());
						count++;
					}
				}
				if(count > Clique_Tester.MAX_CLIQUE) {
					os.println("ERROR: too many cliques! - cutting off at "+Clique_Tester.MAX_CLIQUE+" for larger files change the default Clique_Tester.MAX_CLIQUE param");
					i=len;
				}
			}
		
			if(i%t==0) {
				System.out.print(".");
			}
		} // for
		System.out.println();

		os.close();
		try {
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	Vector<Clique> allC_seed_2(Clique edge, int min_size, int max_size) {
		EnterAllCSeed++;
		Vector<Clique> ans = new Vector<Clique>();
		ans.add(edge);
		int i=0;
		//	int size = 2;
		while (ans.size()>i) {
			whileEnter++;
			Clique curr = ans.elementAt(i);
			if(curr.size()<max_size) {
				ifEnter++;
				VertexSet Ni = curr.commonNi();
				for(int a=0;a<Ni.size();a++) {
					forNi++;
					Clique c = new Clique(curr,Ni.at(a));
					if ((c.clique().size() + c.commonNi().size()) >= min_size) /*Michal: changed num 2*/
						ans.add(c);
				}
			}
			else {i=ans.size();} // speedup trick 
			i++;
		}
		return ans;
	}


	/**
	 * Function for the new Graph format parsing.
	 */
	private void parseNewGraph() throws IOException {
		String[] nameOfFIle;
		if (System.getProperty("os.name").equals("Linux"))
			nameOfFIle = _file_name.split("/");
		else
			nameOfFIle = _file_name.split("\\\\"); // Don't have Windows. Have
													// to check it//

		fileName = nameOfFIle[nameOfFIle.length - 1];
		FileReader fr1 = null;
		try {
			fr1 = new FileReader(this._file_name);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		BufferedReader reader = new BufferedReader(fr1);
		String str = reader.readLine();
		try {
			VertexSet vertSet;
			numOfVertices = Integer.parseInt(str);
			str = reader.readLine();
			numOfEdges = Integer.parseInt(str);
			str = reader.readLine();
			int line = 0;
			_V = new Vector<VertexSet>();
			_V.setSize(numOfVertices);
			String ll = "0%   20%   40%   60%   80%   100%";
			int t = Math.max(1, numOfEdges / ll.length());
			if (Clique_Tester.Debug) {
				System.out.println("Reading default graph |E|=" + numOfEdges + " and |V|=" + numOfVertices
						+ " from file --> " + fileName + " \nthis may take a while");
				System.out.println(ll);
			}
			_mat_flag = true;

			while (str != null) {
				if (Clique_Tester.Debug) {
					if (line % t == 0)
						System.out.print(".");
				}
				String[] data1 = str.split("\\s+");
				float v = Float.parseFloat(data1[2]);
				int numOfLine = Integer.parseInt(data1[0]);
				int secondNode = Integer.parseInt(data1[1]);
				if (v > _TH) {
					if (_V.elementAt(numOfLine) == null) {
						vertSet = new VertexSet();
						vertSet.add(Integer.parseInt(data1[1]));
						_E_size++;
						_V.set(numOfLine, vertSet);
					} else {
						_V.get(numOfLine).add(secondNode);
						_E_size++;
					}
					if (_V.elementAt(secondNode) == null) {
						vertSet = new VertexSet();
						vertSet.add(numOfLine);
						_V.set(secondNode, vertSet);

					} else if (!_V.elementAt(secondNode).contains(numOfLine)) {
						_V.get(secondNode).add(numOfLine);
					}
				}
				str = reader.readLine();
				line++;
			}

			if (Graph._mat_flag & Clique_Tester.Convert) {
				write2file();
			}
			if (Clique_Tester.Debug) {
				System.out.println("");
				System.out.print("done reading the graph! ");
				this.print();
				C0 = allEdges();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Computes the size of the biggest clique in the whole graph
	 * @param The size of searching cliques
	 */
	public int sizeOfBiggestClique(int csize) {
		Clique.init(this);
		Queue<Clique> queue = new LinkedList<Clique>();
		int ans = 0;
		_VC = new Vector<Clique>();
		for (int i = 0; i < C0.size(); i++) {
			VertexSet currEdge = C0.elementAt(i);
			queue.offer(new Clique(currEdge.at(0), currEdge.at(1)));
		}
		while (!queue.isEmpty()) {
			Clique cl = queue.poll();

			if (cl.size() > ans)
				ans = cl.size();
			if (cl.size() + cl.commonNi().size() > ans) {
				for (int j = 0; j < cl.commonNi().size(); j++) {
					queue.offer(new Clique(cl, cl.commonNi().at(j)));
				}
			}
			if (csize > 1 && csize == ans) {
				_VC.addElement(cl);
			}
		}
		return ans;
	}

	public void getAllCliquesOfSize(String out_file, int csize) {
		if (csize > 1) {
			FileWriter fw = null;
			try {
				fw = new FileWriter(out_file);
			} catch (IOException e) {
				e.printStackTrace();
			}
			PrintWriter os = new PrintWriter(fw);
			for (int i = 0; i < _VC.size(); i++) {
				os.println(_VC.elementAt(i).toFile());
			}
			os.close();
			try {
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else
			System.out.println("There is not a clique of size " + csize);
	}

	private void init() {
		FileReader fr = null;
		try {
			fr = new FileReader(this._file_name);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		BufferedReader is = new BufferedReader(fr);
		try {
			String s = is.readLine();
			StringTokenizer st = new StringTokenizer(s, ", ");
			int len = st.countTokens();
			int line = 0;

			String ll = "0%   20%   40%   60%   80%   100%";
			int t = Math.max(1, len / ll.length());
			if (Clique_Tester.Debug) {
				System.out
						.println("Reading a corrolation matrix of size: " + len + "*" + len + " this may take a while");
				System.out.println(ll);
			}
			_mat_flag = true;
			if (s.startsWith("A")) {
				if (Clique_Tester.Debug) {
					System.out.println("Assumes compact representation! two line haeder!!!");
					System.out.println("Header Line1: " + s);
					s = is.readLine();
					System.out.println("Header Line2: " + s);
					s = is.readLine();
					st = new StringTokenizer(s, ", ");
					_mat_flag = false;
				}
			}

			while (s != null) {

				if (Clique_Tester.Debug) {
					if (line % t == 0)
						System.out.print(".");
				}
				VertexSet vs = new VertexSet();
				if (_mat_flag) {
					for (int i = 0; i < len; i++) {
						float v = new Double(st.nextToken()).floatValue();
						if (v > _TH & line < i) {
							vs.add(i);
							_E_size++;
						}
					}
				} else {
					st.nextToken();
					while (st.hasMoreTokens()) {
						int ind = new Integer(st.nextToken()).intValue();
						// bug fixed as for Ronens format.
						if (line < ind)
							vs.add(ind);
					}
				}
				_V.add(vs);
				line++;
				s = is.readLine();
				if (s != null)
					st = new StringTokenizer(s, ", ");
			}
			// end while

			if (_mat_flag & Clique_Tester.Convert) {
				write2file();
			}
			if (Clique_Tester.Debug) {
				System.out.println("");
				System.out.print("done reading the graph! ");
				this.print();
				C0 = allEdges();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public VertexSet Ni(int i) {
		VertexSet ans = _V.elementAt(i);
		return ans;
	}

	public void print() {
		System.out.println("Graph: |V|=" + _V.size() + " ,  |E|=" + _E_size);

	}

	
	/**
	 * computes all the 2 cliques --> i.e. all the edges
	 * 
	 * @return vector <VertexSet> with all edges. (Pairs of vertices)
	 */
	private Vector<VertexSet> allEdges() { // all edges ï¿½ all cliques of size 2/
		Vector<VertexSet> ans = new Vector<VertexSet>();
		for (int i = 0; i < _V.size(); i++) {
			if (_V.elementAt(i) != null) {
				VertexSet curr = _V.elementAt(i);
				for (int a = 0; a < curr.size(); a++) {
					if (i < curr.at(a)) {
						VertexSet tmp = new VertexSet();
						tmp.add(i);
						tmp.add(curr.at(a));
						ans.add(tmp);
					}
				}
			}
		}
		return ans;
	}


	/**
	 * this function simply add the clique (with no added intersection data) to
	 * the set of cliques)
	 * 
	 * @param ans
	 * @param C1
	 */
	@SuppressWarnings("unused")
	private void addToSet(Vector<VertexSet> ans, Vector<Clique> C1) {
		for (int i = 0; i < C1.size(); i++) {
			ans.add(C1.elementAt(i).clique());
		}
	}

	/*
	 * Vector<Clique> findMax(Clique edge, int min_size) { Vector<Clique> c2 =
	 * new Vector<Clique>(); c2.add(edge);
	 * 
	 * }
	 */

	// writing all cliques to .txt file
	public void write2file() {
		FileWriter fw = null;
		try {
			fw = new FileWriter(this._file_name + "_DG.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
		PrintWriter os = new PrintWriter(fw);
		os.println("ALL_Cliques: of file: " + _file_name + ",  TH:" + this._TH);
		os.println("");
		try {
			for (int i = 0; i < _V.size(); i++) {

				VertexSet curr = _V.elementAt(i);
				os.println(i + ", " + curr.toFile());
			}
		} catch (NullPointerException e) {
			System.out.println("\nThere are no edges in graph... maybe the threshold value is too big?");
		}

		os.close();
		try {
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * returns size of maximal clique
	 */

	public int Main_Max_Clique(Clique edge, int min_size, int max_size) {
		int ind = _AllEdges.size() - 1;
		int MAX = 2;
		while (ind >= 0) {
			VertexSet curr_edge = _AllEdges.elementAt(ind);
			Clique c = new Clique(curr_edge.at(0), curr_edge.at(1));
			MAX = max_per_edge(MAX, c);
			ind--;
		}
		return MAX;
	}

	/*
	 * help function for Main_Max_Clique
	 * 
	 */
	private int max_per_edge(int max, Clique edge) {
		int Answer = max;
		Vector<Clique> ans = new Vector<Clique>();
		ans.add(edge);
		int i = 0;
		// int size = 2;
		while (ans.size() > i) {
			Clique curr = ans.elementAt(i);
			VertexSet Ni = curr.commonNi();
			if ((curr.size() + Ni.size()) > Answer) {
				for (int a = 0; a < Ni.size(); a++) {
					Clique c = new Clique(curr, Ni.at(a));
					if ((c.size() + c.commonNi().size()) > Answer)
						ans.add(c);
					if (c.size() > Answer)
						Answer = c.clique().size();
				}
			} else {
				i = ans.size();
			} // speedup trick
			i++;
		}
		return Answer;
	}

	/*
	 * find all cliques of specific size
	 */

	Vector<Clique> find_clique(int num) {
		Vector<Clique> result = new Vector<Clique>();
		Vector<Clique> ans = new Vector<Clique>();
		int ind = _AllEdges.size() - 1;
		while (ind >= 0) {

			VertexSet curr_edge = _AllEdges.elementAt(ind);
			Clique c = new Clique(curr_edge.at(0), curr_edge.at(1));
			ans = all_cliqueV(c);
			ind--;
			int len = ans.size();

			for (int i = 0; i < len; i++) {
				if (ans.elementAt(i).size() == num)
					result.add(ans.elementAt(i));
			}

		}

		return result;
	}

	/*
	 * help function
	 */
	Vector<Clique> all_cliqueV(Clique edge) {
		Vector<Clique> ans = new Vector<Clique>();
		ans.add(edge);
		int i = 0;
		// int size = 2;
		while (ans.size() > i) {
			Clique curr = ans.elementAt(i);
			VertexSet Ni = curr.commonNi();
			for (int a = 0; a < Ni.size(); a++) {
				Clique c = new Clique(curr, Ni.at(a));
				ans.add(c);
			}
		} // speedup trick
		i++;
		return ans;
	}

}