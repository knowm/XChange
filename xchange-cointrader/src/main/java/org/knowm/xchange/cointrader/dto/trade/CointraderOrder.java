package org.knowm.xchange.cointrader.dto.trade;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.utils.jackson.CurrencyPairDeserializer;
import org.knowm.xchange.utils.jackson.SqlUtcTimeDeserializer;

import si.mazi.rescu.ExceptionalReturnContentException;

/**
 * @author Matija Mazi
 */
public class CointraderOrder {

  private final Long id;
  private final Date created;
  private final Type type;
  private final BigDecimal quantity;
  private final BigDecimal price;
  private final BigDecimal total;
  private final CurrencyPair currencyPair;

  public CointraderOrder(@JsonProperty("id") Long id, @JsonProperty("created") @JsonDeserialize(using = SqlUtcTimeDeserializer.class) Date created,
      @JsonProperty("type") Type type, @JsonProperty("amount") BigDecimal quantity, @JsonProperty("price") BigDecimal price,
      @JsonProperty("total") BigDecimal total,
      @JsonProperty("currency_pair") @JsonDeserialize(using = CurrencyPairDeserializer.class) CurrencyPair currencyPair)
      throws ExceptionalReturnContentException {
    this.total = total;
    this.id = id;
    this.created = created;
    this.type = type;
    this.quantity = quantity;
    this.price = price;
    this.currencyPair = currencyPair;
  }

  public Long getId() {
    return id;
  }

  public Date getCreated() {
    return created;
  }

  public Type getType() {
    return type;
  }

  public BigDecimal getQuantity() {
    return quantity;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public CurrencyPair getCurrencyPair() {
    return currencyPair;
  }

  public BigDecimal getTotal() {
    return total;
  }

  @Override
  public String toString() {
    return String.format("CointraderOrder{id=%d, created=%s, type=%s, quantity=%s, price=%s, total=%s}", id, created, type, quantity, price, total);
  }

  public enum Type {
    Buy, Sell
  }
}