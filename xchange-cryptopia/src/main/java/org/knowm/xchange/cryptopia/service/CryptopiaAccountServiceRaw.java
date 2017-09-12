package org.knowm.xchange.cryptopia.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.knowm.xchange.cryptopia.Cryptopia;
import org.knowm.xchange.cryptopia.CryptopiaDigest;
import org.knowm.xchange.cryptopia.CryptopiaExchange;
import org.knowm.xchange.cryptopia.dto.CryptopiaBaseResponse;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.utils.DateUtils;

import si.mazi.rescu.RestProxyFactory;

public class CryptopiaAccountServiceRaw {

  private final Cryptopia api;
  private final CryptopiaDigest signatureCreator;

  public CryptopiaAccountServiceRaw(CryptopiaExchange exchange) {
    this.api = RestProxyFactory.createProxy(Cryptopia.class, exchange.getExchangeSpecification().getSslUri());
    this.signatureCreator = CryptopiaDigest.createInstance(exchange.getNonceFactory(), exchange.getExchangeSpecification().getSecretKey(), exchange.getExchangeSpecification().getApiKey());
  }

  public List<Balance> getBalances() throws IOException {
    CryptopiaBaseResponse<List<Map>> response = api.getBalance(signatureCreator, new HashMap<>());
    if (!response.isSuccess())
      throw new ExchangeException("Failed to get balance: " + response.toString());

    List<Balance> balances = new ArrayList<>();
    for (Map datum : response.getData()) {
      Currency symbol = Currency.getInstance(datum.get("Symbol").toString());
      BigDecimal total = new BigDecimal(datum.get("Total").toString());
      BigDecimal available = new BigDecimal(datum.get("Available").toString());
      BigDecimal heldForTrades = new BigDecimal(datum.get("HeldForTrades").toString());
      BigDecimal pendingWithdraw = new BigDecimal(datum.get("PendingWithdraw").toString());
      BigDecimal unconfirmed = new BigDecimal(datum.get("Unconfirmed").toString());
      Balance balance = new Balance(symbol, total, available, heldForTrades, BigDecimal.ZERO, BigDecimal.ZERO, pendingWithdraw, unconfirmed);

      balances.add(balance);
    }

    return balances;
  }

  public String submitWithdraw(Currency currency, BigDecimal amount, String address, String paymentId) throws IOException {
    CryptopiaBaseResponse<Long> response = api.submitWithdraw(signatureCreator, new Cryptopia.SubmitWithdrawRequest(currency.getCurrencyCode(), address, paymentId, amount));
    if (!response.isSuccess())
      throw new ExchangeException("Failed to withdraw funds: " + response.toString());

    return String.valueOf(response.getData());
  }

  public String getDepositAddress(Currency currency) throws IOException {
    CryptopiaBaseResponse<Map> response = api.getDepositAddress(signatureCreator, new Cryptopia.GetDepositAddressRequest(currency.getCurrencyCode()));
    if (!response.isSuccess())
      throw new ExchangeException("Failed to get address: " + response.toString());

    return response.getData().get("Address").toString();
  }

  public List<FundingRecord> getTransactions(String type, Integer count) throws IOException {
    CryptopiaBaseResponse<List<Map>> response = api.getTransactions(signatureCreator, new Cryptopia.GetTransactionsRequest(type, count));
    if (!response.isSuccess())
      throw new ExchangeException("Failed to get transactions: " + response.toString());

    List<FundingRecord> results = new ArrayList<>();
    for (Map map : response.getData()) {
      Date timeStamp = DateUtils.fromISO8601DateString(map.get("Timestamp").toString());
      Currency currency = Currency.getInstance(map.get("Currency").toString());
      FundingRecord.Type fundingType = map.get("Type").toString().equals(CryptopiaAccountService.CryptopiaFundingHistoryParams.Type.Deposit.name())
          ? FundingRecord.Type.DEPOSIT : FundingRecord.Type.WITHDRAWAL;

      FundingRecord.Status status;

      String rawStatus = map.get("Status").toString();
      if (rawStatus.equals("Confirmed"))
        status = FundingRecord.Status.COMPLETE;
      else if (rawStatus.equals("Pending"))
        status = FundingRecord.Status.PROCESSING;
      else
        status = FundingRecord.Status.FAILED;//is there a 4th state?

      String address = map.get("Address") == null ? null : map.get("Address").toString();

      FundingRecord fundingRecord = new FundingRecord(
          address,
          timeStamp,
          currency,
          new BigDecimal(map.get("Amount").toString()),
          map.get("Id").toString(),
          map.get("TxId").toString(),
          fundingType,
          status,
          null,
          new BigDecimal(map.get("Fee").toString()),
          null
      );

      results.add(fundingRecord);
    }

    return results;
  }
}
