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
