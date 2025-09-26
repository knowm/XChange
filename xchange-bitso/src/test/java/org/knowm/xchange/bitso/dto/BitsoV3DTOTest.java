package org.knowm.xchange.bitso.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.time.Instant;
import org.junit.Test;
import org.knowm.xchange.bitso.BitsoJacksonObjectMapperFactory;
import org.knowm.xchange.bitso.dto.account.BitsoBalance;
import org.knowm.xchange.bitso.dto.marketdata.BitsoAvailableBooks;
import org.knowm.xchange.bitso.dto.marketdata.BitsoOrderBook;
import org.knowm.xchange.bitso.dto.marketdata.BitsoTicker;
import org.knowm.xchange.bitso.dto.marketdata.BitsoTrades;
import org.knowm.xchange.bitso.dto.trade.*;

public class BitsoV3DTOTest {

  private final ObjectMapper objectMapper = BitsoJacksonObjectMapperFactory.getInstance();

  @Test
  public void testBitsoOrderRequestSerialization() throws Exception {
    BitsoOrderRequest request =
        BitsoOrderRequest.builder()
            .book("btc_mxn")
            .side(BitsoOrderSide.BUY)
            .type(BitsoOrderType.LIMIT)
            .major(new BigDecimal("0.01"))
            .price(new BigDecimal("5000.00"))
            .originId("test-order-123")
            .timeInForce(BitsoTimeInForce.GOOD_TILL_CANCELLED)
            .build();

    String json = objectMapper.writeValueAsString(request);
    System.out.println("BitsoOrderRequest JSON: " + json);

    assertThat(json).contains("\"book\":\"btc_mxn\"");
    assertThat(json).contains("\"side\":\"buy\"");
    assertThat(json).contains("\"type\":\"limit\"");
    assertThat(json).contains("\"major\":0.01");
    assertThat(json).contains("\"price\":5000.00");
    assertThat(json).contains("\"origin_id\":\"test-order-123\"");
    assertThat(json).contains("\"time_in_force\":\"goodtillcancelled\"");
  }

  @Test
  public void testBitsoOrderResponseDeserialization() throws Exception {
    String json = "{\"oid\":\"test-order-456\"}";

    BitsoOrderResponse response = objectMapper.readValue(json, BitsoOrderResponse.class);

    assertThat(response.getOid()).isEqualTo("test-order-456");
  }

  @Test
  public void testBitsoOrderDeserialization() throws Exception {
    String json =
        "{"
            + "\"book\":\"btc_mxn\","
            + "\"created_at\":\"2024-01-15T10:30:00.000+00:00\","
            + "\"oid\":\"test-order-789\","
            + "\"origin_id\":\"client-order-123\","
            + "\"original_amount\":\"0.01000000\","
            + "\"original_value\":\"500.00\","
            + "\"price\":\"50000.00\","
            + "\"side\":\"buy\","
            + "\"status\":\"open\","
            + "\"time_in_force\":\"goodtillcancelled\","
            + "\"type\":\"limit\","
            + "\"unfilled_amount\":\"0.01000000\","
            + "\"updated_at\":\"2024-01-15T10:30:00.000+00:00\""
            + "}";

    BitsoOrder order = objectMapper.readValue(json, BitsoOrder.class);

    assertThat(order.getBook()).isEqualTo("btc_mxn");
    assertThat(order.getOid()).isEqualTo("test-order-789");
    assertThat(order.getOriginId()).isEqualTo("client-order-123");
    assertThat(order.getSide()).isEqualTo(BitsoOrderSide.BUY);
    assertThat(order.getStatus()).isEqualTo(BitsoOrderStatus.OPEN);
    assertThat(order.getType()).isEqualTo(BitsoOrderType.LIMIT);
    assertThat(order.getPrice()).isEqualTo(new BigDecimal("50000.00"));
    assertThat(order.getOriginalAmount()).isEqualTo(new BigDecimal("0.01000000"));
  }

