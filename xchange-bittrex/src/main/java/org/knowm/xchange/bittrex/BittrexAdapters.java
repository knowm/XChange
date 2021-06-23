package org.knowm.xchange.bittrex;

import static org.knowm.xchange.bittrex.BittrexConstants.OFFLINE;
import static org.knowm.xchange.bittrex.BittrexConstants.ONLINE;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.knowm.xchange.bittrex.dto.account.BittrexBalance;
import org.knowm.xchange.bittrex.dto.marketdata.*;
import org.knowm.xchange.bittrex.dto.trade.BittrexOrder;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderStatus;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Fee;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.marketdata.Trades.TradeSortType;
import org.knowm.xchange.dto.meta.CurrencyMetaData;
import org.knowm.xchange.dto.meta.CurrencyPairMetaData;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.dto.meta.WalletHealth;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.UserTrade;

public final class BittrexAdapters {

  public static List<CurrencyPair> adaptCurrencyPairs(Collection<BittrexSymbol> bittrexSymbols) {
    return bittrexSymbols.stream()
        .map(BittrexAdapters::adaptCurrencyPair)
        .collect(Collectors.toList());
  }

  public static CurrencyPair adaptCurrencyPair(BittrexSymbol bittrexSymbol) {
    Currency baseSymbol = bittrexSymbol.getBaseCurrencySymbol();
    Currency counterSymbol = bittrexSymbol.getQuoteCurrencySymbol();
    return new CurrencyPair(baseSymbol, counterSymbol);
  }

  public static List<LimitOrder> adaptOpenOrders(List<BittrexOrder> bittrexOpenOrders) {
    return bittrexOpenOrders == null
        ? null
        : bittrexOpenOrders.stream().map(BittrexAdapters::adaptOrder).collect(Collectors.toList());
  }

  public static List<LimitOrder> adaptOrders(
      BittrexLevel[] orders, CurrencyPair currencyPair, OrderType orderType, int depth) {
    if (orders == null || orders.length == 0) {
      return new ArrayList<>();
    }
    return Arrays.stream(orders)
        .limit(Math.min(orders.length, depth))
        .map(
            order ->
                new LimitOrder.Builder(orderType, currencyPair)
                    .originalAmount(order.getAmount())
                    .limitPrice(order.getPrice())
                    .build())
        .collect(Collectors.toList());
  }

  public static LimitOrder adaptOrder(BittrexOrder order) {
    return adaptOrder(order, adaptOrderStatus(order));
  }

  public static LimitOrder adaptOrder(BittrexOrder order, OrderStatus status) {

    OrderType type =
        order.getDirection().equalsIgnoreCase(BittrexConstants.SELL)
            ? OrderType.ASK
            : OrderType.BID;
    CurrencyPair pair = BittrexUtils.toCurrencyPair(order.getMarketSymbol());
    return new LimitOrder.Builder(type, pair)
        .originalAmount(order.getQuantity())
        .id(order.getId())
        .timestamp(order.getUpdatedAt() != null ? order.getUpdatedAt() : order.getCreatedAt())
        .limitPrice(order.getLimit())
        .remainingAmount(order.getQuantity().subtract(order.getFillQuantity()))
        .fee(order.getCommission())
        .orderStatus(status)
        .build();
  }

  public static OrderStatus adaptOrderStatus(BittrexOrder order) {
    if (order.getQuantity() == null) {
      return OrderStatus.UNKNOWN;
    }
    if (order.getFillQuantity() == null
        || order.getFillQuantity().compareTo(BigDecimal.ZERO) == 0) {
      return OrderStatus.NEW;
    }
    BigDecimal remaining = order.getQuantity().subtract(order.getFillQuantity());
    return remaining.signum() == 1 ? OrderStatus.PARTIALLY_FILLED : OrderStatus.FILLED;
  }

