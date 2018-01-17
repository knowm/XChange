package org.knowm.xchange.kucoin.dto;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.kucoin.dto.account.KucoinUserInfoResponse;
import org.knowm.xchange.kucoin.dto.marketdata.KucoinOrderBook;
import org.knowm.xchange.kucoin.dto.marketdata.KucoinTicker;

public class KucoinAdapters {
  public static String adaptCurrencyPair(CurrencyPair pair) {
    return pair.counter.getCurrencyCode() + "-" + pair.base.getCurrencyCode();
  }
  
  public static Ticker adaptTicker(KucoinResponse<KucoinTicker> tickResponse, CurrencyPair pair) {
    KucoinTicker kcTick = tickResponse.getData();
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

  public static OrderBook adaptOrderBook(KucoinResponse<KucoinOrderBook> response,
      CurrencyPair currencyPair) {
    KucoinOrderBook kcOrders = response.getData();
    Date timestamp = new Date(response.getTimestamp());
    List<LimitOrder> asks = new LinkedList<>();
    kcOrders.getSell().stream()
        .forEach(s -> asks.add(adaptLimitOrder(currencyPair, OrderType.ASK, s, timestamp)));
    List<LimitOrder> bids = new LinkedList<>();
    kcOrders.getBuy().stream()
        .forEach(s -> bids.add(adaptLimitOrder(currencyPair, OrderType.BID, s, timestamp)));
    return new OrderBook(timestamp, asks, bids);
  }
  
  private static LimitOrder adaptLimitOrder(CurrencyPair currencyPair, OrderType orderType,
      List<BigDecimal> kucoinLimitOrder, Date timestamp) {
    return new LimitOrder(orderType, kucoinLimitOrder.get(1), currencyPair, null, timestamp, kucoinLimitOrder.get(0));
  }
}
