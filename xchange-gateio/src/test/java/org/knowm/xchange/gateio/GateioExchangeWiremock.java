package org.knowm.xchange.gateio;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.github.tomakehurst.wiremock.recording.RecordSpecBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;

/** Sets up the wiremock for exchange */
public abstract class GateioExchangeWiremock {

  protected static GateioExchange exchange;

  private static final boolean IS_RECORDING = false;

  @RegisterExtension
  public static WireMockExtension wireMockRule =
      WireMockExtension.newInstance()
          .options(options().dynamicPort())
          .configureStaticDsl(true)
          .build();

  @BeforeAll
  public static void initExchange() {
    ExchangeSpecification exSpec = new ExchangeSpecification(GateioExchange.class);
    exSpec.setSslUri("http://localhost:" + wireMockRule.getPort());

    if (IS_RECORDING) {
      // use default url and record the requests
      wireMockRule.startRecording(
          new RecordSpecBuilder()
              .forTarget("https://api.gateio.ws")
              .matchRequestBodyWithEqualToJson()
              .extractTextBodiesOver(1L)
              .chooseBodyMatchTypeAutomatically());
    }

    exchange = (GateioExchange) ExchangeFactory.INSTANCE.createExchange(exSpec);
  }

  @AfterAll
  public static void stop() {
    if (IS_RECORDING) {
      wireMockRule.stopRecording();
    }
  }
}
