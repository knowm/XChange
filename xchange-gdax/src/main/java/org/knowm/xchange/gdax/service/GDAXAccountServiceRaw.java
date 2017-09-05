package org.knowm.xchange.gdax.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

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

  public List<Map> ledger(String accountId, Integer startingOrderId) throws IOException {
    return coinbaseEx.ledger(apiKey, digest, nonceFactory, passphrase, accountId, startingOrderId);
  }

  /**
   * @return the report id
   */
  public String requestNewReport(GDAX.GDAXReportRequest reportRequest) throws IOException {
    Map response = coinbaseEx.createReport(apiKey, digest, nonceFactory, passphrase, reportRequest);
    return response.get("id").toString();
  }

  public List report(String reportId) throws IOException {
    return coinbaseEx.getReport(apiKey, digest, nonceFactory, passphrase, reportId);
  }
}
