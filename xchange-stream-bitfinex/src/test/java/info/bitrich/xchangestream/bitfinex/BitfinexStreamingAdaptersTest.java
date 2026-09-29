package info.bitrich.xchangestream.bitfinex;

import static org.assertj.core.api.Assertions.assertThat;

import info.bitrich.xchangestream.bitfinex.dto.BitfinexWebSocketAuthOrder;
import info.bitrich.xchangestream.bitfinex.dto.BitfinexWebSocketAuthTrade;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderStatus;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.UserTrade;

class BitfinexStreamingAdaptersTest {

  @Test
  void marketOrder() {
    BitfinexWebSocketAuthOrder bitfinexWebSocketAuthOrder =
        new BitfinexWebSocketAuthOrder(
            123123123L, // id,
            0L, // groupId,
            456456456L, // cid,
            "tBTCUSD", // symbol,
            1548674205259L, // mtsCreateamount
            1548674205259L, // mtsUpdate
            new BigDecimal("0.000"), // amount
            new BigDecimal("0.004"), // amountOrig
            "MARKET", // type
            null, // typePrev
            "EXECUTED @ 3495.1(0.004)", // orderStatus
            new BigDecimal("3495.2"), // price
            new BigDecimal("3495.2"), // priceAvg
            BigDecimal.ZERO, // priceTrailing
            BigDecimal.ZERO, // priceAuxLimit
            0, // placedId
            0 // flags
            );

    // TODO awaiting https://github.com/knowm/XChange/pull/2907 then I can add market order
    // support to XChange itself. In the meantime these are returned as limit orders.

    Order adaptedOrder = BitfinexStreamingAdapters.adaptOrder(bitfinexWebSocketAuthOrder);

    assertThat(adaptedOrder.getId()).isEqualTo("123123123");
    assertThat(adaptedOrder.getType()).isEqualTo(Order.OrderType.BID);
    assertThat(adaptedOrder.getAveragePrice()).isEqualTo(new BigDecimal("3495.2"));
    assertThat(adaptedOrder.getCumulativeAmount()).isEqualTo(new BigDecimal("0.004"));
    assertThat(adaptedOrder.getCurrencyPair()).isEqualTo(CurrencyPair.BTC_USD);

    // TODO see above. should be:
    // assertEquals(Collections.singleton(BitfinexOrderFlags.MARGIN), adaptedOrder.getOrderFlags());

    assertThat(adaptedOrder.getOriginalAmount()).isEqualTo(new BigDecimal("0.004"));
    assertThat(adaptedOrder.getRemainingAmount()).isEqualTo(new BigDecimal("0.000"));
    assertThat(adaptedOrder.getStatus()).isEqualTo(OrderStatus.FILLED);
    assertThat(adaptedOrder.getTimestamp().getTime()).isEqualTo(new Date(1548674205259L).getTime());
  }

  @Test
  void stopOrder() {
    BitfinexWebSocketAuthOrder bitfinexWebSocketAuthOrder =
        new BitfinexWebSocketAuthOrder(
            123123123L, // id,
            0L, // groupId,
            456456456L, // cid,
            "tBTCUSD", // symbol,
            1548674205259L, // mtsCreateamount
            1548674205259L, // mtsUpdate
            new BigDecimal("0.000"), // amount
            new BigDecimal("0.004"), // amountOrig
            "STOP", // type
            null, // typePrev
            "EXECUTED @ 3495.1(0.004)", // orderStatus
            new BigDecimal("3495.2"), // price
            new BigDecimal("3495.2"), // priceAvg
            BigDecimal.ZERO, // priceTrailing
            BigDecimal.ZERO, // priceAuxLimit
            0, // placedId
            0 // flags
            );

    // TODO awaiting https://github.com/knowm/XChange/pull/2907 then I can add market order
    // support to XChange itself. In the meantime these are returned as limit orders.

    Order adaptedOrder = BitfinexStreamingAdapters.adaptOrder(bitfinexWebSocketAuthOrder);

    assertThat(adaptedOrder.getId()).isEqualTo("123123123");
    assertThat(adaptedOrder.getType()).isEqualTo(Order.OrderType.BID);
    assertThat(adaptedOrder.getAveragePrice()).isEqualTo(new BigDecimal("3495.2"));
    assertThat(adaptedOrder.getCumulativeAmount()).isEqualTo(new BigDecimal("0.004"));
    assertThat(adaptedOrder.getCurrencyPair()).isEqualTo(CurrencyPair.BTC_USD);

    // TODO see above. should be:
    // assertEquals(Collections.singleton(BitfinexOrderFlags.MARGIN), adaptedOrder.getOrderFlags());

    assertThat(adaptedOrder.getOriginalAmount()).isEqualTo(new BigDecimal("0.004"));
    assertThat(adaptedOrder.getRemainingAmount()).isEqualTo(new BigDecimal("0.000"));
    assertThat(adaptedOrder.getStatus()).isEqualTo(OrderStatus.FILLED);
    assertThat(adaptedOrder.getTimestamp().getTime()).isEqualTo(new Date(1548674205259L).getTime());
  }

