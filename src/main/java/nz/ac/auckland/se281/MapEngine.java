package nz.ac.auckland.se281;

import java.util.List;

/** This class is the main entry point. */
public class MapEngine {

  public MapEngine() {
    // add other code here if you want
    loadMap(); // keep this mehtod invocation
  }

  /** invoked one time only when constracting the MapEngine class. */
  private void loadMap() {
    List<String> countries = Utils.readCountries();
    List<String> adjacencies = Utils.readAdjacencies();
    // add code here to create your data structures
    Graph<Country> worldMap = new Graph<>();

    // populate graph with country nodes
    for (int i = 0; i < countries.size(); i++) {
      String[] currentCountry = countries.get(i).split(",");
      worldMap.addVertex(new Country(currentCountry[0], currentCountry[1], currentCountry[2]));
    }

    // connect nodes where neighbouring occurs
    for (int i = 0; i < adjacencies.size(); i++) {
      String[] currentCountryNeighbours = adjacencies.get(i).split(",");
      for (int j = 1; j < currentCountryNeighbours.length; j++) {
        worldMap.addEdge(
            new Country(currentCountryNeighbours[0]), new Country(currentCountryNeighbours[j]));
      }
    }
  }

  /** this method is invoked when the user run the command info-country. */
  public void showInfoCountry() {
    // add code here
  }

  /** this method is invoked when the user run the command route. */
  public void showRoute() {}
}
