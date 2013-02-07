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
package com.xeiam.xchange.mtgox.v1.service.marketdata.streaming.socketio;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.BlockingQueue;

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

  private ObjectMapper streamObjectMapper = new ObjectMapper();

  // private final BlockingQueue<Ticker> tickerQueue;
  private final BlockingQueue<ExchangeEvent> eventQueue;

  /**
   * Constructor
   * 
   * @param tickerQueue The consumer Ticker queue
   * @param tradeQueue
   * @param depthQueue
   * @param eventQueue The consumer exchange event queue
   */
  public MtGoxRunnableExchangeEventListener(BlockingQueue<ExchangeEvent> eventQueue) {

    this.eventQueue = eventQueue;
  }

  @Override
  public void handleEvent(ExchangeEvent exchangeEvent) {

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
      log.debug("Generic message. Length=" + exchangeEvent.getData().length());
      addToEventQueue(exchangeEvent);
      break;
    case JSON_MESSAGE:
      log.debug("JSON message. Length=" + exchangeEvent.getData().length());

      // Get raw JSON
      Map<String, Object> rawJSON = JSONUtils.getJsonGenericMap(exchangeEvent.getData(), streamObjectMapper);

      // Determine what has been sent
      if (rawJSON.containsKey("ticker")) {

        // Get MtGoxTicker from JSON String
        MtGoxTicker mtGoxTicker = JSONUtils.getJsonObject(JSONUtils.getJSONString(rawJSON.get("ticker"), streamObjectMapper), MtGoxTicker.class, streamObjectMapper);

        // Adapt to XChange DTOs
        Ticker ticker = MtGoxAdapters.adaptTicker(mtGoxTicker);

        // TODO Remove this once ticker queue is removed
        // addToTickerQueue(ticker);

        // Create a ticker event
        ExchangeEvent tickerEvent = new DefaultExchangeEvent(ExchangeEventType.TICKER, exchangeEvent.getData(), ticker);
        addToEventQueue(tickerEvent);
        break;
      } else {
        if (rawJSON.containsKey("trade")) {

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
            OrderBookUpdate depthstream = MtGoxAdapters.adaptDepthStream(mtGoxDepthStream);

            // Create a depth event
            ExchangeEvent depthEvent = new DefaultExchangeEvent(ExchangeEventType.DEPTH, exchangeEvent.getData(), depthstream);
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
      log.error("Error message. Length=" + exchangeEvent.getData().length());
      addToEventQueue(exchangeEvent);
      break;
    default:
      throw new IllegalStateException("Unknown ExchangeEventType " + exchangeEvent.getEventType().name());
    }

  }

  /*
   * private void addToTickerQueue(Ticker ticker) { try { tickerQueue.put(ticker); } catch (InterruptedException e) { throw new ExchangeException("InterruptedException!", e); } } private void
   * addToTradeQueue(Trade tradeStream) { try { tradeQueue.put(tradeStream); } catch (InterruptedException e) { throw new ExchangeException("InterruptedException!", e); } } private void
   * addToDepthQueue(Trade depthStream) { try { tradeQueue.put(depthStream); } catch (InterruptedException e) { throw new ExchangeException("InterruptedException!", e); } }
   */
  private void addToEventQueue(ExchangeEvent event) {

    try {
      eventQueue.put(event);
    } catch (InterruptedException e) {
      throw new ExchangeException("InterruptedException!", e);
    }
  }

}
