package nz.ac.auckland.se281;

public class CountryNotValidException extends RuntimeException {
  public CountryNotValidException(String countryName) {
    MessageCli.INVALID_COUNTRY.printMessage(countryName);
  }
}
