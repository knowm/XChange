package org.knowm.xchange.coinbase.v2.dto.account;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Collections;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CoinbasePaymentMethodsData {

  private final List<CoinbasePaymentMethod> data;

  private CoinbasePaymentMethodsData(@JsonProperty("data") final List<CoinbasePaymentMethod> data) {
    this.data = data;
  }

  public List<CoinbasePaymentMethod> getData() {
    return Collections.unmodifiableList(data);
  }

  @Override
  public String toString() {
    return "" + data;
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class CoinbasePaymentMethod {

    private final String id;
    private final String name;
    private final String type;
    private final String currency;

    private boolean primaryBuy;
    private boolean primarySell;
    private boolean allowBuy;
    private boolean allowSell;
    private boolean allowDeposit;
    private boolean allowWithdraw;
    private boolean instantBuy;
    private boolean instantSell;
    private boolean verified;

    @JsonCreator
    CoinbasePaymentMethod(
        @JsonProperty("id") String id,
        @JsonProperty("name") String name,
        @JsonProperty("type") String type,
        @JsonProperty("currency") String currency) {
      this.id = id;
      this.name = name;
      this.type = type;
      this.currency = currency;
    }

    public String getId() {
      return id;
    }

    public String getName() {
      return name;
    }

    public String getType() {
      return type;
    }

    public String getCurrency() {
      return currency;
    }

    public boolean isPrimaryBuy() {
      return primaryBuy;
    }

    public void setPrimaryBuy(boolean primaryBuy) {
      this.primaryBuy = primaryBuy;
    }

    public boolean isPrimarySell() {
      return primarySell;
    }

    public void setPrimarySell(boolean primarySell) {
      this.primarySell = primarySell;
    }

    public boolean isAllowBuy() {
      return allowBuy;
    }

    public void setAllowBuy(boolean allowBuy) {
      this.allowBuy = allowBuy;
    }

    public boolean isAllowSell() {
      return allowSell;
    }

    public void setAllowSell(boolean allowSell) {
      this.allowSell = allowSell;
    }

    public boolean isAllowDeposit() {
      return allowDeposit;
    }

    public void setAllowDeposit(boolean allowDeposit) {
      this.allowDeposit = allowDeposit;
    }

    public boolean isAllowWithdraw() {
      return allowWithdraw;
    }

    public void setAllowWithdraw(boolean allowWithdraw) {
      this.allowWithdraw = allowWithdraw;
    }

    public boolean isInstantBuy() {
      return instantBuy;
    }

    public void setInstantBuy(boolean instantBuy) {
      this.instantBuy = instantBuy;
    }

    public boolean isInstantSell() {
      return instantSell;
    }

    public void setInstantSell(boolean instantSell) {
      this.instantSell = instantSell;
    }

    public boolean isVerified() {
      return verified;
    }

    public void setVerified(boolean verified) {
      this.verified = verified;
    }

    @Override
    public String toString() {
      return "CoinbasePaymentMethod [id="
          + id
          + ", name="
          + name
          + ", type="
          + type
          + ", currency="
          + currency
          + "]";
    }
  }
}
