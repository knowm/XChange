package org.knowm.xchange.deribit.v2.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assumptions.assumeThat;

import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.deribit.DeribitIntegrationTestParent;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.instrument.Instrument;

public class DeribitMarketDataServiceIntegration extends DeribitIntegrationTestParent {

  @Test
  void valid_currencies() throws IOException {
    List<Currency> currencies =
        ((DeribitMarketDataService) exchange.getMarketDataService()).getCurrencies();

    assertThat(currencies).isNotEmpty();
    assertThat(currencies.stream().distinct().count()).isEqualTo(currencies.size());
  }

  @Test
  void valid_instruments() throws IOException {
    List<Instrument> instruments =
        ((DeribitMarketDataService) exchange.getMarketDataService()).getInstruments();

    assertThat(instruments).isNotEmpty();
    assertThat(instruments.stream().distinct().count()).isEqualTo(instruments.size());
  }

  @Test
  void valid_orderbook() throws IOException {
    OrderBook orderBook = exchange.getMarketDataService().getOrderBook(CurrencyPair.BTC_USDT);

    assertThat(orderBook.getBids()).isNotEmpty();
    assertThat(orderBook.getAsks()).isNotEmpty();

    assertThat(orderBook.getAsks().get(0).getLimitPrice())
        .isGreaterThan(orderBook.getBids().get(0).getLimitPrice());

    assertThat(orderBook.getBids())
        .allSatisfy(
            limitOrder -> {
              assertThat(limitOrder.getInstrument()).isEqualTo(CurrencyPair.BTC_USDT);
              assertThat(limitOrder.getType()).isEqualTo(OrderType.BID);
            });

    assertThat(orderBook.getAsks())
        .allSatisfy(
            limitOrder -> {
              assertThat(limitOrder.getInstrument()).isEqualTo(CurrencyPair.BTC_USDT);
              assertThat(limitOrder.getType()).isEqualTo(OrderType.ASK);
            });
  }

  @Test
  void valid_orderbook_futures() throws IOException {
    FuturesContract futuresContract = new FuturesContract(CurrencyPair.BTC_USD, "PERPETUAL");

    OrderBook orderBook = exchange.getMarketDataService().getOrderBook(futuresContract);

    assertThat(orderBook.getBids()).isNotEmpty();
    assertThat(orderBook.getAsks()).isNotEmpty();

    assertThat(orderBook.getAsks().get(0).getLimitPrice())
        .isGreaterThan(orderBook.getBids().get(0).getLimitPrice());

    assertThat(orderBook.getBids())
        .allSatisfy(
            limitOrder -> {
              assertThat(limitOrder.getInstrument()).isEqualTo(futuresContract);
              assertThat(limitOrder.getType()).isEqualTo(OrderType.BID);
            });

    assertThat(orderBook.getAsks())
        .allSatisfy(
            limitOrder -> {
              assertThat(limitOrder.getInstrument()).isEqualTo(futuresContract);
              assertThat(limitOrder.getType()).isEqualTo(OrderType.ASK);
            });
  }

  @Test
  void valid_single_ticker() throws IOException {
    Ticker ticker = exchange.getMarketDataService().getTicker(CurrencyPair.BTC_USDT);

    assertThat(ticker.getInstrument()).isEqualTo(CurrencyPair.BTC_USDT);
    assertThat(ticker.getLast()).isPositive();

    if (ticker.getBid().signum() > 0 && ticker.getAsk().signum() > 0) {
      assertThat(ticker.getBid()).isLessThan(ticker.getAsk());
    }
  }

  @Test
  void valid_single_ticker_futures() throws IOException {
    Ticker ticker =
        exchange
            .getMarketDataService()
            .getTicker(new FuturesContract(CurrencyPair.BTC_USD, "PERPETUAL"));

    assertThat(ticker.getInstrument())
        .isEqualTo(new FuturesContract(CurrencyPair.BTC_USD, "PERPETUAL"));
    assertThat(ticker.getLast()).isPositive();

    if (ticker.getBid().signum() > 0 && ticker.getAsk().signum() > 0) {
      assertThat(ticker.getBid()).isLessThan(ticker.getAsk());
    }
  }

  @Test
  void valid_trades() throws IOException {
    Trades trades = exchange.getMarketDataService().getTrades(CurrencyPair.BTC_USDT);

    assumeThat(trades.getTrades()).isNotEmpty();

    assertThat(trades.getTrades())
        .allSatisfy(
            trade -> {
              assertThat(trade.getInstrument()).isEqualTo(CurrencyPair.BTC_USDT);
              assertThat(trade.getPrice()).isPositive();
              assertThat(trade.getOriginalAmount()).isPositive();
              assertThat(trade).hasNoNullFieldsOrPropertiesExcept("makerOrderId", "takerOrderId");
            });
  }

  @Test
  void valid_trades_futures() throws IOException {
    FuturesContract futuresContract = new FuturesContract(CurrencyPair.BTC_USD, "PERPETUAL");
    Trades trades = exchange.getMarketDataService().getTrades(futuresContract);

    assumeThat(trades.getTrades()).isNotEmpty();

    assertThat(trades.getTrades())
        .allSatisfy(
            trade -> {
              assertThat(trade.getInstrument()).isEqualTo(futuresContract);
              assertThat(trade.getPrice()).isPositive();
              assertThat(trade.getOriginalAmount()).isPositive();
              assertThat(trade).hasNoNullFieldsOrPropertiesExcept("makerOrderId", "takerOrderId");
            });
  }
}
