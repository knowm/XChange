package org.knowm.xchange.yacuna;

import java.math.BigDecimal;
import java.util.Date;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.yacuna.dto.marketdata.YacunaTicker;

/**
 * Created by Yingzhe on 12/27/2014.
 */
public class YacunaAdapters {

  public static Ticker adaptTicker(YacunaTicker ticker, CurrencyPair currencyPair) {

    BigDecimal last = ticker.getOverallStatistics() != null && ticker.getOverallStatistics().getLastPricePair() != null
        ? ticker.getOverallStatistics().getLastPricePair().getAmount() : null;
    BigDecimal high = ticker.getDailyStatistics() != null && ticker.getDailyStatistics().getHighPricePair() != null
        ? ticker.getDailyStatistics().getHighPricePair().getAmount() : null;
    BigDecimal low = ticker.getDailyStatistics() != null && ticker.getDailyStatistics().getLowPricePair() != null
        ? ticker.getDailyStatistics().getLowPricePair().getAmount() : null;
    BigDecimal buy = ticker.getOverallStatistics() != null && ticker.getOverallStatistics().getBuyPricePair() != null
        ? ticker.getOverallStatistics().getBuyPricePair().getAmount() : null;
    BigDecimal sell = ticker.getOverallStatistics() != null && ticker.getOverallStatistics().getSellPricePair() != null
        ? ticker.getOverallStatistics().getSellPricePair().getAmount() : null;
    BigDecimal volume = ticker.getDailyStatistics() != null && ticker.getDailyStatistics().getVolumePair() != null
        ? ticker.getDailyStatistics().getVolumePair().getAmount() : null;
    Date date = ticker.getOverallStatistics() != null ? ticker.getOverallStatistics().getTimestamp() : new Date();

    return new Ticker.Builder().currencyPair(currencyPair).last(last).high(high).low(low).bid(buy).ask(sell).volume(volume).timestamp(date).build();
  }
}
