package org.knowm.xchange.coinfloor.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Objects;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Balance.Builder;

public class CoinfloorBalance {
  @JsonProperty("gbp_balance")
  public BigDecimal gbpBalance = BigDecimal.ZERO;

  @JsonProperty("usd_balance")
  public BigDecimal usdBalance = BigDecimal.ZERO;

  @JsonProperty("eur_balance")
  public BigDecimal eurBalance = BigDecimal.ZERO;

  @JsonProperty("xbt_balance")
  public BigDecimal btcBalance = BigDecimal.ZERO;

  @JsonProperty("bch_balance")
  public BigDecimal bchBalance = BigDecimal.ZERO;

  @JsonProperty("eth_balance")
  public BigDecimal ethBalance = BigDecimal.ZERO;

  @JsonProperty("ltc_balance")
  public BigDecimal ltcBalance = BigDecimal.ZERO;

  @JsonProperty("xrp_balance")
  public BigDecimal xrpBalance = BigDecimal.ZERO;

  @JsonProperty("gbp_reserved")
  public BigDecimal gbpReserved = BigDecimal.ZERO;

  @JsonProperty("usd_reserved")
  public BigDecimal usdReserved = BigDecimal.ZERO;

  @JsonProperty("eur_reserved")
  public BigDecimal eurReserved = BigDecimal.ZERO;

  @JsonProperty("xbt_reserved")
  public BigDecimal btcReserved = BigDecimal.ZERO;

  @JsonProperty("bch_reserved")
  public BigDecimal bchReserved = BigDecimal.ZERO;

  @JsonProperty("eth_reserved")
  public BigDecimal ethReserved = BigDecimal.ZERO;

  @JsonProperty("ltc_reserved")
  public BigDecimal ltcReserved = BigDecimal.ZERO;

  @JsonProperty("xrp_reserved")
  public BigDecimal xrpReserved = BigDecimal.ZERO;

  @JsonProperty("gbp_available")
  public BigDecimal gbpAvailable = BigDecimal.ZERO;

  @JsonProperty("usd_available")
  public BigDecimal usdAvailable = BigDecimal.ZERO;

  @JsonProperty("eur_available")
  public BigDecimal eurAvailable = BigDecimal.ZERO;

  @JsonProperty("xbt_available")
  public BigDecimal btcAvailable = BigDecimal.ZERO;

  @JsonProperty("bch_available")
  public BigDecimal bchAvailable = BigDecimal.ZERO;

  @JsonProperty("eth_available")
  public BigDecimal ethAvailable = BigDecimal.ZERO;

  @JsonProperty("ltc_available")
  public BigDecimal ltcAvailable = BigDecimal.ZERO;

  @JsonProperty("xrp_available")
  public BigDecimal xrpAvailable = BigDecimal.ZERO;

  public boolean hasCurrency(Currency currency) {
    if (currency.equals(Currency.BTC)) {
      return !Objects.equals(btcBalance, BigDecimal.ZERO);
    } else if (currency.equals(Currency.GBP)) {
      return !Objects.equals(gbpBalance, BigDecimal.ZERO);
    } else if (currency.equals(Currency.EUR)) {
      return !Objects.equals(eurBalance, BigDecimal.ZERO);
    } else if (currency.equals(Currency.USD)) {
      return !Objects.equals(usdBalance, BigDecimal.ZERO);
    } else if (currency.equals(Currency.BCH)) {
      return !Objects.equals(bchBalance, BigDecimal.ZERO);
    } else if (currency.equals(Currency.XRP)) {
      return !Objects.equals(xrpBalance, BigDecimal.ZERO);
    } else if (currency.equals(Currency.LTC)) {
      return !Objects.equals(ltcBalance, BigDecimal.ZERO);
    } else if (currency.equals(Currency.ETH)) {
      return !Objects.equals(ethBalance, BigDecimal.ZERO);
    } else {
      return false;
    }
  }

  public Balance getBalance(Currency currency) {
    if (currency.equals(Currency.XBT)) {
      return new Builder()
          .setCurrency(currency)
          .setTotal(btcBalance)
          .setAvailable(btcAvailable)
          .setFrozen(btcReserved)
          .createBalance();
    } else if (currency.equals(Currency.GBP)) {
      return new Builder()
          .setCurrency(currency)
          .setTotal(gbpBalance)
          .setAvailable(gbpAvailable)
          .setFrozen(gbpReserved)
          .createBalance();
    } else if (currency.equals(Currency.EUR)) {
      return new Builder()
          .setCurrency(currency)
          .setTotal(eurBalance)
          .setAvailable(eurAvailable)
          .setFrozen(eurReserved)
          .createBalance();
    } else if (currency.equals(Currency.USD)) {
      return new Builder()
          .setCurrency(currency)
          .setTotal(usdBalance)
          .setAvailable(usdAvailable)
          .setFrozen(usdReserved)
          .createBalance();
    } else if (currency.equals(Currency.BCH)) {
      return new Builder()
          .setCurrency(currency)
          .setTotal(bchBalance)
          .setAvailable(bchAvailable)
          .setFrozen(bchReserved)
          .createBalance();
    } else if (currency.equals(Currency.XRP)) {
      return new Builder()
          .setCurrency(currency)
          .setTotal(xrpBalance)
          .setAvailable(xrpAvailable)
          .setFrozen(xrpReserved)
          .createBalance();
    } else if (currency.equals(Currency.LTC)) {
      return new Builder()
          .setCurrency(currency)
          .setTotal(ltcBalance)
          .setAvailable(ltcAvailable)
          .setFrozen(ltcReserved)
          .createBalance();
    } else if (currency.equals(Currency.ETH)) {
      return new Builder()
          .setCurrency(currency)
          .setTotal(ethBalance)
          .setAvailable(ethAvailable)
          .setFrozen(ethReserved)
          .createBalance();
    } else {
      throw new IllegalArgumentException("Unsupported currency: " + currency);
    }
  }
}
