package org.knowm.xchange.cryptocom.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.cryptocom.CryptoCom;
import org.knowm.xchange.cryptocom.CryptoComExchange;
import org.knowm.xchange.cryptocom.dto.CryptoComRequest;
import org.knowm.xchange.cryptocom.dto.CryptoComResponse;
import org.knowm.xchange.cryptocom.dto.trade.CryptoComOrderSide;
import org.knowm.xchange.cryptocom.dto.trade.CryptoComOrderType;
import org.knowm.xchange.exceptions.ExchangeSecurityException;

public class CryptoComTradeServiceRawTest {

  private final ObjectMapper mapper = new ObjectMapper();

  @Test
  public void marketBuy_sendsNotionalInsteadOfQuantity() throws Exception {
    CryptoComTradeServiceRaw raw = newRaw();

    raw.createCryptoComOrder(
        "BTC_USDT", CryptoComOrderSide.BUY, CryptoComOrderType.MARKET, null, "100", null, null);

    CryptoComRequest sent = lastRequest;
    assertThat(sent.getParams()).containsEntry("notional", "100");
    assertThat(sent.getParams()).doesNotContainKey("quantity");
  }

  @Test
  public void marketSell_sendsQuantity() throws Exception {
    CryptoComTradeServiceRaw raw = newRaw();

    raw.createCryptoComOrder(
        "BTC_USDT", CryptoComOrderSide.SELL, CryptoComOrderType.MARKET, null, "0.5", null, null);

    CryptoComRequest sent = lastRequest;
    assertThat(sent.getParams()).containsEntry("quantity", "0.5");
    assertThat(sent.getParams()).doesNotContainKey("notional");
  }

  @Test
  public void limitBuy_sendsQuantity() throws Exception {
    CryptoComTradeServiceRaw raw = newRaw();

    raw.createCryptoComOrder(
        "BTC_USDT",
        CryptoComOrderSide.BUY,
        CryptoComOrderType.LIMIT,
        "50000",
        "0.5",
        null,
        null);

    CryptoComRequest sent = lastRequest;
    assertThat(sent.getParams()).containsEntry("quantity", "0.5");
    assertThat(sent.getParams()).doesNotContainKey("notional");
  }

  @Test
  public void missingCredentials_rejectedBeforeSigning() throws Exception {
    CryptoComTradeServiceRaw raw = newRaw(null, null);

    assertThatThrownBy(
            () ->
                raw.createCryptoComOrder(
                    "BTC_USDT",
                    CryptoComOrderSide.BUY,
                    CryptoComOrderType.MARKET,
                    null,
                    "100",
                    null,
                    null))
        .isInstanceOf(ExchangeSecurityException.class);
  }

  private CryptoComRequest lastRequest;

  private CryptoComTradeServiceRaw newRaw() throws Exception {
    return newRaw("key", "secret");
  }

  private CryptoComTradeServiceRaw newRaw(String apiKey, String secretKey) throws Exception {
    CryptoCom cryptoCom = mock(CryptoCom.class);
    when(cryptoCom.createOrder(any()))
        .thenAnswer(
            invocation -> {
              lastRequest = invocation.getArgument(0);
              ObjectNode result = mapper.createObjectNode();
              result.put("order_id", "1");
              CryptoComResponse response = new CryptoComResponse();
              response.setResult(result);
              return response;
            });

    CryptoComExchange exchange = mock(CryptoComExchange.class);
    ExchangeSpecification spec = new ExchangeSpecification(CryptoComExchange.class);
    spec.setApiKey(apiKey);
    spec.setSecretKey(secretKey);
    when(exchange.getExchangeSpecification()).thenReturn(spec);
    when(exchange.getCryptoCom()).thenReturn(cryptoCom);
    when(exchange.nextRequestId()).thenReturn(1L);

    return new CryptoComTradeServiceRaw(exchange, new ResilienceRegistries());
  }
}
