package info.bitrich.xchangestream.coincheck;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.JsonNode;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.SneakyThrows;
import org.junit.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.OrderBookUpdate;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.trade.LimitOrder;

public class CoincheckStreamingAdapterTest {

  @Test
  public void testParseOrderbookUpdate() {
    JsonNode json = CoincheckTestUtil.loadJson("example-orderbook-data.json");
    List<OrderBookUpdate> updates =
        CoincheckStreamingAdapter.parseOrderBookUpdates(json).collect(Collectors.toList());

    List<OrderBookUpdate> expected =
        Arrays.asList(
            new OrderBookUpdate(
                Order.OrderType.BID,
                new BigDecimal("0"),
                CurrencyPair.BTC_JPY,
                new BigDecimal("148634.0"),
                null,
                new BigDecimal("0")),
            new OrderBookUpdate(
                Order.OrderType.BID,
                new BigDecimal("0.0574"),
                CurrencyPair.BTC_JPY,
                new BigDecimal("148633.0"),
                null,
                new BigDecimal("0.0574")),
            new OrderBookUpdate(
                Order.OrderType.ASK,
                new BigDecimal("0"),
                CurrencyPair.BTC_JPY,
                new BigDecimal("148834.0"),
                null,
                new BigDecimal("0")),
            new OrderBookUpdate(
                Order.OrderType.ASK,
                new BigDecimal("1.0581"),
                CurrencyPair.BTC_JPY,
                new BigDecimal("148833.0"),
                null,
                new BigDecimal("1.0581")));
    assertThat(expected.size()).isEqualTo(4);
    for (int i = 0; i < 4; i++) {
      assertThat(updates.get(i).getTotalVolume()).isNotNull();
      assertThat(updates.get(i).getTotalVolume()).isEqualTo(expected.get(i).getTotalVolume());
      LimitOrder updatedOrder = updates.get(i).getLimitOrder();
      LimitOrder expectedOrder = expected.get(i).getLimitOrder();
      assertThat(updatedOrder.getType()).isEqualTo(expectedOrder.getType());
      assertThat(updatedOrder.getOriginalAmount()).isEqualTo(expectedOrder.getOriginalAmount());
      assertThat(updatedOrder.getLimitPrice()).isEqualTo(expectedOrder.getLimitPrice());
    }
  }

  @Test
  @SneakyThrows
  public void testParseTrade() {
    JsonNode json = CoincheckTestUtil.loadJson("example-trade-data.json");
    Trade trade = CoincheckStreamingAdapter.parseTrade(json);

    Trade expected =
        new Trade(
            Order.OrderType.ASK,
            new BigDecimal("5.0"),
            CurrencyPair.BTC_JPY,
            new BigDecimal("148638.0"),
            null,
            "2357062",
            null,
            null);
    assertThat(trade).isEqualTo(expected);
  }

  @Test
  @SneakyThrows
  public void testGetChannelNameFromTradeMessage() {
    JsonNode json = CoincheckTestUtil.loadJson("example-trade-data.json");
    assertThat(CoincheckStreamingAdapter.getChannelNameFromMessage(json))
        .isEqualTo("btc_jpy-trades");
  }

  @Test
  @SneakyThrows
  public void testGetChannelNameFromOrderBookMessage() {
    JsonNode json = CoincheckTestUtil.loadJson("example-orderbook-data.json");
    assertThat(CoincheckStreamingAdapter.getChannelNameFromMessage(json))
        .isEqualTo("btc_jpy-orderbook");
  }
}
