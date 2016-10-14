package org.knowm.xchange.bitcoinde;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;

import org.junit.Test;
import org.knowm.xchange.bitcoinde.dto.marketdata.BitcoindeOrderBook;
import org.knowm.xchange.bitcoinde.dto.marketdata.BitcoindeRate;
import org.knowm.xchange.bitcoinde.dto.marketdata.BitcoindeTrade;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author matthewdowney
 */
public class BitcoindeAdapterTest {

  @Test
  public void testOrderBookAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = BitcoindeAdapterTest.class.getResourceAsStream("/orderbook.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitcoindeOrderBook bitcoindeOrderBook = mapper.readValue(is, BitcoindeOrderBook.class);

    // Create a generic OrderBook object from a Bitcoinde specific OrderBook
    OrderBook orderBook = BitcoindeAdapters.adaptOrderBook(bitcoindeOrderBook, CurrencyPair.BTC_EUR);

    // verify all fields are filled correctly
    assertThat(orderBook.getBids().get(0).getLimitPrice().toString()).isEqualTo("222.5");
    assertThat(orderBook.getBids().get(0).getType()).isEqualTo(OrderType.BID);
    assertThat(orderBook.getBids().get(0).getTradableAmount()).isEqualTo(new BigDecimal("0.35"));
    assertThat(orderBook.getBids().get(0).getCurrencyPair()).isEqualTo(CurrencyPair.BTC_EUR);
  }

  @Test
  public void testTradesAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = BitcoindeAdapterTest.class.getResourceAsStream("/trades.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitcoindeTrade[] bitcoindeTrades = mapper.readValue(is, BitcoindeTrade[].class);

    // Use our adapter to get a generic Trades object from a
    // BitcoindeTrade[] object
    Trades trades = BitcoindeAdapters.adaptTrades(bitcoindeTrades, CurrencyPair.BTC_EUR);

    // Make sure the adapter got all the data
    assertThat(trades.getTrades().size()).isEqualTo(3);
    assertThat(trades.getlastID()).isEqualTo(1210158);

    // Verify that all fields are filled
    assertThat(trades.getTrades().get(0).getId()).isEqualTo("1210152");
    assertThat(trades.getTrades().get(0).getPrice().toString()).isEqualTo("236.61");
    assertThat(trades.getTrades().get(0).getTradableAmount()).isEqualTo(new BigDecimal("0.78054655"));
    assertThat(trades.getTrades().get(0).getCurrencyPair()).isEqualTo(CurrencyPair.BTC_EUR);

    // Verify that the date is correct
    Date date = new Date();
    date.setTime(1428255708L * 1000); // Create the expected date for trade
    // 0
    assertThat(trades.getTrades().get(0).getTimestamp()).isEqualTo(date); // Make
    // sure
    // the
    // dates
    // match
  }

  @Test
  public void testTickerAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = BitcoindeAdapterTest.class.getResourceAsStream("/rate.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitcoindeRate bitcoindeRate = mapper.readValue(is, BitcoindeRate.class);

    // Create a generic Ticker object from a BitcoindeRate object
    Ticker ticker = BitcoindeAdapters.adaptTicker(bitcoindeRate, CurrencyPair.BTC_EUR);

    // Verify that we have the right rate & currency
    assertThat(ticker.getLast().toString()).isEqualTo("225.18347646");
    assertThat(ticker.getCurrencyPair()).isEqualTo(CurrencyPair.BTC_EUR);
  }
}
