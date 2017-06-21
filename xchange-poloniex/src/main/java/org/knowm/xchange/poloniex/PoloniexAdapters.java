package org.knowm.xchange.poloniex;

import static org.knowm.xchange.dto.account.FundingRecord.Type.DEPOSIT;
import static org.knowm.xchange.dto.account.FundingRecord.Type.WITHDRAWAL;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.LoanOrder;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.marketdata.Trades.TradeSortType;
import org.knowm.xchange.dto.meta.CurrencyMetaData;
import org.knowm.xchange.dto.meta.CurrencyPairMetaData;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.dto.trade.FixedRateLoanOrder;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.poloniex.dto.LoanInfo;
import org.knowm.xchange.poloniex.dto.account.PoloniexBalance;
import org.knowm.xchange.poloniex.dto.account.PoloniexLoan;
import org.knowm.xchange.poloniex.dto.marketdata.PoloniexCurrencyInfo;
import org.knowm.xchange.poloniex.dto.marketdata.PoloniexDepth;
import org.knowm.xchange.poloniex.dto.marketdata.PoloniexLevel;
import org.knowm.xchange.poloniex.dto.marketdata.PoloniexMarketData;
import org.knowm.xchange.poloniex.dto.marketdata.PoloniexPublicTrade;
import org.knowm.xchange.poloniex.dto.marketdata.PoloniexTicker;
import org.knowm.xchange.poloniex.dto.trade.PoloniexDeposit;
import org.knowm.xchange.poloniex.dto.trade.PoloniexDepositsWithdrawalsResponse;
import org.knowm.xchange.poloniex.dto.trade.PoloniexOpenOrder;
import org.knowm.xchange.poloniex.dto.trade.PoloniexUserTrade;
import org.knowm.xchange.poloniex.dto.trade.PoloniexWithdrawal;

/**
 * @author Zach Holmes
 * @author Dave Seyb
 * @version 2.0 *
 */

public class PoloniexAdapters {

  public static Ticker adaptPoloniexTicker(PoloniexTicker poloniexTicker, CurrencyPair currencyPair) {

    PoloniexMarketData marketData = poloniexTicker.getPoloniexMarketData();

    BigDecimal last = marketData.getLast();
    BigDecimal bid = marketData.getHighestBid();
    BigDecimal ask = marketData.getLowestAsk();
    BigDecimal high = marketData.getHigh24hr();
    BigDecimal low = marketData.getLow24hr();
    BigDecimal volume = marketData.getQuoteVolume();

    return new Ticker.Builder().currencyPair(currencyPair).last(last).bid(bid).ask(ask).high(high).low(low).volume(volume).build();

  }

  public static OrderBook adaptPoloniexDepth(PoloniexDepth depth, CurrencyPair currencyPair) {

    List<LimitOrder> asks = adaptPoloniexPublicOrders(depth.getAsks(), OrderType.ASK, currencyPair);
    List<LimitOrder> bids = adaptPoloniexPublicOrders(depth.getBids(), OrderType.BID, currencyPair);

    return new OrderBook(null, asks, bids);
  }

  public static List<LimitOrder> adaptPoloniexPublicOrders(List<List<BigDecimal>> rawLevels, OrderType orderType, CurrencyPair currencyPair) {

    List<PoloniexLevel> levels = new ArrayList<>();

    for (List<BigDecimal> rawLevel : rawLevels) {
      levels.add(adaptRawPoloniexLevel(rawLevel));
    }

    List<LimitOrder> orders = new ArrayList<>();

    for (PoloniexLevel level : levels) {

      LimitOrder limitOrder = new LimitOrder.Builder(orderType, currencyPair).tradableAmount(level.getAmount()).limitPrice(level.getLimit()).build();
      orders.add(limitOrder);
    }
    return orders;
  }

  public static PoloniexLevel adaptRawPoloniexLevel(List<BigDecimal> level) {

    PoloniexLevel poloniexLevel = new PoloniexLevel(level.get(1), level.get(0));
    return poloniexLevel;
  }

  public static Trades adaptPoloniexPublicTrades(PoloniexPublicTrade[] poloniexPublicTrades, CurrencyPair currencyPair) {

    List<Trade> trades = new ArrayList<>();

    for (PoloniexPublicTrade poloniexTrade : poloniexPublicTrades) {
      trades.add(adaptPoloniexPublicTrade(poloniexTrade, currencyPair));
    }

    return new Trades(trades, TradeSortType.SortByTimestamp);
  }

  public static Trade adaptPoloniexPublicTrade(PoloniexPublicTrade poloniexTrade, CurrencyPair currencyPair) {

    OrderType type = poloniexTrade.getType().equalsIgnoreCase("buy") ? OrderType.BID : OrderType.ASK;
    Date timestamp = PoloniexUtils.stringToDate(poloniexTrade.getDate());

    Trade trade = new Trade(type, poloniexTrade.getAmount(), currencyPair, poloniexTrade.getRate(), timestamp, poloniexTrade.getTradeID());
    return trade;
  }

  public static List<Balance> adaptPoloniexBalances(HashMap<String, PoloniexBalance> poloniexBalances) {

    List<Balance> balances = new ArrayList<>();

    for (Map.Entry<String, PoloniexBalance> item : poloniexBalances.entrySet()) {

      Currency currency = Currency.getInstance(item.getKey());
      balances.add(new Balance(currency, null, item.getValue().getAvailable(), item.getValue().getOnOrders()));
    }

    return balances;
  }

