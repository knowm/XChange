package org.knowm.xchange.bitbay;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.junit.Test;
import org.knowm.xchange.bitbay.dto.acount.BitbayAccountInfoResponse;
import org.knowm.xchange.bitbay.dto.trade.BitbayOrder;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.trade.OpenOrders;

/** Tests the BitbayAdapter class */
public class BitbayAdapterTest {

  @Test
  public void testAccountInfoAdapter() throws IOException {
    // Read in the JSON from the example resources
    InputStream is =
        BitbayAdapterTest.class.getResourceAsStream(
            "/org/knowm/xchange/bitbay/account/example-info-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitbayAccountInfoResponse balances = mapper.readValue(is, BitbayAccountInfoResponse.class);

    AccountInfo accountInfo = BitbayAdapters.adaptAccountInfo("Joe Mama", balances);
    assertThat(accountInfo.getUsername()).isEqualTo("Joe Mama");
    assertThat(accountInfo.getWallet().getBalance(Currency.USD).getCurrency())
        .isEqualTo(Currency.USD);
    assertThat(accountInfo.getWallet().getBalance(Currency.USD).getTotal()).isEqualTo("2.20");
    assertThat(accountInfo.getWallet().getBalance(Currency.USD).getAvailable()).isEqualTo("2.00");
    assertThat(accountInfo.getWallet().getBalance(Currency.USD).getFrozen()).isEqualTo("0.20");
    assertThat(accountInfo.getWallet().getBalance(Currency.BTC).getCurrency())
        .isEqualTo(Currency.BTC);
    assertThat(accountInfo.getWallet().getBalance(Currency.BTC).getTotal()).isEqualTo("1.10000000");
    assertThat(accountInfo.getWallet().getBalance(Currency.BTC).getAvailable())
        .isEqualTo("1.00000000");
    assertThat(accountInfo.getWallet().getBalance(Currency.BTC).getFrozen())
        .isEqualTo("0.10000000");
  }

  @Test
  public void testOpenOrdersAdapter() throws IOException {
    // Read in the JSON from the example resources
    InputStream is =
        BitbayAdapterTest.class.getResourceAsStream(
            "/org/knowm/xchange/bitbay/trade/example-orders-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    List<BitbayOrder> orders = mapper.readValue(is, new TypeReference<List<BitbayOrder>>() {});

    OpenOrders openOrders = BitbayAdapters.adaptOpenOrders(orders);

    assertThat(openOrders.getOpenOrders().size()).isEqualTo(2);
    assertThat(openOrders.getOpenOrders().get(0).getLimitPrice()).isEqualByComparingTo("1400");
    assertThat(openOrders.getOpenOrders().get(0).getOriginalAmount()).isEqualTo("0.10000000");
    assertThat(openOrders.getOpenOrders().get(0).getCurrencyPair()).isEqualTo(CurrencyPair.BTC_EUR);
    assertThat(openOrders.getOpenOrders().get(0).getType()).isEqualTo(Order.OrderType.ASK);
    assertThat(openOrders.getOpenOrders().get(0).getId()).isEqualTo("59057271");

    assertThat(openOrders.getOpenOrders().get(1).getLimitPrice()).isEqualByComparingTo("1500");
    assertThat(openOrders.getOpenOrders().get(1).getOriginalAmount()).isEqualTo("0.10000000");
    assertThat(openOrders.getOpenOrders().get(1).getCurrencyPair()).isEqualTo(CurrencyPair.BTC_EUR);
    assertThat(openOrders.getOpenOrders().get(1).getType()).isEqualTo(Order.OrderType.ASK);
    assertThat(openOrders.getOpenOrders().get(1).getId()).isEqualTo("59057261");
  }
}
