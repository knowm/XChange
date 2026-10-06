package org.knowm.xchange.coinbase.v2;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.knowm.xchange.coinbase.v2.dto.account.transactions.CoinbaseBuySell;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.marketdata.Trades.TradeSortType;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;

public final class CoinbaseAdapters {

  private static final int PRICE_SCALE = 10;

  private CoinbaseAdapters() {}

  public static UserTrades adaptTrades(List<CoinbaseBuySell> transactions, OrderType orderType) {
    final List<UserTrade> trades = new ArrayList<>();

    for (CoinbaseBuySell transaction : transactions) {
      trades.add(adaptTrade(transaction, orderType));
    }

    return new UserTrades(trades, TradeSortType.SortByTimestamp);
  }

  public static List<FundingRecord> adaptFundings(List<CoinbaseBuySell> trades) {
    final List<FundingRecord> records = new ArrayList<>();

    for (CoinbaseBuySell record : trades) {
      records.add(adaptFunding(record));
    }

    return records;
  }

  private static FundingRecord adaptFunding(CoinbaseBuySell transaction) {

    FundingRecord.Type type = null;
    FundingRecord.Status status;
    String recordType = transaction.getResource().toUpperCase();

    switch (recordType) {
      case "WITHDRAWAL":
      case "CREATE_VOUCHER":
        type = FundingRecord.Type.WITHDRAWAL;
        break;
      case "DEPOSIT":
      case "USED_VOUCHER":
      case "NEW_USER_REWARD":
      case "REFERRAL":
        type = FundingRecord.Type.DEPOSIT;
        break;
      default:
        // here we ignore the other types which are trading
    }

    switch (transaction.getStatus().toUpperCase()) {
      case "OK":
      case "COMPLETED":
        status = FundingRecord.Status.COMPLETE;
        break;
      case "NEW":
      case "SENT":
      case "CREATED":
      case "WAITING":
      case "PENDING":
        status = FundingRecord.Status.PROCESSING;
        break;
      default:
        status = FundingRecord.Status.FAILED;
    }

    FundingRecord funding =
        FundingRecord.builder()
            .date(Date.from(transaction.getCreatedAt().toInstant()))
            .currency(Currency.getInstance(transaction.getAmount().getCurrency()))
            .amount(transaction.getAmount().getAmount())
            .internalId(transaction.getId())
            .type(type)
            .status(status)
            .fee(transaction.getFee().getAmount())
            .build();
    return funding;
  }

  private static UserTrade adaptTrade(CoinbaseBuySell transaction, OrderType orderType) {
    // Bug fix - Null point exception in case of cancelled transactions

    String transactionId =
        transaction.getTransaction() == null
            ? null
            : (transaction.getTransaction().getId() == null
                ? null
                : transaction.getTransaction().getId());
    return UserTrade.builder()
        .type(orderType)
        .originalAmount(transaction.getAmount().getAmount())
        .instrument(
            new CurrencyPair(
                transaction.getAmount().getCurrency(), transaction.getTotal().getCurrency()))
        .price(
            transaction
                .getSubTotal()
                .getAmount()
                .divide(transaction.getAmount().getAmount(), PRICE_SCALE, RoundingMode.HALF_UP))
        .timestamp(Date.from(transaction.getCreatedAt().toInstant()))
        .id(transaction.getId())
        .orderId(transactionId)
        .feeAmount(transaction.getFee().getAmount())
        .feeCurrency(Currency.getInstance(transaction.getFee().getCurrency()))
        .build();
  }
}