  @Test
  void newLimitOrder() {
    BitfinexWebSocketAuthOrder bitfinexWebSocketAuthOrder =
        new BitfinexWebSocketAuthOrder(
            123123123L, // id,
            0L, // groupId,
            456456456L, // cid,
            "tBTCUSD", // symbol,
            1548674205259L, // mtsCreateamount
            1548674205267L, // mtsUpdate
            new BigDecimal("0.004"), // amount
            new BigDecimal("0.004"), // amountOrig
            "EXCHANGE LIMIT", // type
            null, // typePrev
            "ACTIVE", // orderStatus
            new BigDecimal("3495.2"), // price
            BigDecimal.ZERO, // priceAvg
            BigDecimal.ZERO, // priceTrailing
            BigDecimal.ZERO, // priceAuxLimit
            0, // placedId
            0 // flags
            );

    LimitOrder adaptedOrder =
        (LimitOrder) BitfinexStreamingAdapters.adaptOrder(bitfinexWebSocketAuthOrder);

    assertThat(adaptedOrder.getId()).isEqualTo("123123123");
    assertThat(adaptedOrder.getType()).isEqualTo(Order.OrderType.BID);
    assertThat(adaptedOrder.getAveragePrice()).isEqualTo(BigDecimal.ZERO);
    assertThat(BigDecimal.ZERO.compareTo(adaptedOrder.getCumulativeAmount())).isEqualTo(0);
    assertThat(adaptedOrder.getCurrencyPair()).isEqualTo(CurrencyPair.BTC_USD);
    assertThat(adaptedOrder.getLimitPrice()).isEqualTo(new BigDecimal("3495.2"));
    assertThat(adaptedOrder.getOrderFlags()).isEqualTo(Collections.emptySet());
    assertThat(adaptedOrder.getOriginalAmount()).isEqualTo(new BigDecimal("0.004"));
    assertThat(adaptedOrder.getRemainingAmount()).isEqualTo(new BigDecimal("0.004"));
    assertThat(adaptedOrder.getStatus()).isEqualTo(OrderStatus.NEW);
    assertThat(adaptedOrder.getTimestamp().getTime()).isEqualTo(new Date(1548674205259L).getTime());
  }

  @Test
  void cancelledLimitOrder() {
    BitfinexWebSocketAuthOrder bitfinexWebSocketAuthOrder =
        new BitfinexWebSocketAuthOrder(
            123123123L, // id,
            0L, // groupId,
            456456456L, // cid,
            "tBTCUSD", // symbol,
            1548674205259L, // mtsCreateamount
            1548674205267L, // mtsUpdate
            new BigDecimal("-0.004"), // amount
            new BigDecimal("-0.004"), // amountOrig
            "LIMIT", // type
            null, // typePrev
            "CANCELED", // orderStatus
            new BigDecimal("3495.2"), // price
            BigDecimal.ZERO, // priceAvg
            BigDecimal.ZERO, // priceTrailing
            BigDecimal.ZERO, // priceAuxLimit
            0, // placedId
            0 // flags
            );

    LimitOrder adaptedOrder =
        (LimitOrder) BitfinexStreamingAdapters.adaptOrder(bitfinexWebSocketAuthOrder);

    assertThat(adaptedOrder.getId()).isEqualTo("123123123");
    assertThat(adaptedOrder.getType()).isEqualTo(Order.OrderType.ASK);
    assertThat(adaptedOrder.getAveragePrice()).isEqualTo(BigDecimal.ZERO);
    assertThat(BigDecimal.ZERO.compareTo(adaptedOrder.getCumulativeAmount())).isEqualTo(0);
    assertThat(adaptedOrder.getCurrencyPair()).isEqualTo(CurrencyPair.BTC_USD);
    assertThat(adaptedOrder.getLimitPrice()).isEqualTo(new BigDecimal("3495.2"));
    assertThat(adaptedOrder.getOriginalAmount()).isEqualTo(new BigDecimal("0.004"));
    assertThat(adaptedOrder.getRemainingAmount()).isEqualTo(new BigDecimal("0.004"));

    // TODO see above. should be:
    // assertEquals(Collections.singleton(BitfinexOrderFlags.MARGIN), adaptedOrder.getOrderFlags());

    assertThat(adaptedOrder.getStatus()).isEqualTo(OrderStatus.CANCELED);
    assertThat(adaptedOrder.getTimestamp().getTime()).isEqualTo(new Date(1548674205259L).getTime());
  }

