package com.xeiam.xchange.currency;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

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
@JsonSerialize(using = CustomCurrencyPairSerializer.class)
public class CurrencyPair implements Comparable<CurrencyPair> {

  // Provide some standard major symbols
  public static final CurrencyPair EUR_USD = new CurrencyPair(Currencies.EUR, Currencies.USD);
  public static final CurrencyPair GBP_USD = new CurrencyPair(Currencies.GBP, Currencies.USD);
  public static final CurrencyPair USD_JPY = new CurrencyPair(Currencies.USD, Currencies.JPY);
  public static final CurrencyPair JPY_USD = new CurrencyPair(Currencies.JPY, Currencies.USD);
  public static final CurrencyPair USD_CHF = new CurrencyPair(Currencies.USD, Currencies.CHF);
  public static final CurrencyPair USD_AUD = new CurrencyPair(Currencies.USD, Currencies.AUD);
  public static final CurrencyPair USD_CAD = new CurrencyPair(Currencies.USD, Currencies.CAD);
  public static final CurrencyPair USD_RUR = new CurrencyPair(Currencies.USD, Currencies.RUR);
  public static final CurrencyPair EUR_RUR = new CurrencyPair(Currencies.EUR, Currencies.RUR);
  public static final CurrencyPair USD_XRP = new CurrencyPair(Currencies.USD, Currencies.XRP);
  public static final CurrencyPair EUR_XRP = new CurrencyPair(Currencies.EUR, Currencies.XRP);
  public static final CurrencyPair USD_XVN = new CurrencyPair(Currencies.USD, Currencies.XVN);
  public static final CurrencyPair EUR_XVN = new CurrencyPair(Currencies.EUR, Currencies.XVN);
  public static final CurrencyPair KRW_XRP = new CurrencyPair(Currencies.KRW, Currencies.XRP);

  // Provide some courtesy BTC major symbols
  public static final CurrencyPair BTC_USD = new CurrencyPair(Currencies.BTC, Currencies.USD);
  public static final CurrencyPair BTC_GBP = new CurrencyPair(Currencies.BTC, Currencies.GBP);
  public static final CurrencyPair BTC_EUR = new CurrencyPair(Currencies.BTC, Currencies.EUR);
  public static final CurrencyPair BTC_JPY = new CurrencyPair(Currencies.BTC, Currencies.JPY);
  public static final CurrencyPair BTC_CHF = new CurrencyPair(Currencies.BTC, Currencies.CHF);
  public static final CurrencyPair BTC_AUD = new CurrencyPair(Currencies.BTC, Currencies.AUD);
  public static final CurrencyPair BTC_CAD = new CurrencyPair(Currencies.BTC, Currencies.CAD);
  public static final CurrencyPair BTC_CNY = new CurrencyPair(Currencies.BTC, Currencies.CNY);
  public static final CurrencyPair BTC_DKK = new CurrencyPair(Currencies.BTC, Currencies.DKK);
  public static final CurrencyPair BTC_HKD = new CurrencyPair(Currencies.BTC, Currencies.HKD);
  public static final CurrencyPair BTC_MXN = new CurrencyPair(Currencies.BTC, Currencies.MXN);
  public static final CurrencyPair BTC_NZD = new CurrencyPair(Currencies.BTC, Currencies.NZD);
  public static final CurrencyPair BTC_PLN = new CurrencyPair(Currencies.BTC, Currencies.PLN);
  public static final CurrencyPair BTC_RUB = new CurrencyPair(Currencies.BTC, Currencies.RUB);
  public static final CurrencyPair BTC_SEK = new CurrencyPair(Currencies.BTC, Currencies.SEK);
  public static final CurrencyPair BTC_SGD = new CurrencyPair(Currencies.BTC, Currencies.SGD);
  public static final CurrencyPair BTC_NOK = new CurrencyPair(Currencies.BTC, Currencies.NOK);
  public static final CurrencyPair BTC_THB = new CurrencyPair(Currencies.BTC, Currencies.THB);
  public static final CurrencyPair BTC_RUR = new CurrencyPair(Currencies.BTC, Currencies.RUR);
  public static final CurrencyPair BTC_ZAR = new CurrencyPair(Currencies.BTC, Currencies.ZAR);
  public static final CurrencyPair BTC_BRL = new CurrencyPair(Currencies.BTC, Currencies.BRL);
  public static final CurrencyPair BTC_CZK = new CurrencyPair(Currencies.BTC, Currencies.CZK);
  public static final CurrencyPair BTC_ILS = new CurrencyPair(Currencies.BTC, Currencies.ILS);
  public static final CurrencyPair BTC_KRW = new CurrencyPair(Currencies.BTC, Currencies.KRW);
  public static final CurrencyPair BTC_LTC = new CurrencyPair(Currencies.BTC, Currencies.LTC);
  public static final CurrencyPair BTC_XRP = new CurrencyPair(Currencies.BTC, Currencies.XRP);
  public static final CurrencyPair BTC_NMC = new CurrencyPair(Currencies.BTC, Currencies.NMC);
  public static final CurrencyPair BTC_XVN = new CurrencyPair(Currencies.BTC, Currencies.XVN);
  public static final CurrencyPair BTC_IDR = new CurrencyPair(Currencies.BTC, Currencies.IDR);
  public static final CurrencyPair BTC_PHP = new CurrencyPair(Currencies.BTC, Currencies.PHP);
  public static final CurrencyPair BTC_STR = new CurrencyPair(Currencies.BTC, Currencies.STR);

