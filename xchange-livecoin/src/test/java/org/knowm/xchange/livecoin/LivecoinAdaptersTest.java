package org.knowm.xchange.livecoin;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Wallet;

public class LivecoinAdaptersTest {
  @Test
  public void liveCoinAdapterTest() {
    List<Map> response =
        Arrays.asList(
            responseItem("total", "USD", "16.45759873"),
            responseItem("trade", "USD", "0.0"),
            responseItem("available", "USD", "16.45759873"),
            responseItem("total", "BTC", "0.00671945"),
            responseItem("trade", "BTC", "0.1"),
            responseItem("available", "BTC", "0.00671945"),
            responseItem("total", "XBT", "0.0"),
            responseItem("trade", "XBT", "0.0"),
            responseItem("available", "XBT", "0.0"));

    List<Wallet> wallets = LivecoinAdapters.adaptWallets(response);

    assertTrue(wallets.contains(wallet(Currency.USD, "16.45759873", "0.0", "16.45759873")));
    assertTrue(wallets.contains(wallet(Currency.BTC, "0.00671945", "0.1", "0.00671945")));
  }

  private static Map<String, String> responseItem(String type, String currency, String balance) {
    HashMap<String, String> map = new HashMap<>();
    map.put("type", type);
    map.put("currency", currency);
    map.put("value", balance);
    return map;
  }

  private static Wallet wallet(Currency currency, String total, String trade, String available) {
    return new Wallet(
        currency.getCurrencyCode(),
        new Balance.Builder()
            .currency(currency)
            .total(new BigDecimal(total))
            .available(new BigDecimal(available))
            .frozen(new BigDecimal(trade))
            .build());
  }
}
