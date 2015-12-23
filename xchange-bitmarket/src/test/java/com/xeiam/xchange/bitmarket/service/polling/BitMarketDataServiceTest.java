package com.xeiam.xchange.bitmarket.service.polling;

import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bitmarket.BitMarket;
import com.xeiam.xchange.bitmarket.BitMarketCompareUtils;
import com.xeiam.xchange.bitmarket.BitMarketExchange;
import com.xeiam.xchange.bitmarket.BitMarketTestSupport;
import com.xeiam.xchange.bitmarket.dto.marketdata.BitMarketOrderBook;
import com.xeiam.xchange.bitmarket.dto.marketdata.BitMarketTicker;
import com.xeiam.xchange.bitmarket.dto.marketdata.BitMarketTrade;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trade;
import com.xeiam.xchange.dto.marketdata.Trades;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.io.IOException;
import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.powermock.api.mockito.PowerMockito.mock;

@RunWith(PowerMockRunner.class)
public class BitMarketDataServiceTest extends BitMarketTestSupport {

  private BitMarketDataService dataService;

  @Before public void setUp() {
    BitMarketExchange exchange = (BitMarketExchange) ExchangeFactory.INSTANCE.createExchange(BitMarketExchange.class.getCanonicalName());
    ExchangeSpecification specification = exchange.getExchangeSpecification();
    specification.setUserName(SPECIFICATION_USERNAME);
    specification.setApiKey(SPECIFICATION_API_KEY);
    specification.setSecretKey(SPECIFICATION_SECRET_KEY);

    dataService = new BitMarketDataService(exchange);
  }

  @Test public void constructor() {
    assertThat(Whitebox.getInternalState(dataService, "apiKey")).isEqualTo(SPECIFICATION_API_KEY);
  }

  @Test public void shouldGetTicker() throws IOException {
    // given
    BitMarketTicker response = parse("marketdata/example-ticker-data", BitMarketTicker.class);

    BitMarket bitMarket = mock(BitMarket.class);
    PowerMockito.when(bitMarket.getTicker("BTCAUD")).thenReturn(response);
    Whitebox.setInternalState(dataService, "bitMarket", bitMarket);

    // when
    Ticker ticker = dataService.getTicker(CurrencyPair.BTC_AUD);

    // then
    BitMarketCompareUtils.compareTickers(ticker, TICKER);
  }

  @Test public void shouldGetTrades() throws IOException {
    // given
    BitMarketTrade[] response = parse("marketdata/example-trades-data", BitMarketTrade[].class);

    BitMarket bitMarket = mock(BitMarket.class);
    PowerMockito.when(bitMarket.getTrades("BTCAUD")).thenReturn(response);
    Whitebox.setInternalState(dataService, "bitMarket", bitMarket);

    // when
    Trades trades = dataService.getTrades(CurrencyPair.BTC_AUD);
    List<Trade> tradeList = trades.getTrades();

    // then
    assertThat(tradeList).hasSize(3);
    for (int i=0; i < tradeList.size(); i++) {
      BitMarketCompareUtils.compareTrades(tradeList.get(i), TRADES[i]);
    }
  }

  @Test public void shouldGetOrderBook() throws IOException {
    // given
    BitMarketOrderBook response = parse("marketdata/example-order-book-data", BitMarketOrderBook.class);

    BitMarket bitMarket = mock(BitMarket.class);
    PowerMockito.when(bitMarket.getOrderBook("BTCAUD")).thenReturn(response);
    Whitebox.setInternalState(dataService, "bitMarket", bitMarket);

    // when
    OrderBook orderBook = dataService.getOrderBook(CurrencyPair.BTC_AUD);

    // then
    BitMarketCompareUtils.compareOrderBooks(orderBook, ORDER_BOOK);
  }
}
