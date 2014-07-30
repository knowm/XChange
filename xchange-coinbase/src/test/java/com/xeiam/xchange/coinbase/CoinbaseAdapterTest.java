package com.xeiam.xchange.coinbase;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.coinbase.dto.account.CoinbaseUser;
import com.xeiam.xchange.coinbase.dto.account.CoinbaseUsers;
import com.xeiam.xchange.coinbase.dto.marketdata.CoinbaseMoney;
import com.xeiam.xchange.coinbase.dto.marketdata.CoinbasePrice;
import com.xeiam.xchange.coinbase.dto.marketdata.CoinbaseSpotPriceHistory;
import com.xeiam.xchange.coinbase.dto.trade.CoinbaseTransfers;
import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Ticker.TickerBuilder;
import com.xeiam.xchange.dto.marketdata.Trade;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.Wallet;
import com.xeiam.xchange.utils.DateUtils;

/**
 * @author jamespedwards42
 */
public class CoinbaseAdapterTest {

  @Test
  public void testAdaptAccountInfo() throws IOException {

    Wallet wallet = new Wallet("BTC", new BigDecimal("7.10770000"));
    List<Wallet> wallets = new ArrayList<Wallet>();
    wallets.add(wallet);
    AccountInfo expectedAccountInfo = new AccountInfo("demo@demo.com", wallets);

    // Read in the JSON from the example resources
    InputStream is = CoinbaseAdapterTest.class.getResourceAsStream("/account/example-users-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    CoinbaseUsers users = mapper.readValue(is, CoinbaseUsers.class);

    List<CoinbaseUser> userList = users.getUsers();
    CoinbaseUser user = userList.get(0);

    AccountInfo accountInfo = CoinbaseAdapters.adaptAccountInfo(user);
    assertThat(accountInfo).isEqualsToByComparingFields(expectedAccountInfo);
  }

  @Test
  public void testAdaptTrades() throws IOException {

    BigDecimal tradableAmount = new BigDecimal("1.20000000");
    BigDecimal price = new BigDecimal("905.10").divide(tradableAmount, RoundingMode.HALF_EVEN);
    Trade expectedTrade =
        new Trade(OrderType.BID, tradableAmount, CurrencyPair.BTC_USD, price, DateUtils.fromISO8601DateString("2014-02-06T18:12:38-08:00"), "52f4411767c71baf9000003f", "52f4411767c71baf9000003f");

    // Read in the JSON from the example resources
    InputStream is = CoinbaseAdapterTest.class.getResourceAsStream("/trade/example-transfers-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    CoinbaseTransfers transfers = mapper.readValue(is, CoinbaseTransfers.class);

    Trades trades = CoinbaseAdapters.adaptTrades(transfers);
    List<Trade> tradeList = trades.getTrades();
    assertThat(tradeList.size()).isEqualTo(1);

    Trade trade = tradeList.get(0);
    assertThat(trade).isEqualsToByComparingFields(expectedTrade);
  }

  @Test
  public void testAdaptTicker() throws IOException {

    Ticker expectedTicker =
        TickerBuilder.newInstance().withCurrencyPair(CurrencyPair.BTC_USD).withAsk(new BigDecimal("723.09")).withBid(new BigDecimal("723.09")).withLast(new BigDecimal("719.79")).withLow(
            new BigDecimal("718.2")).withHigh(new BigDecimal("723.11")).build();

    InputStream is = CoinbaseAdapterTest.class.getResourceAsStream("/marketdata/example-price-data.json");
    ObjectMapper mapper = new ObjectMapper();
    CoinbasePrice price = mapper.readValue(is, CoinbasePrice.class);

    CoinbaseMoney spotPrice = new CoinbaseMoney(Currencies.USD, new BigDecimal("719.79"));

    is = CoinbaseAdapterTest.class.getResourceAsStream("/marketdata/example-spot-rate-history-data.json");
    String spotPriceHistoryString;
    Scanner scanner = null;
    try {
      scanner = new Scanner(is);
      spotPriceHistoryString = scanner.useDelimiter("\\A").next();
    } finally {
      scanner.close();
    }

    CoinbaseSpotPriceHistory spotPriceHistory = CoinbaseSpotPriceHistory.fromRawString(spotPriceHistoryString);

    Ticker ticker = CoinbaseAdapters.adaptTicker(CurrencyPair.BTC_USD, price, price, spotPrice, spotPriceHistory);

    assertThat(ticker).isEqualsToByComparingFields(expectedTicker);
  }
}