  public static final CurrencyPair XDC_BTC = new CurrencyPair(Currencies.XDC, Currencies.BTC);

  public static final CurrencyPair XRP_BTC = new CurrencyPair(Currencies.XRP, Currencies.BTC);

  public static final CurrencyPair LTC_USD = new CurrencyPair(Currencies.LTC, Currencies.USD);
  public static final CurrencyPair LTC_KRW = new CurrencyPair(Currencies.LTC, Currencies.KRW);
  public static final CurrencyPair LTC_CNY = new CurrencyPair(Currencies.LTC, Currencies.CNY);
  public static final CurrencyPair LTC_RUR = new CurrencyPair(Currencies.LTC, Currencies.RUR);
  public static final CurrencyPair LTC_EUR = new CurrencyPair(Currencies.LTC, Currencies.EUR);
  public static final CurrencyPair LTC_BTC = new CurrencyPair(Currencies.LTC, Currencies.BTC);
  public static final CurrencyPair LTC_XRP = new CurrencyPair(Currencies.LTC, Currencies.XRP);

  public static final CurrencyPair NMC_USD = new CurrencyPair(Currencies.NMC, Currencies.USD);
  public static final CurrencyPair NMC_CNY = new CurrencyPair(Currencies.NMC, Currencies.CNY);
  public static final CurrencyPair NMC_EUR = new CurrencyPair(Currencies.NMC, Currencies.EUR);
  public static final CurrencyPair NMC_KRW = new CurrencyPair(Currencies.NMC, Currencies.KRW);
  public static final CurrencyPair NMC_BTC = new CurrencyPair(Currencies.NMC, Currencies.BTC);
  public static final CurrencyPair NMC_LTC = new CurrencyPair(Currencies.NMC, Currencies.LTC);
  public static final CurrencyPair NMC_XRP = new CurrencyPair(Currencies.NMC, Currencies.XRP);

  public static final CurrencyPair NVC_USD = new CurrencyPair(Currencies.NVC, Currencies.USD);
  public static final CurrencyPair NVC_BTC = new CurrencyPair(Currencies.NVC, Currencies.BTC);

  public static final CurrencyPair TRC_BTC = new CurrencyPair(Currencies.TRC, Currencies.BTC);

