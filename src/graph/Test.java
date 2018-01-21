package graph;

public class Test {
	public static void main(String[] args) {
//		System.out.println(111);
//		UndirectedGraph g = new UndirectedGraph();
//		g.loadWeightedGraph("E:\\LTDTData\\un-wg-9.txt");
//		System.out.println(g.printWeightGraph());
//		System.out.println(g.kruskal().printWeightGraph());
		
//		Graph g1 = new UndirectedGraph();
//		g1.loadGraph("e:\\LTDTData\\un-7-01.txt");
//		g1.loadGraph("e:\\LTDTData\\un-uc-7-01.txt");
//		System.out.println(g1.dfs());
//		System.out.println(g1.bfs());
//		System.out.println(g1.isConnected(3, 0));
//		System.out.println(g1.findConnectedPath(3, 0));
		
//		Graph g2 = new DirectedGraph();
//		g2.loadGraph("e:\\LTDTData\\di-5.txt");
//		System.out.println(g2);
//		System.out.println(g2.dfsRecursive());
		
//		DirectedGraph g3 = new DirectedGraph();
//		g3.loadGraph("E:\\LTDTData\\di-6-01.txt");
//		System.out.println(g3.isEulerCycle());
//		System.out.println(g3.eulerCycle());
		
//		Graph g4 = new UndirectedGraph();
//		g4.loadGraph("E:\\LTDTData\\un-5-02.txt");
//		System.out.println(g4);
//		System.out.println(g4.isEulerCycle());
//		System.out.println(g4.findEulerCycle());
		
//		Graph g5 = new UndirectedGraph();
//		g5.loadGraph("E:\\LTDTData\\un-5-03.txt");
//		System.out.println(g5);
//		System.out.println(g5.isEulerPath());
//		System.out.println(g5.findEulerPath());
		
//		UndirectedGraph g6 = new UndirectedGraph();
//		g6.loadGraph("E:\\LTDTData\\un-6-01.txt");
//		System.out.println(g6.isBipatiteCompeleGraph());
		
//		UndirectedGraph g7 = new UndirectedGraph();
//		g7.loadWeightGraph("E:\\LTDTData\\un-wg-7.txt");
//		System.out.println(g7.printWeightGraph());
//		System.out.println(g7.dijkstra(1));
//		g7.loadWeightGraph("E:\\Download\\10.txt");
//		System.out.println(g7.dijkstraAB(3, 9));
		
//		DirectedGraph g8 = new DirectedGraph();
//		g8.loadWeightGraph("E:\\LTDTData\\di-wg-4.txt");
//		System.out.println(g8.floyd());
		
//		UndirectedGraph g9 = new UndirectedGraph();
//		g9.loadGraph("E:\\\\LTDTData\\\\un-tree-8.txt");
//		System.out.println(g9.isTree());
		
//		UndirectedGraph g10 = new UndirectedGraph();
//		g10.loadWeightedGraph("E:\\LTDTData\\un-wg-6.txt");
//		g10.bellmanFord(5);
		
		DirectedGraph g11 = new DirectedGraph();
		g11.loadAdjacencyGraph("E:\\LTDTData\\di-4-01.txt");
		System.out.println(g11.printAdjacencyGraph());
		g11.findPath(2, 3);
	}
}
