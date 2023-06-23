package org.knowm.xchange.gateio.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.gateio.GateioExchangeWiremock;

public class GateioMarketDataServiceTest extends GateioExchangeWiremock {

  GateioMarketDataService gateioMarketDataService = (GateioMarketDataService) exchange.getMarketDataService();


  @Test
  public void valid_orderbook() throws IOException {
    OrderBook actual = gateioMarketDataService.getOrderBook(CurrencyPair.BTC_USDT);

    List<LimitOrder> expectedAsks = new ArrayList<>();
    expectedAsks.add(new LimitOrder.Builder(OrderType.ASK, CurrencyPair.BTC_USDT)
        .id("")
        .limitPrice(new BigDecimal("200"))
        .originalAmount(BigDecimal.ONE)
        .build());
    expectedAsks.add(new LimitOrder.Builder(OrderType.ASK, CurrencyPair.BTC_USDT)
        .id("")
        .limitPrice(new BigDecimal("250"))
        .originalAmount(BigDecimal.TEN)
        .build());

    List<LimitOrder> expectedBids = new ArrayList<>();
    expectedBids.add(new LimitOrder.Builder(OrderType.BID, CurrencyPair.BTC_USDT)
        .id("")
        .limitPrice(new BigDecimal("150"))
        .originalAmount(BigDecimal.ONE)
        .build());
    expectedBids.add(new LimitOrder.Builder(OrderType.BID, CurrencyPair.BTC_USDT)
        .id("")
        .limitPrice(new BigDecimal("100"))
        .originalAmount(BigDecimal.TEN)
        .build());
//    Date expectedTimestamp = Date.from(Instant.parse("2023-05-14T22:10:10.493Z"));

    OrderBook expected = new OrderBook(null, expectedAsks, expectedBids);

    assertThat(actual)
        .usingRecursiveComparison()
        .ignoringFieldsMatchingRegexes(".*userReference")
        .isEqualTo(expected);
  }
}