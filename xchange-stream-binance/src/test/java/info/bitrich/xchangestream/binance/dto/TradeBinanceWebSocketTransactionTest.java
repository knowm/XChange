package info.bitrich.xchangestream.binance.dto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.BeforeClass;
import org.junit.Test;

public class TradeBinanceWebSocketTransactionTest {
  private static ObjectMapper mapper;

  @BeforeClass
  public static void setupClass() {
    JsonFactory jf = new JsonFactory();
    jf.enable(JsonParser.Feature.ALLOW_COMMENTS);
    mapper = new ObjectMapper(jf);
  }

  @Test
  public void testMapping() throws Exception {
    InputStream stream = this.getClass().getResourceAsStream("testTradeEvent.json");
    TradeBinanceWebsocketTransaction transaction =
        mapper.readValue(stream, TradeBinanceWebsocketTransaction.class);
    assertEquals(
        BaseBinanceWebSocketTransaction.BinanceWebSocketTypes.TRADE, transaction.getEventType());

    BinanceRawTrade rawTrade = transaction.getRawTrade();

    assertThat(rawTrade.getEventType()).isEqualTo("trade");
    assertThat(rawTrade.getEventTime()).isEqualTo("123456789");
    assertThat(rawTrade.getSymbol()).isEqualTo("BNBBTC");
    assertThat(rawTrade.getTradeId()).isEqualByComparingTo(12345L);

    assertThat(rawTrade.getPrice()).isEqualByComparingTo(BigDecimal.valueOf(0.001));
    assertThat(rawTrade.getQuantity()).isEqualByComparingTo(BigDecimal.valueOf(100));
    assertThat(rawTrade.getBuyerOrderId()).isEqualByComparingTo(88L);
    assertThat(rawTrade.getSellerOrderId()).isEqualByComparingTo(50L);
    assertThat(rawTrade.getTimestamp()).isEqualByComparingTo(123456785L);
    assertThat(rawTrade.isBuyerMarketMaker());
    assertThat(rawTrade.isIgnore());
  }
}
