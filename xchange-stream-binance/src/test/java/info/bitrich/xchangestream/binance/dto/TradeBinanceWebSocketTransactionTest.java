package info.bitrich.xchangestream.binance.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.binance.dto.market.BinanceRawTrade;
import info.bitrich.xchangestream.binance.dto.market.TradeBinanceWebsocketTransaction;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class TradeBinanceWebSocketTransactionTest {
  private static ObjectMapper mapper;

  @BeforeAll
  static void setupClass() {
    JsonFactory jf = new JsonFactory();
    jf.enable(JsonParser.Feature.ALLOW_COMMENTS);
    mapper = new ObjectMapper(jf);
  }

  @Test
  void mapping() throws Exception {
    InputStream stream = this.getClass().getResourceAsStream("testTradeEvent.json");
    TradeBinanceWebsocketTransaction transaction =
        mapper.readValue(stream, TradeBinanceWebsocketTransaction.class);
    assertThat(transaction.getEventType())
        .isEqualTo(BaseBinanceWebSocketTransaction.BinanceWebSocketTypes.TRADE);

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
