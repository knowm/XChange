package com.xeiam.xchange.bitmarket.service.polling;

import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.bitmarket.BitMarket;
import com.xeiam.xchange.bitmarket.BitMarketExchange;
import com.xeiam.xchange.bitmarket.dto.marketdata.BitMarketOrderBook;
import com.xeiam.xchange.bitmarket.dto.marketdata.BitMarketTicker;
import com.xeiam.xchange.bitmarket.dto.marketdata.BitMarketTrade;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.powermock.api.mockito.PowerMockito.mock;

@RunWith(PowerMockRunner.class)
public class BitMarketDataServiceTest {

  private BitMarketDataService dataService;

  @Before public void setUp() throws Exception {
    BitMarketExchange exchange = (BitMarketExchange) ExchangeFactory.INSTANCE.createExchange(BitMarketExchange.class.getCanonicalName());
    exchange.getExchangeSpecification().setUserName("admin");
    exchange.getExchangeSpecification().setApiKey("publicKey");
    exchange.getExchangeSpecification().setSecretKey("secretKey");

    dataService = new BitMarketDataService(exchange);
  }

  @Test public void constructor() throws Exception {
    assertThat(Whitebox.getInternalState(dataService, "apiKey")).isEqualTo("publicKey");
  }

  @Test public void shouldGetTicker() throws Exception {
    // given
    BitMarketTicker response = new BitMarketTicker(new BigDecimal("1794.5000"), new BigDecimal("1789.2301"), new BigDecimal("1789.2001"), new BigDecimal("1756.5000"), new BigDecimal("1813.5000"),
        new BigDecimal("1785.8484"), new BigDecimal("455.69192487"));

    BitMarket bitMarket = mock(BitMarket.class);
    PowerMockito.when(bitMarket.getTicker("BTCAUD")).thenReturn(response);
    Whitebox.setInternalState(dataService, "bitMarket", bitMarket);

    // when
    Ticker ticker = dataService.getTicker(CurrencyPair.BTC_AUD);

    // then
    assertThat(ticker.toString())
        .isEqualTo("Ticker [currencyPair=BTC/AUD, last=1789.2001, bid=1789.2301, " + "ask=1794.5000, high=1813.5000, low=1756.5000,avg=null, volume=455.69192487, timestamp=null]");
  }

  @Test public void shouldGetTrades() throws IOException {
    // given
    BitMarketTrade[] response = new BitMarketTrade[] { new BitMarketTrade("78455", new BigDecimal("14.6900"), new BigDecimal("27.24579867"), 1450344119),
        new BitMarketTrade("78454", new BigDecimal("14.4105"), new BigDecimal("5.22284399"), 1450343831),
        new BitMarketTrade("78453", new BigDecimal("14.4105"), new BigDecimal("0.10560487"), 1450303414) };

    BitMarket bitMarket = mock(BitMarket.class);
    PowerMockito.when(bitMarket.getTrades("BTCAUD")).thenReturn(response);
    Whitebox.setInternalState(dataService, "bitMarket", bitMarket);

    // when
    Trades trades = dataService.getTrades(CurrencyPair.BTC_AUD);

    // then
    assertThat(trades.getTrades()).hasSize(3);
    assertThat(trades.getTrades().get(0).toString()).isEqualTo("Trade [type=BID, tradableAmount=0.10560487, currencyPair=BTC/AUD, price=14.4105, timestamp=Sat Jan 17 21:51:43 MSK 1970, id=78453]");
    assertThat(trades.getTrades().get(1).toString()).isEqualTo("Trade [type=BID, tradableAmount=5.22284399, currencyPair=BTC/AUD, price=14.4105, timestamp=Sat Jan 17 21:52:23 MSK 1970, id=78454]");
    assertThat(trades.getTrades().get(2).toString()).isEqualTo("Trade [type=BID, tradableAmount=27.24579867, currencyPair=BTC/AUD, price=14.6900, timestamp=Sat Jan 17 21:52:24 MSK 1970, id=78455]");
  }

  @Test public void shouldGetOrderBook() throws Exception {
    // given
    BitMarketOrderBook response = new BitMarketOrderBook(
        new BigDecimal[][] { new BigDecimal[] { new BigDecimal("14.6999"), new BigDecimal("20.47") }, new BigDecimal[] { new BigDecimal("14.7"), new BigDecimal("10.06627287") } },
        new BigDecimal[][] { new BigDecimal[] { new BigDecimal("14.4102"), new BigDecimal("1.55") }, new BigDecimal[] { new BigDecimal("14.4101"), new BigDecimal("27.77224019") },
            new BigDecimal[] { new BigDecimal("0"), new BigDecimal("52669.33019064") } });

    BitMarket bitMarket = mock(BitMarket.class);
    PowerMockito.when(bitMarket.getOrderBook("BTCAUD")).thenReturn(response);
    Whitebox.setInternalState(dataService, "bitMarket", bitMarket);

    // when
    OrderBook orderBook = dataService.getOrderBook(CurrencyPair.BTC_AUD);

    // then
    assertThat(orderBook.getAsks()).hasSize(2);
    assertThat(orderBook.getAsks().get(0).getType()).isEqualTo(Order.OrderType.ASK);
    assertThat(orderBook.getAsks().get(0).getCurrencyPair()).isEqualTo(CurrencyPair.BTC_AUD);
    assertThat(orderBook.getAsks().get(0).getLimitPrice()).isEqualTo(new BigDecimal("14.6999"));
    assertThat(orderBook.getAsks().get(0).getTradableAmount()).isEqualTo(new BigDecimal("20.47"));
    assertThat(orderBook.getAsks().get(1).getType()).isEqualTo(Order.OrderType.ASK);
    assertThat(orderBook.getAsks().get(1).getCurrencyPair()).isEqualTo(CurrencyPair.BTC_AUD);
    assertThat(orderBook.getAsks().get(1).getLimitPrice()).isEqualTo(new BigDecimal("14.7"));
    assertThat(orderBook.getAsks().get(1).getTradableAmount()).isEqualTo(new BigDecimal("10.06627287"));

    assertThat(orderBook.getBids()).hasSize(3);
    assertThat(orderBook.getBids().get(0).getType()).isEqualTo(Order.OrderType.BID);
    assertThat(orderBook.getBids().get(0).getCurrencyPair()).isEqualTo(CurrencyPair.BTC_AUD);
    assertThat(orderBook.getBids().get(0).getLimitPrice()).isEqualTo(new BigDecimal("14.4102"));
    assertThat(orderBook.getBids().get(0).getTradableAmount()).isEqualTo(new BigDecimal("1.55"));
    assertThat(orderBook.getBids().get(1).getType()).isEqualTo(Order.OrderType.BID);
    assertThat(orderBook.getBids().get(1).getCurrencyPair()).isEqualTo(CurrencyPair.BTC_AUD);
    assertThat(orderBook.getBids().get(1).getLimitPrice()).isEqualTo(new BigDecimal("14.4101"));
    assertThat(orderBook.getBids().get(1).getTradableAmount()).isEqualTo(new BigDecimal("27.77224019"));
    assertThat(orderBook.getBids().get(2).getType()).isEqualTo(Order.OrderType.BID);
    assertThat(orderBook.getBids().get(2).getCurrencyPair()).isEqualTo(CurrencyPair.BTC_AUD);
    assertThat(orderBook.getBids().get(2).getLimitPrice()).isEqualTo(new BigDecimal("0"));
    assertThat(orderBook.getBids().get(2).getTradableAmount()).isEqualTo(new BigDecimal("52669.33019064"));
  }
}
