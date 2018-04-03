package org.knowm.xchange.bitcoinium;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.Test;
import org.knowm.xchange.bitcoinium.dto.marketdata.BitcoiniumDepthJSONTest;
import org.knowm.xchange.bitcoinium.dto.marketdata.BitcoiniumOrderbook;
import org.knowm.xchange.bitcoinium.dto.marketdata.BitcoiniumTicker;
import org.knowm.xchange.bitcoinium.dto.marketdata.BitcoiniumTickerJSONTest;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;

/** Tests the BitcoiniumAdapter class */
public class BitcoiniumAdapterTest {

  @Test
  public void testOrderAdapterWithDepth() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        BitcoiniumDepthJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/bitcoinium/dto/marketdata/example-depth-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitcoiniumOrderbook bitcoiniumDepth = mapper.readValue(is, BitcoiniumOrderbook.class);

    OrderBook orderBook = BitcoiniumAdapters.adaptOrderbook(bitcoiniumDepth, CurrencyPair.BTC_USD);

    // Verify all fields filled
    assertThat(orderBook.getAsks().get(0).getLimitPrice()).isEqualTo(new BigDecimal("522.9"));
    assertThat(orderBook.getAsks().get(0).getType()).isEqualTo(OrderType.ASK);
    assertThat(orderBook.getAsks().get(0).getOriginalAmount()).isEqualTo(new BigDecimal("1.07"));
    assertThat(orderBook.getAsks().get(0).getCurrencyPair()).isEqualTo(CurrencyPair.BTC_USD);
  }

  @Test
  public void testTickerAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        BitcoiniumTickerJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/bitcoinium/dto/marketdata/example-ticker-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitcoiniumTicker BitcoiniumTicker = mapper.readValue(is, BitcoiniumTicker.class);

    Ticker ticker = BitcoiniumAdapters.adaptTicker(BitcoiniumTicker, CurrencyPair.BTC_USD);
    System.out.println(ticker.toString());

    assertThat(ticker.getLast()).isEqualTo(new BigDecimal("516.8"));
    assertThat(ticker.getLow().toString()).isEqualTo("508.28");
    assertThat(ticker.getHigh().toString()).isEqualTo("523.09");
    assertThat(ticker.getVolume()).isEqualTo(new BigDecimal("3522"));
  }
}
