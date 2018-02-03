package org.knowm.xchange.kucoin.service;

import java.io.IOException;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.kucoin.Kucoin;
import org.knowm.xchange.kucoin.KucoinException;
import org.knowm.xchange.kucoin.dto.marketdata.KucoinTicker;
import org.knowm.xchange.service.BaseService;

import si.mazi.rescu.RestProxyFactory;

/**
 * @author Jan Akerman
 */
public class KucoinMarketDataServiceRaw implements BaseService {

  private final Kucoin kucoin;

  public KucoinMarketDataServiceRaw(Exchange exchange) {
    kucoin = RestProxyFactory.createProxy(Kucoin.class, exchange.getExchangeSpecification().getSslUri());
  }

  public KucoinTicker getKucoinTicker(CurrencyPair currencyPair) throws IOException {
    try {
      return kucoin.getTicker(Kucoin.toSymbol(currencyPair)).getData();
    } catch (KucoinException e) {
      throw new ExchangeException(e.getError(), e);
    }
  }

  public List<KucoinTicker> getKucoinTickers() throws IOException {
    try {
      return kucoin.getTickers().getData();
    } catch (KucoinException e) {
      throw new ExchangeException(e.getError(), e);
    }
  }

}
