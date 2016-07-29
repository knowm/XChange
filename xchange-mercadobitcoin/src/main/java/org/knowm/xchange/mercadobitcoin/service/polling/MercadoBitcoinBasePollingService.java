package org.knowm.xchange.mercadobitcoin.service.polling;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.polling.BasePollingService;

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
