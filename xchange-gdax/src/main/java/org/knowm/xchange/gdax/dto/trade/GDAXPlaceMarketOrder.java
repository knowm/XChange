package org.knowm.xchange.gdax.dto.trade;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

/**
 * MARKET ORDER PARAMETERS
 *
 * <table>
 * <tr><th>Param</th><th>Description</th></tr>
 * <tr><td>size</td><td>[optional] Desired amount in BTC</td></tr>
 * <tr><td>funds</td><td>[optional] Desired amount of quote currency to use</td></tr>
 * </table>
 *
 * <em>One of size or funds is required.</em>
 *
 * @author bryant_harris
 */
public class GDAXPlaceMarketOrder extends GDAXPlaceOrder {
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("size")
  BigDecimal size;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("funds")
  BigDecimal funds;

  public GDAXPlaceMarketOrder(
      String clientOld,
      Type type,
      Side side,
      String productId,
      SelfTradePrevention stp,
      Stop stop,
      BigDecimal stopPrice,
      BigDecimal size,
      BigDecimal funds) {
    super(clientOld, type, side, productId, stp, stop, stopPrice);
    this.size = size;
    this.funds = funds;

    if ((size != null && funds != null) || (size == null && funds == null))
      throw new IllegalArgumentException("One of size or funds is required.");
  }

  public BigDecimal getSize() {
    return size;
  }

  public BigDecimal getFunds() {
    return funds;
  }

  @Override
  public String toString() {
    return "GDAXPlaceMarketOrder [size="
        + size
        + ", funds="
        + funds
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

  public static class Builder extends GDAXPlaceOrder.Builder<GDAXPlaceMarketOrder, Builder> {
    BigDecimal size;
    BigDecimal funds;

    public Builder size(BigDecimal size) {
      this.size = size;
      return this;
    }

    public Builder funds(BigDecimal funds) {
      this.funds = funds;
      return this;
    }

    @Override
    public GDAXPlaceMarketOrder build() {
      return new GDAXPlaceMarketOrder(
          clientOid, type, side, productId, stp, stop, stopPrice, size, funds);
    }
  }
}
