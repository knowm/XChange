package org.knowm.xchange.blockchain;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.blockchain.dto.account.BlockchainSymbol;
import org.knowm.xchange.blockchain.service.BlockchainAccountService;
import org.knowm.xchange.blockchain.service.BlockchainAccountServiceRaw;
import org.knowm.xchange.blockchain.service.BlockchainMarketDataService;
import org.knowm.xchange.blockchain.service.BlockchainTradeService;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import si.mazi.rescu.SynchronizedValueFactory;

import jakarta.ws.rs.HeaderParam;
import java.io.IOException;
import java.util.Map;

import static org.knowm.xchange.blockchain.BlockchainConstants.*;

/**
 * @author scuevas
 */
public class BlockchainExchange extends BaseExchange {

    protected static ResilienceRegistries RESILIENCE_REGISTRIES;

    protected BlockchainAuthenticated blockchain;

    @Override
    protected void initServices() {
        this.blockchain = ExchangeRestProxyBuilder
                .forInterface(BlockchainAuthenticated.class, this.getExchangeSpecification())
                .clientConfigCustomizer(clientConfig -> {
                    clientConfig.addDefaultParam(HeaderParam.class, X_API_TOKEN, this.getExchangeSpecification().getSecretKey());
                    clientConfig.addDefaultParam(HeaderParam.class, X_API_INTEGRATION, XCHANGE);
                }).build();

        this.accountService = new BlockchainAccountService(this, this.blockchain, this.getResilienceRegistries());
        this.tradeService = new BlockchainTradeService(this, this.blockchain, this.getResilienceRegistries());
        this.marketDataService = new BlockchainMarketDataService(this, this.blockchain, this.getResilienceRegistries());
    }

    @Override
    public void remoteInit() throws IOException, ExchangeException {
        BlockchainAccountServiceRaw dataService =
                (BlockchainAccountServiceRaw) this.accountService;
        Map<String, BlockchainSymbol> markets = dataService.getSymbols();
        exchangeMetaData = BlockchainAdapters.adaptMetaData(markets);
    }

    @Override
    public ExchangeSpecification getDefaultExchangeSpecification() {
        ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass());
        exchangeSpecification.setSslUri("https://api.blockchain.com");
        exchangeSpecification.setHost("www.blockchain.com");
        exchangeSpecification.setPort(80);
        exchangeSpecification.setExchangeName("Blockchain Exchange");
        exchangeSpecification.setExchangeDescription("Blockchain Exchange");
        return exchangeSpecification;
    }

    @Override
    public SynchronizedValueFactory<Long> getNonceFactory() {
        throw new NotYetImplementedForExchangeException(NOT_IMPLEMENTED_YET);
    }

    @Override
    public ResilienceRegistries getResilienceRegistries() {
        if (RESILIENCE_REGISTRIES == null) {
            RESILIENCE_REGISTRIES = BlockchainResilience.getResilienceRegistries();
        }
        return RESILIENCE_REGISTRIES;
    }
}
