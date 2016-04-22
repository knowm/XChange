package org.knowm.xchange.cointrader.dto.trade;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.utils.jackson.CurrencyPairDeserializer;
import org.knowm.xchange.utils.jackson.SqlUtcTimeDeserializer;

/**
 * @author Matija Mazi
 */
@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
public final class CointraderUserTrade {

  @JsonProperty
  @JsonDeserialize(using = CurrencyPairDeserializer.class)
  CurrencyPair currencyPair;
  @JsonProperty
  Long tradeId;
  @JsonProperty
  Long orderId;
  @JsonProperty
  @JsonDeserialize(using = SqlUtcTimeDeserializer.class)
  Date executed;
  @JsonProperty
  CointraderOrder.Type type;
  @JsonProperty
  BigDecimal quantity;
  @JsonProperty
  BigDecimal price;
  @JsonProperty
  BigDecimal fee;
  @JsonProperty
  BigDecimal total;

  public Long getTradeId() {
    return tradeId;
  }

  public Long getOrderId() {
    return orderId;
  }

  public Date getExecuted() {
    return executed;
  }

  public CointraderOrder.Type getType() {
    return type;
  }

  public BigDecimal getQuantity() {
    return quantity;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public BigDecimal getFee() {
    return fee;
  }

  public BigDecimal getTotal() {
    return total;
  }

  public CurrencyPair getCurrencyPair() {
    return currencyPair;
  }

  @Override
  public String toString() {
    return String.format(
        "CointraderUserTransaction{id=%d, currency_pair=%s, order_id=%d, microtime=%s, type=%s, amount=%s, price=%s, fee=%s, total=%s}", tradeId,
        currencyPair, orderId, executed, type, quantity, price, fee, total);
  }
}
