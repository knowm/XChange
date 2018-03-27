package org.knowm.xchange.bitmex;

/** Delivery dates for future date currencies */
public enum BitmexPrompt {
  PERPETUAL("perpetual"),
  WEEKLY("weekly"),
  MONTHLY("monthly"),
  QUARTERLY("quarterly"),
  BIQUARTERLY("biquarterly");

  private final String name;

  /** Private constructor so it cannot be instantiated */
  BitmexPrompt(String name) {

    this.name = name;
  }

  public static <T extends Enum<T>> T valueOfIgnoreCase(Class<T> enumeration, String name) {

    for (T enumValue : enumeration.getEnumConstants()) {
      if (enumValue.name().equalsIgnoreCase(name)) {
        return enumValue;
      }
    }

    throw new IllegalArgumentException(
        String.format("There is no value with name '%s' in Enum %s", name, enumeration.getName()));
  }

  public String getName() {

    return name;
  }
}
