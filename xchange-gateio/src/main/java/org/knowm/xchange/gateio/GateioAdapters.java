package org.knowm.xchange.gateio;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.account.FundingRecord.Status;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.marketdata.Trades.TradeSortType;
import org.knowm.xchange.dto.meta.CurrencyMetaData;
import org.knowm.xchange.dto.meta.CurrencyPairMetaData;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.dto.meta.WalletHealth;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.gateio.dto.GateioOrderType;
import org.knowm.xchange.gateio.dto.account.GateioDepositsWithdrawals;
import org.knowm.xchange.gateio.dto.account.GateioFunds;
import org.knowm.xchange.gateio.dto.marketdata.GateioCoin;
import org.knowm.xchange.gateio.dto.marketdata.GateioDepth;
import org.knowm.xchange.gateio.dto.marketdata.GateioFeeInfo;
import org.knowm.xchange.gateio.dto.marketdata.GateioMarketInfoWrapper.GateioMarketInfo;
import org.knowm.xchange.gateio.dto.marketdata.GateioPublicOrder;
import org.knowm.xchange.gateio.dto.marketdata.GateioTicker;
import org.knowm.xchange.gateio.dto.marketdata.GateioTradeHistory;
import org.knowm.xchange.gateio.dto.trade.GateioOpenOrder;
import org.knowm.xchange.gateio.dto.trade.GateioOpenOrders;
import org.knowm.xchange.gateio.dto.trade.GateioTrade;
import org.knowm.xchange.gateio.service.GateioMarketDataServiceRaw;
import org.knowm.xchange.utils.DateUtils;

/** Various adapters for converting from Bter DTOs to XChange DTOs */
public final class GateioAdapters {

  /** private Constructor */
  private GateioAdapters() {}

  public static CurrencyPair adaptCurrencyPair(String pair) {

    final String[] currencies = pair.toUpperCase().split("_");
    return new CurrencyPair(currencies[0], currencies[1]);
  }

  public static Ticker adaptTicker(CurrencyPair currencyPair, GateioTicker gateioTicker) {

    BigDecimal ask = gateioTicker.getLowestAsk();
    BigDecimal bid = gateioTicker.getHighestBid();
    BigDecimal last = gateioTicker.getLast();
    BigDecimal low = gateioTicker.getLow24hr();
    BigDecimal high = gateioTicker.getHigh24hr();
    // Looks like gate.io vocabulary is inverted...
    BigDecimal baseVolume = gateioTicker.getQuoteVolume();
    BigDecimal quoteVolume = gateioTicker.getBaseVolume();
    BigDecimal percentageChange = gateioTicker.getPercentChange();

    return new Ticker.Builder()
        .currencyPair(currencyPair)
        .ask(ask)
        .bid(bid)
        .last(last)
        .low(low)
        .high(high)
        .volume(baseVolume)
        .quoteVolume(quoteVolume)
        .percentageChange(percentageChange)
        .build();
  }

  public static LimitOrder adaptOrder(
      GateioPublicOrder order, CurrencyPair currencyPair, OrderType orderType) {

    return new LimitOrder(orderType, order.getAmount(), currencyPair, "", null, order.getPrice());
  }

  public static List<LimitOrder> adaptOrders(
      List<GateioPublicOrder> orders, CurrencyPair currencyPair, OrderType orderType) {

    List<LimitOrder> limitOrders = new ArrayList<>();

    for (GateioPublicOrder bterOrder : orders) {
      limitOrders.add(adaptOrder(bterOrder, currencyPair, orderType));
    }

    return limitOrders;
  }

  public static OrderBook adaptOrderBook(GateioDepth depth, CurrencyPair currencyPair) {

    List<LimitOrder> asks =
        GateioAdapters.adaptOrders(depth.getAsks(), currencyPair, OrderType.ASK);
    Collections.reverse(asks);
    List<LimitOrder> bids =
        GateioAdapters.adaptOrders(depth.getBids(), currencyPair, OrderType.BID);

    return new OrderBook(null, asks, bids);
  }

