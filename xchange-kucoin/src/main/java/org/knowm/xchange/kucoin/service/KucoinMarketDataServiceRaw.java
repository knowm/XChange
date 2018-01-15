package org.knowm.xchange.kucoin.service;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.kucoin.dto.marketdata.KucoinTickResponse;

public class KucoinMarketDataServiceRaw extends KucoinBaseService {

  protected KucoinMarketDataServiceRaw(Exchange exchange) {
    super(exchange);
  }
  
  KucoinTickResponse getTicker(String market) throws IOException {
    return kucoin.getTicker(market);
  }
}
