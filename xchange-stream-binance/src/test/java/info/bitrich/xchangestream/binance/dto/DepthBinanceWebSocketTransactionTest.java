package info.bitrich.xchangestream.binance.dto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.binance.dto.market.DepthBinanceWebSocketTransaction;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Map.Entry;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.binance.dto.marketdata.BinanceOrderbook;

class DepthBinanceWebSocketTransactionTest {
  private static ObjectMapper mapper;

  @BeforeAll
  static void setupClass() {
    JsonFactory jf = new JsonFactory();
    jf.enable(JsonParser.Feature.ALLOW_COMMENTS);
    mapper = new ObjectMapper(jf);
  }

  @Test
  void mapping() throws Exception {
    InputStream stream = this.getClass().getResourceAsStream("testDepthEvent.json");
    DepthBinanceWebSocketTransaction transaction =
        mapper.readValue(stream, DepthBinanceWebSocketTransaction.class);
    assertThat(transaction.getEventType())
        .isEqualTo(BaseBinanceWebSocketTransaction.BinanceWebSocketTypes.DEPTH_UPDATE);

    BinanceOrderbook orderBook = transaction.getOrderBook();

    Iterator<Entry<BigDecimal, BigDecimal>> bidIterator = orderBook.bids.entrySet().iterator();
    assertOrderBookEntry(bidIterator, 0.10376590, 59.15767010);

    Iterator<Entry<BigDecimal, BigDecimal>> askIterator = orderBook.asks.entrySet().iterator();
    assertOrderBookEntry(askIterator, 0.10376586, 159.15767010);
    assertOrderBookEntry(askIterator, 0.10383109, 345.86845230);
    assertOrderBookEntry(askIterator, 0.10490700, 0.00000000);
  }

  private void assertOrderBookEntry(
      Iterator<Entry<BigDecimal, BigDecimal>> entryIterator, double price, double volume) {
    Entry<BigDecimal, BigDecimal> firstAskEntry = entryIterator.next();
    assertThat(firstAskEntry.getKey().doubleValue()).isCloseTo(price, within(0.0));
    assertThat(firstAskEntry.getValue().doubleValue()).isCloseTo(volume, within(0.0));
  }
}
