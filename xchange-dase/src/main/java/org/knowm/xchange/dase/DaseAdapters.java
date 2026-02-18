package org.knowm.xchange.dase;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dase.dto.account.ApiAccountTxn;
import org.knowm.xchange.dase.dto.account.DaseBalanceItem;
import org.knowm.xchange.dase.dto.account.DaseBalancesResponse;
import org.knowm.xchange.dase.dto.marketdata.DaseOrderBookSnapshot;
import org.knowm.xchange.dase.dto.marketdata.DaseTicker;
import org.knowm.xchange.dase.dto.marketdata.DaseTrade;
import org.knowm.xchange.dase.dto.trade.DaseOrder;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.exceptions.ExchangeException;

public final class DaseAdapters {

  private DaseAdapters() {}

  public static String toMarketString(CurrencyPair pair) {
    return pair.getBase().getCurrencyCode() + "-" + pair.getCounter().getCurrencyCode();
  }

  public static AccountInfo adaptAccountInfo(
      String portfolioId, DaseBalancesResponse balancesResponse) {
    List<Balance> balances = new ArrayList<>();
    if (balancesResponse != null && balancesResponse.getBalances() != null) {
      for (DaseBalanceItem b : balancesResponse.getBalances()) {
        Currency currency = Currency.getInstance(b.getCurrency());
        Balance xchgBalance = new Balance(currency, b.getTotal(), b.getAvailable(), b.getBlocked());
        balances.add(xchgBalance);
      }
    }
    Wallet wallet = Wallet.Builder.from(balances).build();
    return new AccountInfo(portfolioId, null, List.of(wallet));
  }

  public static CurrencyPair toCurrencyPair(String market) {
    if (market == null) return null;
    String[] parts = market.trim().split("-");
    if (parts.length != 2) return null;
    return new CurrencyPair(parts[0].toUpperCase(), parts[1].toUpperCase());
  }

  public static Ticker adaptTicker(DaseTicker t, CurrencyPair pair) {
    return new Ticker.Builder()
        .instrument(pair)
        .timestamp(new Date(t.getTime()))
        .ask(t.getAsk())
        .bid(t.getBid())
        .last(t.getPrice())
        .volume(t.getVolume())
        .build();
  }

  public static OrderBook adaptOrderBook(DaseOrderBookSnapshot s, CurrencyPair pair) {
    List<LimitOrder> bids = createOrders(pair, Order.OrderType.BID, s.getBids());
    List<LimitOrder> asks = createOrders(pair, Order.OrderType.ASK, s.getAsks());
    return new OrderBook(new Date(s.getTimestamp()), asks, bids);
  }

  public static Trades adaptTrades(List<DaseTrade> trades, CurrencyPair pair) {
    List<org.knowm.xchange.dto.marketdata.Trade> out =
        new ArrayList<>(trades == null ? 0 : trades.size());
    if (trades != null) {
      for (DaseTrade tr : trades) {
        // maker_side indicates the maker's side; taker side is the opposite and maps to Trade type
        String makerSide = tr.getMakerSide();
        Order.OrderType takerType;
        if ("buy".equalsIgnoreCase(makerSide)) {
          takerType = Order.OrderType.ASK; // maker buy -> taker sell (ASK)
        } else if ("sell".equalsIgnoreCase(makerSide)) {
          takerType = Order.OrderType.BID; // maker sell -> taker buy (BID)
        } else {
          takerType = null; // unknown; let builder handle null if allowed
        }

        out.add(
            org.knowm.xchange.dto.marketdata.Trade.builder()
                .type(takerType)
                .originalAmount(tr.getSize())
                .price(tr.getPrice())
                .instrument(pair)
                .timestamp(new Date(tr.getTime()))
                .id(tr.getId())
                .build());
      }
    }
    return new Trades(out, Trades.TradeSortType.SortByTimestamp);
  }

