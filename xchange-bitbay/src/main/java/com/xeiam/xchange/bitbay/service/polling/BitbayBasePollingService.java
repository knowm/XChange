package com.xeiam.xchange.bitbay.service.polling;

import java.io.IOException;
import java.util.List;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bitbay.Bitbay;
import com.xeiam.xchange.bitbay.service.BitbayDigest;
import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

/**
 * @author kpysniak
 */
public class BitbayBasePollingService extends BaseExchangeService implements BasePollingService {

  /**
   * Constructor
   *
   * @param exchange
   */
  protected BitbayBasePollingService(Exchange exchange) {

      super(exchange);
  }

  @Override
  public List<CurrencyPair> getExchangeSymbols() throws IOException {

    return exchange.getMetaData().getCurrencyPairs();
  }

}
