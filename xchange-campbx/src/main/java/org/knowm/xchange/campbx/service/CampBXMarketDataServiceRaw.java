package org.knowm.xchange.campbx.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.campbx.dto.marketdata.CampBXOrderBook;
import org.knowm.xchange.campbx.dto.marketdata.CampBXTicker;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;

/** @author Matija Mazi */
public class CampBXMarketDataServiceRaw extends CampBXBaseService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public CampBXMarketDataServiceRaw(Exchange exchange) {

    super(exchange);
  }

  public CampBXTicker getCampBXTicker() throws IOException {

    CampBXTicker campbxTicker = campBX.getTicker();

    if (!campbxTicker.isError()) {
      return campbxTicker;
    } else {
      throw new ExchangeException("Error calling getCampBXTicker(): " + campbxTicker.getError());
    }
  }

  public CampBXOrderBook getCampBXOrderBook() throws IOException {

    CampBXOrderBook campBXOrderBook = campBX.getOrderBook();

    if (!campBXOrderBook.isError()) {
      return campBXOrderBook;
    } else {
      throw new ExchangeException(
          "Error calling getCampBXFullOrderBook(): " + campBXOrderBook.getError());
    }
  }

  public Trades getCampBXTrades(CurrencyPair currencyPair, Object... args) throws IOException {

    throw new NotAvailableFromExchangeException();
  }
}
