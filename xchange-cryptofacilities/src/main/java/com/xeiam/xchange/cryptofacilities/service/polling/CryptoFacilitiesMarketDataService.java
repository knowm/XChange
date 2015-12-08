package com.xeiam.xchange.cryptofacilities.service.polling;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.cryptofacilities.CryptoFacilitiesAdapters;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.exceptions.NotAvailableFromExchangeException;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;

/**
 * @author Jean-Christophe Laruelle
 */

public class CryptoFacilitiesMarketDataService extends CryptoFacilitiesMarketDataServiceRaw implements PollingMarketDataService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public CryptoFacilitiesMarketDataService(Exchange exchange) {

    super(exchange);
  }
  
  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {

	  return CryptoFacilitiesAdapters.adaptTicker(getCryptoFacilitiesTicker(currencyPair), currencyPair);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {

	  return CryptoFacilitiesAdapters.adaptOrderBook(getCryptoFacilitiesCumulativeBidAsk(currencyPair));
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {

	  throw new NotAvailableFromExchangeException();
	  
  }  

}
