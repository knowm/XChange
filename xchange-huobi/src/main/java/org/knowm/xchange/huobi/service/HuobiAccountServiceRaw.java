package org.knowm.xchange.huobi.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.huobi.HuobiUtils;
import org.knowm.xchange.huobi.dto.account.HuobiAccount;
import org.knowm.xchange.huobi.dto.account.HuobiBalance;
import org.knowm.xchange.huobi.dto.account.results.HuobiAccountResult;
import org.knowm.xchange.huobi.dto.account.results.HuobiBalanceResult;

public class HuobiAccountServiceRaw extends HuobiBaseService {

  HuobiAccountServiceRaw(Exchange exchange) {
    super(exchange);
  }

  HuobiBalance getHuobiBalance(String accountID) throws IOException {
    HuobiBalanceResult huobiBalanceResult =
        huobi.getBalance(
            accountID,
            exchange.getExchangeSpecification().getApiKey(),
            HuobiDigest.HMAC_SHA_256,
            2,
            HuobiUtils.createUTCDate(exchange.getNonceFactory()),
            signatureCreator);
    return checkResult(huobiBalanceResult);
  }

  public HuobiAccount[] getAccounts() throws IOException {
    HuobiAccountResult huobiAccountResult =
        huobi.getAccount(
            exchange.getExchangeSpecification().getApiKey(),
            HuobiDigest.HMAC_SHA_256,
            2,
            HuobiUtils.createUTCDate(exchange.getNonceFactory()),
            signatureCreator);
    return checkResult(huobiAccountResult);
  }
}
