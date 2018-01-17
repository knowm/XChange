package org.knowm.xchange.kucoin.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Test;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Jan Akerman
 */
public class KucoinTickerTest {

  @Test
  public void testUnmarshall() throws Exception {
    InputStream is = KucoinTickerTest.class.getResourceAsStream("/marketdata/single-ticker-data.json");

    KucoinResponse<KucoinTicker> result = tickerTransaction(is);
    assertThat(result).isNotNull();
    assertThat(result.getCode()).isEqualTo("OK");
    assertThat(result.getMessage()).isEqualTo("Operation succeeded.");
    assertThat(result.getTimestamp()).isEqualTo(1516210452950L);
    assertThat(result.isSuccess()).isTrue();

    KucoinTicker ticker = result.getData();
    assertThat(ticker).isNotNull();
    assertThat(ticker.getCoinType()).isEqualTo(Currency.getInstance("XAS"));
    assertThat(ticker.isTrading()).isTrue();
    assertThat(ticker.getSymbol()).isEqualTo(new CurrencyPair("XAS", "BTC"));
    assertThat(ticker.getLastDealPrice()).isEqualByComparingTo(BigDecimal.valueOf(0.000073));
    assertThat(ticker.getBuy()).isEqualByComparingTo(BigDecimal.valueOf(0.00007199));
    assertThat(ticker.getSell()).isEqualByComparingTo(BigDecimal.valueOf(0.00007479));
    assertThat(ticker.getChange()).isEqualByComparingTo(BigDecimal.valueOf(-0.000007));
    assertThat(ticker.getCoinTypePair()).isEqualTo(Currency.getInstance("BTC"));
    assertThat(ticker.getSort()).isEqualByComparingTo(BigDecimal.valueOf(0));
    assertThat(ticker.getFeeRate()).isEqualByComparingTo(BigDecimal.valueOf(0.001));
    assertThat(ticker.getVolumeValue()).isEqualByComparingTo(BigDecimal.valueOf(10.47014496));
    assertThat(ticker.getHigh()).isEqualByComparingTo(BigDecimal.valueOf(0.000088));
    assertThat(ticker.getDatetime()).isEqualTo(1516210452000L);
    assertThat(ticker.getVol()).isEqualByComparingTo(BigDecimal.valueOf(143660.02334));
    assertThat(ticker.getLow()).isEqualByComparingTo(BigDecimal.valueOf(0.00006611));
  }

  private KucoinResponse<KucoinTicker> tickerTransaction(InputStream s) throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    return mapper.readValue(s, new TypeReference<KucoinResponse<KucoinTicker>>() {
    });
  }
}