  public static LimitOrder adaptOrder(
      GateioOpenOrder order, Collection<CurrencyPair> currencyPairs) {

    String[] currencyPairSplit = order.getCurrencyPair().split("_");
    CurrencyPair currencyPair = new CurrencyPair(currencyPairSplit[0], currencyPairSplit[1]);
    return new LimitOrder(
        order.getType().equals("sell") ? OrderType.ASK : OrderType.BID,
        order.getAmount(),
        currencyPair,
        order.getOrderNumber(),
        null,
        order.getRate());
  }

  public static OpenOrders adaptOpenOrders(
      GateioOpenOrders openOrders, Collection<CurrencyPair> currencyPairs) {

    List<LimitOrder> adaptedOrders = new ArrayList<>();
    for (GateioOpenOrder openOrder : openOrders.getOrders()) {
      adaptedOrders.add(adaptOrder(openOrder, currencyPairs));
    }

    return new OpenOrders(adaptedOrders);
  }

  public static OrderType adaptOrderType(GateioOrderType cryptoTradeOrderType) {

    return (cryptoTradeOrderType.equals(GateioOrderType.BUY)) ? OrderType.BID : OrderType.ASK;
  }

  public static Trade adaptTrade(
      GateioTradeHistory.GateioPublicTrade trade, CurrencyPair currencyPair) {

    OrderType orderType = adaptOrderType(trade.getType());
    Date timestamp = DateUtils.fromMillisUtc(trade.getDate() * 1000);

    return new Trade.Builder()
        .type(orderType)
        .originalAmount(trade.getAmount())
        .currencyPair(currencyPair)
        .price(trade.getPrice())
        .timestamp(timestamp)
        .id(trade.getTradeId())
        .build();
  }

  public static Trades adaptTrades(GateioTradeHistory tradeHistory, CurrencyPair currencyPair) {

    List<Trade> tradeList = new ArrayList<>();
    long lastTradeId = 0;
    for (GateioTradeHistory.GateioPublicTrade trade : tradeHistory.getTrades()) {
      String tradeIdString = trade.getTradeId();
      if (!tradeIdString.isEmpty()) {
        long tradeId = Long.valueOf(tradeIdString);
        if (tradeId > lastTradeId) {
          lastTradeId = tradeId;
        }
      }
      Trade adaptedTrade = adaptTrade(trade, currencyPair);
      tradeList.add(adaptedTrade);
    }

    return new Trades(tradeList, lastTradeId, TradeSortType.SortByTimestamp);
  }

  public static Wallet adaptWallet(GateioFunds bterAccountInfo) {

    List<Balance> balances = new ArrayList<>();
    for (Entry<String, BigDecimal> funds : bterAccountInfo.getAvailableFunds().entrySet()) {
      Currency currency = Currency.getInstance(funds.getKey().toUpperCase());
      BigDecimal amount = funds.getValue();
      BigDecimal locked = bterAccountInfo.getLockedFunds().get(currency.toString());

      balances.add(new Balance(currency, null, amount, locked == null ? BigDecimal.ZERO : locked));
    }
    for (Entry<String, BigDecimal> funds : bterAccountInfo.getLockedFunds().entrySet()) {
      Currency currency = Currency.getInstance(funds.getKey().toUpperCase());
      if (balances.stream().noneMatch(balance -> balance.getCurrency().equals(currency))) {
        BigDecimal amount = funds.getValue();
        balances.add(new Balance(currency, null, BigDecimal.ZERO, amount));
      }
    }

    return Wallet.Builder.from(balances).build();
  }

  public static UserTrades adaptUserTrades(List<GateioTrade> userTrades) {

    List<UserTrade> trades = new ArrayList<>();
    for (GateioTrade userTrade : userTrades) {
      trades.add(adaptUserTrade(userTrade));
    }

    return new UserTrades(trades, TradeSortType.SortByTimestamp);
  }

  public static UserTrade adaptUserTrade(GateioTrade gateioTrade) {

    OrderType orderType = adaptOrderType(gateioTrade.getType());
    Date timestamp = DateUtils.fromMillisUtc(gateioTrade.getTimeUnix() * 1000);
    CurrencyPair currencyPair = adaptCurrencyPair(gateioTrade.getPair());

    return new UserTrade.Builder()
        .type(orderType)
        .originalAmount(gateioTrade.getAmount())
        .currencyPair(currencyPair)
        .price(gateioTrade.getRate())
        .timestamp(timestamp)
        .id(gateioTrade.getTradeID())
        .orderId(gateioTrade.getOrderNumber())
        .build();
  }

