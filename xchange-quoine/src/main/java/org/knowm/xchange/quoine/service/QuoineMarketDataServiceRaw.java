package org.knowm.xchange.quoine.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.quoine.dto.marketdata.QuoineOrderBook;
import org.knowm.xchange.quoine.dto.marketdata.QuoineProduct;
import si.mazi.rescu.HttpStatusIOException;

public class QuoineMarketDataServiceRaw extends QuoineBaseService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public QuoineMarketDataServiceRaw(Exchange exchange) {

    super(exchange);
  }

  public QuoineProduct getQuoineProduct(String currencyPair) throws IOException {

    try {
      return quoine.getQuoineProduct(currencyPair);
    } catch (HttpStatusIOException e) {
      throw new ExchangeException(e.getHttpBody(), e);
    }
  }

  public QuoineProduct[] getQuoineProducts() throws IOException {
    try {
      return quoine.getQuoineProducts();
    } catch (HttpStatusIOException e) {
      throw new ExchangeException(e.getHttpBody(), e);
    }
  }

  public QuoineOrderBook getOrderBook(int id) throws IOException {

    try {
      return quoine.getOrderBook(id);
    } catch (HttpStatusIOException e) {
      throw new ExchangeException(e.getHttpBody(), e);
    }
  }
}
