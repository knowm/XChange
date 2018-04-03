package org.knowm.xchange.gdax.dto.trade;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

/**
 * These parameters are common to all order types. Depending on the order type, additional
 * parameters will be required (see below).
 *
 * <p>PARAMETERS
 *
 * <table>
 * <tr><th>Param</th><th>Description</th></tr>
 * <tr><td>client_oid</td><td>[optional] Order ID selected by you to identify your order</td></tr>
 * <tr><td>type</td><td>[optional] limit or market (default is limit)</td></tr>
 * <tr><td>side</td><td>buy or sell</td></tr>
 * <tr><td>product_id</td><td>A valid product id</td></tr>
 * <tr><td>stp</td><td>[optional] Self-trade prevention flag</td></tr>
 * <tr><td>stop</td><td>[optional] Either loss or entry. Requires stop_price to be defined.</td></tr>
 * <tr><td>stop_price</td><td>[optional] Only if stop is defined. Sets trigger price for stop order.</td></tr>
 * </table>
 *
 * @author bryant_harris
 */
public abstract class GDAXPlaceOrder {
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("client_oid")
  String clientOid;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("type")
  Type type;

  @JsonProperty("side")
  Side side;

  @JsonProperty("product_id")
  String productId;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("stp")
  SelfTradePrevention stp;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("stop")
  Stop stop;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("stop_price")
  BigDecimal stopPrice;

  public GDAXPlaceOrder(
      String clientOid,
      Type type,
      Side side,
      String productId,
      SelfTradePrevention stp,
      Stop stop,
      BigDecimal stopPrice) {
    this.clientOid = clientOid;
    this.type = type;
    this.side = side;
    this.productId = productId;
    this.stp = stp;
    this.stop = stop;
    this.stopPrice = stopPrice;
  }

  public String getClientOid() {
    return clientOid;
  }

  public Type getType() {
    return type;
  }

  public Side getSide() {
    return side;
  }

  public String getProductId() {
    return productId;
  }

  public SelfTradePrevention getStp() {
    return stp;
  }

  public Stop getStop() {
    return stop;
  }

  public BigDecimal getStopPrice() {
    return stopPrice;
  }

  @SuppressWarnings("unchecked")
  abstract static class Builder<T, B extends Builder<?, ?>> {
    String clientOid;
    Type type;
    Side side;
    String productId;
    SelfTradePrevention stp;
    Stop stop;
    BigDecimal stopPrice;

    public B clientOid(String clientOid) {
      this.clientOid = clientOid;
      return (B) this;
    }

    public B type(Type type) {
      this.type = type;
      return (B) this;
    }

    public B side(Side side) {
      this.side = side;
      return (B) this;
    }

    public B productId(String productId) {
      this.productId = productId;
      return (B) this;
    }

    public B stp(SelfTradePrevention stp) {
      this.stp = stp;
      return (B) this;
    }

    public B stop(Stop stop) {
      this.stop = stop;
      return (B) this;
    }

    public B stopPrice(BigDecimal stopPrice) {
      this.stopPrice = stopPrice;
      return (B) this;
    }

    public abstract T build();
  }

  public enum Type {
    limit,
    market;
  }

  public enum Side {
    buy,
    sell
  }

  public enum SelfTradePrevention {
    dc, // Decrease and Cancel (default)
    co, // Cancel oldest
    cn, // Cancel newest
    cb; // Cancel both
  }

  public enum Stop {
    loss,
    entry
  }
}
