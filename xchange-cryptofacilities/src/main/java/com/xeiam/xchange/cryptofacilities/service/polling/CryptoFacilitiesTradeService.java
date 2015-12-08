package com.xeiam.xchange.cryptofacilities.service.polling;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.cryptofacilities.CryptoFacilitiesAdapters;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.dto.trade.UserTrades;
import com.xeiam.xchange.exceptions.ExchangeException;
import com.xeiam.xchange.exceptions.NotAvailableFromExchangeException;
import com.xeiam.xchange.service.polling.trade.PollingTradeService;
import com.xeiam.xchange.service.polling.trade.params.TradeHistoryParams;

/**
 * @author Jean-Christophe Laruelle
 */

public class CryptoFacilitiesTradeService extends CryptoFacilitiesTradeServiceRaw implements PollingTradeService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public CryptoFacilitiesTradeService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {
	  
    return CryptoFacilitiesAdapters.adaptOpenOrders(super.getCryptoFacilitiesOpenOrders());
    
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {

	  throw new NotAvailableFromExchangeException();
	  
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {

    return CryptoFacilitiesAdapters.adaptOrderId(super.placeCryptoFacilitiesLimitOrder(limitOrder));
  }

  @Override
	public boolean cancelOrder(String orderId) throws IOException {

	  throw new ExchangeException("You must use the specific cancelCryptoFacilitiesOrder(String uid, CurrencyPair currencyPair) method in the CryptoFacilitiesTradeService class");
	  
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {

    return CryptoFacilitiesAdapters.adaptTrades(super.getCryptoFacilitiesTrades(100));
  }

  @Override
  public com.xeiam.xchange.service.polling.trade.params.TradeHistoryParams createTradeHistoryParams() {

	  throw new NotAvailableFromExchangeException();
  }

}
