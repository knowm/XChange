package org.knowm.xchange.coinfloor.streaming;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinfloor.dto.streaming.CoinfloorExchangeEvent;
import org.knowm.xchange.coinfloor.dto.streaming.CoinfloorStreamingConfiguration;
import org.knowm.xchange.coinfloor.streaming.RequestFactory.CoinfloorRequest;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.service.streaming.BaseWebSocketExchangeService;
import org.knowm.xchange.service.streaming.ExchangeEvent;
import org.knowm.xchange.service.streaming.ExchangeEventType;
import org.knowm.xchange.service.streaming.StreamingExchangeService;

/**
 * @author obsessiveOrange
 */
public class CoinfloorStreamingExchangeService extends BaseWebSocketExchangeService implements StreamingExchangeService {

  private final Logger logger = LoggerFactory.getLogger(CoinfloorStreamingExchangeService.class);

  private final CoinfloorStreamingConfiguration configuration;
  private final CoinfloorEventListener exchangeEventListener;
  private final BlockingQueue<ExchangeEvent> systemEventQueue = new LinkedBlockingQueue<ExchangeEvent>();
  private final BlockingQueue<CoinfloorExchangeEvent> updateEventQueue = new LinkedBlockingQueue<CoinfloorExchangeEvent>();

  ObjectMapper jsonObjectMapper;

  /**
   * Constructor
   *
   * @param exchange
   * @param exchangeStreamingConfiguration
   */
  public CoinfloorStreamingExchangeService(Exchange exchange, CoinfloorStreamingConfiguration exchangeStreamingConfiguration) {

    super(exchange, exchangeStreamingConfiguration);

    this.configuration = exchangeStreamingConfiguration;
    this.exchangeEventListener = new CoinfloorEventListener(consumerEventQueue, systemEventQueue);

    this.jsonObjectMapper = new ObjectMapper();

  }

  @Override
  public void connect() {

    String apiBase;
    if (configuration.isEncryptedChannel()) {
      apiBase = String.format("%s:%s", exchange.getExchangeSpecification().getSslUriStreaming(), exchange.getExchangeSpecification().getPort());
    } else {
      apiBase = String.format("%s:%s", exchange.getExchangeSpecification().getPlainTextUriStreaming(), exchange.getExchangeSpecification().getPort());
    }

    URI uri = URI.create(apiBase);

    Map<String, String> headers = new HashMap<String, String>(1);
    headers.put("Origin", String.format("%s:%s", exchange.getExchangeSpecification().getHost(), exchange.getExchangeSpecification().getPort()));

    logger.debug("Streaming URI='{}'", uri);

    // Use the default internal connect
    internalConnect(uri, exchangeEventListener, headers);

    try {
      if (getNextSystemEvent().getEventType() != ExchangeEventType.WELCOME) {
        throw new ExchangeException("Could not connect.");
      }
    } catch (InterruptedException e) {
      throw new ExchangeException("Could not connect.");
    }

    if (configuration.getauthenticateOnConnect()) {
      authenticate();
    }
  }

  public void authenticate() {

    if (exchange.getExchangeSpecification().getUserName() == null || exchange.getExchangeSpecification().getUserName() == null
        || exchange.getExchangeSpecification().getUserName() == null) {
      throw new ExchangeException("Username (UserID), Cookie, and Password cannot be null");
    }
    try {
      Long.valueOf(exchange.getExchangeSpecification().getUserName());
    } catch (NumberFormatException e) {
      throw new ExchangeException("Username (UserID) must be the string representation of a integer or long value.");
    }

    RequestFactory.CoinfloorAuthenticationRequest authVars = new RequestFactory.CoinfloorAuthenticationRequest(
        Long.valueOf(exchange.getExchangeSpecification().getUserName()),
        (String) exchange.getExchangeSpecification().getExchangeSpecificParametersItem("cookie"), exchange.getExchangeSpecification().getPassword(),
        exchangeEventListener.getServerNonce());

    doNewRequest(authVars, ExchangeEventType.AUTHENTICATION);

  }

