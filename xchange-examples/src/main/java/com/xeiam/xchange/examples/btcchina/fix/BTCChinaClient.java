package com.xeiam.xchange.examples.btcchina.fix;

import java.io.IOException;
import java.io.InputStream;

import quickfix.ConfigError;
import quickfix.DefaultMessageFactory;
import quickfix.DoNotSend;
import quickfix.FileLogFactory;
import quickfix.FileStoreFactory;
import quickfix.Initiator;
import quickfix.LogFactory;
import quickfix.MessageFactory;
import quickfix.MessageStoreFactory;
import quickfix.SessionNotFound;
import quickfix.SessionSettings;
import quickfix.SocketInitiator;

import com.xeiam.xchange.btcchina.service.fix.BTCChinaApplication;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.Ticker;

public class BTCChinaClient {

  public static void main(String args[]) throws ConfigError, DoNotSend, IOException, SessionNotFound, InterruptedException {

    BTCChinaApplication app = new BTCChinaApplication() {
      @Override
      public void onLogon(quickfix.SessionID sessionId) {
        super.onLogon(sessionId);
        this.requestSnapshot(CurrencyPair.BTC_CNY, sessionId);
        this.requestSnapshotAndUpdates(CurrencyPair.BTC_CNY, sessionId);
      }

      @Override
      protected void onTicker(Ticker ticker) {

        System.out.println(ticker);
      };

    };

    InputStream inputStream = BTCChinaClient.class.getResourceAsStream("client.cfg");
    SessionSettings settings = new SessionSettings(inputStream);
    MessageStoreFactory storeFactory = new FileStoreFactory(settings);
    LogFactory logFactory = new FileLogFactory(settings);
    MessageFactory messageFactory = new DefaultMessageFactory();
    Initiator initiator = new SocketInitiator(app, storeFactory, settings, logFactory, messageFactory);
    initiator.block();
  }

}
