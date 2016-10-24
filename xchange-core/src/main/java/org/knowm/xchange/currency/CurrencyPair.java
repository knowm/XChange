package org.knowm.xchange.currency;

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
  public static final CurrencyPair EUR_USD = new CurrencyPair(Currency.EUR, Currency.USD);
  public static final CurrencyPair GBP_USD = new CurrencyPair(Currency.GBP, Currency.USD);
  public static final CurrencyPair USD_JPY = new CurrencyPair(Currency.USD, Currency.JPY);
  public static final CurrencyPair JPY_USD = new CurrencyPair(Currency.JPY, Currency.USD);
  public static final CurrencyPair USD_CHF = new CurrencyPair(Currency.USD, Currency.CHF);
  public static final CurrencyPair USD_AUD = new CurrencyPair(Currency.USD, Currency.AUD);
  public static final CurrencyPair USD_CAD = new CurrencyPair(Currency.USD, Currency.CAD);
  public static final CurrencyPair USD_RUR = new CurrencyPair(Currency.USD, Currency.RUR);
  public static final CurrencyPair EUR_RUR = new CurrencyPair(Currency.EUR, Currency.RUR);
  public static final CurrencyPair USD_XRP = new CurrencyPair(Currency.USD, Currency.XRP);
  public static final CurrencyPair EUR_XRP = new CurrencyPair(Currency.EUR, Currency.XRP);
  public static final CurrencyPair USD_XVN = new CurrencyPair(Currency.USD, Currency.XVN);
  public static final CurrencyPair EUR_XVN = new CurrencyPair(Currency.EUR, Currency.XVN);
  public static final CurrencyPair KRW_XRP = new CurrencyPair(Currency.KRW, Currency.XRP);

  // Provide some courtesy BTC major symbols
  public static final CurrencyPair BTC_USD = new CurrencyPair(Currency.BTC, Currency.USD);
  public static final CurrencyPair BTC_GBP = new CurrencyPair(Currency.BTC, Currency.GBP);
  public static final CurrencyPair BTC_EUR = new CurrencyPair(Currency.BTC, Currency.EUR);
  public static final CurrencyPair BTC_JPY = new CurrencyPair(Currency.BTC, Currency.JPY);
  public static final CurrencyPair BTC_CHF = new CurrencyPair(Currency.BTC, Currency.CHF);
  public static final CurrencyPair BTC_AUD = new CurrencyPair(Currency.BTC, Currency.AUD);
  public static final CurrencyPair BTC_CAD = new CurrencyPair(Currency.BTC, Currency.CAD);
  public static final CurrencyPair BTC_CNY = new CurrencyPair(Currency.BTC, Currency.CNY);
  public static final CurrencyPair BTC_DKK = new CurrencyPair(Currency.BTC, Currency.DKK);
  public static final CurrencyPair BTC_HKD = new CurrencyPair(Currency.BTC, Currency.HKD);
  public static final CurrencyPair BTC_MXN = new CurrencyPair(Currency.BTC, Currency.MXN);
  public static final CurrencyPair BTC_NZD = new CurrencyPair(Currency.BTC, Currency.NZD);
  public static final CurrencyPair BTC_PLN = new CurrencyPair(Currency.BTC, Currency.PLN);
  public static final CurrencyPair BTC_RUB = new CurrencyPair(Currency.BTC, Currency.RUB);
  public static final CurrencyPair BTC_SEK = new CurrencyPair(Currency.BTC, Currency.SEK);
  public static final CurrencyPair BTC_SGD = new CurrencyPair(Currency.BTC, Currency.SGD);
  public static final CurrencyPair BTC_NOK = new CurrencyPair(Currency.BTC, Currency.NOK);
  public static final CurrencyPair BTC_THB = new CurrencyPair(Currency.BTC, Currency.THB);
  public static final CurrencyPair BTC_RUR = new CurrencyPair(Currency.BTC, Currency.RUR);
  public static final CurrencyPair BTC_ZAR = new CurrencyPair(Currency.BTC, Currency.ZAR);
  public static final CurrencyPair BTC_BRL = new CurrencyPair(Currency.BTC, Currency.BRL);
  public static final CurrencyPair BTC_CZK = new CurrencyPair(Currency.BTC, Currency.CZK);
  public static final CurrencyPair BTC_ILS = new CurrencyPair(Currency.BTC, Currency.ILS);
  public static final CurrencyPair BTC_KRW = new CurrencyPair(Currency.BTC, Currency.KRW);
  public static final CurrencyPair BTC_LTC = new CurrencyPair(Currency.BTC, Currency.LTC);
  public static final CurrencyPair BTC_XRP = new CurrencyPair(Currency.BTC, Currency.XRP);
  public static final CurrencyPair BTC_NMC = new CurrencyPair(Currency.BTC, Currency.NMC);
  public static final CurrencyPair BTC_XVN = new CurrencyPair(Currency.BTC, Currency.XVN);
  public static final CurrencyPair BTC_IDR = new CurrencyPair(Currency.BTC, Currency.IDR);
  public static final CurrencyPair BTC_PHP = new CurrencyPair(Currency.BTC, Currency.PHP);
  public static final CurrencyPair BTC_STR = new CurrencyPair(Currency.BTC, Currency.STR);
  public static final CurrencyPair BTC_INR = new CurrencyPair(Currency.BTC, Currency.INR);
  public static final CurrencyPair BTC_XAUR = new CurrencyPair(Currency.BTC, Currency.XAUR);

  public static final CurrencyPair ETH_BTC = new CurrencyPair(Currency.ETH, Currency.BTC);
  public static final CurrencyPair ETH_USD = new CurrencyPair(Currency.ETH, Currency.USD);
  public static final CurrencyPair ETH_EUR = new CurrencyPair(Currency.ETH, Currency.EUR);
  public static final CurrencyPair ETH_JPY = new CurrencyPair(Currency.ETH, Currency.JPY);
  public static final CurrencyPair ETH_CNY = new CurrencyPair(Currency.ETH, Currency.CNY);
  public static final CurrencyPair ETH_AUD = new CurrencyPair(Currency.ETH, Currency.AUD);
  public static final CurrencyPair ETH_NZD = new CurrencyPair(Currency.ETH, Currency.NZD);

  public static final CurrencyPair XAUR_BTC = new CurrencyPair(Currency.XAUR, Currency.BTC);
  
  public static final CurrencyPair XDC_BTC = new CurrencyPair(Currency.XDC, Currency.BTC);

  public static final CurrencyPair XRP_BTC = new CurrencyPair(Currency.XRP, Currency.BTC);

  public static final CurrencyPair LTC_USD = new CurrencyPair(Currency.LTC, Currency.USD);
  public static final CurrencyPair LTC_KRW = new CurrencyPair(Currency.LTC, Currency.KRW);
  public static final CurrencyPair LTC_CNY = new CurrencyPair(Currency.LTC, Currency.CNY);
  public static final CurrencyPair LTC_RUR = new CurrencyPair(Currency.LTC, Currency.RUR);
  public static final CurrencyPair LTC_EUR = new CurrencyPair(Currency.LTC, Currency.EUR);
  public static final CurrencyPair LTC_BTC = new CurrencyPair(Currency.LTC, Currency.BTC);
  public static final CurrencyPair LTC_XRP = new CurrencyPair(Currency.LTC, Currency.XRP);

  public static final CurrencyPair NMC_USD = new CurrencyPair(Currency.NMC, Currency.USD);
  public static final CurrencyPair NMC_CNY = new CurrencyPair(Currency.NMC, Currency.CNY);
  public static final CurrencyPair NMC_EUR = new CurrencyPair(Currency.NMC, Currency.EUR);
  public static final CurrencyPair NMC_KRW = new CurrencyPair(Currency.NMC, Currency.KRW);
  public static final CurrencyPair NMC_BTC = new CurrencyPair(Currency.NMC, Currency.BTC);
  public static final CurrencyPair NMC_LTC = new CurrencyPair(Currency.NMC, Currency.LTC);
  public static final CurrencyPair NMC_XRP = new CurrencyPair(Currency.NMC, Currency.XRP);

  public static final CurrencyPair NVC_USD = new CurrencyPair(Currency.NVC, Currency.USD);
  public static final CurrencyPair NVC_BTC = new CurrencyPair(Currency.NVC, Currency.BTC);

  public static final CurrencyPair TRC_BTC = new CurrencyPair(Currency.TRC, Currency.BTC);

  public static final CurrencyPair PPC_USD = new CurrencyPair(Currency.PPC, Currency.USD);
  public static final CurrencyPair PPC_BTC = new CurrencyPair(Currency.PPC, Currency.BTC);
  public static final CurrencyPair PPC_LTC = new CurrencyPair(Currency.PPC, Currency.LTC);

  public static final CurrencyPair FTC_USD = new CurrencyPair(Currency.FTC, Currency.USD);
  public static final CurrencyPair FTC_CNY = new CurrencyPair(Currency.FTC, Currency.CNY);
  public static final CurrencyPair FTC_BTC = new CurrencyPair(Currency.FTC, Currency.BTC);
  public static final CurrencyPair FTC_LTC = new CurrencyPair(Currency.FTC, Currency.LTC);

  public static final CurrencyPair XPM_USD = new CurrencyPair(Currency.XPM, Currency.USD);
  public static final CurrencyPair XPM_CNY = new CurrencyPair(Currency.XPM, Currency.CNY);
  public static final CurrencyPair XPM_BTC = new CurrencyPair(Currency.XPM, Currency.BTC);
  public static final CurrencyPair XPM_LTC = new CurrencyPair(Currency.XPM, Currency.LTC);
  public static final CurrencyPair XPM_PPC = new CurrencyPair(Currency.XPM, Currency.PPC);

  public static final CurrencyPair XVN_XRP = new CurrencyPair(Currency.XVN, Currency.XRP);

  // start of extra ANX supported pair
  // BTC
  public static final CurrencyPair BTC_XDC = new CurrencyPair(Currency.BTC, Currency.XDC);
  public static final CurrencyPair BTC_PPC = new CurrencyPair(Currency.BTC, Currency.PPC);
  public static final CurrencyPair STR_BTC = new CurrencyPair(Currency.STR, Currency.BTC);

  // LTC
  public static final CurrencyPair LTC_HKD = new CurrencyPair(Currency.LTC, Currency.HKD);
  public static final CurrencyPair LTC_XDC = new CurrencyPair(Currency.LTC, Currency.XDC);
  public static final CurrencyPair LTC_NMC = new CurrencyPair(Currency.LTC, Currency.NMC);
  public static final CurrencyPair LTC_PPC = new CurrencyPair(Currency.LTC, Currency.PPC);

  // DOGE
  public static final CurrencyPair DOGE_HKD = new CurrencyPair(Currency.DOGE, Currency.HKD);
  public static final CurrencyPair DOGE_BTC = new CurrencyPair(Currency.DOGE, Currency.BTC);
  public static final CurrencyPair DOGE_LTC = new CurrencyPair(Currency.DOGE, Currency.LTC);
  public static final CurrencyPair DOGE_NMC = new CurrencyPair(Currency.DOGE, Currency.NMC);
  public static final CurrencyPair DOGE_PPC = new CurrencyPair(Currency.DOGE, Currency.PPC);
  public static final CurrencyPair DOGE_USD = new CurrencyPair(Currency.DOGE, Currency.USD);

  public static final CurrencyPair XDC_HKD = new CurrencyPair(Currency.XDC, Currency.HKD);
  public static final CurrencyPair XDC_LTC = new CurrencyPair(Currency.XDC, Currency.LTC);
  public static final CurrencyPair XDC_NMC = new CurrencyPair(Currency.XDC, Currency.NMC);
  public static final CurrencyPair XDC_PPC = new CurrencyPair(Currency.XDC, Currency.PPC);
  public static final CurrencyPair XDC_USD = new CurrencyPair(Currency.XDC, Currency.USD);

  // NMC
  public static final CurrencyPair NMC_HKD = new CurrencyPair(Currency.NMC, Currency.HKD);
  public static final CurrencyPair NMC_XDC = new CurrencyPair(Currency.NMC, Currency.XDC);
  public static final CurrencyPair NMC_PPC = new CurrencyPair(Currency.NMC, Currency.PPC);

  // PPC
  public static final CurrencyPair PPC_HKD = new CurrencyPair(Currency.PPC, Currency.HKD);
  public static final CurrencyPair PPC_XDC = new CurrencyPair(Currency.PPC, Currency.XDC);
  public static final CurrencyPair PPC_NMC = new CurrencyPair(Currency.PPC, Currency.NMC);
  // end

  // not real currencies, but tradable commodities (GH/s)
  public static final CurrencyPair GHs_BTC = new CurrencyPair(Currency.GHs, Currency.BTC);
  public static final CurrencyPair GHs_NMC = new CurrencyPair(Currency.GHs, Currency.NMC);

  public static final CurrencyPair CNC_BTC = new CurrencyPair(Currency.CNC, Currency.BTC);

  public static final CurrencyPair WDC_USD = new CurrencyPair(Currency.WDC, Currency.USD);
  public static final CurrencyPair WDC_BTC = new CurrencyPair(Currency.WDC, Currency.BTC);
  public static final CurrencyPair DVC_BTC = new CurrencyPair(Currency.DVC, Currency.BTC);

  public static final CurrencyPair DGC_BTC = new CurrencyPair(Currency.DGC, Currency.BTC);

  public static final CurrencyPair UTC_USD = new CurrencyPair(Currency.UTC, Currency.USD);
  public static final CurrencyPair UTC_EUR = new CurrencyPair(Currency.UTC, Currency.EUR);
  public static final CurrencyPair UTC_BTC = new CurrencyPair(Currency.UTC, Currency.BTC);
  public static final CurrencyPair UTC_LTC = new CurrencyPair(Currency.UTC, Currency.LTC);

  public final Currency base;
  public final Currency counter;

  /**
   * <p>
   * Full constructor
   * </p>
   * In general the CurrencyPair.base is what you're wanting to buy/sell. The CurrencyPair.counter is what currency you want to use to pay/receive for
   * your purchase/sale.
   *
   * @param base The base currency is what you're wanting to buy/sell
   * @param counter The counter currency is what currency you want to use to pay/receive for your purchase/sale.
   */
  public CurrencyPair(Currency base, Currency counter) {

    this.base = base;
    this.counter = counter;
  }

  /**
   * <p>
   * String constructor
   * </p>
   * In general the CurrencyPair.base is what you're wanting to buy/sell. The CurrencyPair.counter is what currency you want to use to pay/receive for
   * your purchase/sale.
   *
   * @param baseSymbol The base symbol is what you're wanting to buy/sell
   * @param counterSymbol The counter symbol is what currency you want to use to pay/receive for your purchase/sale.
   */
  public CurrencyPair(String baseSymbol, String counterSymbol) {

    this(Currency.getInstance(baseSymbol), Currency.getInstance(counterSymbol));
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

    this.base = Currency.getInstance(base);
    this.counter = Currency.getInstance(counter);
  }

  @Override
  public String toString() {

    return base + "/" + counter;
  }

  @Override
  public int hashCode() {

    final int prime = 31;
    int result = 1;
    result = prime * result + ((base == null) ? 0 : base.hashCode());
    result = prime * result + ((counter == null) ? 0 : counter.hashCode());
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
    if (base == null) {
      if (other.base != null) {
        return false;
      }
    } else if (!base.equals(other.base)) {
      return false;
    }
    if (counter == null) {
      if (other.counter != null) {
        return false;
      }
    } else if (!counter.equals(other.counter)) {
      return false;
    }
    return true;
  }

  @Override
  public int compareTo(CurrencyPair o) {

    return (base.compareTo(o.base) << 16) + counter.compareTo(o.counter);
  }
}
