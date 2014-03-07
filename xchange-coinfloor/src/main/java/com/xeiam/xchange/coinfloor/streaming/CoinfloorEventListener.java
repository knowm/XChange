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

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.coinfloor.CoinfloorAdapters;
import com.xeiam.xchange.coinfloor.CoinfloorUtils;
import com.xeiam.xchange.coinfloor.dto.streaming.CoinfloorExchangeEvent;
import com.xeiam.xchange.service.streaming.ExchangeEvent;
import com.xeiam.xchange.service.streaming.ExchangeEventListener;
import com.xeiam.xchange.service.streaming.ExchangeEventType;

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
        CoinfloorExchangeEvent authenticationEvent = new CoinfloorExchangeEvent((Integer) jsonData.get("tag"), ExchangeEventType.AUTHENTICATION, event.getData(), jsonData);
        addToEventQueue(authenticationEvent);
        break;
      case 101:
        CoinfloorExchangeEvent getBalancesEvent =
            new CoinfloorExchangeEvent((Integer) jsonData.get("tag"), ExchangeEventType.USER_WALLET, event.getData(), coinfloorAdapters.adaptBalances(event.getData()));
        addToEventQueue(getBalancesEvent);
        break;
      case 102:
      case 103:
        CoinfloorExchangeEvent getTradeVolumeEvent =
            new CoinfloorExchangeEvent((Integer) jsonData.get("tag"), ExchangeEventType.USER_TRADE_VOLUME, event.getData(), coinfloorAdapters.adaptTradeVolume(event.getData()));
        addToEventQueue(getTradeVolumeEvent);
        break;
      case 201:
        CoinfloorExchangeEvent watchOrdersReturn =
            new CoinfloorExchangeEvent((Integer) jsonData.get("tag"), ExchangeEventType.SUBSCRIBE_ORDERS, event.getData(), coinfloorAdapters.adaptOrders(event.getData()));
        addToEventQueue(watchOrdersReturn);
        break;
      case 202:
        CoinfloorExchangeEvent watchTickerReturn =
            new CoinfloorExchangeEvent((Integer) jsonData.get("tag"), ExchangeEventType.SUBSCRIBE_TICKER, event.getData(), coinfloorAdapters.adaptTicker(event.getData()));
        addToEventQueue(watchTickerReturn);
        break;
      case 301:
        CoinfloorExchangeEvent getOpenOrdersReturn =
            new CoinfloorExchangeEvent((Integer) jsonData.get("tag"), ExchangeEventType.USER_ORDERS_LIST, event.getData(), coinfloorAdapters.adaptOpenOrders(event.getData()));
        addToEventQueue(getOpenOrdersReturn);
        break;
      case 302:
        CoinfloorExchangeEvent placeOrderReturn =
            new CoinfloorExchangeEvent((Integer) jsonData.get("tag"), ExchangeEventType.USER_ORDER, event.getData(), coinfloorAdapters.adaptPlaceOrder(event.getData()));
        addToEventQueue(placeOrderReturn);
        break;
      case 303:
        CoinfloorExchangeEvent cancelOrderReturn =
            new CoinfloorExchangeEvent((Integer) jsonData.get("tag"), ExchangeEventType.USER_ORDER_CANCELED, event.getData(), coinfloorAdapters.adaptCancelOrder(event.getData()));
        addToEventQueue(cancelOrderReturn);
        break;
      case 304:
        CoinfloorExchangeEvent estMarketOrderReturn =
            new CoinfloorExchangeEvent((Integer) jsonData.get("tag"), ExchangeEventType.USER_MARKET_ORDER_EST, event.getData(), coinfloorAdapters.adaptEstimateMarketOrder(event.getData()));
        addToEventQueue(estMarketOrderReturn);
        break;
      }
    }
    else if (jsonData.containsKey("notice")) {
      if (jsonData.get("notice").equals("Welcome")) {
        CoinfloorExchangeEvent welcomeEvent = new CoinfloorExchangeEvent(0, ExchangeEventType.WELCOME, event.getData(), jsonData);
        addToEventQueue(welcomeEvent);

        serverNonce = (String) jsonData.get("nonce");
      }
      else if (jsonData.get("notice").equals("BalanceChanged")) {
        CoinfloorExchangeEvent balancesChangedEvent = new CoinfloorExchangeEvent(0, ExchangeEventType.USER_WALLET_UPDATE, event.getData(), coinfloorAdapters.adaptBalancesChanged(event.getData()));
        addToEventQueue(balancesChangedEvent);
      }
      else if (jsonData.get("notice").equals("OrderOpened")) {
        CoinfloorExchangeEvent orderOpenedEvent = new CoinfloorExchangeEvent(0, ExchangeEventType.ORDER_ADDED, event.getData(), coinfloorAdapters.adaptOrderOpened(event.getData()));
        addToEventQueue(orderOpenedEvent);
      }
      else if (jsonData.get("notice").equals("OrdersMatched")) {
        CoinfloorExchangeEvent tradeEvent = new CoinfloorExchangeEvent(0, ExchangeEventType.TRADE, event.getData(), coinfloorAdapters.adaptOrdersMatched(event.getData()));
        addToEventQueue(tradeEvent);
      }
      else if (jsonData.get("notice").equals("OrderClosed")) {
        CoinfloorExchangeEvent orderClosedEvent = new CoinfloorExchangeEvent(0, ExchangeEventType.ORDER_CANCELED, event.getData(), coinfloorAdapters.adaptOrderClosed(event.getData()));
        addToEventQueue(orderClosedEvent);
      }
      else if (jsonData.get("notice").equals("TickerChanged")) {
        CoinfloorExchangeEvent tickerChangedEvent = new CoinfloorExchangeEvent(0, ExchangeEventType.TICKER, event.getData(), coinfloorAdapters.adaptTickerUpdate(event.getData()));
        addToEventQueue(tickerChangedEvent);
      }
    }
    else {
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
