package org.knowm.xchange.getbtc.service;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.getbtc.GetbtcAdapters;
import org.knowm.xchange.getbtc.dto.marketdata.GetbtcOrderbook;
import org.knowm.xchange.getbtc.dto.marketdata.GetbtcTicker;
import org.knowm.xchange.getbtc.dto.marketdata.GetbtcTickerResponse;
import org.knowm.xchange.getbtc.dto.marketdata.GetbtcTransaction;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.marketdata.params.Params;
/**
 * kevinobamatheus@gmail.com
 * @author kevingates
 *
 */
public class GetbtcMarketDataService extends GetbtcMarketDataServiceRaw implements MarketDataService {
  public GetbtcMarketDataService(Exchange exchange) {
    super(exchange);
  }

  public List<GetbtcTicker> ticketAll() {
	    return null;
  }
  
  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {
	GetbtcTickerResponse getbtcTicker = getGetbtcTicker(currencyPair);
    
    if (getbtcTicker != null && getbtcTicker.getTicker() != null) {
      return GetbtcAdapters.convertTicker(getbtcTicker);
    } else {
      return null;
    }
  }
  
  @Override
  public List<Ticker> getTickers(Params params) throws IOException {

	  return GetbtcAdapters.convertTickerMap(getGetbtcTickers());
  }  
  
  /**
   * getOrderBook
   */
  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {
	GetbtcOrderbook getbtcOrderbook = getGetbtcOrderBook(currencyPair);
	
    return getbtcOrderbook != null && getbtcOrderbook.getOrderBook() != null
        ? GetbtcAdapters.adaptOrderBook(getbtcOrderbook, currencyPair)
        : null;
  }
 
  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {
	  GetbtcTransaction[] getbtcTransactions = getTransactions(currencyPair);
 
    return GetbtcAdapters.adaptTrades(getbtcTransactions, currencyPair);
  }  
  
}
