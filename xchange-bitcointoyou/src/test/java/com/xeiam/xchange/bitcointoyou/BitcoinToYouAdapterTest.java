package com.xeiam.xchange.bitcointoyou;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.bitcointoyou.dto.BitcoinToYouBaseTradeApiResult;
import com.xeiam.xchange.bitcointoyou.dto.account.BitcoinToYouBalance;
import com.xeiam.xchange.bitcointoyou.dto.marketdata.BitcoinToYouOrderBook;
import com.xeiam.xchange.bitcointoyou.dto.marketdata.BitcoinToYouTicker;
import com.xeiam.xchange.bitcointoyou.dto.marketdata.BitcoinToYouTransaction;
import com.xeiam.xchange.bitcointoyou.dto.trade.BitcoinToYouOrder;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;

/**
 * Tests the BitcoinToYouAdapter class
 *
 * @author Felipe Micaroni Lalli
 */
public class BitcoinToYouAdapterTest {

  @Test
  public void testOrderBookAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = BitcoinToYouAdapterTest.class.getResourceAsStream("/marketdata/example-full-depth-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitcoinToYouOrderBook bitcoinToYouOrderBook = mapper.readValue(is, BitcoinToYouOrderBook.class);

    OrderBook orderBook = BitcoinToYouAdapters.adaptOrderBook(bitcoinToYouOrderBook, CurrencyPair.BTC_BRL);
    assertThat(orderBook.getBids().size()).isEqualTo(2);

    // verify all fields filled
    assertThat(orderBook.getBids().get(0).getLimitPrice().toString()).isEqualTo("956.72");
    assertThat(orderBook.getBids().get(0).getType()).isEqualTo(OrderType.BID);
    assertThat(orderBook.getBids().get(0).getTradableAmount()).isEqualTo(new BigDecimal("0.00055518"));
    assertThat(orderBook.getBids().get(0).getCurrencyPair()).isEqualTo(CurrencyPair.BTC_BRL);
  }

  @Test
  public void testTradesAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = BitcoinToYouAdapterTest.class.getResourceAsStream("/marketdata/example-trades-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitcoinToYouTransaction[] transactions = mapper.readValue(is, BitcoinToYouTransaction[].class);

    Trades trades = BitcoinToYouAdapters.adaptTrades(transactions, CurrencyPair.BTC_BRL);
    assertThat(trades.getTrades().size()).isEqualTo(3);
    assertThat(trades.getlastID()).isEqualTo(25527);
    // verify all fields filled
    assertThat(trades.getTrades().get(0).getId()).isEqualTo("25525");
    assertThat(trades.getTrades().get(0).getPrice().toString()).isEqualTo("980.28");
    assertThat(trades.getTrades().get(0).getType() == OrderType.BID);
    assertThat(trades.getTrades().get(0).getTradableAmount()).isEqualTo(new BigDecimal("0.0099238"));
    assertThat(trades.getTrades().get(0).getCurrencyPair()).isEqualTo(CurrencyPair.BTC_BRL);
  }

  @Test
  public void testTickerAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = BitcoinToYouAdapterTest.class.getResourceAsStream("/marketdata/example-ticker-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitcoinToYouTicker bitcoinToYouTicker = mapper.readValue(is, BitcoinToYouTicker.class);

    Ticker ticker = BitcoinToYouAdapters.adaptTicker(bitcoinToYouTicker, CurrencyPair.BTC_BRL);

    assertThat(ticker.getLast().toString()).isEqualTo("955.04");
    assertThat(ticker.getBid().toString()).isEqualTo("954.7900000000");
    assertThat(ticker.getAsk().toString()).isEqualTo("982.4800000000");
    assertThat(ticker.getVolume()).isEqualTo(new BigDecimal("58.16484697"));
    assertThat(ticker.getTimestamp()).isEqualTo(new Date(1412878640L * 1000L));
  }

  @Test
  public void testAccountInfoAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = BitcoinToYouAdapterTest.class.getResourceAsStream("/account/example-accountinfo-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitcoinToYouBaseTradeApiResult<BitcoinToYouBalance[]> bitcoinToYouAccountInfo = mapper.readValue(is,
        new TypeReference<BitcoinToYouBaseTradeApiResult<BitcoinToYouBalance[]>>() {
        });

    AccountInfo accountInfo = BitcoinToYouAdapters.adaptAccountInfo(bitcoinToYouAccountInfo, "Nina Tufão & Bit");
    assertThat(accountInfo.getUsername()).isEqualTo("Nina Tufão & Bit");
    assertThat(accountInfo.getWallets().get(0).getCurrency()).isEqualTo("BRL");
    assertThat(accountInfo.getWallets().get(0).getBalance()).isEqualTo(new BigDecimal("17628.7309736"));
    assertThat(accountInfo.getWallets().get(1).getCurrency()).isEqualTo("BTC");
    assertThat(accountInfo.getWallets().get(1).getBalance()).isEqualTo(new BigDecimal("2.15868664"));
    assertThat(accountInfo.getWallets().get(2).getCurrency()).isEqualTo("LTC");
    assertThat(accountInfo.getWallets().get(2).getBalance()).isEqualTo(new BigDecimal("12.68654019"));
  }

  @Test
  public void testOrdersAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = BitcoinToYouAdapterTest.class.getResourceAsStream("/trade/example-userorders.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitcoinToYouBaseTradeApiResult<BitcoinToYouOrder[]> apiResult = mapper.readValue(is,
        new TypeReference<BitcoinToYouBaseTradeApiResult<BitcoinToYouOrder[]>>() {
        });

    List<LimitOrder> orders = BitcoinToYouAdapters.adaptOrders(apiResult);

    Map<String, LimitOrder> orderById = new HashMap<String, LimitOrder>();

    for (LimitOrder order : orders) {
      orderById.put(order.getId(), order);
    }

    assertThat(orderById.get("67233").getType()).isEqualTo(OrderType.BID);
    assertThat(orderById.get("67233").getTimestamp()).isEqualTo(new Date(1392902550123L));
    assertThat(orderById.get("67233").getLimitPrice()).isEqualTo(new BigDecimal("500.9800000000"));
    assertThat(orderById.get("67233").getTradableAmount()).isEqualTo(new BigDecimal("0.01000000"));
    assertThat(orderById.get("67233").getCurrencyPair()).isEqualTo(CurrencyPair.BTC_BRL);
  }
}
