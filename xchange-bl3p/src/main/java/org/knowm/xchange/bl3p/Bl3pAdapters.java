package org.knowm.xchange.bl3p;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.knowm.xchange.bl3p.dto.Bl3pUserTransactions;
import org.knowm.xchange.bl3p.dto.account.Bl3pAccountInfo;
import org.knowm.xchange.bl3p.dto.trade.Bl3pGetOrder;
import org.knowm.xchange.bl3p.dto.trade.Bl3pOpenOrders;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrade;

public class Bl3pAdapters {

  private Bl3pAdapters() {}

  public static Wallet adaptBalances(
      Map<String, Bl3pAccountInfo.Bl3pAccountInfoWallet> bl3pBalances) {
    List<Balance> balances = new ArrayList<>(bl3pBalances.size());

    for (Bl3pAccountInfo.Bl3pAccountInfoWallet bl3pWallet : bl3pBalances.values()) {
      balances.add(
          new Balance(
              new Currency(bl3pWallet.getAvailable().currency),
              bl3pWallet.getBalance().value,
              bl3pWallet.getAvailable().value));
    }

    return Wallet.Builder.from(balances).build();
  }

  public static OpenOrders adaptOpenOrders(
      CurrencyPair currencyPair, Bl3pOpenOrders.Bl3pOpenOrder[] bl3pOrders) {
    List<LimitOrder> result = new ArrayList<>(bl3pOrders.length);

    for (Bl3pOpenOrders.Bl3pOpenOrder bl3pOrder : bl3pOrders) {
      Order.OrderType orderType = Bl3pUtils.fromBl3pOrderType(bl3pOrder.getStatus());
      BigDecimal limitPrice = bl3pOrder.getPrice().value;
      BigDecimal originalAmount = bl3pOrder.getAmountFunds().value;
      BigDecimal executedAmount = bl3pOrder.getAmountExecuted().value;
      BigDecimal remainingAmount = originalAmount.subtract(executedAmount);

      result.add(
          new LimitOrder.Builder(orderType, currencyPair)
              .cumulativeAmount(executedAmount)
              .id("" + bl3pOrder.getOrderId())
              .limitPrice(limitPrice)
              .originalAmount(originalAmount)
              .remainingAmount(remainingAmount)
              .timestamp(bl3pOrder.getTimestamp())
              .build());
    }

    return new OpenOrders(result);
  }

  public static Order adaptGetOrder(
      CurrencyPair currencyPair, Bl3pGetOrder.Bl3pGetOrderData bl3pOrder) {
    Order.OrderType orderType = Bl3pUtils.fromBl3pOrderType(bl3pOrder.getStatus());
    BigDecimal limitPrice = bl3pOrder.getPrice().value;
    BigDecimal originalAmount = bl3pOrder.getTotalAmount().value;
    BigDecimal executedAmount = bl3pOrder.getTotalSpent().value;
    BigDecimal remainingAmount = originalAmount.subtract(executedAmount);

    return new LimitOrder.Builder(orderType, currencyPair)
        .cumulativeAmount(executedAmount)
        .id("" + bl3pOrder.getOrderId())
        .limitPrice(limitPrice)
        .originalAmount(originalAmount)
        .remainingAmount(remainingAmount)
        .timestamp(bl3pOrder.getTimestamp())
        .build();
  }

  public static List<UserTrade> adaptUserTransactionsToUserTrades(
      Bl3pUserTransactions.Bl3pUserTransaction[] transactions) {
    List<UserTrade> result = new ArrayList<>(transactions.length);

    for (Bl3pUserTransactions.Bl3pUserTransaction t : transactions) {
      UserTrade ut =
          new UserTrade.Builder()
              .currencyPair(CurrencyPair.BTC_EUR)
              .id(Integer.toString(t.id))
              .orderId(Integer.toString(t.orderId))
              .type(t.type == "credit" ? Order.OrderType.BID : Order.OrderType.ASK)
              .timestamp(t.date)
              .price(t.price.value)
              .feeAmount(t.fee.value)
              .feeCurrency(Currency.getInstance(t.fee.currency))
              .originalAmount(t.amount.value)
              .build();

      result.add(ut);
    }

    return result;
  }

  public static List<FundingRecord> adaptUserTransactionsToFundingRecords(
      Bl3pUserTransactions.Bl3pUserTransaction[] transactions) {
    List<FundingRecord> list = new ArrayList<>(transactions.length);

    for (Bl3pUserTransactions.Bl3pUserTransaction tx : transactions) {
      list.add(
          FundingRecord.builder()
              .amount(tx.amount.value)
              .balance(tx.balance.value)
              .currency(Currency.getInstance(tx.amount.currency))
              .date(tx.date)
              .fee(tx.fee == null ? null : tx.fee.value)
              .type(
                  tx.type == "deposit" ? FundingRecord.Type.DEPOSIT : FundingRecord.Type.WITHDRAWAL)
              .build());
    }

    return list;
  }
}
