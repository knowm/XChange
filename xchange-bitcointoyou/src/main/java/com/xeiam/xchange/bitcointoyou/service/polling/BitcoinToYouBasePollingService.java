package com.xeiam.xchange.bitcointoyou.service.polling;

import java.io.IOException;
import java.util.List;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.bitcointoyou.BitcoinToYou;
import com.xeiam.xchange.bitcointoyou.BitcoinToYouAuthenticated;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;

/**
 * @author Felipe Micaroni Lalli
 */
public class BitcoinToYouBasePollingService extends BaseExchangeService implements BasePollingService {

  protected final BitcoinToYouAuthenticated bitcoinToYouAuthenticated;
  protected final BitcoinToYou bitcoinToYou;

  /**
   * Constructor
   *
   * @param exchange
   */
  public BitcoinToYouBasePollingService(Exchange exchange) {

    super(exchange);
    this.bitcoinToYouAuthenticated = RestProxyFactory.createProxy(BitcoinToYouAuthenticated.class, exchange.getExchangeSpecification().getSslUri());
    this.bitcoinToYou = RestProxyFactory.createProxy(BitcoinToYou.class, exchange.getExchangeSpecification().getSslUri());
  }

  @Override
  public List<CurrencyPair> getExchangeSymbols() throws IOException {

    return exchange.getMetaData().getCurrencyPairs();
  }

}
