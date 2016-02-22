package com.xeiam.xchange.coinbaseex.service.polling;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.coinbaseex.CoinbaseEx;
import com.xeiam.xchange.coinbaseex.dto.account.CoinbaseExAccount;
import com.xeiam.xchange.coinbaseex.dto.account.CoinbaseExSendMoneyRequest;
import com.xeiam.xchange.coinbaseex.dto.trade.CoinbaseExSendMoneyResponse;
import com.xeiam.xchange.currency.Currency;

import java.io.IOException;
import java.math.BigDecimal;

public class CoinbaseExAccountServiceRaw extends CoinbaseExBasePollingService<CoinbaseEx> {

  public CoinbaseExAccountServiceRaw(Exchange exchange) {

    super(CoinbaseEx.class, exchange);
  }

  public CoinbaseExAccount[] getCoinbaseExAccountInfo() throws IOException {
    return coinbaseEx.getAccounts(apiKey, digest, getTimestamp(), passphrase);
  }

  public CoinbaseExSendMoneyResponse sendMoney(String accountId, String to, BigDecimal amount, Currency currency) {
    return coinbaseEx.sendMoney(
            new CoinbaseExSendMoneyRequest(to, amount, currency.getCurrencyCode()),
            apiKey, digest, getTimestamp(), passphrase,
            accountId
    );
  }
}
