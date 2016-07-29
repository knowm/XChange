package org.knowm.xchange.coinfloor.dto.streaming.trade;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.coinfloor.CoinfloorUtils;
import org.knowm.xchange.coinfloor.CoinfloorUtils.CoinfloorCurrency;

/**
 * @author obsessiveOrange
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class CoinfloorCancelOrder {

  private final int tag;
  private final int errorCode;
  private final CoinfloorCurrency base;
  private final CoinfloorCurrency counter;
  private final BigDecimal quantity;
  private final BigDecimal price;

  public CoinfloorCancelOrder(@JsonProperty("tag") int tag, @JsonProperty("errorCode") int errorCode, @JsonProperty("base") int base,
      @JsonProperty("counter") int counter, @JsonProperty("quantity") int quantity, @JsonProperty("price") int price) {

    this.tag = tag;
    this.errorCode = errorCode;
    this.base = CoinfloorUtils.getCurrency(base);
    this.counter = CoinfloorUtils.getCurrency(counter);
    this.quantity = CoinfloorUtils.scaleToBigDecimal(this.base, quantity);
    this.price = CoinfloorUtils.scalePriceToBigDecimal(this.base, this.counter, price);
  }

  public int getTag() {

    return tag;
  }

  public int getErrorCode() {

    return errorCode;
  }

  public CoinfloorCurrency getBase() {

    return base;
  }

  public CoinfloorCurrency getCounter() {

    return counter;
  }

  public BigDecimal getQuantity() {

    return quantity;
  }

  public BigDecimal getPrice() {

    return price;
  }

  @Override
  public String toString() {

    return "CoinfloorCancelOrderReturn{tag='" + tag + "', errorcode='" + errorCode + "', base='" + base + "', counter='" + counter + "', quantity='"
        + quantity + "', price='" + price + "'}";
  }
}
