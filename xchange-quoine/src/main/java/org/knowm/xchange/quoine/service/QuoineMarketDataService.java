package org.knowm.xchange.quoine.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.quoine.QuoineAdapters;
import org.knowm.xchange.quoine.dto.marketdata.QuoineOrderBook;
import org.knowm.xchange.quoine.dto.marketdata.QuoineProduct;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.marketdata.params.CurrencyPairsParam;
import org.knowm.xchange.service.marketdata.params.Params;

public class QuoineMarketDataService extends QuoineMarketDataServiceRaw
    implements MarketDataService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public QuoineMarketDataService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {

    QuoineProduct quoineTicker = getQuoineProduct(QuoineAdapters.toPairString(currencyPair));
    return QuoineAdapters.adaptTicker(quoineTicker, currencyPair);
  }

  @Override
  public List<Ticker> getTickers(Params params) throws IOException {
    if (!(params instanceof CurrencyPairsParam)) {
      throw new IllegalArgumentException("Params must be instance of CurrencyPairsParam");
    }

    CurrencyPairsParam pairs = (CurrencyPairsParam) params;
    QuoineProduct[] products = getQuoineProducts();

    return Arrays.stream(products)
        .filter(
            product ->
                pairs.getCurrencyPairs().stream()
                    .anyMatch(
                        pair ->
                            product.getBaseCurrency().equals(pair.base.getCurrencyCode())
                                && product
                                    .getQuotedCurrency()
                                    .equals(pair.counter.getCurrencyCode())))
        .map(product -> QuoineAdapters.adaptTicker(product, buildCurrencyPair(product)))
        .collect(Collectors.toList());
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {
    QuoineOrderBook quoineOrderBook = getOrderBook(productId(currencyPair));
    return QuoineAdapters.adaptOrderBook(quoineOrderBook, currencyPair);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {

    throw new NotAvailableFromExchangeException();
  }

  private static CurrencyPair buildCurrencyPair(QuoineProduct product) {
    return new CurrencyPair(product.getBaseCurrency(), product.getQuotedCurrency());
  }
}
