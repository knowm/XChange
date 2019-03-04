package org.knowm.xchange.simulated;

import static java.math.BigDecimal.ZERO;
import static java.math.RoundingMode.HALF_UP;
import static java.util.UUID.randomUUID;
import static java.util.stream.Collectors.toList;
import static org.knowm.xchange.dto.Order.OrderType.ASK;
import static org.knowm.xchange.dto.Order.OrderType.BID;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.exceptions.ExchangeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Ordering;

final class MatchingEngine {

  private static final Logger LOGGER = LoggerFactory.getLogger(MatchingEngine.class);
  private static final BigDecimal FEE_RATE = new BigDecimal("0.001");
  private static final int TRADE_HISTORY_SIZE = 50;

  private final CurrencyPair currencyPair;
  private final int priceScale;
  private final Consumer<Fill> onFill;

  private final LinkedList<BookLevel> asks = new LinkedList<>();
  private final LinkedList<BookLevel> bids = new LinkedList<>();
  private final LinkedList<Trade> publicTrades = new LinkedList<>();
  private final Multimap<String, UserTrade> userTrades = LinkedListMultimap.create();

  private volatile BigDecimal last;

  MatchingEngine(CurrencyPair currencyPair, int priceScale) {
    this(currencyPair, priceScale,  f -> {});
  }

  MatchingEngine(CurrencyPair currencyPair, int priceScale, Consumer<Fill> onFill) {
    this.currencyPair = currencyPair;
    this.priceScale = priceScale;
    this.onFill = onFill;
  }

  public synchronized LimitOrder postOrder(String apiKey, Order original) {
    LOGGER.debug("User {} posting order: {}", apiKey, original);
    BookOrder takerOrder = BookOrder.fromOrder(original, apiKey);
    switch (takerOrder.getType()) {
      case ASK:
        LOGGER.debug("Matching against bids");
        chewBook(bids, takerOrder);
        if (!takerOrder.isDone()) {
          if (original instanceof MarketOrder) {
            throw new ExchangeException("Cannot fulfil order. No buyers.");
          }
          insertIntoBook(asks, takerOrder, ASK);
        }
        break;
      case BID:
        LOGGER.debug("Matching against asks");
        chewBook(asks, takerOrder);
        if (!takerOrder.isDone()) {
          if (original instanceof MarketOrder) {
            throw new ExchangeException("Cannot fulfil order. No sellers.");
          }
          insertIntoBook(bids, takerOrder, BID);
        }
        break;
      default:
        throw new ExchangeException("Unsupported order type: " + takerOrder.getType());
    }
    return takerOrder.toOrder(currencyPair);
  }

  private void insertIntoBook(LinkedList<BookLevel> book, BookOrder takerOrder, OrderType type) {
    Iterator<BookLevel> iter = book.iterator();
    int i = 0;
    while (iter.hasNext()) {
      BookLevel level = iter.next();
      int signum = level.getPrice().compareTo(takerOrder.getLimitPrice());
      if (signum == 0) {
        level.getOrders().add(takerOrder);
        return;
      } else if (signum < 0 && type == BID || signum > 0 && type == ASK) {
        BookLevel newLevel = new BookLevel(takerOrder.getLimitPrice());
        newLevel.getOrders().add(takerOrder);
        book.add(i, newLevel);
        return;
      }
      i++;
    }
    BookLevel newLevel = new BookLevel(takerOrder.getLimitPrice());
    newLevel.getOrders().add(takerOrder);
    book.add(newLevel);
    return;
  }

  public synchronized Level3OrderBook book() {
    return new Level3OrderBook(
      FluentIterable.from(asks)
        .transformAndConcat(BookLevel::getOrders)
        .transform(o -> o.toOrder(currencyPair))
        .toList(),
      FluentIterable.from(bids)
        .transformAndConcat(BookLevel::getOrders)
        .transform(o -> o.toOrder(currencyPair))
        .toList()
    );
  }

  public synchronized Ticker ticker() {
    return new Ticker.Builder()
        .ask(asks.isEmpty() ? null : asks.get(0).getPrice())
        .bid(bids.isEmpty() ? null : bids.get(0).getPrice())
        .last(last)
        .build();
  }

  public synchronized List<Trade> publicTrades() {
    return FluentIterable.from(publicTrades)
        .transform(t -> Trade.Builder.from(t).build())
        .toList();
  }

  public synchronized List<UserTrade> tradeHistory(String apiKey) {
    return ImmutableList.copyOf(userTrades.get(apiKey));
  }

  private void chewBook(Iterable<BookLevel> makerOrders, BookOrder takerOrder) {
    Iterator<BookLevel> levelIter = makerOrders.iterator();
    while (levelIter.hasNext()) {
      BookLevel level = levelIter.next();
      Iterator<BookOrder> orderIter = level.getOrders().iterator();
      while (orderIter.hasNext() && !takerOrder.isDone()) {
        BookOrder makerOrder = orderIter.next();

        LOGGER.debug("Matching against maker order {}", makerOrder);
        if (!makerOrder.matches(takerOrder)) {
          LOGGER.debug("Ran out of maker orders at this price");
          return;
        }

        BigDecimal tradeAmount = takerOrder.getRemainingAmount().compareTo(makerOrder.getRemainingAmount()) > 0
            ? makerOrder.getRemainingAmount()
            : takerOrder.getRemainingAmount();

        LOGGER.debug("Matches for {}", tradeAmount);
        matchOff(takerOrder, makerOrder, tradeAmount);

        if (makerOrder.isDone()) {
          LOGGER.debug("Maker order removed from book");
          orderIter.remove();
          if (level.getOrders().isEmpty()) {
            levelIter.remove();
          }
        }
      }
    }
  }

