package com.xeiam.xchange.oer.service.polling;

import java.io.IOException;
import java.util.List;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;

/**
 * @author timmolter
 */
public class OERBasePollingService extends BaseExchangeService implements BasePollingService {

  public OERBasePollingService(Exchange exchange) {

    super(exchange);
  }
}
