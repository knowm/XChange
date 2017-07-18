package org.knowm.xchange.jubi;

import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.jubi.dto.account.JubiBalance;
import org.knowm.xchange.jubi.dto.marketdata.JubiTicker;
import org.knowm.xchange.jubi.dto.marketdata.JubiTrade;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Yingzhe on 3/16/2015.
 * Adapter for market data.
 */
public class JubiAdapters {
  /**
   * private constructor
   */
  private JubiAdapters() {
  }

  public static Ticker adaptTicker(JubiTicker ticker, CurrencyPair currencyPair) {
    BigDecimal high = ticker.getHigh();
    BigDecimal low = ticker.getLow();
    BigDecimal bid = ticker.getBuy();
    BigDecimal ask = ticker.getSell();
    BigDecimal last = ticker.getLast();
    BigDecimal volume = ticker.getVol();
    return new Ticker.Builder().currencyPair(currencyPair).last(last).high(high).low(low).bid(bid).ask(ask).volume(volume).build();
  }

  public static Trades adaptTrades(JubiTrade[] jubiTrades, CurrencyPair currencyPair) {

    List<Trade> tradeList = new ArrayList<>();

    for (JubiTrade jubiTrade : jubiTrades) {

      Order.OrderType orderType = jubiTrade.getType().equals("buy") ? Order.OrderType.BID : Order.OrderType.ASK;
      Trade trade = new Trade(orderType, jubiTrade.getAmount(), currencyPair, jubiTrade.getPrice(), new Date(jubiTrade.getDate() * 1000),
              jubiTrade.getTid());

      tradeList.add(trade);
    }

    return new Trades(tradeList, Trades.TradeSortType.SortByTimestamp);
  }

  public static AccountInfo adaptAccountInfo(JubiBalance jubiBalance, String userName) {

    List<Balance> balances = new ArrayList<>();
    for (Map.Entry<String, BigDecimal> funds : jubiBalance.getAvailableFunds().entrySet()) {
      Currency currency = Currency.getInstance(funds.getKey().toUpperCase());
      BigDecimal amount = funds.getValue();
      BigDecimal locked = jubiBalance.getLockedFunds().get(funds.getKey());
      balances.add(new Balance(currency, null, amount, locked));
    }
    return new AccountInfo(userName, new Wallet(jubiBalance.getUid(), userName, balances));
  }
}
