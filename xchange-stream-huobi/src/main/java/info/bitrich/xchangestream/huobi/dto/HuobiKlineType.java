package info.bitrich.xchangestream.huobi.dto;

public enum HuobiKlineType {
  MIN1("1min"),
  MIN5("5min"),
  MIN15("15min"),
  MIN30("30min"),
  MIN60("60min"),
  HOUR4("4hour"),
  DAY1("1day"),
  MON1("1mon"),
  WEEK("1week"),
  YEAR("1year");

  HuobiKlineType(String name) {
    this.name = name;
  }

  private String name;

  public String getName() {
    return name;
  }
}
