package org.knowm.xchange.coinbasepro.service;

import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinbasepro.CoinbasePro;
import org.knowm.xchange.coinbasepro.dto.CoinbaseProException;
import org.knowm.xchange.coinbasepro.dto.CoinbaseProTransfers;
import org.knowm.xchange.coinbasepro.dto.account.*;
import org.knowm.xchange.coinbasepro.dto.trade.CoinbaseProAccount;
import org.knowm.xchange.coinbasepro.dto.trade.CoinbaseProAccountAddress;
import org.knowm.xchange.coinbasepro.dto.trade.CoinbaseProSendMoneyResponse;
import org.knowm.xchange.currency.Currency;
import si.mazi.rescu.SynchronizedValueFactory;

public class CoinbaseProAccountServiceRaw extends CoinbaseProBaseService {

  private final SynchronizedValueFactory<Long> nonceFactory;

  public CoinbaseProAccountServiceRaw(Exchange exchange) {

    super(exchange);
    this.nonceFactory = exchange.getNonceFactory();
  }

  public org.knowm.xchange.coinbasepro.dto.account.CoinbaseProAccount[] getCoinbaseProAccountInfo()
      throws CoinbaseProException, IOException {
    return coinbasePro.getAccounts(apiKey, digest, nonceFactory, passphrase);
  }

  public CoinbaseProFee getCoinbaseProFees() throws CoinbaseProException, IOException {
    return coinbasePro.getFees(apiKey, digest, nonceFactory, passphrase);
  }

  public CoinbaseProSendMoneyResponse sendMoney(
      String accountId, String to, BigDecimal amount, Currency currency)
      throws CoinbaseProException, IOException {
    return coinbasePro.sendMoney(
        new CoinbaseProSendMoneyRequest(to, amount, currency.getCurrencyCode()),
        apiKey,
        digest,
        nonceFactory,
        passphrase,
        accountId);
  }

  public CoinbaseProWithdrawCryptoResponse withdrawCrypto(
      String address,
      BigDecimal amount,
      Currency currency,
      String destinationTag,
      boolean noDestinationTag)
      throws CoinbaseProException, IOException {
    return coinbasePro.withdrawCrypto(
        apiKey,
        digest,
        nonceFactory,
        passphrase,
        new CoinbaseProWithdrawFundsRequest(
            amount, currency.getCurrencyCode(), address, destinationTag, noDestinationTag));
  }

  public List<Map> ledger(String accountId, Integer startingOrderId) throws IOException {
    return coinbasePro.ledger(apiKey, digest, nonceFactory, passphrase, accountId, startingOrderId);
  }

  /** @return the report id */
  public String requestNewReport(CoinbasePro.CoinbaseProReportRequest reportRequest)
      throws IOException {
    Map response =
        coinbasePro.createReport(apiKey, digest, nonceFactory, passphrase, reportRequest);
    return response.get("id").toString();
  }

  public Map report(String reportId) throws IOException {
    return coinbasePro.getReport(apiKey, digest, nonceFactory, passphrase, reportId);
  }

  public CoinbaseProTransfers transfers(
      String accountId, String profileId, int limit, String after) {
    return coinbasePro.transfers(
        apiKey, digest, nonceFactory, passphrase, accountId, profileId, limit, after);
  }

  public CoinbaseProAccount[] getCoinbaseAccounts() throws IOException {
    return coinbasePro.getCoinbaseProAccounts(apiKey, digest, nonceFactory, passphrase);
  }

  public CoinbaseProAccountAddress getCoinbaseAccountAddress(String accountId) {
    return coinbasePro.getCoinbaseProAccountAddress(
        apiKey, digest, nonceFactory, passphrase, accountId);
  }

  public CoinbaseProWebsocketAuthData getWebsocketAuthData()
      throws CoinbaseProException, IOException {
    long timestamp = nonceFactory.createValue();
    JsonNode json = coinbasePro.getVerifyId(apiKey, digest, timestamp, passphrase);
    String userId = json.get("id").asText();
    CoinbaseProDigest coinbaseProDigest = (CoinbaseProDigest) digest;
    CoinbaseProWebsocketAuthData data =
        new CoinbaseProWebsocketAuthData(
            userId, apiKey, passphrase, coinbaseProDigest.getSignature(), timestamp);
    return data;
  }
}
