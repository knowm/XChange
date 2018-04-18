package org.knowm.xchange.currency;

import java.io.Serializable;

// @JsonSerialize(using = CustomCurrencyPairSerializer.class)
public interface CurrencyPair extends Comparable<CurrencyPair>, Serializable {
  // Provide some standard major symbols
  CurrencyPair EUR_USD = build(Currency.EUR, Currency.USD);
  CurrencyPair GBP_USD = build(Currency.GBP, Currency.USD);
  CurrencyPair USD_JPY = build(Currency.USD, Currency.JPY);
  CurrencyPair JPY_USD = build(Currency.JPY, Currency.USD);
  CurrencyPair USD_CHF = build(Currency.USD, Currency.CHF);
  CurrencyPair USD_AUD = build(Currency.USD, Currency.AUD);
  CurrencyPair USD_CAD = build(Currency.USD, Currency.CAD);
  CurrencyPair USD_RUR = build(Currency.USD, Currency.RUR);
  CurrencyPair EUR_RUR = build(Currency.EUR, Currency.RUR);
  CurrencyPair USD_XRP = build(Currency.USD, Currency.XRP);
  CurrencyPair EUR_XRP = build(Currency.EUR, Currency.XRP);
  CurrencyPair USD_XVN = build(Currency.USD, Currency.XVN);
  CurrencyPair EUR_XVN = build(Currency.EUR, Currency.XVN);
  CurrencyPair KRW_XRP = build(Currency.KRW, Currency.XRP);
  // Provide some courtesy BTC major symbols
  CurrencyPair BTC_USD = build(Currency.BTC, Currency.USD);
  CurrencyPair BTC_GBP = build(Currency.BTC, Currency.GBP);
  CurrencyPair BTC_EUR = build(Currency.BTC, Currency.EUR);
  CurrencyPair BTC_JPY = build(Currency.BTC, Currency.JPY);
  CurrencyPair BTC_CHF = build(Currency.BTC, Currency.CHF);
  CurrencyPair BTC_AUD = build(Currency.BTC, Currency.AUD);
  CurrencyPair BTC_CAD = build(Currency.BTC, Currency.CAD);
  CurrencyPair BTC_CNY = build(Currency.BTC, Currency.CNY);
  CurrencyPair BTC_DKK = build(Currency.BTC, Currency.DKK);
  CurrencyPair BTC_HKD = build(Currency.BTC, Currency.HKD);
  CurrencyPair BTC_MXN = build(Currency.BTC, Currency.MXN);
  CurrencyPair BTC_NZD = build(Currency.BTC, Currency.NZD);
  CurrencyPair BTC_PLN = build(Currency.BTC, Currency.PLN);
  CurrencyPair BTC_RUB = build(Currency.BTC, Currency.RUB);
  CurrencyPair BTC_SEK = build(Currency.BTC, Currency.SEK);
  CurrencyPair BTC_SGD = build(Currency.BTC, Currency.SGD);
  CurrencyPair BTC_NOK = build(Currency.BTC, Currency.NOK);
  CurrencyPair BTC_THB = build(Currency.BTC, Currency.THB);
  CurrencyPair BTC_RUR = build(Currency.BTC, Currency.RUR);
  CurrencyPair BTC_ZAR = build(Currency.BTC, Currency.ZAR);
  CurrencyPair BTC_BRL = build(Currency.BTC, Currency.BRL);
  CurrencyPair BTC_CZK = build(Currency.BTC, Currency.CZK);
  CurrencyPair BTC_ILS = build(Currency.BTC, Currency.ILS);
  CurrencyPair BTC_KRW = build(Currency.BTC, Currency.KRW);
  CurrencyPair BTC_LTC = build(Currency.BTC, Currency.LTC);
  CurrencyPair BTC_XRP = build(Currency.BTC, Currency.XRP);
  CurrencyPair BTC_NMC = build(Currency.BTC, Currency.NMC);
  CurrencyPair BTC_XVN = build(Currency.BTC, Currency.XVN);
  CurrencyPair BTC_IDR = build(Currency.BTC, Currency.IDR);
  CurrencyPair BTC_PHP = build(Currency.BTC, Currency.PHP);
  CurrencyPair BTC_STR = build(Currency.BTC, Currency.STR);
  CurrencyPair BTC_INR = build(Currency.BTC, Currency.INR);
  CurrencyPair BTC_XAUR = build(Currency.BTC, Currency.XAUR);
  CurrencyPair BTC_IOC = build(Currency.BTC, Currency.IOC);
  CurrencyPair BTC_TRY = build(Currency.BTC, Currency.TRY);
  CurrencyPair ETH_TRY = build(Currency.ETH, Currency.TRY);
  CurrencyPair BCH_USD = build(Currency.BCH, Currency.USD);
  CurrencyPair BCH_AUD = build(Currency.BCH, Currency.AUD);
  CurrencyPair BCH_CAD = build(Currency.BCH, Currency.CAD);
  CurrencyPair BCH_GBP = build(Currency.BCH, Currency.GBP);
  CurrencyPair BCH_EUR = build(Currency.BCH, Currency.EUR);
  CurrencyPair BCH_BTC = build(Currency.BCH, Currency.BTC);
  CurrencyPair BCH_ETH = build(Currency.BCH, Currency.ETH);
  CurrencyPair ETH_BTC = build(Currency.ETH, Currency.BTC);
  CurrencyPair ETH_GBP = build(Currency.ETH, Currency.GBP);
  CurrencyPair ETH_USD = build(Currency.ETH, Currency.USD);
  CurrencyPair ETH_EUR = build(Currency.ETH, Currency.EUR);
  CurrencyPair ETH_JPY = build(Currency.ETH, Currency.JPY);
  CurrencyPair ETH_CNY = build(Currency.ETH, Currency.CNY);
  CurrencyPair ETH_AUD = build(Currency.ETH, Currency.AUD);
  CurrencyPair ETH_NZD = build(Currency.ETH, Currency.NZD);
  CurrencyPair XAUR_BTC = build(Currency.XAUR, Currency.BTC);
  CurrencyPair XDC_BTC = build(Currency.XDC, Currency.BTC);
  CurrencyPair SC_BTC = build(Currency.SC, Currency.BTC);
  CurrencyPair DCR_BTC = build(Currency.DCR, Currency.BTC);
  CurrencyPair XRP_BTC = build(Currency.XRP, Currency.BTC);
  CurrencyPair XRP_EUR = build(Currency.XRP, Currency.EUR);
  CurrencyPair XRP_USD = build(Currency.XRP, Currency.USD);
  CurrencyPair XRP_USDT = build(Currency.XRP, Currency.USDT);
  CurrencyPair XRP_GBP = build(Currency.XRP, Currency.GBP);
  CurrencyPair LTC_AUD = build(Currency.LTC, Currency.AUD);
  CurrencyPair LTC_GBP = build(Currency.LTC, Currency.GBP);
  CurrencyPair LTC_USD = build(Currency.LTC, Currency.USD);
  CurrencyPair LTC_KRW = build(Currency.LTC, Currency.KRW);
  CurrencyPair LTC_CNY = build(Currency.LTC, Currency.CNY);
  CurrencyPair LTC_RUR = build(Currency.LTC, Currency.RUR);
  CurrencyPair LTC_EUR = build(Currency.LTC, Currency.EUR);
  CurrencyPair LTC_BTC = build(Currency.LTC, Currency.BTC);
  CurrencyPair LTC_XRP = build(Currency.LTC, Currency.XRP);
  CurrencyPair LTC_TRY = build(Currency.LTC, Currency.TRY);
  CurrencyPair NMC_USD = build(Currency.NMC, Currency.USD);
  CurrencyPair NMC_CNY = build(Currency.NMC, Currency.CNY);
  CurrencyPair NMC_EUR = build(Currency.NMC, Currency.EUR);
  CurrencyPair NMC_KRW = build(Currency.NMC, Currency.KRW);
  CurrencyPair NMC_BTC = build(Currency.NMC, Currency.BTC);
  CurrencyPair NMC_LTC = build(Currency.NMC, Currency.LTC);
  CurrencyPair NMC_XRP = build(Currency.NMC, Currency.XRP);
  CurrencyPair NVC_USD = build(Currency.NVC, Currency.USD);
  CurrencyPair NVC_BTC = build(Currency.NVC, Currency.BTC);
  CurrencyPair TRC_BTC = build(Currency.TRC, Currency.BTC);
  CurrencyPair PPC_USD = build(Currency.PPC, Currency.USD);
  CurrencyPair PPC_BTC = build(Currency.PPC, Currency.BTC);
  CurrencyPair PPC_LTC = build(Currency.PPC, Currency.LTC);
  CurrencyPair FTC_USD = build(Currency.FTC, Currency.USD);
  CurrencyPair FTC_CNY = build(Currency.FTC, Currency.CNY);
  CurrencyPair FTC_BTC = build(Currency.FTC, Currency.BTC);
  CurrencyPair FTC_LTC = build(Currency.FTC, Currency.LTC);
  CurrencyPair XEM_USD = build(Currency.XEM, Currency.USD);
  CurrencyPair XEM_USDT = build(Currency.XEM, Currency.USDT);
  CurrencyPair XEM_KRW = build(Currency.XEM, Currency.KRW);
  CurrencyPair XEM_JPY = build(Currency.XEM, Currency.JPY);
  CurrencyPair XEM_BTC = build(Currency.XEM, Currency.BTC);
  CurrencyPair XEM_ETH = build(Currency.XEM, Currency.ETH);
  CurrencyPair XEM_EUR = build(Currency.XEM, Currency.EUR);
  CurrencyPair XMR_BTC = build(Currency.XMR, Currency.BTC);
  CurrencyPair XMR_ETH = build(Currency.XMR, Currency.ETH);
  CurrencyPair XMR_USD = build(Currency.XMR, Currency.USD);
  CurrencyPair XPM_USD = build(Currency.XPM, Currency.USD);
  CurrencyPair XPM_CNY = build(Currency.XPM, Currency.CNY);
  CurrencyPair XPM_BTC = build(Currency.XPM, Currency.BTC);
  CurrencyPair XPM_LTC = build(Currency.XPM, Currency.LTC);
  CurrencyPair XPM_PPC = build(Currency.XPM, Currency.PPC);
  CurrencyPair XVN_XRP = build(Currency.XVN, Currency.XRP);
  CurrencyPair STEEM_BTC = build(Currency.STEEM, Currency.BTC);
  CurrencyPair STEEM_USD = build(Currency.STEEM, Currency.USD);
  CurrencyPair STEEM_USDT = build(Currency.STEEM, Currency.USDT);
  CurrencyPair STEEM_ETH = build(Currency.STEEM, Currency.ETH);
  CurrencyPair STEEM_BNB = build(Currency.STEEM, Currency.BNB);
  CurrencyPair STEEM_KRW = build(Currency.STEEM, Currency.KRW);
  // start of extra ANX supported pair
  // BTC
  CurrencyPair BTC_XDC = build(Currency.BTC, Currency.XDC);
  CurrencyPair BTC_PPC = build(Currency.BTC, Currency.PPC);
  CurrencyPair STR_BTC = build(Currency.STR, Currency.BTC);
  // LTC
  CurrencyPair LTC_HKD = build(Currency.LTC, Currency.HKD);
  CurrencyPair LTC_XDC = build(Currency.LTC, Currency.XDC);
  CurrencyPair LTC_NMC = build(Currency.LTC, Currency.NMC);
  CurrencyPair LTC_PPC = build(Currency.LTC, Currency.PPC);
  // DOGE
  CurrencyPair DOGE_HKD = build(Currency.DOGE, Currency.HKD);
  CurrencyPair DOGE_BTC = build(Currency.DOGE, Currency.BTC);
  CurrencyPair DOGE_LTC = build(Currency.DOGE, Currency.LTC);
  CurrencyPair DOGE_NMC = build(Currency.DOGE, Currency.NMC);
  CurrencyPair DOGE_PPC = build(Currency.DOGE, Currency.PPC);
  CurrencyPair DOGE_USD = build(Currency.DOGE, Currency.USD);
  CurrencyPair DOGE_TRY = build(Currency.DOGE, Currency.TRY);
  CurrencyPair XDC_HKD = build(Currency.XDC, Currency.HKD);
  CurrencyPair XDC_LTC = build(Currency.XDC, Currency.LTC);
  CurrencyPair XDC_NMC = build(Currency.XDC, Currency.NMC);
  CurrencyPair XDC_PPC = build(Currency.XDC, Currency.PPC);
  CurrencyPair XDC_USD = build(Currency.XDC, Currency.USD);
  // NMC
  CurrencyPair NMC_HKD = build(Currency.NMC, Currency.HKD);
  CurrencyPair NMC_XDC = build(Currency.NMC, Currency.XDC);
  CurrencyPair NMC_PPC = build(Currency.NMC, Currency.PPC);
  // PPC
  CurrencyPair PPC_HKD = build(Currency.PPC, Currency.HKD);
  CurrencyPair PPC_XDC = build(Currency.PPC, Currency.XDC);
  CurrencyPair PPC_NMC = build(Currency.PPC, Currency.NMC);
  // IOTA
  CurrencyPair IOTA_USD = build(Currency.IOT, Currency.USD);
  CurrencyPair IOTA_BTC = build(Currency.IOT, Currency.BTC);
  CurrencyPair IOTA_ETH = build(Currency.IOT, Currency.ETH);
  // OMG
  CurrencyPair OMG_USD = build(Currency.OMG, Currency.USD);
  CurrencyPair OMG_BTC = build(Currency.OMG, Currency.BTC);
  CurrencyPair OMG_ETH = build(Currency.OMG, Currency.ETH);
  // not real currencies, but tradable commodities (GH/s)
  CurrencyPair GHs_BTC = build(Currency.GHs, Currency.BTC);
  CurrencyPair GHs_NMC = build(Currency.GHs, Currency.NMC);
  CurrencyPair CNC_BTC = build(Currency.CNC, Currency.BTC);
  CurrencyPair WDC_USD = build(Currency.WDC, Currency.USD);
  CurrencyPair WDC_BTC = build(Currency.WDC, Currency.BTC);
  CurrencyPair DVC_BTC = build(Currency.DVC, Currency.BTC);
  CurrencyPair DGC_BTC = build(Currency.DGC, Currency.BTC);
  CurrencyPair UTC_USD = build(Currency.UTC, Currency.USD);
  CurrencyPair UTC_EUR = build(Currency.UTC, Currency.EUR);
  CurrencyPair UTC_BTC = build(Currency.UTC, Currency.BTC);
  CurrencyPair UTC_LTC = build(Currency.UTC, Currency.LTC);
  // Kraken additional pairs
  CurrencyPair ETC_BTC = build(Currency.ETC, Currency.BTC);
  CurrencyPair ETC_EUR = build(Currency.ETC, Currency.EUR);
  CurrencyPair ETC_ETH = build(Currency.ETC, Currency.ETH);
  CurrencyPair ETC_USD = build(Currency.ETC, Currency.USD);
  CurrencyPair ICN_BTC = build(Currency.ICN, Currency.BTC);
  CurrencyPair ICN_ETH = build(Currency.ICN, Currency.ETH);
  CurrencyPair DASH_USD = build(Currency.DASH, Currency.USD);
  CurrencyPair DASH_EUR = build(Currency.DASH, Currency.EUR);
  CurrencyPair DASH_BTC = build(Currency.DASH, Currency.BTC);
  CurrencyPair DASH_TRY = build(Currency.DASH, Currency.TRY);
  CurrencyPair MLN_ETH = build(Currency.MLN, Currency.ETH);
  CurrencyPair MLN_BTC = build(Currency.MLN, Currency.BTC);
  CurrencyPair ZEC_EUR = build(Currency.ZEC, Currency.EUR);
  CurrencyPair ZEC_USD = build(Currency.ZEC, Currency.USD);
  CurrencyPair ZEC_BTC = build(Currency.ZEC, Currency.BTC);
  CurrencyPair GNO_ETH = build(Currency.GNO, Currency.ETH);
  CurrencyPair GNO_BTC = build(Currency.GNO, Currency.BTC);
  CurrencyPair EOS_ETH = build(Currency.EOS, Currency.ETH);
  CurrencyPair EOS_BTC = build(Currency.EOS, Currency.BTC);
  CurrencyPair BCC_USD = build(Currency.BCC, Currency.USD);
  CurrencyPair BCC_BTC = build(Currency.BCC, Currency.BTC);
  CurrencyPair BAT_USD = build(Currency.BAT, Currency.USD);
  CurrencyPair BAT_BTC = build(Currency.BAT, Currency.BTC);
  // Tether Pairs
  CurrencyPair BTC_USDT = build(Currency.BTC, Currency.USDT);
  // UAH pairs
  CurrencyPair BTC_UAH = build(Currency.BTC, Currency.UAH);
  CurrencyPair ETH_UAH = build(Currency.ETH, Currency.UAH);
  CurrencyPair BCH_UAH = build(Currency.BCH, Currency.UAH);
  CurrencyPair ETC_7D = build(Currency.ETC, Currency.valueOf("7D"));

  static String getOutput(Currency base, Currency counter) {
    return base + "/" + counter;
  }

  static CurrencyPair valueOf(String in) {
    String[] split = in.split("/");
    return build(split[0], split[1]);
  }

  static org.knowm.xchange.currency.impl.CurrencyPair build(Currency base, Currency counter) {
    return new org.knowm.xchange.currency.impl.CurrencyPair(base, counter);
  }

  static org.knowm.xchange.currency.impl.CurrencyPair build(
      String baseSymbol, String counterSymbol) {
    return new org.knowm.xchange.currency.impl.CurrencyPair(baseSymbol, counterSymbol);
  }

  static org.knowm.xchange.currency.impl.CurrencyPair build(String currencyPair) {
    return new org.knowm.xchange.currency.impl.CurrencyPair(currencyPair);
  }

  Currency getBase();

  Currency getCounter();
}
