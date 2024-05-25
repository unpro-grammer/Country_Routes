package nz.ac.auckland.se281;

/**
 * Data structure that comprises information about each country, acting as a node for the Graph data
 * structure.
 */
public class Country {
  private String name;
  private String continent;
  private String fee;

  /**
   * Constructor for creating a new country.
   *
   * @param name Name of country.
   * @param continent Continent the country belongs in.
   * @param fee The tax fee associated with crossing a border into this country.
   */
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

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    // at the point, it is guaranteed that both objects are of type Country, allowing casting
    Country other = (Country) obj;
    if (name == null) {
      if (other.name != null) {
        return false;
      }
    } else if (!name.equals(other.name)) {
      return false;
    }
    return true;
  }
}
