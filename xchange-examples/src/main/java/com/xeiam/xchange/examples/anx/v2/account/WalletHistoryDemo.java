package com.xeiam.xchange.examples.anx.v2.account;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.anx.v2.ANXV2;
import com.xeiam.xchange.anx.v2.dto.ANXException;
import com.xeiam.xchange.anx.v2.dto.account.polling.ANXWalletHistoryEntry;
import com.xeiam.xchange.anx.v2.dto.account.polling.ANXWalletHistoryWrapper;
import com.xeiam.xchange.anx.v2.service.ANXV2Digest;
import com.xeiam.xchange.examples.anx.v2.ANXExamplesUtils;
import com.xeiam.xchange.utils.nonce.CurrentTimeNonceFactory;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

/**
 * Demo requesting wallethistory at ANX
 */
public class WalletHistoryDemo {

  public static void main(String[] args) throws ANXException, IOException {

    Exchange ANXExchange = ANXExamplesUtils.createExchange();
    ANXV2 ANXV2 = RestProxyFactory.createProxy(ANXV2.class, ANXExchange.getExchangeSpecification().getSslUri());
    ParamsDigest signatureCreator = ANXV2Digest.createInstance(ANXExchange.getExchangeSpecification().getSecretKey());

    ANXWalletHistoryWrapper wallethistory = ANXV2.getWalletHistory(ANXExchange.getExchangeSpecification().getApiKey(), signatureCreator,
        new CurrentTimeNonceFactory(), "BTC", null);

    System.out.println("WalletHistory: " + wallethistory.getANXWalletHistory().toString());
    for (ANXWalletHistoryEntry entry : wallethistory.getANXWalletHistory().getANXWalletHistoryEntries()) {
      System.out.println(entry.toString());
    }
  }
}
