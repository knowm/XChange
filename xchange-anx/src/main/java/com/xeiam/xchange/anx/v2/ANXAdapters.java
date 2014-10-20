package com.xeiam.xchange.anx.v2;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.xeiam.xchange.anx.v2.dto.account.polling.ANXAccountInfo;
import com.xeiam.xchange.anx.v2.dto.account.polling.ANXWallet;
import com.xeiam.xchange.anx.v2.dto.account.polling.Wallets;
import com.xeiam.xchange.anx.v2.dto.marketdata.ANXOrder;
import com.xeiam.xchange.anx.v2.dto.marketdata.ANXTicker;
import com.xeiam.xchange.anx.v2.dto.marketdata.ANXTrade;
import com.xeiam.xchange.anx.v2.dto.trade.polling.ANXOpenOrder;
import com.xeiam.xchange.anx.v2.dto.trade.polling.ANXTradeResult;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Ticker.TickerBuilder;
import com.xeiam.xchange.dto.marketdata.Trade;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.marketdata.Trades.TradeSortType;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.Wallet;
import com.xeiam.xchange.utils.DateUtils;

/**
 * Various adapters for converting from anx DTOs to XChange DTOs
 */
public final class ANXAdapters {

  private static final String SIDE_BID = "bid";
  private static final int PRICE_SCALE = 8;

  /**
   * private Constructor
   */
  private ANXAdapters() {

  }

  /**
   * Adapts a ANXAccountInfo to a AccountInfo
   * 
   * @param anxAccountInfo
   * @return
   */
  public static AccountInfo adaptAccountInfo(ANXAccountInfo anxAccountInfo) {

    // Adapt to XChange DTOs
    AccountInfo accountInfo = new AccountInfo(anxAccountInfo.getLogin(), anxAccountInfo.getTradeFee(), ANXAdapters.adaptWallets(anxAccountInfo.getWallets()));
    return accountInfo;
  }

  /**
   * Adapts a ANXOrder to a LimitOrder
   * 
   * @param price
   * @param currency
   * @param orderTypeString
   * @return
   */
  public static LimitOrder adaptOrder(BigDecimal amount, BigDecimal price, String tradedCurrency, String transactionCurrency, String orderTypeString, String id, Date timestamp) {

    // place a limit order
    OrderType orderType = SIDE_BID.equalsIgnoreCase(orderTypeString) ? OrderType.BID : OrderType.ASK;
    CurrencyPair currencyPair = adaptCurrencyPair(tradedCurrency, transactionCurrency);

    LimitOrder limitOrder = new LimitOrder(orderType, amount, currencyPair, id, timestamp, price);

    return limitOrder;

  }

  /**
   * Adapts a List of ANXOrders to a List of LimitOrders
   * 
   * @param anxOrders
   * @param currency
   * @param orderType
   * @return
   */
  public static List<LimitOrder> adaptOrders(List<ANXOrder> anxOrders, String tradedCurrency, String currency, String orderType, String id) {

    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();

    for (ANXOrder anxOrder : anxOrders) {
      limitOrders.add(adaptOrder(anxOrder.getAmount(), anxOrder.getPrice(), tradedCurrency, currency, orderType, id, new Date(anxOrder.getStamp())));
    }

    return limitOrders;
  }

  public static List<LimitOrder> adaptOrders(ANXOpenOrder[] anxOpenOrders) {

    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();

    for (int i = 0; i < anxOpenOrders.length; i++) {
      limitOrders.add(adaptOrder(anxOpenOrders[i].getAmount().getValue(), anxOpenOrders[i].getPrice().getValue(), anxOpenOrders[i].getItem(), anxOpenOrders[i].getCurrency(), anxOpenOrders[i]
          .getType(), anxOpenOrders[i].getOid(), new Date(anxOpenOrders[i].getDate())));
    }

    return limitOrders;
  }

  /**
   * Adapts a ANX Wallet to a XChange Wallet
   * 
   * @param anxWallet
   * @return
   */
  public static Wallet adaptWallet(ANXWallet anxWallet) {

    if (anxWallet == null) { // use the presence of a currency String to indicate existing wallet at ANX
      return null; // an account maybe doesn't contain a ANXWallet
    }
    else {
      return new Wallet(anxWallet.getBalance().getCurrency(), anxWallet.getBalance().getValue());
    }

  }

  /**
   * Adapts a List of ANX Wallets to a List of XChange Wallets
   * 
   * @param anxWallets
   * @return
   */
  public static List<Wallet> adaptWallets(Wallets anxWallets) {

    List<Wallet> wallets = new ArrayList<Wallet>();

    for (ANXWallet anxWallet : anxWallets.getANXWallets()) {
      Wallet wallet = adaptWallet(anxWallet);
      if (wallet != null) {
        wallets.add(wallet);
      }
    }
    return wallets;

  }

