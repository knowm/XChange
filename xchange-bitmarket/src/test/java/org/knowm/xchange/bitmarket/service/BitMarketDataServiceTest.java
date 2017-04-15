package org.knowm.xchange.bitmarket.service;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.powermock.api.mockito.PowerMockito.mock;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bitmarket.BitMarket;
import org.knowm.xchange.bitmarket.BitMarketAssert;
import org.knowm.xchange.bitmarket.BitMarketExchange;
import org.knowm.xchange.bitmarket.BitMarketTestSupport;
import org.knowm.xchange.bitmarket.dto.marketdata.BitMarketOrderBook;
import org.knowm.xchange.bitmarket.dto.marketdata.BitMarketTicker;
import org.knowm.xchange.bitmarket.dto.marketdata.BitMarketTrade;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

@RunWith(PowerMockRunner.class)
public class BitMarketDataServiceTest extends BitMarketTestSupport {

  private BitMarketDataService dataService;

  @Before
  public void setUp() {
    BitMarketExchange exchange = (BitMarketExchange) ExchangeFactory.INSTANCE.createExchange(BitMarketExchange.class.getCanonicalName());
    ExchangeSpecification specification = exchange.getExchangeSpecification();
    specification.setUserName(SPECIFICATION_USERNAME);
    specification.setApiKey(SPECIFICATION_API_KEY);
    specification.setSecretKey(SPECIFICATION_SECRET_KEY);

    dataService = new BitMarketDataService(exchange);
  }

  @Test
  public void constructor() {
    assertThat(Whitebox.getInternalState(dataService, "apiKey")).isEqualTo(SPECIFICATION_API_KEY);
  }

  @Test
  public void shouldGetTicker() throws IOException {
    // given
    BitMarketTicker response = parse("marketdata/example-ticker-data", BitMarketTicker.class);

    BitMarket bitMarket = mock(BitMarket.class);
    PowerMockito.when(bitMarket.getTicker("BTCAUD")).thenReturn(response);
    Whitebox.setInternalState(dataService, "bitMarket", bitMarket);

    // when
    Ticker ticker = dataService.getTicker(CurrencyPair.BTC_AUD);

    // then
    BitMarketAssert.assertEquals(ticker, TICKER);
  }

  @Test
  public void shouldGetTrades() throws IOException {
    // given
    final Trade[] expectedTrades = expectedTrades();
    BitMarketTrade[] response = parse("marketdata/example-trades-data", BitMarketTrade[].class);

    BitMarket bitMarket = mock(BitMarket.class);
    PowerMockito.when(bitMarket.getTrades("BTCAUD")).thenReturn(response);
    Whitebox.setInternalState(dataService, "bitMarket", bitMarket);

    // when
    Trades trades = dataService.getTrades(CurrencyPair.BTC_AUD);
    List<Trade> tradeList = trades.getTrades();

    // then
    assertThat(tradeList).hasSize(3);
    for (int i = 0; i < tradeList.size(); i++) {
      BitMarketAssert.assertEquals(tradeList.get(i), expectedTrades[i]);
    }
  }

  @Test
  public void shouldGetOrderBook() throws IOException {
    // given
    BitMarketOrderBook response = parse("marketdata/example-order-book-data", BitMarketOrderBook.class);

    BitMarket bitMarket = mock(BitMarket.class);
    PowerMockito.when(bitMarket.getOrderBook("BTCAUD")).thenReturn(response);
    Whitebox.setInternalState(dataService, "bitMarket", bitMarket);

    // when
    OrderBook orderBook = dataService.getOrderBook(CurrencyPair.BTC_AUD);

    // then
    BitMarketAssert.assertEquals(orderBook, ORDER_BOOK);
  }
}
