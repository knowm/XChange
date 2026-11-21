package org.knowm.xchange.binance.service.trade;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.containing;
import static com.github.tomakehurst.wiremock.client.WireMock.notMatching;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.binance.AbstractResilienceTest;
import org.knowm.xchange.binance.BinanceExchange;
import org.knowm.xchange.binance.dto.ExchangeType;
import org.knowm.xchange.binance.service.BinanceTradeService;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OrderHedgeMode;

/**
 * Regression tests for order placement in Binance hedge (dual-side) mode. Binance rejects requests
 * that send {@code reduceOnly} together with {@code positionSide} ("Cannot send this parameter in
 * Hedge Mode"), so in hedge mode the request must carry {@code positionSide} only, while one-way
 * mode must keep translating exit orders into {@code reduceOnly}.
 *
 * @see <a
 *     href="https://developers.binance.com/docs/derivatives/usds-margined-futures/trade/rest-api">New
 *     Order (Trade - Futures USDⓈ-M REST API)</a>
 */
class TradeServiceHedgeModeOrderTest extends AbstractResilienceTest {

  private static final String NEW_ORDER_RESPONSE = "{\"orderId\":\"42\"}";

  @Test
  void hedgeModeExitOrderSendsPositionSideWithoutReduceOnly() throws Exception {
    BinanceTradeService service = createFuturesExchange();
    stubNewFuturesOrder();

    LimitOrder order =
        new LimitOrder.Builder(Order.OrderType.EXIT_ASK, new FuturesContract("BTC/USDT/PERP"))
            .originalAmount(new BigDecimal("1"))
            .limitPrice(new BigDecimal("30000"))
            .flag(OrderHedgeMode.TWOWAY)
            .build();

    String orderId = service.placeLimitOrder(order);

    assertThat(orderId).isEqualTo("42");
    verify(
        postRequestedFor(urlPathEqualTo("/fapi/v1/order"))
            .withRequestBody(containing("positionSide=SHORT"))
            // reduceOnly must not be sent at all when positionSide is set
            .withRequestBody(notMatching(".*reduceOnly.*")));
  }

  @Test
  void oneWayExitOrderSendsReduceOnlyWithoutPositionSide() throws Exception {
    BinanceTradeService service = createFuturesExchange();
    stubNewFuturesOrder();

    LimitOrder order =
        new LimitOrder.Builder(Order.OrderType.EXIT_ASK, new FuturesContract("BTC/USDT/PERP"))
            .originalAmount(new BigDecimal("1"))
            .limitPrice(new BigDecimal("30000"))
            .build();

    String orderId = service.placeLimitOrder(order);

    assertThat(orderId).isEqualTo("42");
    verify(
        postRequestedFor(urlPathEqualTo("/fapi/v1/order"))
            .withRequestBody(containing("reduceOnly=true"))
            .withRequestBody(notMatching(".*positionSide.*")));
  }

  private void stubNewFuturesOrder() {
    stubFor(
        post(urlPathEqualTo("/fapi/v1/order"))
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBody(NEW_ORDER_RESPONSE)));
  }

  private BinanceTradeService createFuturesExchange() {
    BinanceExchange exchange =
        new BinanceExchange() {
          @Override
          protected void concludeHostParams(ExchangeSpecification exchangeSpecification) {
            // keep the wiremock url
          }
        };
    ExchangeSpecification specification = exchange.getDefaultExchangeSpecification();
    specification.setHost("localhost");
    specification.setSslUri("http://localhost:" + wireMockRule.getPort() + "/");
    specification.setPort(wireMockRule.getPort());
    specification.setShouldLoadRemoteMetaData(false);
    specification.setHttpReadTimeout(READ_TIMEOUT_MS);
    specification.setExchangeSpecificParametersItem(
        BinanceExchange.EXCHANGE_TYPE, ExchangeType.FUTURES);
    specification.setApiKey("apiKey");
    specification.setSecretKey("secretKey");
    specification.getResilience().setRetryEnabled(false);
    specification.getResilience().setRateLimiterEnabled(false);
    exchange.applySpecification(specification);
    return (BinanceTradeService) exchange.getTradeService();
  }
}
