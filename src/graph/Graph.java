package graph;

import java.io.*;
import java.util.*;

public abstract class Graph {
	protected int[][] adjacency;
	protected double[][] weighted;

	public Graph() {
		adjacency = new int[0][0];
		weighted = new double[0][0];
	}

	public Graph(int[][] adjacency) {
		this.adjacency = adjacency;
	}

	public Graph(double[][] weighted) {
		this.weighted = weighted;
	}

	public abstract boolean addEdge(int f, int l);

	public abstract boolean deleteEdge(int f, int l);

	public int sumEdge() {
		return sumDeg() / 2;
	};

	public void addVertex() {
		int[][] newAdjMatrix = new int[sumVertex() + 1][sumVertex() + 1];
		for (int i = 0; i < sumVertex(); i++) {
			for (int j = 0; j < sumVertex(); j++) {
				newAdjMatrix[i][j] = adjacency[i][j];
			}
		}
		adjacency = newAdjMatrix;
	}

	public boolean deleteVertex(int v) {
		int[][] newAdjMatrix = new int[adjacency.length - 1][adjacency.length - 1];
		if (v < 0 || v >= adjacency.length) {
			return false;
		}
		for (int i = 0; i < v; i++) {
			for (int j = 0; j < v; j++) {
				newAdjMatrix[i][j] = adjacency[i][j];
			}
		}
		for (int i = v; i < newAdjMatrix.length; i++) {
			for (int j = v; j < newAdjMatrix.length; j++) {
				newAdjMatrix[i][j] = adjacency[i + 1][j + 1];
			}
		}
		adjacency = newAdjMatrix;
		return true;
	}

	public int sumVertex() {
		return this.adjacency.length;
	}

	public int sumDeg() {
		int deg = 0;
		for (int i = 0; i < sumVertex(); i++) {
			for (int j = 0; j < sumVertex(); j++) {
				deg += adjacency[i][j];
			}
		}
		return deg;
	};

	public abstract int deg(int v);