  @SuppressWarnings("incomplete-switch")
  private CoinfloorExchangeEvent doNewRequest(CoinfloorRequest requestObject, ExchangeEventType expectedEventType) {

    try {
      try {
        logger.trace("Sent message: " + jsonObjectMapper.writeValueAsString(requestObject));
        send(jsonObjectMapper.writeValueAsString(requestObject));
      } catch (JsonProcessingException e) {
        throw new ExchangeException("Cannot convert Object to String", e);
      }

      CoinfloorExchangeEvent nextEvent = checkNextSystemEvent();
      while (!nextEvent.getEventType().equals(expectedEventType) || nextEvent.getTag() != requestObject.getTag()) {
        switch (nextEvent.getEventType()) {
        case USER_WALLET_UPDATE:
        case ORDER_ADDED:
        case TRADE:
        case ORDER_CANCELED:
        case TICKER:
        case AUTHENTICATION:
        case WELCOME:
          updateEventQueue.put(getNextSystemEvent());
          break;

        }
        synchronized (this) {
          nextEvent = checkNextSystemEvent();
        }
      }
      return getNextSystemEvent();
    } catch (Exception e) {
      throw new ExchangeException("Error processing request", e);
    }
  }

  /**
   * Get user's balances Upon receipt of response, a CoinfloorExchangeEvent with payload Map<String, Object>, consisting of: > A raw object of type
   * CoinfloorBalances (key "raw") > A generic object of type Wallet (key "generic")
   */
  public CoinfloorExchangeEvent getBalances() {

    return doNewRequest(new RequestFactory.GetBalancesRequest(), ExchangeEventType.USER_WALLET);
  }

  /**
   * Get user's open orders Upon receipt of response, a CoinfloorExchangeEvent with payload Map<String, Object>, consisting of: > A raw object of type
   * CoinfloorOpenOrders (key "raw") > A generic object of type OpenOrders (key "generic")
   */
  public CoinfloorExchangeEvent getOrders() {

    return doNewRequest(new RequestFactory.GetOrdersRequest(), ExchangeEventType.USER_ORDERS_LIST);
  }

  /**
   * Place an order Upon receipt of response, a CoinfloorExchangeEvent with payload Map<String, Object>, consisting of: > A raw object of type
   * CoinfloorPlaceOrder (key "raw") > A generic object of type String, representing the orderID (key "generic")
   */
  public CoinfloorExchangeEvent placeOrder(Order order) {

    return doNewRequest(new RequestFactory.PlaceOrderRequest(order), ExchangeEventType.USER_ORDER);
  }

  /**
   * Cancel an order Upon receipt of response, a CoinfloorExchangeEvent with payload Map<String, Object>, consisting of: > A raw object of type
   * CoinfloorCancelOrder (key "raw") > A generic object of type LimitOrder, representing the cancelled order (key "generic")
   */
  public CoinfloorExchangeEvent cancelOrder(int orderID) {

    return doNewRequest(new RequestFactory.CancelOrderRequest(orderID), ExchangeEventType.USER_ORDER_CANCELED);
  }

  /**
   * Get past 30-day trade volume Upon receipt of response, a CoinfloorExchangeEvent with payload Map<String, Object>, consisting of: > A raw object
   * of type CoinfloorTradeVolume (key "raw") > A generic object of type BigDecimal, representing the past-30 day volume (key "generic")
   */
  public CoinfloorExchangeEvent getTradeVolume(String currency) {

    return doNewRequest(new RequestFactory.GetTradeVolumeRequest(currency), ExchangeEventType.USER_TRADE_VOLUME);
  }

  /**
   * Estimate the results of a market order Upon receipt of response, a CoinfloorExchangeEvent with payload Map<String, Object>, consisting of: > A
   * raw object of type CoinfloorEstimateMarketOrder (key "raw") Note that this method has no (useful) generic return. The "generic" key corresponds
   * to the same item as the "raw" key
   */
  public CoinfloorExchangeEvent estimateMarketOrder(MarketOrder order) {

    return doNewRequest(new RequestFactory.EstimateMarketOrderRequest(order), ExchangeEventType.USER_MARKET_ORDER_EST);
  }

  /**
   * Watch the orderbook Upon receipt of response, a CoinfloorExchangeEvent with payload Map<String, Object>, consisting of: > A raw object of type
   * CoinfloorOrderbookReturn (key "raw") > A generic object of type Depth (key "generic")
   */
  public CoinfloorExchangeEvent watchOrders(String tradableIdentifier, String tradingCurrency) {

    return doNewRequest(new RequestFactory.WatchOrdersRequest(tradableIdentifier, tradingCurrency), ExchangeEventType.SUBSCRIBE_ORDERS);
  }

