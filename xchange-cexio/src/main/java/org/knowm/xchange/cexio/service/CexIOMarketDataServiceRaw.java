package org.knowm.xchange.cexio.service;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.cexio.CexIO;
import org.knowm.xchange.cexio.dto.marketdata.CexIOCurrencyLimits;
import org.knowm.xchange.cexio.dto.marketdata.CexIODepth;
import org.knowm.xchange.cexio.dto.marketdata.CexIOTicker;
import org.knowm.xchange.cexio.dto.marketdata.CexIOTrade;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.currency.CurrencyPair;

/**
 * @author timmolter
 */
public class CexIOMarketDataServiceRaw extends CexIOBaseService {

  private final CexIO cexio;

  /**
   * Constructor
   *
   * @param exchange
   */
  public CexIOMarketDataServiceRaw(Exchange exchange) {

    super(exchange);

    this.cexio =
        ExchangeRestProxyBuilder.forInterface(CexIO.class, exchange.getExchangeSpecification())
            .build();
  }

  List<CexIOTicker> getAllCexIOTickers() throws IOException {
    return cexio.getAllTickers().getData();
  }

  public CexIOTicker getCexIOTicker(CurrencyPair currencyPair) throws IOException {

    CexIOTicker cexIOTicker =
        cexio.getTicker(
            currencyPair.getBase().getCurrencyCode(), currencyPair.getCounter().getCurrencyCode());

    return cexIOTicker;
  }

  public CexIODepth getCexIOOrderBook(CurrencyPair currencyPair) throws IOException {

    CexIODepth cexIODepth =
        cexio.getDepth(
            currencyPair.getBase().getCurrencyCode(), currencyPair.getCounter().getCurrencyCode());

    return cexIODepth;
  }

  public CexIOTrade[] getCexIOTrades(CurrencyPair currencyPair, Long since) throws IOException {

    CexIOTrade[] trades;

    if (since != null) {
      trades =
          cexio.getTradesSince(
              currencyPair.getBase().getCurrencyCode(),
              currencyPair.getCounter().getCurrencyCode(),
              since);
    } else { // default to full available trade history
      trades =
          cexio.getTrades(
              currencyPair.getBase().getCurrencyCode(),
              currencyPair.getCounter().getCurrencyCode());
    }

    return trades;
  }

  public CexIOCurrencyLimits getCurrencyLimits() throws IOException {
    return cexio.getCurrencyLimits();
  }
}
