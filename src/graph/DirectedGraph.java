package graph;

import java.util.Stack;

public class DirectedGraph extends Graph {

	public DirectedGraph() {
		super();
	}

	public DirectedGraph(int[][] graph) {
		super(graph);
	}

	public DirectedGraph(double[][] weighted) {
		super(weighted);
	}

	@Override
	public boolean addEdge(int f, int l) {
		if (f < 0 || f >= sumVertex() || l < 0 || l >= sumVertex()) {
			return false;
		}
		adjacency[f][l]++;
		return true;
	}

	@Override
	public boolean deleteEdge(int f, int l) {
		if (f < 0 || f >= sumVertex() || l < 0 || l >= sumVertex()) {
			return false;
		}
		if (adjacency[f][l] == 0) {
			return false;
		}
		adjacency[f][l]--;
		return true;
	}

	@Override
	public int deg(int v) {
		int deg = 0;
		for (int i = 0; i < adjacency.length; i++) {
			deg += adjacency[v][i];
			deg += adjacency[i][v];
		}
		return deg;
	}

	public int degIn(int vertex) {
		int deg = 0;
		for (int i = 0; i < adjacency.length; i++) {
			deg += adjacency[i][vertex];
		}
		return deg;
	}

	public int degOut(int vertex) {
		int deg = 0;
		for (int i = 0; i < adjacency.length; i++) {
			deg += adjacency[vertex][i];
		}
		return deg;
	}

	@Override
	public boolean isConnected() {
		int[][] unGrap = toUndirectGraph();
		boolean[] visited = new boolean[sumVertex()];
		Stack<Integer> stack = new Stack<>();
		int i = 0;
		stack.push(i);
		int count = 0;
		do {
			if (!visited[i]) {
				visited[i] = true;
				count++;
			}
			if (count == sumVertex())
				return true;
			for (int j = 0; j < sumVertex(); j++) {
				if (unGrap[i][j] != 0 && !visited[j]) {
					stack.push(i);
					i = j;
					break;
				} else {
					if ((j == sumVertex() - 1) && !stack.isEmpty()) {
						i = stack.pop();
					}
				}
			}
		} while (!stack.isEmpty());

		for (int j = 0; j < visited.length; j++) {
			if (!visited[j]) {
				return false;
			}
		}
		return true;
	}
	
	public boolean isStrongConnected() {
		boolean[] visited = new boolean[sumVertex()];
		Stack<Integer> stack = new Stack<>();
		int i = 0;
		stack.push(i);
		int count = 0;
		while (!stack.isEmpty()) {
			if (!visited[i]) {
				visited[i] = true;
				count++;
			}
			if (count == sumVertex())
				return true;
			for (int j = 0; j < sumVertex(); j++) {
				if (adjacency[i][j] != 0 && !visited[j]) {
					stack.push(i);
					i = j;
					break;
				} else {
					if ((j == sumVertex() - 1) && !stack.isEmpty()) {
						i = stack.pop();
					}
				}
			}
		}

		for (int j = 0; j < visited.length; j++) {
			if (!visited[j]) {
				return false;
			}
		}
		return true;
	}

	private int[][] toUndirectGraph() {
		int[][] graph = adjacency;
		for (int i = 0; i < adjacency.length; i++) {
			for (int j = i; j < adjacency.length; j++) {
				int sum = adjacency[i][j] + adjacency[j][i];
				graph[i][j] = sum;
				graph[j][i] = sum;
			}
		}
		return graph;
	}

	@Override
	public boolean isEulerCycle() {
		if (!isConnected())
			return false;
		for (int i = 0; i < adjacency.length; i++) {
			if (degIn(i) != degOut(i))
				return false;
		}
		return true;
	}

	@Override
	public boolean isEulerPath() {
		if (!isConnected())
			return false;
		int count = 0;
		int mark = 0;
		for (int i = 0; i < adjacency.length; i++) {
			if (degIn(i) != degOut(i)) {
				count++;
				mark += degIn(i) - degOut(i);
			}
		}
		return count == 2 && mark == 0;
	}

	@Override
	protected void addVirtualEdge() {
		int start = 0;
		int end = 0;
		for (int i = 0; i < adjacency.length; i++) {
			if ((degOut(i) - degIn(i)) == 1)
				start = i;
		}
		for (int i = 0; i < adjacency.length; i++) {
			if ((degIn(i) - degOut(i)) == 1)
				end = i;
		}
		deleteEdge(start, end);
	}

	@Override
	protected int findStartVertex() {
		for (int i = 0; i < adjacency.length; i++) {
			if ((degOut(i) - degIn(i)) == 1)
				return i;
		}
		return 0;
	}
	
	public int sumDegIn() {
		return sumDeg() / 2;
	}

	public int sumDegOut() {
		return sumDeg() / 2;
	}

}
