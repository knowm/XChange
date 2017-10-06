package org.knowm.xchange.coinfloor.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinfloor.CoinfloorAdapters;
import org.knowm.xchange.coinfloor.dto.trade.CoinfloorOrder;
import org.knowm.xchange.coinfloor.dto.trade.CoinfloorUserTransaction;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderByIdParams;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamMultiCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamOffset;
import org.knowm.xchange.service.trade.params.TradeHistoryParamPaging;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsSorted;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParamMultiCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class CoinfloorTradeService extends CoinfloorTradeServiceRaw implements TradeService {

  private static final CurrencyPair NO_CURRENCY_PAIR = null;
  private static final Collection<CurrencyPair> NO_CURRENCY_PAIR_COLLECTION = Collections.emptySet();

  private final Collection<CurrencyPair> allConfiguredCurrencyPairs;

  public CoinfloorTradeService(Exchange exchange) {
    super(exchange);
    allConfiguredCurrencyPairs = exchange.getExchangeMetaData().getCurrencyPairs().keySet();
  }

  @Override
  public OpenOrders getOpenOrders() throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    // no currency pairs have been supplied - search them all
    return getOpenOrders(NO_CURRENCY_PAIR, allConfiguredCurrencyPairs);
  }

  @Override
  public OpenOrders getOpenOrders(
      OpenOrdersParams params) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    CurrencyPair pair;
    if (params instanceof OpenOrdersParamCurrencyPair) {
      pair = ((OpenOrdersParamCurrencyPair) params).getCurrencyPair();
    } else {
      pair = NO_CURRENCY_PAIR;
    }

    Collection<CurrencyPair> pairs;
    if (params instanceof OpenOrdersParamMultiCurrencyPair) {
      pairs = ((OpenOrdersParamMultiCurrencyPair) params).getCurrencyPairs();
    } else {
      pairs = NO_CURRENCY_PAIR_COLLECTION;
    }

    return getOpenOrders(pair, pairs);
  }

  private OpenOrders getOpenOrders(CurrencyPair pair, Collection<CurrencyPair> pairs) throws IOException {
    Collection<CoinfloorOrder> orders = new ArrayList<>();

    if (pair == NO_CURRENCY_PAIR) {
      if (pairs.isEmpty()) {
        // no currency pairs have been supplied - search them all
        pairs = allConfiguredCurrencyPairs;
      }
    } else {
      CoinfloorOrder[] orderArray = getOpenOrders(pair);
      for (CoinfloorOrder order : orderArray) {
        order.setCurrencyPair(pair);
        orders.add(order);
      }
    }

    for (CurrencyPair currencyPair : pairs) {
      CoinfloorOrder[] orderArray = getOpenOrders(currencyPair);
      for (CoinfloorOrder order : orderArray) {
        order.setCurrencyPair(currencyPair);
        orders.add(order);
      }
    }

    return CoinfloorAdapters.adaptOpenOrders(orders);
  }

  /**
   * By default if no CurrencyPairs are specified then the trade history for all markets will be returned.
   */
  @Override
  public OpenOrdersParams createOpenOrdersParams() {
    return new CoinfloorOpenOrdersParams();
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {
    Integer limit;
    if (params instanceof TradeHistoryParamPaging) {
      limit = ((TradeHistoryParamPaging) params).getPageLength();
    } else {
      limit = null;
    }

    Long offset;
    if (params instanceof TradeHistoryParamOffset) {
      offset = ((TradeHistoryParamOffset) params).getOffset();
    } else {
      offset = null;
    }

    TradeHistoryParamsSorted.Order sort;
    if (params instanceof TradeHistoryParamsSorted) {
      sort = ((TradeHistoryParamsSorted) params).getOrder();
    } else {
      sort = null;
    }

    CurrencyPair pair;
    if (params instanceof TradeHistoryParamCurrencyPair) {
      pair = ((TradeHistoryParamCurrencyPair) params).getCurrencyPair();
    } else {
      pair = NO_CURRENCY_PAIR;
    }

    Collection<CurrencyPair> pairs;
    if (params instanceof TradeHistoryParamMultiCurrencyPair) {
      pairs = ((TradeHistoryParamMultiCurrencyPair) params).getCurrencyPairs();
    } else {
      pairs = NO_CURRENCY_PAIR_COLLECTION;
    }

    Collection<CoinfloorUserTransaction> transactions = new ArrayList<>();

    if (pair == NO_CURRENCY_PAIR) {
      if (pairs.isEmpty()) {
        // no currency pairs have been supplied - search them all
        pairs = allConfiguredCurrencyPairs;
      }

      for (CurrencyPair currencyPair : pairs) {
        transactions.addAll(Arrays.asList(getUserTransactions(currencyPair, limit, offset, sort)));
      }
    } else {
      transactions.addAll(Arrays.asList(getUserTransactions(pair, limit, offset, sort)));
    }

    return CoinfloorAdapters.adaptTradeHistory(transactions);
  }

  /**
   * By default if no CurrencyPairs are specified then the trade history for all markets will be returned.
   */
  @Override
  public TradeHistoryParams createTradeHistoryParams() {
    return new CoinfloorTradeHistoryParams();
  }

  @Override
  public String placeLimitOrder(
      LimitOrder order) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    CoinfloorOrder rawOrder = placeLimitOrder(order.getCurrencyPair(), order.getType(), order.getTradableAmount(), order.getLimitPrice());
    return Long.toString(rawOrder.getId());
  }

  @Override
  public String placeMarketOrder(
      MarketOrder order) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    placeMarketOrder(order.getCurrencyPair(), order.getType(), order.getTradableAmount());
    return ""; // coinfloor does not return an id for market orders
  }

  @Override
  public boolean cancelOrder(
      String orderId) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    // API requires currency pair but value seems to be ignored - only the order ID is used for lookup. 
    CurrencyPair currencyPairValueIsIgnored = CurrencyPair.BTC_GBP;
    return cancelOrder(currencyPairValueIsIgnored, Long.parseLong(orderId));
  }

  @Override
  public boolean cancelOrder(CancelOrderParams orderParams) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    if (orderParams instanceof CancelOrderByIdParams) {
      cancelOrder(((CancelOrderByIdParams) orderParams).orderId);
    }
    return false;
  }

  @Override
  public Collection<Order> getOrder(
      String... orderIds) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public void verifyOrder(LimitOrder limitOrder) throws NotYetImplementedForExchangeException {
    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public void verifyOrder(MarketOrder marketOrder) throws NotYetImplementedForExchangeException {
    throw new NotYetImplementedForExchangeException();
  }
}
