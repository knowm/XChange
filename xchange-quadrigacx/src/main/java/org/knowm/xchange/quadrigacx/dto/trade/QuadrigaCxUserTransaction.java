package org.knowm.xchange.quadrigacx.dto.trade;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import org.knowm.xchange.quadrigacx.util.QuadrigaCxTransactionTypeDeserializer;

public final class QuadrigaCxUserTransaction {

  private final String datetime;
  private final long id;
  private final String order_id;
  private final TransactionType type;
  private final BigDecimal rate;
  private final BigDecimal fee;
  private final String method;
  private Map<String, BigDecimal> currencyAmount = new HashMap<>();

  /**
   * Constructor
   *
   * @param datetime
   * @param id
   * @param order_id
   * @param type
   * @param rate
   * @param fee
   * @param method
   */
  public QuadrigaCxUserTransaction(
      @JsonProperty("datetime") String datetime,
      @JsonProperty("id") long id,
      @JsonProperty("order_id") String order_id,
      @JsonProperty("type") @JsonDeserialize(using = QuadrigaCxTransactionTypeDeserializer.class)
          TransactionType type,
      @JsonProperty("rate") BigDecimal rate,
      @JsonProperty("fee") BigDecimal fee,
      @JsonProperty("method") String method) {

    this.datetime = datetime;
    this.id = id;
    this.order_id = order_id;
    this.type = type;
    this.rate = rate;
    this.fee = fee;
    this.method = method;
  }

  public String getDatetime() {

    return datetime;
  }

  public long getId() {

    return id;
  }

  public String getOrderId() {

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

  public BigDecimal getPrice() {

    return rate;
  }

  public BigDecimal getFee() {

    return fee;
  }

  public String getMethod() {

    return method;
  }

  @JsonAnySetter
  public void setCurrencyAmount(String currency, BigDecimal ammount) {
    this.currencyAmount.put(currency, ammount);
  }

  public BigDecimal getCurrencyAmount(String currency) {
    return this.currencyAmount.get(currency.toLowerCase());
  }

  @Override
  public String toString() {
    return "QuadrigaCxUserTransaction{"
        + "datetime='"
        + datetime
        + '\''
        + ", id="
        + id
        + ", order_id='"
        + order_id
        + '\''
        + ", type="
        + type
        + ", rate="
        + rate
        + ", fee="
        + fee
        + ", method='"
        + method
        + '\''
        + ", currencyAmount="
        + currencyAmount
        + '}';
  }

  public enum TransactionType {
    deposit,
    withdrawal,
    trade,
    type3_reserved,
    type4_reserved,
    type5_reseverd,
    type6_reseved,
    type7_reserved
  }
}
