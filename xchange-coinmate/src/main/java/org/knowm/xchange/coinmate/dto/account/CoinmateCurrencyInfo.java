package org.knowm.xchange.coinmate.dto.account;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

@Data
public class CoinmateCurrencyInfo {

  private final String currency;
  private final String currencyName;
  private final boolean depositEnabled;
  private final boolean withdrawEnabled;
  private final int precision;
  private final List<CoinmateNetworkInfo> networks;

  @JsonCreator
  public CoinmateCurrencyInfo(
      @JsonProperty("currency") String currency,
      @JsonProperty("currencyName") String currencyName,
      @JsonProperty("depositEnabled") boolean depositEnabled,
      @JsonProperty("withdrawEnabled") boolean withdrawEnabled,
      @JsonProperty("precision") int precision,
      @JsonProperty("networks") List<CoinmateNetworkInfo> networks) {
    this.currency = currency;
    this.currencyName = currencyName;
    this.depositEnabled = depositEnabled;
    this.withdrawEnabled = withdrawEnabled;
    this.precision = precision;
    this.networks = networks;
  }
}
