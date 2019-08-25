package org.knowm.xchange.enigma;

import static org.knowm.xchange.utils.jackson.CurrencyPairDeserializer.getCurrencyPairFromString;

import java.util.List;
import java.util.stream.Collectors;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.enigma.dto.account.EnigmaBalance;
import org.knowm.xchange.enigma.dto.trade.EnigmaExecutedQuote;
import org.knowm.xchange.enigma.dto.trade.EnigmaOrderSubmission;

public final class EnigmaAdapters {

  private EnigmaAdapters() {}

  public static AccountInfo adaptAccountInfo(EnigmaBalance enigmaBalance, String userName) {

    List<Balance> balances =
        enigmaBalance.getBalances().entrySet().stream()
            .map(
                balanceEntry ->
                    new Balance(
                        Currency.getInstance(balanceEntry.getKey().toUpperCase()),
                        balanceEntry.getValue()))
            .collect(Collectors.toList());

    return new AccountInfo(userName, new Wallet(balances));
  }

  public static Trade adaptTrade(EnigmaExecutedQuote enigmaTrade) {

    return new Trade.Builder()
        .currencyPair(getCurrencyPairFromString(enigmaTrade.getProductName()))
        .price(enigmaTrade.getPrice())
        .originalAmount(enigmaTrade.getQuantity())
        .timestamp(enigmaTrade.getCreatedAt())
        .type(enigmaTrade.getSide().equals("sell") ? Order.OrderType.ASK : Order.OrderType.BID)
        .build();
  }

  public static Trade adaptTrade(EnigmaOrderSubmission enigmaTrade) {
    return new Trade.Builder()
        .id(String.valueOf(enigmaTrade.getId()))
        .currencyPair(getCurrencyPairFromString(enigmaTrade.getProductName()))
        .price(enigmaTrade.getPrice())
        .originalAmount(enigmaTrade.getQuantity())
        .timestamp(enigmaTrade.getSent())
        .type(enigmaTrade.getSide().equals("sell") ? Order.OrderType.ASK : Order.OrderType.BID)
        .build();
  }

  public static Trades adaptTrades(List<Trade> trades) {
    return new Trades(trades);
  }
}
