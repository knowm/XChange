package org.knowm.xchange.abucoins;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TimeZone;

import org.knowm.xchange.abucoins.dto.AbucoinsCreateLimitOrderRequest;
import org.knowm.xchange.abucoins.dto.AbucoinsCreateMarketOrderRequest;
import org.knowm.xchange.abucoins.dto.AbucoinsCryptoWithdrawalRequest;
import org.knowm.xchange.abucoins.dto.account.AbucoinsAccount;
import org.knowm.xchange.abucoins.dto.marketdata.AbucoinsOrderBook;
import org.knowm.xchange.abucoins.dto.marketdata.AbucoinsTicker;
import org.knowm.xchange.abucoins.dto.marketdata.AbucoinsTrade;
import org.knowm.xchange.abucoins.dto.trade.AbucoinsOrder;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderStatus;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.marketdata.Trades.TradeSortType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.service.trade.params.DefaultWithdrawFundsParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Author: bryant_harris
 */

public class AbucoinsAdapters {
  private static Logger logger = LoggerFactory.getLogger(AbucoinsAdapters.class);
          
  protected static Date parseDate(final String rawDate) {

    String modified;
    if (rawDate.length() > 23) {
      modified = rawDate.substring(0, 23);
    } else if (rawDate.endsWith("Z")) {
      switch (rawDate.length()) {
      case 20:
        modified = rawDate.substring(0, 19) + ".000";
        break;
      case 22:
        modified = rawDate.substring(0, 21) + "00";
        break;
      case 23:
        modified = rawDate.substring(0, 22) + "0";
        break;
      default:
        modified = rawDate;
        break;
      }
    } else {
      switch (rawDate.length()) {
      case 19:
        modified = rawDate + ".000";
        break;
      case 21:
        modified = rawDate + "00";
        break;
      case 22:
        modified = rawDate + "0";
        break;
      default:
        modified = rawDate;
        break;
      }
    }
    try {
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
      dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
      return dateFormat.parse(modified);
    } catch (ParseException e) {
      logger.warn("unable to parse rawDate={} modified={}", rawDate, modified, e);
      return null;
    }
  }

  /**
   * Adapts a AbucoinsTrade to a Trade Object
   *
   * @param trade        Abucoins trade object
   * @param currencyPair trade currencies
   * @return The XChange Trade
   */
  public static Trade adaptTrade(AbucoinsTrade trade, CurrencyPair currencyPair) {

    BigDecimal amount = trade.getSize();
    BigDecimal price = trade.getPrice();
    Date date = parseDate(trade.getTime());
    OrderType type = trade.getSide().equals("buy") ? OrderType.BID : OrderType.ASK;
    return new Trade(type, amount, currencyPair, price, date, trade.getTradeID());
  }

  /**
   * Adapts a AbucoinsTrade[] to a Trades Object
   *
   * @param abucoinsTrades  The Abucoins trade data returned by API
   * @param currencyPair trade currencies
   * @return The trades
   */
  public static Trades adaptTrades(AbucoinsTrade[] abucoinsTrades, CurrencyPair currencyPair) {

    List<Trade> tradesList = new ArrayList<>();
    long lastTradeId = 0;
    for (AbucoinsTrade trade : abucoinsTrades )
      tradesList.add( adaptTrade(trade, currencyPair));

    return new Trades(tradesList, lastTradeId, TradeSortType.SortByTimestamp);
  }

  /**
   * Adapts a AbucoinsTicker to a Ticker Object
   *
   * @param ticker       The exchange specific ticker
   * @param currencyPair The currency pair (e.g. BTC/USD)
   * @return The ticker
   */
  public static Ticker adaptTicker(AbucoinsTicker ticker, CurrencyPair currencyPair) {

    BigDecimal last = ticker.getPrice();
    BigDecimal bid = ticker.getBid();
    BigDecimal ask = ticker.getAsk();
    BigDecimal volume = ticker.getVolume();
    if ( ticker.getTime() == null )
      throw new RuntimeException("Null date for: " + ticker); 
    Date timestamp = parseDate(ticker.getTime());

    return new Ticker.Builder().currencyPair(currencyPair).last(last).bid(bid).ask(ask).volume(volume).timestamp(timestamp)
        .build();
  }

  /**
   * Adapts Cex.IO Depth to OrderBook Object
   *
   * @param abucoinsOrderBook        AbucoinsOrderBook order book

   * @param currencyPair The currency pair (e.g. BTC/USD)
   * @return The XChange OrderBook
   */
  public static OrderBook adaptOrderBook(AbucoinsOrderBook abucoinsOrderBook, CurrencyPair currencyPair) {

    List<LimitOrder> asks = createOrders(currencyPair, OrderType.ASK, abucoinsOrderBook.getAsks());
    List<LimitOrder> bids = createOrders(currencyPair, OrderType.BID, abucoinsOrderBook.getBids());
    
    return new OrderBook(new Date(), asks, bids);
  }
  
  public static AccountInfo adaptAccountInfo(AbucoinsAccount[] accounts) {
    // group account balances by profileID
    Map<Long,List<Balance>> mapByProfileID = new HashMap<>();
    for ( AbucoinsAccount account : accounts ) {
      List<Balance> balances = mapByProfileID.get(account.getProfileID());
      if ( balances == null ) {
        balances = new ArrayList<>();
        mapByProfileID.put(account.getProfileID(), balances);
      }
      balances.add( adaptBalance(account));
    }
    
    // create a wallet for each profileID
    List<Wallet> wallets = new ArrayList<>();
    for ( Long profileID : mapByProfileID.keySet()) {
      List<Balance> balances = mapByProfileID.get(profileID);
      wallets.add( new Wallet(String.valueOf(profileID), balances));
    }
          
    return new AccountInfo("", wallets);
  }