  public static LoanInfo adaptPoloniexLoans(HashMap<String, PoloniexLoan[]> poloniexLoans) {

    Map<String, List<LoanOrder>> loans = new HashMap<>();

    for (Map.Entry<String, PoloniexLoan[]> item : poloniexLoans.entrySet()) {
      List<LoanOrder> loanOrders = new ArrayList<>();
      for (PoloniexLoan poloniexLoan : item.getValue()) {
        Date date = PoloniexUtils.stringToDate(poloniexLoan.getDate());
        loanOrders.add(new FixedRateLoanOrder(OrderType.ASK, poloniexLoan.getCurrency(), poloniexLoan.getAmount(), poloniexLoan.getRange(),
            poloniexLoan.getId(), date, poloniexLoan.getRate())); //TODO
      }
      loans.put(item.getKey(), loanOrders);
    }

    return new LoanInfo(loans.get("provided"), loans.get("used"));
  }

  public static OpenOrders adaptPoloniexOpenOrders(Map<String, PoloniexOpenOrder[]> poloniexOpenOrders) {

    List<LimitOrder> openOrders = new ArrayList<>();
    for (String pairString : poloniexOpenOrders.keySet()) {
      CurrencyPair currencyPair = PoloniexUtils.toCurrencyPair(pairString);

      for (PoloniexOpenOrder openOrder : poloniexOpenOrders.get(pairString)) {

        openOrders.add(adaptPoloniexOpenOrder(openOrder, currencyPair));
      }
    }

    return new OpenOrders(openOrders);
  }

  public static LimitOrder adaptPoloniexOpenOrder(PoloniexOpenOrder openOrder, CurrencyPair currencyPair) {

    OrderType type = openOrder.getType().equals("buy") ? OrderType.BID : OrderType.ASK;
    Date timestamp = PoloniexUtils.stringToDate(openOrder.getDate());
    LimitOrder limitOrder = new LimitOrder.Builder(type, currencyPair).limitPrice(openOrder.getRate()).tradableAmount(openOrder.getAmount())
        .id(openOrder.getOrderNumber()).timestamp(timestamp).build();

    return limitOrder;
  }

  public static UserTrade adaptPoloniexUserTrade(PoloniexUserTrade userTrade, CurrencyPair currencyPair) {

    OrderType orderType = userTrade.getType().equalsIgnoreCase("buy") ? OrderType.BID : OrderType.ASK;
    BigDecimal amount = userTrade.getAmount();
    BigDecimal price = userTrade.getRate();
    Date date = PoloniexUtils.stringToDate(userTrade.getDate());
    String tradeId = String.valueOf(userTrade.getTradeID());
    String orderId = String.valueOf(userTrade.getOrderNumber());

    // Poloniex returns fee as a multiplier, e.g. a 0.2% fee is 0.002
    BigDecimal feeAmount = amount.multiply(price).multiply(userTrade.getFee());

    return new UserTrade(orderType, amount, currencyPair, price, date, tradeId, orderId, feeAmount,
        Currency.getInstance(currencyPair.counter.getCurrencyCode()));
  }

  public static ExchangeMetaData adaptToExchangeMetaData(Map<String, PoloniexCurrencyInfo> poloniexCurrencyInfo,
      Map<String, PoloniexMarketData> poloniexMarketData, ExchangeMetaData exchangeMetaData) {

    Map<Currency, CurrencyMetaData> currencyMetaDataMap = exchangeMetaData.getCurrencies();
    CurrencyMetaData currencyArchetype = currencyMetaDataMap.values().iterator().next();

    for (Map.Entry<String, PoloniexCurrencyInfo> entry : poloniexCurrencyInfo.entrySet()) {

      Currency ccy = Currency.getInstance(entry.getKey());

      if(!currencyMetaDataMap.containsKey(ccy))
        currencyMetaDataMap.put(ccy, currencyArchetype);
    }

    Map<CurrencyPair, CurrencyPairMetaData> marketMetaDataMap = exchangeMetaData.getCurrencyPairs();
    CurrencyPairMetaData marketArchetype = marketMetaDataMap.values().iterator().next();

    for (String market : poloniexMarketData.keySet()) {
      CurrencyPair currencyPair = PoloniexUtils.toCurrencyPair(market);

      if(!marketMetaDataMap.containsKey(currencyPair))
        marketMetaDataMap.put(currencyPair, marketArchetype);
    }

    return exchangeMetaData;
  }

  public static List<FundingRecord> adaptFundingRecords(PoloniexDepositsWithdrawalsResponse poloFundings) {
    final ArrayList<FundingRecord> fundingRecords = new ArrayList<>();
    for (PoloniexDeposit d : poloFundings.getDeposits()) {
      fundingRecords.add(new FundingRecord(d.getAddress(), d.getTimestamp(), new Currency(d.getCurrency()),
          d.getAmount(), null, d.getTxid(), DEPOSIT, FundingRecord.Status.resolveStatus(d.getStatus()), null, null, d.getStatus()));
    }
    for (PoloniexWithdrawal w : poloFundings.getWithdrawals()) {
      final String[] statusParts = w.getStatus().split(": *");
      final String statusStr = statusParts[0];
      final FundingRecord.Status status = FundingRecord.Status.resolveStatus(statusStr);
      final String externalId = statusParts.length == 1 ? null : statusParts[1];
      fundingRecords.add(new FundingRecord(w.getAddress(), w.getTimestamp(), new Currency(w.getCurrency()),
          w.getAmount(), String.valueOf(w.getWithdrawalNumber()), externalId, WITHDRAWAL, status, null, null, w.getStatus()));
    }
    return fundingRecords;
  }
}
