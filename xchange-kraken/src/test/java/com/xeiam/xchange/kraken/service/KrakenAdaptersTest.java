package com.xeiam.xchange.kraken.service;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;

import org.joda.money.BigMoney;
import org.joda.money.CurrencyUnit;
import org.junit.Assert;
import org.junit.Test;
import org.xchange.kraken.KrakenAdapters;
import org.xchange.kraken.dto.account.KrakenBalanceResult;
import org.xchange.kraken.dto.marketdata.KrakenTradesResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.kraken.service.marketdata.KrakenTradesJSONTest;

public class KrakenAdaptersTest {

  @Test
  public void testAdaptOrders() {

    fail("Not yet implemented");
  }

  @Test
  public void testAdaptTicker() {

    fail("Not yet implemented");
  }

  @Test
  public void testAdaptTrades() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = KrakenTradesJSONTest.class.getResourceAsStream("/marketdata/example-trades-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    KrakenTradesResult krakenTrades = mapper.readValue(is, KrakenTradesResult.class);

    Trades trades = KrakenAdapters.adaptTrades(krakenTrades.getResult().getTradesPerCurrencyPair("XXBTZEUR"), Currencies.EUR, Currencies.BTC, krakenTrades.getResult().getLast());
    Assert.assertEquals(2, trades.getTrades().size());
    assertThat(trades.getTrades().get(0).getTimestamp()).isBefore(new Date());
    assertThat(trades.getTrades().get(0).getPrice().getAmount()).isEqualTo("92.50000");
    assertThat(trades.getTrades().get(1).getTradableAmount()).isEqualTo("0.05506000");

  }

  @Test
  public void testAdaptBalance() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = KrakenTradesJSONTest.class.getResourceAsStream("/account/example-balance-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    KrakenBalanceResult krakenBalance = mapper.readValue(is, KrakenBalanceResult.class);
    AccountInfo info = KrakenAdapters.adaptBalance(krakenBalance, null);
    assertThat(info.getBalance(CurrencyUnit.EUR)).isEqualTo(BigMoney.of(CurrencyUnit.EUR, new BigDecimal("1.0539")));
    assertThat(info.getBalance(CurrencyUnit.of(Currencies.BTC))).isEqualTo(BigMoney.of(CurrencyUnit.of(Currencies.BTC), new BigDecimal("0.4888583300")));

  }
}
