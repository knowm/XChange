package org.knowm.xchange.coinmarketcap.pro.v1.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.Date;
import java.util.List;
import org.knowm.xchange.utils.jackson.ISO8601DateDeserializer;

public final class CmcCurrencyInfo {

  private final String symbol;
  private final CmcUrls urls;
  private final String name;
  private final String logo;
  private final int id;
  private final String category;
  private final String slug;
  private final CmcPlatform platform;
  private final List<String> tags;
  private final Date dateAdded;

  public CmcCurrencyInfo(
      @JsonProperty("symbol") String symbol,
      @JsonProperty("urls") CmcUrls urls,
      @JsonProperty("name") String name,
      @JsonProperty("logo") String logo,
      @JsonProperty("id") int id,
      @JsonProperty("category") String category,
      @JsonProperty("slug") String slug,
      @JsonProperty("platform") CmcPlatform platform,
      @JsonProperty("tags") List<String> tags,
      @JsonProperty("date_added") @JsonDeserialize(using = ISO8601DateDeserializer.class)
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

  public CmcUrls getUrls() {
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

  public CmcPlatform getPlatform() {
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
    return "CmcCurrencyInfo{"
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
