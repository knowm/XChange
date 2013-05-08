/**
 * Copyright (C) 2013 Xeiam LLC http://xeiam.com
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
package com.xeiam.xchange.mtgox.v2.service.trade.streaming;

import java.util.Map;
import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import si.mazi.rescu.JSONUtils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.dto.marketdata.OrderBookUpdate;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trade;
import com.xeiam.xchange.mtgox.v1.MtGoxAdapters;
import com.xeiam.xchange.mtgox.v1.dto.marketdata.MtGoxDepthUpdate;
import com.xeiam.xchange.mtgox.v1.dto.marketdata.MtGoxTicker;
import com.xeiam.xchange.mtgox.v1.dto.marketdata.MtGoxTrade;
import com.xeiam.xchange.mtgox.v2.dto.account.streaming.MtGoxAccountInfo;
import com.xeiam.xchange.mtgox.v2.dto.account.streaming.MtGoxWalletUpdate;
import com.xeiam.xchange.mtgox.v2.dto.trade.streaming.MtGoxOpenOrder;
import com.xeiam.xchange.mtgox.v2.dto.trade.streaming.MtGoxOrderCanceled;
import com.xeiam.xchange.mtgox.v2.dto.trade.streaming.MtGoxTradeLag;
import com.xeiam.xchange.service.streaming.DefaultExchangeEvent;
import com.xeiam.xchange.service.streaming.ExchangeEvent;
import com.xeiam.xchange.service.streaming.ExchangeEventListener;
import com.xeiam.xchange.service.streaming.ExchangeEventType;

/**
 * @author timmolter
 */
public class MtGoxExchangeEventListener extends ExchangeEventListener {

  private static final Logger log = LoggerFactory.getLogger(MtGoxExchangeEventListener.class);

  private final ObjectMapper streamObjectMapper;

  private final BlockingQueue<ExchangeEvent> consumerEventQueue;

  /**
   * Constructor
   * 
   * @param consumerEventQueue
   */
  public MtGoxExchangeEventListener(BlockingQueue<ExchangeEvent> consumerEventQueue) {

    this.consumerEventQueue = consumerEventQueue;
    streamObjectMapper = new ObjectMapper();
    streamObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

  }

