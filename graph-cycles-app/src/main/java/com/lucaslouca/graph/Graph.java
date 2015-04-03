package com.lucaslouca.graph;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Graph {
	private final List<Vertex> vertices;
	private final List<Edge> edges;

	/**
	 * Constructor for <code>Graph</code>.
	 * 
	 */
	public Graph() {
		vertices = new ArrayList<Vertex>();
		edges = new ArrayList<Edge>();
	}

	/**
	 * Constructor for <code>Graph</code>.
	 * 
	 * @param vertices
	 *            <code>List</code> of <code>Vertex</code>s in the
	 *            <code>Graph</code>.
	 * @param edges
	 *            <code>List</code> of <code>Edge</code>s in the
	 *            <code>Graph</code>.
	 */
	public Graph(List<Vertex> vertices, List<Edge> edges) {
		this.vertices = vertices;
		this.edges = edges;
	}

	/**
	 * Return the <code>List</code> of <code>Vertex</code>s in the
	 * <code>Graph</code>.
	 * 
	 * @return <code>List</code> of <code>Vertex</code>s in the
	 *         <code>Graph</code> .
	 */
	public List<Vertex> getVertices() {
		return vertices;
	}

	/**
	 * Return the <code>List</code> of <code>Edge</code>s in the
	 * <code>Graph</code>.
	 * 
	 * @return <code>List</code> of <code>Edge</code>s in the <code>Graph</code>
	 *         .
	 */
	public List<Edge> getEdges() {
		return edges;
	}

	/**
	 * Removes and <code>Edge</code> from the graph.
	 * 
	 * @param edge
	 */
	public void removeEdge(Edge edge) {
		edges.remove(edge);
	}

	/**
	 * Returns <code>Set<Vertex></code> of neighbor <code>Vertex</code> for
	 * <code>node</code> .
	 * 
	 * @param node
	 *            <code>Set<Vertex></code> of adjacent <code>Vertex</code> for
	 *            <code>node</code> .
	 * @return
	 */
	public Set<Vertex> getNeighbors(Vertex node) {
		Set<Vertex> neighbors = new HashSet<Vertex>();
		for (Edge edge : edges) {
			if (edge.getSource().equals(node)) {
				neighbors.add(edge.getDestination());
			}
		}
		return neighbors;
	}

	/**
	 * Add forward and backwards (directed) edges to the <code>Graph</code>
	 * connecting two <code>Vertex</code> that hold data <code>src</code> and
	 * <code>dest</code>.
	 * 
	 * @param src
	 *            <code>Point</code> data for source <code>Vertex</code>
	 * @param dest
	 *            <code>Point</code> data for destination <code>Vertex</code>
	 */
	public void addBidirectionalEdge(Point src, Point dest) {
		Vertex sourceVertex = new Vertex("Vertex (" + src.getX() + "," + src.getY() + ")", src);
		Vertex destinationVertex = new Vertex("Vertex (" + dest.getX() + "," + dest.getY() + ")", dest);

		if (!vertices.contains(sourceVertex)) {
			vertices.add(sourceVertex);
		}

		if (!vertices.contains(destinationVertex)) {
			vertices.add(destinationVertex);
		}

		edges.add(new Edge(sourceVertex, destinationVertex));
		edges.add(new Edge(destinationVertex, sourceVertex));
	}

	@Override
	public String toString() {
		return "Graph [vertices=" + vertices + ", edges=" + edges + "]";
	}
}
