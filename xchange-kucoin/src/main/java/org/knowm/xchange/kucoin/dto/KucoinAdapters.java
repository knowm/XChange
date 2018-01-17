package org.knowm.xchange.kucoin.dto;

import java.util.Arrays;
import java.util.Date;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.kucoin.dto.account.KucoinUserInfoResponse;
import org.knowm.xchange.kucoin.dto.marketdata.KucoinTicker;

public class KucoinAdapters {
  public static String adaptCurrencyPair(CurrencyPair pair) {
    return pair.counter.getCurrencyCode() + "-" + pair.base.getCurrencyCode();
  }
  
  public static Ticker adaptTicker(KucoinTicker kcTick, CurrencyPair pair) {
    return new Ticker.Builder()
        .currencyPair(pair)
        .ask(kcTick.getBuy())
        .bid(kcTick.getSell())
        .high(kcTick.getHigh())
        .low(kcTick.getLow())
        .last(kcTick.getLastDealPrice())
        .volume(kcTick.getVol())
        .quoteVolume(kcTick.getVolValue())
        .timestamp(new Date(kcTick.getDatetime()))
        .build();
  }

  public static AccountInfo adaptAccountInfo(KucoinUserInfoResponse kucoinInfo) {
    return new AccountInfo(kucoinInfo.getUserInfo().getEmail(), Arrays.asList());
  }
}
