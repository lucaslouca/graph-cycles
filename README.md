# graph-cycles

Java application to list all cycles in an undirected and directed graph

### How to Import into Eclipse
* **File** -> **Import...** -> **Existing Maven Projects**
* Click **Next**
* Click **Browse...** for the **Root Directory**
* Select and open **graph-cycles-app**
* Click **Finish**
* Do a mvn update on **graph-cycles-app**

### How to Test
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

### Algorithm summary

The below described algorithm is implemented in <a href="https://github.com/lucaslouca/graph-cycles/blob/master/graph-cycles-app/src/main/java/com/lucaslouca/graph/CycleUtil.java">CycleUtil.java</a>

1. Compute a cycle basis of graph ``G = (V, E)``
  * Find a minimal spanning tree ``(V, E')`` of ``G``, using <a href="http://en.wikipedia.org/wiki/Depth-first_search">Depth-first search (DFS)</a> and its associated set of back edges
  * If ``e in B`` is a back edge, insert it into the minimal spanning tree's edges ``E'`` to form a set ``E'' = E' + {e}``. The resulting graph ``(V, E'')`` has exactly one cycle, which may be constructed by applying a DFS
2. Generate the *incidence vector* of each cycle in the cycle basis
3. Create all possible combinations of the computed incidence vectors. Let the set of all combinations be ``C``
4. Generate a cycle from each incidence vector in ``C``

### References
* <a href="http://dspace.mit.edu/bitstream/handle/1721.1/68106/FTL_R_1982_07.pdf">Algorithmic Approaches To Circuit Enumeration and Applications</a>
* <a href="http://en.wikipedia.org/wiki/Cycle_basis">Cycle basis</a>
