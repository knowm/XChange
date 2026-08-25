package org.knowm.xchange.cryptocom;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.knowm.xchange.cryptocom.dto.account.CryptoComBalance;
import org.knowm.xchange.cryptocom.dto.account.CryptoComDepositRecord;
import org.knowm.xchange.cryptocom.dto.account.CryptoComWithdrawalRecord;
import org.knowm.xchange.cryptocom.dto.marketdata.CryptoComInstrument;
import org.knowm.xchange.cryptocom.dto.marketdata.CryptoComOrderBookData;
import org.knowm.xchange.cryptocom.dto.marketdata.CryptoComPublicTrade;
import org.knowm.xchange.cryptocom.dto.marketdata.CryptoComTicker;
import org.knowm.xchange.cryptocom.dto.trade.CryptoComOrder;
import org.knowm.xchange.cryptocom.dto.trade.CryptoComUserTrade;
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
import org.knowm.xchange.dto.meta.CurrencyMetaData;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.dto.meta.InstrumentMetaData;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.instrument.Instrument;

public final class CryptoComAdapters {

  private CryptoComAdapters() {}

  public static Long toEpochMillis(Date date) {
    return date == null ? null : date.getTime();
  }

  public static String toInstrumentName(Instrument instrument) {
    if (instrument == null) {
      return null;
    }
    return instrument.getBase().getCurrencyCode() + "_" + instrument.getCounter().getCurrencyCode();
  }

  public static CurrencyPair toCurrencyPair(String instrumentName) {
    if (instrumentName == null) {
      return null;
    }
    String[] parts = instrumentName.split("_");
    if (parts.length != 2) {
      return null;
    }
    return new CurrencyPair(parts[0], parts[1]);
  }

  /**
   * Crypto.com only supports spot pairs; derivative {@link Instrument}s (e.g. perpetual swaps)
   * aren't implemented. Casts safely, throwing the standard "not implemented" exception instead of
   * an opaque {@link ClassCastException}.
   */
  public static CurrencyPair requireCurrencyPair(Instrument instrument, String operationName) {
    if (!(instrument instanceof CurrencyPair)) {
      throw new NotYetImplementedForExchangeException(operationName);
    }
    return (CurrencyPair) instrument;
  }

  public static ExchangeMetaData adaptExchangeMetaData(List<CryptoComInstrument> instruments) {
    Map<Instrument, InstrumentMetaData> currencyPairs = new HashMap<>();
    Map<Currency, CurrencyMetaData> currencies = new HashMap<>();

    if (instruments != null) {
      for (CryptoComInstrument instrument : instruments) {
        if (!"CCY_PAIR".equals(instrument.getInstType()) || instrument.getBaseCurrency() == null) {
          continue;
        }
        CurrencyPair pair =
            new CurrencyPair(instrument.getBaseCurrency(), instrument.getQuoteCurrency());

        InstrumentMetaData instrumentMetaData =
            InstrumentMetaData.builder()
                .priceScale(instrument.getQuoteDecimals())
                .volumeScale(instrument.getQuantityDecimals())
                .amountStepSize(toBigDecimal(instrument.getQtyTickSize()))
                .priceStepSize(toBigDecimal(instrument.getPriceTickSize()))
                .marketOrderEnabled(Boolean.TRUE.equals(instrument.getTradable()))
                .build();
        currencyPairs.put(pair, instrumentMetaData);

        currencies.putIfAbsent(
            pair.getBase(), new CurrencyMetaData(instrument.getQuantityDecimals(), null));
        currencies.putIfAbsent(
            pair.getCounter(), new CurrencyMetaData(instrument.getQuoteDecimals(), null));
      }
    }

    return new ExchangeMetaData(currencyPairs, currencies, null, null, true);
  }

  public static BigDecimal toBigDecimal(String value) {
    return value == null ? null : new BigDecimal(value);
  }

  public static Ticker adaptTicker(CryptoComTicker ticker) {
    if (ticker == null) {
      return null;
    }
    CurrencyPair pair = toCurrencyPair(ticker.getInstrumentName());
    return new Ticker.Builder()
        .instrument(pair)
        .last(toBigDecimal(ticker.getLatestTradePrice()))
        .bid(toBigDecimal(ticker.getBestBidPrice()))
        .bidSize(toBigDecimal(ticker.getBestBidSize()))
        .ask(toBigDecimal(ticker.getBestAskPrice()))
        .askSize(toBigDecimal(ticker.getBestAskSize()))
        .high(toBigDecimal(ticker.getHigh24h()))
        .low(toBigDecimal(ticker.getLow24h()))
        .volume(toBigDecimal(ticker.getVolume24h()))
        .quoteVolume(toBigDecimal(ticker.getVolume24hUsd()))
        .percentageChange(toBigDecimal(ticker.getPriceChange24h()))
        .timestamp(ticker.getTimestamp() == null ? null : new Date(ticker.getTimestamp()))
        .build();
  }

