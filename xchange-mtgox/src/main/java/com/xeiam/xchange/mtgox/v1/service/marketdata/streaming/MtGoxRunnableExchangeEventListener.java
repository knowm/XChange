/**
 * Copyright (C) 2012 - 2013 Xeiam LLC http://xeiam.com
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
package com.xeiam.xchange.mtgox.v1.service.marketdata.streaming;

import java.util.Map;
import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.dto.marketdata.OrderBookUpdate;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trade;
import com.xeiam.xchange.mtgox.v1.MtGoxAdapters;
import com.xeiam.xchange.mtgox.v1.dto.marketdata.MtGoxDepthStream;
import com.xeiam.xchange.mtgox.v1.dto.marketdata.MtGoxTicker;
import com.xeiam.xchange.mtgox.v1.dto.marketdata.MtGoxTradeStream;
import com.xeiam.xchange.rest.JSONUtils;
import com.xeiam.xchange.service.DefaultExchangeEvent;
import com.xeiam.xchange.service.ExchangeEvent;
import com.xeiam.xchange.service.ExchangeEventType;
import com.xeiam.xchange.service.RunnableExchangeEventListener;

/**
 * <p>
 * Exchange event listener to provide the following to MtGox:
 * </p>
 * <ul>
 * <li>Provision of dedicated Ticker queue</li>
 * <li>Provision of dedicated non-Ticker event queue</li>
 * </ul>
 */
public class MtGoxRunnableExchangeEventListener extends RunnableExchangeEventListener {

  private static final Logger log = LoggerFactory.getLogger(MtGoxRunnableExchangeEventListener.class);

  private final ObjectMapper streamObjectMapper;

  private final BlockingQueue<ExchangeEvent> consumerEventQueue;

  /**
   * Constructor
   * 
   * @param consumerEventQueue
   */
  public MtGoxRunnableExchangeEventListener(BlockingQueue<ExchangeEvent> consumerEventQueue) {

    this.consumerEventQueue = consumerEventQueue;
    streamObjectMapper = new ObjectMapper();
    streamObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

  }

  @Override
  public void handleEvent(ExchangeEvent exchangeEvent) {

    // log.debug("exchangeEvent: " + exchangeEvent.getEventType());

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
      // log.debug("Generic message. Length=" + exchangeEvent.getData().length());
      // log.debug("JSON message =" + exchangeEvent.getData());

      // Get raw JSON
      Map<String, Object> rawJSON = JSONUtils.getJsonGenericMap(exchangeEvent.getData(), streamObjectMapper);

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
          MtGoxTradeStream mtGoxTradeStream = JSONUtils.getJsonObject(JSONUtils.getJSONString(rawJSON.get("trade"), streamObjectMapper), MtGoxTradeStream.class, streamObjectMapper);

          // Adapt to XChange DTOs
          Trade trade = MtGoxAdapters.adaptTradeStream(mtGoxTradeStream);

          // Create a trade event
          ExchangeEvent tradeEvent = new DefaultExchangeEvent(ExchangeEventType.TRADE, exchangeEvent.getData(), trade);
          addToEventQueue(tradeEvent);
          break;
        } else {
          if (rawJSON.containsKey("depth")) {

            // Get MtGoxDepthStream from JSON String
            MtGoxDepthStream mtGoxDepthStream = JSONUtils.getJsonObject(JSONUtils.getJSONString(rawJSON.get("depth"), streamObjectMapper), MtGoxDepthStream.class, streamObjectMapper);

            // Adapt to XChange DTOs
            OrderBookUpdate orderBookUpdate = MtGoxAdapters.adaptDepthStream(mtGoxDepthStream);

            // Create a depth event
            ExchangeEvent depthEvent = new DefaultExchangeEvent(ExchangeEventType.DEPTH, exchangeEvent.getData(), orderBookUpdate);
            addToEventQueue(depthEvent);
            break;
          } else {
            log.debug("MtGox operational message");
            addToEventQueue(exchangeEvent);
          }
        }
      }
      break;
    case ERROR:
      log.error("Error message: " + exchangeEvent.getData());
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