  public static ExchangeMetaData adaptToExchangeMetaData(
      GateioMarketDataServiceRaw marketDataService) throws IOException {

    Map<CurrencyPair, CurrencyPairMetaData> currencyPairs = new HashMap<>();
    Map<Currency, CurrencyMetaData> currencies = new HashMap<>();

    for (Entry<CurrencyPair, GateioMarketInfo> entry :
        marketDataService.getBTERMarketInfo().entrySet()) {

      CurrencyPair currencyPair = entry.getKey();
      GateioMarketInfo btermarketInfo = entry.getValue();

      CurrencyPairMetaData currencyPairMetaData =
          new CurrencyPairMetaData(
              btermarketInfo.getFee(),
              btermarketInfo.getMinAmount(),
              null,
              btermarketInfo.getDecimalPlaces(),
              null);
      currencyPairs.put(currencyPair, currencyPairMetaData);
    }

    if (marketDataService.getApiKey() != null) {
      Map<String, GateioFeeInfo> gateioFees = marketDataService.getGateioFees();
      Map<String, GateioCoin> coins = marketDataService.getGateioCoinInfo().getCoins();
      for (String coin : coins.keySet()) {
        GateioCoin gateioCoin = coins.get(coin);
        GateioFeeInfo gateioFeeInfo = gateioFees.get(coin);
        if (gateioCoin != null && gateioFeeInfo != null) {
          currencies.put(new Currency(coin), adaptCurrencyMetaData(gateioCoin, gateioFeeInfo));
        }
      }
    }

    return new ExchangeMetaData(currencyPairs, currencies, null, null, null);
  }

  private static CurrencyMetaData adaptCurrencyMetaData(
      GateioCoin gateioCoin, GateioFeeInfo gateioFeeInfo) {
    WalletHealth walletHealth = WalletHealth.ONLINE;
    if (gateioCoin.isWithdrawDelayed()) {
      walletHealth = WalletHealth.UNKNOWN;
    } else if (gateioCoin.isDelisted()
        || (gateioCoin.isWithdrawDisabled() && gateioCoin.isDepositDisabled())) {
      walletHealth = WalletHealth.OFFLINE;
    } else if (gateioCoin.isDepositDisabled()) {
      walletHealth = WalletHealth.DEPOSITS_DISABLED;
    } else if (gateioCoin.isWithdrawDisabled()) {
      walletHealth = WalletHealth.WITHDRAWALS_DISABLED;
    }
    return new CurrencyMetaData(
        0,
        new BigDecimal(gateioFeeInfo.getWithdrawFix()),
        gateioFeeInfo.getWithdrawAmountMini(),
        walletHealth);
  }

  public static List<FundingRecord> adaptDepositsWithdrawals(
      GateioDepositsWithdrawals depositsWithdrawals) {
    List<FundingRecord> result = new ArrayList<>();

    depositsWithdrawals
        .getDeposits()
        .forEach(
            d -> {
              FundingRecord r =
                  new FundingRecord(
                      d.address,
                      d.getTimestamp(),
                      Currency.getInstance(d.currency),
                      d.amount,
                      d.id,
                      d.txid,
                      FundingRecord.Type.DEPOSIT,
                      status(d.status),
                      null,
                      null,
                      null);
              result.add(r);
            });
    depositsWithdrawals
        .getWithdraws()
        .forEach(
            w -> {
              FundingRecord r =
                  new FundingRecord(
                      w.address,
                      w.getTimestamp(),
                      Currency.getInstance(w.currency),
                      w.amount,
                      w.id,
                      w.txid,
                      FundingRecord.Type.WITHDRAWAL,
                      status(w.status),
                      null,
                      null,
                      null);
              result.add(r);
            });

    return result;
  }

  private static FundingRecord.Status status(String gateioStatus) {
    switch (gateioStatus) {
      case "DONE":
        return Status.COMPLETE;
      default:
        return Status.PROCESSING; // @TODO which statusses are possible at gate.io?
    }
  }
}
