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

  public Country getInputCountry(MessageCli msg) {

    Country country;
    String inputCountry;
    String caseCorrectInput = "";

    while (true) {
      try {
        msg.printMessage();
        inputCountry = Utils.scanner.nextLine();
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
      MessageCli.NO_CROSSBORDER_TRAVEL.printMessage();
    } else {
      List<Country> path;
      path = worldMap.bfsShortestPath(source, destination);
      MessageCli.ROUTE_INFO.printMessage(displayPath(path));
      MessageCli.CONTINENT_INFO.printMessage(getContinents(path));
      MessageCli.TAX_INFO.printMessage(getTaxFees(path));
    }
  }

  public String displayPath(List<Country> path) {
    StringBuilder str = new StringBuilder();
    str.append("[");
    for (int i = 0; i < path.size(); i++) {
      str.append(path.get(i).getName());
      if (i < path.size() - 1) {
        str.append(", ");
      }
    }
    str.append("]");
    return str.toString();
  }

  public String getContinents(List<Country> path) {
    List<String> continents = new ArrayList<>();
    for (Country c : path) {
      if (!continents.contains(c.getContinent())) {
        continents.add(c.getContinent());
      }
    }
    StringBuilder cont = new StringBuilder();
    cont.append("[");
    for (int i = 0; i < continents.size(); i++) {
      cont.append(continents.get(i));
      if (i < continents.size() - 1) {
        cont.append(", ");
      }
    }
    cont.append("]");
    return cont.toString();
  }

  public String getTaxFees(List<Country> path) {
    int sum = 0;
    for (int i = 1; i < path.size(); i++) {
      int tax = Integer.parseInt(path.get(i).getFee());
      sum += tax;
    }

    return sum + "";
  }
}