  public static Trade adaptTrade(BittrexTrade trade, CurrencyPair currencyPair) {
    OrderType orderType =
        BittrexConstants.BUY.equalsIgnoreCase(trade.getTakerSide()) ? OrderType.BID : OrderType.ASK;
    BigDecimal amount = trade.getQuantity();
    BigDecimal price = trade.getRate();
    Date date = trade.getExecutedAt();
    String tradeId = trade.getId();
    return new Trade.Builder()
        .type(orderType)
        .originalAmount(amount)
        .currencyPair(currencyPair)
        .price(price)
        .timestamp(date)
        .id(tradeId)
        .build();
  }

  public static Trades adaptTrades(List<BittrexTrade> trades, CurrencyPair currencyPair) {
    return new Trades(
        trades.stream()
            .map(trade -> adaptTrade(trade, currencyPair))
            .sorted(Comparator.comparing(Trade::getTimestamp))
            .collect(Collectors.toList()),
        TradeSortType.SortByTimestamp);
  }

  public static List<UserTrade> adaptUserTrades(List<BittrexOrder> bittrexUserTrades) {
    return bittrexUserTrades.stream()
        .map(
            bittrexOrder ->
                new UserTrade.Builder()
                    .type(
                        BittrexConstants.BUY.equalsIgnoreCase(bittrexOrder.getDirection())
                            ? OrderType.BID
                            : OrderType.ASK)
                    .originalAmount(bittrexOrder.getFillQuantity())
                    .currencyPair(BittrexUtils.toCurrencyPair(bittrexOrder.getMarketSymbol()))
                    .price(bittrexOrder.getLimit())
                    .timestamp(bittrexOrder.getClosedAt())
                    .id(bittrexOrder.getId())
                    .feeAmount(bittrexOrder.getCommission())
                    .feeCurrency(
                        BittrexUtils.toCurrencyPair(bittrexOrder.getMarketSymbol()).counter)
                    .build())
        .collect(Collectors.toList());
  }

  public static List<Ticker> adaptTickers(
      Set<CurrencyPair> currencyPairs,
      List<BittrexMarketSummary> bittrexMarketSummaries,
      List<BittrexTicker> bittrexTickers) {
    Map<CurrencyPair, SummaryTickerPair> tickerCombinationMap =
        new HashMap<>(Math.min(bittrexMarketSummaries.size(), bittrexTickers.size()));
    bittrexMarketSummaries.forEach(
        marketSummary ->
            tickerCombinationMap.put(
                BittrexUtils.toCurrencyPair(marketSummary.getSymbol()),
                new SummaryTickerPair(marketSummary, null)));
    bittrexTickers.forEach(
        ticker -> {
          CurrencyPair currencyPair = BittrexUtils.toCurrencyPair(ticker.getSymbol());
          if (tickerCombinationMap.containsKey(currencyPair)) {
            tickerCombinationMap.get(currencyPair).setTicker(ticker);
          }
        });

    return tickerCombinationMap.entrySet().stream()
        .filter(entry -> currencyPairs.isEmpty() || currencyPairs.contains(entry.getKey()))
        .map(Map.Entry::getValue)
        .filter(
            summaryTickerPair ->
                summaryTickerPair.getSummary() != null && summaryTickerPair.getTicker() != null)
        .map(
            summaryTickerPair ->
                BittrexAdapters.adaptTicker(
                    summaryTickerPair.getSummary(), summaryTickerPair.getTicker()))
        .collect(Collectors.toList());
  }

