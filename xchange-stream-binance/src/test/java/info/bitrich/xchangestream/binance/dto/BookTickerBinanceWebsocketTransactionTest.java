package info.bitrich.xchangestream.binance.dto;

import static info.bitrich.xchangestream.binance.dto.BaseBinanceWebSocketTransaction.BinanceWebSocketTypes.BOOK_TICKER;
import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;
import org.junit.BeforeClass;
import org.junit.Test;
import org.knowm.xchange.binance.BinanceAdapters;
import org.knowm.xchange.binance.dto.marketdata.BinanceBookTicker;

public class BookTickerBinanceWebsocketTransactionTest {

  private static ObjectMapper mapper;

  @BeforeClass
  public static void setupClass() {
    JsonFactory jf = new JsonFactory();
    jf.enable(JsonParser.Feature.ALLOW_COMMENTS);
    mapper = new ObjectMapper(jf);
  }

  @Test
  public void test_deserialization_of_transaction_message() throws IOException {
    InputStream stream =
        BookTickerBinanceWebsocketTransactionTest.class.getResourceAsStream(
            "testBookTickerEvent.json");
    BookTickerBinanceWebSocketTransaction tickerTransaction =
        mapper.readValue(stream, BookTickerBinanceWebSocketTransaction.class);

    assertThat(tickerTransaction).isNotNull();
    assertThat(tickerTransaction.eventType).isEqualTo(BOOK_TICKER);
    assertThat(tickerTransaction.getEventTime().getTime())
        .isLessThanOrEqualTo(new Date().getTime());
    assertThat(tickerTransaction.getCurrencyPair())
        .isEqualTo(BinanceAdapters.adaptSymbol("BNBUSDT"));

    BinanceBookTicker ticker = tickerTransaction.getTicker();
    assertThat(ticker.getUpdateId()).isEqualTo(400900217);
    assertThat(ticker.getBidPrice()).isEqualByComparingTo(BigDecimal.valueOf(25.35190000));
    assertThat(ticker.getBidQty()).isEqualByComparingTo(BigDecimal.valueOf(31.21000000));
    assertThat(ticker.getAskPrice()).isEqualByComparingTo(BigDecimal.valueOf(25.36520000));
    assertThat(ticker.getAskQty()).isEqualByComparingTo(BigDecimal.valueOf(40.66000000));
  }
}