  @Test
  void partiallyFilledLimitOrder() {
    BitfinexWebSocketAuthOrder bitfinexWebSocketAuthOrder =
        new BitfinexWebSocketAuthOrder(
            123123123L, // id,
            0L, // groupId,
            456456456L, // cid,
            "tBTCUSD", // symbol,
            1548674205259L, // mtsCreateamount
            1548674205267L, // mtsUpdate
            new BigDecimal("-0.001"), // amount
            new BigDecimal("-0.004"), // amountOrig
            "LIMIT", // type
            null, // typePrev
            "PARTIALLY FILLED @ 3495.1(0.003)", // orderStatus
            new BigDecimal("3495.2"), // price
            new BigDecimal("3495.1"), // priceAvg
            BigDecimal.ZERO, // priceTrailing
            BigDecimal.ZERO, // priceAuxLimit
            0, // placedId
            0 // flags
            );

    LimitOrder adaptedOrder =
        (LimitOrder) BitfinexStreamingAdapters.adaptOrder(bitfinexWebSocketAuthOrder);

    assertThat(adaptedOrder.getId()).isEqualTo("123123123");
    assertThat(adaptedOrder.getType()).isEqualTo(Order.OrderType.ASK);
    assertThat(adaptedOrder.getAveragePrice()).isEqualTo(new BigDecimal("3495.1"));
    assertThat(adaptedOrder.getCumulativeAmount()).isEqualTo(new BigDecimal("0.003"));
    assertThat(adaptedOrder.getCurrencyPair()).isEqualTo(CurrencyPair.BTC_USD);
    assertThat(adaptedOrder.getLimitPrice()).isEqualTo(new BigDecimal("3495.2"));
    assertThat(adaptedOrder.getOriginalAmount()).isEqualTo(new BigDecimal("0.004"));
    assertThat(adaptedOrder.getRemainingAmount()).isEqualTo(new BigDecimal("0.001"));

    // TODO see above. should be:
    // assertEquals(Collections.singleton(BitfinexOrderFlags.MARGIN), adaptedOrder.getOrderFlags());

    assertThat(adaptedOrder.getStatus()).isEqualTo(OrderStatus.PARTIALLY_FILLED);
    assertThat(adaptedOrder.getTimestamp().getTime()).isEqualTo(new Date(1548674205259L).getTime());
  }

  @Test
  void executedLimitOrder() {
    BitfinexWebSocketAuthOrder bitfinexWebSocketAuthOrder =
        new BitfinexWebSocketAuthOrder(
            123123123L, // id,
            0L, // groupId,
            456456456L, // cid,
            "tBTCUSD", // symbol,
            1548674205259L, // mtsCreateamount
            1548674205267L, // mtsUpdate
            BigDecimal.ZERO, // amount
            new BigDecimal("0.004"), // amountOrig
            "EXCHANGE LIMIT", // type
            null, // typePrev
            "EXECUTED @ 3495.1(0.004): was PARTIALLY FILLED @ 3495.1(0.003)", // orderStatus
            new BigDecimal("3495.2"), // price
            new BigDecimal("3495.1"), // priceAvg
            BigDecimal.ZERO, // priceTrailing
            BigDecimal.ZERO, // priceAuxLimit
            0, // placedId
            0 // flags
            );

    LimitOrder adaptedOrder =
        (LimitOrder) BitfinexStreamingAdapters.adaptOrder(bitfinexWebSocketAuthOrder);

    assertThat(adaptedOrder.getId()).isEqualTo("123123123");
    assertThat(adaptedOrder.getType()).isEqualTo(Order.OrderType.BID);
    assertThat(adaptedOrder.getAveragePrice()).isEqualTo(new BigDecimal("3495.1"));
    assertThat(adaptedOrder.getCumulativeAmount()).isEqualTo(new BigDecimal("0.004"));
    assertThat(adaptedOrder.getCurrencyPair()).isEqualTo(CurrencyPair.BTC_USD);
    assertThat(adaptedOrder.getLimitPrice()).isEqualTo(new BigDecimal("3495.2"));
    assertThat(adaptedOrder.getOriginalAmount()).isEqualTo(new BigDecimal("0.004"));
    assertThat(adaptedOrder.getRemainingAmount()).isEqualTo(new BigDecimal("0.000"));
    assertThat(adaptedOrder.getOrderFlags()).isEqualTo(Collections.emptySet());
    assertThat(adaptedOrder.getStatus()).isEqualTo(OrderStatus.FILLED);
    assertThat(adaptedOrder.getTimestamp().getTime()).isEqualTo(new Date(1548674205259L).getTime());
  }

