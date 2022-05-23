package org.knowm.xchange.blockchain;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.blockchain.service.BlockchainAccountService;
import org.knowm.xchange.blockchain.service.BlockchainMarketDataService;
import org.knowm.xchange.blockchain.service.BlockchainTradeService;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.client.ResilienceRegistries;
import si.mazi.rescu.SynchronizedValueFactory;

import javax.ws.rs.HeaderParam;

import static org.knowm.xchange.blockchain.BlockchainConstants.*;

/** @author Tim Molter */
public class BlockchainExchange extends BaseExchange {

  protected static ResilienceRegistries RESILIENCE_REGISTRIES;

  protected BlockchainAuthenticated blockchain;

  @Override
  protected void initServices() {
    this.blockchain = ExchangeRestProxyBuilder
            .forInterface(BlockchainAuthenticated.class, this.getExchangeSpecification())
            .clientConfigCustomizer(clientConfig -> {
              String secretKey = this.getExchangeSpecification().getSecretKey();
              clientConfig.addDefaultParam(HeaderParam.class, X_API_TOKEN, secretKey);
//              clientConfig.addDefaultParam(HeaderParam.class, AUTHORIZATION, String.format(BEARER_TOKEN_FORMAT, secretKey));
            }).build();

    this.marketDataService = new BlockchainMarketDataService(this, this.blockchain, this.getResilienceRegistries());
    this.accountService = new BlockchainAccountService(this, this.blockchain, this.getResilienceRegistries());
    this.tradeService = new BlockchainTradeService(this, this.blockchain, this.getResilienceRegistries());
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {
    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass());
    exchangeSpecification.setSslUri("https://api.staging.blockchain.info");
    exchangeSpecification.setHost("www.blockchain.com");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Blockchain Exchange");
    exchangeSpecification.setExchangeDescription("Blockchain Exchange");
    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  @Override
  public ResilienceRegistries getResilienceRegistries() {
    if (RESILIENCE_REGISTRIES == null) {
      RESILIENCE_REGISTRIES = BlockchainResilience.getResilienceRegistries();
    }
    return RESILIENCE_REGISTRIES;
  }
}