  /**
   * Adapts AbucoinsAccount to a Balance
   *
   * @param account AbucoinsAccount balance
   * @return The account info
   */
  public static Balance adaptBalance(AbucoinsAccount account) {
    Currency currency = Currency.getInstance( account.getCurrency());
    return new Balance(currency, account.getBalance(), account.getAvailable(), account.getHold());
  }

  public static List<LimitOrder> createOrders(CurrencyPair currencyPair, OrderType orderType, AbucoinsOrderBook.LimitOrder[] orders) {

    List<LimitOrder> limitOrders = new ArrayList<>();
    if (orders == null)
      return limitOrders;

    for (AbucoinsOrderBook.LimitOrder o : orders) {
      limitOrders.add(createOrder(currencyPair, o, orderType));
    }
    return limitOrders;
  }

  public static LimitOrder createOrder(CurrencyPair currencyPair, AbucoinsOrderBook.LimitOrder priceAndAmount, OrderType orderType) {

    return new LimitOrder(orderType, priceAndAmount.getSize(), currencyPair, "", null, priceAndAmount.getPrice()); //??
  }
  
  public static OpenOrders adaptOpenOrders(AbucoinsOrder[] orders) {
    List<LimitOrder> l = new ArrayList<>();
    for ( AbucoinsOrder order : orders )
      l.add( adaptLimitOrder( order ));
    OpenOrders retVal = new OpenOrders(l);
          
    return retVal;
  }
  
  public static Order adaptOrder(AbucoinsOrder order) {
    switch ( order.getType() ) {
    case limit:
      return adaptLimitOrder(order);

    case market:
      return adaptMarketOrder(order);

    default:
      logger.warn("Unrecognized order type " + order.getType() + " returning null for Order");
      return null;
    }
  }
  
  public static LimitOrder adaptLimitOrder(AbucoinsOrder order) {
    return new LimitOrder( adaptOrderType(order.getSide()),
                           order.getSize(),
                           order.getFilledSize(),
                           adaptCurrencyPair( order.getProductID() ),
                           order.getId(),
                           parseDate( order.getCreatedAt() ),
                           order.getPrice());
  }
  
  public static MarketOrder adaptMarketOrder(AbucoinsOrder order) {
    return new MarketOrder( adaptOrderType(order.getSide()),
                            order.getSize(),
                            adaptCurrencyPair( order.getProductID() ),
                            order.getId(),
                            parseDate( order.getCreatedAt() ),
                            order.getPrice(),
                            order.getFilledSize(),
                            null,
                            adaptOrderStatus(order.getStatus()));
  }
  
  public static OrderType adaptOrderType(AbucoinsOrder.Side side) {
    switch ( side ) {
    case buy:
      return OrderType.BID;
                  
    case sell:
      return OrderType.ASK;
                  
    default:
      logger.warn("Unrecognized Side " + side + " returning null for OrderType");
      return null;
    }
  }
  
  public static AbucoinsOrder.Side adaptAbucoinsSide(OrderType orderType) {
    switch( orderType ) {
    case BID:
      return AbucoinsOrder.Side.buy;
                  
    case ASK:
      return AbucoinsOrder.Side.sell;

    default:
      logger.warn("Unrecognized OrderType " + orderType + " returning null for Side");
      return null;
    }
  }
  
  public static OrderStatus adaptOrderStatus(AbucoinsOrder.Status status) {
    switch ( status ) {
    case pending:
      return OrderStatus.PENDING_NEW;
                  
    case open:
      return OrderStatus.NEW;
                  
    case done:
      return OrderStatus.FILLED;
                  
    case rejected:
      return OrderStatus.REJECTED;
                  
    default:
      logger.warn("Unrecognized Status " + status + " returning null for OrderStatus");
      return null;
    }
  }
  
  public static String adaptCurrencyPairToProductID(CurrencyPair currencyPair) {
    return currencyPair == null ? null : currencyPair.toString().replace('/','-');
  }
  
  public static CurrencyPair adaptCurrencyPair(String abucoinsProductID) {
    int indexOf = abucoinsProductID.indexOf('-');
    String base = abucoinsProductID.substring(0, indexOf);
    String counter = abucoinsProductID.substring(indexOf+1);
    return new CurrencyPair(Currency.getInstanceNoCreate(base), Currency.getInstanceNoCreate(counter));
  }
  
  public static AbucoinsCreateMarketOrderRequest adaptAbucoinsCreateMarketOrderRequest(MarketOrder marketOrder) {
    return new AbucoinsCreateMarketOrderRequest( adaptAbucoinsSide(marketOrder.getType()),
                                                 adaptCurrencyPairToProductID(marketOrder.getCurrencyPair()),
                                                 marketOrder.getOriginalAmount(),
                                                 null);
  }
  
  public static AbucoinsCreateLimitOrderRequest adaptAbucoinsCreateLimitOrderRequest(LimitOrder limitOrder) {
    return new AbucoinsCreateLimitOrderRequest( adaptAbucoinsSide(limitOrder.getType()),
                                                adaptCurrencyPairToProductID(limitOrder.getCurrencyPair()),
                                                null,
                                                null,
                                                limitOrder.getLimitPrice(),
                                                limitOrder.getOriginalAmount(),
                                                AbucoinsOrder.TimeInForce.GTC,
                                                null,
                                                null);
  }
  
  public static String[] adaptToSetOfIDs(String resp) {
    return resp.replaceAll("[\\[\\\"\\] ]", "").split(",");
  }
}
