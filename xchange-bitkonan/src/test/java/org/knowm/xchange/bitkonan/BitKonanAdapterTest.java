package org.knowm.xchange.bitkonan;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.junit.Test;
import org.knowm.xchange.bitkonan.dto.marketdata.BitKonanMarketDataJsonTest;
import org.knowm.xchange.bitkonan.dto.marketdata.BitKonanOrderBook;
import org.knowm.xchange.bitkonan.dto.marketdata.BitKonanTicker;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.trade.LimitOrder;

import com.fasterxml.jackson.databind.ObjectMapper;

public class BitKonanAdapterTest {

  @Test
  public void testAdaptTicker() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = BitKonanAdapterTest.class.getResourceAsStream("/marketdata/bitkonan-example-ticker-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();

    BitKonanTicker ticker = mapper.readValue(is, BitKonanTicker.class);
    Ticker adaptedTicker = BitKonanAdapters.adaptTicker(ticker, CurrencyPair.BTC_USD);

    assertThat(adaptedTicker.getAsk()).isEqualTo("609.58");
    assertThat(adaptedTicker.getBid()).isEqualTo("608.63");
    assertThat(adaptedTicker.getLow()).isEqualTo("563.4");
    assertThat(adaptedTicker.getHigh()).isEqualTo("621.81");
    assertThat(adaptedTicker.getLast()).isEqualTo("609.24");
    assertThat(adaptedTicker.getVolume()).isEqualTo("1232.17");
    assertThat(adaptedTicker.getCurrencyPair()).isEqualTo(CurrencyPair.BTC_USD);
  }

  @Test
  public void testAdaptOrderbook() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = BitKonanMarketDataJsonTest.class.getResourceAsStream("/marketdata/bitkonan-example-orderbook-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitKonanOrderBook orderBook = mapper.readValue(is, BitKonanOrderBook.class);

    OrderBook adaptedOrderBook = BitKonanAdapters.adaptOrderBook(orderBook, CurrencyPair.BTC_USD);

    List<LimitOrder> asks = adaptedOrderBook.getAsks();
    assertThat(asks.size()).isEqualTo(10);
    LimitOrder order = asks.get(0);
    assertThat(order.getLimitPrice()).isEqualTo("347.89");
    assertThat(order.getTradableAmount()).isEqualTo("0.46557");
    assertThat(order.getCurrencyPair()).isEqualTo(CurrencyPair.BTC_USD);
  }

}
