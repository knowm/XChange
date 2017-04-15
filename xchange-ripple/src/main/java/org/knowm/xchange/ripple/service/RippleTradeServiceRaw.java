package org.knowm.xchange.ripple.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.ripple.RippleExchange;
import org.knowm.xchange.ripple.dto.RippleAmount;
import org.knowm.xchange.ripple.dto.RippleException;
import org.knowm.xchange.ripple.dto.account.ITransferFeeSource;
import org.knowm.xchange.ripple.dto.trade.IRippleTradeTransaction;
import org.knowm.xchange.ripple.dto.trade.RippleAccountOrders;
import org.knowm.xchange.ripple.dto.trade.RippleLimitOrder;
import org.knowm.xchange.ripple.dto.trade.RippleNotifications;
import org.knowm.xchange.ripple.dto.trade.RippleNotifications.RippleNotification;
import org.knowm.xchange.ripple.dto.trade.RippleOrderCancelRequest;
import org.knowm.xchange.ripple.dto.trade.RippleOrderCancelResponse;
import org.knowm.xchange.ripple.dto.trade.RippleOrderEntryRequest;
import org.knowm.xchange.ripple.dto.trade.RippleOrderEntryRequestBody;
import org.knowm.xchange.ripple.dto.trade.RippleOrderEntryResponse;
import org.knowm.xchange.ripple.service.params.RippleTradeHistoryCount;
import org.knowm.xchange.ripple.service.params.RippleTradeHistoryHashLimit;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamPaging;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsTimeSpan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RippleTradeServiceRaw extends RippleBaseService {

  private static final Boolean EXCLUDE_FAILED = true;
  private static final Boolean EARLIEST_FIRST = false;
  private static final Long START_LEDGER = null;
  private static final Long END_LEDGER = null;

  private final Logger logger = LoggerFactory.getLogger(getClass());

  private final Map<String, Map<String, IRippleTradeTransaction>> rawTradeStore = new ConcurrentHashMap<>();

  public RippleTradeServiceRaw(final Exchange exchange) {
    super(exchange);
  }

  public String placeOrder(final RippleLimitOrder order, final boolean validate) throws RippleException, IOException {
    final RippleOrderEntryRequest entry = new RippleOrderEntryRequest();
    entry.setSecret(exchange.getExchangeSpecification().getSecretKey());

    final RippleOrderEntryRequestBody request = entry.getOrder();

    final RippleAmount baseAmount;
    final RippleAmount counterAmount;
    if (order.getType() == OrderType.BID) {
      request.setType("buy");
      // buying: we receive base and pay with counter, taker receives counter and pays with base
      counterAmount = request.getTakerGets();
      baseAmount = request.getTakerPays();
    } else {
      request.setType("sell");
      // selling: we receive counter and pay with base, taker receives base and pays with counter
      baseAmount = request.getTakerGets();
      counterAmount = request.getTakerPays();
    }

    baseAmount.setCurrency(order.getCurrencyPair().base.getCurrencyCode());
    baseAmount.setValue(order.getTradableAmount());
    if (baseAmount.getCurrency().equals("XRP") == false) {
      // not XRP - need a counterparty for this currency
      final String counterparty = order.getBaseCounterparty();
      if (counterparty.isEmpty()) {
        throw new ExchangeException(String.format("base counterparty must be populated for currency %s", baseAmount.getCurrency()));
      }
      baseAmount.setCounterparty(counterparty);
    }

    counterAmount.setCurrency(order.getCurrencyPair().counter.getCurrencyCode());
    counterAmount.setValue(order.getTradableAmount().multiply(order.getLimitPrice()));
    if (counterAmount.getCurrency().equals("XRP") == false) {
      // not XRP - need a counterparty for this currency
      final String counterparty = order.getCounterCounterparty();
      if (counterparty.isEmpty()) {
        throw new ExchangeException(String.format("counter counterparty must be populated for currency %s", counterAmount.getCurrency()));
      }
      counterAmount.setCounterparty(counterparty);
    }

    final RippleOrderEntryResponse response = rippleAuthenticated.orderEntry(exchange.getExchangeSpecification().getApiKey(), validate, entry);
    return Long.toString(response.getOrder().getSequence());
  }

  public boolean cancelOrder(final String orderId, final boolean validate) throws RippleException, IOException {
    final RippleOrderCancelRequest cancel = new RippleOrderCancelRequest();
    cancel.setSecret(exchange.getExchangeSpecification().getSecretKey());

    final RippleOrderCancelResponse response = rippleAuthenticated.orderCancel(exchange.getExchangeSpecification().getApiKey(), Long.valueOf(orderId),
        validate, cancel);
    return response.isSuccess();
  }

  public RippleAccountOrders getOpenAccountOrders() throws RippleException, IOException {
    return ripplePublic.openAccountOrders(exchange.getExchangeSpecification().getApiKey());
  }

  public RippleNotifications getNotifications(final String account, final Boolean excludeFailed, final Boolean earliestFirst,
      final Integer resultsPerPage, final Integer page, final Long startLedger, final Long endLedger) throws RippleException, IOException {
    return ripplePublic.notifications(account, excludeFailed, earliestFirst, resultsPerPage, page, startLedger, endLedger);
  }

  /**
   * Retrieve order details from local store if they have been previously stored otherwise query external server.
   */
  public IRippleTradeTransaction getTrade(final String account, final RippleNotification notification) throws RippleException, IOException {
    final RippleExchange ripple = (RippleExchange) exchange;
    if (ripple.isStoreTradeTransactionDetails()) {
      Map<String, IRippleTradeTransaction> cache = rawTradeStore.get(account);
      if (cache == null) {
        cache = new ConcurrentHashMap<>();
        rawTradeStore.put(account, cache);
      }
      if (cache.containsKey(notification.getHash())) {
        return cache.get(notification.getHash());
      }
    }

    final IRippleTradeTransaction trade;
    try {
      if (notification.getType().equals("order")) {
        trade = ripplePublic.orderTransaction(account, notification.getHash());
      } else if (notification.getType().equals("payment")) {
        trade = ripplePublic.paymentTransaction(account, notification.getHash());
      } else {
        throw new IllegalArgumentException(String.format("unexpected notification %s type for transaction[%s] and account[%s]",
            notification.getType(), notification.getHash(), notification.getAccount()));
      }

    } catch (final RippleException e) {
      if (e.getHttpStatusCode() == 500 && e.getErrorType().equals("transaction")) {
        // Do not let an individual transaction parsing bug in the Ripple REST service cause a total trade   
        // history failure. See https://github.com/ripple/ripple-rest/issues/384 as an example of this situation. 
        logger.error("exception reading {} transaction[{}] for account[{}]", notification.getType(), notification.getHash(), account, e);
        return null;
      } else {
        throw e;
      }
    }
    if (ripple.isStoreTradeTransactionDetails()) {
      rawTradeStore.get(account).put(notification.getHash(), trade);
    }
    return trade;
  }

  public List<IRippleTradeTransaction> getTradesForAccount(final TradeHistoryParams params,
      final String account) throws RippleException, IOException {
    final Integer pageLength;
    final Integer pageNumber;
    if (params instanceof TradeHistoryParamPaging) {
      final TradeHistoryParamPaging pagingParams = (TradeHistoryParamPaging) params;
      pageLength = pagingParams.getPageLength();
      pageNumber = pagingParams.getPageNumber();
    } else {
      pageLength = pageNumber = null;
    }

    final Collection<String> currencyFilter = new HashSet<>();
    if (params instanceof TradeHistoryParamCurrencyPair) {
      final CurrencyPair pair = ((TradeHistoryParamCurrencyPair) params).getCurrencyPair();
      if (pair != null) {
        currencyFilter.add(pair.base.getCurrencyCode());
        currencyFilter.add(pair.counter.getCurrencyCode());
      }
    }

    final Date startTime, endTime;
    if (params instanceof TradeHistoryParamsTimeSpan) {
      final TradeHistoryParamsTimeSpan timeSpanParams = (TradeHistoryParamsTimeSpan) params;
      // return all trades between start time (oldest) and end time (most recent)
      startTime = timeSpanParams.getStartTime();
      endTime = timeSpanParams.getEndTime();
    } else {
      startTime = endTime = null;
    }

    final RippleTradeHistoryCount rippleCount;
    if (params instanceof RippleTradeHistoryCount) {
      rippleCount = (RippleTradeHistoryCount) params;
    } else {
      rippleCount = null;
    }

    final String hashLimit;
    if (params instanceof RippleTradeHistoryHashLimit) {
      hashLimit = ((RippleTradeHistoryHashLimit) params).getHashLimit();
    } else {
      hashLimit = null;
    }

    final List<IRippleTradeTransaction> trades = new ArrayList<>();

    final RippleNotifications notifications = ripplePublic.notifications(account, EXCLUDE_FAILED, EARLIEST_FIRST, pageLength, pageNumber,
        START_LEDGER, END_LEDGER);
    if (rippleCount != null) {
      rippleCount.incrementApiCallCount();
    }
    if (notifications.getNotifications().isEmpty()) {
      return trades;
    }

    // Notifications are returned with the most recent at bottom of the result page. Therefore,
    // in order to consider the most recent first, loop through using a reverse order iterator.
    final ListIterator<RippleNotification> iterator = notifications.getNotifications().listIterator(notifications.getNotifications().size());
    while (iterator.hasPrevious()) {
      if (rippleCount != null) {
        if (rippleCount.getTradeCountLimit() > 0 && rippleCount.getTradeCount() >= rippleCount.getTradeCountLimit()) {
          return trades; // found enough trades
        }
        if (rippleCount.getApiCallCountLimit() > 0 && rippleCount.getApiCallCount() >= rippleCount.getApiCallCountLimit()) {
          return trades; // reached the query limit
        }
      }

      final RippleNotification notification = iterator.previous();
      if ((endTime != null) && notification.getTimestamp().after(endTime)) {
        // this trade is more recent than the end time - ignore it
        continue;
      }
      if ((startTime != null) && notification.getTimestamp().before(startTime)) {
        // this trade is older than the start time - stop searching
        return trades;
      }

      if (notification.getType().equals("order")) {
        // standard on single order book trade
      } else if (notification.getType().equals("payment") && notification.getDirection().equals("passthrough")) {
        // indirect trade passing through this order book
      } else {
        continue; // not a trade related notification
      }

      final IRippleTradeTransaction trade = getTrade(account, notification);
      if (rippleCount != null) {
        rippleCount.incrementApiCallCount();
      }
      if (trade == null) {
        continue;
      }

      final List<RippleAmount> balanceChanges = trade.getBalanceChanges();
      if (balanceChanges.size() < 2 || balanceChanges.size() > 3) {
        continue; // this is not a trade - a trade will change 2 or 3 (including XRP fee) currency balances
      }

      if (currencyFilter.isEmpty()
          || (currencyFilter.contains(balanceChanges.get(0).getCurrency()) && currencyFilter.contains(balanceChanges.get(1).getCurrency()))) {
        // no currency filter has been applied || currency filter match
        trades.add(trade);
        if (rippleCount != null) {
          rippleCount.incrementTradeCount();
        }
      }

      if (trade.getHash().equals(hashLimit)) {
        return trades; // found the last required trade - stop searching
      }
    }

    if (params instanceof TradeHistoryParamPaging && (hashLimit != null || startTime != null)) {
      // Still looking for trades, if query was complete it would have returned in the
      // loop above. Increment the page number and search next set of notifications.
      final TradeHistoryParamPaging pagingParams = (TradeHistoryParamPaging) params;
      final int currentPage;
      if (pagingParams.getPageNumber() == null) {
        currentPage = 1;
      } else {
        currentPage = pagingParams.getPageNumber();
      }
      pagingParams.setPageNumber(currentPage + 1);
      trades.addAll(getTradesForAccount(params, account));
    }

    return trades;

  }

  /**
   * The Ripple network transaction fee varies depending on how busy the network is as described
   * <a href="https://wiki.ripple.com/Transaction_Fee">here</a>.
   *
   * @return current network transaction fee in units of XRP
   */
  public BigDecimal getTransactionFee() {
    return ripplePublic.getTransactionFee().getFee().stripTrailingZeros();
  }

  /**
   * @return transfer fee for the base leg of the order in the base currency
   */
  public BigDecimal getExpectedBaseTransferFee(final RippleLimitOrder order) throws IOException {
    final ITransferFeeSource transferFeeSource = (ITransferFeeSource) exchange.getAccountService();
    final String counterparty = order.getBaseCounterparty();
    final String currency = order.getCurrencyPair().base.getCurrencyCode();
    final BigDecimal quantity = order.getTradableAmount();
    final OrderType type = order.getType();
    return getExpectedTransferFee(transferFeeSource, counterparty, currency, quantity, type);
  }

  /**
   * @return transfer fee for the counter leg of the order in the counter currency
   */
  public BigDecimal getExpectedCounterTransferFee(final RippleLimitOrder order) throws IOException {
    final ITransferFeeSource transferFeeSource = (ITransferFeeSource) exchange.getAccountService();
    final String counterparty = order.getCounterCounterparty();
    final String currency = order.getCurrencyPair().counter.getCurrencyCode();
    final BigDecimal quantity = order.getTradableAmount().multiply(order.getLimitPrice());
    final OrderType type;
    if (order.getType() == OrderType.BID) {
      type = OrderType.ASK;
    } else {
      type = OrderType.BID;
    }
    return getExpectedTransferFee(transferFeeSource, counterparty, currency, quantity, type);
  }

  /**
   * The expected counterparty transfer fee for an order that results in a transfer of the supplied amount of currency. The fee rate is payable when
   * sending the currency (not receiving it) and it set by the issuing counterparty. The rate may be zero. Transfer fees are not applicable to sending
   * XRP. More details can be found <a href="https://wiki.ripple.com/Transit_Fee">here</a>.
   *
   * @return transfer fee of the supplied currency
   */
  public static BigDecimal getExpectedTransferFee(final ITransferFeeSource transferFeeSource, final String counterparty, final String currency,
      final BigDecimal quantity, final OrderType type) throws IOException {
    if (currency.equals("XRP")) {
      return BigDecimal.ZERO;
    }
    if (counterparty.isEmpty()) {
      return BigDecimal.ZERO;
    }
    final BigDecimal transferFeeRate = transferFeeSource.getTransferFeeRate(counterparty);
    if ((transferFeeRate.compareTo(BigDecimal.ZERO) > 0) && (type == OrderType.ASK)) {
      // selling/sending the base currency so will incur a transaction fee on it
      return quantity.multiply(transferFeeRate).abs();
    } else {
      return BigDecimal.ZERO;
    }
  }

  /**
   * Clear any stored order details to allow memory to be released.
   */
  public void clearOrderDetailsStore() {
    for (final Map<String, IRippleTradeTransaction> cache : rawTradeStore.values()) {
      cache.clear();
    }
    rawTradeStore.clear();
  }
}