  /**
   * Adapts every spot-pair ticker in the list. Perpetual/derivative instrument names (e.g. {@code
   * 1INCHUSD-PERP}) don't parse into a {@link CurrencyPair} via {@link #toCurrencyPair}; since
   * {@link Ticker.Builder} rejects a null instrument, those entries are skipped up front instead of
   * failing the whole batch (e.g. all of {@code public/get-tickers}, which returns spot and
   * perpetual instruments together).
   */
  public static List<Ticker> adaptTickers(List<CryptoComTicker> tickers) {
    if (tickers == null) {
      return Collections.emptyList();
    }
    return tickers.stream()
        .filter(
            ticker -> ticker != null && toCurrencyPair(ticker.getInstrumentName()) != null)
        .map(CryptoComAdapters::adaptTicker)
        .collect(Collectors.toList());
  }

  public static OrderBook adaptOrderBook(
      CryptoComOrderBookData orderBookData, CurrencyPair currencyPair) {
    if (orderBookData == null) {
      return null;
    }
    List<LimitOrder> asks =
        adaptOrderBookLevels(orderBookData.getAsks(), OrderType.ASK, currencyPair);
    List<LimitOrder> bids =
        adaptOrderBookLevels(orderBookData.getBids(), OrderType.BID, currencyPair);
    Date timestamp =
        orderBookData.getTimestamp() == null ? null : new Date(orderBookData.getTimestamp());
    return new OrderBook(timestamp, asks, bids);
  }

  private static List<LimitOrder> adaptOrderBookLevels(
      List<List<String>> levels, OrderType orderType, CurrencyPair currencyPair) {
    if (levels == null) {
      return Collections.emptyList();
    }
    List<LimitOrder> orders = new ArrayList<>(levels.size());
    for (List<String> level : levels) {
      BigDecimal price = new BigDecimal(level.get(0));
      BigDecimal quantity = new BigDecimal(level.get(1));
      orders.add(new LimitOrder(orderType, quantity, currencyPair, null, null, price));
    }
    return orders;
  }

  public static Trade adaptTrade(CryptoComPublicTrade trade, CurrencyPair currencyPair) {
    return Trade.builder()
        .instrument(currencyPair)
        .price(toBigDecimal(trade.getPrice()))
        .originalAmount(toBigDecimal(trade.getQuantity()))
        .timestamp(trade.getTimestamp() == null ? null : new Date(trade.getTimestamp()))
        .id(trade.getTradeId())
        .type("SELL".equalsIgnoreCase(trade.getSide()) ? OrderType.ASK : OrderType.BID)
        .build();
  }

  public static Trades adaptTrades(List<CryptoComPublicTrade> trades, CurrencyPair currencyPair) {
    if (trades == null) {
      return new Trades(Collections.emptyList(), Trades.TradeSortType.SortByTimestamp);
    }
    List<Trade> adapted =
        trades.stream().map(trade -> adaptTrade(trade, currencyPair)).collect(Collectors.toList());
    return new Trades(adapted, Trades.TradeSortType.SortByTimestamp);
  }

  public static OrderType adaptOrderType(String side) {
    return "SELL".equalsIgnoreCase(side) ? OrderType.ASK : OrderType.BID;
  }

  public static OrderStatus adaptOrderStatus(CryptoComOrder order) {
    return adaptOrderStatus(order.getStatus(), toBigDecimal(order.getCumulativeQuantity()));
  }

  /**
   * Split out from {@link #adaptOrderStatus(CryptoComOrder)} so status can be computed once the raw
   * fields (not necessarily wrapped in a {@link CryptoComOrder}) are known.
   */
  public static OrderStatus adaptOrderStatus(String status, BigDecimal cumulativeQuantity) {
    if (status == null) {
      return OrderStatus.UNKNOWN;
    }
    switch (status.toUpperCase()) {
      case "ACTIVE":
        if (cumulativeQuantity != null && cumulativeQuantity.compareTo(BigDecimal.ZERO) > 0) {
          return OrderStatus.PARTIALLY_FILLED;
        }
        return OrderStatus.NEW;
      case "FILLED":
        return OrderStatus.FILLED;
      case "CANCELED":
        return OrderStatus.CANCELED;
      case "REJECTED":
        return OrderStatus.REJECTED;
      case "EXPIRED":
        return OrderStatus.EXPIRED;
      default:
        return OrderStatus.UNKNOWN;
    }
  }

  public static LimitOrder adaptOrder(CryptoComOrder order) {
    if (order == null) {
      return null;
    }
    CurrencyPair pair = toCurrencyPair(order.getInstrumentName());
    Date timestamp = order.getCreateTime() == null ? null : new Date(order.getCreateTime());
    return new LimitOrder.Builder(adaptOrderType(order.getSide()), pair)
        .id(order.getOrderId())
        .userReference(order.getClientOid())
        .originalAmount(toBigDecimal(order.getQuantity()))
        .cumulativeAmount(toBigDecimal(order.getCumulativeQuantity()))
        .averagePrice(toBigDecimal(order.getAvgPrice()))
        .limitPrice(toBigDecimal(order.getLimitPrice()))
        .timestamp(timestamp)
        .orderStatus(adaptOrderStatus(order))
        .build();
  }

