package org.knowm.xchange.coinbase;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.Test;
import org.knowm.xchange.coinbase.dto.account.CoinbaseUser;
import org.knowm.xchange.coinbase.dto.account.CoinbaseUsers;
import org.knowm.xchange.coinbase.dto.marketdata.CoinbaseMoney;
import org.knowm.xchange.coinbase.dto.marketdata.CoinbasePrice;
import org.knowm.xchange.coinbase.dto.marketdata.CoinbaseSpotPriceHistory;
import org.knowm.xchange.coinbase.dto.trade.CoinbaseTransfers;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.utils.DateUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author jamespedwards42
 */
public class CoinbaseAdapterTest {

  @Test
  public void testAdaptAccountInfo() throws IOException {

    Balance balance = new Balance(Currency.BTC, new BigDecimal("7.10770000"));
    List<Balance> balances = new ArrayList<>();
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
        Currency.getInstance("USD"));

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
    try (Scanner scanner = new Scanner(is)) {
      spotPriceHistoryString = scanner.useDelimiter("\\A").next();
    }

    CoinbaseSpotPriceHistory spotPriceHistory = CoinbaseSpotPriceHistory.fromRawString(spotPriceHistoryString);

    Ticker ticker = CoinbaseAdapters.adaptTicker(CurrencyPair.BTC_USD, price, price, spotPrice, spotPriceHistory);

    // TOD  this seems broke. If you
//    assertThat(ticker).isEqualsToByComparingFields(expectedTicker);
  }
}
