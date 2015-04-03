package com.lucaslouca.graph;

import static org.junit.Assert.assertEquals;

import java.awt.Point;
import java.util.List;

import org.junit.Test;

public class CycleUtilTest {

	@Test
	public void testNumberOfAllCycles1() {
		Graph g = new Graph();
		g.addBidirectionalEdge(new Point(0, 0), new Point(0, 1));
		g.addBidirectionalEdge(new Point(0, 1), new Point(3, 1));
		g.addBidirectionalEdge(new Point(3, 1), new Point(3, 0));
		g.addBidirectionalEdge(new Point(3, 0), new Point(0, 0));

		CycleUtil cycleUtil = new CycleUtil(g);
		List<Graph> cycles = cycleUtil.listAllCycles();
		assertEquals(1, cycles.size());
	}

	@Test
	public void testNumberOfAllCycles2() {
		Graph g = new Graph();
		g.addBidirectionalEdge(new Point(0, 0), new Point(0, 1));
		g.addBidirectionalEdge(new Point(0, 1), new Point(3, 1));
		g.addBidirectionalEdge(new Point(3, 1), new Point(3, 0));
		g.addBidirectionalEdge(new Point(3, 0), new Point(0, 0));
		g.addBidirectionalEdge(new Point(3, 1), new Point(6, 1));
		g.addBidirectionalEdge(new Point(6, 1), new Point(6, 0));
		g.addBidirectionalEdge(new Point(6, 0), new Point(3, 0));

		CycleUtil cycleUtil = new CycleUtil(g);
		List<Graph> cycles = cycleUtil.listAllCycles();
		assertEquals(3, cycles.size());
	}

	@Test
	public void testNumberOfAllCycles3() {
		Graph g = new Graph();
		g.addBidirectionalEdge(new Point(0, 0), new Point(0, 1));
		g.addBidirectionalEdge(new Point(0, 1), new Point(0, 2));
		g.addBidirectionalEdge(new Point(0, 1), new Point(1, 1));
		g.addBidirectionalEdge(new Point(0, 2), new Point(1, 2));
		g.addBidirectionalEdge(new Point(1, 2), new Point(1, 1));
		g.addBidirectionalEdge(new Point(1, 1), new Point(1, 0));
		g.addBidirectionalEdge(new Point(1, 0), new Point(0, 0));
		g.addBidirectionalEdge(new Point(1, 2), new Point(2, 2));
		g.addBidirectionalEdge(new Point(2, 2), new Point(2, 1));
		g.addBidirectionalEdge(new Point(2, 1), new Point(1, 1));
		g.addBidirectionalEdge(new Point(2, 1), new Point(2, 0));
		g.addBidirectionalEdge(new Point(2, 0), new Point(1, 0));

		CycleUtil cycleUtil = new CycleUtil(g);
		List<Graph> cycles = cycleUtil.listAllCycles();
		assertEquals(15, cycles.size());
	}

	@Test
	public void testNumberOfAllCycles4() {
		Graph g = new Graph();
		g.addBidirectionalEdge(new Point(0, 0), new Point(0, 1));
		g.addBidirectionalEdge(new Point(0, 1), new Point(3, 1));
		g.addBidirectionalEdge(new Point(3, 1), new Point(3, 0));
		g.addBidirectionalEdge(new Point(3, 0), new Point(0, 0));
		g.addBidirectionalEdge(new Point(3, 1), new Point(6, 1));
		g.addBidirectionalEdge(new Point(6, 1), new Point(6, 0));
		g.addBidirectionalEdge(new Point(6, 0), new Point(3, 0));

		CycleUtil cycleUtil = new CycleUtil(g);
		List<Graph> cycles = cycleUtil.listAllCycles();
		assertEquals(3, cycles.size());
	}

	@Test
	public void testNumberOfAllCycles5() {
		Graph g = new Graph();
		g.addBidirectionalEdge(new Point(0, 0), new Point(0, 0));
		CycleUtil cycleUtil = new CycleUtil(g);
		List<Graph> cycles = cycleUtil.listAllCycles();
		assertEquals(0, cycles.size());
	}

	@Test
	public void testNoCycles1() {
		Graph g = new Graph();
		g.addBidirectionalEdge(new Point(0, 0), new Point(0, 1));
		g.addBidirectionalEdge(new Point(0, 1), new Point(3, 1));
		g.addBidirectionalEdge(new Point(3, 1), new Point(3, 0));
		g.addBidirectionalEdge(new Point(3, 0), new Point(0, 99));

		CycleUtil cycleUtil = new CycleUtil(g);
		List<Graph> cycles = cycleUtil.listAllCycles();
		assertEquals(0, cycles.size());
	}

}