  private void matchOff(BookOrder takerOrder, BookOrder makerOrder, BigDecimal tradeAmount) {
    Date timestamp = new Date();

    UserTrade takerTrade = new UserTrade.Builder()
        .currencyPair(currencyPair)
        .id(randomUUID().toString())
        .originalAmount(tradeAmount)
        .price(makerOrder.getLimitPrice())
        .timestamp(timestamp)
        .type(takerOrder.getType())
        .orderId(takerOrder.getId())
        .feeAmount(takerOrder.getType() == ASK
            ? tradeAmount.multiply(makerOrder.getLimitPrice()).multiply(FEE_RATE)
            : tradeAmount.multiply(FEE_RATE))
        .feeCurrency(takerOrder.getType() == ASK ? currencyPair.counter : currencyPair.base)
        .build();

    LOGGER.debug("Created taker trade: {}", takerTrade);

    accumulate(takerOrder, takerTrade);

    OrderType makerType = takerOrder.getType() == OrderType.ASK ? OrderType.BID : OrderType.ASK;
    UserTrade makerTrade = new UserTrade.Builder()
        .currencyPair(currencyPair)
        .id(randomUUID().toString())
        .originalAmount(tradeAmount)
        .price(makerOrder.getLimitPrice())
        .timestamp(timestamp)
        .type(makerType)
        .orderId(makerOrder.getId())
        .feeAmount(makerType == ASK
            ? tradeAmount.multiply(makerOrder.getLimitPrice()).multiply(FEE_RATE)
            : tradeAmount.multiply(FEE_RATE))
        .feeCurrency(makerType == ASK ? currencyPair.counter : currencyPair.base)
        .build();

    LOGGER.debug("Created maker trade: {}", makerOrder);
    accumulate(makerOrder, makerTrade);

    last = makerOrder.getLimitPrice();
    recordFill(new Fill(takerOrder.getApiKey(), takerTrade, true));
    recordFill(new Fill(makerOrder.getApiKey(), makerTrade, false));
  }

  private void accumulate(BookOrder bookOrder, UserTrade trade) {
    BigDecimal amount = trade.getOriginalAmount();
    BigDecimal price = trade.getPrice();
    BigDecimal newTotal = bookOrder.getCumulativeAmount().add(amount);

    if (bookOrder.getCumulativeAmount().compareTo(ZERO) == 0) {
      bookOrder.setAveragePrice(price);
    } else {
      bookOrder.setAveragePrice(
          bookOrder.getAveragePrice()
              .multiply(bookOrder.getCumulativeAmount())
              .add(price.multiply(amount))
              .divide(newTotal, priceScale, HALF_UP));
    }

    bookOrder.setCumulativeAmount(newTotal);
    bookOrder.setFee(bookOrder.getFee().add(trade.getFeeAmount()));
  }

  public synchronized List<LimitOrder> openOrders(String apiKey) {
    return Stream.concat(asks.stream(), bids.stream())
        .flatMap(v -> v.getOrders().stream())
        .filter(o -> o.getApiKey().equals(apiKey))
        .sorted(Ordering.natural().onResultOf(BookOrder::getTimestamp).reversed())
        .map(o -> o.toOrder(currencyPair))
        .collect(toList());
  }

  public synchronized OrderBook level2() {
    return new OrderBook(new Date(), accumulateBookSide(asks), accumulateBookSide(bids));
  }

  private List<LimitOrder> accumulateBookSide(List<BookLevel> book) {
    BigDecimal price = null;
    BigDecimal amount = ZERO;
    List<LimitOrder> result = new ArrayList<>();
    Iterator<BookOrder> iter = book.stream().flatMap(v -> v.getOrders().stream()).iterator();
    while (iter.hasNext()) {
      BookOrder bookOrder = iter.next();
      amount = amount.add(bookOrder.getRemainingAmount());
      if (price != null && bookOrder.getLimitPrice().compareTo(price) != 0) {
        result.add(new LimitOrder.Builder(ASK, currencyPair).originalAmount(amount).limitPrice(price).build());
        amount = ZERO;
      }
      price = bookOrder.getLimitPrice();
    }
    if (price != null) {
      result.add(new LimitOrder.Builder(ASK, currencyPair).originalAmount(amount).limitPrice(price).build());
    }
    return result;
  }

  private void recordFill(Fill fill) {
    if (fill.isTaker()) {
      publicTrades.push(fill.getTrade());
      if (publicTrades.size() > TRADE_HISTORY_SIZE) {
        publicTrades.removeLast();
      }
    }
    userTrades.put(fill.getApiKey(), fill.getTrade());
    onFill.accept(fill);
  }
}
