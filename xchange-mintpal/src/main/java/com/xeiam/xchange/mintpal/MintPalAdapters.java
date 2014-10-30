package com.xeiam.xchange.mintpal;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trade;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.marketdata.Trades.TradeSortType;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.mintpal.dto.marketdata.MintPalPublicOrder;
import com.xeiam.xchange.mintpal.dto.marketdata.MintPalPublicOrders;
import com.xeiam.xchange.mintpal.dto.marketdata.MintPalPublicTrade;
import com.xeiam.xchange.mintpal.dto.marketdata.MintPalTicker;

/**
 * @author jamespedwards42
 */
public class MintPalAdapters {

  public static Collection<CurrencyPair> adaptCurrencyPairs(final List<MintPalTicker> tickers) {

    final Set<CurrencyPair> currencyPairs = new HashSet<CurrencyPair>();
    for (final MintPalTicker ticker : tickers)
      currencyPairs.add(adaptCurrencyPair(ticker));
    return currencyPairs;
  }

  public static CurrencyPair adaptCurrencyPair(final MintPalTicker mintPalTicker) {

    return new CurrencyPair(mintPalTicker.getCode(), mintPalTicker.getExchange());
  }

  public static Ticker adaptTicker(final MintPalTicker mintPalTicker) {

    MathContext mc = new MathContext(8, RoundingMode.HALF_UP);
    BigDecimal baseVolume = mintPalTicker.getVolume24Hour().divide(mintPalTicker.getLastPrice(), mc);

    return new Ticker.Builder().currencyPair(adaptCurrencyPair(mintPalTicker)).ask(mintPalTicker.getTopAsk()).bid(mintPalTicker.getTopBid()).high(mintPalTicker.getHigh24Hour())
        .low(mintPalTicker.getLow24Hour()).volume(baseVolume).last(mintPalTicker.getLastPrice()).build();
  }

  public static OrderBook adaptOrderBook(final CurrencyPair currencyPair, final List<MintPalPublicOrders> mintPalOrderBook) {

    final boolean firstIsBids = mintPalOrderBook.get(0).getType().equalsIgnoreCase("buy");
    final List<LimitOrder> bids = firstIsBids ? adaptOrders(currencyPair, OrderType.BID, mintPalOrderBook.get(0)) : adaptOrders(currencyPair, OrderType.BID, mintPalOrderBook.get(1));
    final List<LimitOrder> asks = firstIsBids ? adaptOrders(currencyPair, OrderType.ASK, mintPalOrderBook.get(1)) : adaptOrders(currencyPair, OrderType.ASK, mintPalOrderBook.get(0));

    return new OrderBook(null, asks, bids);
  }

  public static List<LimitOrder> adaptOrders(final CurrencyPair currencyPair, final OrderType orderType, final MintPalPublicOrders mintPalOrders) {

    final List<LimitOrder> orders = new ArrayList<LimitOrder>();
    for (final MintPalPublicOrder mintPalOrder : mintPalOrders.getOrders())
      orders.add(adaptOrder(currencyPair, orderType, mintPalOrder));

    return orders;
  }

  public static LimitOrder adaptOrder(final CurrencyPair currencyPair, final OrderType orderType, final MintPalPublicOrder mintPalOrder) {

    return new LimitOrder(orderType, mintPalOrder.getAmount(), currencyPair, null, null, mintPalOrder.getPrice());
  }

  public static Trades adaptPublicTrades(final CurrencyPair currencyPair, final List<MintPalPublicTrade> mintPalTrades) {

    final List<Trade> trades = new ArrayList<Trade>();
    for (final MintPalPublicTrade trade : mintPalTrades)
      trades.add(adaptPublicTrade(currencyPair, trade));

    return new Trades(trades, TradeSortType.SortByTimestamp);
  }

  public static Trade adaptPublicTrade(final CurrencyPair currencyPair, final MintPalPublicTrade mintPalTrade) {

    return new Trade(mintPalTrade.getType().equalsIgnoreCase("buy") ? OrderType.BID : OrderType.ASK, mintPalTrade.getAmount(), currencyPair, mintPalTrade.getPrice(), mintPalTrade.getTime(), null);
  }
}
