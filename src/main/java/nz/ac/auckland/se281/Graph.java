package nz.ac.auckland.se281;

import java.util.*;

public class Graph<T> {

  private Map<T, List<T>> adjacencies;

  public Graph() {
    this.adjacencies = new HashMap<>();
  }

  public void addVertex(T node) {
    adjacencies.putIfAbsent(node, new LinkedList<>());
  }

  public void addEdge(T node1, T node2) {
    addVertex(node1);
    addVertex(node2);
    adjacencies.get(node1).add(node2);
  }

  // BFS: LinkedHashSet to track visited nodes
}
