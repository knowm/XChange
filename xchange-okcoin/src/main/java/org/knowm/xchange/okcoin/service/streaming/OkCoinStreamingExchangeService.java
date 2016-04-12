package org.knowm.xchange.okcoin.service.streaming;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.java_websocket.WebSocket.READYSTATE;

import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.okcoin.OkCoinExchange;
import org.knowm.xchange.service.streaming.ExchangeEvent;
import org.knowm.xchange.service.streaming.ExchangeStreamingConfiguration;
import org.knowm.xchange.service.streaming.StreamingExchangeService;

public class OkCoinStreamingExchangeService implements StreamingExchangeService {
  private final WebSocketBase socketBase;
  private final BlockingQueue<ExchangeEvent> eventQueue = new LinkedBlockingQueue<ExchangeEvent>();
  private final OkCoinExchangeStreamingConfiguration exchangeStreamingConfiguration;
  private final ChannelProvider channelProvider;

  public OkCoinStreamingExchangeService(ExchangeSpecification exchangeSpecification, ExchangeStreamingConfiguration exchangeStreamingConfiguration) {
    this.exchangeStreamingConfiguration = (OkCoinExchangeStreamingConfiguration) exchangeStreamingConfiguration;

    String sslUri = (String) exchangeSpecification.getExchangeSpecificParametersItem("Websocket_SslUri");
    boolean useFutures = (Boolean) exchangeSpecification.getExchangeSpecificParametersItem("Use_Futures");

    channelProvider = useFutures ? new FuturesChannelProvider(OkCoinExchange.futuresContractOfConfig(exchangeSpecification))
        : new SpotChannelProvider();

    WebSocketService socketService = new OkCoinWebSocketService(eventQueue, channelProvider,
        this.exchangeStreamingConfiguration.getMarketDataCurrencyPairs());
    socketBase = new WebSocketBase(sslUri, socketService);
  }

  @Override
  public void connect() {
    socketBase.start();

    for (CurrencyPair currencyPair : exchangeStreamingConfiguration.getMarketDataCurrencyPairs()) {
      socketBase.addChannel(channelProvider.getTicker(currencyPair));
      socketBase.addChannel(channelProvider.getDepth(currencyPair));
      socketBase.addChannel(channelProvider.getTrades(currencyPair));
    }
  }

  @Override
  public void disconnect() {
  }

  @Override
  public ExchangeEvent getNextEvent() throws InterruptedException {
    return eventQueue.take();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int countEventsAvailable() {
    return eventQueue.size();
  }

  @Override
  public void send(String msg) {
  }

  @Override
  public READYSTATE getWebSocketStatus() {
    return READYSTATE.OPEN;
  }
}
