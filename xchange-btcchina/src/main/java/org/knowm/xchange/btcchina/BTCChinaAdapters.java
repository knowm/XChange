package org.knowm.xchange.btcchina;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.knowm.xchange.btcchina.dto.BTCChinaResponse;
import org.knowm.xchange.btcchina.dto.BTCChinaValue;
import org.knowm.xchange.btcchina.dto.account.BTCChinaAccountInfo;
import org.knowm.xchange.btcchina.dto.account.BTCChinaDeposit;
import org.knowm.xchange.btcchina.dto.account.BTCChinaWithdrawal;
import org.knowm.xchange.btcchina.dto.account.response.BTCChinaGetDepositsResponse;
import org.knowm.xchange.btcchina.dto.account.response.BTCChinaGetWithdrawalsResponse;
import org.knowm.xchange.btcchina.dto.marketdata.BTCChinaDepth;
import org.knowm.xchange.btcchina.dto.marketdata.BTCChinaTicker;
import org.knowm.xchange.btcchina.dto.marketdata.BTCChinaTickerObject;
import org.knowm.xchange.btcchina.dto.marketdata.BTCChinaTrade;
import org.knowm.xchange.btcchina.dto.trade.BTCChinaMarketDepth;
import org.knowm.xchange.btcchina.dto.trade.BTCChinaMarketDepthOrder;
import org.knowm.xchange.btcchina.dto.trade.BTCChinaOrder;
import org.knowm.xchange.btcchina.dto.trade.BTCChinaOrderDetail;
import org.knowm.xchange.btcchina.dto.trade.BTCChinaOrders;
import org.knowm.xchange.btcchina.dto.trade.BTCChinaTransaction;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderStatus;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.marketdata.Trades.TradeSortType;
import org.knowm.xchange.dto.meta.CurrencyMetaData;
import org.knowm.xchange.dto.meta.CurrencyPairMetaData;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.utils.DateUtils;

/**
 * Various adapters for converting from BTCChina DTOs to XChange DTOs.
 */
public final class BTCChinaAdapters {

  private static final int TICKER_MARKET_KEY_PREFIX_LENGTH = "ticker_".length();
  private static final int ORDERS_MARKET_KEY_PREFIX_LENGTH = "order_".length();

  /**
   * private Constructor
   */
  private BTCChinaAdapters() {

  }

  /**
   * Adapts an array of btcchinaOrders to a List of LimitOrders
   */
  public static List<LimitOrder> adaptOrders(BigDecimal[][] btcchinaOrders, CurrencyPair currencyPair, OrderType orderType) {

    List<LimitOrder> limitOrders = new ArrayList<>(btcchinaOrders.length);

    for (BigDecimal[] btcchinaOrder : btcchinaOrders) {
      limitOrders.add(adaptOrder(btcchinaOrder[1], btcchinaOrder[0], currencyPair, orderType));
    }

    return limitOrders;
  }

  /**
   * Adapts a BTCChinaOrder to a LimitOrder
   */
  public static LimitOrder adaptOrder(BigDecimal amount, BigDecimal price, CurrencyPair currencyPair, OrderType orderType) {

    return new LimitOrder(orderType, amount, currencyPair, "", null, price);

  }

  /**
   * Adapts a BTCChinaTrade to a Trade Object
   *
   * @param btcChinaTrade A BTCChina trade
   * @return The XChange Trade
   */
  public static Trade adaptTrade(BTCChinaTrade btcChinaTrade, CurrencyPair currencyPair) {

    BigDecimal amount = btcChinaTrade.getAmount();
    BigDecimal price = btcChinaTrade.getPrice();
    Date date = adaptDate(btcChinaTrade.getDate());
    OrderType orderType = btcChinaTrade.getOrderType().equals("sell") ? OrderType.ASK : OrderType.BID;

    final String tradeId = String.valueOf(btcChinaTrade.getTid());
    return new Trade(orderType, amount, currencyPair, price, date, tradeId);
  }

