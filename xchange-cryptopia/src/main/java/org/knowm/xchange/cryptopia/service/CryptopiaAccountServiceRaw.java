package org.knowm.xchange.cryptopia.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.knowm.xchange.cryptopia.Cryptopia.GetDepositAddressRequest;
import org.knowm.xchange.cryptopia.Cryptopia.GetTransactionsRequest;
import org.knowm.xchange.cryptopia.Cryptopia.SubmitWithdrawRequest;
import org.knowm.xchange.cryptopia.CryptopiaAdapters;
import org.knowm.xchange.cryptopia.CryptopiaExchange;
import org.knowm.xchange.cryptopia.dto.CryptopiaBaseResponse;
import org.knowm.xchange.cryptopia.service.CryptopiaAccountService.CryptopiaFundingHistoryParams;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Balance.Builder;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.account.FundingRecord.Status;
import org.knowm.xchange.dto.account.FundingRecord.Type;
import org.knowm.xchange.exceptions.ExchangeException;

public class CryptopiaAccountServiceRaw extends CryptopiaBaseService {

  public CryptopiaAccountServiceRaw(CryptopiaExchange exchange) {

    super(exchange);
  }

  public List<Balance> getBalances() throws IOException {

    CryptopiaBaseResponse<List<Map>> response =
        cryptopia.getBalance(signatureCreator, new HashMap<>());
    if (!response.isSuccess())
      throw new ExchangeException("Failed to get balance: " + response.toString());

    List<Balance> balances = new ArrayList<>();
    for (Map datum : response.getData()) {
      Currency symbol = Currency.valueOf(datum.get("Symbol").toString());
      BigDecimal total = new BigDecimal(datum.get("Total").toString());
      BigDecimal available = new BigDecimal(datum.get("Available").toString());
      BigDecimal heldForTrades = new BigDecimal(datum.get("HeldForTrades").toString());
      BigDecimal pendingWithdraw = new BigDecimal(datum.get("PendingWithdraw").toString());
      BigDecimal unconfirmed = new BigDecimal(datum.get("Unconfirmed").toString());
      org.knowm.xchange.dto.account.Balance balance =
          new Builder()
              .setCurrency(symbol)
              .setTotal(total)
              .setAvailable(available)
              .setFrozen(heldForTrades)
              .setBorrowed(BigDecimal.ZERO)
              .setLoaned(BigDecimal.ZERO)
              .setWithdrawing(pendingWithdraw)
              .setDepositing(unconfirmed)
              .createBalance();

      balances.add(balance);
    }

    return balances;
  }

  public String submitWithdraw(
      Currency currency, BigDecimal amount, String address, String paymentId) throws IOException {
    CryptopiaBaseResponse<Long> response =
        cryptopia.submitWithdraw(
            signatureCreator,
            new SubmitWithdrawRequest(currency.getCurrencyCode(), address, paymentId, amount));
    if (!response.isSuccess())
      throw new ExchangeException("Failed to withdraw funds: " + response.toString());

    return String.valueOf(response.getData());
  }

  public String getDepositAddress(Currency currency) throws IOException {
    CryptopiaBaseResponse<Map> response =
        cryptopia.getDepositAddress(
            signatureCreator, new GetDepositAddressRequest(currency.getCurrencyCode()));
    if (!response.isSuccess())
      throw new ExchangeException("Failed to get address: " + response.toString());

    return response.getData().get("Address").toString();
  }

  public List<FundingRecord> getTransactions(String type, Integer count) throws IOException {
    CryptopiaBaseResponse<List<Map>> response =
        cryptopia.getTransactions(signatureCreator, new GetTransactionsRequest(type, count));
    if (!response.isSuccess())
      throw new ExchangeException("Failed to get transactions: " + response.toString());

    List<FundingRecord> results = new ArrayList<>();
    for (Map map : response.getData()) {
      Date timeStamp = CryptopiaAdapters.convertTimestamp(map.get("Timestamp").toString());
      Currency currency = Currency.valueOf(map.get("Currency").toString());
      Type fundingType =
          map.get("Type").toString().equals(CryptopiaFundingHistoryParams.Type.Deposit.name())
              ? Type.DEPOSIT
              : Type.WITHDRAWAL;

      Status status;
      String rawStatus = map.get("Status").toString();
      switch (rawStatus) {
        case "UnConfirmed":
        case "Pending":
          status = Status.PROCESSING;
          break;
        case "Confirmed":
        case "Complete":
          status = Status.COMPLETE;
          break;
        default:
          status = Status.resolveStatus(rawStatus);
          if (status == null) {
            status = Status.FAILED;
          }
          break;
      }

      String address = map.get("Address") == null ? null : map.get("Address").toString();

      FundingRecord fundingRecord =
          new FundingRecord(
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
              null);

      results.add(fundingRecord);
    }

    return results;
  }
}
