package org.knowm.xchange.coinsetter.service.streaming;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinsetter.dto.marketdata.CoinsetterLevel;
import org.knowm.xchange.coinsetter.dto.marketdata.CoinsetterLevels;
import org.knowm.xchange.coinsetter.dto.marketdata.CoinsetterPair;
import org.knowm.xchange.coinsetter.dto.marketdata.CoinsetterTicker;
import org.knowm.xchange.coinsetter.dto.marketdata.CoinsetterTrade;
import org.knowm.xchange.coinsetter.dto.trade.CoinsetterOrderStatus;
import org.knowm.xchange.coinsetter.service.streaming.event.CoinsetterExchangeListener;
import org.knowm.xchange.coinsetter.service.streaming.event.CoinsetterSocketAdapter;
import org.knowm.xchange.coinsetter.service.streaming.event.CoinsetterSocketListener;
import org.knowm.xchange.service.BaseExchangeService;

import io.socket.IOAcknowledge;

/**
 * Coinsetter Websockets API implementation, fires Coinsetter exchange raw DTO events.
 */
public class CoinsetterSocketIOServiceRaw extends BaseExchangeService {

  private final Logger log = LoggerFactory.getLogger(CoinsetterSocketIOServiceRaw.class);

  private final CoinsetterSocket socket;
  private final Gson gson = new Gson();

  private final List<CoinsetterExchangeListener> exchangeListeners = new ArrayList<CoinsetterExchangeListener>();

  /**
   * Constructor
   *
   * @param exchange
   * @param coinsetterStreamingConfiguration
   */
  public CoinsetterSocketIOServiceRaw(Exchange exchange, CoinsetterStreamingConfiguration coinsetterStreamingConfiguration) {

    super(exchange);

    socket = new CoinsetterSocket(exchange, coinsetterStreamingConfiguration);
    socket.addListener(new CoinsetterSocketAdapter() {

      @Override
      public void on(String event, IOAcknowledge ack, JsonElement... args) {

        if (event == null) {
          log.warn("event is null.");
        } else if (event.equals("last")) {
          CoinsetterTrade last = gson.fromJson(args[0], CoinsetterTrade.class);
          for (CoinsetterExchangeListener listener : exchangeListeners) {
            listener.onLast(last);
          }
        } else if (event.equals("ticker")) {
          CoinsetterTicker ticker = gson.fromJson(args[0], CoinsetterTicker.class);
          for (CoinsetterExchangeListener listener : exchangeListeners) {
            listener.onTicker(ticker);
          }
        } else if (event.equals("depth")) {
          CoinsetterPair[] depth = gson.fromJson(args[0], CoinsetterPair[].class);
          for (CoinsetterExchangeListener listener : exchangeListeners) {
            listener.onDepth(depth);
          }
        } else if (event.equals("levels")) {
          CoinsetterLevels levels = gson.fromJson(args[0], CoinsetterLevels.class);
          for (CoinsetterExchangeListener listener : exchangeListeners) {
            listener.onLevels(levels);
          }
        } else if (event.equals("levels_COINSETTER") || event.equals("levels_SMART")) {
          CoinsetterLevel level = gson.fromJson(args[0], CoinsetterLevel.class);
          for (CoinsetterExchangeListener listener : exchangeListeners) {
            listener.onLevel(level);
          }
        } else if (event.startsWith("orders-")) {
          CoinsetterOrderStatus orderStatus = gson.fromJson(args[0], CoinsetterOrderStatus.class);
          for (CoinsetterExchangeListener listener : exchangeListeners) {
            listener.onOrderStatus(orderStatus);
          }
        } else {
          log.warn("Unknown event: {}", event);
        }
      }

    });
  }

  public void addListener(CoinsetterSocketListener listener) {

    this.socket.addListener(listener);
  }

  public void addListener(CoinsetterExchangeListener listener) {

    this.exchangeListeners.add(listener);
  }

  public void connect() {

    socket.connect();
  }

  public void disconnect() {

    socket.disconnect();
  }

}
