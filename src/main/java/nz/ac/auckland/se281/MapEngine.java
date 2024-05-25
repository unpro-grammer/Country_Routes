package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.List;

/** This class is the main entry point. */
public class MapEngine {

  private Graph worldMap;

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
            worldMap.getCountry(currentCountryNeighbours[0]),
            worldMap.getCountry(currentCountryNeighbours[j]));
      }
    }
  }

  /**
   * Asks the user to input the name of a country for use in either the info-country or route
   * commands.
   *
   * @param msg The appropriate message that should serve as a prompt to the user, depending on the
   *     purpose of the country name they are to input.
   * @return The corresponding Country object associated with the name provided as input.
   */
  public Country getInputCountry(MessageCli msg) {

    Country country;
    String inputCountry;
    String caseCorrectInput = "";

    while (true) {
      try {
        // print prompt that asks for an input country specific to the current purpose
        msg.printMessage();
        inputCountry = Utils.scanner.nextLine();
        // allow users to use any case for the first letter of each word in the country name
        caseCorrectInput = Utils.capitalizeFirstLetterOfEachWord(inputCountry);
        country = worldMap.getCountry(caseCorrectInput);
        break;
      } catch (CountryNotValidException e) {
        MessageCli.INVALID_COUNTRY.printMessage(caseCorrectInput);
      }
    }
    return country;
  }

  /** this method is invoked when the user run the command info-country. */
  public void showInfoCountry() {
    Country inputCountry = getInputCountry(MessageCli.INSERT_COUNTRY);

    MessageCli.COUNTRY_INFO.printMessage(
        inputCountry.getName(), inputCountry.getContinent(), inputCountry.getFee());
  }

  /** this method is invoked when the user run the command route. */
  public void showRoute() {
    Country source = getInputCountry(MessageCli.INSERT_SOURCE);
    Country destination = getInputCountry(MessageCli.INSERT_DESTINATION);

    if (source.equals(destination)) {
      // if the source and destination country is the same, there is no need for travel.
      MessageCli.NO_CROSSBORDER_TRAVEL.printMessage();
    } else {
      List<Country> path;
      // find the shortest path between source and destination country.
      path = worldMap.bfsShortestPath(source, destination);
      // display all the relevant info associated with travel.
      MessageCli.ROUTE_INFO.printMessage(displayPath(path));
      MessageCli.CONTINENT_INFO.printMessage(getContinents(path));
      MessageCli.TAX_INFO.printMessage(getTaxFees(path));
    }
  }

  /**
   * Use stringbuilder to construct a visual representation of the path provided.
   *
   * @param path The list of countries in the path.
   * @return The string that represents the given path.
   */
  public String displayPath(List<Country> path) {
    StringBuilder str = new StringBuilder();
    str.append("[");
    for (int i = 0; i < path.size(); i++) {
      str.append(path.get(i).getName());
      if (i < path.size() - 1) {
        // only add ", " after items that are not the last item.
        str.append(", ");
      }
    }
    str.append("]");
    return str.toString();
  }

  /**
   * Use a stringbuilder to represent the unique continents that have been passed via the path.
   *
   * @param path The list of countries in the path.
   * @return A visual representation of the continents crossed via the path.
   */
  public String getContinents(List<Country> path) {
    List<String> continents = new ArrayList<>();
    for (Country c : path) {
      // ensure continents are not duplicated.
      if (!continents.contains(c.getContinent())) {
        continents.add(c.getContinent());
      }
    }
    StringBuilder cont = new StringBuilder();
    cont.append("[");
    for (int i = 0; i < continents.size(); i++) {
      cont.append(continents.get(i));
      if (i < continents.size() - 1) {
        // only add ", " after items that are not the last item.
        cont.append(", ");
      }
    }
    cont.append("]");
    return cont.toString();
  }

  /**
   * Calculate total tax fee associated with travelling along the given path.
   *
   * @param path List of countries along the path.
   * @return Total tax fee that must be paid for cross-border travel.
   */
  public String getTaxFees(List<Country> path) {
    int sum = 0;
    // sum up all tax fees along the path, neglecting the source country.
    for (int i = 1; i < path.size(); i++) {
      int tax = Integer.parseInt(path.get(i).getFee());
      sum += tax;
    }

    return sum + "";
  }
}
