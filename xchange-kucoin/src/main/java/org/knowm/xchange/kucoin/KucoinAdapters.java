package org.knowm.xchange.kucoin;

import java.util.Date;

import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.kucoin.dto.marketdata.KucoinTicker;

/**
 * @author Jan Akerman
 */
public class KucoinAdapters {

  public static Ticker adaptTicker(KucoinTicker kucoinTicker) {
    return new Ticker.Builder()
        .last(kucoinTicker.getLastDealPrice())
        .bid(kucoinTicker.getBuy())
        .ask(kucoinTicker.getSell())
        .high(kucoinTicker.getHigh())
        .low(kucoinTicker.getLow())
        .volume(kucoinTicker.getVol())
        .currencyPair(kucoinTicker.getSymbol())
        .timestamp(new Date(kucoinTicker.getDatetime()))
        .build();
  }

}
