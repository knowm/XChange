package org.knowm.xchange.zaif;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.meta.CurrencyPairMetaData;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.zaif.dto.marketdata.ZaifFullBook;
import org.knowm.xchange.zaif.dto.marketdata.ZaifFullBookTier;
import org.knowm.xchange.zaif.dto.marketdata.ZaifMarket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZaifAdapters {

  private static Logger logger = LoggerFactory.getLogger(ZaifAdapters.class);

  public ZaifAdapters() {

  }

  public static OrderBook adaptOrderBook(ZaifFullBook book, CurrencyPair currencyPair) {

    List<LimitOrder> asks = toLimitOrderList(book.getAsks(), Order.OrderType.ASK, currencyPair);
    List<LimitOrder> bids = toLimitOrderList(book.getBids(), Order.OrderType.BID, currencyPair);

    return new OrderBook(null, asks, bids);
  }

  private static List<LimitOrder> toLimitOrderList(ZaifFullBookTier[] levels, Order.OrderType orderType, CurrencyPair currencyPair) {

    List<LimitOrder> allLevels = new ArrayList<>();

    if (levels != null) {
      for (ZaifFullBookTier tier : levels) {
        allLevels.add(new LimitOrder(orderType, tier.getVolume(), currencyPair, "", null, tier.getPrice()));
      }
    }

    return allLevels;
  }

  public static ExchangeMetaData adaptMetadata(List<ZaifMarket> markets) {
    Map<CurrencyPair, CurrencyPairMetaData> pairMeta = new HashMap<>();
    for (ZaifMarket zaifMarket : markets) {
      pairMeta.put(zaifMarket.getName(), new CurrencyPairMetaData(null, null, null, null));
    }
    return new ExchangeMetaData(pairMeta, null, null, null, null);
  }
}
