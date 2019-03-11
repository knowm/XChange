package org.knowm.xchange.bitcointoyou;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bitcointoyou.service.polling.BitcointoyouAccountService;
import org.knowm.xchange.bitcointoyou.service.polling.BitcointoyouMarketDataService;
import org.knowm.xchange.bitcointoyou.service.polling.BitcointoyouTradeService;
import org.knowm.xchange.utils.nonce.AtomicLongIncrementalTime2013NonceFactory;
import si.mazi.rescu.SynchronizedValueFactory;

/**
 * The Bitcointoyou Exchange represantation itself.
 *
 * @author Jonathas Carrijo
 * @author Danilo Guimaraes
 */
public class BitcointoyouExchange extends BaseExchange implements Exchange {

  private final SynchronizedValueFactory<Long> nonceFactory =
      new AtomicLongIncrementalTime2013NonceFactory();

  @Override
  protected void initServices() {

    final ExchangeSpecification spec = getExchangeSpecification();
    this.marketDataService = new BitcointoyouMarketDataService(this);

    if (spec.getApiKey() != null && spec.getSecretKey() != null) {
      this.accountService = new BitcointoyouAccountService(this);
      this.tradeService = new BitcointoyouTradeService(this);
    }
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification =
        new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://www.bitcointoyou.com/");
    exchangeSpecification.setHost("www.bitcointoyou.com");
    exchangeSpecification.setPort(443);
    exchangeSpecification.setExchangeName("Bitcointoyou");
    exchangeSpecification.setExchangeDescription("Bitcointoyou is a Brazilian bitcoin exchange.");
    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {

    return nonceFactory;
  }
}
