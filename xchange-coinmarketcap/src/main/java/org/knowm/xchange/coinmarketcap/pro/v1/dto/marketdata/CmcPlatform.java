package org.knowm.xchange.coinmarketcap.pro.v1.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class CmcPlatform {

  private final int id;
  private final String name;
  private final String symbol;
  private final String slug;
  private final String tokenAddress;

  public CmcPlatform(
      @JsonProperty("id") int id,
      @JsonProperty("name") String name,
      @JsonProperty("symbol") String symbol,
      @JsonProperty("slug") String slug,
      @JsonProperty("token_address") String tokenAddress) {

    this.id = id;
    this.name = name;
    this.symbol = symbol;
    this.slug = slug;
    this.tokenAddress = tokenAddress;
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

  public String getTokenAddress() {
    return tokenAddress;
  }

  @Override
  public String toString() {
    return "CmcPlatform{"
        + "id="
        + id
        + ", name='"
        + name
        + '\''
        + ", symbol='"
        + symbol
        + '\''
        + ", slug='"
        + slug
        + '\''
        + ", tokenAddress='"
        + tokenAddress
        + '\''
        + '}';
  }
}
