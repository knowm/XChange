package org.knowm.xchange.bybit;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.knowm.xchange.bybit.dto.BybitResult;
import org.knowm.xchange.bybit.dto.account.allcoins.BybitAllCoinBalance;
import org.knowm.xchange.bybit.dto.account.allcoins.BybitAllCoinsBalance;
import org.knowm.xchange.bybit.dto.account.walletbalance.BybitCoinWalletBalance;
import org.knowm.xchange.bybit.dto.marketdata.tickers.BybitTicker;
import org.knowm.xchange.bybit.dto.marketdata.tickers.linear.BybitLinearInverseTicker;
import org.knowm.xchange.bybit.dto.marketdata.tickers.option.BybitOptionTicker;
import org.knowm.xchange.bybit.dto.marketdata.tickers.spot.BybitSpotTicker;
import org.knowm.xchange.bybit.dto.trade.BybitOrderStatus;
import org.knowm.xchange.bybit.dto.trade.BybitSide;
import org.knowm.xchange.bybit.dto.trade.details.BybitOrderDetail;
import org.knowm.xchange.bybit.service.BybitException;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderStatus;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Ticker.Builder;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.instrument.Instrument;

public class BybitAdapters {

  public static final List<String> QUOTE_CURRENCIES = Arrays.asList("USDT", "USDC", "BTC", "DAI");

  public static Wallet adaptBybitBalances(List<BybitCoinWalletBalance> coinWalletBalances) {
    List<Balance> balances = new ArrayList<>(coinWalletBalances.size());
    for (BybitCoinWalletBalance bybitCoinBalance : coinWalletBalances) {
      balances.add(
          new Balance(
              new Currency(bybitCoinBalance.getCoin()),
              new BigDecimal(bybitCoinBalance.getEquity()),
              new BigDecimal(bybitCoinBalance.getAvailableToWithdraw())));
    }
    return Wallet.Builder.from(balances).build();
  }

  public static Wallet adaptBybitBalances(BybitAllCoinsBalance allCoinsBalance) {
    List<Balance> balances = new ArrayList<>(allCoinsBalance.getBalance().size());
    for (BybitAllCoinBalance coinBalance : allCoinsBalance.getBalance()) {
      balances.add(
          new Balance(
              new Currency(coinBalance.getCoin()),
              coinBalance.getWalletBalance(),
              coinBalance.getTransferBalance()));
    }
    return Wallet.Builder.from(balances).build();
  }

  public static BybitSide getSideString(Order.OrderType type) {
    if (type == Order.OrderType.ASK) {
      return BybitSide.SELL;
    }
    if (type == Order.OrderType.BID) {
      return BybitSide.BUY;
    }
    throw new IllegalArgumentException("invalid order type");
  }

  public static Order.OrderType getOrderType(BybitSide side) {
    if ("sell".equalsIgnoreCase(side.name())) {
      return Order.OrderType.ASK;
    }
    if ("buy".equalsIgnoreCase(side.name())) {
      return Order.OrderType.BID;
    }
    throw new IllegalArgumentException("invalid order type");
  }

  public static String convertToBybitSymbol(String instrumentName) {
    return instrumentName.replace("/", "").toUpperCase();
  }

  public static CurrencyPair guessSymbol(String symbol) {
    for (String quoteCurrency : QUOTE_CURRENCIES) {
      if (symbol.endsWith(quoteCurrency)) {
        int splitIndex = symbol.lastIndexOf(quoteCurrency);
        return new CurrencyPair(symbol.substring(0, splitIndex), symbol.substring(splitIndex));
      }
    }
    int splitIndex = symbol.length() - 3;
    return new CurrencyPair(symbol.substring(0, splitIndex), symbol.substring(splitIndex));
  }

  public static LimitOrder adaptBybitOrderDetails(BybitOrderDetail bybitOrderResult) {
    LimitOrder limitOrder =
        new LimitOrder(
            getOrderType(bybitOrderResult.getSide()),
            bybitOrderResult.getQty(),
            bybitOrderResult.getCumExecQty(),
            guessSymbol(bybitOrderResult.getSymbol()),
            bybitOrderResult.getOrderId(),
            bybitOrderResult.getCreatedTime(),
            bybitOrderResult.getPrice()) {};
    limitOrder.setAveragePrice(bybitOrderResult.getAvgPrice());
    limitOrder.setOrderStatus(adaptBybitOrderStatus(bybitOrderResult.getOrderStatus()));
    return limitOrder;
  }

  private static OrderStatus adaptBybitOrderStatus(BybitOrderStatus orderStatus) {
    switch (orderStatus) {
      case CREATED:
        return OrderStatus.OPEN;
      case NEW:
        return OrderStatus.NEW;
      case REJECTED:
        return OrderStatus.REJECTED;
      case PARTIALLY_FILLED:
      case ACTIVE:
        return OrderStatus.PARTIALLY_FILLED;
      case PARTIALLY_FILLED_CANCELED:
        return OrderStatus.PARTIALLY_CANCELED;
      case FILLED:
        return OrderStatus.FILLED;
      case CANCELLED:
        return OrderStatus.CANCELED;
      case UNTRIGGERED:
      case TRIGGERED:
        return OrderStatus.UNKNOWN;
      case DEACTIVATED:
        return OrderStatus.STOPPED;
      default:
        throw new IllegalStateException("Unexpected value: " + orderStatus);
    }
  }

  public static <T> BybitException createBybitExceptionFromResult(BybitResult<T> walletBalances) {
    return new BybitException(
        walletBalances.getRetCode(), walletBalances.getRetMsg(), walletBalances.getRetExtInfo());
  }

  public static Ticker adaptBybitLinearInverseTicker(
      Instrument instrument, Date time, BybitLinearInverseTicker bybitTicker) {
    return adaptBybitTickerBuilder(instrument, time, bybitTicker)
        .open(bybitTicker.getPrevPrice24h())
        .percentageChange(bybitTicker.getPrice24hPcnt())
        .build();
  }

  public static Ticker adaptBybitSpotTicker(
      Instrument instrument, Date time, BybitSpotTicker bybitTicker) {
    return adaptBybitTickerBuilder(instrument, time, bybitTicker)
        .open(bybitTicker.getPrevPrice24h())
        .percentageChange(bybitTicker.getPrice24hPcnt())
        .build();
  }

  public static Ticker adaptBybitOptionTicker(
      Instrument instrument, Date time, BybitOptionTicker bybitTicker) {
    return adaptBybitTickerBuilder(instrument, time, bybitTicker).build();
  }

  private static Builder adaptBybitTickerBuilder(
      Instrument instrument, Date time, BybitTicker bybitTicker) {
    return new Ticker.Builder()
        .timestamp(time)
        .instrument(instrument)
        .last(bybitTicker.getLastPrice())
        .bid(bybitTicker.getBid1Price())
        .bidSize(bybitTicker.getBid1Size())
        .ask(bybitTicker.getAsk1Price())
        .askSize(bybitTicker.getAsk1Size())
        .high(bybitTicker.getHighPrice24h())
        .low(bybitTicker.getLowPrice24h())
        .quoteVolume(bybitTicker.getTurnover24h())
        .volume(bybitTicker.getVolume24h());
  }
}
