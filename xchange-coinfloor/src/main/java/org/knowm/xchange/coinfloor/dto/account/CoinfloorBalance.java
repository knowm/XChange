package org.knowm.xchange.coinfloor.dto.account;

import java.math.BigDecimal;
import java.util.Objects;

import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.Balance;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CoinfloorBalance {
  @JsonProperty("gbp_balance")
  public BigDecimal gbpBalance = BigDecimal.ZERO;

  @JsonProperty("usd_balance")
  public BigDecimal usdBalance = BigDecimal.ZERO;

  @JsonProperty("eur_balance")
  public BigDecimal eurBalance = BigDecimal.ZERO;

  @JsonProperty("xbt_balance")
  public BigDecimal btcBalance = BigDecimal.ZERO;

  @JsonProperty("gbp_reserved")
  public BigDecimal gbpReserved = BigDecimal.ZERO;

  @JsonProperty("usd_reserved")
  public BigDecimal usdReserved = BigDecimal.ZERO;

  @JsonProperty("eur_reserved")
  public BigDecimal eurReserved = BigDecimal.ZERO;

  @JsonProperty("xbt_reserved")
  public BigDecimal btcReserved = BigDecimal.ZERO;

  @JsonProperty("gbp_available")
  public BigDecimal gbpAvailable = BigDecimal.ZERO;

  @JsonProperty("usd_available")
  public BigDecimal usdAvailable = BigDecimal.ZERO;

  @JsonProperty("eur_available")
  public BigDecimal eurAvailable = BigDecimal.ZERO;

  @JsonProperty("xbt_available")
  public BigDecimal btcAvailable = BigDecimal.ZERO;

  public boolean hasCurrency(Currency currency) {
    if (currency.equals(Currency.BTC)) {
      return !Objects.equals(btcBalance, BigDecimal.ZERO);
    } else if (currency.equals(Currency.GBP)) {
      return !Objects.equals(gbpBalance, BigDecimal.ZERO);
    } else if (currency.equals(Currency.EUR)) {
      return !Objects.equals(eurBalance, BigDecimal.ZERO);
    } else if (currency.equals(Currency.USD)) {
      return !Objects.equals(usdBalance, BigDecimal.ZERO);
    } else {
      return false;
    }
  }

  public Balance getBalance(Currency currency) {
    if (currency.equals(Currency.XBT)) {
      return new Balance(currency, btcBalance, btcAvailable, btcReserved);
    } else if (currency.equals(Currency.GBP)) {
      return new Balance(currency, gbpBalance, gbpAvailable, gbpReserved);
    } else if (currency.equals(Currency.EUR)) {
      return new Balance(currency, eurBalance, eurAvailable, eurReserved);
    } else if (currency.equals(Currency.USD)) {
      return new Balance(currency, usdBalance, usdAvailable, usdReserved);
    } else {
      throw new IllegalArgumentException("Unsupported currency: " + currency);
    }
  }
}
