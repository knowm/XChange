package org.knowm.xchange.gateio.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.gateio.GateioExchangeWiremock;

public class GateioMarketDataServiceTest extends GateioExchangeWiremock {

  GateioMarketDataService gateioMarketDataService = (GateioMarketDataService) exchange.getMarketDataService();


  @Test
  void getOrderBook_valid() throws IOException {
    OrderBook actual = gateioMarketDataService.getOrderBook(CurrencyPair.BTC_USDT);

    List<LimitOrder> expectedAsks = new ArrayList<>();
    expectedAsks.add(new LimitOrder.Builder(OrderType.ASK, CurrencyPair.BTC_USDT)
        .limitPrice(new BigDecimal("200"))
        .originalAmount(BigDecimal.ONE)
        .build());
    expectedAsks.add(new LimitOrder.Builder(OrderType.ASK, CurrencyPair.BTC_USDT)
        .limitPrice(new BigDecimal("250"))
        .originalAmount(BigDecimal.TEN)
        .build());

    List<LimitOrder> expectedBids = new ArrayList<>();
    expectedBids.add(new LimitOrder.Builder(OrderType.BID, CurrencyPair.BTC_USDT)
        .limitPrice(new BigDecimal("150"))
        .originalAmount(BigDecimal.ONE)
        .build());
    expectedBids.add(new LimitOrder.Builder(OrderType.BID, CurrencyPair.BTC_USDT)
        .limitPrice(new BigDecimal("100"))
        .originalAmount(BigDecimal.TEN)
        .build());
    Date expectedTimestamp = Date.from(Instant.parse("2023-05-14T22:10:10.493Z"));

    OrderBook expected = new OrderBook(expectedTimestamp, expectedAsks, expectedBids);

    assertThat(actual)
        .usingRecursiveComparison()
        .ignoringFieldsMatchingRegexes(".*userReference")
        .isEqualTo(expected);
  }


  @Test
  void getTicker_valid() throws IOException {
    Ticker actual = gateioMarketDataService.getTicker(CurrencyPair.BTC_USDT);

    Ticker expected = new Ticker.Builder()
            .instrument(CurrencyPair.BTC_USDT)
            .last(new BigDecimal("26028.7"))
            .ask(new BigDecimal("26026.8"))
            .bid(new BigDecimal("26026.7"))
            .high(new BigDecimal("26202.4"))
            .low(new BigDecimal("25606.3"))
            .volume(new BigDecimal("4726.066213526"))
            .quoteVolume(new BigDecimal("122407105.4829"))
            .percentageChange(new BigDecimal("1.1"))
            .build();

    assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
  }


  @Test
  void getTickers_valid() throws IOException {
    List<Ticker> actual = gateioMarketDataService.getTickers(null);

    assertThat(actual).hasSize(2);
  }


  @Test
  void getCurrencies_valid() throws IOException {
    List<Currency> actual = gateioMarketDataService.getCurrencies();

    assertThat(actual).containsOnly(Currency.BTC, Currency.ETH);
  }


  @Test
  void getCurrencyPairs_valid() throws IOException {
    List<CurrencyPair> actual = gateioMarketDataService.getCurrencyPairs();

    assertThat(actual).containsOnly(CurrencyPair.BTC_USDT, CurrencyPair.ETH_USDT, new CurrencyPair("CHZ/USDT"));
  }


}
