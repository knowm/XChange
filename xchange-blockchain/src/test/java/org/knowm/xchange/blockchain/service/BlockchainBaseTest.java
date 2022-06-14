package org.knowm.xchange.blockchain.service;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.ClassRule;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.blockchain.BlockchainExchange;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;

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

    protected void stubPost(String fileName, int statusCode, String url) {
        stubFor(
                post(urlPathEqualTo(url))
                        .willReturn(
                                aResponse()
                                        .withStatus(statusCode)
                                        .withHeader("Content-Type", "application/json")
                                        .withBodyFile(fileName)));
    }

    protected void stubGet(String fileName, int statusCode, String url) {
        stubFor(
                get(urlPathEqualTo(url))
                        .willReturn(
                                aResponse()
                                        .withStatus(statusCode)
                                        .withHeader("Content-Type", "application/json")
                                        .withBodyFile(fileName)));
    }

    protected void stubDelete(int statusCode, String url) {
        stubFor(
                delete(urlPathEqualTo(url))
                        .willReturn(
                                aResponse()
                                        .withStatus(statusCode)
                                        .withHeader("Content-Type", "application/json")));
    }
}
