package com.lucaslouca.app;

import java.awt.Point;
import java.util.List;

import com.lucaslouca.graph.CycleUtil;
import com.lucaslouca.graph.Graph;

public class App {
	public static void main(String[] args) {
		Graph g = new Graph();

		g.addBidirectionalEdge(new Point(2, 1), new Point(2, 5));
		g.addBidirectionalEdge(new Point(2, 1), new Point(3, 1));
		g.addBidirectionalEdge(new Point(2, 5), new Point(4, 5));
		g.addBidirectionalEdge(new Point(3, 1), new Point(6, 1));
		g.addBidirectionalEdge(new Point(3, 1), new Point(4, 5));
		g.addBidirectionalEdge(new Point(4, 5), new Point(7, 5));
		g.addBidirectionalEdge(new Point(7, 5), new Point(7, 2));
		g.addBidirectionalEdge(new Point(7, 5), new Point(9, 5));
		g.addBidirectionalEdge(new Point(9, 5), new Point(9, 4));
		g.addBidirectionalEdge(new Point(9, 4), new Point(7, 2));
		g.addBidirectionalEdge(new Point(9, 4), new Point(9, 1));
		g.addBidirectionalEdge(new Point(9, 1), new Point(7, 1));
		g.addBidirectionalEdge(new Point(7, 1), new Point(7, 2));
		g.addBidirectionalEdge(new Point(7, 1), new Point(6, 1));
		g.addBidirectionalEdge(new Point(7, 2), new Point(6, 1));

		CycleUtil cycleUtil = new CycleUtil(g);
		List<Graph> cycles = cycleUtil.listAllCycles();
		for (Graph cycle : cycles) {
			System.out.println(cycle.getEdges());
		}
	}

}
