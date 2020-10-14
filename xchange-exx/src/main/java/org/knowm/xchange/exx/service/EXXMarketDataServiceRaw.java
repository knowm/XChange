package org.knowm.xchange.exx.service;

import java.io.IOException;
import java.util.Map;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.exx.EXX;
import org.knowm.xchange.exx.EXXAdapters;
import org.knowm.xchange.exx.dto.marketdata.EXXOrderbook;
import org.knowm.xchange.exx.dto.marketdata.EXXTicker;
import org.knowm.xchange.exx.dto.marketdata.EXXTickerResponse;
import org.knowm.xchange.exx.dto.marketdata.EXXTransaction;
import si.mazi.rescu.SynchronizedValueFactory;

public class EXXMarketDataServiceRaw extends EXXBaseService {
  private final EXX exx;

  private String apiKey;
  private String secretKey;
  private SynchronizedValueFactory<Long> nonceFactory;

  public EXXMarketDataServiceRaw(Exchange exchange) {

    super(exchange);

    this.exx =
        ExchangeRestProxyBuilder.forInterface(EXX.class, exchange.getExchangeSpecification())
            .build();

    this.apiKey = super.apiKey;
    this.secretKey = super.secretKey;
    this.nonceFactory = exchange.getNonceFactory();
  }

  /**
   * @return Object
   * @throws IOException
   */
  public EXXOrderbook getExxOrderBook(CurrencyPair currencyPair) throws IOException {

    return exx.getOrderBook(EXXAdapters.toSymbol(currencyPair));
  }

  /**
   * getEXXTicker
   *
   * @return Object
   * @throws IOException
   */
  public EXXTickerResponse getExxTicker(CurrencyPair currencyPair) throws IOException {

    return exx.getTicker(EXXAdapters.toSymbol(currencyPair));
  }

  protected Map<String, EXXTicker> getExxTickers() throws IOException {
    Map<String, EXXTicker> data = exx.getTickers();

    return data;
  }

  /**
   * @param currencyPair
   * @return
   * @throws IOException
   */
  public EXXTransaction[] getTransactions(CurrencyPair currencyPair) throws IOException {
    try {
      return exx.getTransactions(EXXAdapters.toSymbol(currencyPair));
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
    return null;
  }
}
