package org.knowm.xchange.coindcx;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.knowm.xchange.coindcx.dto.CoindcxOrderStatusResponse;
import org.knowm.xchange.coindcx.dto.CoindcxTickersResponse;
import org.knowm.xchange.coindcx.dto.CoindcxTradeHistoryResponse;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderStatus;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;

public final class CoindcxAdapters {

  public static Order adaptOrder(CoindcxOrderStatusResponse geminiOrderStatusResponse) {

    UUID id = geminiOrderStatusResponse.getId();
    CurrencyPair currencyPair = adaptCurrencyPair(geminiOrderStatusResponse.getMarket());
    BigDecimal averageExecutionPrice = geminiOrderStatusResponse.getAvg_price();
    BigDecimal executedAmount = geminiOrderStatusResponse.getRemaining_quantity();
    BigDecimal originalAmount = geminiOrderStatusResponse.getTotal_quantity();
    OrderType orderType =
        (geminiOrderStatusResponse.getSide().equals("buy")) ? OrderType.BID : OrderType.ASK;
    OrderStatus orderStatus = adaptOrderstatus(geminiOrderStatusResponse);
    Date timestamp = geminiOrderStatusResponse.getCreated_at();

    if (geminiOrderStatusResponse.getOrder_type().contains("limit_order")) {

      BigDecimal limitPrice = geminiOrderStatusResponse.getPrice_per_unit();

      return new LimitOrder(
          orderType,
          originalAmount,
          currencyPair,
          id.toString(),
          timestamp,
          limitPrice,
          averageExecutionPrice,
          executedAmount,
          null,
          orderStatus);

    } else if (geminiOrderStatusResponse.getOrder_type().contains("market_order")) {

      return new MarketOrder(
          orderType,
          originalAmount,
          currencyPair,
          id.toString(),
          timestamp,
          averageExecutionPrice,
          executedAmount,
          null,
          orderStatus);
    }

    throw new NotYetImplementedForExchangeException();
  }

  public static List<CurrencyPair> adaptCurrencyPairs(Collection<String> GeminiSymbol) {

    List<CurrencyPair> currencyPairs = new ArrayList<>();
    for (String symbol : GeminiSymbol) {
      currencyPairs.add(adaptCurrencyPair(symbol));
    }
    return currencyPairs;
  }

  public static CurrencyPair adaptCurrencyPair(String GeminiSymbol) {

    String tradableIdentifier = GeminiSymbol.substring(0, 3).toUpperCase();
    String transactionCurrency = GeminiSymbol.substring(3).toUpperCase();
    return new CurrencyPair(tradableIdentifier, transactionCurrency);
  }

  private static OrderStatus adaptOrderstatus(
      CoindcxOrderStatusResponse geminiOrderStatusResponse) {

    if (geminiOrderStatusResponse.getStatus().equals("cancel")) return OrderStatus.CANCELED;

    if (geminiOrderStatusResponse.getRemaining_quantity().equals(new BigDecimal(0.0)))
      return OrderStatus.FILLED;

    if (geminiOrderStatusResponse.getRemaining_quantity().compareTo(new BigDecimal(0.0)) > 0)
      return OrderStatus.PARTIALLY_FILLED;

    throw new NotYetImplementedForExchangeException();
  }

  public static UserTrades adaptUserTrades(List<CoindcxTradeHistoryResponse> coindcxTradeHistoryResponses){

    List<UserTrade> tradeList=new ArrayList();

    for(CoindcxTradeHistoryResponse trades : coindcxTradeHistoryResponses){
      CurrencyPair currencyPair = new CurrencyPair(trades.getSymbol());
      OrderType type = trades.getSide().equals("buy") ? OrderType.BID : OrderType.ASK;
      UserTrade userTrade=UserTrade.builder()
                              .id(String.valueOf(trades.getId()))
                              .orderId(trades.getOrderId())
                              .feeAmount(new BigDecimal(trades.getFeeAmount()))
                              .instrument(currencyPair)
                              .type(type)
                              .originalAmount(trades.getQuantity())
                              .price(trades.getPrice())
                              .timestamp(new Date(trades.getTimestamp()))
                              .build();
      tradeList.add(userTrade);
    }

    return new UserTrades(tradeList, Trades.TradeSortType.SortByID);
  }

  public static Ticker adaptTickerBasedOnCurencyPair(List<CoindcxTickersResponse> coindcxTickersResponses,CurrencyPair currencyPair){
    String currency= currencyPair.base.getCurrencyCode()+currencyPair.counter.getCurrencyCode();
    Ticker ticker=null;
    for(CoindcxTickersResponse tickersResponse : coindcxTickersResponses){
      if(tickersResponse.getMarket().equals(currency)){
        ticker = new Ticker.Builder()
                .instrument(currencyPair)
                .high(new BigDecimal(tickersResponse.getHigh()))
                .low(new BigDecimal(tickersResponse.getLow()))
                .ask(new BigDecimal(tickersResponse.getAsk()))
                .bid(new BigDecimal(tickersResponse.getBid()))
                .last(new BigDecimal(tickersResponse.getLast_price()))
                .volume(new BigDecimal(tickersResponse.getVolume()))
                .timestamp(new Date(tickersResponse.getTimestamp()))
                .vwap(new BigDecimal(tickersResponse.getChange_24_hour()))
                .build();
      }
    }
    return ticker;
  }
}
