package org.knowm.xchange.blockchain.service;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.ClassRule;
import org.junit.Rule;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.blockchain.BlockchainExchange;

public class BlockchainBaseTest {

    @ClassRule
    public static WireMockRule wireMockRule = new WireMockRule();

    protected static BlockchainExchange createExchange() {
        BlockchainExchange exchange =
                ExchangeFactory.INSTANCE.createExchangeWithoutSpecification(BlockchainExchange.class);
        ExchangeSpecification specification = exchange.getDefaultExchangeSpecification();
        specification.setHost("localhost");
        specification.setSslUri("http://localhost:" + wireMockRule.port() + "/");
        specification.setPort(wireMockRule.port());
        specification.setShouldLoadRemoteMetaData(false);
        specification.setHttpReadTimeout(1000);
        exchange.applySpecification(specification);
        return exchange;
    }
}
