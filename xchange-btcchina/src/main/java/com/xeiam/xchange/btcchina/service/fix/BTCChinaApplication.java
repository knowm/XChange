package com.xeiam.xchange.btcchina.service.fix;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import quickfix.Application;
import quickfix.DoNotSend;
import quickfix.FieldNotFound;
import quickfix.IncorrectDataFormat;
import quickfix.IncorrectTagValue;
import quickfix.Message;
import quickfix.RejectLogon;
import quickfix.Session;
import quickfix.SessionID;
import quickfix.UnsupportedMessageType;
import quickfix.fix44.MarketDataIncrementalRefresh;
import quickfix.fix44.MarketDataSnapshotFullRefresh;
import quickfix.fix44.MessageCracker;

import com.xeiam.xchange.btcchina.BTCChinaAdapters;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.Ticker;

/**
 * {@link Application} implementation for BTCChina.
 */
public class BTCChinaApplication extends MessageCracker implements Application {

  private final Logger log = LoggerFactory.getLogger(BTCChinaApplication.class);

  private volatile Ticker ticker;

  @Override
  public void fromAdmin(Message message, SessionID sessionId) throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, RejectLogon {

    log.debug("fromAdmin: {}", message);
  }

  @Override
  public void toAdmin(Message message, SessionID sessionId) {

    log.debug("toAdmin: {}", message);
  }

  @Override
  public void fromApp(Message message, SessionID sessionId) throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, UnsupportedMessageType {

    log.debug("fromApp: {}", message);

    crack(message, sessionId);
  }

  @Override
  public void toApp(Message message, SessionID sessionId) throws DoNotSend {

    log.debug("toApp: {} {}", sessionId, message);
  }

  @Override
  public void onCreate(SessionID sessionId) {

  }

  @Override
  public void onLogon(final SessionID sessionId) {

    log.debug("onLogon: {}", sessionId);
  }

  @Override
  public void onLogout(SessionID sessionId) {

    log.debug("onLogout: {}", sessionId);
  }

  @Override
  public void onMessage(MarketDataSnapshotFullRefresh message, SessionID sessionId) throws FieldNotFound {

    log.debug("{}", message);

    setTicker(BTCChinaFIXAdapters.adaptTicker(message));
    onTicker(ticker);
  }

  @Override
  public void onMessage(MarketDataIncrementalRefresh message, SessionID sessionId) throws FieldNotFound {

    log.debug("{}", message);

    setTicker(BTCChinaFIXAdapters.adaptUpdate(getTicker(), message));
    onTicker(ticker);
  }

  /**
   * Callback of ticker refreshed.
   *
   * @param ticker the refreshed ticker.
   */
  protected void onTicker(Ticker ticker) {
  }

  public Ticker getTicker() {

    return ticker;
  }

  protected void setTicker(Ticker ticker) {

    this.ticker = ticker;
  }

  public void requestSnapshot(CurrencyPair currencyPair, SessionID sessionId) {

    requestSnapshot(adaptSymbol(currencyPair), sessionId);
  }

  public void requestSnapshot(String symbol, SessionID sessionId) {

    // MARKET DATA SNAPSHOT FULL REFRESH REQUEST (V)
    Message message = BTCChinaMarketDataRequest.marketDataFullSnapRequest(symbol);
    sendMessage(message, sessionId);
  }

  public void requestSnapshotAndUpdates(CurrencyPair currencyPair, SessionID sessionId) {

    requestSnapshotAndUpdates(adaptSymbol(currencyPair), sessionId);
  }

  public void requestSnapshotAndUpdates(String symbol, SessionID sessionId) {

    // MARKET DATA INCREMENTAL REFRESH REQUEST (V)
    Message message = BTCChinaMarketDataRequest.marketDataIncrementalRequest(symbol);
    sendMessage(message, sessionId);
  }

  public void unsubscribe(CurrencyPair currencyPair, SessionID sessionId) {
    unsubscribe(adaptSymbol(currencyPair), sessionId);
  }

  public void unsubscribe(String symbol, SessionID sessionId) {

    // UNSUBSCRIBE MARKET DATA INCREMENTAL REFRESH (V)
    Message message = BTCChinaMarketDataRequest.unsubscribeIncrementalRequest(symbol);
    sendMessage(message, sessionId);
  }

  private void sendMessage(Message message, SessionID sessionId) {

    Session.lookupSession(sessionId).send(message);
  }

  private String adaptSymbol(CurrencyPair currencyPair) {

    return BTCChinaAdapters.adaptMarket(currencyPair).toUpperCase();
  }

}