  public static Order adaptOrder(DaseOrder o) {
    CurrencyPair pair = toCurrencyPair(o.getMarket());
    if (pair == null) {
      throw new ExchangeException("Invalid market in order: " + o.getMarket());
    }
    OrderType side =
        "buy".equalsIgnoreCase(o.getSide()) ? Order.OrderType.BID : Order.OrderType.ASK;
    BigDecimal originalAmount = parseDecimal(o.getSize());
    BigDecimal averagePrice = parseDecimal(o.getFilledPrice());
    BigDecimal cumulativeAmount = parseDecimal(o.getFilled());
    boolean hasPartial =
        cumulativeAmount != null
            && originalAmount != null
            && cumulativeAmount.compareTo(BigDecimal.ZERO) > 0
            && cumulativeAmount.compareTo(originalAmount) < 0;

    Order.OrderStatus status;
    if ("open".equalsIgnoreCase(o.getStatus())) {
      status = hasPartial ? Order.OrderStatus.PARTIALLY_FILLED : Order.OrderStatus.NEW;
    } else if ("canceled".equalsIgnoreCase(o.getStatus())) {
      status = Order.OrderStatus.CANCELED;
    } else if ("closed".equalsIgnoreCase(o.getStatus())) {
      boolean isFullyFilled =
          originalAmount != null
              && cumulativeAmount != null
              && cumulativeAmount.compareTo(originalAmount) >= 0;
      status = isFullyFilled ? Order.OrderStatus.FILLED : Order.OrderStatus.CANCELED;
    } else {
      status = Order.OrderStatus.UNKNOWN;
    }
    Date ts = o.getCreatedAt() == null ? null : new Date(o.getCreatedAt());

    if ("limit".equalsIgnoreCase(o.getType())) {
      BigDecimal price = parseDecimal(o.getPrice());
      return new LimitOrder(
          side,
          originalAmount,
          pair,
          o.getId(),
          ts,
          price,
          averagePrice,
          cumulativeAmount,
          null,
          status,
          o.getClientId());
    }

    // market: use core MarketOrder DTO
    return new org.knowm.xchange.dto.trade.MarketOrder(
        side,
        // Prefer original amount; if unknown and the order is FILLED, fall back to
        // cumulative.
        // Otherwise leave null to avoid misrepresenting size.
        originalAmount != null
            ? originalAmount
            : (status == Order.OrderStatus.FILLED && cumulativeAmount != null
                ? cumulativeAmount
                : null),
        pair,
        o.getId(),
        ts,
        averagePrice,
        cumulativeAmount,
        null,
        status,
        o.getClientId());
  }

  private static BigDecimal parseDecimal(String s) {
    if (s == null) return null;
    try {
      return new BigDecimal(s);
    } catch (Exception e) {
      return null;
    }
  }

  private static List<LimitOrder> createOrders(
      CurrencyPair pair, Order.OrderType side, List<List<BigDecimal>> levels) {
    List<LimitOrder> out = new ArrayList<>(levels == null ? 0 : levels.size());
    if (levels == null) {
      return out;
    }
    for (List<BigDecimal> l : levels) {
      if (l == null || l.size() != 2) {
        continue;
      }
      BigDecimal price = l.get(0);
      BigDecimal amount = l.get(1);
      if (price == null || amount == null) {
        continue;
      }
      out.add(new LimitOrder(side, amount, pair, null, null, price));
    }
    return out;
  }

  public static List<FundingRecord> adaptFundingRecords(List<ApiAccountTxn> txns) {
    List<FundingRecord> out = new ArrayList<>(txns == null ? 0 : txns.size());
    if (txns == null) {
      return out;
    }
    for (ApiAccountTxn t : txns) {
      if (t == null) {
        continue;
      }
      Currency currency = t.getCurrency() == null ? null : Currency.getInstance(t.getCurrency());
      Date date = new Date(t.getCreatedAt());

      FundingRecord.Type type = mapTxnTypeToFundingType(t.getTxnType());
      FundingRecord.Status status = FundingRecord.Status.COMPLETE;
      String description = t.getTxnType();

      FundingRecord fr =
          FundingRecord.builder()
              .date(date)
              .currency(currency)
              .amount(t.getAmount())
              .internalId(t.getId())
              .type(type)
              .status(status)
              .description(description)
              .build();
      out.add(fr);
    }
    return out;
  }

  private static FundingRecord.Type mapTxnTypeToFundingType(String txnType) {
    if (txnType == null) {
      throw new IllegalArgumentException("txnType is null");
    }
    switch (txnType) {
      case "deposit":
        return FundingRecord.Type.DEPOSIT;
      case "withdrawal_commit":
        return FundingRecord.Type.WITHDRAWAL;
      case "withdrawal_block":
        return FundingRecord.Type.OTHER_OUTFLOW;
      case "withdrawal_unblock":
        return FundingRecord.Type.OTHER_INFLOW;
      case "trade_fill_fee_base":
      case "trade_fill_fee_quote":
        return FundingRecord.Type.OTHER_OUTFLOW;
      case "trade_fill_credit_base":
      case "trade_fill_credit_quote":
        return FundingRecord.Type.OTHER_INFLOW;
      case "trade_fill_debit_base":
      case "trade_fill_debit_quote":
        return FundingRecord.Type.OTHER_OUTFLOW;
      case "portfolio_transfer_credit":
        return FundingRecord.Type.INTERNAL_DEPOSIT;
      case "portfolio_transfer_debit":
        return FundingRecord.Type.INTERNAL_WITHDRAWAL;
      default:
        throw new IllegalArgumentException("Unknown txnType: " + txnType);
    }
  }
}
