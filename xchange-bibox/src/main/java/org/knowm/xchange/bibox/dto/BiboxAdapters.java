package org.knowm.xchange.bibox.dto;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.knowm.xchange.bibox.dto.account.BiboxCoin;
import org.knowm.xchange.bibox.dto.marketdata.BiboxTicker;
import org.knowm.xchange.bibox.dto.trade.BiboxOrderBook;
import org.knowm.xchange.bibox.dto.trade.BiboxOrderBookEntry;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.trade.LimitOrder;

/**
 * @author odrotleff
 */
public class BiboxAdapters {

  public static String toBiboxPair(CurrencyPair pair) {

    return pair.base.getCurrencyCode() + "_" + pair.counter.getCurrencyCode();
  }

  public static Ticker adaptTicker(BiboxTicker ticker, CurrencyPair currencyPair) {
    return new Ticker.Builder().currencyPair(currencyPair).ask(ticker.getSell())
        .bid(ticker.getBuy()).high(ticker.getHigh()).low(ticker.getLow()).last(ticker.getLast())
        .volume(ticker.getVol()).timestamp(new Date(ticker.getTimestamp())).build();
  }

  public static AccountInfo adaptAccountInfo(List<BiboxCoin> coins) {
    Wallet wallet = adaptWallet(coins);
    System.out.println(wallet);
    return new AccountInfo(wallet);
  }

  private static Wallet adaptWallet(List<BiboxCoin> coins) {
    List<Balance> balances =
        coins.stream().map(BiboxAdapters::adaptBalance).collect(Collectors.toList());
    return new Wallet(balances);
  }

  private static Balance adaptBalance(BiboxCoin coin) {
    return new Balance.Builder()
        .currency(Currency.getInstance(coin.getSymbol()))
        .available(coin.getBalance())
        .frozen(coin.getFreeze())
        .total(coin.getTotalBalance())
        .build();
  }

  public static OrderBook adaptOrderBook(BiboxOrderBook orderBook, CurrencyPair currencyPair) {
    return new OrderBook(
        new Date(orderBook.getUpdateTime()),
        orderBook.getAsks().stream().map(e -> adaptOrderBookOrder(e, OrderType.ASK, currencyPair)).collect(Collectors.toList()),
        orderBook.getBids().stream().map(e -> adaptOrderBookOrder(e, OrderType.BID, currencyPair)).collect(Collectors.toList()));
  }

  public static LimitOrder adaptOrderBookOrder(BiboxOrderBookEntry entry, OrderType orderType, CurrencyPair currencyPair) {
    return new LimitOrder.Builder(orderType, currencyPair)
        .limitPrice(entry.getPrice())
        .originalAmount(entry.getVolume())
        .build();
  }
}
