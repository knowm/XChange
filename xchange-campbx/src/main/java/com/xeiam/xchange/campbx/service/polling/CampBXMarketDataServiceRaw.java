package com.xeiam.xchange.campbx.service.polling;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.campbx.dto.marketdata.CampBXOrderBook;
import com.xeiam.xchange.campbx.dto.marketdata.CampBXTicker;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.exceptions.ExchangeException;
import com.xeiam.xchange.exceptions.NotAvailableFromExchangeException;

/**
 * @author Matija Mazi
 */
public class CampBXMarketDataServiceRaw extends CampBXBasePollingService {

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
      throw new ExchangeException("Error calling getCampBXFullOrderBook(): " + campBXOrderBook.getError());
    }
  }

  public Trades getCampBXTrades(CurrencyPair currencyPair, Object... args) throws IOException {

    throw new NotAvailableFromExchangeException();
  }

}
