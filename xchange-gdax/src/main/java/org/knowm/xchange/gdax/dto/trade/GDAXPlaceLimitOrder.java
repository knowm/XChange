package org.knowm.xchange.gdax.dto.trade;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

/**
 * LIMIT ORDER PARAMETERS
 *
 * <table>
 * <tr><th>Param</th><th>Description</th></tr>
 * <tr><td>price</td><td>Price per bitcoin</td></tr>
 * <tr><td>size</td><td>Amount of BTC to buy or sell</td></tr>
 * <tr><td>time_in_force</td><td>[optional] GTC, GTT, IOC, or FOK (default is GTC)</td></tr>
 * <tr><td>cancel_after</td><td>[optional] min, hour, day.  Requires time_in_force to be GTT</td></tr>
 * <tr><td>post_only</td><td>[optional] Post only flag.  Invalid when time_in_force is IOC or FOK</td></tr>
 * </table>
 *
 * @author bryant_harris
 */
public class GDAXPlaceLimitOrder extends GDAXPlaceOrder {
  @JsonProperty("price")
  BigDecimal price;

  @JsonProperty("size")
  BigDecimal size;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("time_in_force")
  TimeInForce timeInForce;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("cancel_after")
  CancelAfter cancelAfter;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("post_only")
  Boolean postOnly;

  public GDAXPlaceLimitOrder(
      String clientOld,
      Type type,
      Side side,
      String productId,
      SelfTradePrevention stp,
      Stop stop,
      BigDecimal stopPrice,
      BigDecimal price,
      BigDecimal size,
      TimeInForce timeInForce,
      CancelAfter cancelAfter,
      Boolean postOnly) {
    super(clientOld, type, side, productId, stp, stop, stopPrice);
    this.price = price;
    this.size = size;
    this.timeInForce = timeInForce;
    this.cancelAfter = cancelAfter;
    this.postOnly = postOnly;

    if (cancelAfter != null && timeInForce != TimeInForce.GTT)
      throw new IllegalArgumentException("cancel_after Requires time_in_force to be GTT");
    if (postOnly != null && (timeInForce == TimeInForce.IOC || timeInForce == TimeInForce.FOK))
      throw new IllegalArgumentException("post_only Invalid when time_in_force is IOC or FOK");
  }

  public BigDecimal getPrice() {
    return price;
  }

  public BigDecimal getSize() {
    return size;
  }

  public TimeInForce getTimeInForce() {
    return timeInForce;
  }

  public CancelAfter getCancelAfter() {
    return cancelAfter;
  }

  public Boolean getPostOnly() {
    return postOnly;
  }

  @Override
  public String toString() {
    return "GDAXPlaceLimitOrder [price="
        + price
        + ", size="
        + size
        + ", timeInForce="
        + timeInForce
        + ", cancelAfter="
        + cancelAfter
        + ", postOnly="
        + postOnly
        + ", clientOid="
        + clientOid
        + ", type="
        + type
        + ", side="
        + side
        + ", productId="
        + productId
        + ", stp="
        + stp
        + ", stop="
        + stop
        + ", stopPrice="
        + stopPrice
        + "]";
  }

  public static class Builder extends GDAXPlaceOrder.Builder<GDAXPlaceLimitOrder, Builder> {
    BigDecimal price;
    BigDecimal size;
    TimeInForce timeInForce;
    CancelAfter cancelAfter;
    Boolean postOnly;

    public Builder price(BigDecimal price) {
      this.price = price;
      return this;
    }

    public Builder size(BigDecimal size) {
      this.size = size;
      return this;
    }

    public Builder timeInForce(TimeInForce timeInForce) {
      this.timeInForce = timeInForce;
      return this;
    }

    public Builder cancelAfter(CancelAfter cancelAfter) {
      this.cancelAfter = cancelAfter;
      return this;
    }

    public Builder postOnly(boolean postOnly) {
      this.postOnly = postOnly;
      return this;
    }

    @Override
    public GDAXPlaceLimitOrder build() {
      return new GDAXPlaceLimitOrder(
          clientOid,
          type,
          side,
          productId,
          stp,
          stop,
          stopPrice,
          price,
          size,
          timeInForce,
          cancelAfter,
          postOnly);
    }
  }

  public enum TimeInForce {
    GTC,
    GTT,
    IOC,
    FOK
  }

  public enum CancelAfter {
    min,
    hour,
    day;
  }
}
