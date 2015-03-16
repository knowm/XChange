package com.xeiam.xchange.bitcurex;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.bitcurex.BitcurexAdapters;
import com.xeiam.xchange.bitcurex.dto.marketdata.BitcurexAccountJSONTest;
import com.xeiam.xchange.bitcurex.dto.marketdata.BitcurexDepth;
import com.xeiam.xchange.bitcurex.dto.marketdata.BitcurexDepthJSONTest;
import com.xeiam.xchange.bitcurex.dto.marketdata.BitcurexFunds;
import com.xeiam.xchange.bitcurex.dto.marketdata.BitcurexTicker;
import com.xeiam.xchange.bitcurex.dto.marketdata.BitcurexTickerJSONTest;
import com.xeiam.xchange.bitcurex.dto.marketdata.BitcurexTrade;
import com.xeiam.xchange.bitcurex.dto.marketdata.BitcurexTradesJSONTest;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.utils.DateUtils;

/**
 * Tests the BitcurexAdapter class
 */
public class BitcurexAdapterTest {

  @Test
  public void testOrderAdapterWithDepth() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = BitcurexDepthJSONTest.class.getResourceAsStream("/marketdata/example-depth-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitcurexDepth BitcurexDepth = mapper.readValue(is, BitcurexDepth.class);

    List<LimitOrder> asks = BitcurexAdapters.adaptOrders(BitcurexDepth.getAsks(), CurrencyPair.BTC_EUR, OrderType.ASK, "");

    // Verify all fields filled
    assertThat(asks.get(0).getLimitPrice().doubleValue()).isEqualTo(70.00000000);
    assertThat(asks.get(0).getType()).isEqualTo(OrderType.ASK);
    assertThat(asks.get(0).getTradableAmount().doubleValue()).isEqualTo(0.1021341);
    assertThat(asks.get(0).getCurrencyPair()).isEqualTo(CurrencyPair.BTC_EUR);

  }

  @Test
  public void testTradeAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = BitcurexTradesJSONTest.class.getResourceAsStream("/marketdata/example-trades-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitcurexTrade[] BitcurexTrades = mapper.readValue(is, BitcurexTrade[].class);

    Trades trades = BitcurexAdapters.adaptTrades(BitcurexTrades, CurrencyPair.BTC_EUR);
    assertThat(trades.getTrades().size()).isEqualTo(70);

    // Verify all fields filled
    assertThat(trades.getTrades().get(0).getPrice().doubleValue() == 70.00000000);
    assertThat(trades.getTrades().get(0).getTradableAmount().doubleValue() == 23.99500000);
    assertThat(DateUtils.toUTCString(trades.getTrades().get(0).getTimestamp())).isEqualTo("2013-07-29 16:53:28 GMT");
  }

  @Test
  public void testTickerAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = BitcurexTickerJSONTest.class.getResourceAsStream("/marketdata/example-ticker-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitcurexTicker BitcurexTicker = mapper.readValue(is, BitcurexTicker.class);

    Ticker ticker = BitcurexAdapters.adaptTicker(BitcurexTicker, CurrencyPair.BTC_PLN);
    System.out.println(ticker.toString());

    assertThat(ticker.getLast().toString()).isEqualTo("1555");
    assertThat(ticker.getLow().toString()).isEqualTo("1538");
    assertThat(ticker.getHigh().toString()).isEqualTo("1555");
    assertThat(ticker.getVolume()).isEqualTo(new BigDecimal("42.67895867"));

  }

  @Test
  public void testAccountFundsAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = BitcurexAccountJSONTest.class.getResourceAsStream("/marketdata/example-funds-eur-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitcurexFunds bitcurexFunds = mapper.readValue(is, BitcurexFunds.class);

    AccountInfo accountInfo = BitcurexAdapters.adaptAccountInfo(bitcurexFunds, "demo");
    System.out.println(accountInfo.toString());

    assertThat(accountInfo.getBalance("BTC").compareTo(new BigDecimal("2.59033845")) == 0);
    assertThat(accountInfo.getBalance("EUR").compareTo(new BigDecimal("6160.06838790")) == 0);
    assertThat(accountInfo.getUsername().toString()).isEqualTo("demo");
  }

  @Test
  public void testAccountFundsPLNAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = BitcurexAccountJSONTest.class.getResourceAsStream("/marketdata/example-funds-pln-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitcurexFunds bitcurexFunds = mapper.readValue(is, BitcurexFunds.class);

    AccountInfo accountInfo = BitcurexAdapters.adaptAccountInfo(bitcurexFunds, "demo");
    System.out.println(accountInfo.toString());

    assertThat(accountInfo.getBalance("BTC").compareTo(new BigDecimal("2.59033845")) == 0);
    assertThat(accountInfo.getBalance("PLN").compareTo(new BigDecimal("6160.06838790")) == 0);
    assertThat(accountInfo.getUsername().toString()).isEqualTo("demo");
  }

}
