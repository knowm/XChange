package org.knowm.xchange.examples.anx.v2.account;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.anx.v2.ANXV2;
import org.knowm.xchange.anx.v2.dto.ANXException;
import org.knowm.xchange.anx.v2.dto.account.ANXWalletHistoryEntry;
import org.knowm.xchange.anx.v2.dto.account.ANXWalletHistoryWrapper;
import org.knowm.xchange.anx.v2.service.ANXV2Digest;
import org.knowm.xchange.examples.anx.v2.ANXExamplesUtils;
import org.knowm.xchange.utils.nonce.CurrentTimeNonceFactory;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

/** Demo requesting wallethistory at ANX */
public class WalletHistoryDemo {

  public static void main(String[] args) throws ANXException, IOException {

    Exchange ANXExchange = ANXExamplesUtils.createExchange();
    ANXV2 ANXV2 =
        RestProxyFactory.createProxy(
            ANXV2.class, ANXExchange.getExchangeSpecification().getSslUri());
    ParamsDigest signatureCreator =
        ANXV2Digest.createInstance(ANXExchange.getExchangeSpecification().getSecretKey());

    ANXWalletHistoryWrapper wallethistory =
        ANXV2.getWalletHistory(
            ANXExchange.getExchangeSpecification().getApiKey(),
            signatureCreator,
            new CurrentTimeNonceFactory(),
            "BTC",
            null,
            null,
            null);

    System.out.println("WalletHistory: " + wallethistory.getANXWalletHistory().toString());
    for (ANXWalletHistoryEntry entry :
        wallethistory.getANXWalletHistory().getANXWalletHistoryEntries()) {
      System.out.println(entry.toString());
    }
  }
}
