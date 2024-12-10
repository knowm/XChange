package org.knowm.xchange.bitmex;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.recording.RecordSpecBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;

/** Sets up the wiremock for exchange */
public abstract class BitmexExchangeWiremock {

  protected static BitmexExchange exchange;

//      private static final boolean IS_RECORDING = true;
  private static final boolean IS_RECORDING = false;

  private static WireMockServer wireMockServer;

  @BeforeAll
  public static void initExchange() {
    wireMockServer = new WireMockServer(options().dynamicPort());
    wireMockServer.start();

    ExchangeSpecification exSpec = new ExchangeSpecification(BitmexExchange.class);
    exSpec.setSslUri("http://localhost:" + wireMockServer.port());
    exSpec.setApiKey(System.getProperty("apiKey", "abc"));
    exSpec.setSecretKey(System.getProperty("secretKey", "bcd"));

    if (IS_RECORDING) {
      // use default url and record the requests
      wireMockServer.startRecording(
          new RecordSpecBuilder()
              .forTarget("https://www.bitmex.com")
              .matchRequestBodyWithEqualToJson()
              .extractTextBodiesOver(1L)
              .chooseBodyMatchTypeAutomatically());
    }

    exchange = (BitmexExchange) ExchangeFactory.INSTANCE.createExchange(exSpec);
  }

  @AfterAll
  public static void stop() {
    if (IS_RECORDING) {
      wireMockServer.stopRecording();
    }
    wireMockServer.stop();
  }
}
