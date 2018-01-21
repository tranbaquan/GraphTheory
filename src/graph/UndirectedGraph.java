package graph;

import java.util.*;

public class UndirectedGraph extends Graph {

	public UndirectedGraph() {
		super();
	}

	public UndirectedGraph(int[][] adjacency) {
		super(adjacency);
	}

	public UndirectedGraph(double[][] weighted) {
		super(weighted);
	}

	@Override
	public boolean addEdge(int f, int l) {
		if (f < 0 || f >= sumVertex() || l < 0 || l >= sumVertex()) {
			return false;
		}
		adjacency[f][l]++;
		adjacency[l][f]++;
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
		adjacency[l][f]--;
		return true;
	}

	@Override
	public int deg(int v) {
		int deg = 0;
		for (int i = 0; i < adjacency.length; i++) {
			deg += adjacency[v][i];
		}
		return deg;
	}

	@Override
	public boolean isConnected() {
		boolean[] visited = new boolean[sumVertex()];
		Stack<Integer> stack = new Stack<>();
		int i = 0;
		int count = 0;
		stack.push(i);
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

	@Override
	public boolean isEulerCycle() {
		if (!isConnected())
			return false;
		for (int i = 0; i < adjacency.length; i++) {
			if (deg(i) % 2 != 0)
				return false;
		}
		return true;
	}

	@Override
	public boolean isEulerPath() {
		if (!isConnected())
			return false;
		int count = 0;
		for (int i = 0; i < adjacency.length; i++) {
			if (deg(i) % 2 != 0)
				count++;
		}
		return count == 2;
	}

	@Override
	protected void addVirtualEdge() {
		List<Integer> v = new ArrayList<>();
		for (int i = 0; i < adjacency.length; i++) {
			if (deg(i) % 2 == 1) {
				v.add(i);
			}
		}
		addEdge(v.get(0), v.get(1));
	}

	@Override
	protected int findStartVertex() {
		for (int i = 0; i < adjacency.length; i++) {
			if (deg(i) % 2 == 1)
				return i;
		}
		return 0;
	}
	
	public boolean isCompleteGraph() {
		if (!isConnected())
			return false;
		for (int i = 0; i < adjacency.length; i++) {
			if (deg(i) != sumVertex() - 1)
				return false;
		}
		return true;
	}
	
	public boolean isBipatiteCompeleGraph() {
		if (!isConnected())
			return false;
		if (!isBipartite())
			return false;
		int[] color = new int[sumVertex()];
		color[0] = 1;
		int other = 0;
		int count = 0;
		for (int i = 0; i < color.length; i++) {
			if (adjacency[0][i] > 0) {
				color[i] = -color[0];
				other = i;
				count++;
			}
		}
		for (int i = 0; i < color.length; i++) {
			if (adjacency[other][i] > 0) {
				color[i] = -color[other];
			}
		}
		for (int i = 0; i < color.length; i++) {
			if (color[i] == 1) {
				if (deg(i) != count)
					return false;
			}
			if (color[i] == -1) {
				if (deg(i) != (sumVertex() - count))
					return false;
			}
		}
		return true;
	}

	public boolean isTree() {
		if (sumVertex() - sumEdge() != 1)
			return false;
		if (!isConnected())
			return false;
		for (int i = 0; i < adjacency.length; i++) {
			for (int j = 0; j < adjacency.length; j++) {
				if (adjacency[i][j] > 1)
					return false;
			}
		}
		return true;
	}

	public int eccentricity(int v) {
		int max = 0;
		for (int i = 0; i < adjacency.length; i++) {
			int pathSize = findConnectedPath(v, i).size() - 1;
			if (pathSize > max)
				max = pathSize;
		}
		return max;
	}

	public int centerVertex() {
		int min = 0;
		for (int i = 0; i < adjacency.length; i++) {
			if (eccentricity(min) > eccentricity(i))
				min = i;
		}
		return min;
	}

	public List<Integer> centerVerties() {
		List<Integer> res = new ArrayList<>();
		int min = 0;
		for (int i = 0; i < adjacency.length; i++) {
			if (eccentricity(min) > eccentricity(i))
				min = i;
		}
		int ce = eccentricity(min);
		for (int i = 0; i < adjacency.length; i++) {
			if (ce == eccentricity(i))
				res.add(i);
		}
		return res;
	}

	public int radiusTree() {
		return eccentricity(centerVertex());
	}

	public Graph spanningTreeBFS() {
		UndirectedGraph tree = new UndirectedGraph(new int[adjacency.length][adjacency.length]);
		Queue<Integer> queue = new LinkedList<>();
		queue.offer(0);
		int i;
		while (!queue.isEmpty()) {
			i = queue.poll();
			for (int j = 0; j < sumVertex(); j++) {
				if (adjacency[i][j] > 0) {
					if (!queue.contains(j) && deg(j)==0) {
						queue.offer(j);
						tree.addEdge(i, j);
					}
				}
			}
		}
		return tree;
	}
	public Graph spanningTreeDFS() {
		UndirectedGraph tree = new UndirectedGraph(new int[adjacency.length][adjacency.length]);
		visit(0, tree);
		return tree;
	}

	private void visit(int i, UndirectedGraph tree) {
		for (int j = 0; j < adjacency.length; j++) {
			if (adjacency[i][j] > 0) {
				if(deg(j)==0) {
					tree.addEdge(i, j);
					visit(j, tree);
				}
			}
		}
	}
	
	public void addWeightedEgde(int f, int l, double w) {
		this.weighted[f][l] = w;
		this.weighted[l][f] = w;
	}
	public void deleteWeightedEgde(int f, int l) {
		this.weighted[f][l] = Double.POSITIVE_INFINITY;
		this.weighted[l][f] = Double.POSITIVE_INFINITY;
	}
	
	public boolean isConnectedWeighted(int f, int l) {
		if (f == l)
			return true;
		boolean[] visited = new boolean[weighted.length];
		Stack<Integer> stack = new Stack<>();
		int i = f;
		stack.push(i);
		while (!stack.isEmpty()) {
			visited[i] = true;
			for (int j = 0; j < weighted.length; j++) {
				if (weighted[i][j] != Double.POSITIVE_INFINITY && !visited[j]) {
					if (j == l) {
						return true;
					}
					stack.push(i);
					i = j;
					break;
				} else {
					if ((j == weighted.length - 1) && !stack.isEmpty()) {
						i = stack.pop();
						break;
					}
				}
			}
		}
		return false;
	}
	
	public Graph kruskal() {
		UndirectedGraph clone = this;
		UndirectedGraph mst = new UndirectedGraph(new double[weighted.length][weighted.length]);
		int count = 0;
		while (count < weighted.length-1) {
			double min = Double.POSITIVE_INFINITY;
			int f = 0;
			int l = 0;
			for (int i = 0; i < weighted.length; i++) {
				for (int j = 0; j < weighted.length; j++) {
					if(clone.weighted[i][j] < min) {
						min = clone.weighted[i][j];
						f = i;
						l = j;
					}
				}
			}
			if(!isConnectedWeighted(f, l)) {
				mst.addWeightedEgde(f, l, min);
				count++;
			}
			clone.deleteWeightedEgde(f, l);
		}
		return mst;
	}
}
