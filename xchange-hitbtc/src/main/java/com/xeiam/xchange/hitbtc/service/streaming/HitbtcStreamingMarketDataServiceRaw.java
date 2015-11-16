package com.xeiam.xchange.hitbtc.service.streaming;

import java.net.URI;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.service.streaming.BaseWebSocketExchangeService;

public class HitbtcStreamingMarketDataServiceRaw extends BaseWebSocketExchangeService {

  private final HitbtcStreamingMarketDataRawEventListener exchangeEventListener;

  /**
   * Constructor
   *
   * @param exchange
   */
  public HitbtcStreamingMarketDataServiceRaw(Exchange exchange, HitbtcStreamingMarketDataConfiguration configuration) {

    super(exchange, configuration);

    this.exchangeEventListener = new HitbtcStreamingMarketDataRawEventListener(consumerEventQueue);
  }

  @Override
  public void connect() throws Exception {

    URI uri = URI.create(exchange.getExchangeSpecification().getPlainTextUriStreaming());
    
    // Use the default internal connect
    internalConnect(uri, exchangeEventListener, null);
  }
}
