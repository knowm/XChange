package org.knowm.xchange.kucoin;

import static java.util.stream.Collectors.toCollection;
import static org.knowm.xchange.dto.Order.OrderStatus.CANCELED;
import static org.knowm.xchange.dto.Order.OrderStatus.NEW;
import static org.knowm.xchange.dto.Order.OrderStatus.PARTIALLY_FILLED;
import static org.knowm.xchange.dto.Order.OrderStatus.UNKNOWN;
import static org.knowm.xchange.dto.Order.OrderType.ASK;
import static org.knowm.xchange.dto.Order.OrderType.BID;
import static org.knowm.xchange.kucoin.dto.KucoinOrderFlags.HIDDEN;
import static org.knowm.xchange.kucoin.dto.KucoinOrderFlags.ICEBERG;
import static org.knowm.xchange.kucoin.dto.KucoinOrderFlags.POST_ONLY;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Ordering;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.IOrderFlags;
import org.knowm.xchange.dto.Order.OrderStatus;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.account.FundingRecord.Status;
import org.knowm.xchange.dto.account.FundingRecord.Type;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.marketdata.Trades.TradeSortType;
import org.knowm.xchange.dto.meta.CurrencyMetaData;
import org.knowm.xchange.dto.meta.InstrumentMetaData;
import org.knowm.xchange.dto.meta.WalletHealth;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.StopOrder;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.kucoin.KucoinTradeService.KucoinOrderFlags;
import org.knowm.xchange.kucoin.dto.request.OrderCreateApiRequest;
import org.knowm.xchange.kucoin.dto.response.AccountBalancesResponse;
import org.knowm.xchange.kucoin.dto.response.AllTickersResponse;
import org.knowm.xchange.kucoin.dto.response.DepositResponse;
import org.knowm.xchange.kucoin.dto.response.HistOrdersResponse;
import org.knowm.xchange.kucoin.dto.response.KucoinCurrencyResponseV3;
import org.knowm.xchange.kucoin.dto.response.OrderBookResponse;
import org.knowm.xchange.kucoin.dto.response.OrderResponse;
import org.knowm.xchange.kucoin.dto.response.SymbolResponse;
import org.knowm.xchange.kucoin.dto.response.SymbolTickResponse;
import org.knowm.xchange.kucoin.dto.response.TradeHistoryResponse;
import org.knowm.xchange.kucoin.dto.response.TradeResponse;
import org.knowm.xchange.kucoin.dto.response.WithdrawalResponse;

public class KucoinAdapters {

  public static String adaptCurrencyPair(Instrument instrument) {
    return instrument == null
        ? null
        : instrument.getBase().getCurrencyCode() + "-" + instrument.getCounter().getCurrencyCode();
  }

  public static CurrencyPair adaptCurrencyPair(String symbol) {
    String[] split = symbol.split("-");
    if (split.length != 2) {
      throw new ExchangeException("Invalid kucoin symbol: " + symbol);
    }
    return new CurrencyPair(split[0], split[1]);
  }

  public static Ticker.Builder adaptTickerFull(CurrencyPair pair, SymbolTickResponse stats) {
    return new Ticker.Builder()
        .instrument(pair)
        .bid(stats.getBuy())
        .ask(stats.getSell())
        .last(stats.getLast())
        .high(stats.getHigh())
        .low(stats.getLow())
        .volume(stats.getVol())
        .quoteVolume(stats.getVolValue())
        .open(stats.getOpen())
        .timestamp(new Date(stats.getTime()));
  }

  public static List<Ticker> adaptAllTickers(AllTickersResponse allTickersResponse) {
    return Arrays.stream(allTickersResponse.getTicker())
        .map(
            ticker ->
                new Ticker.Builder()
                    .instrument(adaptCurrencyPair(ticker.getSymbol()))
                    .bid(ticker.getBuy())
                    .bidSize(ticker.getBestBidSize())
                    .ask(ticker.getSell())
                    .askSize(ticker.getBestAskSize())
                    .last(ticker.getLast())
                    .high(ticker.getHigh())
                    .low(ticker.getLow())
                    .volume(ticker.getVol())
                    .quoteVolume(ticker.getVolValue())
                    .percentageChange(
                        ticker.getChangeRate().multiply(new BigDecimal("100"), new MathContext(8)))
                    .build())
        .collect(Collectors.toList());
  }

