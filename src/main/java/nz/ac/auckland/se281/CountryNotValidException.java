package nz.ac.auckland.se281;

public class CountryNotValidException extends RuntimeException {
  public CountryNotValidException() {
    MessageCli.INVALID_COUNTRY.printMessage();
  }
}
