package org.knowm.xchange.kraken;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.recording.RecordSpecBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;

/** Sets up the wiremock for exchange */
public abstract class KrakenExchangeWiremock {

  protected static KrakenExchange exchange;

  private static final boolean IS_RECORDING = false;

  protected static WireMockServer wireMockServer;

  @BeforeAll
  public static void initExchange() {
    wireMockServer = new WireMockServer(options().dynamicPort());
    wireMockServer.start();

    ExchangeSpecification exSpec = new ExchangeSpecification(KrakenExchange.class);
    exSpec.setSslUri("http://localhost:" + wireMockServer.port());
    exSpec.setApiKey("a");
    exSpec.setSecretKey("YWJj");

    if (IS_RECORDING) {
      // use default url and record the requests
      wireMockServer.startRecording(
          new RecordSpecBuilder()
              .forTarget("https://api.kraken.com")
              .captureHeader("")
              .matchRequestBodyWithEqualTo()
              .extractTextBodiesOver(1L)
              .chooseBodyMatchTypeAutomatically());
    }

    exchange = (KrakenExchange) ExchangeFactory.INSTANCE.createExchange(exSpec);
  }

  @AfterAll
  public static void stop() {
    if (IS_RECORDING) {
      wireMockServer.stopRecording();
    }
    wireMockServer.stop();
  }
}
