package nz.ac.auckland.se281;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * Data structure for use in storing map, where countries are nodes and connections between
 * countries are edges.
 */
public class Graph {

  private Map<Country, List<Country>> adjacencies;

  public Graph() {
    this.adjacencies = new HashMap<>();
  }

  /**
   * Adds a vertex to the graph of type Country.
   *
   * @param node The node to be added.
   */
  public void addVertex(Country node) {
    List<Country> theAdjacents = new LinkedList<>();
    adjacencies.putIfAbsent(node, theAdjacents);
  }

  /**
   * Adds an edge to the graph between two nodes of type Country.
   *
   * @param node1 First country.
   * @param node2 Second country.
   */
  public void addEdge(Country node1, Country node2) {
    addVertex(node1);
    addVertex(node2);
    adjacencies.get(node1).add(node2);
  }

  /**
   * Checks if the country, passed as the country's name, is present within the graph data
   * structure.
   *
   * @param name Name of country.
   * @return Whether the country has been added to the map as a vertex.
   */
  public boolean countryExists(String name) {
    for (Country n : adjacencies.keySet()) {
      if (n.getName().equals(name)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Returns the Country object as identified by its name.
   *
   * @param name Name of country.
   * @return The instance of the Country object.
   */
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

  /**
   * Uses the breadth-first-search method to traverse the graph data structure from the root (source
   * country) until the destination country is reached, while recording the parents/previous nodes
   * that each subsequently visited country originated from, thus allowing for the recording of a
   * shortest path taken.
   *
   * @param source The country to travel from.
   * @param destination The country to travel to.
   * @return The shortest path between the two countries, as a list of intermediate countries.
   */
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

  /**
   * Helper method that backtraces from a located destination to the source country, using a map
   * that records each visited node's parent(s)/previous node(s).
   *
   * @param prevs The hashmap that pairs each visited country with the countries they could have
   *     come from given the possible paths from source to destination country.
   * @param source The country to travel from.
   * @param dest The country to travel to.
   * @return The shortest path between source and destination country.
   */
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
