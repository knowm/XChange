package org.knowm.xchange.quoine.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

/** @author timmolter */
public final class Execution {

  private final BigDecimal quantity;
  private final BigDecimal price;
  private final String makerSide;
  private final BigDecimal createdAt;
  private final BigDecimal sellerFee;
  private final BigDecimal buyerFee;
  private final BigDecimal buyFxRate;
  private final BigDecimal sellFxRate;

  /**
   * Constructor
   *
   * @param quantity
   * @param price
   * @param makerSide
   * @param createdAt
   * @param sellerFee
   * @param buyerFee
   * @param buyFxRate
   * @param sellFxRate
   */
  public Execution(
      @JsonProperty("quantity") BigDecimal quantity,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("maker_side") String makerSide,
      @JsonProperty("created_at") BigDecimal createdAt,
      @JsonProperty("seller_fee") BigDecimal sellerFee,
      @JsonProperty("buyer_fee") BigDecimal buyerFee,
      @JsonProperty("buy_fx_rate") BigDecimal buyFxRate,
      @JsonProperty("sell_fx_rate") BigDecimal sellFxRate) {
    this.quantity = quantity;
    this.price = price;
    this.makerSide = makerSide;
    this.createdAt = createdAt;
    this.sellerFee = sellerFee;
    this.buyerFee = buyerFee;
    this.buyFxRate = buyFxRate;
    this.sellFxRate = sellFxRate;
  }

  public BigDecimal getQuantity() {
    return quantity;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public String getMakerSide() {
    return makerSide;
  }

  public BigDecimal getCreatedAt() {
    return createdAt;
  }

  public BigDecimal getSellerFee() {
    return sellerFee;
  }

  public BigDecimal getBuyerFee() {
    return buyerFee;
  }

  public BigDecimal getBuyFxRate() {
    return buyFxRate;
  }

  public BigDecimal getSellFxRate() {
    return sellFxRate;
  }

  @Override
  public String toString() {
    return "Execution [quantity="
        + quantity
        + ", price="
        + price
        + ", makerSide="
        + makerSide
        + ", createdAt="
        + createdAt
        + ", sellerFee="
        + sellerFee
        + ", buyerFee="
        + buyerFee
        + ", buyFxRate="
        + buyFxRate
        + ", sellFxRate="
        + sellFxRate
        + "]";
  }
}
