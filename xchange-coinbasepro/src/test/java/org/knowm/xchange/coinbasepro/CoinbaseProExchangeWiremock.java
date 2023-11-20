package org.knowm.xchange.coinbasepro;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import com.github.tomakehurst.wiremock.recording.RecordSpecBuilder;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;

public abstract class CoinbaseProExchangeWiremock {

    protected static CoinbaseProExchange exchange;
    private static final boolean IS_RECORDING = false;

    @ClassRule
    public static WireMockClassRule wireMockRule = new WireMockClassRule(options().dynamicPort());

    @BeforeClass
    public static void initExchange() {
      ExchangeSpecification exSpec = new ExchangeSpecification(CoinbaseProExchange.class);

      if (IS_RECORDING) {

        wireMockRule.startRecording(
            new RecordSpecBuilder()
                .forTarget("https://api.pro.coinbase.com")
                .matchRequestBodyWithEqualToJson()
                .extractTextBodiesOver(1L)
                .chooseBodyMatchTypeAutomatically()
        );

      }

      exchange = (CoinbaseProExchange) ExchangeFactory.INSTANCE.createExchange(exSpec);

    }


    @AfterClass
    public static void stop() {
      if (IS_RECORDING) {
        wireMockRule.stopRecording();
      }
    }
}
