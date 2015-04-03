# graph-cycles

Java application to list all cycles in an undirected and directed graph

###How to Import into Eclipse
* **File** -> **Import...** -> **Existing Maven Projects**
* Click **Next**
* Click **Browse...** for the **Root Directory**
* Select and open **graph-cycles-app**
* Click **Finish**
* Do a mvn update on **graph-cycles-app**

###How to Test
JUnit tests are located under ``src/test/java``.

Example test:
>```java
>	@Test
>	public void testNumberOfAllCycles() {
>		Graph g = new Graph();
>		g.addBidirectionalEdge(new Point(0, 0), new Point(0, 1));
>		g.addBidirectionalEdge(new Point(0, 1), new Point(3, 1));
>		g.addBidirectionalEdge(new Point(3, 1), new Point(3, 0));
>		g.addBidirectionalEdge(new Point(3, 0), new Point(0, 0));
>
>		CycleUtil cycleUtil = new CycleUtil(g);
>		List<Graph> cycles = cycleUtil.listAllCycles();
>		assertEquals(1, cycles.size());
>	}
```

See the test class ``com.lucaslouca.graph.CycleUtilTest`` for more tests. You can also run ``com.lucaslouca.app.App`` located under ``src/main/java`` if you want to.

###References
* <a href="http://dspace.mit.edu/bitstream/handle/1721.1/68106/FTL_R_1982_07.pdf">Algorithmic Approaches To Circuit Enumeration and Applications</a>
* <a href="http://en.wikipedia.org/wiki/Cycle_basis">Cycle basis</a>
