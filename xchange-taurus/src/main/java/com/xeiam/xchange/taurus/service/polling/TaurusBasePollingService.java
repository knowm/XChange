package com.xeiam.xchange.taurus.service.polling;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;

/**
 * @author timmolter
 */
public class TaurusBasePollingService extends BaseExchangeService implements BasePollingService {

  public TaurusBasePollingService(Exchange exchange) {
    super(exchange);
  }
}
