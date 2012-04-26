package com.xeiam.xchange;

import net.jcip.annotations.Immutable;

import com.xeiam.xchange.utils.Assert;

/**
 * <p>
 * Value object to provide the following to API:
 * </p>
 * <ul>
 * <li>Provision of major currency symbol pairs (EUR/USD, GBP/USD etc)</li>
 * <li>Provision of arbitrary symbol pairs for exchange index trading, notional currencies etc</li>
 * </ul>
 * <p>
 * Symbol pairs are quoted, for example, as EUR/USD 1.25 such that 1 EUR can be purchased with 1.25 USD
 * </p>
 */
@Immutable
public class CurrencyPair {

  // Provide some standard major symbols
  public static final CurrencyPair EUR_USD = new CurrencyPair("EUR");
  public static final CurrencyPair GBP_USD = new CurrencyPair("GBP");
  public static final CurrencyPair USD_JPY = new CurrencyPair("USD", "JPY");
  public static final CurrencyPair USD_CHF = new CurrencyPair("USD", "CHF");
  public static final CurrencyPair USD_AUD = new CurrencyPair("USD", "AUD");
  public static final CurrencyPair USD_CAD = new CurrencyPair("USD", "CAD");

  // Provide some courtesy BTC major symbols
  public static final CurrencyPair BTC_USD = new CurrencyPair("BTC");
  public static final CurrencyPair BTC_GBP = new CurrencyPair("BTC", "GBP");
  public static final CurrencyPair BTC_EUR = new CurrencyPair("BTC", "EUR");
  public static final CurrencyPair BTC_JPY = new CurrencyPair("BTC", "JPY");
  public static final CurrencyPair BTC_CHF = new CurrencyPair("BTC", "CHF");
  public static final CurrencyPair BTC_AUD = new CurrencyPair("BTC", "AUD");
  public static final CurrencyPair BTC_CAD = new CurrencyPair("BTC", "CAD");

  public final String baseSymbol;
  public final String counterSymbol;

  /**
   * <p>
   * Reduced constructor using the global reserve currency symbol (USD) as the default counter
   * </p>
   * 
   * @param baseSymbol The base symbol (single unit)
   */
  public CurrencyPair(String baseSymbol) {
    this(baseSymbol, "USD");
  }

  /**
   * <p>
   * Full constructor
   * </p>
   * 
   * @param baseSymbol The base symbol (single unit)
   * @param counterSymbol The counter symbol (multiple units)
   */
  public CurrencyPair(String baseSymbol, String counterSymbol) {

    Assert.hasLength(baseSymbol, 3, "baseSymbol cannot be null and must be 3 characters in length");
    Assert.hasLength(counterSymbol, 3, "counterSymbol cannot be null and must be 3 characters in length");
    this.baseSymbol = baseSymbol;
    this.counterSymbol = counterSymbol;
  }

  @Override
  public String toString() {
    return baseSymbol + "/" + counterSymbol;
  }

}
