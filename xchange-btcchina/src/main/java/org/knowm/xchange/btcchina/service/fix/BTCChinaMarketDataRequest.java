package org.knowm.xchange.btcchina.service.fix;

import quickfix.Message;
import quickfix.field.MDEntryType;
import quickfix.field.MDReqID;
import quickfix.field.MarketDepth;
import quickfix.field.SubscriptionRequestType;
import quickfix.field.Symbol;
import quickfix.fix44.MarketDataRequest;
import quickfix.fix44.MarketDataRequest.NoMDEntryTypes;
import quickfix.fix44.MarketDataRequest.NoRelatedSym;

/**
 * FIX MarketData Request Constructors.
 */
public class BTCChinaMarketDataRequest {

  /**
   * MARKET DATA SNAPSHOT FULL REFRESH REQUEST (V)
   *
   * @return @tickerRequest request message
   */
  public static Message marketDataFullSnapRequest(String symbol) {

    return marketDataRequest(symbol, SubscriptionRequestType.SNAPSHOT);
  }

  /**
   * MARKET DATA INCREMENTAL REFRESH REQUEST (V)
   *
   * @return @tickerRequest request message
   */
  public static Message marketDataIncrementalRequest(String symbol) {

    return marketDataRequest(symbol, SubscriptionRequestType.SNAPSHOT_PLUS_UPDATES);
  }

  /**
   * UNSUBSCRIBE MARKET DATA INCREMENTAL REFRESH (V)
   *
   * @return @tickerRequest request message
   */
  public static Message unsubscribeIncrementalRequest(String symbol) {

    return marketDataRequest(symbol, SubscriptionRequestType.DISABLE_PREVIOUS_SNAPSHOT_PLUS_UPDATE_REQUEST);
  }

  private static Message marketDataRequest(String symbol, char subscriptionRequestType) {

    MarketDataRequest tickerRequest = new MarketDataRequest();

    NoRelatedSym noRelatedSym = new NoRelatedSym();
    noRelatedSym.set(new Symbol(symbol));
    tickerRequest.addGroup(noRelatedSym);

    tickerRequest.set(new MDReqID("123")); // any value
    tickerRequest.set(new SubscriptionRequestType(subscriptionRequestType));
    tickerRequest.set(new MarketDepth(0));

    addMDType(tickerRequest, MDEntryType.BID);
    addMDType(tickerRequest, MDEntryType.OFFER);
    addMDType(tickerRequest, MDEntryType.TRADE);
    addMDType(tickerRequest, MDEntryType.INDEX_VALUE);
    addMDType(tickerRequest, MDEntryType.OPENING_PRICE);
    addMDType(tickerRequest, MDEntryType.CLOSING_PRICE);
    addMDType(tickerRequest, MDEntryType.SETTLEMENT_PRICE);
    addMDType(tickerRequest, MDEntryType.TRADING_SESSION_HIGH_PRICE);
    addMDType(tickerRequest, MDEntryType.TRADING_SESSION_LOW_PRICE);
    addMDType(tickerRequest, MDEntryType.TRADING_SESSION_VWAP_PRICE);
    addMDType(tickerRequest, MDEntryType.IMBALANCE);
    addMDType(tickerRequest, MDEntryType.TRADE_VOLUME);
    addMDType(tickerRequest, MDEntryType.OPEN_INTEREST);

    return tickerRequest;
  }

  private static void addMDType(MarketDataRequest tickerRequest, char type) {

    NoMDEntryTypes g = new NoMDEntryTypes();
    g.set(new MDEntryType(type));
    tickerRequest.addGroup(g);
  }

}
