package org.knowm.xchange.gdax.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.gdax.GDAX;
import org.knowm.xchange.gdax.dto.GDAXException;
import org.knowm.xchange.gdax.dto.account.GDAXAccount;
import org.knowm.xchange.gdax.dto.account.GDAXSendMoneyRequest;
import org.knowm.xchange.gdax.dto.account.GDAXWithdrawCryptoResponse;
import org.knowm.xchange.gdax.dto.account.GDAXWithdrawFundsRequest;
import org.knowm.xchange.gdax.dto.trade.GDAXSendMoneyResponse;
import si.mazi.rescu.SynchronizedValueFactory;

import java.io.IOException;
import java.math.BigDecimal;

public class GDAXAccountServiceRaw extends GDAXBaseService<GDAX> {

  private final SynchronizedValueFactory<Long> nonceFactory;

  public GDAXAccountServiceRaw(Exchange exchange) {

    super(GDAX.class, exchange);
    this.nonceFactory = exchange.getNonceFactory();
  }

  public GDAXAccount[] getCoinbaseExAccountInfo() throws GDAXException, IOException {
    return coinbaseEx.getAccounts(apiKey, digest, nonceFactory, passphrase);
  }

  public GDAXSendMoneyResponse sendMoney(String accountId, String to, BigDecimal amount, Currency currency) throws GDAXException, IOException {
    return coinbaseEx.sendMoney(new GDAXSendMoneyRequest(to, amount, currency.getCurrencyCode()), apiKey, digest, nonceFactory, passphrase,
        accountId);
  }

  public GDAXWithdrawCryptoResponse withdrawCrypto(String address, BigDecimal amount, Currency currency) throws GDAXException, IOException {
    return coinbaseEx.withdrawCrypto(apiKey, digest, nonceFactory, passphrase, new GDAXWithdrawFundsRequest(amount, currency.getCurrencyCode(), address));
  }

}
