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
package com.xeiam.xchange.mtgox.v2.service.streaming;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.dto.marketdata.OrderBookUpdate;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trade;
import com.xeiam.xchange.mtgox.v2.MtGoxAdapters;
import com.xeiam.xchange.mtgox.v2.dto.account.polling.MtGoxAccountInfo;
import com.xeiam.xchange.mtgox.v2.dto.account.streaming.MtGoxWalletUpdate;
import com.xeiam.xchange.mtgox.v2.dto.marketdata.MtGoxDepthUpdate;
import com.xeiam.xchange.mtgox.v2.dto.marketdata.MtGoxTicker;
import com.xeiam.xchange.mtgox.v2.dto.marketdata.MtGoxTrade;
import com.xeiam.xchange.mtgox.v2.dto.trade.polling.MtGoxOpenOrder;
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

      try {
        // Get raw JSON
        // Map<String, Object> rawJSON = JSONUtils.getJsonGenericMap(exchangeEvent.getData(), streamObjectMapper);
        Map<String, Object> rawJSON;
        rawJSON = streamObjectMapper.readValue(exchangeEvent.getData(), new TypeReference<Map<String, Object>>() {
        });

        String operation = (String) rawJSON.get("op");
        if ("private".equals(operation)) {
          String priv = (String) rawJSON.get("private");
          if ("user_order".equals(priv)) {
            // MtGoxOpenOrder order = JSONUtils.getJsonObject(JSONUtils.getJSONString(rawJSON.get("user_order"), streamObjectMapper), MtGoxOpenOrder.class, streamObjectMapper);
            MtGoxOpenOrder order = streamObjectMapper.readValue(streamObjectMapper.writeValueAsString(rawJSON.get("user_order")), MtGoxOpenOrder.class);
            ExchangeEvent userOrderEvent = new DefaultExchangeEvent(ExchangeEventType.USER_ORDER, exchangeEvent.getData(), order);
            addToEventQueue(userOrderEvent);
            break;
          }
          else if ("lag".equals(priv)) {
            // MtGoxTradeLag lag = JSONUtils.getJsonObject(JSONUtils.getJSONString(rawJSON.get("lag"), streamObjectMapper), MtGoxTradeLag.class, streamObjectMapper);
            MtGoxTradeLag lag = streamObjectMapper.readValue(streamObjectMapper.writeValueAsString(rawJSON.get("lag")), MtGoxTradeLag.class);
            ExchangeEvent lagEvent = new DefaultExchangeEvent(ExchangeEventType.TRADE_LAG, exchangeEvent.getData(), lag);
            addToEventQueue(lagEvent);
            break;
          }
          else if ("wallet".equals(priv)) {
            // MtGoxWalletUpdate walletUpdate = JSONUtils.getJsonObject(JSONUtils.getJSONString(rawJSON.get("wallet"), streamObjectMapper), MtGoxWalletUpdate.class, streamObjectMapper);
            MtGoxWalletUpdate walletUpdate = streamObjectMapper.readValue(streamObjectMapper.writeValueAsString(rawJSON.get("wallet")), MtGoxWalletUpdate.class);
            ExchangeEvent walletUpdateEvent = new DefaultExchangeEvent(ExchangeEventType.USER_WALLET_UPDATE, exchangeEvent.getData(), walletUpdate);
            addToEventQueue(walletUpdateEvent);
            break;
          }

        }
        else if ("result".equals(operation)) {

          String id = (String) rawJSON.get("id");

          if ("idkey".equals(id)) {
            ExchangeEvent idEvent = new DefaultExchangeEvent(ExchangeEventType.PRIVATE_ID_KEY, null, rawJSON.get("result"));
            addToEventQueue(idEvent);
            break;
          }
          else if ("orders".equals(id)) {
            MtGoxOpenOrder[] orders = null;

            if (rawJSON.get("result") != null) {
              // orders = JSONUtils.getJsonObject(JSONUtils.getJSONString(rawJSON.get("result"), streamObjectMapper), MtGoxOpenOrder[].class, streamObjectMapper);
              orders = streamObjectMapper.readValue(streamObjectMapper.writeValueAsString(rawJSON.get("result")), MtGoxOpenOrder[].class);
            }

            ExchangeEvent ordersEvent = new DefaultExchangeEvent(ExchangeEventType.USER_ORDERS_LIST, exchangeEvent.getData(), orders);
            addToEventQueue(ordersEvent);
            break;

          }
          else if ("info".equals(id)) {
            // MtGoxAccountInfo accountInfo = JSONUtils.getJsonObject(JSONUtils.getJSONString(rawJSON.get("result"), streamObjectMapper), MtGoxAccountInfo.class, streamObjectMapper);
            MtGoxAccountInfo accountInfo = streamObjectMapper.readValue(streamObjectMapper.writeValueAsString(rawJSON.get("result")), MtGoxAccountInfo.class);
            ExchangeEvent accountInfoEvent = new DefaultExchangeEvent(ExchangeEventType.ACCOUNT_INFO, exchangeEvent.getData(), accountInfo);
            addToEventQueue(accountInfoEvent);
            break;

          }
          else if (id.startsWith("order_add")) {
            ExchangeEvent userOrderAddedEvent = new DefaultExchangeEvent(ExchangeEventType.USER_ORDER_ADDED, exchangeEvent.getData(), rawJSON.get("result"));
            addToEventQueue(userOrderAddedEvent);
            break;

          }
          else if (id.startsWith("order_cancel")) {

            // TODO implement the case when the following message comes in from MtGox
            // {id=order_cancel:c8fa912b-d929-4cc5-98e6-3ea23667cfa5, message=Order not found, op=remark, success=false}

            // if (((String) rawJSON.get("message")).equals("Order not found")) {
            // ExchangeEvent userOrderCanceledEvent = new DefaultExchangeEvent(ExchangeEventType.USER_ORDER_NOT_FOUND, exchangeEvent.getData(), rawJSON.get("message"));
            // addToEventQueue(userOrderCanceledEvent);
            // }
            // else {
            // MtGoxOrderCanceled orderCanceled = JSONUtils.getJsonObject(JSONUtils.getJSONString(rawJSON.get("result"), streamObjectMapper), MtGoxOrderCanceled.class, streamObjectMapper);
            MtGoxOrderCanceled orderCanceled = streamObjectMapper.readValue(streamObjectMapper.writeValueAsString(rawJSON.get("result")), MtGoxOrderCanceled.class);
            ExchangeEvent userOrderCanceledEvent = new DefaultExchangeEvent(ExchangeEventType.USER_ORDER_CANCELED, exchangeEvent.getData(), orderCanceled);
            addToEventQueue(userOrderCanceledEvent);
            // }

            break;
          }

        }
        else if ("remark".equals(operation)) {
          System.out.println("Msg from server: " + rawJSON.toString());
          break;
        }

        // Determine what has been sent
        if (rawJSON.containsKey("ticker")) {

          // Get MtGoxTicker from JSON String
          // MtGoxTicker mtGoxTicker = JSONUtils.getJsonObject(JSONUtils.getJSONString(rawJSON.get("ticker"), streamObjectMapper), MtGoxTicker.class, streamObjectMapper);
          MtGoxTicker mtGoxTicker = streamObjectMapper.readValue(streamObjectMapper.writeValueAsString(rawJSON.get("ticker")), MtGoxTicker.class);

          // Adapt to XChange DTOs
          Ticker ticker = MtGoxAdapters.adaptTicker(mtGoxTicker);

          // Create a ticker event
          ExchangeEvent tickerEvent = new DefaultExchangeEvent(ExchangeEventType.TICKER, exchangeEvent.getData(), ticker);
          addToEventQueue(tickerEvent);
          break;
        }
        else {
          if (rawJSON.containsKey("trade")) {

            // log.debug("exchangeEvent: " + exchangeEvent.getEventType());

            // Get MtGoxTradeStream from JSON String
            // MtGoxTrade mtGoxTradeStream = JSONUtils.getJsonObject(JSONUtils.getJSONString(rawJSON.get("trade"), streamObjectMapper), MtGoxTrade.class, streamObjectMapper);
            MtGoxTrade mtGoxTradeStream = streamObjectMapper.readValue(streamObjectMapper.writeValueAsString(rawJSON.get("trade")), MtGoxTrade.class);

            // Adapt to XChange DTOs
            Trade trade = MtGoxAdapters.adaptTrade(mtGoxTradeStream);

            // Create a trade event
            ExchangeEvent tradeEvent = new DefaultExchangeEvent(ExchangeEventType.TRADE, exchangeEvent.getData(), trade);
            addToEventQueue(tradeEvent);
            break;
          }
          else {
            if (rawJSON.containsKey("depth")) {

              // Get MtGoxDepthStream from JSON String
              // MtGoxDepthUpdate mtGoxDepthStream = JSONUtils.getJsonObject(JSONUtils.getJSONString(rawJSON.get("depth"), streamObjectMapper), MtGoxDepthUpdate.class, streamObjectMapper);
              MtGoxDepthUpdate mtGoxDepthStream = streamObjectMapper.readValue(streamObjectMapper.writeValueAsString(rawJSON.get("depth")), MtGoxDepthUpdate.class);

              // Adapt to XChange DTOs
              OrderBookUpdate orderBookUpdate = MtGoxAdapters.adaptDepthUpdate(mtGoxDepthStream);

              // Create a depth event
              ExchangeEvent depthEvent = new DefaultExchangeEvent(ExchangeEventType.DEPTH, exchangeEvent.getData(), orderBookUpdate);
              addToEventQueue(depthEvent);
              break;
            }
            else {

              log.debug("MtGox operational message");
              System.out.println("msg: " + rawJSON.toString());
              addToEventQueue(exchangeEvent);
            }
          }
        }
      } catch (JsonParseException e) {
        log.error("Error parsing returned JSON", e);
      } catch (JsonMappingException e) {
        log.error("Error parsing returned JSON", e);
      } catch (IOException e) {
        log.error("Error parsing returned JSON", e);
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