  public static InstrumentMetaData toInstrumentMetaData(SymbolResponse symbolResponse) {
    return InstrumentMetaData.builder()
        .minimumAmount(symbolResponse.getBaseMinSize())
        .maximumAmount(symbolResponse.getBaseMaxSize())
        .counterMinimumAmount(symbolResponse.getQuoteMinSize())
        .counterMaximumAmount(symbolResponse.getQuoteMaxSize())
        .priceScale(symbolResponse.getPriceIncrement().scale())
        .priceStepSize(symbolResponse.getPriceIncrement())
        .volumeScale(symbolResponse.getBaseIncrement().scale())
        .amountStepSize(symbolResponse.getBaseIncrement())
        .tradingFeeCurrency(symbolResponse.getFeeCurrency())
        .marketOrderEnabled(symbolResponse.isEnableTrading())
        .build();
  }

  public static CurrencyMetaData toCurrencyMetaData(KucoinCurrencyResponseV3 currencyResponseV3) {
    return CurrencyMetaData.builder()
        .scale(currencyResponseV3.getPrecision())
        .walletHealth(toWalletHealth(currencyResponseV3))
        .build();
  }

  public static WalletHealth toWalletHealth(KucoinCurrencyResponseV3 currencyResponseV3) {
    if (currencyResponseV3.isDepositEnabled() && currencyResponseV3.isWithdrawEnabled()) {
      return WalletHealth.ONLINE;
    }
    if (!currencyResponseV3.isDepositEnabled() && currencyResponseV3.isWithdrawEnabled()) {
      return WalletHealth.DEPOSITS_DISABLED;
    }
    if (currencyResponseV3.isDepositEnabled() && !currencyResponseV3.isWithdrawEnabled()) {
      return WalletHealth.WITHDRAWALS_DISABLED;
    }
    if (!currencyResponseV3.isDepositEnabled() && !currencyResponseV3.isWithdrawEnabled()) {
      return WalletHealth.OFFLINE;
    }
    return WalletHealth.UNKNOWN;
  }

  public static OrderBook adaptOrderBook(Instrument instrument, OrderBookResponse kc) {
    Date timestamp = new Date(kc.getTime());
    List<LimitOrder> asks =
        kc.getAsks().stream()
            .map(PriceAndSize::new)
            .sorted(Ordering.natural().onResultOf(s -> s.price))
            .map(s -> adaptLimitOrder(instrument, ASK, s, timestamp))
            .collect(toCollection(LinkedList::new));
    List<LimitOrder> bids =
        kc.getBids().stream()
            .map(PriceAndSize::new)
            .sorted(Ordering.natural().onResultOf((PriceAndSize s) -> s.price).reversed())
            .map(s -> adaptLimitOrder(instrument, BID, s, timestamp))
            .collect(toCollection(LinkedList::new));
    return new OrderBook(timestamp, asks, bids, true);
  }

  private static LimitOrder adaptLimitOrder(
      Instrument instrument, OrderType orderType, PriceAndSize priceAndSize, Date timestamp) {
    return new LimitOrder.Builder(orderType, instrument)
        .limitPrice(priceAndSize.price)
        .originalAmount(priceAndSize.size)
        .orderStatus(NEW)
        .build();
  }

  public static Trades adaptTrades(
      CurrencyPair currencyPair, List<TradeHistoryResponse> kucoinTrades) {
    return new Trades(
        kucoinTrades.stream().map(o -> adaptTrade(currencyPair, o)).collect(Collectors.toList()),
        TradeSortType.SortByTimestamp);
  }

  public static Balance adaptBalance(AccountBalancesResponse a) {
    return new Balance(
        Currency.getInstance(a.getCurrency()), a.getBalance(), a.getAvailable(), a.getHolds());
  }