  @Override
  public void handleEvent(ExchangeEvent exchangeEvent) {

    // System.out.println("Msg from server: " + exchangeEvent.getEventType());
    // System.out.println("Msg from server: " + exchangeEvent.getData());

    switch (exchangeEvent.getEventType()) {
    case CONNECT:
      log.debug("MtGox connected");
      addToEventQueue(exchangeEvent);
      break;
    case DISCONNECT:
      log.debug("MtGox disconnected");
      addToEventQueue(exchangeEvent);
      break;
    case MESSAGE:

      // Get raw JSON
      Map<String, Object> rawJSON = JSONUtils.getJsonGenericMap(exchangeEvent.getData(), streamObjectMapper);
      String operation = (String) rawJSON.get("op");
      if ("private".equals(operation)) {
        String priv = (String) rawJSON.get("private");
        if ("user_order".equals(priv)) {
          MtGoxOpenOrder order = JSONUtils.getJsonObject(JSONUtils.getJSONString(rawJSON.get("user_order"), streamObjectMapper), MtGoxOpenOrder.class, streamObjectMapper);
          ExchangeEvent userOrderEvent = new DefaultExchangeEvent(ExchangeEventType.USER_ORDER, exchangeEvent.getData(), order);
          addToEventQueue(userOrderEvent);
          break;
        } else if ("lag".equals(priv)) {
          MtGoxTradeLag lag = JSONUtils.getJsonObject(JSONUtils.getJSONString(rawJSON.get("lag"), streamObjectMapper), MtGoxTradeLag.class, streamObjectMapper);
          ExchangeEvent lagEvent = new DefaultExchangeEvent(ExchangeEventType.TRADE_LAG, exchangeEvent.getData(), lag);
          addToEventQueue(lagEvent);
          break;
        } else if ("wallet".equals(priv)) {
          MtGoxWalletUpdate walletUpdate = JSONUtils.getJsonObject(JSONUtils.getJSONString(rawJSON.get("wallet"), streamObjectMapper), MtGoxWalletUpdate.class, streamObjectMapper);
          ExchangeEvent walletUpdateEvent = new DefaultExchangeEvent(ExchangeEventType.USER_WALLET_UPDATE, exchangeEvent.getData(), walletUpdate);
          addToEventQueue(walletUpdateEvent);
          break;
        }

      } else if ("result".equals(operation)) {
        String id = (String) rawJSON.get("id");

        if ("idkey".equals(id)) {
          ExchangeEvent idEvent = new DefaultExchangeEvent(ExchangeEventType.PRIVATE_ID_KEY, null, rawJSON.get("result"));
          addToEventQueue(idEvent);
          break;
        } else if ("orders".equals(id)) {
          MtGoxOpenOrder[] orders = null;

          if (rawJSON.get("result") != null) {
            orders = JSONUtils.getJsonObject(JSONUtils.getJSONString(rawJSON.get("result"), streamObjectMapper), MtGoxOpenOrder[].class, streamObjectMapper);
          }

          ExchangeEvent ordersEvent = new DefaultExchangeEvent(ExchangeEventType.USER_ORDERS_LIST, exchangeEvent.getData(), orders);
          addToEventQueue(ordersEvent);
          break;

        } else if ("info".equals(id)) {
          MtGoxAccountInfo accountInfo = JSONUtils.getJsonObject(JSONUtils.getJSONString(rawJSON.get("result"), streamObjectMapper), MtGoxAccountInfo.class, streamObjectMapper);
          ExchangeEvent accountInfoEvent = new DefaultExchangeEvent(ExchangeEventType.ACCOUNT_INFO, exchangeEvent.getData(), accountInfo);
          addToEventQueue(accountInfoEvent);
          break;

        } else if (id.startsWith("order_add")) {
          ExchangeEvent userOrderAddedEvent = new DefaultExchangeEvent(ExchangeEventType.USER_ORDER_ADDED, exchangeEvent.getData(), rawJSON.get("result"));
          addToEventQueue(userOrderAddedEvent);
          break;

        } else if (id.startsWith("order_cancel")) {
          MtGoxOrderCanceled orderCanceled = JSONUtils.getJsonObject(JSONUtils.getJSONString(rawJSON.get("result"), streamObjectMapper), MtGoxOrderCanceled.class, streamObjectMapper);
          ExchangeEvent userOrderCanceledEvent = new DefaultExchangeEvent(ExchangeEventType.USER_ORDER_CANCELED, exchangeEvent.getData(), orderCanceled);
          addToEventQueue(userOrderCanceledEvent);
          break;
        }

      } else if ("remark".equals(operation)) {
        System.out.println("Msg from server: " + rawJSON.toString());
        break;
      }

      // Determine what has been sent
      if (rawJSON.containsKey("ticker")) {

        // Get MtGoxTicker from JSON String
        MtGoxTicker mtGoxTicker = JSONUtils.getJsonObject(JSONUtils.getJSONString(rawJSON.get("ticker"), streamObjectMapper), MtGoxTicker.class, streamObjectMapper);

        // Adapt to XChange DTOs
        Ticker ticker = MtGoxAdapters.adaptTicker(mtGoxTicker);

        // Create a ticker event
        ExchangeEvent tickerEvent = new DefaultExchangeEvent(ExchangeEventType.TICKER, exchangeEvent.getData(), ticker);
        addToEventQueue(tickerEvent);
        break;
      } else {
        if (rawJSON.containsKey("trade")) {

          // log.debug("exchangeEvent: " + exchangeEvent.getEventType());

          // Get MtGoxTradeStream from JSON String
          MtGoxTrade mtGoxTradeStream = JSONUtils.getJsonObject(JSONUtils.getJSONString(rawJSON.get("trade"), streamObjectMapper), MtGoxTrade.class, streamObjectMapper);

          // Adapt to XChange DTOs
          Trade trade = MtGoxAdapters.adaptTrade(mtGoxTradeStream);

          // Create a trade event
          ExchangeEvent tradeEvent = new DefaultExchangeEvent(ExchangeEventType.TRADE, exchangeEvent.getData(), trade);
          addToEventQueue(tradeEvent);
          break;
        } else {
          if (rawJSON.containsKey("depth")) {

            // Get MtGoxDepthStream from JSON String
            MtGoxDepthUpdate mtGoxDepthStream = JSONUtils.getJsonObject(JSONUtils.getJSONString(rawJSON.get("depth"), streamObjectMapper), MtGoxDepthUpdate.class, streamObjectMapper);

            // Adapt to XChange DTOs
            OrderBookUpdate orderBookUpdate = MtGoxAdapters.adaptDepthUpdate(mtGoxDepthStream);

            // Create a depth event
            ExchangeEvent depthEvent = new DefaultExchangeEvent(ExchangeEventType.DEPTH, exchangeEvent.getData(), orderBookUpdate);
            addToEventQueue(depthEvent);
            break;
          } else {

            log.debug("MtGox operational message");
            System.out.println("msg: " + rawJSON.toString());
            addToEventQueue(exchangeEvent);
          }
        }
      }
      break;
    case ERROR:
      log.error("Error message: " + exchangeEvent.getPayload());
      addToEventQueue(exchangeEvent);
      break;
    default:
      throw new IllegalStateException("Unknown ExchangeEventType " + exchangeEvent.getEventType().name());
    }

  }

  private void addToEventQueue(ExchangeEvent event) {

    try {
      consumerEventQueue.put(event);
    } catch (InterruptedException e) {
      throw new ExchangeException("InterruptedException!", e);
    }
  }

}