  public static Trades adaptTrades(List<BTCChinaTrade> btcchinaTrades, CurrencyPair currencyPair) {

    return adaptTrades(btcchinaTrades.toArray(new BTCChinaTrade[0]), currencyPair);
  }

  /**
   * Adapts a BTCChinaTrade[] to a Trades Object.
   *
   * @param btcchinaTrades The BTCChina trade data
   * @return The trades
   */
  public static Trades adaptTrades(BTCChinaTrade[] btcchinaTrades, CurrencyPair currencyPair) {

    List<Trade> tradesList = new ArrayList<>(btcchinaTrades.length);
    long latestTradeId = 0;
    for (BTCChinaTrade btcchinaTrade : btcchinaTrades) {
      long tradeId = btcchinaTrade.getTid();
      if (tradeId > latestTradeId) {
        latestTradeId = tradeId;
      }
      tradesList.add(adaptTrade(btcchinaTrade, currencyPair));
    }
    return new Trades(tradesList, latestTradeId, TradeSortType.SortByID);
  }

  /**
   * Adapts a BTCChinaTicker to a Ticker Object
   */
  public static Ticker adaptTicker(BTCChinaTicker btcChinaTicker, CurrencyPair currencyPair) {

    return adaptTicker(btcChinaTicker.getTicker(), currencyPair);
  }

  public static Ticker adaptTicker(BTCChinaTickerObject ticker, CurrencyPair currencyPair) {

    BigDecimal last = ticker.getLast();
    BigDecimal high = ticker.getHigh();
    BigDecimal low = ticker.getLow();
    BigDecimal vwap = ticker.getVwap();
    BigDecimal buy = ticker.getBuy();
    BigDecimal sell = ticker.getSell();
    BigDecimal volume = ticker.getVol();
    Date date = adaptDate(ticker.getDate());

    return new Ticker.Builder().currencyPair(currencyPair).last(last).high(high).low(low).vwap(vwap).bid(buy).ask(sell).volume(volume).timestamp(date)
        .build();
  }

  public static Map<CurrencyPair, Ticker> adaptTickers(BTCChinaTicker btcChinaTicker) {

    Map<CurrencyPair, Ticker> tickers = new LinkedHashMap<>(btcChinaTicker.size());
    for (Map.Entry<String, BTCChinaTickerObject> entry : btcChinaTicker.entrySet()) {
      CurrencyPair currencyPair = adaptCurrencyPairFromTickerMarketKey(entry.getKey());
      tickers.put(currencyPair, adaptTicker(entry.getValue(), currencyPair));
    }
    return tickers;
  }

  /**
   * Adapts a BTCChinaAccountInfoResponse to Wallet Object
   */
  public static AccountInfo adaptAccountInfo(BTCChinaResponse<BTCChinaAccountInfo> response) {

    BTCChinaAccountInfo result = response.getResult();
    return new AccountInfo(result.getProfile().getUsername(), result.getProfile().getTradeFee(),
        BTCChinaAdapters.adaptWallet(result.getBalances(), result.getFrozens(), result.getLoans()));
  }

  public static Wallet adaptWallet(Map<String, BTCChinaValue> balances, Map<String, BTCChinaValue> frozens, Map<String, BTCChinaValue> loans) {

    List<Balance> wallet = new ArrayList<>(balances.size());

    for (Map.Entry<String, BTCChinaValue> entry : balances.entrySet()) {
      BTCChinaValue frozen = frozens.get(entry.getKey());
      BTCChinaValue loan = loans.get(entry.getKey());

      BigDecimal balanceAmount = BTCChinaUtils.valueToBigDecimal(entry.getValue());
      BigDecimal frozenAmount = frozen == null ? BigDecimal.ZERO : BTCChinaUtils.valueToBigDecimal(frozen);
      BigDecimal loanAmount = loan == null ? BigDecimal.ZERO : BTCChinaUtils.valueToBigDecimal(loan);

      wallet.add(new Balance.Builder().currency(Currency.getInstance(entry.getValue().getCurrency())).available(balanceAmount).frozen(frozenAmount)
          .borrowed(loanAmount).build());
    }
    return new Wallet(wallet);

  }

