package org.knowm.xchange.examples.btcchina.fix;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.knowm.xchange.btcchina.service.fix.BTCChinaApplication;
import org.knowm.xchange.btcchina.service.fix.fix44.BTCChinaMessageFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import quickfix.ConfigError;
import quickfix.DoNotSend;
import quickfix.FileLogFactory;
import quickfix.FileStoreFactory;
import quickfix.Initiator;
import quickfix.LogFactory;
import quickfix.MessageFactory;
import quickfix.MessageStoreFactory;
import quickfix.SessionID;
import quickfix.SessionNotFound;
import quickfix.SessionSettings;
import quickfix.SocketInitiator;

public class BTCChinaClient {

  private static final Logger log = LoggerFactory.getLogger(BTCChinaClient.class);

  public static void main(String args[]) throws ConfigError, DoNotSend, IOException, SessionNotFound, InterruptedException {

    final String accessKey = args[0];
    final String secretKey = args[1];

    BTCChinaApplication app = new BTCChinaApplication() {
      @Override
      public void onLogon(quickfix.SessionID sessionId) {
        super.onLogon(sessionId);
        this.requestSnapshot(CurrencyPair.BTC_CNY, sessionId);
        this.requestSnapshotAndUpdates(CurrencyPair.BTC_CNY, sessionId);
      }

      @Override
      protected void onTicker(Ticker ticker, SessionID id) {

        log.info("ticker: {}", ticker);
      }

      @Override
      protected void onAccountInfo(String accReqId, AccountInfo accountInfo, SessionID id) {

        log.info("accReqId: {}, accountInfo: {}", accReqId, accountInfo);
      }
    };

    InputStream inputStream = BTCChinaClient.class.getResourceAsStream("client.cfg");
    SessionSettings settings = new SessionSettings(inputStream);
    MessageStoreFactory storeFactory = new FileStoreFactory(settings);
    LogFactory logFactory = new FileLogFactory(settings);
    MessageFactory messageFactory = new BTCChinaMessageFactory();
    Initiator initiator = new SocketInitiator(app, storeFactory, settings, logFactory, messageFactory);
    initiator.start();

    while (!initiator.isLoggedOn()) {
      TimeUnit.SECONDS.sleep(1);
    }

    log.info("logged on.");
    SessionID sessionId = initiator.getSessions().get(0);

    // account info request: U1000
    app.requestAccountInfo(accessKey, secretKey, UUID.randomUUID().toString(), sessionId);
    log.info("account info request sent.");

    TimeUnit.SECONDS.sleep(30);
    log.info("exiting...");
  }

}