  private static Trade adaptTrade(CurrencyPair currencyPair, TradeHistoryResponse trade) {
    return Trade.builder()
        .instrument(currencyPair)
        .originalAmount(trade.getSize())
        .price(trade.getPrice())
        .timestamp(new Date(Long.parseLong(trade.getSequence())))
        .type(adaptSide(trade.getSide()))
        .build();
  }

  private static OrderType adaptSide(String side) {
    return "sell".equals(side) ? ASK : BID;
  }

  private static String adaptSide(OrderType type) {
    return type.equals(ASK) ? "sell" : "buy";
  }

  public static Order adaptOrder(OrderResponse order) {

    OrderType orderType = adaptSide(order.getSide());
    CurrencyPair currencyPair = adaptCurrencyPair(order.getSymbol());

    OrderStatus status;
    if (order.isCancelExist()) {
      status = CANCELED;
    } else if (order.isActive()) {
      if (order.getDealSize().signum() == 0) {
        status = NEW;
      } else {
        status = PARTIALLY_FILLED;
      }
    } else {
      status = UNKNOWN;
    }

    Order.Builder builder;
    if (StringUtils.isNotEmpty(order.getStop())) {
      BigDecimal limitPrice = order.getPrice();
      if (limitPrice != null && limitPrice.compareTo(BigDecimal.ZERO) == 0) {
        limitPrice = null;
      }
      builder =
          new StopOrder.Builder(orderType, currencyPair)
              .limitPrice(limitPrice)
              .stopPrice(order.getStopPrice());
    } else {
      builder = new LimitOrder.Builder(orderType, currencyPair).limitPrice(order.getPrice());
    }
    builder =
        builder
            .averagePrice(
                order.getDealSize().compareTo(BigDecimal.ZERO) == 0
                    ? MoreObjects.firstNonNull(order.getPrice(), order.getStopPrice())
                    : order.getDealFunds().divide(order.getDealSize(), RoundingMode.HALF_UP))
            .cumulativeAmount(order.getDealSize())
            .fee(order.getFee())
            .id(order.getId())
            .orderStatus(status)
            .originalAmount(order.getSize())
            .remainingAmount(order.getSize().subtract(order.getDealSize()))
            .timestamp(order.getCreatedAt());

    if (StringUtils.isNotEmpty(order.getTimeInForce())) {
      builder.flag(TimeInForce.getTimeInForce(order.getTimeInForce()));
    }

    return builder instanceof StopOrder.Builder
        ? ((StopOrder.Builder) builder).build()
        : ((LimitOrder.Builder) builder).build();
  }

  public static UserTrade adaptUserTrade(TradeResponse trade) {
    return UserTrade.builder()
        .instrument(adaptCurrencyPair(trade.getSymbol()))
        .feeAmount(trade.getFee())
        .feeCurrency(Currency.getInstance(trade.getFeeCurrency()))
        .id(trade.getTradeId())
        .orderId(trade.getOrderId())
        .originalAmount(trade.getSize())
        .price(trade.getPrice())
        .timestamp(trade.getTradeCreatedAt())
        .type(adaptSide(trade.getSide()))
        .build();
  }

  public static UserTrade adaptHistOrder(HistOrdersResponse histOrder) {
    CurrencyPair currencyPair = adaptCurrencyPair(histOrder.getSymbol());
    return UserTrade.builder()
        .instrument(currencyPair)
        .feeAmount(histOrder.getFee())
        .feeCurrency(currencyPair.getBase())
        .id(histOrder.getId())
        .originalAmount(histOrder.getAmount())
        .price(histOrder.getPrice())
        .timestamp(histOrder.getTradeCreatedAt())
        .type(adaptSide(histOrder.getSide()))
        .build();
  }

  public static OrderCreateApiRequest adaptLimitOrder(LimitOrder limitOrder) {
    return ((OrderCreateApiRequest.OrderCreateApiRequestBuilder) adaptOrder(limitOrder))
        .type("limit")
        .price(limitOrder.getLimitPrice().toPlainString())
        .postOnly(limitOrder.hasFlag(POST_ONLY))
        .hidden(limitOrder.hasFlag(HIDDEN))
        .iceberg(limitOrder.hasFlag(ICEBERG))
        .build();
  }