  @Test
  public void testBitsoBalanceDeserialization() throws Exception {
    String json =
        "{"
            + "\"balances\":["
            + "{"
            + "\"currency\":\"btc\","
            + "\"total\":\"1.00000000\","
            + "\"locked\":\"0.25000000\","
            + "\"available\":\"0.75000000\","
            + "\"pending_deposit\":\"0.00000000\","
            + "\"pending_withdrawal\":\"0.00000000\""
            + "},"
            + "{"
            + "\"currency\":\"mxn\","
            + "\"total\":\"10000.00\","
            + "\"locked\":\"2500.00\","
            + "\"available\":\"7500.00\","
            + "\"pending_deposit\":\"0.00\","
            + "\"pending_withdrawal\":\"0.00\""
            + "}"
            + "]"
            + "}";

    BitsoBalance balance = objectMapper.readValue(json, BitsoBalance.class);

    assertThat(balance.getBalances()).hasSize(2);

    BitsoBalance.CurrencyBalance btcBalance = balance.getBalances().get(0);
    assertThat(btcBalance.getCurrency()).isEqualTo("btc");
    assertThat(btcBalance.getTotal()).isEqualTo(new BigDecimal("1.00000000"));
    assertThat(btcBalance.getLocked()).isEqualTo(new BigDecimal("0.25000000"));
    assertThat(btcBalance.getAvailable()).isEqualTo(new BigDecimal("0.75000000"));

    BitsoBalance.CurrencyBalance mxnBalance = balance.getBalances().get(1);
    assertThat(mxnBalance.getCurrency()).isEqualTo("mxn");
    assertThat(mxnBalance.getTotal()).isEqualTo(new BigDecimal("10000.00"));
  }

  @Test
  public void testBitsoBaseResponseDeserialization() throws Exception {
    String json = "{" + "\"success\":true," + "\"payload\":{\"oid\":\"test-order-123\"}" + "}";

    BitsoBaseResponse<BitsoOrderResponse> response =
        objectMapper.readValue(
            json,
            objectMapper
                .getTypeFactory()
                .constructParametricType(BitsoBaseResponse.class, BitsoOrderResponse.class));

    assertThat(response.getSuccess()).isTrue();
    assertThat(response.getPayload()).isNotNull();
    assertThat(response.getPayload().getOid()).isEqualTo("test-order-123");
  }

  @Test
  public void testBitsoUserTransactionDeserialization() throws Exception {
    String json =
        "{"
            + "\"book\":\"btc_mxn\","
            + "\"created_at\":\"2024-01-15T10:30:00.000+00:00\","
            + "\"fees_amount\":\"-50.00\","
            + "\"fees_currency\":\"mxn\","
            + "\"major\":\"-0.01000000\","
            + "\"major_currency\":\"btc\","
            + "\"maker_side\":\"sell\","
            + "\"minor\":\"500.00\","
            + "\"minor_currency\":\"mxn\","
            + "\"oid\":\"trade-order-123\","
            + "\"origin_id\":\"client-trade-456\","
            + "\"price\":\"50000.00\","
            + "\"side\":\"sell\","
            + "\"tid\":\"trade-789\""
            + "}";

    BitsoUserTransaction transaction = objectMapper.readValue(json, BitsoUserTransaction.class);

    assertThat(transaction.getBook()).isEqualTo("btc_mxn");
    assertThat(transaction.getOid()).isEqualTo("trade-order-123");
    assertThat(transaction.getTid()).isEqualTo("trade-789");
    assertThat(transaction.getSide()).isEqualTo("sell");
    assertThat(transaction.getMakerSide()).isEqualTo("sell");
    assertThat(transaction.getPrice()).isEqualTo(new BigDecimal("50000.00"));
    assertThat(transaction.getMajor()).isEqualTo(new BigDecimal("-0.01000000"));
    assertThat(transaction.getMinor()).isEqualTo(new BigDecimal("500.00"));
    assertThat(transaction.getFeesAmount()).isEqualTo(new BigDecimal("-50.00"));
    assertThat(transaction.getFeesCurrency()).isEqualTo("mxn");
  }

