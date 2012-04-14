package com.xeiam.xchange;

import com.xeiam.xchange.utils.Assert;
import net.jcip.annotations.Immutable;

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
// TODO Refactor rename this to CurrencyPair
@Immutable
public class SymbolPair {

  // Provide some standard major symbols
  public static final SymbolPair EUR_USD = new SymbolPair("EUR");
  public static final SymbolPair GBP_USD = new SymbolPair("GBP");
  public static final SymbolPair USD_JPY = new SymbolPair("USD", "JPY");
  public static final SymbolPair USD_CHF = new SymbolPair("USD", "CHF");
  public static final SymbolPair USD_AUD = new SymbolPair("USD", "AUD");
  public static final SymbolPair USD_CAD = new SymbolPair("USD", "CAD");

  // Provide some courtesy BTC major symbols
  public static final SymbolPair BTC_USD = new SymbolPair("BTC");
  public static final SymbolPair BTC_GBP = new SymbolPair("BTC", "GBP");
  public static final SymbolPair BTC_EUR = new SymbolPair("BTC", "EUR");
  public static final SymbolPair BTC_JPY = new SymbolPair("BTC", "JPY");
  public static final SymbolPair BTC_CHF = new SymbolPair("BTC", "CHF");
  public static final SymbolPair BTC_AUD = new SymbolPair("BTC", "AUD");
  public static final SymbolPair BTC_CAD = new SymbolPair("BTC", "CAD");

  public final String baseSymbol;
  public final String counterSymbol;

  /**
   * <p>
   * Reduced constructor using the global reserve currency symbol (USD) as the default counter
   * </p>
   * 
   * @param baseSymbol The base symbol (single unit)
   */
  public SymbolPair(String baseSymbol) {
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
  public SymbolPair(String baseSymbol, String counterSymbol) {

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
