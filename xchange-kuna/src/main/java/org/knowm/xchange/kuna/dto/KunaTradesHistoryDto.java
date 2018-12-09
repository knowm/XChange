package org.knowm.xchange.kuna.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class KunaTradesHistoryDto {

  private String id;

  private BigDecimal price;

  private BigDecimal volume;

  private BigDecimal funds;

  private String market;

  @JsonProperty("created_at")
  private Long createdAt;

  private String side;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public BigDecimal getVolume() {
    return volume;
  }

  public void setVolume(BigDecimal volume) {
    this.volume = volume;
  }

  public BigDecimal getFunds() {
    return funds;
  }

  public void setFunds(BigDecimal funds) {
    this.funds = funds;
  }

  public String getMarket() {
    return market;
  }

  public void setMarket(String market) {
    this.market = market;
  }

  public Long getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Long createdAt) {
    this.createdAt = createdAt;
  }

  public String getSide() {
    return side;
  }

  public void setSide(String side) {
    this.side = side;
  }
}
