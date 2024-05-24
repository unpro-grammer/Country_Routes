package nz.ac.auckland.se281;

import java.util.*;

public class Graph {

  private Map<Country, List<Country>> adjacencies;

  public Graph() {
    this.adjacencies = new HashMap<>();
  }

  public void addVertex(Country node) {
    List<Country> theAdjacents = new LinkedList<>();
    adjacencies.putIfAbsent(node, theAdjacents);
  }

  public void addEdge(Country node1, Country node2) {
    addVertex(node1);
    addVertex(node2);
    adjacencies.get(node1).add(node2);
  }

  public boolean countryExists(String name) {
    for (Country n : adjacencies.keySet()) {
      if (n.getName().equals(name)) {
        return true;
      }
    }
    return false;
  }

  public Country getCountry(String name) {
    for (Country n : adjacencies.keySet()) {
      if (n.getName().equals(name)) {
        return n;
      }
    }
    if (!countryExists(name)) {
      throw new CountryNotValidException(name);
    }
    return null;
  }

  // BFS: LinkedHashSet to track visited nodes
}
