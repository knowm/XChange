package com.xeiam.xchange.coinbase;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.dto.account.Wallet;
import com.xeiam.xchange.dto.account.Balance;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.coinbase.dto.account.CoinbaseUser;
import com.xeiam.xchange.coinbase.dto.account.CoinbaseUsers;
import com.xeiam.xchange.coinbase.dto.marketdata.CoinbaseMoney;
import com.xeiam.xchange.coinbase.dto.marketdata.CoinbasePrice;
import com.xeiam.xchange.coinbase.dto.marketdata.CoinbaseSpotPriceHistory;
import com.xeiam.xchange.coinbase.dto.trade.CoinbaseTransfers;
import com.xeiam.xchange.currency.Currency;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.trade.UserTrade;
import com.xeiam.xchange.dto.trade.UserTrades;
import com.xeiam.xchange.utils.DateUtils;

/**
 * @author jamespedwards42
 */
public class CoinbaseAdapterTest {

  @Test
  public void testAdaptAccountInfo() throws IOException {

    Balance balance = new Balance(Currency.BTC, new BigDecimal("7.10770000"));
    List<Balance> balances = new ArrayList<Balance>();
    balances.add(balance);
    AccountInfo expectedAccountInfo = new AccountInfo("demo@demo.com", new Wallet(balances));

    // Read in the JSON from the example resources
    InputStream is = CoinbaseAdapterTest.class.getResourceAsStream("/account/example-users-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    CoinbaseUsers users = mapper.readValue(is, CoinbaseUsers.class);

    List<CoinbaseUser> userList = users.getUsers();
    CoinbaseUser user = userList.get(0);

    AccountInfo accountInfo = CoinbaseAdapters.adaptAccountInfo(user);
    //// fest bug: map fields are compared by values() which is always false
    //assertThat(wallet).isLenientEqualsToByIgnoringFields(expectedWallet, "balances");
    //assertThat(wallet.getBalance("BTC")).isEqualTo(expectedWallet.getBalance("BTC"));
    assertThat(accountInfo).isEqualsToByComparingFields(expectedAccountInfo);
  }

  @Test
  public void testAdaptTrades() throws IOException {

    BigDecimal tradableAmount = new BigDecimal("1.20000000");
    BigDecimal price = new BigDecimal("905.10").divide(tradableAmount, RoundingMode.HALF_EVEN);

    UserTrade expectedTrade = new UserTrade(OrderType.BID, tradableAmount, CurrencyPair.BTC_USD, price,
        DateUtils.fromISO8601DateString("2014-02-06T18:12:38-08:00"), "52f4411767c71baf9000003f", "52f4411667c71baf9000003c", new BigDecimal("9.05"),
        "USD");

    // Read in the JSON from the example resources
    InputStream is = CoinbaseAdapterTest.class.getResourceAsStream("/trade/example-transfers-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    CoinbaseTransfers transfers = mapper.readValue(is, CoinbaseTransfers.class);

    UserTrades trades = CoinbaseAdapters.adaptTrades(transfers);
    List<UserTrade> tradeList = trades.getUserTrades();
    assertThat(tradeList.size()).isEqualTo(1);

    UserTrade trade = tradeList.get(0);
    assertThat(trade).isEqualsToByComparingFields(expectedTrade);
  }

  @Test
  public void testAdaptTicker() throws IOException {

    Ticker expectedTicker = new Ticker.Builder().currencyPair(CurrencyPair.BTC_USD).ask(new BigDecimal("723.09")).bid(new BigDecimal("723.09"))
        .last(new BigDecimal("719.79")).low(new BigDecimal("718.2")).high(new BigDecimal("723.11")).build();

    InputStream is = CoinbaseAdapterTest.class.getResourceAsStream("/marketdata/example-price-data.json");
    ObjectMapper mapper = new ObjectMapper();
    CoinbasePrice price = mapper.readValue(is, CoinbasePrice.class);

    CoinbaseMoney spotPrice = new CoinbaseMoney("USD", new BigDecimal("719.79"));

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
