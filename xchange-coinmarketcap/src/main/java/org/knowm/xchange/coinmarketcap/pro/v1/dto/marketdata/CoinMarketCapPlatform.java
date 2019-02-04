package org.knowm.xchange.coinmarketcap.pro.v1.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class CoinMarketCapPlatform {

  private final int id;
  private final String name;
  private final String slug;
  private final String tokenAdress;

  public CoinMarketCapPlatform(
      @JsonProperty("id") int id,
      @JsonProperty("name") String name,
      @JsonProperty("slug") String slug,
      @JsonProperty("token_address") String tokenAdress) {
    this.id = id;
    this.name = name;
    this.slug = slug;
    this.tokenAdress = tokenAdress;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getSlug() {
    return slug;
  }

  public String getTokenAdress() {
    return tokenAdress;
  }

  @Override
  public String toString() {
    return "CoinMarketCapPlatform{"
        + "id="
        + id
        + ", name='"
        + name
        + '\''
        + ", slug='"
        + slug
        + '\''
        + ", tokenAdress='"
        + tokenAdress
        + '\''
        + '}';
  }
}
