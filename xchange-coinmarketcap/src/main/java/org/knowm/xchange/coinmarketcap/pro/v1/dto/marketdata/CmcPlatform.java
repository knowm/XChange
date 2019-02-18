package org.knowm.xchange.coinmarketcap.pro.v1.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class CmcPlatform {

  private final int id;
  private final String name;
  private final String symbol;
  private final String slug;
  private final String tokenAdress;


  public CmcPlatform(
      @JsonProperty("id") int id,
      @JsonProperty("name") String name,
      @JsonProperty("symbol") String symbol,
      @JsonProperty("slug") String slug,
      @JsonProperty("token_address") String tokenAdress) {
    this.id = id;
    this.name = name;
    this.symbol = symbol;
    this.slug = slug;
    this.tokenAdress = tokenAdress;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getSymbol() {
    return symbol;
  }

  public String getSlug() {
    return slug;
  }

  public String getTokenAdress() {
    return tokenAdress;
  }

  @Override
  public String toString() {
    return "CmcPlatform{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", symbol='" + symbol + '\'' +
            ", slug='" + slug + '\'' +
            ", tokenAdress='" + tokenAdress + '\'' +
            '}';
  }
}
