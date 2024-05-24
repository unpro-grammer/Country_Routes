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
  public List<Country> bfsShortestPath(Country source, Country destination) {
    Set<Country> visited = new LinkedHashSet<>();
    Queue<Country> queue = new LinkedList<>();
    List<Country> shortestPath = new LinkedList<>();
    Map<Country, List<Country>> prevs = new HashMap<>();

    queue.add(source);
    visited.add(source);

    boolean found = false;
    while (!queue.isEmpty() && found == false) {
      Country country = queue.poll();

      for (Country c : adjacencies.get(country)) {

        // eventually record in order the nodes that counted as a predecessor of c, essentially
        if (!prevs.keySet().contains(c)) {
          prevs.put(c, new LinkedList<>());
        }
        prevs.get(c).add(country);

        if (c.equals(destination)) {
          // delegate construction of path to path retracing function for readability
          shortestPath = retracePath(prevs, source, destination);
          found = true;
          break;
        }

        if (!visited.contains(c)) {
          visited.add(c);
          queue.add(c);
        }
      }
    }

    return shortestPath;
  }

  public List<Country> retracePath(
      Map<Country, List<Country>> prevs, Country source, Country dest) {

    List<Country> shortestPath = new LinkedList<>();
    Country current = dest;

    while (!current.equals(source)) {
      shortestPath.add(current);
      // retrieve the "first" parent/prev node of current
      Country prev = prevs.get(current).get(0);
      current = prev;
    }

    shortestPath.add(source);
    Collections.reverse(shortestPath);

    return shortestPath;
  }
}