  /**
   * Adapts {@link BTCChinaDepth} to {@link OrderBook}.
   *
   * @param btcChinaDepth {@link BTCChinaDepth}
   * @param currencyPair the currency pair of the depth.
   * @return {@link OrderBook}
   */
  public static OrderBook adaptOrderBook(BTCChinaDepth btcChinaDepth, CurrencyPair currencyPair) {

    List<LimitOrder> asks = BTCChinaAdapters.adaptOrders(btcChinaDepth.getAsksArray(), currencyPair, OrderType.ASK);
    Collections.reverse(asks);
    List<LimitOrder> bids = BTCChinaAdapters.adaptOrders(btcChinaDepth.getBidsArray(), currencyPair, OrderType.BID);

    return new OrderBook(BTCChinaAdapters.adaptDate(btcChinaDepth.getDate()), asks, bids);
  }

  public static OrderBook adaptOrderBook(BTCChinaMarketDepth marketDepth, CurrencyPair currencyPair) {

    List<LimitOrder> asks = adaptLimitOrders(marketDepth.getAsks(), OrderType.ASK, currencyPair);
    List<LimitOrder> bids = adaptLimitOrders(marketDepth.getBids(), OrderType.BID, currencyPair);
    return new OrderBook(adaptDate(marketDepth.getDate()), asks, bids);
  }

  public static List<LimitOrder> adaptLimitOrders(BTCChinaMarketDepthOrder[] orders, OrderType orderType, CurrencyPair currencyPair) {

    List<LimitOrder> limitOrders = new ArrayList<>(orders.length);
    for (BTCChinaMarketDepthOrder order : orders) {
      limitOrders.add(adaptLimitOrder(order, orderType, currencyPair));
    }
    return limitOrders;
  }

  public static LimitOrder adaptLimitOrder(BTCChinaMarketDepthOrder order, OrderType orderType, CurrencyPair currencyPair) {

    return new LimitOrder.Builder(orderType, currencyPair).limitPrice(order.getPrice()).tradableAmount(order.getAmount()).build();
  }

  public static List<LimitOrder> adaptOrders(BTCChinaOrder[] orders, CurrencyPair currencyPair) {

    List<LimitOrder> limitOrders = new ArrayList<>(orders.length);

    for (BTCChinaOrder order : orders) {
      LimitOrder limitOrder = adaptLimitOrder(order, currencyPair);
      limitOrders.add(limitOrder);
    }

    return limitOrders;
  }

  public static List<LimitOrder> adaptOrders(BTCChinaOrders orders, CurrencyPair specifiedCurrencyPair) {

    List<LimitOrder> limitOrders = new ArrayList<>();

    BTCChinaOrder[] certainCurrencyPairOrders = orders.getOrdersArray();
    if (certainCurrencyPairOrders != null) {
      limitOrders.addAll(adaptOrders(certainCurrencyPairOrders, specifiedCurrencyPair));
    }

    for (Map.Entry<String, BTCChinaOrder[]> entry : orders.entrySet()) {
      CurrencyPair currencyPair = adaptCurrencyPairFromOrdersMarketKey(entry.getKey());
      limitOrders.addAll(adaptOrders(entry.getValue(), currencyPair));
    }

    return limitOrders;
  }

  /**
   * Adapts BTCChinaOrder to LimitOrder.
   */
  public static LimitOrder adaptLimitOrder(BTCChinaOrder order, CurrencyPair currencyPair) {

    OrderType orderType = order.getType().equals("bid") ? OrderType.BID : OrderType.ASK;
    BigDecimal amount = order.getAmount();
    String id = Long.toString(order.getId());
    Date date = adaptDate(order.getDate());
    BigDecimal price = order.getPrice();

    return new LimitOrder(orderType, amount, currencyPair, id, date, price);
  }

