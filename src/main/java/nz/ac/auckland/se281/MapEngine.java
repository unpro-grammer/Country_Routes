package nz.ac.auckland.se281;

import java.util.List;

/** This class is the main entry point. */
public class MapEngine {

  Graph worldMap;

  public MapEngine() {
    // add other code here if you want
    loadMap(); // keep this mehtod invocation
  }

  /** invoked one time only when constracting the MapEngine class. */
  private void loadMap() {
    List<String> countries = Utils.readCountries();
    List<String> adjacencies = Utils.readAdjacencies();

    worldMap = new Graph();

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

  public String getInputCountry() {
    MessageCli.INSERT_COUNTRY.printMessage();
    String inputCountry = Utils.scanner.nextLine();
    if (!worldMap.countryExists(inputCountry)) {
      throw new CountryNotValidException(inputCountry);
    }
    return inputCountry;
  }

  /** this method is invoked when the user run the command info-country. */
  public void showInfoCountry() {
    String inputCountry;
    while (true) {
      try {
        inputCountry = getInputCountry();
        break;
      } catch (CountryNotValidException e) {

      }
    }
  }

  /** this method is invoked when the user run the command route. */
  public void showRoute() {}
}
