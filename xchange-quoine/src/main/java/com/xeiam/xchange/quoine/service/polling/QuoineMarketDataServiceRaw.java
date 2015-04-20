package com.xeiam.xchange.quoine.service.polling;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.quoine.dto.marketdata.QuoineTicker;

public class QuoineMarketDataServiceRaw extends QuoineBasePollingService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public QuoineMarketDataServiceRaw(Exchange exchange) {

    super(exchange);
  }

  public QuoineTicker getQuoineTicker(String currencyPair) throws IOException {

    return quoine.getTicker(currencyPair);

  }

}
