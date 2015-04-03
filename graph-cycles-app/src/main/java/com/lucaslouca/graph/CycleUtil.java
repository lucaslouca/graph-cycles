package com.lucaslouca.graph;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CycleUtil {
	private Graph originalGraph;

	public CycleUtil(Graph originalGraph) {
		this.originalGraph = originalGraph;
	}

	/**
	 * Returns all cycles in the Graph.
	 * 
	 * @return A <code>List</code> of <code>Graph</code> holding all the cycles.
	 * 
	 * @throws <code>IllegalArgumentException</code> if graph is
	 *         <code>null</code>.
	 */
	public List<Graph> listAllCycles() {
		if (originalGraph == null) {
			throw new IllegalArgumentException("Graph cannot be null!");
		}
		List<List<Vertex>> cycleBasis = computeCycleBasisOfGraph(originalGraph);
		List<Graph> allCycles = listAllCyclesFromBasis(originalGraph, cycleBasis);
		return allCycles;
	}

	/**
	 * Computes and returns a cycle basis of <code>Graph</code> g.
	 * 
	 * A cycle basis of an undirected graph is a set of simple cycles that forms
	 * a basis of the cycle space of the graph. That is, it is a minimal set of
	 * cycles that allows every Eulerian subgraph to be expressed as a symmetric
	 * difference of basis cycles.
	 * 
	 * @see http://en.wikipedia.org/wiki/Cycle_basis
	 * 
	 * @param g
	 *            the <code>Graph</code> object for which we want to compute the
	 *            cycle basis.
	 * 
	 * @return A <code>List</code> of cycles (<code>List</code> of
	 *         <code>Vertex</code>) that form the cycle basis of <code>g</code>.
	 */
	private List<List<Vertex>> computeCycleBasisOfGraph(Graph g) {
		// Copy of the original Graph. We need a copy because we are going to alter it.
		Graph graphCopy = new Graph(new ArrayList<Vertex>(g.getVertices()), new ArrayList<Edge>(g.getEdges()));

		List<Edge> backEdges = new ArrayList<Edge>(graphCopy.getEdges());

		// Create a minimal spanning tree and its associated set of back edges.
		Graph minimalSpanningTree = createMinimalSpanningTree(graphCopy);

		backEdges.removeAll(minimalSpanningTree.getEdges());
		//backEdges = cleanEdges(backEdges);

		// If e in B is a back edge, insert it into the minimal spanning tree's
		// edges E to form a set E' = E + {e}. The resulting graph G = (V, E')
		// has exactly one cycle, which may be constructed by applying a
		// depth-first search.
		List<List<Vertex>> cycles = new ArrayList<List<Vertex>>();
		for (Edge backEdge : backEdges) {
			Edge forward = new Edge(backEdge.getSource(), backEdge.getDestination());
			Edge backwards = new Edge(backEdge.getDestination(), backEdge.getSource());

			// Only process edges for which we haven't already processed its opposite
			if (backEdges.indexOf(backwards) > backEdges.indexOf(forward)) {
				minimalSpanningTree.getEdges().add(forward);
				minimalSpanningTree.getEdges().add(backwards);

				findCycle(minimalSpanningTree, forward.getSource(), forward.getSource(), new HashSet<Vertex>(), new ArrayList<Vertex>(), cycles);

				minimalSpanningTree.getEdges().remove(forward);
				minimalSpanningTree.getEdges().remove(backwards);
			}
		}

		return cycles;
	}

	/**
	 * Computes and returns all cycles in the Graph based on a cycle basis.
	 * 
	 * @param g
	 *            the <code>Graph</code>
	 * @param basisCycles
	 *            <code>List</code> of cycles (<code>List</code> of
	 *            <code>Vertex</code>) that form the cycle basis of
	 *            <code>g</code>.
	 * @return A <code>List</code> of <code>Graph</code> representing all the
	 *         cycles.
	 * 
	 */
	private List<Graph> listAllCyclesFromBasis(Graph g, List<List<Vertex>> basisCycles) {
		// Now that we got all the base cycles we can create their incidence
		// vectors
		List<BigInteger> incidenceVectors = new ArrayList<BigInteger>();
		for (List<Vertex> cycle : basisCycles) {
			BigInteger iv = incidenceVectorOfCycle(cycle, g.getEdges());
			incidenceVectors.add(iv);
		}

		// Create all possible combinations of incidence vectors
		List<List<BigInteger>> powerSet = powerSet(incidenceVectors);

		// Create new incidence vector combination by xoring the vectors in each combinatio together
		List<BigInteger> incidenceCombinations = new ArrayList<BigInteger>();
		for (List<BigInteger> combination : powerSet) {
			if (combination.size() > 0) {
				BigInteger result = combination.get(0);
				for (int i = 1; i < combination.size(); i++) {
					result = result.xor(combination.get(i));
				}
				incidenceCombinations.add(result);
			}
		}

		List<Graph> allCycles = new ArrayList<Graph>();
		for (BigInteger incidenceVector : incidenceCombinations) {
			Graph cycle = cycleFromIncidenceVector(incidenceVector, g.getEdges());
			allCycles.add(cycle);
		}

		return allCycles;
	}

	/**
	 * Generates and returns a minimal spanning tree of Graph g.
	 * 
	 * @param g
	 *            the <code>Graph</code> object
	 * @return A minimal spanning tree of <code>g</code>
	 */
	private Graph createMinimalSpanningTree(Graph g) {
		// Create a minimal spanning tree and its associated set of back edges.
		Vertex start = g.getVertices().get(0);

		// Compute back-edges
		Set<Edge> backEdges = backEdges(g, start, start, new HashSet<Vertex>(), new HashSet<Edge>());

		// A minimal spanning tree is basically the original graph (all vertices) but without the back-edges 
		List<Edge> spanningTreeEdges = new ArrayList<Edge>(g.getEdges());
		spanningTreeEdges.removeAll(backEdges);
		Graph minimalSpanningTree = new Graph(g.getVertices(), spanningTreeEdges);
		return minimalSpanningTree;
	}

	/**
	 * Returns a Set of back edges of Graph g while removing these edges from
	 * the Graph.
	 * 
	 * It is a DFS where we mark an edge as a back-edge (and remove it from the
	 * graph) if it leads to an already visited vertex.
	 * 
	 * @param g
	 *            The graph
	 * @param root
	 *            from which vertex we come from (aka parent/root)
	 * @param current
	 *            the current vertex
	 * @param visited
	 *            Set of already visited vertices
	 * @param backEdges
	 *            Set of found back-edges
	 * 
	 * @return Set<Edge> of back-edges
	 */
	private Set<Edge> backEdges(Graph g, Vertex root, Vertex current, Set<Vertex> visited, Set<Edge> backEdges) {
		visited.add(current);

		for (Vertex n : g.getNeighbors(current)) {
			if (!visited.contains(n)) {
				backEdges(g, current, n, visited, backEdges);
			} else if (!n.equals(root)) {
				// Found a back-edge
				Edge edge1 = new Edge(current, n);
				Edge edge2 = new Edge(n, current);

				// Remove from graph
				g.removeEdge(edge1);
				g.removeEdge(edge2);

				// Add to result set
				backEdges.add(edge1);
				backEdges.add(edge2);
			}
		}

		return backEdges;
	}

	/**
	 * 
	 * Finds the cycle in <code>Graph g</code>
	 * 
	 * @param g
	 *            the <code>Graph</code>
	 * @param root
	 *            Start vertex
	 * @param current
	 *            Current vertex
	 * @param visited
	 *            Set of already visited �vertices
	 * @param stack
	 *            Stack that hold the sequence of vertices on the path
	 */
	private void findCycle(Graph g, Vertex root, Vertex current, Set<Vertex> visited, List<Vertex> stack, List<List<Vertex>> basisCycles) {
		visited.add(current);
		stack.add(current);

		for (Vertex n : g.getNeighbors(current)) {
			if (!visited.contains(n)) {
				findCycle(g, current, n, visited, stack, basisCycles);
			} else if (!n.equals(root) && stack.contains(n)) {
				// Found a cycle, add to result set
				basisCycles.add(new ArrayList<Vertex>(stack));
			}
		}

		stack.remove(current);
	}

	/**
	 * Generates the incidence vector of a cycle.<br>
	 * <br>
	 * 
	 * Assume that the ordered list of all edges in the original Graph is: <br>
	 * <br>
	 * 
	 * (1,2) (2,3) (2,6) (3,4) (3,6) (4,5) (4,6) (5,6) <br>
	 * <br>
	 * 
	 * The incidence vector of cycle (2,3,4,5,6,2) is 01110101 and the one of
	 * cycle (3,4,5,6,3) is 00011101.
	 * 
	 * @see http://dspace.mit.edu/bitstream/handle/1721.1/68106/FTL_R_1982_07
	 *      .pdf
	 * 
	 * @param cycle
	 *            List<Vertex> list of vertices in the cycle
	 * @param originalEdges
	 *            The ordered list of edges in the original Graph
	 * @return The incidence vector describing the <code>cycle</code> in form of
	 *         a <code>BigInteger</code> based on <code>originalEdges</code>.
	 */
	private BigInteger incidenceVectorOfCycle(List<Vertex> cycle, List<Edge> originalEdges) {
		StringBuilder sb = new StringBuilder();

		// Create list of the edges that build up the cycle
		List<Edge> cycleEdges = new ArrayList<Edge>();
		for (int i = 0; i < cycle.size() - 1; i++) {
			cycleEdges.add(new Edge(cycle.get(i), cycle.get(i + 1)));
			cycleEdges.add(new Edge(cycle.get(i + 1), cycle.get(i)));
		}
		cycleEdges.add(new Edge(cycle.get(0), cycle.get(cycle.size() - 1)));
		cycleEdges.add(new Edge(cycle.get(cycle.size() - 1), cycle.get(0)));

		// Create incidence vector
		for (Edge oe : originalEdges) {
			if (cycleEdges.contains(oe)) {
				sb.append("1");
			} else {
				sb.append("0");
			}
		}

		BigInteger mask = new BigInteger(sb.toString(), 2);
		return mask;
	}

	/**
	 * Generates a cycle out of an incidence vector.<br>
	 * <br>
	 * 
	 * Assume that the ordered list of all edges in the original Graph is: <br>
	 * <br>
	 * 
	 * (1,2) (2,3) (2,6) (3,4) (3,6) (4,5) (4,6) (5,6) <br>
	 * <br>
	 * 
	 * The incidence vector of cycle (2,3,4,5,6,2) is 01110101.
	 * 
	 * @param vector
	 *            A BigInteger representing the incidence vector of a cycle
	 * @param originalEdges
	 *            An ordered list containing all the edges of a Graph
	 * @return A <code>Graph</code> object representing the cycle described by
	 *         the incidence vector <code>vector</code> based on
	 *         <code>originalEdges</code>.
	 */
	private Graph cycleFromIncidenceVector(BigInteger vector, List<Edge> originalEdges) {
		List<Vertex> cycleVertices = new ArrayList<Vertex>();
		List<Edge> cycleEdges = new ArrayList<Edge>();

		for (int i = 0; i < originalEdges.size(); i++) {
			if (vector.testBit(i)) {
				// because when we generated our incidence vector string '1xxxx' the 1 was at index 0. But big integer index 0 is the string's index 4
				Edge oe = originalEdges.get(originalEdges.size() - 1 - i);
				Vertex source = oe.getSource();
				Vertex destination = oe.getDestination();
				if (cycleVertices.contains(source) == false) {
					cycleVertices.add(source);
				}
				if (cycleVertices.contains(destination) == false) {
					cycleVertices.add(destination);
				}

				cycleEdges.add(oe);
			}
		}

		return new Graph(cycleVertices, cycleEdges);
	}

	/**
	 * Given a <code>List</code> create and return the power-set of that list.
	 * 
	 * @param list
	 *            A <code>List</code> from which we want to obtain its power-set
	 * @return The power-set of <code>list</code>.
	 */
	private <T> List<List<T>> powerSet(List<T> list) {
		List<List<T>> powerSet = new ArrayList<List<T>>();

		powerSet.add(new ArrayList<T>());

		for (T e : list) {
			List<List<T>> newPowerSet = new ArrayList<List<T>>();
			for (List<T> subSet : powerSet) {
				newPowerSet.add(subSet);

				List<T> newSubSet = new ArrayList<T>();
				newSubSet.addAll(subSet);
				newSubSet.add(e);

				newPowerSet.add(newSubSet);
			}

			powerSet = newPowerSet;
		}
		return powerSet;
	}

}
