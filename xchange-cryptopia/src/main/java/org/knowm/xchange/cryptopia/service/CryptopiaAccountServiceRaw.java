package org.knowm.xchange.cryptopia.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.knowm.xchange.cryptopia.Cryptopia;
import org.knowm.xchange.cryptopia.CryptopiaAdapters;
import org.knowm.xchange.cryptopia.CryptopiaExchange;
import org.knowm.xchange.cryptopia.dto.CryptopiaBaseResponse;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.FundingRecord;

public class CryptopiaAccountServiceRaw extends CryptopiaBaseService {

  public CryptopiaAccountServiceRaw(CryptopiaExchange exchange) {

    super(exchange);
  }

  public List<Balance> getBalances() throws IOException {

    CryptopiaBaseResponse<List<Map>> response =
        cryptopia.getBalance(signatureCreator, new HashMap<>());

    List<Balance> balances = new ArrayList<>();
    for (Map datum : response.getData()) {
      Currency symbol = Currency.getInstance(datum.get("Symbol").toString());
      BigDecimal total = new BigDecimal(datum.get("Total").toString());
      BigDecimal available = new BigDecimal(datum.get("Available").toString());
      BigDecimal heldForTrades = new BigDecimal(datum.get("HeldForTrades").toString());
      BigDecimal pendingWithdraw = new BigDecimal(datum.get("PendingWithdraw").toString());
      BigDecimal unconfirmed = new BigDecimal(datum.get("Unconfirmed").toString());
      Balance balance =
          new Balance(
              symbol,
              total,
              available,
              heldForTrades,
              BigDecimal.ZERO,
              BigDecimal.ZERO,
              pendingWithdraw,
              unconfirmed);

      balances.add(balance);
    }

    return balances;
  }

  public String submitWithdraw(
      Currency currency, BigDecimal amount, String address, String paymentId) throws IOException {
    CryptopiaBaseResponse<Long> response =
        cryptopia.submitWithdraw(
            signatureCreator,
            new Cryptopia.SubmitWithdrawRequest(
                currency.getCurrencyCode(), address, paymentId, amount));

    return String.valueOf(response.getData());
  }

  public String getDepositAddress(Currency currency) throws IOException {
    CryptopiaBaseResponse<Map> response =
        cryptopia.getDepositAddress(
            signatureCreator, new Cryptopia.GetDepositAddressRequest(currency.getCurrencyCode()));

    return response.getData().get("Address").toString();
  }

  public List<FundingRecord> getTransactions(String type, Integer count) throws IOException {
    CryptopiaBaseResponse<List<Map>> response =
        cryptopia.getTransactions(
            signatureCreator, new Cryptopia.GetTransactionsRequest(type, count));

    List<FundingRecord> results = new ArrayList<>();
    for (Map map : response.getData()) {
      Date timeStamp = CryptopiaAdapters.convertTimestamp(map.get("Timestamp").toString());
      Currency currency = Currency.getInstance(map.get("Currency").toString());
      FundingRecord.Type fundingType =
          map.get("Type")
                  .toString()
                  .equals(CryptopiaAccountService.CryptopiaFundingType.Deposit.name())
              ? FundingRecord.Type.DEPOSIT
              : FundingRecord.Type.WITHDRAWAL;

      FundingRecord.Status status;
      String rawStatus = map.get("Status").toString();
      switch (rawStatus) {
        case "UnConfirmed":
        case "Pending":
          status = FundingRecord.Status.PROCESSING;
          break;
        case "Confirmed":
        case "Complete":
          status = FundingRecord.Status.COMPLETE;
          break;
        default:
          status = FundingRecord.Status.resolveStatus(rawStatus);
          if (status == null) {
            status = FundingRecord.Status.FAILED;
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
