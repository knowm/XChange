package org.knowm.xchange.bittrex.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.bittrex.BittrexExchange;
import org.knowm.xchange.bittrex.BittrexUtils;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.trade.LimitOrder;

/** @author walec51 */
public class MarketDataTestIntegration {

  private static BittrexMarketDataService marketDataService;

  @BeforeClass
  public static void setUp() {
    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(BittrexExchange.class.getName());
    marketDataService = (BittrexMarketDataService) exchange.getMarketDataService();
  }

  @Test
  public void tickerTest() throws Exception {
    Ticker ticker = marketDataService.getTicker(CurrencyPair.ETH_BTC);
    System.out.println(ticker.toString());
    assertThat(ticker).isNotNull();
    assertThat(ticker.getLast()).isNotNull().isPositive();
    assertThat(ticker.getQuoteVolume()).isNotNull().isPositive();
    assertThat(ticker.getVolume()).isNotNull().isPositive();
    assertThat(ticker.getHigh()).isNotNull().isPositive();
  }

  @Test
  public void orderBooksTest() throws Exception {
    OrderBook orderBook = marketDataService.getOrderBook(CurrencyPair.ETH_BTC);
    List<LimitOrder> asks = orderBook.getAsks();
    assertThat(asks).isNotEmpty();
    LimitOrder firstAsk = asks.get(0);
    assertThat(firstAsk.getLimitPrice()).isNotNull().isPositive();
    assertThat(firstAsk.getRemainingAmount()).isNotNull().isPositive();
  }

  @Test
  public void sequencedOrderBookTest() throws Exception {
    BittrexMarketDataServiceRaw.SequencedOrderBook sequencedOrderBook =
        marketDataService.getBittrexSequencedOrderBook(
            BittrexUtils.toPairString(CurrencyPair.ETH_BTC), 500);
    List<LimitOrder> asks = sequencedOrderBook.getOrderBook().getAsks();
    assertThat(asks).isNotEmpty();
    assertThat(sequencedOrderBook.getSequence().length()).isGreaterThan(1);
    LimitOrder firstAsk = asks.get(0);
    assertThat(firstAsk.getLimitPrice()).isNotNull().isPositive();
    assertThat(firstAsk.getRemainingAmount()).isNotNull().isPositive();
  }
}
