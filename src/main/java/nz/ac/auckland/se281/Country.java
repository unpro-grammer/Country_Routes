package nz.ac.auckland.se281;

public class Country {
  private String name;
  private String continent;
  private String fee;

  public Country(String name, String continent, String fee) {
    this.name = name;
    this.continent = continent;
    this.fee = fee;
  }

  public String getName() {
    return name;
  }

  public String getContinent() {
    return continent;
  }

  public String getFee() {
    return fee;
  }
}
