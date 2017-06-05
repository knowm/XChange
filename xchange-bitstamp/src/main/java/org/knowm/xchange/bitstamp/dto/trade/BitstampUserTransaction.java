package org.knowm.xchange.bitstamp.dto.trade;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.knowm.xchange.bitstamp.util.BitstampTransactionTypeDeserializer;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * @author Matija Mazi
 */
public final class BitstampUserTransaction {

  private final String datetime;
  private final long id;
  private final long order_id;
  private final TransactionType type;
  private final BigDecimal fee;
  
  // possible pairs at the moment: btcusd, btceur, eurusd, xrpusd, xrpeur, xrpbtc
  private String base;          // btc, eur, xrp
  private String counter;       // usd, eur, btc
  
  private final Map<String, BigDecimal> amounts = new HashMap<>();
  private BigDecimal price;

  /**
   * Constructor
   *
   * @param datetime
   * @param id
   * @param order_id
   * @param type
   * @param fee
   */
  public BitstampUserTransaction(@JsonProperty("datetime") String datetime, @JsonProperty("id") long id
          , @JsonProperty("order_id") long order_id
          , @JsonProperty("type") @JsonDeserialize(using = BitstampTransactionTypeDeserializer.class) TransactionType type
          , @JsonProperty("fee") BigDecimal fee) {

    this.datetime = datetime;
    this.id = id;
    this.order_id = order_id;
    this.type = type;
    this.fee = fee;             // fee currency is the counter currency
  }
  
  @JsonAnySetter
  public void setDynamicProperty(String name, BigDecimal value) {
      // here we handle dynamically the amounts of base and counter curency plus the rate (price), which contains the underscore, ie "btc_usd
      int i = name.indexOf('_');
      if (i >= 0) {
          base = name.substring(0, i);
          counter = name.substring(i + 1);
          price = value;
      } else {
          amounts.put(name, value);
      }
  }
  
  public String getDatetime() {
    return datetime;
  }

  public long getId() {
    return id;
  }

  public long getOrderId() {
    return order_id;
  }

  public TransactionType getType() {
    return type;
  }

  public boolean isDeposit() {
    return type == TransactionType.deposit;
  }

  public boolean isWithdrawal() {
    return type == TransactionType.withdrawal;
  }

  public boolean isMarketTrade() {
    return type == TransactionType.trade;
  }

  public BigDecimal getCounterAmount() {
    return amounts.get(counter);
  }
  
  public BigDecimal getBaseAmount() {
      return amounts.get(base);
    }

  public BigDecimal getPrice() {
    return price;
  }

  public String getCounterCurrency() {
    return counter;
  }
  
  public String getBaseCurrency() {
      return base;
  }

  public BigDecimal getFee() {
    return fee;
  }
  public String getFeeCurrency() {
      return counter;
    }

  @Override
  public String toString() {

    return String.format("UserTransaction{datetime=%s, id=%d, type=%s, base=%s, counter=%s, fee=%s}", datetime, id, type, base, counter, fee);
  }

  public enum TransactionType {
    deposit, withdrawal, trade, rippleWithdrawal, rippleDeposit, type5_reseverd, type6_reseved, type7_reserved /*
                                                                                                                * reseved so parsing won 't break in
                                                                                                                * case Bitstamp adds new types
                                                                                                                */
  }
}