  // public static OrderBookUpdate adaptDepthUpdate(ANXDepthUpdate anxDepthUpdate) {
  //
  // OrderType orderType = anxDepthUpdate.getTradeType().equals("bid") ? OrderType.BID : OrderType.ASK;
  // BigDecimal volume = new BigDecimal(anxDepthUpdate.getVolumeInt()).divide(new BigDecimal(ANXUtils.BTC_VOLUME_AND_AMOUNT_INT_2_DECIMAL_FACTOR));
  //
  // String tradableIdentifier = anxDepthUpdate.getItem();
  // String transactionCurrency = anxDepthUpdate.getCurrency();
  // CurrencyPair currencyPair = new CurrencyPair(tradableIdentifier, transactionCurrency);
  //
  // BigDecimal price = ANXUtils.getPrice(anxDepthUpdate.getPriceInt());
  //
  // BigDecimal totalVolume = new BigDecimal(anxDepthUpdate.getTotalVolumeInt()).divide(new BigDecimal(ANXUtils.BTC_VOLUME_AND_AMOUNT_INT_2_DECIMAL_FACTOR));
  // Date date = new Date(anxDepthUpdate.getNow() / 1000);
  //
  // OrderBookUpdate orderBookUpdate = new OrderBookUpdate(orderType, volume, currencyPair, price, date, totalVolume);
  //
  // return orderBookUpdate;
  // }

  /**
   * Adapts ANXTrade's to a Trades Object
   * 
   * @param anxTrades
   * @return
   */
  public static Trades adaptTrades(List<ANXTrade> anxTrades) {

    List<Trade> tradesList = new ArrayList<Trade>();
    long latestTid = 0;
    for (ANXTrade anxTrade : anxTrades) {
      long tid = anxTrade.getTid();
      if (tid > latestTid)
        latestTid = tid;
      tradesList.add(adaptTrade(anxTrade));
    }
    return new Trades(tradesList, latestTid, TradeSortType.SortByID);
  }

  /**
   * Adapts a ANXTrade to a Trade Object
   * 
   * @param anxTrade
   * @return
   */
  public static Trade adaptTrade(ANXTrade anxTrade) {

    OrderType orderType = adaptSide(anxTrade.getTradeType());
    BigDecimal amount = anxTrade.getAmount();
    BigDecimal price = anxTrade.getPrice();
    CurrencyPair currencyPair = adaptCurrencyPair(anxTrade.getItem(), anxTrade.getPriceCurrency());
    Date dateTime = DateUtils.fromMillisUtc(anxTrade.getTid());
    final String tradeId = String.valueOf(anxTrade.getTid());

    return new Trade(orderType, amount, currencyPair, price, dateTime, tradeId, null);
  }

  public static Ticker adaptTicker(ANXTicker anxTicker) {

    BigDecimal volume = anxTicker.getVol().getValue();
    BigDecimal last = anxTicker.getLast().getValue();
    BigDecimal bid = anxTicker.getBuy().getValue();
    BigDecimal ask = anxTicker.getSell().getValue();
    BigDecimal high = anxTicker.getHigh().getValue();
    BigDecimal low = anxTicker.getLow().getValue();
    Date timestamp = new Date(anxTicker.getNow() / 1000);

    CurrencyPair currencyPair = adaptCurrencyPair(anxTicker.getVol().getCurrency(), anxTicker.getAvg().getCurrency());

    return TickerBuilder.newInstance().withCurrencyPair(currencyPair).withLast(last).withBid(bid).withAsk(ask).withHigh(high).withLow(low).withVolume(volume).withTimestamp(timestamp).build();

  }

  public static CurrencyPair adaptCurrencyPair(String tradeCurrency, String priceCurrency) {

    return new CurrencyPair(tradeCurrency, priceCurrency);
  }

  public static Trades adaptUserTrades(ANXTradeResult[] anxTradeResults) {

    List<Trade> trades = new ArrayList<Trade>(anxTradeResults.length);
    for (ANXTradeResult tradeResult : anxTradeResults) {
      trades.add(adaptUserTrade(tradeResult));
    }

    long lastId = trades.size() > 0 ? anxTradeResults[0].getTimestamp().getTime() : 0L;
    return new Trades(trades, lastId, TradeSortType.SortByTimestamp);
  }

  private static Trade adaptUserTrade(ANXTradeResult t) {

    BigDecimal tradedCurrencyFillAmount = t.getTradedCurrencyFillAmount();
    CurrencyPair currencyPair = adaptCurrencyPair(t.getCurrencyPair());
    BigDecimal price = t.getSettlementCurrencyFillAmount().divide(tradedCurrencyFillAmount, PRICE_SCALE, BigDecimal.ROUND_HALF_EVEN);
    OrderType type = adaptSide(t.getSide());
    return new Trade(type, tradedCurrencyFillAmount, currencyPair, price, t.getTimestamp(), t.getTradeId(), t.getOrderId());
  }

  private static CurrencyPair adaptCurrencyPair(String currencyPairRaw) {

    if ("DOGEBTC".equalsIgnoreCase(currencyPairRaw))
      return CurrencyPair.DOGE_BTC;
    else if (currencyPairRaw.length() != 6)
      throw new IllegalArgumentException("Unrecognized currency pair " + currencyPairRaw);
    else
      return new CurrencyPair(currencyPairRaw.substring(0, 3), currencyPairRaw.substring(3));
  }

  private static OrderType adaptSide(String side) {

    return SIDE_BID.equals(side) ? OrderType.BID : OrderType.ASK;
  }
}
