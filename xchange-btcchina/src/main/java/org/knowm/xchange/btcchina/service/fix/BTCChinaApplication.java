package org.knowm.xchange.btcchina.service.fix;

import java.math.BigDecimal;

import org.knowm.xchange.btcchina.BTCChinaAdapters;
import org.knowm.xchange.btcchina.service.fix.fix44.AccountInfoRequest;
import org.knowm.xchange.btcchina.service.fix.fix44.AccountInfoResponse;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.utils.nonce.CurrentNanosecondTimeIncrementalNonceFactory;
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
import quickfix.fix44.NewOrderSingle;
import quickfix.fix44.OrderCancelRequest;
import quickfix.fix44.OrderMassStatusRequest;
import quickfix.fix44.OrderStatusRequest;
import si.mazi.rescu.SynchronizedValueFactory;

/**
 * {@link Application} implementation for BTCChina.
 */
public class BTCChinaApplication extends MessageCracker implements Application {

  private final Logger log = LoggerFactory.getLogger(BTCChinaApplication.class);

  private SynchronizedValueFactory<Long> nonceFactory = new CurrentNanosecondTimeIncrementalNonceFactory();

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
  public void onLogon(SessionID sessionId) {

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
    onTicker(ticker, sessionId);
  }

  @Override
  public void onMessage(MarketDataIncrementalRefresh message, SessionID sessionId) throws FieldNotFound {

    log.debug("{}", message);

    setTicker(BTCChinaFIXAdapters.adaptUpdate(getTicker(), message));
    onTicker(ticker, sessionId);
  }

  public void onMessage(AccountInfoResponse message, SessionID sessionId) throws FieldNotFound {

    log.debug("{}", message);

    onAccountInfo(message.getAccReqID().getValue(), new AccountInfo(BTCChinaFIXAdapters.adaptWallet(message)), sessionId);
  }

  /**
   * Callback of ticker refreshed.
   *
   * @param ticker the refreshed ticker.
   * @param sessionId the FIX session ID.
   */
  protected void onTicker(Ticker ticker, SessionID sessionId) {
  }

  /**
   * Callback of account info got from server.
   *
   * @param accReqId the account request ID as assigned in the request.
   * @param accountInfo the account info.
   */
  protected void onAccountInfo(String accReqId, AccountInfo accountInfo, SessionID sessionId) {
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

  public void requestAccountInfo(String accessKey, String secretKey, String accReqId, SessionID sessionId) {

    AccountInfoRequest message = BTCChinaTradeRequest.createAccountInfoRequest(nonceFactory.createValue(), accessKey, secretKey, accReqId);
    sendMessage(message, sessionId);
  }

  public void placeOrder(String accessKey, String secretKey, String clOrdId, char side, char ordType, BigDecimal orderQty, BigDecimal price,
      String symbol, SessionID sessionId) {

    NewOrderSingle message = BTCChinaTradeRequest.createNewOrderSingle(nonceFactory.createValue(), accessKey, secretKey, clOrdId, side, ordType,
        orderQty, price, symbol);
    sendMessage(message, sessionId);
  }

  public void cancelOrder(String accessKey, String secretKey, String clOrdId, String orderId, String symbol, SessionID sessionId) {

    OrderCancelRequest message = BTCChinaTradeRequest.createOrderCancelRequest(nonceFactory.createValue(), accessKey, secretKey, clOrdId, orderId,
        symbol);
    sendMessage(message, sessionId);
  }

  public void requestOrderMassStatus(String accessKey, String secretKey, String massStatusReqId, int massStatusReqType, String symbol,
      SessionID sessionId) {

    OrderMassStatusRequest message = BTCChinaTradeRequest.createOrderMassStatusRequest(nonceFactory.createValue(), accessKey, secretKey,
        massStatusReqId, massStatusReqType, symbol);
    sendMessage(message, sessionId);
  }

  public void requestOrderStatus(String accessKey, String secretKey, String clOrdId, String orderId, String symbol, SessionID sessionId) {

    OrderStatusRequest message = BTCChinaTradeRequest.createOrderStatusRequest(nonceFactory.createValue(), accessKey, secretKey, clOrdId, orderId,
        symbol);
    sendMessage(message, sessionId);
  }

  private void sendMessage(Message message, SessionID sessionId) {

    Session.lookupSession(sessionId).send(message);
  }

  private String adaptSymbol(CurrencyPair currencyPair) {

    return BTCChinaAdapters.adaptMarket(currencyPair).toUpperCase();
  }

  @Override
  public void crack(Message message, SessionID sessionId) throws UnsupportedMessageType, FieldNotFound, IncorrectTagValue {

    if (message instanceof AccountInfoResponse) {
      onMessage((AccountInfoResponse) message, sessionId);
    } else {
      super.crack(message, sessionId);
    }
  }

}