  public static final CurrencyPair PPC_USD = new CurrencyPair(Currencies.PPC, Currencies.USD);
  public static final CurrencyPair PPC_BTC = new CurrencyPair(Currencies.PPC, Currencies.BTC);
  public static final CurrencyPair PPC_LTC = new CurrencyPair(Currencies.PPC, Currencies.LTC);

  public static final CurrencyPair FTC_USD = new CurrencyPair(Currencies.FTC, Currencies.USD);
  public static final CurrencyPair FTC_CNY = new CurrencyPair(Currencies.FTC, Currencies.CNY);
  public static final CurrencyPair FTC_BTC = new CurrencyPair(Currencies.FTC, Currencies.BTC);
  public static final CurrencyPair FTC_LTC = new CurrencyPair(Currencies.FTC, Currencies.LTC);

  public static final CurrencyPair XPM_USD = new CurrencyPair(Currencies.XPM, Currencies.USD);
  public static final CurrencyPair XPM_CNY = new CurrencyPair(Currencies.XPM, Currencies.CNY);
  public static final CurrencyPair XPM_BTC = new CurrencyPair(Currencies.XPM, Currencies.BTC);
  public static final CurrencyPair XPM_LTC = new CurrencyPair(Currencies.XPM, Currencies.LTC);
  public static final CurrencyPair XPM_PPC = new CurrencyPair(Currencies.XPM, Currencies.PPC);

  public static final CurrencyPair XVN_XRP = new CurrencyPair(Currencies.XVN, Currencies.XRP);

  // start of extra ANX supported pair
  // BTC
  public static final CurrencyPair BTC_XDC = new CurrencyPair(Currencies.BTC, Currencies.XDC);
  public static final CurrencyPair BTC_PPC = new CurrencyPair(Currencies.BTC, Currencies.PPC);
  public static final CurrencyPair STR_BTC = new CurrencyPair(Currencies.STR, Currencies.BTC);

  // LTC
  public static final CurrencyPair LTC_HKD = new CurrencyPair(Currencies.LTC, Currencies.HKD);
  public static final CurrencyPair LTC_XDC = new CurrencyPair(Currencies.LTC, Currencies.XDC);
  public static final CurrencyPair LTC_NMC = new CurrencyPair(Currencies.LTC, Currencies.NMC);
  public static final CurrencyPair LTC_PPC = new CurrencyPair(Currencies.LTC, Currencies.PPC);

  // DOGE
  public static final CurrencyPair DOGE_HKD = new CurrencyPair(Currencies.DOGE, Currencies.HKD);
  public static final CurrencyPair DOGE_BTC = new CurrencyPair(Currencies.DOGE, Currencies.BTC);
  public static final CurrencyPair DOGE_LTC = new CurrencyPair(Currencies.DOGE, Currencies.LTC);
  public static final CurrencyPair DOGE_NMC = new CurrencyPair(Currencies.DOGE, Currencies.NMC);
  public static final CurrencyPair DOGE_PPC = new CurrencyPair(Currencies.DOGE, Currencies.PPC);
  public static final CurrencyPair DOGE_USD = new CurrencyPair(Currencies.DOGE, Currencies.USD);

  public static final CurrencyPair XDC_HKD = new CurrencyPair(Currencies.XDC, Currencies.HKD);
  public static final CurrencyPair XDC_LTC = new CurrencyPair(Currencies.XDC, Currencies.LTC);
  public static final CurrencyPair XDC_NMC = new CurrencyPair(Currencies.XDC, Currencies.NMC);
  public static final CurrencyPair XDC_PPC = new CurrencyPair(Currencies.XDC, Currencies.PPC);
  public static final CurrencyPair XDC_USD = new CurrencyPair(Currencies.XDC, Currencies.USD);

  // NMC
  public static final CurrencyPair NMC_HKD = new CurrencyPair(Currencies.NMC, Currencies.HKD);
  public static final CurrencyPair NMC_XDC = new CurrencyPair(Currencies.NMC, Currencies.XDC);
  public static final CurrencyPair NMC_PPC = new CurrencyPair(Currencies.NMC, Currencies.PPC);

