package org.knowm.xchange.binance.service.trade;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.delete;
import static com.github.tomakehurst.wiremock.client.WireMock.matchingJsonPath;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static com.github.tomakehurst.wiremock.client.WireMock.deleteRequestedFor;
import static org.assertj.core.api.Assertions.assertThat;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.Test;
import org.knowm.xchange.binance.AbstractResilienceTest;
import org.knowm.xchange.binance.BinanceAdapters;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.DefaultCancelOrderByInstrumentAndIdParams;

public class TradeServiceCancelOrderTest extends AbstractResilienceTest {

  private static final String CANCEL_ORDER_RESPONSE =
      "{"
          + "\"symbol\":\"LTCBTC\","
          + "\"origClientOrderId\":\"myOrder1\","
          + "\"orderId\":12345,"
          + "\"clientOrderId\":\"cancelMyOrder1\","
          + "\"price\":\"0.1\","
          + "\"origQty\":\"1.0\","
          + "\"executedQty\":\"0.0\","
          + "\"cummulativeQuoteQty\":\"0.0\","
          + "\"status\":\"CANCELED\","
          + "\"timeInForce\":\"GTC\","
          + "\"type\":\"LIMIT\","
          + "\"side\":\"BUY\""
          + "}";

  @Test
  public void cancelOrderByInstrumentAndIdParamsSucceedsWithoutUserReference() throws Exception {
    // given — caller supplies only Instrument + Id (no user reference). This previously
    // threw ClassCastException because the guard let it through but the body cast
    // to CancelOrderByUserReferenceParams unconditionally.
    BinanceAdapters.putSymbolMapping("LTCBTC", CurrencyPair.LTC_BTC);
    TradeService service = createExchangeWithRetryDisabled().getTradeService();
    stubFor(
        delete(urlPathEqualTo("/api/v3/order"))
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBody(CANCEL_ORDER_RESPONSE)));

    DefaultCancelOrderByInstrumentAndIdParams params =
        new DefaultCancelOrderByInstrumentAndIdParams(CurrencyPair.LTC_BTC, "12345");

    // when
    boolean result = service.cancelOrder(params);

    // then
    assertThat(result).isTrue();
    verify(
        deleteRequestedFor(urlPathEqualTo("/api/v3/order"))
            .withQueryParam("orderId", WireMock.equalTo("12345")));
    // origClientOrderId must not have been sent — caller did not provide a user reference
    assertThat(
            wireMockRule
                .findAll(deleteRequestedFor(urlPathEqualTo("/api/v3/order")))
                .get(0)
                .queryParameter("origClientOrderId")
                .isPresent())
        .isFalse();
  }
}