  public static OpenOrders adaptOpenOrders(List<CryptoComOrder> orders) {
    List<LimitOrder> limitOrders =
        orders == null
            ? Collections.emptyList()
            : orders.stream().map(CryptoComAdapters::adaptOrder).collect(Collectors.toList());
    return new OpenOrders(limitOrders);
  }

  public static UserTrade adaptUserTrade(CryptoComUserTrade trade) {
    if (trade == null) {
      return null;
    }
    CurrencyPair pair = toCurrencyPair(trade.getInstrumentName());
    return UserTrade.builder()
        .instrument(pair)
        .id(trade.getTradeId())
        .orderId(trade.getOrderId())
        .type(adaptOrderType(trade.getSide()))
        .price(toBigDecimal(trade.getTradedPrice()))
        .originalAmount(toBigDecimal(trade.getTradedQuantity()))
        .feeAmount(toBigDecimal(trade.getFees()))
        .feeCurrency(
            trade.getFeeInstrumentName() == null
                ? null
                : new Currency(trade.getFeeInstrumentName()))
        .timestamp(trade.getCreateTime() == null ? null : new Date(trade.getCreateTime()))
        .build();
  }

  public static UserTrades adaptUserTrades(List<CryptoComUserTrade> trades) {
    if (trades == null) {
      return new UserTrades(Collections.emptyList(), Trades.TradeSortType.SortByTimestamp);
    }
    List<UserTrade> adapted =
        trades.stream().map(CryptoComAdapters::adaptUserTrade).collect(Collectors.toList());
    return new UserTrades(adapted, Trades.TradeSortType.SortByTimestamp);
  }

  public static AccountInfo adaptAccountInfo(List<CryptoComBalance> balances) {
    List<Balance> wallets = new ArrayList<>();
    if (balances != null) {
      for (CryptoComBalance balance : balances) {
        if (balance.getPositionBalances() == null) {
          continue;
        }
        for (CryptoComBalance.PositionBalance positionBalance : balance.getPositionBalances()) {
          BigDecimal quantity = toBigDecimal(positionBalance.getQuantity());
          BigDecimal reserved = toBigDecimal(positionBalance.getReservedQty());
          if (quantity == null) {
            continue;
          }
          BigDecimal available = reserved == null ? quantity : quantity.subtract(reserved);
          wallets.add(
              new Balance(
                  new Currency(positionBalance.getInstrumentName()),
                  quantity,
                  available,
                  reserved));
        }
      }
    }
    return new AccountInfo(Wallet.Builder.from(wallets).build());
  }

  public static FundingRecord adaptDepositRecord(CryptoComDepositRecord record) {
    return FundingRecord.builder()
        .type(FundingRecord.Type.DEPOSIT)
        .currency(new Currency(record.getCurrency()))
        .amount(toBigDecimal(record.getAmount()))
        .fee(toBigDecimal(record.getFee()))
        .address(record.getAddress())
        .internalId(record.getId())
        .date(record.getCreateTime() == null ? null : new Date(record.getCreateTime()))
        .status(adaptFundingStatus(record.getStatus()))
        .description("Deposit via " + record.getNetwork())
        .build();
  }

  public static FundingRecord adaptWithdrawalRecord(CryptoComWithdrawalRecord record) {
    return FundingRecord.builder()
        .type(FundingRecord.Type.WITHDRAWAL)
        .currency(new Currency(record.getCurrency()))
        .amount(toBigDecimal(record.getAmount()))
        .fee(toBigDecimal(record.getFee()))
        .address(record.getAddress())
        .internalId(record.getId())
        .date(record.getCreateTime() == null ? null : new Date(record.getCreateTime()))
        .status(adaptFundingStatus(record.getStatus()))
        .blockchainTransactionHash(record.getTxid())
        .description("Withdrawal via " + record.getNetwork())
        .build();
  }

  private static FundingRecord.Status adaptFundingStatus(String status) {
    if (status == null) {
      return null;
    }
    switch (status.toUpperCase()) {
      case "1":
      case "COMPLETED":
        return FundingRecord.Status.COMPLETE;
      case "0":
      case "PENDING":
      case "PROCESSING":
        return FundingRecord.Status.PROCESSING;
      case "2":
      case "FAILED":
      case "REJECTED":
        return FundingRecord.Status.FAILED;
      case "CANCELLED":
        return FundingRecord.Status.CANCELLED;
      default:
        return FundingRecord.Status.PROCESSING;
    }
  }

  public static List<FundingRecord> adaptDepositRecords(List<CryptoComDepositRecord> records) {
    if (records == null) {
      return Collections.emptyList();
    }
    return records.stream().map(CryptoComAdapters::adaptDepositRecord).collect(Collectors.toList());
  }

  public static List<FundingRecord> adaptWithdrawalRecords(
      List<CryptoComWithdrawalRecord> records) {
    if (records == null) {
      return Collections.emptyList();
    }
    return records.stream()
        .map(CryptoComAdapters::adaptWithdrawalRecord)
        .collect(Collectors.toList());
  }
}
