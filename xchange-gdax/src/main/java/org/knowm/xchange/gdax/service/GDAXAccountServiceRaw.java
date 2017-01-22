package org.knowm.xchange.gdax.service;

import java.io.IOException;
import java.math.BigDecimal;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.gdax.GDAX;
import org.knowm.xchange.gdax.dto.account.GDAXAccount;
import org.knowm.xchange.gdax.dto.account.GDAXSendMoneyRequest;
import org.knowm.xchange.gdax.dto.trade.GDAXSendMoneyResponse;

public class GDAXAccountServiceRaw extends GDAXBaseService<GDAX> {

  public GDAXAccountServiceRaw(Exchange exchange) {

    super(GDAX.class, exchange);
  }

  public GDAXAccount[] getCoinbaseExAccountInfo() throws IOException {
    return coinbaseEx.getAccounts(apiKey, digest, getTimestamp(), passphrase);
  }

  public GDAXSendMoneyResponse sendMoney(String accountId, String to, BigDecimal amount, Currency currency) {
    return coinbaseEx.sendMoney(new GDAXSendMoneyRequest(to, amount, currency.getCurrencyCode()), apiKey, digest, getTimestamp(), passphrase,
        accountId);
  }
}
