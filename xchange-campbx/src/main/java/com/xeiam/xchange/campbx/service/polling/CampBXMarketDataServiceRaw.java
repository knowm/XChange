package com.xeiam.xchange.campbx.service.polling;

import java.io.IOException;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.campbx.CampBX;
import com.xeiam.xchange.campbx.dto.marketdata.CampBXOrderBook;
import com.xeiam.xchange.campbx.dto.marketdata.CampBXTicker;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.Trades;

/**
 * @author Matija Mazi
 */
public class CampBXMarketDataServiceRaw extends CampBXBasePollingService {

  private final CampBX campBX;

  /**
   * Constructor
   * 
   * @param exchangeSpecification The {@link ExchangeSpecification}
   */
  public CampBXMarketDataServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
    this.campBX = RestProxyFactory.createProxy(CampBX.class, exchangeSpecification.getSslUri());
  }

  public CampBXTicker getCampBXTicker() throws IOException {

    CampBXTicker campbxTicker = campBX.getTicker();

    if (!campbxTicker.isError()) {
      return campbxTicker;
    }
    else {
      throw new ExchangeException("Error calling getCampBXTicker(): " + campbxTicker.getError());
    }
  }

  public CampBXOrderBook getCampBXOrderBook() throws IOException {

    CampBXOrderBook campBXOrderBook = campBX.getOrderBook();

    if (!campBXOrderBook.isError()) {
      return campBXOrderBook;
    }
    else {
      throw new ExchangeException("Error calling getCampBXFullOrderBook(): " + campBXOrderBook.getError());
    }
  }

  public Trades getCampBXTrades(CurrencyPair currencyPair, Object... args) throws IOException {

    throw new NotAvailableFromExchangeException();
  }

}
