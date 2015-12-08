package com.xeiam.xchange.cryptofacilities.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.cryptofacilities.dto.marketdata.CryptoFacilitiesCancel;
import com.xeiam.xchange.cryptofacilities.dto.marketdata.CryptoFacilitiesOpenOrders;
import com.xeiam.xchange.cryptofacilities.dto.marketdata.CryptoFacilitiesOrder;
import com.xeiam.xchange.cryptofacilities.dto.marketdata.CryptoFacilitiesTrades;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.trade.LimitOrder;

/**
 * @author Jean-Christophe Laruelle
 */

public class CryptoFacilitiesTradeServiceRaw extends CryptoFacilitiesBasePollingService {

  /**
   * Constructor
   *
   * @param exchange
   * @param nonceFactory
   */
  public CryptoFacilitiesTradeServiceRaw(Exchange exchange) {

    super(exchange);
  }

  public CryptoFacilitiesOrder placeCryptoFacilitiesLimitOrder(LimitOrder order) throws IOException
  {
	  String type = "LMT";
	  String tradeable = order.getCurrencyPair().base.toString();
	  String unit = order.getCurrencyPair().counter.toString();
	  String dir = "Buy";
	  if(order.getType().equals(OrderType.ASK))
		dir = "Sell";
	  BigDecimal qty = order.getTradableAmount();
	  BigDecimal price = order.getLimitPrice();
	  
	  CryptoFacilitiesOrder ord = cryptoFacilities.placeOrder(exchange.getExchangeSpecification().getApiKey(), signatureCreator, exchange.getNonceFactory(), type, tradeable, unit, dir, qty, price);
	  
	  return ord;
  }
  
  public CryptoFacilitiesCancel cancelCryptoFacilitiesOrder(String uid, CurrencyPair currencyPair) throws IOException
  {	  
	  CryptoFacilitiesCancel res = cryptoFacilities.cancelOrder(exchange.getExchangeSpecification().getApiKey(), signatureCreator, exchange.getNonceFactory(), uid, currencyPair.base.toString(), currencyPair.counter.toString());
	  
	  return res;
  }
  
  public CryptoFacilitiesOpenOrders getCryptoFacilitiesOpenOrders() throws IOException
  {
	  CryptoFacilitiesOpenOrders openOrders = null;
	try {
		openOrders = cryptoFacilities.openOrders(exchange.getExchangeSpecification().getApiKey(), signatureCreator, exchange.getNonceFactory());
	} catch (Exception e) {
		return null;
	}
	  
	  return openOrders;
  }

  public CryptoFacilitiesTrades getCryptoFacilitiesTrades(int number) throws IOException
  {
	  CryptoFacilitiesTrades trades = null;
	  
	  try {
		trades = cryptoFacilities.trades(exchange.getExchangeSpecification().getApiKey(), signatureCreator, exchange.getNonceFactory(), number);
	} catch (Exception e) {
		return null;
	}
	  
	  return trades;
  }

  
}
