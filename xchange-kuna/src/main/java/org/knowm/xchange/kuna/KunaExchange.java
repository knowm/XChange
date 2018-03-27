package org.knowm.xchange.kuna;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.kuna.service.KunaAccountService;
import org.knowm.xchange.kuna.service.KunaMarketDataService;
import org.knowm.xchange.kuna.service.KunaTradeService;
import org.knowm.xchange.utils.nonce.CurrentTimeNonceFactory;
import si.mazi.rescu.SynchronizedValueFactory;

/**
 * Class for accessing Kuna Exchange.
 *
 * @author Dat Bui
 */
public class KunaExchange extends BaseExchange implements Exchange {

  public static final String KUNA_URL = "https://kuna.io/api/";
  public static final String KUNA_HOST = "kuna.io";
  public static final int KUNA_PORT = 80;

  private SynchronizedValueFactory<Long> nonceFactory = new CurrentTimeNonceFactory();

  @Override
  protected void initServices() {
    this.marketDataService = new KunaMarketDataService(this);
    this.accountService = new KunaAccountService(this);
    this.tradeService = new KunaTradeService(this);
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {
    return nonceFactory;
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {
    ExchangeSpecification exchangeSpecification =
        new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri(KUNA_URL);
    exchangeSpecification.setHost(KUNA_HOST);
    exchangeSpecification.setPort(KUNA_PORT);
    exchangeSpecification.setExchangeName("Kuna");
    exchangeSpecification.setExchangeDescription(
        "Kuna is the ukrainian crypto currency exchange platform.");
    return exchangeSpecification;
  }
}
