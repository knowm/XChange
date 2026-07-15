package org.knowm.xchange.gateio.service;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.dto.account.Fee;
import org.knowm.xchange.gateio.GateioExchange;
import org.knowm.xchange.gateio.config.Config;
import org.knowm.xchange.gateio.dto.GateioExchangeType;
import org.knowm.xchange.instrument.Instrument;
import si.mazi.rescu.CustomRestProxyFactoryImpl;

import java.io.IOException;
import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;

public class GateioFuturesAccountServiceTest {

  static GateioExchange exchange;
  static WireMockServer wireMockServer;

  @BeforeAll
  static void init() {
    wireMockServer = new WireMockServer(WireMockConfiguration.wireMockConfig().dynamicPort());
    wireMockServer.start();

    Config.getInstance().setRestProxyFactoryClass(CustomRestProxyFactoryImpl.class);

    ExchangeSpecification exSpec = new ExchangeSpecification(GateioExchange.class);
    exSpec.setSslUri("http://localhost:" + wireMockServer.port());
    exSpec.setApiKey("a");
    exSpec.setSecretKey("b");
    exSpec.setExchangeSpecificParametersItem(GateioExchange.EXCHANGE_TYPE, GateioExchangeType.FUTURES);

    exchange = (GateioExchange) ExchangeFactory.INSTANCE.createExchange(exSpec);
  }

  @AfterAll
  static void stop() {
    wireMockServer.stop();
  }

  @Test
  void get_dynamic_trading_fees_by_instrument() throws IOException {
    GateioAccountService gateioAccountService = (GateioAccountService) exchange.getAccountService();
    Map<Instrument, Fee> fees = gateioAccountService.getDynamicTradingFeesByInstrument();
    assertThat(fees).isNotEmpty();
    Fee fee = fees.values().iterator().next();
    assertThat(fee.getMakerFee()).isNotNull();
    assertThat(fee.getTakerFee()).isNotNull();
  }

  @Test
  void set_leverage() throws IOException {
    wireMockServer.stubFor(post(urlPathMatching("/futures/usdt/positions/BTC_USDT/leverage"))
        .willReturn(aResponse()
            .withHeader("Content-Type", "application/json")
            .withBodyFile("api_v4_futures_set_leverage.json")));

    GateioAccountService gateioAccountService = (GateioAccountService) exchange.getAccountService();
    boolean success = gateioAccountService.setLeverage(new FuturesContract("BTC/USDT/USDT"), 10);
    assertThat(success).isTrue();

    wireMockServer.verify(postRequestedFor(urlPathMatching("/api/v4/futures/usdt/positions/BTC_USDT/leverage"))
        .withQueryParam("leverage", equalTo("10"))
        .withoutQueryParam("cross_leverage_limit"));
  }
}
