package com.xeiam.xchange.btc38.service.polling;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.btc38.Btc38;
import com.xeiam.xchange.btc38.dto.marketdata.Btc38TickerReturn;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;
import si.mazi.rescu.RestProxyFactory;

import java.io.IOException;
import java.util.*;

/**
 * Created by Yingzhe on 12/18/2014.
 */
public class Btc38BasePollingService<T extends Btc38> extends BaseExchangeService implements BasePollingService {

  private static HashMap<String, CurrencyPair> CURRENCY_PAIR_MAP;
  private static List<CurrencyPair> CURRENCY_PAIR_LIST;
  protected final T btc38;

  /**
   * Constructor Initialize common properties from the exchange specification
   *
   * @param exchange The {@link com.xeiam.xchange.Exchange}
   */
  protected Btc38BasePollingService(Class<T> type, Exchange exchange) {

    super(exchange);

    this.btc38 = RestProxyFactory.createProxy(type, exchange.getExchangeSpecification().getSslUri());
  }

  protected HashMap<String, CurrencyPair> getCurrencyPairMap() throws IOException {

    if (CURRENCY_PAIR_MAP == null) {
      CURRENCY_PAIR_MAP = new HashMap<String, CurrencyPair>();
      Map<String, Btc38TickerReturn> btcTickers = this.btc38.getMarketTicker("BTC");
      Map<String, Btc38TickerReturn> cnyTickers = this.btc38.getMarketTicker("CNY");

      if (btcTickers != null) {
        for (String key : btcTickers.keySet()) {
          String base = key.toUpperCase();
          String target = "BTC";
          CURRENCY_PAIR_MAP.put(base + "_" + target, new CurrencyPair(base, target));
        }
      }

      if (cnyTickers != null) {
        for (String key : cnyTickers.keySet()) {
          String base = key.toUpperCase();
          String target = "CNY";
          CURRENCY_PAIR_MAP.put(base + "_" + target, new CurrencyPair(base, target));
        }
      }
    }

    return CURRENCY_PAIR_MAP;
  }

  @Override
  public List<CurrencyPair> getExchangeSymbols() throws IOException {

    if (CURRENCY_PAIR_LIST == null) {
      CURRENCY_PAIR_LIST = new ArrayList<CurrencyPair>(this.getCurrencyPairMap().values());
    }

    return CURRENCY_PAIR_LIST;
  }
}