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
import org.knowm.xchange.utils.timestamp.UnixTimestampFactory;

public class CoinbaseProAccountServiceRaw extends CoinbaseProBaseService {

  public CoinbaseProAccountServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public org.knowm.xchange.coinbasepro.dto.account.CoinbaseProAccount[] getCoinbaseProAccountInfo()
      throws CoinbaseProException, IOException {
    return coinbasePro.getAccounts(
        apiKey, digest, UnixTimestampFactory.INSTANCE.createValue(), passphrase);
  }

  public CoinbaseProFee getCoinbaseProFees() throws CoinbaseProException, IOException {
    return coinbasePro.getFees(
        apiKey, digest, UnixTimestampFactory.INSTANCE.createValue(), passphrase);
  }

  public CoinbaseProSendMoneyResponse sendMoney(
      String accountId, String to, BigDecimal amount, Currency currency)
      throws CoinbaseProException, IOException {
    return coinbasePro.sendMoney(
        new CoinbaseProSendMoneyRequest(to, amount, currency.getCurrencyCode()),
        apiKey,
        digest,
        UnixTimestampFactory.INSTANCE.createValue(),
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
        UnixTimestampFactory.INSTANCE.createValue(),
        passphrase,
        new CoinbaseProWithdrawFundsRequest(
            amount, currency.getCurrencyCode(), address, destinationTag, noDestinationTag));
  }

  public List<Map> ledger(String accountId, Integer startingOrderId) throws IOException {
    return coinbasePro.ledger(
        apiKey,
        digest,
        UnixTimestampFactory.INSTANCE.createValue(),
        passphrase,
        accountId,
        startingOrderId);
  }

  /** @return the report id */
  public String requestNewReport(CoinbasePro.CoinbaseProReportRequest reportRequest)
      throws IOException {
    Map response =
        coinbasePro.createReport(
            apiKey, digest, UnixTimestampFactory.INSTANCE.createValue(), passphrase, reportRequest);
    return response.get("id").toString();
  }

  public Map report(String reportId) throws IOException {
    return coinbasePro.getReport(
        apiKey, digest, UnixTimestampFactory.INSTANCE.createValue(), passphrase, reportId);
  }

  public CoinbaseProTransfers transfers(
      String accountId, String profileId, int limit, String after) {
    return coinbasePro.transfers(
        apiKey,
        digest,
        UnixTimestampFactory.INSTANCE.createValue(),
        passphrase,
        accountId,
        profileId,
        limit,
        after);
  }

  public CoinbaseProTransfers transfers(
      String type, String profileId, String before, String after, int limit) {
    return coinbasePro.transfers(
        apiKey,
        digest,
        UnixTimestampFactory.INSTANCE.createValue(),
        passphrase,
        type,
        profileId,
        before,
        after,
        limit);
  }

  public CoinbaseProAccount[] getCoinbaseAccounts() throws IOException {
    return coinbasePro.getCoinbaseProAccounts(
        apiKey, digest, UnixTimestampFactory.INSTANCE.createValue(), passphrase);
  }

  public CoinbaseProAccountAddress getCoinbaseAccountAddress(String accountId) {
    return coinbasePro.getCoinbaseProAccountAddress(
        apiKey, digest, UnixTimestampFactory.INSTANCE.createValue(), passphrase, accountId);
  }

  public CoinbaseProWebsocketAuthData getWebsocketAuthData()
      throws CoinbaseProException, IOException {
    long timestamp = UnixTimestampFactory.INSTANCE.createValue();
    JsonNode json = coinbasePro.getVerifyId(apiKey, digest, timestamp, passphrase);
    String userId = json.get("id").asText();
    CoinbaseProDigest coinbaseProDigest = (CoinbaseProDigest) digest;
    return new CoinbaseProWebsocketAuthData(
        userId, apiKey, passphrase, coinbaseProDigest.getSignature(), timestamp);
  }
}
