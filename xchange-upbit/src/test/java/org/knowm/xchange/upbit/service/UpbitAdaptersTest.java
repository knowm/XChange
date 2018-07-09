package org.knowm.xchange.upbit.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.Test;
import org.knowm.xchange.upbit.dto.marketdata.UpbitOrderBookData;
import org.knowm.xchange.upbit.dto.marketdata.UpbitOrderBooks;
import org.knowm.xchange.upbit.dto.marketdata.UpbitTicker;
import org.knowm.xchange.upbit.dto.marketdata.UpbitTickers;
import org.knowm.xchange.upbit.dto.marketdata.UpbitTrade;
import org.knowm.xchange.upbit.dto.marketdata.UpbitTrades;

public class UpbitAdaptersTest {

  @Test
  public void testTicker() throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    InputStream is =
        UpbitAdaptersTest.class.getResourceAsStream(
            "/org/knowm/xchange/upbit/dto/marketdata/example-marketrecords-ticker-data.json");

    UpbitTickers upbitTickers = mapper.readValue(is, UpbitTickers.class);
    UpbitTicker upbitTicker = upbitTickers.getTickers()[0];
    assertThat(upbitTicker.getTrade_volume()).isEqualTo(new BigDecimal("0.00625758"));
    assertThat(upbitTicker.getTrade_price()).isEqualTo(new BigDecimal("7050000"));
    assertThat(upbitTicker.getOpening_price()).isEqualTo(new BigDecimal("7109000"));
    assertThat(upbitTicker.getHigh_price()).isEqualTo(new BigDecimal("7109000"));
    assertThat(upbitTicker.getLow_price()).isEqualTo(new BigDecimal("7024000"));
  }

  @Test
  public void testOrderBook() throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    InputStream is =
        UpbitAdaptersTest.class.getResourceAsStream(
            "/org/knowm/xchange/upbit/dto/marketdata/example-marketrecords-orderbook-data.json");

    UpbitOrderBooks upbitOrderBook = mapper.readValue(is, UpbitOrderBooks.class);

    UpbitOrderBookData data = upbitOrderBook.getUpbitOrderBooks()[0].getOrderbookUnits()[0];

    assertThat(data.getBidPrice()).isEqualTo(new BigDecimal("0.07083262"));
    assertThat(data.getBidSize()).isEqualTo(new BigDecimal("0.09857284"));
    assertThat(data.getAskPrice()).isEqualTo(new BigDecimal("0.07084678"));
    assertThat(data.getAskSize()).isEqualTo(new BigDecimal("228.42381741"));
  }

  @Test
  public void testTrades() throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    InputStream is =
        UpbitAdaptersTest.class.getResourceAsStream(
            "/org/knowm/xchange/upbit/dto/marketdata/example-marketrecords-trades-data.json");

    UpbitTrades upbitTrades = mapper.readValue(is, UpbitTrades.class);

    UpbitTrade data = upbitTrades.getUpbitTrades()[0];

    assertThat(data.getTradePrice()).isEqualTo(new BigDecimal("0.07092"));
    assertThat(data.getTradeVolume()).isEqualTo(new BigDecimal("0.01840677"));
    assertThat(data.getAskBid()).isEqualTo("ASK");
  }
}
