package org.knowm.xchange.coinfloor.streaming;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.knowm.xchange.coinfloor.CoinfloorAdapters;
import org.knowm.xchange.coinfloor.CoinfloorUtils;
import org.knowm.xchange.coinfloor.dto.streaming.CoinfloorExchangeEvent;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.service.streaming.ExchangeEvent;
import org.knowm.xchange.service.streaming.ExchangeEventListener;
import org.knowm.xchange.service.streaming.ExchangeEventType;

/**
 * @author obsessiveOrange
 */
public class CoinfloorEventListener extends ExchangeEventListener {

  private static final Logger log = LoggerFactory.getLogger(CoinfloorEventListener.class);
  private final BlockingQueue<ExchangeEvent> systemEventQueue;
  private final BlockingQueue<ExchangeEvent> consumerEventQueue;
  private final ObjectMapper streamObjectMapper;
  private final CoinfloorAdapters coinfloorAdapters = new CoinfloorAdapters();

  private String serverNonce;

  /**
   * @param consumerEventQueue
   */
  public CoinfloorEventListener(BlockingQueue<ExchangeEvent> consumerEventQueue, BlockingQueue<ExchangeEvent> systemEventQueue) {

    this.consumerEventQueue = consumerEventQueue;
    this.systemEventQueue = systemEventQueue;
    this.streamObjectMapper = new ObjectMapper();
    this.streamObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
  }

