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
import org.knowm.xchange.gdax.dto.account.GDAXWebsocketAuthData;
import org.knowm.xchange.gdax.dto.account.GDAXWithdrawCryptoResponse;
import org.knowm.xchange.gdax.dto.account.GDAXWithdrawFundsRequest;
import org.knowm.xchange.gdax.dto.trade.GDAXCoinbaseAccount;
import org.knowm.xchange.gdax.dto.trade.GDAXCoinbaseAccountAddress;
import org.knowm.xchange.gdax.dto.trade.GDAXSendMoneyResponse;

import com.fasterxml.jackson.databind.JsonNode;

import si.mazi.rescu.SynchronizedValueFactory;

public class GDAXAccountServiceRaw extends GDAXBaseService {

  private final SynchronizedValueFactory<Long> nonceFactory;

  public GDAXAccountServiceRaw(Exchange exchange) {

    super(exchange);
    this.nonceFactory = exchange.getNonceFactory();
  }

  public GDAXAccount[] getGDAXAccountInfo() throws GDAXException, IOException {
    return gdax.getAccounts(apiKey, digest, nonceFactory, passphrase);
  }

  public GDAXSendMoneyResponse sendMoney(String accountId, String to, BigDecimal amount, Currency currency) throws GDAXException, IOException {
    return gdax.sendMoney(new GDAXSendMoneyRequest(to, amount, currency.getCurrencyCode()), apiKey, digest, nonceFactory, passphrase,
        accountId);
  }

  public GDAXWithdrawCryptoResponse withdrawCrypto(String address, BigDecimal amount, Currency currency) throws GDAXException, IOException {
    return gdax.withdrawCrypto(apiKey, digest, nonceFactory, passphrase, new GDAXWithdrawFundsRequest(amount, currency.getCurrencyCode(), address));
  }

  public List<Map> ledger(String accountId, Integer startingOrderId) throws IOException {
    return gdax.ledger(apiKey, digest, nonceFactory, passphrase, accountId, startingOrderId);
  }

  /**
   * @return the report id
   */
  public String requestNewReport(GDAX.GDAXReportRequest reportRequest) throws IOException {
    Map response = gdax.createReport(apiKey, digest, nonceFactory, passphrase, reportRequest);
    return response.get("id").toString();
  }

  public List report(String reportId) throws IOException {
    return gdax.getReport(apiKey, digest, nonceFactory, passphrase, reportId);
  }

  public GDAXCoinbaseAccount[] getCoinbaseAccounts() throws IOException {
    return gdax.getGDAXAccounts(apiKey, digest, nonceFactory, passphrase);
  }

  public GDAXCoinbaseAccountAddress getCoinbaseAccountAddress(String accountId) throws IOException {
    return gdax.getGDAXAccountAddress(apiKey, digest, nonceFactory, passphrase, accountId);
  }
  
  public GDAXWebsocketAuthData getWebsocketAuthData() throws GDAXException, IOException {
    long timestamp = nonceFactory.createValue();
    JsonNode json = gdax.getVerifyId(apiKey, digest, timestamp, passphrase);
    String userId = json.get("id").asText();
    GDAXDigest gdaxDigest = (GDAXDigest)digest;
    GDAXWebsocketAuthData data = new GDAXWebsocketAuthData(userId, apiKey, passphrase, gdaxDigest.getSignature(), timestamp);
    return data;
  }
}
