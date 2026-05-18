package info.bitrich.xchangestream.bybit;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import io.reactivex.rxjava3.core.Observable;
import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.CandleStickData;
import org.knowm.xchange.dto.marketdata.CandleStickInterval;
import org.knowm.xchange.instrument.Instrument;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BybitStreamingMarketDataServiceTest {

  private BybitStreamingMarketDataService marketDataService;
  private BybitStreamingService streamingService;
  private final ObjectMapper mapper = StreamingObjectMapperHelper.getObjectMapper();

  @Before
  public void setUp() {
    streamingService = mock(BybitStreamingService.class);
    marketDataService = new BybitStreamingMarketDataService(streamingService);
  }

  @Test
  public void testGetCandleStick() throws Exception {
    JsonNode jsonNode =
        mapper.readTree(
            ClassLoader.getSystemClassLoader().getResourceAsStream("getCandleStickResponse.json"));

    when(streamingService.subscribeChannel(anyString())).thenReturn(Observable.just(jsonNode));

    Instrument instrument = CurrencyPair.BTC_USDT;
    Observable<CandleStickData> candleStickDataObservable = marketDataService.getCandleStick(instrument, CandleStickInterval.m5);

    CandleStickData candleStickData = candleStickDataObservable.blockingFirst();

    assertThat(candleStickData).isNotNull();
    assertThat(candleStickData.getInstrument()).isEqualTo(instrument);
    assertThat(candleStickData.getCandleSticks()).hasSize(1);
    assertThat(candleStickData.getCandleSticks().get(0).getOpen()).isEqualByComparingTo(new BigDecimal("16649.5"));
    assertThat(candleStickData.getCandleSticks().get(0).getClose()).isEqualByComparingTo(new BigDecimal("16677"));
    assertThat(candleStickData.getCandleSticks().get(0).getHigh()).isEqualByComparingTo(new BigDecimal("16677"));
    assertThat(candleStickData.getCandleSticks().get(0).getLow()).isEqualByComparingTo(new BigDecimal("16608"));
    assertThat(candleStickData.getCandleSticks().get(0).getVolume()).isEqualByComparingTo(new BigDecimal("2.081"));
    assertThat(candleStickData.getCandleSticks().get(0).getQuotaVolume()).isEqualByComparingTo(new BigDecimal("34666.4005"));
    assertThat(candleStickData.getCandleSticks().get(0).getTimestamp().toEpochMilli()).isEqualTo(1672324988882L);
  }
}
