package org.knowm.xchange.bithumb.service;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bithumb.BithumbUtils;
import org.knowm.xchange.bithumb.dto.BithumbResponse;
import org.knowm.xchange.bithumb.dto.marketdata.BithumbOrderbook;
import org.knowm.xchange.bithumb.dto.marketdata.BithumbTicker;
import org.knowm.xchange.bithumb.dto.marketdata.BithumbTickersReturn;
import org.knowm.xchange.bithumb.dto.marketdata.BithumbTransactionHistory;
import org.knowm.xchange.currency.CurrencyPair;

public class BithumbMarketDataServiceRaw extends BithumbBaseService {

  protected BithumbMarketDataServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public BithumbTicker getBithumbTicker(CurrencyPair currencyPair) throws IOException {
    final BithumbResponse<BithumbTicker> ticker =
        bithumb.ticker(BithumbUtils.getBaseCurrency(currencyPair));
    return checkResult(ticker);
  }

  public BithumbTickersReturn getBithumbTickers() throws IOException {
    final BithumbResponse<BithumbTickersReturn> tickerAll = bithumb.tickerAll();
    return checkResult(tickerAll);
  }

  public BithumbOrderbook getBithumbOrderBook(CurrencyPair currencyPair) throws IOException {
    final BithumbResponse<BithumbOrderbook> orderbook =
        bithumb.orderbook(BithumbUtils.getBaseCurrency(currencyPair));
    return checkResult(orderbook);
  }

  public List<BithumbTransactionHistory> getBithumbTrades(CurrencyPair currencyPair)
      throws IOException {
    final BithumbResponse<List<BithumbTransactionHistory>> transactionHistory =
        bithumb.transactionHistory(BithumbUtils.getBaseCurrency(currencyPair));
    return checkResult(transactionHistory);
  }
}
