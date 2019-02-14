package org.knowm.xchange.coinmarketcap.pro.v1.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.knowm.xchange.utils.jackson.ISO8601DateDeserializer;

import java.util.Date;
import java.util.List;

public final class CoinMarketCapCurrencyInfo {

  private final String symbol;
  private final CoinMarketCapUrls urls;
  private final String name;
  private final String logo;
  private final int id;
  private final String category;
  private final String slug;
  private final CoinMarketCapPlatform platform;
  private final List<String> tags;
  private final Date dateAdded;

  public CoinMarketCapCurrencyInfo(
      @JsonProperty("symbol") String symbol,
      @JsonProperty("urls") CoinMarketCapUrls urls,
      @JsonProperty("name") String name,
      @JsonProperty("logo") String logo,
      @JsonProperty("id") int id,
      @JsonProperty("category") String category,
      @JsonProperty("slug") String slug,
      @JsonProperty("platform") CoinMarketCapPlatform platform,
      @JsonProperty("tags") List<String> tags,
      @JsonProperty("date_added")
            @JsonDeserialize(using = ISO8601DateDeserializer.class)
              Date dateAdded) {
    this.symbol = symbol;
    this.urls = urls;
    this.name = name;
    this.logo = logo;
    this.id = id;
    this.category = category;
    this.slug = slug;
    this.platform = platform;
    this.tags = tags;
    this.dateAdded = dateAdded;
  }

  public String getSymbol() {
    return symbol;
  }

  public CoinMarketCapUrls getUrls() {
    return urls;
  }

  public String getName() {
    return name;
  }

  public String getLogo() {
    return logo;
  }

  public int getId() {
    return id;
  }

  public String getCategory() {
    return category;
  }

  public String getSlug() {
    return slug;
  }

  public CoinMarketCapPlatform getPlatform() {
    return platform;
  }

  public List<String> getTags() {
    return tags;
  }

  public Date getDateAdded() {
    return dateAdded;
  }

  @Override
  public String toString() {
    return "CoinMarketCapCurrencyInfo{"
        + "symbol='"
        + symbol
        + '\''
        + ", urls="
        + urls
        + ", name='"
        + name
        + '\''
        + ", logo='"
        + logo
        + '\''
        + ", id="
        + id
        + ", category='"
        + category
        + '\''
        + ", slug='"
        + slug
        + '\''
        + ", platform="
        + platform
        + ", tags="
        + tags
        + ", dateAdded='"
        + dateAdded
        + '\''
        + '}';
  }
}
