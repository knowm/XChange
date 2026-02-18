package org.knowm.xchange.deribit;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.recording.RecordSpecBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.deribit.v2.DeribitExchange;

/** Sets up the wiremock for exchange */
public abstract class DeribitExchangeWiremock {

  protected static Exchange exchange;

  private static final boolean IS_RECORDING = false;

  private static WireMockServer wireMockServer;

  @BeforeAll
  public static void initExchange() {
    wireMockServer = new WireMockServer(options().dynamicPort());
    wireMockServer.start();

    ExchangeSpecification exSpec = new ExchangeSpecification(DeribitExchange.class);
    exSpec.setSslUri("http://localhost:" + wireMockServer.port());
    exSpec.setApiKey("a");
    exSpec.setSecretKey("b");

    if (IS_RECORDING) {
      // use default url and record the requests
      wireMockServer.startRecording(
          new RecordSpecBuilder()
              .forTarget("https://www.deribit.com")
              .matchRequestBodyWithEqualToJson()
              .extractTextBodiesOver(1L)
              .chooseBodyMatchTypeAutomatically());
    }

    exchange = ExchangeFactory.INSTANCE.createExchange(exSpec);
  }

  @AfterAll
  public static void stop() {
    if (IS_RECORDING) {
      wireMockServer.stopRecording();
    }
    wireMockServer.stop();
  }
}