  @Test
  public void testBitsoTickerV3() throws Exception {
    String json =
        "{"
            + "\"success\": true,"
            + "\"payload\": {"
            + "\"book\": \"btc_mxn\","
            + "\"volume\": \"112.81964756\","
            + "\"high\": \"472472.82\","
            + "\"last\": \"372110.00\","
            + "\"low\": \"10000.00\","
            + "\"vwap\": \"388387.4631589659\","
            + "\"ask\": \"372800.00\","
            + "\"bid\": \"372110.00\","
            + "\"created_at\": \"2023-03-09T20:58:23+00:00\","
            + "\"change_24\": \"-25580.00\","
            + "\"rolling_average_change\": {"
            + "\"6\": \"-0.5228\""
            + "}"
            + "}"
            + "}";

    BitsoTicker ticker = objectMapper.readValue(json, BitsoTicker.class);

    assertThat(ticker.getSuccess()).isTrue();
    assertThat(ticker.getPayload()).isNotNull();

    BitsoTicker.BitsoTickerData payload = ticker.getPayload();
    assertThat(payload.getBook()).isEqualTo("btc_mxn");
    assertThat(payload.getVolume()).isEqualTo("112.81964756");
    assertThat(payload.getHigh()).isEqualTo("472472.82");
    assertThat(payload.getLast()).isEqualTo("372110.00");
    assertThat(payload.getLow()).isEqualTo("10000.00");
    assertThat(payload.getVwap()).isEqualTo("388387.4631589659");
    assertThat(payload.getAsk()).isEqualTo("372800.00");
    assertThat(payload.getBid()).isEqualTo("372110.00");
    assertThat(payload.getCreatedAt()).isEqualTo(Instant.parse("2023-03-09T20:58:23Z"));
    assertThat(payload.getChange24()).isEqualTo("-25580.00");

    assertThat(ticker.getLast()).isEqualTo(new BigDecimal("372110.00"));
    assertThat(ticker.getHigh()).isEqualTo(new BigDecimal("472472.82"));
    assertThat(ticker.getLow()).isEqualTo(new BigDecimal("10000.00"));
    assertThat(ticker.getVwap()).isEqualTo(new BigDecimal("388387.4631589659"));
  }

  @Test
  public void testBitsoOrderBookV3() throws Exception {
    String json =
        "{"
            + "\"success\": true,"
            + "\"payload\": {"
            + "\"asks\": ["
            + "{\"book\": \"btc_mxn\", \"price\": \"5000.00\", \"amount\": \"0.25\"},"
            + "{\"book\": \"btc_mxn\", \"price\": \"5010.00\", \"amount\": \"0.15\"}"
            + "],"
            + "\"bids\": ["
            + "{\"book\": \"btc_mxn\", \"price\": \"4990.00\", \"amount\": \"0.30\"},"
            + "{\"book\": \"btc_mxn\", \"price\": \"4980.00\", \"amount\": \"0.20\"}"
            + "],"
            + "\"updated_at\": \"2023-03-09T20:58:23+00:00\","
            + "\"sequence\": \"1234567890\""
            + "}"
            + "}";

    BitsoOrderBook orderBook = objectMapper.readValue(json, BitsoOrderBook.class);

    assertThat(orderBook.getSuccess()).isTrue();
    assertThat(orderBook.getPayload()).isNotNull();

    BitsoOrderBook.BitsoOrderBookData payload = orderBook.getPayload();
    assertThat(payload.getAsks()).isNotNull();
    assertThat(payload.getBids()).isNotNull();
    assertThat(payload.getAsks().size()).isEqualTo(2);
    assertThat(payload.getBids().size()).isEqualTo(2);
    assertThat(payload.getUpdatedAt()).isEqualTo(Instant.parse("2023-03-09T20:58:23Z"));
    assertThat(payload.getSequence()).isEqualTo("1234567890");

    // Test individual entries
    BitsoOrderBook.BitsoOrderBookEntry firstAsk = payload.getAsks().get(0);
    assertThat(firstAsk.getBook()).isEqualTo("btc_mxn");
    assertThat(firstAsk.getPrice()).isEqualTo("5000.00");
    assertThat(firstAsk.getAmount()).isEqualTo("0.25");

    BitsoOrderBook.BitsoOrderBookEntry firstBid = payload.getBids().get(0);
    assertThat(firstBid.getBook()).isEqualTo("btc_mxn");
    assertThat(firstBid.getPrice()).isEqualTo("4990.00");
    assertThat(firstBid.getAmount()).isEqualTo("0.30");

    // Test legacy compatibility methods
    assertThat(orderBook.getAsks().size()).isEqualTo(2);
    assertThat(orderBook.getBids().size()).isEqualTo(2);

    // Test legacy format conversion
    assertThat(orderBook.getAsks().get(0).get(0)).isEqualTo(new BigDecimal("5000.00")); // price
    assertThat(orderBook.getAsks().get(0).get(1)).isEqualTo(new BigDecimal("0.25")); // amount
    assertThat(orderBook.getBids().get(0).get(0)).isEqualTo(new BigDecimal("4990.00")); // price
    assertThat(orderBook.getBids().get(0).get(1)).isEqualTo(new BigDecimal("0.30")); // amount
  }

