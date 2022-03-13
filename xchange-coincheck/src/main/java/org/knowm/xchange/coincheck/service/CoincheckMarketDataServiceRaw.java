package org.knowm.xchange.coincheck.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.coincheck.Coincheck;
import org.knowm.xchange.coincheck.dto.marketdata.CoincheckOrderBook;
import org.knowm.xchange.coincheck.dto.marketdata.CoincheckPagination;
import org.knowm.xchange.coincheck.dto.marketdata.CoincheckPair;
import org.knowm.xchange.coincheck.dto.marketdata.CoincheckTicker;
import org.knowm.xchange.coincheck.dto.marketdata.CoincheckTradesContainer;

public class CoincheckMarketDataServiceRaw extends CoincheckBaseService {
  private final Coincheck coincheck;

  public CoincheckMarketDataServiceRaw(Exchange exchange) {
    super(exchange);
    coincheck =
        ExchangeRestProxyBuilder.forInterface(Coincheck.class, exchange.getExchangeSpecification())
            .build();
  }

  public CoincheckTicker getCoincheckTicker(CoincheckPair pair) throws IOException {
    return coincheck.getTicker(pair);
  }

  public CoincheckOrderBook getCoincheckOrderBook(CoincheckPair pair) throws IOException {
    return coincheck.getOrderBook(pair);
  }

  public CoincheckTradesContainer getCoincheckTrades(
      CoincheckPair pair, CoincheckPagination pagination) throws IOException {
    if (pagination == null) {
      pagination = CoincheckPagination.builder().build();
    }
    return coincheck.getTrades(
        pair,
        pagination.getLimit(),
        pagination.getOrder(),
        pagination.getStartingAfter(),
        pagination.getEndingBefore());
  }
}
