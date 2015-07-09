package com.xeiam.xchange.mercadobitcoin.service.polling;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;

/**
 * @author Felipe Micaroni Lalli
 */
public class MercadoBitcoinBasePollingService extends BaseExchangeService implements BasePollingService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public MercadoBitcoinBasePollingService(Exchange exchange) {

    super(exchange);
  }
}
