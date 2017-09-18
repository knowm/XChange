package org.knowm.xchange.bleutrade;

import static org.fest.assertions.api.Assertions.assertThat;

import org.knowm.xchange.bleutrade.dto.account.BleutradeBalance;
import org.knowm.xchange.bleutrade.dto.marketdata.BleutradeCurrency;
import org.knowm.xchange.bleutrade.dto.marketdata.BleutradeMarket;
import org.knowm.xchange.bleutrade.dto.marketdata.BleutradeTicker;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.meta.CurrencyPairMetaData;
import org.knowm.xchange.dto.trade.LimitOrder;

public class BleutradeAssert {

  public static void assertEquals(BleutradeTicker o1, BleutradeTicker o2) {
    assertThat(o1.getBid()).isEqualTo(o2.getBid());
    assertThat(o1.getAsk()).isEqualTo(o2.getAsk());
    assertThat(o1.getMarketName()).isEqualTo(o2.getMarketName());
    assertThat(o1.getHigh()).isEqualTo(o2.getHigh());
    assertThat(o1.getLast()).isEqualTo(o2.getLast());
    assertThat(o1.getLow()).isEqualTo(o2.getLow());
    assertThat(o1.getTimeStamp()).isEqualTo(o2.getTimeStamp());
    assertThat(o1.getVolume()).isEqualTo(o2.getVolume());
    assertThat(o1.getAverage()).isEqualTo(o2.getAverage());
    assertThat(o1.getBaseVolume()).isEqualTo(o2.getBaseVolume());
    assertThat(o1.getIsActive()).isEqualTo(o2.getIsActive());
    assertThat(o1.getPrevDay()).isEqualTo(o2.getPrevDay());
  }

  public static void assertEquals(Balance o1, Balance o2) {
    assertThat(o1.getCurrency()).isEqualTo(o2.getCurrency());
    assertThat(o1.getTotal()).isEqualTo(o2.getTotal());
    assertThat(o1.getAvailable()).isEqualTo(o2.getAvailable());
    assertThat(o1.getFrozen()).isEqualTo(o2.getFrozen());
  }

  public static void assertEquals(Trade o1, Trade o2) {
    assertThat(o1.getType()).isEqualTo(o2.getType());
    assertThat(o1.getTradableAmount()).isEqualTo(o2.getTradableAmount());
    assertThat(o1.getCurrencyPair()).isEqualTo(o2.getCurrencyPair());
    assertThat(o1.getPrice()).isEqualTo(o2.getPrice());
    assertThat(o1.getTimestamp()).isEqualTo(o2.getTimestamp());
    assertThat(o1.getId()).isEqualTo(o2.getId());
  }

  public static void assertEquals(LimitOrder o1, LimitOrder o2) {
    assertThat(o1.getId()).isEqualTo(o2.getId());
    assertThat(o1.getType()).isEqualTo(o2.getType());
    assertThat(o1.getCurrencyPair()).isEqualTo(o2.getCurrencyPair());
    assertThat(o1.getLimitPrice()).isEqualTo(o2.getLimitPrice());
    assertThat(o1.getTradableAmount()).isEqualTo(o2.getTradableAmount());
    assertThat(o1.getRemainingAmount()).isEqualTo(o2.getRemainingAmount());
    assertThat(o1.getTimestamp()).isEqualTo(o2.getTimestamp());
  }

  public static void assertEquals(Ticker o1, Ticker o2) {
    assertThat(o1.getBid()).isEqualTo(o2.getBid());
    assertThat(o1.getAsk()).isEqualTo(o2.getAsk());
    assertThat(o1.getCurrencyPair()).isEqualTo(o2.getCurrencyPair());
    assertThat(o1.getHigh()).isEqualTo(o2.getHigh());
    assertThat(o1.getLast()).isEqualTo(o2.getLast());
    assertThat(o1.getLow()).isEqualTo(o2.getLow());
    assertThat(o1.getTimestamp()).isEqualTo(o2.getTimestamp());
    assertThat(o1.getVolume()).isEqualTo(o2.getVolume());
    assertThat(o1.getVwap()).isEqualTo(o2.getVwap());
  }

  public static void assertEquals(CurrencyPairMetaData o1, CurrencyPairMetaData o2) {
    assertThat(o1.getMinimumAmount()).isEqualTo(o2.getMinimumAmount());
    assertThat(o1.getPriceScale()).isEqualTo(o2.getPriceScale());
    assertThat(o1.getTradingFee()).isEqualTo(o2.getTradingFee());
  }

  public static void assertEquals(BleutradeCurrency o1, BleutradeCurrency o2) {
    assertThat(o1.getCurrency()).isEqualTo(o2.getCurrency());
    assertThat(o1.getCurrencyLong()).isEqualTo(o2.getCurrencyLong());
    assertThat(o1.getMinConfirmation()).isEqualTo(o2.getMinConfirmation());
    assertThat(o1.getTxFee()).isEqualTo(o2.getTxFee());
    assertThat(o1.getIsActive()).isEqualTo(o2.getIsActive());
    assertThat(o1.getCoinType()).isEqualTo(o2.getCoinType());
  }

  public static void assertEquals(BleutradeMarket o1, BleutradeMarket o2) {
    assertThat(o1.getMarketCurrency()).isEqualTo(o2.getMarketCurrency());
    assertThat(o1.getBaseCurrency()).isEqualTo(o2.getBaseCurrency());
    assertThat(o1.getMarketCurrencyLong()).isEqualTo(o2.getMarketCurrencyLong());
    assertThat(o1.getBaseCurrencyLong()).isEqualTo(o2.getBaseCurrencyLong());
    assertThat(o1.getMinTradeSize()).isEqualTo(o2.getMinTradeSize());
    assertThat(o1.getMarketName()).isEqualTo(o2.getMarketName());
    assertThat(o1.getIsActive()).isEqualTo(o2.getIsActive());
  }

  public static void assertEquals(BleutradeBalance o1, BleutradeBalance o2) {
    assertThat(o1.getCurrency()).isEqualTo(o2.getCurrency());
    assertThat(o1.getBalance()).isEqualTo(o2.getBalance());
    assertThat(o1.getAvailable()).isEqualTo(o2.getAvailable());
    assertThat(o1.getPending()).isEqualTo(o2.getPending());
    assertThat(o1.getCryptoAddress()).isEqualTo(o2.getCryptoAddress());
    assertThat(o1.getIsActive()).isEqualTo(o2.getIsActive());
  }
}
