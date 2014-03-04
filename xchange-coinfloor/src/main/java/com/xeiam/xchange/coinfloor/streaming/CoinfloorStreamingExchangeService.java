/**
 * Copyright (C) 2012 - 2014 Xeiam LLC http://xeiam.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.xeiam.xchange.coinfloor.streaming;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.coinfloor.dto.streaming.CoinfloorExchangeEvent;
import com.xeiam.xchange.coinfloor.dto.streaming.CoinfloorStreamingConfiguration;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.MarketOrder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.service.streaming.BaseWebSocketExchangeService;
import com.xeiam.xchange.service.streaming.ExchangeEventType;
import com.xeiam.xchange.service.streaming.StreamingExchangeService;

/**
 * @author obsessiveOrange
 */
public class CoinfloorStreamingExchangeService extends BaseWebSocketExchangeService implements StreamingExchangeService {

  private final Logger logger = LoggerFactory.getLogger(CoinfloorStreamingExchangeService.class);

  private final CoinfloorStreamingConfiguration configuration;
  private final CoinfloorEventListener exchangeEventListener;
  
  ObjectMapper jsonObjectMapper;

  /**
   * @param exchangeSpecification
   * @param exchangeStreamingConfiguration
   */
  public CoinfloorStreamingExchangeService(ExchangeSpecification exchangeSpecification, CoinfloorStreamingConfiguration exchangeStreamingConfiguration) {

    super(exchangeSpecification, exchangeStreamingConfiguration);

    this.configuration = exchangeStreamingConfiguration;
    this.exchangeEventListener = new CoinfloorEventListener(consumerEventQueue);

    this.jsonObjectMapper = new ObjectMapper();

  }

  @Override
  public void connect() {

    String apiBase;
    if (configuration.isEncryptedChannel()) {
      apiBase = String.format("%s:%s", exchangeSpecification.getSslUriStreaming(), exchangeSpecification.getPort());
    }
    else {
      apiBase = String.format("%s:%s", exchangeSpecification.getPlainTextUriStreaming(), exchangeSpecification.getPort());
    }

    URI uri = URI.create(apiBase);

    Map<String, String> headers = new HashMap<String, String>(1);
    headers.put("Origin", String.format("%s:%s", exchangeSpecification.getHost(), exchangeSpecification.getPort()));

    logger.debug("Streaming URI='{}'", uri);

    // Use the default internal connect
    internalConnect(uri, exchangeEventListener, headers);
    
    if(configuration.getauthenticateOnConnect()){authenticate();}
  }

  public void authenticate(){
	if(exchangeSpecification.getUserName() == null || exchangeSpecification.getUserName() == null || exchangeSpecification.getUserName() == null){
		throw new ExchangeException("Username (UserID), Cookie, and Password cannot be null");
	}
	try{Long.valueOf(exchangeSpecification.getUserName());
	}catch(NumberFormatException e){throw new ExchangeException("Username (UserID) must be the string representation of a integer or long value.");}
	  
	CoinfloorExchangeEvent event;
	try {
		for(event = getNextEvent(); !event.getEventType().equals(ExchangeEventType.WELCOME); event = getNextEvent()){}
	} catch (InterruptedException e) {throw new ExchangeException("Interrupted while attempting to authenticate");}
	    	  
	RequestFactory.CoinfloorAuthenticationRequest authVars = new RequestFactory.CoinfloorAuthenticationRequest(
	    Long.valueOf(exchangeSpecification.getUserName()), 
	    exchangeSpecification.getCookie(), 
	    exchangeSpecification.getPassword(), 
	    (String) event.getPayloadItem("nonce"));
	
    try {
      send(jsonObjectMapper.writeValueAsString(authVars));
    } catch (JsonProcessingException e) {
      throw new ExchangeException("Cannot convert Object to String", e);
    }

  }

  private void doNewRequest(Object requestObject){
	try{
	  logger.trace("Sent message: " + jsonObjectMapper.writeValueAsString(requestObject));
      send(jsonObjectMapper.writeValueAsString(requestObject));
    }catch (JsonProcessingException e) {throw new ExchangeException("Cannot convert Object to String", e);}
  }
  
  /**
   * Get user's balances
   * 
   * Upon receipt of response, a CoinfloorExchangeEvent with payload Map<String, Object>, consisting of:
   * > A raw object of type CoinfloorBalances (key "raw")
   * > A generic object of type AccountInfo (key "generic")
   */
  public void getBalances() {
	doNewRequest(new RequestFactory.GetBalancesRequest());
  }

  /**
   * Get user's open orders
   * 
   * Upon receipt of response, a CoinfloorExchangeEvent with payload Map<String, Object>, consisting of:
   * > A raw object of type CoinfloorOpenOrders (key "raw")
   * > A generic object of type OpenOrders (key "generic")
   */
  public void getOrders() {
	doNewRequest(new RequestFactory.GetOrdersRequest());
  }

