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
package com.xeiam.xchange.anx.v2.service.streaming;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

import com.xeiam.xchange.anx.v2.ANXAdapters;
import com.xeiam.xchange.anx.v2.dto.marketdata.ANXDepthUpdate;
import com.xeiam.xchange.anx.v2.dto.marketdata.ANXTicker;
import com.xeiam.xchange.anx.v2.dto.marketdata.ANXTrade;
import com.xeiam.xchange.anx.v2.dto.trade.polling.ANXOpenOrder;
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
import com.xeiam.xchange.anx.v2.dto.account.polling.ANXAccountInfo;
import com.xeiam.xchange.service.streaming.DefaultExchangeEvent;
import com.xeiam.xchange.service.streaming.ExchangeEvent;
import com.xeiam.xchange.service.streaming.ExchangeEventListener;
import com.xeiam.xchange.service.streaming.ExchangeEventType;

/**
 * @author timmolter
 */
public class ANXExchangeEventListener extends ExchangeEventListener {

  private static final Logger log = LoggerFactory.getLogger(ANXExchangeEventListener.class);

  private final ObjectMapper streamObjectMapper;

  private final BlockingQueue<ExchangeEvent> consumerEventQueue;

  /**
   * Constructor
   * 
   * @param consumerEventQueue
   */
  public ANXExchangeEventListener(BlockingQueue<ExchangeEvent> consumerEventQueue) {

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
      log.debug("ANX connected");
      addToEventQueue(exchangeEvent);
      break;
    case DISCONNECT:
      log.debug("ANX disconnected");
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
            // ANXOpenOrder order = JSONUtils.getJsonObject(JSONUtils.getJSONString(rawJSON.get("user_order"), streamObjectMapper), ANXOpenOrder.class, streamObjectMapper);
            ANXOpenOrder order = streamObjectMapper.readValue(streamObjectMapper.writeValueAsString(rawJSON.get("user_order")), ANXOpenOrder.class);
            ExchangeEvent userOrderEvent = new DefaultExchangeEvent(ExchangeEventType.USER_ORDER, exchangeEvent.getData(), order);
            addToEventQueue(userOrderEvent);
            break;
          }
          else if ("lag".equals(priv)) {
            // ANXTradeLag lag = JSONUtils.getJsonObject(JSONUtils.getJSONString(rawJSON.get("lag"), streamObjectMapper), ANXTradeLag.class, streamObjectMapper);
            com.xeiam.xchange.anx.v2.dto.trade.streaming.ANXTradeLag lag = streamObjectMapper.readValue(streamObjectMapper.writeValueAsString(rawJSON.get("lag")), com.xeiam.xchange.anx.v2.dto.trade.streaming.ANXTradeLag.class);
            ExchangeEvent lagEvent = new DefaultExchangeEvent(ExchangeEventType.TRADE_LAG, exchangeEvent.getData(), lag);
            addToEventQueue(lagEvent);
            break;
          }
          else if ("wallet".equals(priv)) {
            // ANXWalletUpdate walletUpdate = JSONUtils.getJsonObject(JSONUtils.getJSONString(rawJSON.get("wallet"), streamObjectMapper), ANXWalletUpdate.class, streamObjectMapper);
            com.xeiam.xchange.anx.v2.dto.account.streaming.ANXWalletUpdate walletUpdate = streamObjectMapper.readValue(streamObjectMapper.writeValueAsString(rawJSON.get("wallet")), com.xeiam.xchange.anx.v2.dto.account.streaming.ANXWalletUpdate.class);
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
            ANXOpenOrder[] orders = null;

            if (rawJSON.get("result") != null) {
              // orders = JSONUtils.getJsonObject(JSONUtils.getJSONString(rawJSON.get("result"), streamObjectMapper), ANXOpenOrder[].class, streamObjectMapper);
              orders = streamObjectMapper.readValue(streamObjectMapper.writeValueAsString(rawJSON.get("result")), ANXOpenOrder[].class);
            }

            ExchangeEvent ordersEvent = new DefaultExchangeEvent(ExchangeEventType.USER_ORDERS_LIST, exchangeEvent.getData(), orders);
            addToEventQueue(ordersEvent);
            break;

          }
          else if ("info".equals(id)) {
            // ANXAccountInfo accountInfo = JSONUtils.getJsonObject(JSONUtils.getJSONString(rawJSON.get("result"), streamObjectMapper), ANXAccountInfo.class, streamObjectMapper);
            ANXAccountInfo accountInfo = streamObjectMapper.readValue(streamObjectMapper.writeValueAsString(rawJSON.get("result")), ANXAccountInfo.class);
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

            // TODO implement the case when the following message comes in from ANX
            // {id=order_cancel:c8fa912b-d929-4cc5-98e6-3ea23667cfa5, message=Order not found, op=remark, success=false}

            // if (((String) rawJSON.get("message")).equals("Order not found")) {
            // ExchangeEvent userOrderCanceledEvent = new DefaultExchangeEvent(ExchangeEventType.USER_ORDER_NOT_FOUND, exchangeEvent.getData(), rawJSON.get("message"));
            // addToEventQueue(userOrderCanceledEvent);
            // }
            // else {
            // ANXOrderCanceled orderCanceled = JSONUtils.getJsonObject(JSONUtils.getJSONString(rawJSON.get("result"), streamObjectMapper), ANXOrderCanceled.class, streamObjectMapper);
            com.xeiam.xchange.anx.v2.dto.trade.streaming.ANXOrderCanceled orderCanceled = streamObjectMapper.readValue(streamObjectMapper.writeValueAsString(rawJSON.get("result")), com.xeiam.xchange.anx.v2.dto.trade.streaming.ANXOrderCanceled.class);
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

          // Get ANXTicker from JSON String
          // ANXTicker anxTicker = JSONUtils.getJsonObject(JSONUtils.getJSONString(rawJSON.get("ticker"), streamObjectMapper), ANXTicker.class, streamObjectMapper);
          ANXTicker anxTicker = streamObjectMapper.readValue(streamObjectMapper.writeValueAsString(rawJSON.get("ticker")), ANXTicker.class);

          // Adapt to XChange DTOs
          Ticker ticker = ANXAdapters.adaptTicker(anxTicker);

          // Create a ticker event
          ExchangeEvent tickerEvent = new DefaultExchangeEvent(ExchangeEventType.TICKER, exchangeEvent.getData(), ticker);
          addToEventQueue(tickerEvent);
          break;
        }
        else {
          if (rawJSON.containsKey("trade")) {

            // log.debug("exchangeEvent: " + exchangeEvent.getEventType());

            // Get ANXTradeStream from JSON String
            // ANXTrade anxTradeStream = JSONUtils.getJsonObject(JSONUtils.getJSONString(rawJSON.get("trade"), streamObjectMapper), ANXTrade.class, streamObjectMapper);
            ANXTrade anxTradeStream = streamObjectMapper.readValue(streamObjectMapper.writeValueAsString(rawJSON.get("trade")), ANXTrade.class);

            // Adapt to XChange DTOs
            Trade trade = ANXAdapters.adaptTrade(anxTradeStream);

            // Create a trade event
            ExchangeEvent tradeEvent = new DefaultExchangeEvent(ExchangeEventType.TRADE, exchangeEvent.getData(), trade);
            addToEventQueue(tradeEvent);
            break;
          }
          else {
            if (rawJSON.containsKey("depth")) {

              // Get ANXDepthStream from JSON String
              // ANXDepthUpdate anxDepthStream = JSONUtils.getJsonObject(JSONUtils.getJSONString(rawJSON.get("depth"), streamObjectMapper), ANXDepthUpdate.class, streamObjectMapper);
              ANXDepthUpdate anxDepthStream = streamObjectMapper.readValue(streamObjectMapper.writeValueAsString(rawJSON.get("depth")), ANXDepthUpdate.class);

              // Adapt to XChange DTOs
              OrderBookUpdate orderBookUpdate = ANXAdapters.adaptDepthUpdate(anxDepthStream);

              // Create a depth event
              ExchangeEvent depthEvent = new DefaultExchangeEvent(ExchangeEventType.DEPTH, exchangeEvent.getData(), orderBookUpdate);
              addToEventQueue(depthEvent);
              break;
            }
            else {

              log.debug("ANX operational message");
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