  /**
   * Stop watching the orderbook Upon receipt of response, a CoinfloorExchangeEvent with payload Map<String, Object>, consisting of: > A raw object of
   * type CoinfloorOrderbookReturn (key "raw") > A generic object of type Depth (key "generic")
   */
  public CoinfloorExchangeEvent unwatchOrders(String tradableIdentifier, String tradingCurrency) {

    return doNewRequest(new RequestFactory.UnwatchOrdersRequest(tradableIdentifier, tradingCurrency), ExchangeEventType.SUBSCRIBE_ORDERS);
  }

  /**
   * Watch the ticker feed Upon receipt of response, a CoinfloorExchangeEvent with payload Map<String, Object>, consisting of: > A raw object of type
   * CoinfloorTicker (key "raw") > A generic object of type Ticker (key "generic")
   */
  public CoinfloorExchangeEvent watchTicker(String tradableIdentifier, String tradingCurrency) {

    return doNewRequest(new RequestFactory.WatchTickerRequest(tradableIdentifier, tradingCurrency), ExchangeEventType.SUBSCRIBE_TICKER);
  }

  /**
   * Stop watching the ticker feed Upon receipt of response, a CoinfloorExchangeEvent with payload Map<String, Object>, consisting of: > A raw object
   * of type CoinfloorTicker (key "raw") > A generic object of type Ticker (key "generic")
   */
  public CoinfloorExchangeEvent unwatchTicker(String tradableIdentifier, String tradingCurrency) {

    return doNewRequest(new RequestFactory.UnwatchTickerRequest(tradableIdentifier, tradingCurrency), ExchangeEventType.SUBSCRIBE_TICKER);
  }

  /**
   * Retrieves cached Wallet. WARNING: EXPERIMENTAL METHOD
   *
   * @return the Wallet, as updated by last BalancesChanged event
   * @throws ExchangeException if getBalances() method has not been called, or data not recieved yet.
   */
  public Wallet getCachedAccountInfo() {

    return exchangeEventListener.getAdapterInstance().getCachedWallet();
  }

  /**
   * Retrieves cached OrderBook. WARNING: EXPERIMENTAL METHOD
   *
   * @return the OrderBook, as updated by last OrderOpened, OrdersMatched or OrderClosed event
   * @throws ExchangeException if watchOrders() method has not been called, or data not recieved yet.
   */
  public OrderBook getCachedOrderBook() {

    return exchangeEventListener.getAdapterInstance().getCachedOrderBook();
  }

  /**
   * Retrieves cached Trades. WARNING: EXPERIMENTAL METHOD
   *
   * @return the Trades, as updated by last OrdersMatched event
   * @throws ExchangeException if watchOrders() method has not been called, or no trades have occurred yet.
   */
  public Trades getCachedTrades() {

    return exchangeEventListener.getAdapterInstance().getCachedTrades();
  }

  /**
   * Retrieves cached Ticker. WARNING: EXPERIMENTAL METHOD
   *
   * @return the Ticker, as updated by last TickerChanged event
   * @throws ExchangeException if watchTicker() method has not been called, or no ticker data has been recieved.
   */
  public Ticker getCachedTicker() {

    return exchangeEventListener.getAdapterInstance().getCachedTicker();
  }

  @Override
  public CoinfloorExchangeEvent getNextEvent() throws InterruptedException {

    return (CoinfloorExchangeEvent) super.getNextEvent();
  }

  public CoinfloorExchangeEvent getNextSystemEvent() throws InterruptedException {

    CoinfloorExchangeEvent event = (CoinfloorExchangeEvent) systemEventQueue.take();
    return event;
  }

  public CoinfloorExchangeEvent checkNextSystemEvent() throws InterruptedException {

    while (true) {
      synchronized (systemEventQueue) {
        if (systemEventQueue.isEmpty()) {
          systemEventQueue.wait();
        }
      }
      CoinfloorExchangeEvent event = (CoinfloorExchangeEvent) systemEventQueue.peek();
      if (event == null) {
        continue;
      }
      return event;
    }
  }
}
