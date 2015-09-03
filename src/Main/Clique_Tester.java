package Main;

import java.io.IOException;
import java.util.Date;

public class Clique_Tester {

	public static int minQ = 3, maxQ = 10;
	public static double TH = 0.8;
	public static double TH1 = 0.01;
	public static int sizeOfSClique=11;
	public static String in_file = System.getProperty("user.dir") + "/src/test1.csv";

	public static String in_file1 = System.getProperty("user.dir") + "/src/1000EWG.txt";
	public static String out_file = null;
	public static String out_file1 = null;
	
	public static boolean Debug = true;
	public static int MAX_CLIQUE = 100000;
	public static boolean Convert = true;

	public static void main(String[] args) throws IOException {

		if (args == null || args.length < 3) {
			help();
		} else {
			parse(args);
		}
		long t0 = new Date().getTime();
		//Graph G = new Graph(in_file, TH, true);
		long t1 = new Date().getTime();
		System.out.println("Init Graph: " + (t1 - t0) + "  ms");
		long t2 = new Date().getTime();

		if (out_file == null)
			out_file = in_file + "_" + TH + "_" + minQ + "_" + maxQ + ".csv";
	//	System.out.println("the biggest clique in the test1 graph is: " + G.sizeOfBiggestClique(sizeOfSClique));

	//	G.All_Cliques_DFS(out_file, minQ, maxQ);
		long t3 = new Date().getTime();
		System.out.println("Alg3: " + (t3 - t2) + "  ms");

		// We are working from here
		long t4 = new Date().getTime();
		if (out_file1 == null)
			out_file1 = in_file1 + "_" + TH + "_" + minQ + "_" + maxQ + ".csv";
		Graph G1 = new Graph(in_file1, TH1, false);

		G1.All_Cliques_DFS_2(out_file1, minQ, maxQ);
		long t5 = new Date().getTime();
		System.out.println("Our Alg: " + (t5 - t4) + "  ms");
		 System.out.println("The biggest clique in the graph is :"+G1.sizeOfBiggestClique(sizeOfSClique));
		 out_file1=null;
		 out_file1 = in_file1 + "_" + "cliques_of_size_"+sizeOfSClique+".csv";
		 G1.getAllCliquesOfSize(out_file1,sizeOfSClique);
	}

	static void help() {
		System.out.println(
				"Wrong Parameters! should use: java -jar All_Cliques <input file> <round value> <min clique> <max clique> <output file> <max_cliques> <Graph convert flag>");
		System.out.println(
				"Wrong Parameters! should use: java -jar All_Cliques test1.csv 0.7 5 7 test1_out.txt 10000 true");
	}

	static void parse(String[] a) {
		try {
			in_file = a[0];
			TH = new Double(a[1]);
			minQ = new Integer(a[2]);
			maxQ = new Integer(a[3]);
			sizeOfSClique=new Integer(a[4]);
			if (a.length > 4)
				out_file = a[4];
			if (a.length > 5)
				MAX_CLIQUE = new Integer(a[5]);
			if (a.length > 6)
				Convert = new Boolean(a[6]);
		} catch (Exception e) {
			e.printStackTrace();
			help();
		}
	}
}