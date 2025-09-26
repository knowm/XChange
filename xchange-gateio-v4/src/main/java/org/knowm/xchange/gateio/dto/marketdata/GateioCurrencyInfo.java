package org.knowm.xchange.gateio.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.math.BigDecimal;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.gateio.config.converter.StringToCurrencyConverter;

@Data
@Builder
@Jacksonized
public class GateioCurrencyInfo {

  @JsonProperty("currency")
  @JsonDeserialize(converter = StringToCurrencyConverter.class)
  Currency currency;

  @JsonProperty("delisted")
  Boolean delisted;

  @JsonProperty("withdraw_disabled")
  Boolean withdrawDisabled;

  @JsonProperty("withdraw_delayed")
  Boolean withdrawDelayed;

  @JsonProperty("deposit_disabled")
  Boolean depositDisabled;

  @JsonProperty("trade_disabled")
  Boolean tradeDisabled;

  @JsonProperty("fixed_rate")
  BigDecimal fixedFeeRate;

  @JsonProperty("chain")
  String mainChain;

  @JsonProperty("chains")
  List<Chain> chains;

  public boolean isWithdrawEnabled() {
    return (withdrawDisabled != null) && !withdrawDisabled;
  }

  public boolean isDepositEnabled() {
    return (depositDisabled != null) && !depositDisabled;
  }

  @Data
  @Builder
  @Jacksonized
  public static class Chain {

    @JsonProperty("name")
    String name;

    @JsonProperty("addr")
    String address;

    @JsonProperty("withdraw_disabled")
    Boolean withdrawDisabled;

    @JsonProperty("withdraw_delayed")
    Boolean withdrawDelayed;

    @JsonProperty("deposit_disabled")
    Boolean depositDisabled;

    public boolean isWithdrawEnabled() {
      return (withdrawDisabled != null) && !withdrawDisabled;
    }

    public boolean isDepositEnabled() {
      return (depositDisabled != null) && !depositDisabled;
    }
  }
}
