package org.knowm.xchange.coinone.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Currency;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.service.trade.params.CancelOrderParams;

public class CoinoneTradeCancelRequest implements CancelOrderParams {

  @JsonProperty("access_token")
  protected String accessTocken;

  @JsonProperty("nonce")
  protected Long nonce;

  @JsonProperty("order_id")
  protected String orderId;

  @JsonProperty("price")
  protected BigDecimal price;

  @JsonProperty("qty")
  protected BigDecimal qty;

  @JsonProperty("is_ask")
  protected boolean isAsk;

  @JsonProperty("currency")
  protected String currency;

  public CoinoneTradeCancelRequest(
      String orderId, BigDecimal price, BigDecimal qty, boolean isAsk, Currency currency) {
    this.orderId = orderId;
    this.price = price;
    this.qty = qty;
    this.isAsk = isAsk;
    this.currency = currency.getSymbol().toLowerCase();
  }

  public CoinoneTradeCancelRequest(String orderId, LimitOrder limitOrder) {
    this.orderId = orderId;
    this.price = limitOrder.getLimitPrice();
    this.qty = limitOrder.getOriginalAmount();
    this.isAsk = limitOrder.getType() == Order.OrderType.ASK ? true : false;
    this.currency = limitOrder.getCurrencyPair().base.getSymbol().toLowerCase();
  }

  public String getAccessTocken() {
    return accessTocken;
  }

  public void setAccessTocken(String accessTocken) {
    this.accessTocken = accessTocken;
  }

  public Long getNonce() {
    return nonce;
  }

  public void setNonce(Long nonce) {
    this.nonce = nonce;
  }

  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public BigDecimal getQty() {
    return qty;
  }

  public void setQty(BigDecimal qty) {
    this.qty = qty;
  }

  public boolean isAsk() {
    return isAsk;
  }

  public void setAsk(boolean isAsk) {
    this.isAsk = isAsk;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }
}
