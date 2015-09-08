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
		Graph G = new Graph(in_file, TH, true);
		long t1 = new Date().getTime();
		System.out.println("Init Graph: " + (t1 - t0) + "  ms");

		if (out_file == null)
			out_file = in_file + "_" + TH + "_" + minQ + "_" + maxQ + ".csv";
		long t2 = new Date().getTime();
		G.All_Cliques_DFS(out_file, minQ, maxQ);
		long t3 = new Date().getTime();
		printTestAll_Cliques_DFS(true);
		initTest();	
		System.out.println("Alg3: " + (t3 - t2) + "  ms\nWith the graph built from file -->> "+parseFileName(in_file)+"\n");
		long diff=t3-t2;

		t2=new Date().getTime();
		G.All_Cliques_DFS_2(out_file, minQ, maxQ);
		t3=new Date().getTime();
		printTestAll_Cliques_DFS(false);
		initTest();	
		System.out.println("===========================================================================\n");
		System.out.println("Our improved algorithm takes: "+(t3-t2)+ " ms\nWith the graph built from file -->> "+parseFileName(in_file)+"\n"
				+
				"\nWe've improved the algorithm in "+ (diff-(t3-t2))+" ms\n");
		System.out.println("the biggest clique in the test1 graph is: " + G.sizeOfBiggestClique(sizeOfSClique));
		System.out.println("\n************************************************************************\n");


		if (out_file1 == null)
			out_file1 = in_file1 + "_" + TH + "_" + minQ + "_" + maxQ + ".csv";
		Graph G1 = new Graph(in_file1, TH1, false);

		t2=new Date().getTime();
		G1.All_Cliques_DFS(out_file1, minQ, maxQ);
		t3=new Date().getTime();
		diff=t3-t2;	
		printTestAll_Cliques_DFS(true);
		initTest();	

		System.out.println("Alg3: "+(t3-t2)+ " ms\nWith the graph built from file -->> "+parseFileName(in_file1));

		t2=new Date().getTime();
		G1.All_Cliques_DFS_2(out_file1, minQ, maxQ);
		t3=new Date().getTime();
		printTestAll_Cliques_DFS(false);
		initTest();	
		
		System.out.println("===============================================");
		System.out.println("Our improved algorithm takes: "+(t3-t2)+ " ms\nWith the graph built from file -->> "+parseFileName(in_file1)+"\n"
				+"==============================================\n"+
				"\nWe've improved the algorithm in "+ (diff-(t3-t2))+" ms\n"+"==============================================\n");
		System.out.println("The biggest clique in the graph is :"+G1.sizeOfBiggestClique(sizeOfSClique));
		System.out.println("\n************************************************************************\n");
		
		//printTestAll_Cliques_DFS();

		 out_file1=null;
		 out_file1 = in_file1 + "_" + "cliques_of_size_"+sizeOfSClique+".csv";
		 G1.getAllCliquesOfSize(out_file1,sizeOfSClique);
	}
	
	static void printTestAll_Cliques_DFS (boolean type) {
		if(type)
		System.out.println("\n+++++++ All_Cliques_DFS Test results: +++++++++++++++");
		else
			System.out.println("\n+++++++ All_Cliques_DFS_2 Test results: +++++++++++++++");

		System.out.println("All_Cliques_DFS:::::::");
		System.out.println("forLen\t=" + Graph.forLen);
		System.out.println("forC1\t=" + Graph.forC1);
		System.out.println("ifCount\t=" + Graph.ifCount);
		System.out.println("allC_seed:::::::::::::");
		System.out.println("EnterAllCSeed\t=" + Graph.EnterAllCSeed);
		System.out.println("whileEnter\t=" + Graph.whileEnter);
		System.out.println("ifEnter\t=" + Graph.ifEnter);
		System.out.println("forNi\t=" + Graph.forNi);
		System.out.println("===============================================");
	}
	public static void initTest(){
		Graph.forLen=0;
		Graph.forC1=0;
		Graph.ifCount=0;
		Graph.EnterAllCSeed=0;
		Graph.ifEnter=0;
		Graph.whileEnter=0;
		Graph.forNi=0;
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
		public static String parseFileName(String _file_name){
			String[] nameOfFIle;
			String fileName;
			if (System.getProperty("os.name").equals("Linux"))
				nameOfFIle = _file_name.split("/");
			else
				nameOfFIle = _file_name.split("\\\\"); // Don't have Windows. Have
														// to check it//

			 fileName = nameOfFIle[nameOfFIle.length - 1];
			return fileName;
		}
	}