	public boolean loadAdjacencyGraph(String path) {
		try (InputStream is = new FileInputStream(path)) {
			BufferedReader buff = new BufferedReader(new InputStreamReader(is));
			String vLine = buff.readLine();
			int v = Integer.parseInt(vLine.trim());
			adjacency = new int[v][v];
			String line;
			int i = 0;
			while ((line = buff.readLine()) != null) {
				line = line.trim();
				String[] lineRead = line.split(" ");
				for (int j = 0; j < lineRead.length; j++) {
					adjacency[i][j] = Integer.parseInt(lineRead[j]);
				}
				i++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e1) {
			e1.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean loadWeightedGraph(String path) {
		try (InputStream is = new FileInputStream(path)) {
			BufferedReader buff = new BufferedReader(new InputStreamReader(is));
			String vLine = buff.readLine();
			int v = Integer.parseInt(vLine.trim());
			weighted = new double[v][v];
			String line;
			int i = 0;
			while ((line = buff.readLine()) != null) {
				line = line.trim();
				String[] lineRead = line.split(" ");
				for (int j = 0; j < lineRead.length; j++) {
					try {
						weighted[i][j] = Integer.parseInt(lineRead[j]);
					} catch (NumberFormatException e) {
						if ("+".equals(lineRead[j])) {
							weighted[i][j] = Double.POSITIVE_INFINITY;
						}
						if ("-".equals(lineRead[j])) {
							weighted[i][j] = Double.NEGATIVE_INFINITY;
						}
					}
				}
				i++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e1) {
			e1.printStackTrace();
			return false;
		}
		return true;
	}

	public abstract boolean isConnected();

	public boolean isConnected(int f, int l) {
		if (f == l)
			return true;
		boolean[] visited = new boolean[adjacency.length];
		Stack<Integer> stack = new Stack<>();
		int i = f;
		stack.push(i);
		while (!stack.isEmpty()) {
			visited[i] = true;
			for (int j = 0; j < adjacency.length; j++) {
				if (adjacency[i][j] != 0 && !visited[j]) {
					if (j == l) {
						return true;
					}
					stack.push(i);
					i = j;
					break;
				} else {
					if ((j == adjacency.length - 1) && !stack.isEmpty()) {
						i = stack.pop();
						break;
					}
				}
			}
		}
		return false;
	}

	public List<Integer> findConnectedPath(int f, int l) {
		boolean[] visited = new boolean[sumVertex()];
		Stack<Integer> stack = new Stack<>();
		LinkedList<Integer> result = new LinkedList<>();
		result.add(f);
		if (f == l) {
			return result;
		}
		int i = f;
		stack.push(i);
		while (!stack.isEmpty()) {
			visited[i] = true;
			for (int j = 0; j < sumVertex(); j++) {
				if (adjacency[i][j] != 0 && !visited[j]) {
					if (j == l) {
						result.add(j);
						return result;
					}
					stack.push(i);
					i = j;
					result.add(i);
					break;
				} else {
					if ((j == sumVertex() - 1) && !stack.isEmpty()) {
						i = stack.pop();
						result.removeLast();
					}
				}
			}
		}
		return result;
	}

	public int countConnectedComponent() {
		boolean[] visited = new boolean[sumVertex()];
		int i = 0;
		int connectComponent = 0;
		int count = 0;
		Stack<Integer> stack = new Stack<>();
		while (count < sumVertex()) {
			for (int j = 0; j < visited.length; j++) {
				if (!visited[j]) {
					stack.push(i);
					i = j;
					connectComponent++;
					break;
				}
			}
			while (!stack.isEmpty()) {
				if (!visited[i]) {
					visited[i] = true;
					count++;
				}
				if (count == sumVertex())
					return connectComponent;
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

		}
		return connectComponent;
	}

	public abstract boolean isEulerCycle();

	public List<Integer> findEulerCycle() {
		int[][] tmp = adjacency;
		List<Integer> result = new ArrayList<>();
		int sumEgde = sumEdge();
		int v = 0;
		result.add(v);
		while (sumEgde > 0) {
			for (int i = 0; i < result.size(); i++) {
				if (deg(result.get(i)) > 0) {
					List<Integer> subCycle = findSubCycle(result.get(i));
					result.addAll(i + 1, subCycle);
					sumEgde -= subCycle.size();
					break;
				}
			}
		}
		adjacency = tmp;
		return result;
	}

	private List<Integer> findSubCycle(int v) {
		int i = v;
		List<Integer> subCycle = new ArrayList<>();
		do {
			for (int j = 0; j < adjacency.length; j++) {
				if (adjacency[i][j] > 0) {
					subCycle.add(j);
					deleteEdge(i, j);
					i = j;
					break;
				}
			}
		} while (i != v);
		return subCycle;
	}

	public abstract boolean isEulerPath();

	public List<Integer> findEulerPath() {
		int[][] tmp = clone(adjacency);
		LinkedList<Integer> result = new LinkedList<>();
		int v = findStartVertex();
		addVirtualEdge();
		result.add(v);
		int sumEgde = sumEdge();
		while (sumEgde > 0) {
			for (int i = 0; i < result.size(); i++) {
				if (deg(result.get(i)) > 0) {
					List<Integer> subCycle = findSubCycle(result.get(i));
					result.addAll(i + 1, subCycle);
					sumEgde -= subCycle.size();
					break;
				}
			}
		}
		result.removeLast();
		adjacency = tmp;
		return result;
	}

	protected abstract void addVirtualEdge();

	protected abstract int findStartVertex();

	private int[][] clone(int[][] data) {
		int[][] result = new int[data.length][];

		for (int i = 0; i < data.length; i++) {
			result[i] = data[i].clone();
		}

		return result;
	}

	public boolean isBipartite() {
		int[] color = new int[sumVertex()];
		Stack<Integer> stack = new Stack<>();
		int i = 0;
		color[i] = 1;
		int count = 1;
		while (count < color.length) {
			for (int j = 0; j < color.length; j++) {
				if (adjacency[i][j] > 0) {
					if (color[j] == 0) {
						color[j] = -color[i];
						stack.push(j);
						count++;
					} else if (color[j] == color[i]) {
						return false;
					}
				}
			}
			if (!stack.isEmpty()) {
				i = stack.pop();
			} else {
				for (int j = 0; j < color.length; j++) {
					if (color[j] == 0) {
						i = j;
					}
				}
			}
		}
		return true;
	}

	public List<Integer> dfs() {
		List<Integer> result = new ArrayList<>();
		boolean[] visited = new boolean[sumVertex()];
		Stack<Integer> stack = new Stack<>();
		int i = 0;
		while (result.size() < sumVertex()) {
			for (int j = 0; j < visited.length; j++) {
				if (!visited[j]) {
					i = j;
					stack.push(i);
					break;
				}
			}
			while (!stack.isEmpty()) {
				if (!visited[i]) {
					visited[i] = true;
					result.add(i);
				}
				if (result.size() == sumVertex())
					return result;
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

		}
		return result;
	}

	public List<Integer> dfsRecursive() {
		List<Integer> visitedList = new ArrayList<>();
		for (int i = 0; i < sumVertex(); i++) {
			if (!visitedList.contains(i)) {
				dfsExt(i, visitedList);
			}
		}
		return visitedList;
	}

	private List<Integer> dfsExt(int node, List<Integer> visitedList) {
		visitedList.add(node);
		for (int i = 0; i < sumVertex(); i++) {
			if (adjacency[node][i] > 0 && !visitedList.contains(i)) {
				dfsExt(i, visitedList);
			}
		}
		return visitedList;
	}

	public List<Integer> bfs() {
		List<Integer> result = new ArrayList<>();
		Queue<Integer> queue = new LinkedList<>();
		boolean[] visited = new boolean[sumVertex()];
		int i;
		for (int v = 0; v < visited.length; v++) {
			if (!queue.contains(v)) {
				queue.offer(v);
			}
			while (!queue.isEmpty()) {
				i = queue.poll();
				if (!visited[i]) {
					visited[i] = true;
					result.add(i);
				}
				for (int j = 0; j < sumVertex(); j++) {
					if (adjacency[i][j] > 0 && !visited[j]) {
						queue.offer(j);
					}
				}
			}
		}
		return result;
	}

	public String printAdjacencyGraph() {
		StringBuffer s = new StringBuffer();
		for (int i = 0; i < sumVertex(); i++) {
			s.append("[");
			for (int j = 0; j < sumVertex(); j++) {
				s.append(adjacency[i][j] + (j < sumVertex() - 1 ? ", " : ""));
			}
			s.append("]\n");
		}
		return s.toString();
	}

	public String printWeightGraph() {
		StringBuffer s = new StringBuffer();
		for (int i = 0; i < weighted.length; i++) {
			s.append("[");
			for (int j = 0; j < weighted.length; j++) {
				if (weighted[i][j] == Double.POSITIVE_INFINITY) {
					s.append("+" + (j < weighted.length - 1 ? ", " : ""));
				} else if (weighted[i][j] == Double.NEGATIVE_INFINITY) {
					s.append("-" + (j < weighted.length - 1 ? ", " : ""));
				} else {
					s.append((int) weighted[i][j] + (j < weighted.length - 1 ? ", " : ""));
				}
			}
			s.append("]\n");
		}
		return s.toString();
	}

	public List<Integer> dijkstraAB(int a, int b) {
		ArrayList<Integer> r = new ArrayList<Integer>();
		double[] l = new double[weighted.length];
		int[] p = new int[weighted.length];
		for (int i = 0; i < weighted.length; i++) {
			r.add(i);
			l[i] = Double.POSITIVE_INFINITY;
			p[i] = -1;
		}
		l[a] = 0;
		while (!r.isEmpty()) {
			int minIndex = r.get(0);
			for (int i = 0; i < r.size(); i++) {
				if (l[minIndex] > l[r.get(i)]) {
					minIndex = r.get(i);
				}
			}
			r.remove(r.indexOf(minIndex));
			for (int i = 0; i < weighted.length; i++) {
				if (weighted[minIndex][i] != Double.POSITIVE_INFINITY) {
					if (l[i] > (weighted[minIndex][i] + l[minIndex])) {
						l[i] = weighted[minIndex][i] + l[minIndex];
						p[i] = minIndex;
					}
				}
			}
		}
		List<Integer> result = new ArrayList<Integer>();
		result.add(b);
		int tmp = b;
		while (tmp != a) {
			result.add(0, p[tmp]);
			tmp = p[tmp];
		}
		return result;
	}

	public List<List<Integer>> dijkstra(int a) {
		List<List<Integer>> result = new ArrayList<List<Integer>>();
		for (int i = 0; i < weighted.length; i++) {
			if (i != a) {
				result.add(dijkstraAB(a, i));
			}
		}
		return result;
	}

	// cho do thi co huong
	public double[][] floyd() {
		double[][] w0 = weighted;
		double[][] wk = new double[weighted.length][weighted.length];
		int[][] p0 = new int[weighted.length][weighted.length];
		int[][] pk = new int[weighted.length][weighted.length];
		for (int i = 0; i < p0.length; i++) {
			for (int j = 0; j < p0.length; j++) {
				if (w0[i][j] != Double.POSITIVE_INFINITY) {
					p0[i][j] = j;
				} else {
					p0[i][j] = -1;
				}
			}
		}
		for (int k = 0; k < w0.length; k++) {
			for (int i = 0; i < w0.length; i++) {
				for (int j = 0; j < w0.length; j++) {
					if (w0[i][j] > (w0[i][k] + w0[k][j])) {
						wk[i][j] = w0[i][k] + w0[k][j];
						pk[i][j] = p0[i][k];
					} else {
						wk[i][j] = w0[i][j];
						pk[i][j] = p0[i][j];
					}
				}
			}
			w0 = wk;
			p0 = pk;
		}
		return wk;
	}

	public List<List<Integer>> bellmanFord(int s) {
		boolean stop = false;
		List<Integer> p = new ArrayList<>();
		List<Double> l = new ArrayList<>();
		for (int i = 0; i < weighted.length; i++) {
			p.add(-1);
			l.add(Double.POSITIVE_INFINITY);
		}
		l.set(s, 0.0);
		while (!stop) {
			stop = true;
			for (int i = 0; i < weighted.length; i++) {
				for (int j = 0; j < weighted.length; j++) {
					if ((l.get(j) > l.get(i) + weighted[i][j])) {
						l.set(j, l.get(i) + weighted[i][j]);
						p.set(j, i);
						stop = false;
					}
				}
			}
		}
		System.out.println(p);
		System.out.println(l);
		return null;
	}

	public boolean findPath(int u, int v) {
		if (u == v) {
			System.out.println(v);
			return true;
		}
		for (int i = 0; i < adjacency.length; i++) {
			if (adjacency[u][i] > 0 && adjacency[i][v] > 0 && i != v) {
				System.out.println(u + " ==> " + i + " ==> " + v);
				return true;
			} else if (adjacency[u][i] > 0) {
					System.out.print(u + " ==> ");
					findPath(i, v);
			}
		}
		return false;
	}
}