  // PPC
  public static final CurrencyPair PPC_HKD = new CurrencyPair(Currencies.PPC, Currencies.HKD);
  public static final CurrencyPair PPC_XDC = new CurrencyPair(Currencies.PPC, Currencies.XDC);
  public static final CurrencyPair PPC_NMC = new CurrencyPair(Currencies.PPC, Currencies.NMC);
  // end

  // not real currencies, but tradable commodities (GH/s)
  public static final CurrencyPair GHs_BTC = new CurrencyPair(Currencies.GHs, Currencies.BTC);
  public static final CurrencyPair GHs_NMC = new CurrencyPair(Currencies.GHs, Currencies.NMC);

  public static final CurrencyPair CNC_BTC = new CurrencyPair(Currencies.CNC, Currencies.BTC);

  public static final CurrencyPair WDC_USD = new CurrencyPair(Currencies.WDC, Currencies.USD);
  public static final CurrencyPair WDC_BTC = new CurrencyPair(Currencies.WDC, Currencies.BTC);
  public static final CurrencyPair DVC_BTC = new CurrencyPair(Currencies.DVC, Currencies.BTC);

  public static final CurrencyPair DGC_BTC = new CurrencyPair(Currencies.DGC, Currencies.BTC);

  public static final CurrencyPair UTC_USD = new CurrencyPair(Currencies.UTC, Currencies.USD);
  public static final CurrencyPair UTC_EUR = new CurrencyPair(Currencies.UTC, Currencies.EUR);
  public static final CurrencyPair UTC_BTC = new CurrencyPair(Currencies.UTC, Currencies.BTC);
  public static final CurrencyPair UTC_LTC = new CurrencyPair(Currencies.UTC, Currencies.LTC);

  public final String baseSymbol;
  public final String counterSymbol;

  /**
   * <p>
   * Full constructor
   * </p>
   * In general the CurrencyPair.base is what you're wanting to buy/sell. The CurrencyPair.counter is what currency you want to use to pay/receive for
   * your purchase/sale.
   *
   * @param baseSymbol The base symbol is what you're wanting to buy/sell
   * @param counterSymbol The counter symbol is what currency you want to use to pay/receive for your purchase/sale.
   */
  public CurrencyPair(String baseSymbol, String counterSymbol) {

    this.baseSymbol = baseSymbol;
    this.counterSymbol = counterSymbol;
  }

  /**
   * Parse currency pair from a string in the same format as returned by toString() method - ABC/XYZ
   */
  public CurrencyPair(String currencyPair) {
    int split = currencyPair.indexOf("/");
    if (split < 1) {
      throw new IllegalArgumentException("Could not parse currency pair from '" + currencyPair + "'");
    }
    String base = currencyPair.substring(0, split);
    String counter = currencyPair.substring(split + 1);

    this.baseSymbol = base;
    this.counterSymbol = counter;
  }

  @Override
  public String toString() {

    return baseSymbol + "/" + counterSymbol;
  }

  @Override
  public int hashCode() {

    final int prime = 31;
    int result = 1;
    result = prime * result + ((baseSymbol == null) ? 0 : baseSymbol.hashCode());
    result = prime * result + ((counterSymbol == null) ? 0 : counterSymbol.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {

    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    CurrencyPair other = (CurrencyPair) obj;
    if (baseSymbol == null) {
      if (other.baseSymbol != null) {
        return false;
      }
    } else if (!baseSymbol.equals(other.baseSymbol)) {
      return false;
    }
    if (counterSymbol == null) {
      if (other.counterSymbol != null) {
        return false;
      }
    } else if (!counterSymbol.equals(other.counterSymbol)) {
      return false;
    }
    return true;
  }

  @Override
  public int compareTo(CurrencyPair o) {
    return (baseSymbol.compareTo(o.baseSymbol) << 16) + counterSymbol.compareTo(o.counterSymbol);
  }
}
