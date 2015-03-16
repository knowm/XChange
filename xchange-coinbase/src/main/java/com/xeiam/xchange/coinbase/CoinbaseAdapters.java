package com.xeiam.xchange.coinbase;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.xeiam.xchange.coinbase.dto.account.CoinbaseUser;
import com.xeiam.xchange.coinbase.dto.marketdata.CoinbaseHistoricalSpotPrice;
import com.xeiam.xchange.coinbase.dto.marketdata.CoinbaseMoney;
import com.xeiam.xchange.coinbase.dto.marketdata.CoinbasePrice;
import com.xeiam.xchange.coinbase.dto.marketdata.CoinbaseSpotPriceHistory;
import com.xeiam.xchange.coinbase.dto.trade.CoinbaseTransfer;
import com.xeiam.xchange.coinbase.dto.trade.CoinbaseTransferType;
import com.xeiam.xchange.coinbase.dto.trade.CoinbaseTransfers;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades.TradeSortType;
import com.xeiam.xchange.dto.trade.UserTrade;
import com.xeiam.xchange.dto.trade.UserTrades;
import com.xeiam.xchange.dto.trade.Wallet;

/**
 * jamespedwards42
 */
public final class CoinbaseAdapters {

  private CoinbaseAdapters() {

  }

  public static AccountInfo adaptAccountInfo(CoinbaseUser user) {

    final String username = user.getEmail();
    final CoinbaseMoney balance = user.getBalance();
    final Wallet wallet = new Wallet(balance.getCurrency(), balance.getAmount());
    final List<Wallet> wallets = new ArrayList<Wallet>();
    wallets.add(wallet);

    final AccountInfo accountInfo = new AccountInfo(username, wallets);
    return accountInfo;
  }

  public static UserTrades adaptTrades(CoinbaseTransfers transfers) {

    final List<UserTrade> trades = new ArrayList<UserTrade>();
    for (CoinbaseTransfer transfer : transfers.getTransfers())
      trades.add(adaptTrade(transfer));

    return new UserTrades(trades, TradeSortType.SortByTimestamp);
  }

  public static UserTrade adaptTrade(CoinbaseTransfer transfer) {

    final OrderType orderType = adaptOrderType(transfer.getType());
    final CoinbaseMoney btcAmount = transfer.getBtcAmount();
    final BigDecimal tradableAmount = btcAmount.getAmount();
    final String tradableIdentifier = btcAmount.getCurrency();
    final CoinbaseMoney subTotal = transfer.getSubtotal();
    final String transactionCurrency = subTotal.getCurrency();
    final BigDecimal price = subTotal.getAmount().divide(tradableAmount, RoundingMode.HALF_EVEN);
    final Date timestamp = transfer.getCreatedAt();
    final String id = transfer.getTransactionId();
    final BigDecimal feeAmount = transfer.getCoinbaseFee().getAmount();
    final String feeCurrency = transfer.getCoinbaseFee().getCurrency();

    return new UserTrade(orderType, tradableAmount, new CurrencyPair(tradableIdentifier, transactionCurrency), price, timestamp, id, id, feeAmount,
        feeCurrency);
  }

  public static OrderType adaptOrderType(CoinbaseTransferType transferType) {

    switch (transferType) {
    case BUY:
      return OrderType.BID;
    case SELL:
      return OrderType.ASK;
    }
    return null;
  }

  private static final int TWENTY_FOUR_HOURS_IN_MILLIS = 1000 * 60 * 60 * 24;

  public static Ticker adaptTicker(CurrencyPair currencyPair, final CoinbasePrice buyPrice, final CoinbasePrice sellPrice,
      final CoinbaseMoney spotRate, final CoinbaseSpotPriceHistory coinbaseSpotPriceHistory) {

    final Ticker.Builder tickerBuilder = new Ticker.Builder().currencyPair(currencyPair).ask(buyPrice.getSubTotal().getAmount())
        .bid(sellPrice.getSubTotal().getAmount()).last(spotRate.getAmount());

    // Get the 24 hour high and low spot price if the history is provided.
    if (coinbaseSpotPriceHistory != null) {
      BigDecimal observedHigh = spotRate.getAmount();
      BigDecimal observedLow = spotRate.getAmount();
      Date twentyFourHoursAgo = null;
      // The spot price history list is sorted in descending order by timestamp when deserialized.
      for (CoinbaseHistoricalSpotPrice historicalSpotPrice : coinbaseSpotPriceHistory.getSpotPriceHistory()) {

        if (twentyFourHoursAgo == null)
          twentyFourHoursAgo = new Date(historicalSpotPrice.getTimestamp().getTime() - TWENTY_FOUR_HOURS_IN_MILLIS);
        else if (historicalSpotPrice.getTimestamp().before(twentyFourHoursAgo))
          break;

        final BigDecimal spotPriceAmount = historicalSpotPrice.getSpotRate();
        if (spotPriceAmount.compareTo(observedLow) < 0)
          observedLow = spotPriceAmount;
        else if (spotPriceAmount.compareTo(observedHigh) > 0)
          observedHigh = spotPriceAmount;
      }
      tickerBuilder.high(observedHigh).low(observedLow);
    }

    return tickerBuilder.build();
  }

}