  public static OrderCreateApiRequest adaptStopOrder(StopOrder stopOrder) {
    return ((OrderCreateApiRequest.OrderCreateApiRequestBuilder) adaptOrder(stopOrder))
        .type(stopOrder.getLimitPrice() == null ? "market" : "limit")
        .price(stopOrder.getLimitPrice().toPlainString())
        .stop(stopOrder.getType().equals(ASK) ? "loss" : "entry")
        .stopPrice(stopOrder.getStopPrice().toPlainString())
        .build();
  }

  public static OrderCreateApiRequest adaptMarketOrder(MarketOrder marketOrder) {
    OrderCreateApiRequest.OrderCreateApiRequestBuilder builder =
        ((OrderCreateApiRequest.OrderCreateApiRequestBuilder) adaptOrder(marketOrder))
            .type("market");

    // on buy order amount corresponds to counter currency
    if (marketOrder.getType() == BID) {
      builder.size(null);
      builder.funds(marketOrder.getOriginalAmount().toPlainString());
    }
    // on sell order amount corresponds to base currency
    else if (marketOrder.getType() == ASK) {
      builder.size(marketOrder.getOriginalAmount().toPlainString());
      builder.funds(null);
    }

    return builder.build();
  }

  /**
   * Returns {@code Object} instead of the Lombok builder in order to avoid a Lombok limitation with
   * Javadoc.
   */
  private static Object adaptOrder(Order order) {
    OrderCreateApiRequest.OrderCreateApiRequestBuilder request = OrderCreateApiRequest.builder();
    boolean hasClientId = false;
    for (IOrderFlags flag : order.getOrderFlags()) {
      if (flag instanceof KucoinOrderFlags) {
        request.clientOid(((KucoinOrderFlags) flag).getClientId());
        hasClientId = true;
      } else if (flag instanceof TimeInForce) {
        request.timeInForce(((TimeInForce) flag).name());
      }
    }

    if (order.getUserReference() != null) {
      request.clientOid(order.getUserReference());
      hasClientId = true;
    }

    if (!hasClientId) {
      request.clientOid(UUID.randomUUID().toString());
    }
    return request
        .symbol(adaptCurrencyPair((CurrencyPair) order.getInstrument()))
        .size(order.getOriginalAmount().toPlainString())
        .side(adaptSide(order.getType()));
  }

  private static final class PriceAndSize {

    final BigDecimal price;
    final BigDecimal size;

    PriceAndSize(List<String> data) {
      this.price = new BigDecimal(data.get(0));
      this.size = new BigDecimal(data.get(1));
    }
  }

  public static FundingRecord adaptFundingRecord(WithdrawalResponse wr) {
    return FundingRecord.builder()
        .address(wr.getAddress())
        .amount(wr.getAmount())
        .currency(Currency.getInstance(wr.getCurrency()))
        .fee(wr.getFee())
        .type(Type.WITHDRAWAL)
        .status(convertStatus(wr.getStatus()))
        .internalId(wr.getId())
        .blockchainTransactionHash(wr.getWalletTxId())
        .description(wr.getMemo())
        .date(wr.getCreatedAt())
        .build();
  }

  private static Status convertStatus(String status) {
    if (status == null) {
      return null;
    }
    switch (status) {
      case "WALLET_PROCESSING":
      case "PROCESSING":
        return Status.PROCESSING;
      case "SUCCESS":
        return Status.COMPLETE;
      case "FAILURE":
        return Status.FAILED;
      default:
        throw new ExchangeException("Not supported status: " + status);
    }
  }

  public static FundingRecord adaptFundingRecord(DepositResponse dr) {
    return FundingRecord.builder()
        .address(dr.getAddress())
        .amount(dr.getAmount())
        .currency(Currency.getInstance(dr.getCurrency()))
        .fee(dr.getFee())
        .type(Type.DEPOSIT)
        .status(convertStatus(dr.getStatus()))
        .blockchainTransactionHash(dr.getWalletTxId())
        .description(dr.getMemo())
        .date(dr.getCreatedAt())
        .build();
  }
}