  public static UserTrade adaptTransaction(BTCChinaTransaction transaction) {

    final String type = transaction.getType();

    // could also be 'rebate' or other
    if (!(type.startsWith("buy") || type.startsWith("sell"))) {
      return null;
    }

    final OrderType orderType = type.startsWith("buy") ? OrderType.BID : OrderType.ASK;
    final CurrencyPair currencyPair = adaptCurrencyPair(transaction.getMarket());

    final BigDecimal amount;
    final BigDecimal money;
    final int scale;

    if (currencyPair.equals(CurrencyPair.BTC_CNY)) {
      amount = transaction.getBtcAmount().abs();
      money = transaction.getCnyAmount().abs();
      scale = BTCChinaExchange.CNY_SCALE;
    } else if (currencyPair.equals(CurrencyPair.LTC_CNY)) {
      amount = transaction.getLtcAmount().abs();
      money = transaction.getCnyAmount().abs();
      scale = BTCChinaExchange.CNY_SCALE;
    } else if (currencyPair.equals(CurrencyPair.LTC_BTC)) {
      amount = transaction.getLtcAmount().abs();
      money = transaction.getBtcAmount().abs();
      scale = BTCChinaExchange.BTC_SCALE;
    } else {
      throw new IllegalArgumentException("Unknown currency pair: " + currencyPair);
    }

    final BigDecimal price = money.divide(amount, scale, RoundingMode.HALF_EVEN);
    final Date date = adaptDate(transaction.getDate());
    final String tradeId = String.valueOf(transaction.getId());

    return new UserTrade(orderType, amount, currencyPair, price, date, tradeId, null, null, (Currency) null);
  }

  /**
   * Adapt BTCChinaTransactions to Trades.
   */
  public static UserTrades adaptTransactions(List<BTCChinaTransaction> transactions) {

    List<UserTrade> tradeHistory = new ArrayList<>(transactions.size());

    for (BTCChinaTransaction transaction : transactions) {
      UserTrade adaptTransaction = adaptTransaction(transaction);
      if (adaptTransaction != null) {
        tradeHistory.add(adaptTransaction);
      }
    }

    return new UserTrades(tradeHistory, TradeSortType.SortByID);
  }

  public static String adaptMarket(CurrencyPair currencyPair) {

    return currencyPair.base.getCurrencyCode().toLowerCase() + currencyPair.counter.getCurrencyCode().toLowerCase();
  }

  public static CurrencyPair adaptCurrencyPairFromTickerMarketKey(String market) {

    return adaptCurrencyPair(market.substring(TICKER_MARKET_KEY_PREFIX_LENGTH));
  }

  public static CurrencyPair adaptCurrencyPairFromOrdersMarketKey(String market) {

    return adaptCurrencyPair(market.substring(ORDERS_MARKET_KEY_PREFIX_LENGTH));
  }

  public static CurrencyPair adaptCurrencyPair(String market) {

    return new CurrencyPair(market.substring(0, 3).toUpperCase(), market.substring(3).toUpperCase());
  }

  public static Date adaptDate(long date) {

    return DateUtils.fromMillisUtc(date * 1000L);
  }

  public static OrderType adaptOrderType(String type) {

    return type.equals("buy") ? OrderType.BID : OrderType.ASK;
  }

  public static OrderStatus adaptOrderStatus(String status) {

    switch (status.toUpperCase()) {

      case "OPEN":
        return OrderStatus.NEW;
      case "CLOSED":
        return OrderStatus.FILLED;
      case "CANCELLED":
        return OrderStatus.CANCELED;
      case "PENDING":
        return OrderStatus.PENDING_NEW;
      case "ERROR":
        return OrderStatus.REJECTED;
      case "INSUFFICIENT_BALANCE":
        return OrderStatus.REJECTED;
      default:
        return null;
    }

  }

  public static ExchangeMetaData adaptToExchangeMetaData(Map<String, BTCChinaTickerObject> products) {

    Map<CurrencyPair, CurrencyPairMetaData> currencyPairs = new HashMap<>();
    Map<Currency, CurrencyMetaData> currencies = new HashMap<>();

    for (String product : products.keySet()) {
      CurrencyPair pair = adaptCurrencyPair(product);
      currencies.put(pair.base, null);
      currencies.put(pair.counter, null);
    }
    return new ExchangeMetaData(currencyPairs, currencies, null, null, false);

  }

