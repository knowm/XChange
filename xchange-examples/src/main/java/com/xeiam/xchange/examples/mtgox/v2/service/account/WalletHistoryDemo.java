/**
 * Copyright (C) 2012 - 2013 Xeiam LLC http://xeiam.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.xeiam.xchange.examples.mtgox.v2.service.account;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.examples.mtgox.v2.MtGoxV2ExamplesUtils;
import com.xeiam.xchange.mtgox.MtGoxUtils;
import com.xeiam.xchange.mtgox.v2.MtGoxV2;
import com.xeiam.xchange.mtgox.v2.dto.account.polling.MtGoxWalletHistoryEntry;
import com.xeiam.xchange.mtgox.v2.dto.account.polling.MtGoxWalletHistoryWrapper;
import com.xeiam.xchange.mtgox.v2.service.MtGoxV2Digest;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

/**
 * Demo requesting wallethistory at MtGox
 */
public class WalletHistoryDemo {

  public static void main(String[] args) {

    Exchange mtGoxExchange = MtGoxV2ExamplesUtils.createExchange();
    MtGoxV2 mtGoxV2 = RestProxyFactory.createProxy(MtGoxV2.class, mtGoxExchange.getExchangeSpecification().getSslUri());
    ParamsDigest signatureCreator = MtGoxV2Digest.createInstance(mtGoxExchange.getExchangeSpecification().getSecretKey());

    MtGoxWalletHistoryWrapper wallethistory = mtGoxV2.getWalletHistory(mtGoxExchange.getExchangeSpecification().getApiKey(), signatureCreator, MtGoxUtils.getNonce(), 
            "BTC", null);
    
    System.out.println("WalletHistory: "+wallethistory.getMtGoxWalletHistory().toString());
    for(MtGoxWalletHistoryEntry entry : wallethistory.getMtGoxWalletHistory().getMtGoxWalletHistoryEntries()){
      System.out.println(entry.toString());
    }
  }
}
