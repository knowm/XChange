package org.knowm.xchange.bibox.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.type.TypeReference;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import org.junit.Test;
import org.knowm.xchange.bibox.BiboxTestUtils;
import org.knowm.xchange.bibox.dto.BiboxResponse;
import org.knowm.xchange.bibox.dto.trade.BiboxOrderBook;

/**
 * Test Marketdata JSON parsing
 *
 * @author odrotleff
 */
public class BiboxMarketdataUnmarshalTest {

  @Test
  public void testTickerUnmarshal() throws IOException {
    BiboxResponse<BiboxTicker> response =
        BiboxTestUtils.getResponse(
            new TypeReference<BiboxResponse<BiboxTicker>>() {},
            "/org/knowm/xchange/bibox/dto/marketdata/example-ticker.json");
    assertThat(response.getCmd()).isEqualTo("ticker");

    BiboxTicker ticker = response.getResult();
    assertThat(ticker.getBuy()).isEqualTo(new BigDecimal("0.00009284"));
    assertThat(ticker.getHigh()).isEqualTo(new BigDecimal("0.00010000"));
    assertThat(ticker.getLast()).isEqualTo(new BigDecimal("0.00009293"));
    assertThat(ticker.getLow()).isEqualTo(new BigDecimal("0.00008892"));
    assertThat(ticker.getSell()).isEqualTo(new BigDecimal("0.00009293"));
    assertThat(ticker.getTimestamp()).isEqualTo(1518037192690L);
    assertThat(ticker.getVol()).isEqualTo(new BigDecimal("5691444"));
    assertThat(ticker.getLastCny()).isEqualTo(new BigDecimal("4.77"));
    assertThat(ticker.getLastUsd()).isEqualTo(new BigDecimal("0.76"));
    assertThat(ticker.getPercent()).isEqualTo("-3.62%");
  }

  @Test
  public void testOrderBookUnmarshal() throws IOException {
    BiboxResponse<BiboxOrderBook> response =
        BiboxTestUtils.getResponse(
            new TypeReference<BiboxResponse<BiboxOrderBook>>() {},
            "/org/knowm/xchange/bibox/dto/marketdata/example-order-book.json");
    assertThat(response.getCmd()).isEqualTo("depth");

    BiboxOrderBook orderBook = response.getResult();
    assertThat(orderBook.getPair()).isEqualTo("BIX_BTC");
    assertThat(orderBook.getUpdateTime()).isEqualTo(1518133407425L);
    assertThat(orderBook.getAsks()).hasSize(2);
    assertThat(orderBook.getBids()).hasSize(2);
    assertThat(orderBook.getAsks().get(0).getPrice()).isEqualTo(BigDecimal.valueOf(0.00010202));
    assertThat(orderBook.getAsks().get(0).getVolume()).isEqualTo(BigDecimal.valueOf(100));
    assertThat(orderBook.getBids().get(0).getPrice()).isEqualTo(BigDecimal.valueOf(0.00010179));
    assertThat(orderBook.getBids().get(0).getVolume()).isEqualTo(BigDecimal.valueOf(127.7667));
  }

  @Test
  public void testAllMarketsUnmarshal() throws IOException {
    BiboxResponse<List<BiboxMarket>> response =
        BiboxTestUtils.getResponse(
            new TypeReference<BiboxResponse<List<BiboxMarket>>>() {},
            "/org/knowm/xchange/bibox/dto/marketdata/example-all-markets.json");
    assertThat(response.getCmd()).isEqualTo("marketAll");

    List<BiboxMarket> markets = response.getResult();
    assertThat(markets).hasSize(2);

    BiboxMarket first = markets.get(0);
    assertThat(first.getId()).isEqualTo(1);
    assertThat(first.getCoinSymbol()).isEqualTo("BIX");
    assertThat(first.getCurrencySymbol()).isEqualTo("BTC");
    assertThat(first.getLast()).isEqualTo(new BigDecimal("0.00010186"));
    assertThat(first.getHigh()).isEqualTo(new BigDecimal("0.00012146"));
    assertThat(first.getLow()).isEqualTo(new BigDecimal("0.00009950"));
    assertThat(first.getChange()).isEqualTo(new BigDecimal("-0.00001812"));
    assertThat(first.getVol24H()).isEqualTo(new BigDecimal("6511185"));
    assertThat(first.getAmount()).isEqualTo(new BigDecimal("706.60"));
    assertThat(first.getLastCny()).isEqualTo(new BigDecimal("5.25"));
    assertThat(first.getHighCny()).isEqualTo(new BigDecimal("6.26"));
    assertThat(first.getLowCny()).isEqualTo(new BigDecimal("5.13"));
    assertThat(first.getLastUsd()).isEqualTo(new BigDecimal("0.83"));
    assertThat(first.getHighUsd()).isEqualTo(new BigDecimal("0.99"));
    assertThat(first.getLowUsd()).isEqualTo(new BigDecimal("0.81"));

    BiboxMarket second = markets.get(1);
    assertThat(second.getId()).isEqualTo(2);
    assertThat(second.getCoinSymbol()).isEqualTo("BIX");
    assertThat(second.getCurrencySymbol()).isEqualTo("ETH");
    assertThat(second.getLast()).isEqualTo(new BigDecimal("0.00100755"));
    assertThat(second.getHigh()).isEqualTo(new BigDecimal("0.00124305"));
    assertThat(second.getLow()).isEqualTo(new BigDecimal("0.00095500"));
    assertThat(second.getChange()).isEqualTo(new BigDecimal("-0.00019295"));
    assertThat(second.getVol24H()).isEqualTo(new BigDecimal("6359569"));
    assertThat(second.getAmount()).isEqualTo(new BigDecimal("6835.14"));
    assertThat(second.getLastCny()).isEqualTo(new BigDecimal("5.25"));
    assertThat(second.getHighCny()).isEqualTo(new BigDecimal("6.48"));
    assertThat(second.getLowCny()).isEqualTo(new BigDecimal("4.98"));
    assertThat(second.getLastUsd()).isEqualTo(new BigDecimal("0.83"));
    assertThat(second.getHighUsd()).isEqualTo(new BigDecimal("1.03"));
    assertThat(second.getLowUsd()).isEqualTo(new BigDecimal("0.79"));
  }
}