  public static Ticker adaptTicker(
      BittrexMarketSummary bittrexMarketSummary, BittrexTicker bittrexTicker) {

    CurrencyPair currencyPair = BittrexUtils.toCurrencyPair(bittrexTicker.getSymbol());
    BigDecimal last = bittrexTicker.getLastTradeRate();
    BigDecimal bid = bittrexTicker.getBidRate();
    BigDecimal ask = bittrexTicker.getAskRate();
    BigDecimal high = bittrexMarketSummary.getHigh();
    BigDecimal low = bittrexMarketSummary.getLow();
    BigDecimal quoteVolume = bittrexMarketSummary.getQuoteVolume();
    BigDecimal volume = bittrexMarketSummary.getVolume();
    BigDecimal percentageChange = bittrexMarketSummary.getPercentChange();
    Date timestamp = bittrexMarketSummary.getUpdatedAt();

    return new Ticker.Builder()
        .currencyPair(currencyPair)
        .last(last)
        .bid(bid)
        .ask(ask)
        .high(high)
        .low(low)
        .quoteVolume(quoteVolume)
        .volume(volume)
        .percentageChange(percentageChange)
        .timestamp(timestamp)
        .build();
  }

  public static Wallet adaptWallet(Collection<BittrexBalance> balances) {
    List<Balance> wallets =
        balances.stream()
            .map(
                bittrexBalance ->
                    new Balance.Builder()
                        .currency(bittrexBalance.getCurrencySymbol())
                        .total(bittrexBalance.getTotal())
                        .available(bittrexBalance.getAvailable())
                        .timestamp(bittrexBalance.getUpdatedAt())
                        .build())
            .collect(Collectors.toList());

    return Wallet.Builder.from(wallets).build();
  }

  public static void adaptMetaData(
      List<BittrexSymbol> rawSymbols,
      List<BittrexCurrency> bittrexCurrencies,
      Map<CurrencyPair, Fee> dynamicTradingFees,
      ExchangeMetaData metaData) {
    List<CurrencyPair> currencyPairs = BittrexAdapters.adaptCurrencyPairs(rawSymbols);
    for (CurrencyPair currencyPair : currencyPairs) {
      CurrencyPairMetaData defaultCurrencyPairMetaData =
          metaData.getCurrencyPairs().get(currencyPair);
      BigDecimal resultingFee = null;
      // Prioritize dynamic fee
      if (dynamicTradingFees != null) {
        Fee fee = dynamicTradingFees.get(currencyPair);
        if (fee != null) {
          resultingFee = fee.getMakerFee();
        }
      } else {
        if (defaultCurrencyPairMetaData != null) {
          resultingFee = defaultCurrencyPairMetaData.getTradingFee();
        }
      }

      CurrencyPairMetaData newCurrencyPairMetaData;
      if (defaultCurrencyPairMetaData != null) {
        newCurrencyPairMetaData =
            new CurrencyPairMetaData(
                resultingFee,
                defaultCurrencyPairMetaData.getMinimumAmount(),
                defaultCurrencyPairMetaData.getMaximumAmount(),
                defaultCurrencyPairMetaData.getPriceScale(),
                defaultCurrencyPairMetaData.getVolumeScale(),
                defaultCurrencyPairMetaData.getFeeTiers(),
                defaultCurrencyPairMetaData.getTradingFeeCurrency());
      } else {
        newCurrencyPairMetaData =
            new CurrencyPairMetaData(resultingFee, null, null, null, null, null, null);
      }

      metaData.getCurrencyPairs().put(currencyPair, newCurrencyPairMetaData);
    }

    for (BittrexCurrency bittrexCurrency : bittrexCurrencies) {
      WalletHealth walletHealth = WalletHealth.UNKNOWN;
      if (ONLINE.equals(bittrexCurrency.getStatus())) {
        walletHealth = WalletHealth.ONLINE;
      } else if (OFFLINE.equals(bittrexCurrency.getStatus())) {
        walletHealth = WalletHealth.OFFLINE;
      }
      metaData
          .getCurrencies()
          .put(
              bittrexCurrency.getSymbol(),
              new CurrencyMetaData(
                  1, BigDecimal.valueOf(bittrexCurrency.getTxFee()), null, walletHealth));
    }
  }

  private BittrexAdapters() {
    throw new AssertionError();
  }

  @Data
  @AllArgsConstructor
  private static class SummaryTickerPair {
    private BittrexMarketSummary summary;
    private BittrexTicker ticker;
  }
}
