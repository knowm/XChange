package org.knowm.xchange.coingi.dto.trade;

import java.math.BigDecimal;
import org.knowm.xchange.coingi.dto.CoingiAuthenticatedRequest;

public class CoingiPlaceLimitOrderRequest extends CoingiAuthenticatedRequest {
  private String currencyPair;
  private int type;
  private BigDecimal price;
  private BigDecimal volume;

  public String getCurrencyPair() {
    return currencyPair;
  }

  public CoingiPlaceLimitOrderRequest setCurrencyPair(String pair) {
    this.currencyPair = pair;
    return this;
  }

  public int getType() {
    return type;
  }

  public CoingiPlaceLimitOrderRequest setOrderType(int type) {
    this.type = type;
    return this;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public CoingiPlaceLimitOrderRequest setPrice(BigDecimal price) {
    this.price = price;
    return this;
  }

  public BigDecimal getVolume() {
    return volume;
  }

  public CoingiPlaceLimitOrderRequest setVolume(BigDecimal volume) {
    this.volume = volume;
    return this;
  }
}
