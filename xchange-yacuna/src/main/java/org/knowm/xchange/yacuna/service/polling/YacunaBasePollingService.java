package org.knowm.xchange.yacuna.service.polling;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.polling.BasePollingService;
import org.knowm.xchange.yacuna.Yacuna;
import org.knowm.xchange.yacuna.dto.marketdata.YacunaTicker;
import org.knowm.xchange.yacuna.dto.marketdata.YacunaTickerReturn;

import si.mazi.rescu.RestProxyFactory;

/**
 * Created by Yingzhe on 12/27/2014.
 */
public class YacunaBasePollingService<T extends Yacuna> extends BaseExchangeService implements BasePollingService {

  private static HashMap<String, CurrencyPair> CURRENCY_PAIR_MAP;
  private static List<CurrencyPair> CURRENCY_PAIR_LIST;
  protected final T yacuna;

  protected YacunaBasePollingService(Class<T> type, Exchange exchange) {

    super(exchange);
    this.yacuna = RestProxyFactory.createProxy(type, exchange.getExchangeSpecification().getSslUri());
  }

  protected HashMap<String, CurrencyPair> getCurrencyPairMap() throws IOException {

    if (CURRENCY_PAIR_MAP == null) {
      CURRENCY_PAIR_MAP = new HashMap<String, CurrencyPair>();
      YacunaTickerReturn tickers = this.yacuna.getTickers();

      if (tickers != null && tickers.getTickerList() != null) {
        for (YacunaTicker yt : tickers.getTickerList()) {
          String base = yt.getBaseCurrency().toUpperCase();
          String target = yt.getTargetCurrency().toUpperCase();
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
