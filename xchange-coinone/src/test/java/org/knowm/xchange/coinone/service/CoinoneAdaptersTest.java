package org.knowm.xchange.coinone.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.Test;
import org.knowm.xchange.coinone.dto.account.CoinoneBalancesResponse;
import org.knowm.xchange.coinone.dto.marketdata.CoinoneOrderBook;
import org.knowm.xchange.coinone.dto.marketdata.CoinoneOrderBookData;
import org.knowm.xchange.coinone.dto.marketdata.CoinoneTicker;

public class CoinoneAdaptersTest {

  @Test
  public void testTicker() throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    InputStream is =
        CoinoneAdaptersTest.class.getResourceAsStream(
            "/org/knowm/xchange/coinone/dto/marketdata/example-marketrecords-ticker-data.json");

    CoinoneTicker coinoneTicker = mapper.readValue(is, CoinoneTicker.class);

    coinoneTicker.getFirst();
    assertThat(coinoneTicker.getVolume()).isEqualTo(new BigDecimal("1578.1005"));
    assertThat(coinoneTicker.getLast()).isEqualTo(new BigDecimal("8510000"));
    assertThat(coinoneTicker.getFirst()).isEqualTo(new BigDecimal("8400000"));
    assertThat(coinoneTicker.getHigh()).isEqualTo(new BigDecimal("8810000"));
    assertThat(coinoneTicker.getLow()).isEqualTo(new BigDecimal("8370000"));
  }

  @Test
  public void testOrderBook() throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    InputStream is =
        CoinoneAdaptersTest.class.getResourceAsStream(
            "/org/knowm/xchange/coinone/dto/marketdata/example-marketrecords-orderbook-data.json");

    CoinoneOrderBook coinoneOrderBook = mapper.readValue(is, CoinoneOrderBook.class);

    CoinoneOrderBookData ask = coinoneOrderBook.getAsks()[0];
    CoinoneOrderBookData bid = coinoneOrderBook.getBids()[0];
    assertThat(bid.getPrice()).isEqualTo(new BigDecimal("536800"));
    assertThat(bid.getQty()).isEqualTo(new BigDecimal("1.1052"));
    assertThat(ask.getPrice()).isEqualTo(new BigDecimal("537400"));
    assertThat(ask.getQty()).isEqualTo(new BigDecimal("0.9641"));
  }

  @Test
  public void wallet() throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    InputStream is =
        CoinoneAdaptersTest.class.getResourceAsStream(
            "/org/knowm/xchange/coinone/dto/marketdata/example-accountrecords-wallet-data.json");

    CoinoneBalancesResponse coinoneBalances = mapper.readValue(is, CoinoneBalancesResponse.class);
    assertThat(coinoneBalances.getEth().getAvail()).isEqualTo(new BigDecimal("13.84596000"));
    assertThat(coinoneBalances.getEth().getBalance()).isEqualTo(new BigDecimal("13.84596000"));
    assertThat(coinoneBalances.getBtc().getAvail()).isEqualTo(new BigDecimal("0.73574641"));
    assertThat(coinoneBalances.getBtc().getBalance()).isEqualTo(new BigDecimal("0.73574641"));
    assertThat(coinoneBalances.getBtg().getAvail()).isEqualTo(new BigDecimal("1.73574641"));
    assertThat(coinoneBalances.getBtg().getBalance()).isEqualTo(new BigDecimal("1.73574641"));
    assertThat(coinoneBalances.getIota().getAvail()).isEqualTo(new BigDecimal("4.00000000"));
    assertThat(coinoneBalances.getIota().getBalance()).isEqualTo(new BigDecimal("5.00000000"));
  }
}
