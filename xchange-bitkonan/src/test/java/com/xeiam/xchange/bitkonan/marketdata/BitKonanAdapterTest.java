package com.xeiam.xchange.bitkonan.marketdata;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.bitkonan.BitKonanAdapters;
import com.xeiam.xchange.bitkonan.dto.marketdata.BitKonanOrderBook;
import com.xeiam.xchange.bitkonan.dto.marketdata.BitKonanTicker;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.trade.LimitOrder;

/**
 * @author Piotr Ładyżyński
 */
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

    OrderBook adaptedOrderBook = BitKonanAdapters.adaptOrderBook(orderBook);

    List<LimitOrder> asks = adaptedOrderBook.getAsks();
    assertThat(asks.size()).isEqualTo(10);
    LimitOrder order = asks.get(0);
    assertThat(order.getLimitPrice()).isEqualTo("347.89");
    assertThat(order.getTradableAmount()).isEqualTo("0.46557");
    assertThat(order.getCurrencyPair()).isEqualTo(CurrencyPair.BTC_USD);
  }

}
