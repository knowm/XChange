package org.knowm.xchange.kucoin;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.kucoin.dto.marketdata.KucoinTicker;

/**
 * @author Jan Akerman
 */
public class KucoinAdaptersTest {

  @Test
  public void adaptTicker() {
    KucoinTicker kucoinTicker = new KucoinTicker(
        "XAS",
        true,
        "XAS-BTC",
        BigDecimal.valueOf(0.000073),
        BigDecimal.valueOf(0.00007199),
        BigDecimal.valueOf(0.00007479),
        BigDecimal.valueOf(-0.000007),
        "BTC",
        BigDecimal.valueOf(0),
        BigDecimal.valueOf(0.001),
        BigDecimal.valueOf(10.47014496),
        BigDecimal.valueOf(0.000088),
        1516210452000L,
        BigDecimal.valueOf(143660.02334),
        BigDecimal.valueOf(0.00006611),
        BigDecimal.valueOf(-0.0875));

    Ticker ticker = KucoinAdapters.adaptTicker(kucoinTicker);

    assertThat(ticker.getCurrencyPair()).isEqualTo(new CurrencyPair("XAS", "BTC"));
    assertThat(ticker.getOpen()).isNull();
    assertThat(ticker.getLast()).isEqualByComparingTo(kucoinTicker.getLastDealPrice());
    assertThat(ticker.getBid()).isEqualByComparingTo(kucoinTicker.getBuy());
    assertThat(ticker.getAsk()).isEqualByComparingTo(kucoinTicker.getSell());
    assertThat(ticker.getHigh()).isEqualByComparingTo(kucoinTicker.getHigh());
    assertThat(ticker.getLow()).isEqualByComparingTo(kucoinTicker.getLow());
    assertThat(ticker.getVwap()).isNull();
    assertThat(ticker.getVolume()).isEqualByComparingTo(kucoinTicker.getVol());
    assertThat(ticker.getQuoteVolume()).isEqualByComparingTo(kucoinTicker.getVol().multiply(kucoinTicker.getLastDealPrice()));
    assertThat(ticker.getTimestamp()).isEqualTo(new Date(kucoinTicker.getDatetime()));
  }

}