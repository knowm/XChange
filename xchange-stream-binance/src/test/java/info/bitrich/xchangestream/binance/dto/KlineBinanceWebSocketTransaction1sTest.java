package info.bitrich.xchangestream.binance.dto;

import static com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_COMMENTS;
import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static info.bitrich.xchangestream.binance.dto.BaseBinanceWebSocketTransaction.BinanceWebSocketTypes.KLINE;
import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.binance.dto.market.KlineBinanceWebSocketTransaction;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import junit.framework.TestCase;
import org.junit.Test;
import org.knowm.xchange.binance.BinanceAdapters;
import org.knowm.xchange.binance.dto.marketdata.BinanceKline;
import org.knowm.xchange.binance.dto.marketdata.KlineInterval;
import org.knowm.xchange.currency.CurrencyPair;

/** Test deserialization of a 1-second kline WebSocket event. */
public class KlineBinanceWebSocketTransaction1sTest extends TestCase {

  private final ObjectMapper mapper =
      new ObjectMapper().enable(ALLOW_COMMENTS).disable(FAIL_ON_UNKNOWN_PROPERTIES);

  @Test
  public void testMapping1sKline() throws IOException {
    BinanceAdapters.putSymbolMapping("BTCUSDT", CurrencyPair.BTC_USDT);
    InputStream stream = this.getClass().getResourceAsStream("testKlineEvent1s.json");

    KlineBinanceWebSocketTransaction transaction =
        mapper.readValue(stream, KlineBinanceWebSocketTransaction.class);

    assertThat(transaction).isNotNull();
    assertThat(transaction.eventType).isEqualTo(KLINE);
    assertThat(transaction.getKlineInterval()).isEqualTo(KlineInterval.s1);

    BinanceKline kline = transaction.toBinanceKline(false);
    assertThat(kline.getInstrument()).isEqualTo(CurrencyPair.BTC_USDT);
    assertThat(kline.getInterval()).isEqualTo(KlineInterval.s1);
    assertThat(kline.getOpenTime()).isEqualTo(1711152000000L);
    assertThat(kline.getCloseTime()).isEqualTo(1711152000999L);
    assertThat(kline.getOpen()).isEqualTo(new BigDecimal("65000.10"));
    assertThat(kline.getClose()).isEqualTo(new BigDecimal("65001.50"));
    assertThat(kline.getHigh()).isEqualTo(new BigDecimal("65002.00"));
    assertThat(kline.getLow()).isEqualTo(new BigDecimal("64999.80"));
    assertThat(kline.getVolume()).isEqualTo(new BigDecimal("1.234"));
    assertThat(kline.getNumberOfTrades()).isEqualTo(42L);
    assertThat(kline.getQuoteAssetVolume()).isEqualTo(new BigDecimal("80215.50"));
    assertThat(kline.isClosed()).isTrue();
  }
}