  /**
   * Place an order
   * 
   * Upon receipt of response, a CoinfloorExchangeEvent with payload Map<String, Object>, consisting of:
   * > A raw object of type CoinfloorPlaceOrder (key "raw")
   * > A generic object of type String, representing the orderID (key "generic")
   */
  public void placeOrder(Order order) {
	doNewRequest(new RequestFactory.PlaceOrderRequest(order));
  }

  /**
   * Cancel an order
   * 
   * Upon receipt of response, a CoinfloorExchangeEvent with payload Map<String, Object>, consisting of:
   * > A raw object of type CoinfloorCancelOrder (key "raw")
   * > A generic object of type LimitOrder, representing the cancelled order (key "generic")
   */
  public void cancelOrder(int orderID) {
	doNewRequest(new RequestFactory.CancelOrderRequest(orderID));
  }

  /**
   * Get past 30-day trade volume
   * 
   * Upon receipt of response, a CoinfloorExchangeEvent with payload Map<String, Object>, consisting of:
   * > A raw object of type CoinfloorTradeVolume (key "raw")
   * > A generic object of type BigDecimal, representing the past-30 day volume (key "generic")
   */
  public void getTradeVolume(String currency) {
	doNewRequest(new RequestFactory.GetTradeVolumeRequest(currency));
  }

  /**
   * Estimate the results of a market order
   * 
   * Upon receipt of response, a CoinfloorExchangeEvent with payload Map<String, Object>, consisting of:
   * > A raw object of type CoinfloorEstimateMarketOrder (key "raw")
   * 
   * Note that this method has no (useful) generic return. The "generic" key corresponds to the same item as the "raw" key
   */
  public void estimateMarketOrder(MarketOrder order) {
	doNewRequest(new RequestFactory.EstimateMarketOrderRequest(order));
  }

  /**
   * Watch the orderbook
   * 
   * Upon receipt of response, a CoinfloorExchangeEvent with payload Map<String, Object>, consisting of:
   * > A raw object of type CoinfloorOrderbookReturn (key "raw")
   * > A generic object of type Depth (key "generic")
   */
  public void watchOrders(String tradableIdentifier, String tradingCurrency) {
	doNewRequest(new RequestFactory.WatchOrdersRequest(tradableIdentifier, tradingCurrency));
  }

  /**
   * Stop watching the orderbook
   */
  public void unwatchOrders(String tradableIdentifier, String tradingCurrency) {
	doNewRequest(new RequestFactory.UnwatchOrdersRequest(tradableIdentifier, tradingCurrency));
  }

  /**
   * Watch the ticker feed
   * 
   * Upon receipt of response, a CoinfloorExchangeEvent with payload Map<String, Object>, consisting of:
   * > A raw object of type CoinfloorTicker (key "raw")
   * > A generic object of type Ticker (key "generic")
   */
  public void watchTicker(String tradableIdentifier, String tradingCurrency) {
	doNewRequest(new RequestFactory.WatchTickerRequest(tradableIdentifier, tradingCurrency));
  }

  /**
   * Stop watching the ticker feed
   */
  public void unwatchTicker(String tradableIdentifier, String tradingCurrency) {
	doNewRequest(new RequestFactory.UnwatchTickerRequest(tradableIdentifier, tradingCurrency));
  }
  
  /**
   * Retrieves cached AccountInfo.
   * @return the AccountInfo, as updated by last BalancesChanged event
   * @throws ExchangeException if getBalances() method has not been called, or data not recieved yet.
   */
  public AccountInfo getCachedAccountInfo() {
	  return exchangeEventListener.getAdapterInstance().getCachedAccountInfo();
  }
  
  /**
   * Retrieves cached OrderBook.
   * @return the OrderBook, as updated by last OrderOpened, OrdersMatched or OrderClosed event
   * @throws ExchangeException if watchOrders() method has not been called, or data not recieved yet.
   */
  public OrderBook getCachedOrderBook() {
	  return exchangeEventListener.getAdapterInstance().getCachedOrderBook();
  }
  
  /**
   * Retrieves cached Trades.
   * @return the Trades, as updated by last OrdersMatched event
   * @throws ExchangeException if watchOrders() method has not been called, or no trades have occurred yet.
   */
  public Trades getCachedTrades() {
	  return exchangeEventListener.getAdapterInstance().getCachedTrades();
  }
  
  @Override
  public List<CurrencyPair> getExchangeSymbols() {

    return null;
  }

  @Override
  public CoinfloorExchangeEvent getNextEvent() throws InterruptedException {
    return (CoinfloorExchangeEvent) super.getNextEvent();
  } 
}
