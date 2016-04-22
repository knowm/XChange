package org.knowm.xchange.coinsetter.service.streaming;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.java_websocket.WebSocket.READYSTATE;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinsetter.CoinsetterAdapters;
import org.knowm.xchange.coinsetter.dto.marketdata.CoinsetterPair;
import org.knowm.xchange.coinsetter.dto.marketdata.CoinsetterTicker;
import org.knowm.xchange.coinsetter.dto.marketdata.CoinsetterTrade;
import org.knowm.xchange.coinsetter.service.streaming.event.CoinsetterExchangeAdapter;
import org.knowm.xchange.coinsetter.service.streaming.event.CoinsetterSocketAdapter;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.service.streaming.DefaultExchangeEvent;
import org.knowm.xchange.service.streaming.ExchangeEvent;
import org.knowm.xchange.service.streaming.ExchangeEventType;
import org.knowm.xchange.service.streaming.StreamingExchangeService;

import io.socket.SocketIOException;

/**
 * Coinsetter streaming service implementation over Websockets API.
 */
public class CoinsetterSocketIOService extends CoinsetterSocketIOServiceRaw implements StreamingExchangeService {

  private final BlockingQueue<ExchangeEvent> consumerEventQueue = new LinkedBlockingQueue<ExchangeEvent>();

  private volatile READYSTATE webSocketStatus = READYSTATE.NOT_YET_CONNECTED;

  private OrderBook previousOrderBook = null;
  private Trade previousTrade = null;

  /**
   * Constructor
   *
   * @param exchange
   * @param coinsetterStreamingConfiguration
   */
  public CoinsetterSocketIOService(Exchange exchange, CoinsetterStreamingConfiguration coinsetterStreamingConfiguration) {

    super(exchange, coinsetterStreamingConfiguration);

    super.addListener(new CoinsetterSocketAdapter() {

      @Override
      public void onConnect() {

        webSocketStatus = READYSTATE.OPEN;
        putEvent(ExchangeEventType.CONNECT);
      }

      @Override
      public void onDisconnect() {

        webSocketStatus = READYSTATE.CLOSED;
        putEvent(ExchangeEventType.DISCONNECT);
      }

      @Override
      public void onError(SocketIOException socketIOException) {

        putEvent(new DefaultExchangeEvent(ExchangeEventType.ERROR, socketIOException.getMessage(), socketIOException));
      }

    });

    super.addListener(new CoinsetterExchangeAdapter() {

      @Override
      public void onTicker(CoinsetterTicker coinsetterTicker) {

        Ticker ticker = CoinsetterAdapters.adaptTicker(coinsetterTicker);
        putEvent(new DefaultExchangeEvent(ExchangeEventType.TICKER, null, ticker));
      }

      @Override
      public void onDepth(CoinsetterPair[] depth) {

        OrderBook orderBook = CoinsetterAdapters.adaptOrderBook(depth);
        if (!orderBook.ordersEqual(previousOrderBook)) {
          putEvent(new DefaultExchangeEvent(ExchangeEventType.DEPTH, null, orderBook));
          previousOrderBook = orderBook;
        }
      }

      @Override
      public void onLast(CoinsetterTrade last) {

        Trade trade = CoinsetterAdapters.adaptTrade(last);
        if (!trade.equals(previousTrade)) {
          putEvent(new DefaultExchangeEvent(ExchangeEventType.TRADE, null, trade));
          previousTrade = trade;
        }
      }
    });
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ExchangeEvent getNextEvent() throws InterruptedException {

    return consumerEventQueue.take();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int countEventsAvailable() {
    return consumerEventQueue.size();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void send(String msg) {

    // There's nothing to send for the current API!
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public READYSTATE getWebSocketStatus() {

    return webSocketStatus;
  }

  private void putEvent(ExchangeEvent event) {

    try {
      consumerEventQueue.put(event);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  private void putEvent(ExchangeEventType exchangeEventType) {

    putEvent(new DefaultExchangeEvent(exchangeEventType, null));
  }

}
