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

import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;

class GateioFuturesAccountServiceTest {

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
  void get_dynamic_trading_fees_by_instrument() throws Exception {
    GateioAccountService gateioAccountService = (GateioAccountService) exchange.getAccountService();
    Map<Instrument, Fee> fees = gateioAccountService.getDynamicTradingFeesByInstrument();
    assertThat(fees).isNotEmpty();
    Fee fee = fees.values().iterator().next();
    assertThat(fee.getMakerFee()).isNotNull();
    assertThat(fee.getTakerFee()).isNotNull();
  }

  @Test
  void set_leverage() throws Exception {
    wireMockServer.stubFor(post(urlPathEqualTo("/api/v4/futures/usdt/positions/BTC_USDT/leverage"))
        .willReturn(aResponse()
            .withHeader("Content-Type", "application/json")
            .withBodyFile("api_v4_futures_set_leverage.json")));

    GateioAccountService gateioAccountService = (GateioAccountService) exchange.getAccountService();
    boolean success = gateioAccountService.setLeverage(new FuturesContract("BTC/USDT/USDT"), 10);
    assertThat(success).isTrue();

    wireMockServer.verify(postRequestedFor(urlPathEqualTo("/api/v4/futures/usdt/positions/BTC_USDT/leverage"))
        .withQueryParam("leverage", equalTo("10"))
        .withoutQueryParam("cross_leverage_limit"));
  }

  @Test
  void set_leverage_single_mode() throws Exception {
    wireMockServer.stubFor(post(urlPathEqualTo("/api/v4/futures/usdt/positions/BTC_USDT/leverage"))
        .willReturn(aResponse()
            .withHeader("Content-Type", "application/json")
            .withBodyFile("api_v4_futures_set_leverage_single.json")));

    GateioAccountService gateioAccountService = (GateioAccountService) exchange.getAccountService();
    boolean success = gateioAccountService.setLeverage(new FuturesContract("BTC/USDT/USDT"), 10);
    assertThat(success).isTrue();
  }

  @Test
  void set_leverage_dual_mode() throws Exception {
    wireMockServer.stubFor(post(urlPathEqualTo("/api/v4/futures/usdt/positions/BTC_USDT/leverage"))
        .willReturn(aResponse()
            .withHeader("Content-Type", "application/json")
            .withBodyFile("api_v4_futures_set_leverage_dual.json")));

    GateioAccountService gateioAccountService = (GateioAccountService) exchange.getAccountService();
    boolean success = gateioAccountService.setLeverage(new FuturesContract("BTC/USDT/USDT"), 10);
    assertThat(success).isTrue();
  }

  @Test
  void set_cross_leverage() throws Exception {
    wireMockServer.stubFor(post(urlPathEqualTo("/api/v4/futures/usdt/positions/BTC_USDT/leverage"))
        .willReturn(aResponse()
            .withHeader("Content-Type", "application/json")
            .withBodyFile("api_v4_futures_set_leverage.json")));

    GateioAccountService gateioAccountService = (GateioAccountService) exchange.getAccountService();
    boolean success = gateioAccountService.setLeverage(new FuturesContract("BTC/USDT/USDT"), 0, 20);
    assertThat(success).isTrue();

    wireMockServer.verify(postRequestedFor(urlPathEqualTo("/api/v4/futures/usdt/positions/BTC_USDT/leverage"))
        .withQueryParam("leverage", equalTo("0"))
        .withQueryParam("cross_leverage_limit", equalTo("20")));
  }

  @Test
  void set_leverage_mismatch() throws Exception {
    wireMockServer.stubFor(post(urlPathEqualTo("/api/v4/futures/usdt/positions/BTC_USDT/leverage"))
        .willReturn(aResponse()
            .withHeader("Content-Type", "application/json")
            .withBodyFile("api_v4_futures_set_leverage.json")));

    GateioAccountService gateioAccountService = (GateioAccountService) exchange.getAccountService();
    boolean success = gateioAccountService.setLeverage(new FuturesContract("BTC/USDT/USDT"), 5);
    assertThat(success).isFalse();
  }

  @Test
  void set_dual_mode() throws Exception {
    wireMockServer.stubFor(post(urlPathEqualTo("/api/v4/futures/usdt/dual_mode"))
        .willReturn(aResponse()
            .withHeader("Content-Type", "application/json")
            .withBodyFile("api_v4_futures_set_dual_mode.json")));

    GateioAccountService gateioAccountService = (GateioAccountService) exchange.getAccountService();
    Boolean dualModeRaw = gateioAccountService.setDualModeRaw("usdt", true);
    assertThat(dualModeRaw).isNotNull();
    assertThat(dualModeRaw).isTrue();
    wireMockServer.verify(postRequestedFor(urlPathEqualTo("/api/v4/futures/usdt/dual_mode"))
        .withQueryParam("dual_mode", equalTo("true")));
  }

  @Test
  void set_dual_mode_no_change() throws Exception {
    wireMockServer.stubFor(post(urlPathEqualTo("/api/v4/futures/usdt/dual_mode"))
        .withQueryParam("dual_mode", equalTo("true"))
        .willReturn(aResponse()
            .withStatus(400)
            .withHeader("Content-Type", "application/json")
            .withBody("{\"label\":\"NO_CHANGE\",\"message\":\"Margin mode not changed\"}")));

    GateioAccountService gateioAccountService = (GateioAccountService) exchange.getAccountService();
    boolean dualMode = gateioAccountService.setDualModeRaw("usdt", true);
    assertThat(dualMode).isTrue();
  }

  @Test
  void set_position_mode() throws Exception {
    wireMockServer.stubFor(post(urlPathEqualTo("/api/v4/futures/btc/dual_mode"))
        .willReturn(aResponse()
            .withHeader("Content-Type", "application/json")
            .withBodyFile("api_v4_futures_set_dual_mode.json")));

    GateioAccountService gateioAccountService = (GateioAccountService) exchange.getAccountService();
    boolean dualModeSet = gateioAccountService.setDualMode("btc", false);
    assertThat(dualModeSet).isFalse();

    wireMockServer.verify(postRequestedFor(urlPathEqualTo("/api/v4/futures/btc/dual_mode"))
        .withQueryParam("dual_mode", equalTo("false")));
  }
}
