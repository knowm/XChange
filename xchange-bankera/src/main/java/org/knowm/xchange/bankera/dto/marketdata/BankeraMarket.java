package org.knowm.xchange.bankera.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BankeraMarket {
  private int id;
  private String slug;
  private String name;

  public BankeraMarket(
      @JsonProperty("id") int id,
      @JsonProperty("slug") String slug,
      @JsonProperty("name") String name) {
    this.id = id;
    this.slug = slug;
    this.name = name;
  }

  public int getId() {
    return id;
  }

  public String getSlug() {
    return slug;
  }

  public String getName() {
    return name;
  }
}