  @Override
  public void handleEvent(ExchangeEvent event) throws ExchangeException {

    log.debug("Received event: " + event.getData());
    Map<String, Object> jsonData;
    try {
      jsonData = streamObjectMapper.readValue(event.getData(), new TypeReference<Map<String, Object>>() {
      });
    } catch (IOException e) {
      throw new ExchangeException("JSON parse error", e);
    }
    CoinfloorUtils.checkSuccess(jsonData);
    if (jsonData.containsKey("tag")) {
      switch (((Integer) jsonData.get("tag") & (1 << 10) - 1)) {
      case 1:
        CoinfloorExchangeEvent authenticationEvent = new CoinfloorExchangeEvent((Integer) jsonData.get("tag"), ExchangeEventType.AUTHENTICATION,
            event.getData(), jsonData);
        addToEventQueue(authenticationEvent);
        break;
      case 101:
        CoinfloorExchangeEvent getBalancesEvent = new CoinfloorExchangeEvent((Integer) jsonData.get("tag"), ExchangeEventType.USER_WALLET,
            event.getData(), coinfloorAdapters.adaptBalances(event.getData()));
        addToEventQueue(getBalancesEvent);
        break;
      case 102:
      case 103:
        CoinfloorExchangeEvent getTradeVolumeEvent = new CoinfloorExchangeEvent((Integer) jsonData.get("tag"), ExchangeEventType.USER_TRADE_VOLUME,
            event.getData(), coinfloorAdapters.adaptTradeVolume(event.getData()));
        addToEventQueue(getTradeVolumeEvent);
        break;
      case 201:
        CoinfloorExchangeEvent watchOrdersReturn = new CoinfloorExchangeEvent((Integer) jsonData.get("tag"), ExchangeEventType.SUBSCRIBE_ORDERS,
            event.getData(), coinfloorAdapters.adaptOrders(event.getData()));
        addToEventQueue(watchOrdersReturn);
        break;
      case 202:
        CoinfloorExchangeEvent watchTickerReturn = new CoinfloorExchangeEvent((Integer) jsonData.get("tag"), ExchangeEventType.SUBSCRIBE_TICKER,
            event.getData(), coinfloorAdapters.adaptTicker(event.getData()));
        addToEventQueue(watchTickerReturn);
        break;
      case 301:
        CoinfloorExchangeEvent getOpenOrdersReturn = new CoinfloorExchangeEvent((Integer) jsonData.get("tag"), ExchangeEventType.USER_ORDERS_LIST,
            event.getData(), coinfloorAdapters.adaptOpenOrders(event.getData()));
        addToEventQueue(getOpenOrdersReturn);
        break;
      case 302:
        CoinfloorExchangeEvent placeOrderReturn = new CoinfloorExchangeEvent((Integer) jsonData.get("tag"), ExchangeEventType.USER_ORDER,
            event.getData(), coinfloorAdapters.adaptPlaceOrder(event.getData()));
        addToEventQueue(placeOrderReturn);
        break;
      case 303:
        CoinfloorExchangeEvent cancelOrderReturn = new CoinfloorExchangeEvent((Integer) jsonData.get("tag"), ExchangeEventType.USER_ORDER_CANCELED,
            event.getData(), coinfloorAdapters.adaptCancelOrder(event.getData()));
        addToEventQueue(cancelOrderReturn);
        break;
      case 304:
        CoinfloorExchangeEvent estMarketOrderReturn = new CoinfloorExchangeEvent((Integer) jsonData.get("tag"),
            ExchangeEventType.USER_MARKET_ORDER_EST, event.getData(), coinfloorAdapters.adaptEstimateMarketOrder(event.getData()));
        addToEventQueue(estMarketOrderReturn);
        break;
      }
    } else if (jsonData.containsKey("notice")) {
      if (jsonData.get("notice").equals("Welcome")) {
        CoinfloorExchangeEvent welcomeEvent = new CoinfloorExchangeEvent(0, ExchangeEventType.WELCOME, event.getData(), jsonData);
        addToEventQueue(welcomeEvent);

        serverNonce = (String) jsonData.get("nonce");
      } else if (jsonData.get("notice").equals("BalanceChanged")) {
        CoinfloorExchangeEvent balancesChangedEvent = new CoinfloorExchangeEvent(0, ExchangeEventType.USER_WALLET_UPDATE, event.getData(),
            coinfloorAdapters.adaptBalancesChanged(event.getData()));
        addToEventQueue(balancesChangedEvent);
      } else if (jsonData.get("notice").equals("OrderOpened")) {
        CoinfloorExchangeEvent orderOpenedEvent = new CoinfloorExchangeEvent(0, ExchangeEventType.ORDER_ADDED, event.getData(),
            coinfloorAdapters.adaptOrderOpened(event.getData()));
        addToEventQueue(orderOpenedEvent);
      } else if (jsonData.get("notice").equals("OrdersMatched")) {
        CoinfloorExchangeEvent tradeEvent = new CoinfloorExchangeEvent(0, ExchangeEventType.TRADE, event.getData(),
            coinfloorAdapters.adaptOrdersMatched(event.getData()));
        addToEventQueue(tradeEvent);
      } else if (jsonData.get("notice").equals("OrderClosed")) {
        CoinfloorExchangeEvent orderClosedEvent = new CoinfloorExchangeEvent(0, ExchangeEventType.ORDER_CANCELED, event.getData(),
            coinfloorAdapters.adaptOrderClosed(event.getData()));
        addToEventQueue(orderClosedEvent);
      } else if (jsonData.get("notice").equals("TickerChanged")) {
        CoinfloorExchangeEvent tickerChangedEvent = new CoinfloorExchangeEvent(0, ExchangeEventType.TICKER, event.getData(),
            coinfloorAdapters.adaptTickerUpdate(event.getData()));
        addToEventQueue(tickerChangedEvent);
      }
    } else {
      log.warn("Exchange returned unexpected event: " + event.toString());
    }
  }

  public CoinfloorAdapters getAdapterInstance() {

    return coinfloorAdapters;
  }

  public String getServerNonce() {

    return serverNonce;
  }

  private void addToEventQueue(CoinfloorExchangeEvent event) {

    try {
      consumerEventQueue.put(event);
      systemEventQueue.put(event);
      synchronized (systemEventQueue) {
        systemEventQueue.notifyAll();
      }
    } catch (InterruptedException e) {
      throw new ExchangeException("InterruptedException!", e);
    }
  }
}
