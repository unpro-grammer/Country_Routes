package nz.ac.auckland.se281;

/**
 * Custom exception that is thrown if the country that is being retrieved does not exist in the
 * graph data structure of interest.
 */
public class CountryNotValidException extends RuntimeException {
  public CountryNotValidException(String countryName) {}
}
