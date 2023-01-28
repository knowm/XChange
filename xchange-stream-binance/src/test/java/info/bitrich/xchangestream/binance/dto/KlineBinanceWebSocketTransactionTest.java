package info.bitrich.xchangestream.binance.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import junit.framework.TestCase;
import org.junit.Test;
import org.knowm.xchange.binance.dto.marketdata.BinanceKline;
import org.knowm.xchange.binance.dto.marketdata.KlineInterval;
import org.knowm.xchange.currency.CurrencyPair;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import static com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_COMMENTS;
import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static info.bitrich.xchangestream.binance.dto.BaseBinanceWebSocketTransaction.BinanceWebSocketTypes.KLINE;
import static org.assertj.core.api.Assertions.assertThat;

public class KlineBinanceWebSocketTransactionTest extends TestCase {

  private final ObjectMapper mapper =
      new ObjectMapper().enable(ALLOW_COMMENTS).disable(FAIL_ON_UNKNOWN_PROPERTIES);

  @Test
  public void testMapping() throws IOException {
    InputStream stream = this.getClass().getResourceAsStream("testKlineEvent.json");

    KlineBinanceWebSocketTransaction klineBinanceWebSocketTransaction =
        mapper.readValue(stream, KlineBinanceWebSocketTransaction.class);

    assertThat(klineBinanceWebSocketTransaction).isNotNull();
    assertThat(klineBinanceWebSocketTransaction.eventType).isEqualTo(KLINE);
    assertThat(klineBinanceWebSocketTransaction.getEventTime().getTime()).isEqualTo(123456789L);

    BinanceKline binanceKline = klineBinanceWebSocketTransaction.toBinanceKline(false);
    assertThat(binanceKline.getOpenTime()).isEqualTo(123400000L);
    assertThat(binanceKline.getCloseTime()).isEqualTo(123460000L);
    assertThat(binanceKline.getInstrument()).isEqualTo(CurrencyPair.XMR_USDT);
    assertThat(binanceKline.getInterval()).isEqualTo(KlineInterval.m1);
    assertThat(binanceKline.getOpen()).isEqualTo(new BigDecimal("0.0010"));
    assertThat(binanceKline.getClose()).isEqualTo(new BigDecimal("0.0020"));
    assertThat(binanceKline.getHigh()).isEqualTo(new BigDecimal("0.0025"));
    assertThat(binanceKline.getLow()).isEqualTo(new BigDecimal("0.0005"));
    assertThat(binanceKline.getVolume()).isEqualTo(new BigDecimal("1000"));
    assertThat(binanceKline.getNumberOfTrades()).isEqualTo(100L);
    assertThat(binanceKline.getQuoteAssetVolume()).isEqualTo(new BigDecimal("1.0000"));
    assertThat(binanceKline.getTakerBuyBaseAssetVolume()).isEqualTo(new BigDecimal("500"));
    assertThat(binanceKline.getTakerBuyQuoteAssetVolume()).isEqualTo(new BigDecimal("0.500"));
  }
}