  @Test
  public void testBitsoTradesV3() throws Exception {
    String json =
        "{"
            + "\"success\": true,"
            + "\"payload\": ["
            + "{"
            + "\"book\": \"btc_mxn\","
            + "\"created_at\": \"2023-03-09T20:58:23+00:00\","
            + "\"amount\": \"0.01000000\","
            + "\"maker_side\": \"buy\","
            + "\"price\": \"500000.00\","
            + "\"tid\": 1234567"
            + "},"
            + "{"
            + "\"book\": \"btc_mxn\","
            + "\"created_at\": \"2023-03-09T20:57:15+00:00\","
            + "\"amount\": \"0.02000000\","
            + "\"maker_side\": \"sell\","
            + "\"price\": \"499500.00\","
            + "\"tid\": 1234566"
            + "}"
            + "]"
            + "}";

    BitsoTrades trades = objectMapper.readValue(json, BitsoTrades.class);

    assertThat(trades.getSuccess()).isTrue();
    assertThat(trades.getPayload()).isNotNull();
    assertThat(trades.getPayload().size()).isEqualTo(2);

    BitsoTrades.BitsoTrade firstTrade = trades.getPayload().get(0);
    assertThat(firstTrade.getBook()).isEqualTo("btc_mxn");
    assertThat(firstTrade.getCreatedAt()).isEqualTo("2023-03-09T20:58:23+00:00");
    assertThat(firstTrade.getAmount()).isEqualTo("0.01000000");
    assertThat(firstTrade.getMakerSide()).isEqualTo("buy");
    assertThat(firstTrade.getPrice()).isEqualTo("500000.00");
    assertThat(firstTrade.getTid()).isEqualTo(1234567);

    BitsoTrades.BitsoTrade secondTrade = trades.getPayload().get(1);
    assertThat(secondTrade.getBook()).isEqualTo("btc_mxn");
    assertThat(secondTrade.getMakerSide()).isEqualTo("sell");
    assertThat(secondTrade.getTid()).isEqualTo(1234566);
  }

  @Test
  public void testBitsoAvailableBooksV3() throws Exception {
    String json =
        "{"
            + "\"success\": true,"
            + "\"payload\": ["
            + "{"
            + "\"book\": \"btc_mxn\","
            + "\"minimum_amount\": \"0.00001000\","
            + "\"maximum_amount\": \"100.00000000\","
            + "\"minimum_price\": \"1.00\","
            + "\"maximum_price\": \"1000000.00\","
            + "\"minimum_value\": \"5.00\","
            + "\"maximum_value\": \"1000000.00\","
            + "\"tick_size\": \"0.01\""
            + "},"
            + "{"
            + "\"book\": \"eth_mxn\","
            + "\"minimum_amount\": \"0.00100000\","
            + "\"maximum_amount\": \"1000.00000000\","
            + "\"minimum_price\": \"1.00\","
            + "\"maximum_price\": \"100000.00\","
            + "\"minimum_value\": \"5.00\","
            + "\"maximum_value\": \"500000.00\","
            + "\"tick_size\": \"0.01\""
            + "}"
            + "]"
            + "}";

    BitsoAvailableBooks availableBooks = objectMapper.readValue(json, BitsoAvailableBooks.class);

    assertThat(availableBooks.getSuccess()).isTrue();
    assertThat(availableBooks.getPayload()).isNotNull();
    assertThat(availableBooks.getPayload().size()).isEqualTo(2);

    BitsoAvailableBooks.BitsoBook firstBook = availableBooks.getPayload().get(0);
    assertThat(firstBook.getBook()).isEqualTo("btc_mxn");
    assertThat(firstBook.getMinimumAmount()).isEqualTo("0.00001000");
    assertThat(firstBook.getMaximumAmount()).isEqualTo("100.00000000");
    assertThat(firstBook.getMinimumPrice()).isEqualTo("1.00");
    assertThat(firstBook.getMaximumPrice()).isEqualTo("1000000.00");
    assertThat(firstBook.getMinimumValue()).isEqualTo("5.00");
    assertThat(firstBook.getMaximumValue()).isEqualTo("1000000.00");
    assertThat(firstBook.getTickSize()).isEqualTo("0.01");

    BitsoAvailableBooks.BitsoBook secondBook = availableBooks.getPayload().get(1);
    assertThat(secondBook.getBook()).isEqualTo("eth_mxn");
    assertThat(secondBook.getMinimumAmount()).isEqualTo("0.00100000");
  }
}
