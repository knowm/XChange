package org.knowm.xchange.bitstamp.dto.trade;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.knowm.xchange.bitstamp.BitstampUtils;
import org.knowm.xchange.currency.Currency;

/** @author Matija Mazi */
public final class BitstampUserTransaction {

  private final Date datetime;
  private final long id;
  private final long order_id;
  private final TransactionType type;
  private final BigDecimal fee;
  private final Map<String, BigDecimal> amounts = new HashMap<>();
  // possible pairs at the moment: btcusd, btceur, eurusd, xrpusd, xrpeur, xrpbtc
  private String base; // btc, eur, xrp
  private String counter; // usd, eur, btc
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
  public BitstampUserTransaction(
      @JsonProperty("datetime") String datetime,
      @JsonProperty("id") long id,
      @JsonProperty("order_id") long order_id,
      @JsonProperty("type") TransactionType type,
      @JsonProperty("fee") BigDecimal fee) {

    this.datetime = BitstampUtils.parseDate(datetime);
    this.id = id;
    this.order_id = order_id;
    this.type = type;
    this.fee = fee; // fee currency is the counter currency
  }

  @JsonAnySetter
  public void setDynamicProperty(String name, Object value) {
    // here we handle dynamically the amounts of base and counter curency plus the rate (price),
    // which contains the underscore, ie "btc_usd
    final Set<String> ccyCodeList = Currency.getAvailableCurrencyCodes();
    String[] nameArr = name.toUpperCase().split("_");
    String name1 = nameArr[0];
    if (nameArr.length == 2) {
      String name2 = nameArr[1];
      if (ccyCodeList.contains(name1) && ccyCodeList.contains(name2)) {
        base = name1;
        counter = name2;
        price = new BigDecimal(value.toString());
      }
    } else if (nameArr.length == 1) {
      if (ccyCodeList.contains(name1)) {
        amounts.put(name1, new BigDecimal(value.toString()));
      }
    }
  }

  public Date getDatetime() {
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

  public boolean isSubAccountTransfer() {
    return type == TransactionType.subAccountTransfer;
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

  public Map<String, BigDecimal> getAmounts() {
    return amounts;
  }

  @Override
  public String toString() {
    return "BitstampUserTransaction [datetime="
        + datetime
        + ", id="
        + id
        + ", order_id="
        + order_id
        + ", type="
        + type
        + ", fee="
        + fee
        + ", base="
        + base
        + ", counter="
        + counter
        + ", amounts="
        + amounts
        + ", price="
        + price
        + "]";
  }

  public enum TransactionType {
    deposit,
    withdrawal,
    trade,
    rippleWithdrawal,
    rippleDeposit,
    subAccountTransfer,
    creditedWithStakedAssets,
    sentAssetsToStaking,
    stakingReward,
    referralReward,
    interAccountTransfer;

    @JsonCreator
    public static TransactionType fromString(int type) {
      switch (type) {
        case 0:
          return deposit;
        case 1:
          return withdrawal;
        case 2:
          return trade;
        case 3:
          return rippleWithdrawal;
        case 4:
          return rippleDeposit;
        case 14:
          return subAccountTransfer;
        case 25:
          return creditedWithStakedAssets;
        case 26:
          return sentAssetsToStaking;
        case 27:
          return stakingReward;
        case 32:
          return referralReward;
        case 35:
          return interAccountTransfer;
        default:
          throw new IllegalArgumentException(type + " has no corresponding value");
      }
    }
  }
}
