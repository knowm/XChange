package org.knowm.xchange.abucoins.dto.account;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * <p>POJO representing and individual object from the output JSON for the Abucoins
 * <code>GET /payment-methods</code> endpoint.</p>
 *
 * Example:
 * <code><pre>
 *     {
 *         "id": "sepa_pln",
 *         "type": "sepa_pln",
 *         "name": "PLN",
 *         "currency": "PLN",
 *         "allow_buy": true,
 *         "allow_sell": true,
 *         "allow_deposit": true,
 *         "allow_withdraw": true,
 *         "limits": {
 *             "buy": 10000,
 *             "sell": 10000,
 *             "deposit": 9223372036854775807,
 *             "withdraw": 9223372036854775807
 *         }
 * </pre></code>
 * @author bryant_harris
 */
public class AbucoinsPaymentMethod {
  String id;
  String type;
  String name;
  String currency;
  boolean allowBuy;
  boolean allowSell;
  boolean allowDeposit;
  boolean allowWithdrawal;
  Limit limits;
  String message;
        
  public AbucoinsPaymentMethod(@JsonProperty("id") String id,
                               @JsonProperty("type") String type,
                               @JsonProperty("name") String name,
                               @JsonProperty("currency") String currency,
                               @JsonProperty("allow_buy") boolean allowBuy,
                               @JsonProperty("allow_sell") boolean allowSell,
                               @JsonProperty("allow_deposit") boolean allowDeposit,
                               @JsonProperty("allow_withdrawl") boolean allowWithdrawal,
                               @JsonProperty("limits") Limit limits,
                               @JsonProperty("message") String message) {
    this.id = id;
    this.type = type;
    this.name = name;
    this.currency = currency;
    this.allowBuy = allowBuy;
    this.allowSell = allowSell;
    this.allowDeposit = allowDeposit;
    this.allowWithdrawal = allowWithdrawal;
    this.limits = limits;
    this.message = message;
  }
        
  public String getId() {
    return id;
  }

  public String getType() {
    return type;
  }

  public String getName() {
    return name;
  }

  public String getCurrency() {
    return currency;
  }

  public boolean isAllowBuy() {
    return allowBuy;
  }

  public boolean isAllowSell() {
    return allowSell;
  }

  public boolean isAllowDeposit() {
    return allowDeposit;
  }

  public boolean isAllowWithdrawal() {
    return allowWithdrawal;
  }

  public Limit getLimits() {
    return limits;
  }
  
  public String getMessage() {
        return message;
  }

  @Override
  public String toString() {
    return "AbucoinsPaymentMethod [id=" + id + ", type=" + type + ", name=" + name + ", currency=" + currency
        + ", allowBuy=" + allowBuy + ", allowSell=" + allowSell + ", allowDeposit=" + allowDeposit
        + ", allowWithdrawal=" + allowWithdrawal + ", limits=" + limits + ", message=" + message + "]";
  }

  public static class Limit {
    BigDecimal buy;
    BigDecimal sell;
    BigDecimal deposit;
    BigDecimal withdraw;
                
    public Limit (@JsonProperty("buy") BigDecimal buy,
                  @JsonProperty("sell") BigDecimal sell,
                  @JsonProperty("deposity") BigDecimal deposit,
                  @JsonProperty("withdraw") BigDecimal withdraw) {
      this.buy = buy;
      this.sell = sell;
      this.deposit = deposit;
      this.withdraw = withdraw;
    }
      
    public BigDecimal getBuy() {
      return buy;
    }

    public BigDecimal getSell() {
      return sell;
    }

    public BigDecimal getDeposit() {
      return deposit;
    }

    public BigDecimal getWithdraw() {
      return withdraw;
    }

    @Override
    public String toString() {
      return "Limit [buy=" + buy + ", sell=" + sell + ", deposit=" + deposit + ", withdraw=" + withdraw + "]";
    }
  }
}