  public static List<FundingRecord> adaptFundingHistory(final BTCChinaGetDepositsResponse depositsResponse,
      final BTCChinaGetWithdrawalsResponse withdrawalsResponse) {

    final List<FundingRecord> fundingRecords = new ArrayList<>();

    if (depositsResponse != null && depositsResponse.getResult() != null) {
      final BTCChinaDeposit[] deposits = depositsResponse.getResult().getDeposits();
      for (final BTCChinaDeposit deposit : deposits) {
        final FundingRecord.Status status = FundingRecord.Status.resolveStatus(deposit.getStatus());
        final FundingRecord fundingRecordEntry = new FundingRecord(deposit.getAddress(), adaptDate(deposit.getDate()), Currency.getInstance(deposit.getCurrency()),
                deposit.getAmount(), String.valueOf(deposit.getId()), null, FundingRecord.Type.DEPOSIT, status, null, null, null);
        fundingRecords.add(fundingRecordEntry);
      }
    }

    if (withdrawalsResponse != null && withdrawalsResponse.getResult() != null) {
      final BTCChinaWithdrawal[] withdrawals = withdrawalsResponse.getResult().getWithdrawals();
      for (final BTCChinaWithdrawal withdrawal : withdrawals) {
        final FundingRecord.Status status = FundingRecord.Status.resolveStatus(withdrawal.getStatus());
        final FundingRecord fundingRecordEntry = new FundingRecord(withdrawal.getAddress(), adaptDate(withdrawal.getDate()), Currency.getInstance(withdrawal.getCurrency()),
                withdrawal.getAmount(), String.valueOf(withdrawal.getId()), withdrawal.getTransaction(), FundingRecord.Type.WITHDRAWAL, status, null, null, null);
        fundingRecords.add(fundingRecordEntry);
      }
    }

    return fundingRecords;
  }

  public static UserTrades adaptUserTradesFromOrders(BTCChinaOrders orders, CurrencyPair currencyPair) {

    final List<UserTrade> tradeHistory = new ArrayList<UserTrade>();

    for (Map.Entry<String, BTCChinaOrder[]> entry : orders.entrySet()) {
      final BTCChinaOrder[] orderArr = entry.getValue();

      if (orderArr!= null && orderArr.length > 0) {

        CurrencyPair currencyPairFromKey = null;
        try {
          currencyPairFromKey = adaptCurrencyPairFromOrdersMarketKey(entry.getKey());
        } catch (Throwable e) {
          if(currencyPair != null){
            currencyPairFromKey = currencyPair;
          } else {
            throw new IllegalArgumentException("Unknown currency pair for the set with one of the order(s) as : " + orderArr[0]);
          }
        }


        for (BTCChinaOrder order : entry.getValue()) {
          final List<UserTrade> tradeList = adaptUserTradeFromOrder(order, currencyPairFromKey);
          tradeHistory.addAll(tradeList);
        }

      }
    }

    if (tradeHistory.size() > 0){
      return new UserTrades(tradeHistory, TradeSortType.SortByID);
    }

    return null;
  }


  private static List<UserTrade> adaptUserTradeFromOrder(BTCChinaOrder order, CurrencyPair currencyPair) {
    final List<UserTrade> tradeList = new ArrayList<UserTrade>();
    final BTCChinaOrderDetail[] fills = order.getDetails();
    if (fills != null && fills.length > 0){
      final OrderType orderType = order.getType().equals("bid") ? OrderType.BID : OrderType.ASK;
      final String orderId = String.valueOf(order.getId());
      final String tradeId = String.valueOf(order.getId());
      for (final BTCChinaOrderDetail fill: fills){
        final BigDecimal price = fill.getPrice();
        final BigDecimal amount = fill.getAmount();
        final Date date = adaptDate(fill.getDateline());;
        tradeList.add(new UserTrade(orderType, amount, currencyPair, price, date, tradeId, orderId, null, null));
      }
    }
    return tradeList;
  }
}
