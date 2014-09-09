package com.xeiam.xchange.cryptotrade.service.polling;

import java.util.ArrayList;
import java.util.List;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.cryptotrade.CryptoTrade;
import com.xeiam.xchange.cryptotrade.dto.CryptoTradeBaseResponse;
import com.xeiam.xchange.cryptotrade.service.CryptoTradeHmacPostBodyDigest;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;

public class CryptoTradeBasePollingService<T extends CryptoTrade> extends BaseExchangeService implements BasePollingService {

  private static final long START_MILLIS = 1356998400000L; // Jan 1st, 2013 in milliseconds from epoch

  protected final String apiKey;
  protected final T cryptoTradeProxy;
  protected final ParamsDigest signatureCreator;

  /**
   * Constructor
   * 
   * @param exchangeSpecification The {@link ExchangeSpecification}
   */
  public CryptoTradeBasePollingService(Class<T> type, ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);

    this.cryptoTradeProxy = RestProxyFactory.createProxy(type, exchangeSpecification.getSslUri());
    this.apiKey = exchangeSpecification.getApiKey();
    this.signatureCreator = CryptoTradeHmacPostBodyDigest.createInstance(exchangeSpecification.getSecretKey());
  }

  protected int nextNonce() {

    return (int) ((System.currentTimeMillis() - START_MILLIS) / 250L);
  }

  protected <R extends CryptoTradeBaseResponse> R handleResponse(final R response) {

    final String status = response.getStatus();
    final String error = response.getError();
    if ((status != null && status.equalsIgnoreCase("error")) || (error != null && !error.isEmpty())) {
      throw new ExchangeException(error);
    }

    return response;
  }

  public static final List<CurrencyPair> CURRENCY_PAIRS = new ArrayList<CurrencyPair>();

  static {
    CURRENCY_PAIRS.add(new CurrencyPair("BC", "USD"));
    CURRENCY_PAIRS.add(new CurrencyPair("BC", "BTC"));
    CURRENCY_PAIRS.add(CurrencyPair.BTC_USD);
    CURRENCY_PAIRS.add(CurrencyPair.BTC_EUR);
    CURRENCY_PAIRS.add(new CurrencyPair("BTCD", "BTC"));
    CURRENCY_PAIRS.add(new CurrencyPair("BTCD", "USD"));
    CURRENCY_PAIRS.add(new CurrencyPair("BTSX", "BTC"));
    CURRENCY_PAIRS.add(new CurrencyPair("BTSX", "USD"));
    CURRENCY_PAIRS.add(new CurrencyPair("CARBON", "BTC"));
    CURRENCY_PAIRS.add(new CurrencyPair("CARBON", "USD"));
    CURRENCY_PAIRS.add(new CurrencyPair("CGB", "BTC"));
    CURRENCY_PAIRS.add(new CurrencyPair("CGB", "USD"));
    CURRENCY_PAIRS.add(new CurrencyPair("CINNI", "USD"));
    CURRENCY_PAIRS.add(new CurrencyPair("CINNI", "BTC"));
    CURRENCY_PAIRS.add(CurrencyPair.DGC_BTC);
    CURRENCY_PAIRS.add(new CurrencyPair("DOGE", "BTC"));
    CURRENCY_PAIRS.add(new CurrencyPair("DOGE", "USD"));
    CURRENCY_PAIRS.add(new CurrencyPair("DRK", "BTC"));
    CURRENCY_PAIRS.add(new CurrencyPair("DRK", "USD"));
    CURRENCY_PAIRS.add(CurrencyPair.DVC_BTC);
    CURRENCY_PAIRS.add(CurrencyPair.FTC_USD);
    CURRENCY_PAIRS.add(CurrencyPair.FTC_BTC);
    CURRENCY_PAIRS.add(CurrencyPair.LTC_USD);
    CURRENCY_PAIRS.add(CurrencyPair.LTC_EUR);
    CURRENCY_PAIRS.add(CurrencyPair.LTC_BTC);
    CURRENCY_PAIRS.add(CurrencyPair.NMC_BTC);
    CURRENCY_PAIRS.add(CurrencyPair.NMC_USD);
    CURRENCY_PAIRS.add(new CurrencyPair("NXT", "BTC"));
    CURRENCY_PAIRS.add(new CurrencyPair("NXT", "USD"));
    CURRENCY_PAIRS.add(new CurrencyPair("ORB", "BTC"));
    CURRENCY_PAIRS.add(new CurrencyPair("ORB", "USD"));
    CURRENCY_PAIRS.add(CurrencyPair.PPC_BTC);
    CURRENCY_PAIRS.add(CurrencyPair.PPC_USD);
    CURRENCY_PAIRS.add(new CurrencyPair("PXI", "BTC"));
    CURRENCY_PAIRS.add(new CurrencyPair("PXI", "USD"));
    CURRENCY_PAIRS.add(new CurrencyPair("RBT", "BTC"));
    CURRENCY_PAIRS.add(new CurrencyPair("RBT", "USD"));
    CURRENCY_PAIRS.add(new CurrencyPair("STR", "BTC"));
    CURRENCY_PAIRS.add(new CurrencyPair("STR", "USD"));
    CURRENCY_PAIRS.add(CurrencyPair.TRC_BTC);
    CURRENCY_PAIRS.add(CurrencyPair.UTC_USD);
    CURRENCY_PAIRS.add(CurrencyPair.UTC_EUR);
    CURRENCY_PAIRS.add(CurrencyPair.UTC_BTC);
    CURRENCY_PAIRS.add(CurrencyPair.UTC_LTC);
    CURRENCY_PAIRS.add(new CurrencyPair("VIA", "BTC"));
    CURRENCY_PAIRS.add(new CurrencyPair("VIA", "USD"));
    CURRENCY_PAIRS.add(CurrencyPair.WDC_USD);
    CURRENCY_PAIRS.add(CurrencyPair.WDC_BTC);
    CURRENCY_PAIRS.add(CurrencyPair.XPM_BTC);
    CURRENCY_PAIRS.add(CurrencyPair.XPM_USD);
    CURRENCY_PAIRS.add(CurrencyPair.XPM_PPC);

    // Securities
    CURRENCY_PAIRS.add(new CurrencyPair("AMC", "BTC"));
    CURRENCY_PAIRS.add(new CurrencyPair("BMI", "BTC"));
    CURRENCY_PAIRS.add(new CurrencyPair("CRM", "BTC"));
    CURRENCY_PAIRS.add(new CurrencyPair("CTB", "BTC"));
    CURRENCY_PAIRS.add(new CurrencyPair("CTL", "LTC"));
    CURRENCY_PAIRS.add(new CurrencyPair("CVF", "BTC"));
    CURRENCY_PAIRS.add(new CurrencyPair("ESB", "BTC"));
    CURRENCY_PAIRS.add(new CurrencyPair("ESL", "LTC"));
  }

  @Override
  public List<CurrencyPair> getExchangeSymbols() {

    return CURRENCY_PAIRS;
  }
}
