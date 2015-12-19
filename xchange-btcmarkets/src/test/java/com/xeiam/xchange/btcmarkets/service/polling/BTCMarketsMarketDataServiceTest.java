package com.xeiam.xchange.btcmarkets.service.polling;

import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.btcmarkets.BTCMarkets;
import com.xeiam.xchange.btcmarkets.BTCMarketsExchange;
import com.xeiam.xchange.btcmarkets.dto.marketdata.BTCMarketsOrderBook;
import com.xeiam.xchange.btcmarkets.dto.marketdata.BTCMarketsTicker;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.exceptions.NotYetImplementedForExchangeException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.util.reflection.Whitebox;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.Assertions.fail;
import static org.powermock.api.mockito.PowerMockito.mock;

@RunWith(PowerMockRunner.class)
@PrepareForTest(BTCMarkets.class)
public class BTCMarketsMarketDataServiceTest {

  private BTCMarketsMarketDataService marketDataService;

  @Before
  public void setUp() {
    BTCMarketsExchange exchange = (BTCMarketsExchange) ExchangeFactory.INSTANCE.createExchange(BTCMarketsExchange.class.getCanonicalName());
    ExchangeSpecification specification = exchange.getExchangeSpecification();
    specification.setUserName("admin");
    specification.setApiKey("publicKey");
    specification.setSecretKey("secretKey");

    marketDataService = new BTCMarketsMarketDataService(exchange);
  }

  @Test
  public void shouldGetTicker() throws IOException {
    // given
    BTCMarketsTicker tickerMock = new BTCMarketsTicker(
        new BigDecimal("10.00000000"),
        new BigDecimal("20.00000000"),
        new BigDecimal("30.00000000"),
        "AUD",
        "BTC",
        new Date(1234567890L)
    );

    BTCMarkets btcmarkets = mock(BTCMarkets.class);
    PowerMockito.when(btcmarkets.getTicker("BTC", "AUD")).thenReturn(tickerMock);
    Whitebox.setInternalState(marketDataService, "btcmarkets", btcmarkets);

    // when
    Ticker ticker = marketDataService.getTicker(CurrencyPair.BTC_AUD);

    // then
    assertThat(ticker.getCurrencyPair()).isEqualTo(CurrencyPair.BTC_AUD);
    assertThat(ticker.getBid()).isEqualTo(new BigDecimal("10.00000000"));
    assertThat(ticker.getAsk()).isEqualTo(new BigDecimal("20.00000000"));
    assertThat(ticker.getLast()).isEqualTo(new BigDecimal("30.00000000"));
    assertThat(ticker.getTimestamp().getTime()).isEqualTo(1234567890L);
  }

  @Test
  public void shouldGetOrderBook() throws IOException {
    // given
    BTCMarketsOrderBook orderBookMock = new BTCMarketsOrderBook();
    Whitebox.setInternalState(orderBookMock, "currency", "AUD");
    Whitebox.setInternalState(orderBookMock, "instrument", "BTC");
    Whitebox.setInternalState(orderBookMock, "timestamp", new Date(1234567890L));
    Whitebox.setInternalState(orderBookMock, "bids", Arrays.asList(
        new BigDecimal[]{new BigDecimal("11.000"), new BigDecimal("21.000"), new BigDecimal("31.000")},
        new BigDecimal[]{new BigDecimal("12.000"), new BigDecimal("22.000"), new BigDecimal("32.000")},
        new BigDecimal[]{new BigDecimal("13.000"), new BigDecimal("23.000"), new BigDecimal("33.000")}
        ));
    Whitebox.setInternalState(orderBookMock, "asks", Arrays.asList(
        new BigDecimal[]{new BigDecimal("41.000"), new BigDecimal("51.000"), new BigDecimal("61.000")},
        new BigDecimal[]{new BigDecimal("42.000"), new BigDecimal("52.000"), new BigDecimal("62.000")}
    ));

    BTCMarkets btcmarkets = mock(BTCMarkets.class);
    PowerMockito.when(btcmarkets.getOrderBook("BTC", "AUD")).thenReturn(orderBookMock);
    Whitebox.setInternalState(marketDataService, "btcmarkets", btcmarkets);

    // when
    OrderBook orderBook = marketDataService.getOrderBook(CurrencyPair.BTC_AUD);

    // then
    assertThat(orderBook.getTimeStamp().getTime()).isEqualTo(1234567890L);

    List<LimitOrder> asks = orderBook.getAsks();
    assertThat(asks).hasSize(2);
    assertThat(asks.get(0).getLimitPrice()).isEqualTo(new BigDecimal("41.000"));
    assertThat(asks.get(1).getLimitPrice()).isEqualTo(new BigDecimal("42.000"));

    List<LimitOrder> bids = orderBook.getBids();
    assertThat(bids).hasSize(3);
    assertThat(bids.get(0).getLimitPrice()).isEqualTo(new BigDecimal("13.000"));
    assertThat(bids.get(1).getLimitPrice()).isEqualTo(new BigDecimal("12.000"));
    assertThat(bids.get(2).getLimitPrice()).isEqualTo(new BigDecimal("11.000"));
  }

  @Test(expected = NotYetImplementedForExchangeException.class)
  public void shouldFailWhenGetTrades() throws IOException {
    // when
    marketDataService.getTrades(CurrencyPair.BTC_AUD);

    // then
    fail("BTCMarketsMarketDataService should throw NotYetImplementedForExchangeException when call getTrades");
  }

}
