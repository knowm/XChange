package info.bitrich.xchangestream.binance.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.binance.BinanceStreamingAdapters;
import info.bitrich.xchangestream.binance.dto.trade.BinanceWebsocketOrderAmendPayload;
import info.bitrich.xchangestream.binance.dto.trade.BinanceWebsocketOrderCancelAndReplacePayload;
import info.bitrich.xchangestream.binance.dto.trade.BinanceWebsocketPayload;
import info.bitrich.xchangestream.binance.dto.trade.BinanceWebsocketPlaceOrderPayload;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;

class BinanceWebsocketTradePayloadTest {

  private static ObjectMapper mapper;

  @BeforeAll
  static void setupClass() {
    mapper = new ObjectMapper();
  }

  @Test
  void testAdaptPlaceLimitOrderScientificNotation() throws Exception {
    BigDecimal smallAmount = new BigDecimal("0.00000001");
    BigDecimal smallPrice = new BigDecimal("0.00000005");

    LimitOrder limitOrder =
        new LimitOrder.Builder(OrderType.BID, CurrencyPair.BTC_USDT)
            .originalAmount(smallAmount)
            .limitPrice(smallPrice)
            .userReference("order123")
            .build();

    BinanceWebsocketPlaceOrderPayload payload =
        BinanceStreamingAdapters.adaptPlaceOrder(limitOrder);
    assertThat(payload.getQuantity()).isEqualTo("0.00000001");
    assertThat(payload.getPrice()).isEqualTo("0.00000005");

    BinanceWebsocketPayload<BinanceWebsocketPlaceOrderPayload> wsPayload =
        new BinanceWebsocketPayload<>("123", "order.place", payload);

    String json = mapper.writeValueAsString(wsPayload);
    JsonNode jsonNode = mapper.readTree(json);

    assertThat(jsonNode.get("params").get("quantity").isTextual()).isTrue();
    assertThat(jsonNode.get("params").get("quantity").asText()).isEqualTo("0.00000001");
    assertThat(jsonNode.get("params").get("price").isTextual()).isTrue();
    assertThat(jsonNode.get("params").get("price").asText()).isEqualTo("0.00000005");
    assertThat(json).doesNotContain("1E-8");
    assertThat(json).doesNotContain("5E-8");
  }

  @Test
  void testAdaptPlaceMarketOrderScientificNotation() throws Exception {
    BigDecimal smallAmount = new BigDecimal("0.00000001");

    MarketOrder marketOrder =
        new MarketOrder.Builder(OrderType.ASK, CurrencyPair.BTC_USDT)
            .originalAmount(smallAmount)
            .userReference("order456")
            .build();

    BinanceWebsocketPlaceOrderPayload payload =
        BinanceStreamingAdapters.adaptPlaceOrder(marketOrder);
    assertThat(payload.getQuantity()).isEqualTo("0.00000001");
    assertThat(payload.getPrice()).isNull();

    BinanceWebsocketPayload<BinanceWebsocketPlaceOrderPayload> wsPayload =
        new BinanceWebsocketPayload<>("123", "order.place", payload);

    String json = mapper.writeValueAsString(wsPayload);
    JsonNode jsonNode = mapper.readTree(json);

    assertThat(jsonNode.get("params").get("quantity").isTextual()).isTrue();
    assertThat(jsonNode.get("params").get("quantity").asText()).isEqualTo("0.00000001");
    assertThat(jsonNode.get("params").has("price")).isFalse();
  }

  @Test
  void testAdaptAmendOrderScientificNotation() throws Exception {
    BigDecimal smallAmount = new BigDecimal("0.00000001");
    BigDecimal smallPrice = new BigDecimal("0.00000005");

    LimitOrder limitOrder =
        new LimitOrder.Builder(OrderType.BID, CurrencyPair.BTC_USDT)
            .originalAmount(smallAmount)
            .limitPrice(smallPrice)
            .id("987654")
            .userReference("order123")
            .build();

    BinanceWebsocketOrderAmendPayload payload =
        BinanceStreamingAdapters.adaptAmendOrder(limitOrder);
    assertThat(payload.getQuantity()).isEqualTo("0.00000001");
    assertThat(payload.getPrice()).isEqualTo("0.00000005");

    BinanceWebsocketPayload<BinanceWebsocketOrderAmendPayload> wsPayload =
        new BinanceWebsocketPayload<>("123", "order.modify", payload);

    String json = mapper.writeValueAsString(wsPayload);
    JsonNode jsonNode = mapper.readTree(json);

    assertThat(jsonNode.get("params").get("quantity").isTextual()).isTrue();
    assertThat(jsonNode.get("params").get("quantity").asText()).isEqualTo("0.00000001");
    assertThat(jsonNode.get("params").get("price").isTextual()).isTrue();
    assertThat(jsonNode.get("params").get("price").asText()).isEqualTo("0.00000005");
  }

  @Test
  void testOrderCancelAndReplacePayloadSerialization() throws Exception {
    BigDecimal smallAmount = new BigDecimal("0.00000001");
    BigDecimal smallPrice = new BigDecimal("0.00000005");

    BinanceWebsocketOrderCancelAndReplacePayload payload =
        BinanceWebsocketOrderCancelAndReplacePayload.builder()
            .symbol("BTCUSDT")
            .price(smallPrice.toPlainString())
            .quantity(smallAmount.toPlainString())
            .build();

    BinanceWebsocketPayload<BinanceWebsocketOrderCancelAndReplacePayload> wsPayload =
        new BinanceWebsocketPayload<>("123", "order.cancelReplace", payload);

    String json = mapper.writeValueAsString(wsPayload);
    JsonNode jsonNode = mapper.readTree(json);

    assertThat(jsonNode.get("params").get("quantity").isTextual()).isTrue();
    assertThat(jsonNode.get("params").get("quantity").asText()).isEqualTo("0.00000001");
    assertThat(jsonNode.get("params").get("price").isTextual()).isTrue();
    assertThat(jsonNode.get("params").get("price").asText()).isEqualTo("0.00000005");
  }
}
