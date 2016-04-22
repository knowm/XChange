package org.knowm.xchange.coinbaseex.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinbaseex.CoinbaseEx;
import org.knowm.xchange.coinbaseex.dto.account.CoinbaseExAccount;
import org.knowm.xchange.coinbaseex.dto.account.CoinbaseExSendMoneyRequest;
import org.knowm.xchange.coinbaseex.dto.trade.CoinbaseExSendMoneyResponse;
import org.knowm.xchange.currency.Currency;

public class CoinbaseExAccountServiceRaw extends CoinbaseExBasePollingService<CoinbaseEx> {

  public CoinbaseExAccountServiceRaw(Exchange exchange) {

    super(CoinbaseEx.class, exchange);
  }

  public CoinbaseExAccount[] getCoinbaseExAccountInfo() throws IOException {
    return coinbaseEx.getAccounts(apiKey, digest, getTimestamp(), passphrase);
  }

  public CoinbaseExSendMoneyResponse sendMoney(String accountId, String to, BigDecimal amount, Currency currency) {
    return coinbaseEx.sendMoney(new CoinbaseExSendMoneyRequest(to, amount, currency.getCurrencyCode()), apiKey, digest, getTimestamp(), passphrase,
        accountId);
  }
}
