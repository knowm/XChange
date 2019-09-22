package org.knowm.xchange.binance.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;
import org.knowm.xchange.binance.BinanceAdapters;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;

public final class BinanceTrade {

  public final long id;
  public final long orderId;
  public final BigDecimal price;
  public final BigDecimal qty;
  public final BigDecimal commission;
  public final Currency commissionAsset;
  public final long time;
  public final boolean isBuyer;
  public final boolean isMaker;
  public final boolean isBestMatch;
  public final CurrencyPair symbol;

  public BinanceTrade(
      @JsonProperty("id") long id,
      @JsonProperty("orderId") long orderId,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("qty") BigDecimal qty,
      @JsonProperty("commission") BigDecimal commission,
      @JsonProperty("commissionAsset") String commissionAsset,
      @JsonProperty("time") long time,
      @JsonProperty("isBuyer") boolean isBuyer,
      @JsonProperty("isMaker") boolean isMaker,
      @JsonProperty("isBestMatch") boolean isBestMatch,
      @JsonProperty("symbol") String symbol) {
    this.id = id;
    this.orderId = orderId;
    this.price = price;
    this.qty = qty;
    this.commission = commission;
    this.commissionAsset = Currency.getInstance(commissionAsset);
    this.time = time;
    this.isBuyer = isBuyer;
    this.isMaker = isMaker;
    this.isBestMatch = isBestMatch;
    this.symbol = BinanceAdapters.adaptSymbol(symbol);
  }

  public Date getTime() {
    return new Date(time);
  }

  public long getId() {
    return id;
  }

  public long getOrderId() {
    return orderId;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public BigDecimal getQty() {
    return qty;
  }

  public BigDecimal getCommission() {
    return commission;
  }

  public Currency getCommissionAsset() {
    return commissionAsset;
  }

  public boolean isBuyer() {
    return isBuyer;
  }

  public boolean isMaker() {
    return isMaker;
  }

  public boolean isBestMatch() {
    return isBestMatch;
  }

  public CurrencyPair getSymbol() {
    return symbol;
  }
}