  @Test
  void tradeBuy() {
    BitfinexWebSocketAuthTrade bitfinexWebSocketAuthTrade =
        new BitfinexWebSocketAuthTrade(
            335015622L, // id
            "tBTCUSD", // pair
            1548674247684L, // mtsCreate
            21895093123L, // orderId
            new BigDecimal("0.00341448"), // execAmount
            new BigDecimal("3495.4"), // execPrice
            "SHOULDNT MATTER", // orderType
            new BigDecimal("3495.9"), // orderPrice
            1548674247683L, // maker
            new BigDecimal("-0.00000682896"), // fee
            "BTC" // feeCurrency
            );
    UserTrade adapted = BitfinexStreamingAdapters.adaptUserTrade(bitfinexWebSocketAuthTrade);
    assertThat(adapted.getInstrument()).isEqualTo(CurrencyPair.BTC_USD);
    assertThat(adapted.getFeeAmount()).isEqualTo(new BigDecimal("0.00000682896"));
    assertThat(adapted.getFeeCurrency()).isEqualTo(CurrencyPair.BTC_USD.getBase());
    assertThat(adapted.getId()).isEqualTo("335015622");
    assertThat(adapted.getOrderId()).isEqualTo("21895093123");
    assertThat(adapted.getOriginalAmount()).isEqualTo(new BigDecimal("0.00341448"));
    assertThat(adapted.getPrice()).isEqualTo(new BigDecimal("3495.4"));
    assertThat(adapted.getTimestamp().getTime()).isEqualTo(new Date(1548674247684L).getTime());
    assertThat(adapted.getType()).isEqualTo(OrderType.BID);
  }

  @Test
  void tradeSell() {
    BitfinexWebSocketAuthTrade bitfinexWebSocketAuthTrade =
        new BitfinexWebSocketAuthTrade(
            335015622L, // id
            "tBTCUSD", // pair
            1548674247684L, // mtsCreate
            21895093123L, // orderId
            new BigDecimal("-0.00341448"), // execAmount
            new BigDecimal("3495.4"), // execPrice
            "SHOULDNT MATTER", // orderType
            new BigDecimal("3495.9"), // orderPrice
            1548674247683L, // maker
            new BigDecimal("0.00000682896"), // fee
            "BTC" // feeCurrency
            );
    UserTrade adapted = BitfinexStreamingAdapters.adaptUserTrade(bitfinexWebSocketAuthTrade);
    assertThat(adapted.getInstrument()).isEqualTo(CurrencyPair.BTC_USD);
    assertThat(adapted.getFeeAmount()).isEqualTo(new BigDecimal("0.00000682896"));
    assertThat(adapted.getFeeCurrency()).isEqualTo(CurrencyPair.BTC_USD.getBase());
    assertThat(adapted.getId()).isEqualTo("335015622");
    assertThat(adapted.getOrderId()).isEqualTo("21895093123");
    assertThat(adapted.getOriginalAmount()).isEqualTo(new BigDecimal("0.00341448"));
    assertThat(adapted.getPrice()).isEqualTo(new BigDecimal("3495.4"));
    assertThat(adapted.getTimestamp().getTime()).isEqualTo(new Date(1548674247684L).getTime());
    assertThat(adapted.getType()).isEqualTo(OrderType.ASK);
  }
}
