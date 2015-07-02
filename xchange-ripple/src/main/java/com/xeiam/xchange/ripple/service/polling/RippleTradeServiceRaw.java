package com.xeiam.xchange.ripple.service.polling;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.exceptions.ExchangeException;
import com.xeiam.xchange.ripple.dto.RippleAmount;
import com.xeiam.xchange.ripple.dto.RippleException;
import com.xeiam.xchange.ripple.dto.trade.RippleAccountOrders;
import com.xeiam.xchange.ripple.dto.trade.RippleLimitOrder;
import com.xeiam.xchange.ripple.dto.trade.RippleNotifications;
import com.xeiam.xchange.ripple.dto.trade.RippleOrderCancelRequest;
import com.xeiam.xchange.ripple.dto.trade.RippleOrderCancelResponse;
import com.xeiam.xchange.ripple.dto.trade.RippleOrderEntryRequestBody;
import com.xeiam.xchange.ripple.dto.trade.RippleOrderEntryRequest;
import com.xeiam.xchange.ripple.dto.trade.RippleOrderEntryResponse;
import com.xeiam.xchange.ripple.dto.trade.RippleOrderDetails;
import com.xeiam.xchange.ripple.dto.trade.RippleNotifications.RippleNotification;
import com.xeiam.xchange.ripple.service.polling.params.RippleTradeHistoryCount;
import com.xeiam.xchange.ripple.service.polling.params.RippleTradeHistoryHashLimit;
import com.xeiam.xchange.service.polling.trade.params.TradeHistoryParamCurrencyPair;
import com.xeiam.xchange.service.polling.trade.params.TradeHistoryParamPaging;
import com.xeiam.xchange.service.polling.trade.params.TradeHistoryParams;
import com.xeiam.xchange.service.polling.trade.params.TradeHistoryParamsTimeSpan;

public class RippleTradeServiceRaw extends RippleBasePollingService {

  private static final Boolean EXCLUDE_FAILED = true;
  private static final Boolean EARLIEST_FIRST = false;
  private static final Long START_LEDGER = null;
  private static final Long END_LEDGER = null;

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

    baseAmount.setCurrency(order.getCurrencyPair().baseSymbol);
    baseAmount.setValue(order.getTradableAmount());
    if (baseAmount.getCurrency().equals(Currencies.XRP) == false) {
      // not XRP - need a counterparty for this currency
      final String counterparty = order.getBaseCounterparty();
      if (counterparty.isEmpty()) {
        throw new ExchangeException(String.format("base counterparty must be populated for currency %s", baseAmount.getCurrency()));
      }
      baseAmount.setCounterparty(counterparty.toString());
    }

    counterAmount.setCurrency(order.getCurrencyPair().counterSymbol);
    counterAmount.setValue(order.getTradableAmount().multiply(order.getLimitPrice()));
    if (counterAmount.getCurrency().equals(Currencies.XRP) == false) {
      // not XRP - need a counterparty for this currency
      final String counterparty = order.getCounterCounterparty();
      if (counterparty.isEmpty()) {
        throw new ExchangeException(String.format("counter counterparty must be populated for currency %s", counterAmount.getCurrency()));
      }
      counterAmount.setCounterparty(counterparty.toString());
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

  public RippleOrderDetails getOrderDetails(final String account, final String hash) throws RippleException, IOException {
    return ripplePublic.orderDetails(account, hash);
  }

  public List<RippleOrderDetails> getTradesForAccount(final TradeHistoryParams params, final String account) throws RippleException, IOException {
    final Integer pageLength;
    final Integer pageNumber;
    if (params instanceof TradeHistoryParamPaging) {
      final TradeHistoryParamPaging pagingParams = (TradeHistoryParamPaging) params;
      pageLength = pagingParams.getPageLength();
      pageNumber = pagingParams.getPageNumber();
    } else {
      pageLength = pageNumber = null;
    }

    final Collection<String> currencyFilter = new HashSet<String>();
    if (params instanceof TradeHistoryParamCurrencyPair) {
      final CurrencyPair pair = ((TradeHistoryParamCurrencyPair) params).getCurrencyPair();
      if (pair != null) {
        currencyFilter.add(pair.baseSymbol);
        currencyFilter.add(pair.counterSymbol);
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

    final List<RippleOrderDetails> trades = new ArrayList<RippleOrderDetails>();

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
        if (rippleCount.getTradeCount() >= rippleCount.getTradeCountLimit()) {
          return trades; // found enough trades
        }
        if (rippleCount.getApiCallCount() >= rippleCount.getApiCallCountLimit()) {
          return trades; // reached the query limit
        }
      }

      final RippleNotification notification = iterator.previous();
      if (endTime != null && notification.getTimestamp().after(endTime)) {
        // this trade is more recent than the end time - ignore it
        continue;
      }
      if (startTime != null && notification.getTimestamp().before(startTime)) {
        // this trade is older than the start time - stop searching
        return trades;
      }

      if (notification.getType().equals("order")) {
        final RippleOrderDetails orderDetails = ripplePublic.orderDetails(account, notification.getHash());
        if (rippleCount != null) {
          rippleCount.incrementApiCallCount();
        }

        final List<RippleAmount> balanceChanges = orderDetails.getBalanceChanges();
        if (balanceChanges.size() != 2) {
          continue; // this is not a trade - a trade will change 2 currency balances
        }

        if (currencyFilter.isEmpty()
            || (currencyFilter.contains(balanceChanges.get(0).getCurrency()) && currencyFilter.contains(balanceChanges.get(1).getCurrency()))) {
          // no currency filter has been applied || currency filter match
          trades.add(orderDetails);
          if (rippleCount != null) {
            rippleCount.incrementTradeCount();
          }
        }

        if (orderDetails.getHash().equals(hashLimit)) {
          return trades; // found the last required trade - stop searching
        }
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
}
