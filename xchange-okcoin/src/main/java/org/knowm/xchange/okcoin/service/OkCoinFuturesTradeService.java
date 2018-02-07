package org.knowm.xchange.okcoin.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.*;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.okcoin.FuturesContract;
import org.knowm.xchange.okcoin.OkCoinAdapters;
import org.knowm.xchange.okcoin.OkCoinUtils;
import org.knowm.xchange.okcoin.dto.trade.OkCoinFuturesOrder;
import org.knowm.xchange.okcoin.dto.trade.OkCoinFuturesOrderResult;
import org.knowm.xchange.okcoin.dto.trade.OkCoinFuturesTradeHistoryResult;
import org.knowm.xchange.okcoin.dto.trade.OkCoinPriceLimit;
import org.knowm.xchange.okcoin.dto.trade.OkCoinTradeResult;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderByIdParams;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.DefaultTradeHistoryParamPaging;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OkCoinFuturesTradeService extends OkCoinTradeServiceRaw implements TradeService {

  private static final OpenOrders noOpenOrders = new OpenOrders(Collections.<LimitOrder> emptyList());
  private final Logger log = LoggerFactory.getLogger(OkCoinFuturesTradeService.class);

  private final int leverRate;
  private final int batchSize = 50;
  private final FuturesContract futuresContract;

  /**
   * Constructor
   * 
   * @param exchange
   */
  public OkCoinFuturesTradeService(Exchange exchange, FuturesContract futuresContract, int leverRate) {

    super(exchange);

    this.leverRate = leverRate;
    this.futuresContract = futuresContract;
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {
    return getOpenOrders(createOpenOrdersParams());
  }

  @Override
  public OpenOrders getOpenOrders(OpenOrdersParams params) throws IOException {
    // TODO use params for currency pair
    List<CurrencyPair> exchangeSymbols = exchange.getExchangeSymbols();

    List<OkCoinFuturesOrderResult> orderResults = new ArrayList<>(exchangeSymbols.size());

    for (int i = 0; i < exchangeSymbols.size(); i++) {
      CurrencyPair symbol = exchangeSymbols.get(i);
      log.debug("Getting order: {}", symbol);

      OkCoinFuturesOrderResult orderResult = getFuturesOrder(-1, OkCoinAdapters.adaptSymbol(symbol), "0", "50", futuresContract);
      if (orderResult.getOrders().length > 0) {
        orderResults.add(orderResult);
      }
    }

    if (orderResults.size() <= 0) {
      return noOpenOrders;
    }

    return OkCoinAdapters.adaptOpenOrdersFutures(orderResults);
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {
    long orderId;
    if (marketOrder.getType() == OrderType.BID || marketOrder.getType() == OrderType.ASK) {
      orderId = futuresTrade(OkCoinAdapters.adaptSymbol(marketOrder.getCurrencyPair()), marketOrder.getType() == OrderType.BID ? "1" : "2", "0",
          marketOrder.getOriginalAmount().toPlainString(), futuresContract, 1, leverRate).getOrderId();
      return String.valueOf(orderId);
    } else {
      return liquidateMarketOrder(marketOrder);
    }
  }

  /**
   * Liquidate long or short contract (depending on market order order type) using a market order
   */
  public String liquidateMarketOrder(MarketOrder marketOrder) throws IOException {

    long orderId = futuresTrade(OkCoinAdapters.adaptSymbol(marketOrder.getCurrencyPair()),
        marketOrder.getType() == OrderType.BID || marketOrder.getType() == OrderType.EXIT_BID ? "3" : "4", "0",
        marketOrder.getOriginalAmount().toPlainString(), futuresContract, 1, leverRate).getOrderId();
    return String.valueOf(orderId);
  }

  /**
   * Retrieves the max price from the okex imposed by the price limits
   */
  public OkCoinPriceLimit getPriceLimits(CurrencyPair currencyPair, Object... args) throws IOException {
    if (args != null && args.length > 0)
      return getFuturesPriceLimits(currencyPair, (FuturesContract) args[0]);
    else
      return getFuturesPriceLimits(currencyPair, futuresContract);

  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {
    long orderId;
    if (limitOrder.getType() == OrderType.BID || limitOrder.getType() == OrderType.ASK) {
      orderId = futuresTrade(OkCoinAdapters.adaptSymbol(limitOrder.getCurrencyPair()), limitOrder.getType() == OrderType.BID ? "1" : "2",
          limitOrder.getLimitPrice().toPlainString(), limitOrder.getOriginalAmount().toPlainString(), futuresContract, 0, leverRate).getOrderId();
      return String.valueOf(orderId);
    } else {
      return liquidateLimitOrder(limitOrder);
    }
  }

  @Override
  public String placeStopOrder(StopOrder stopOrder) throws IOException {
    throw new NotYetImplementedForExchangeException();
  }

  /**
   * Liquidate long or short contract using a limit order
   */
  public String liquidateLimitOrder(LimitOrder limitOrder) throws IOException {

    long orderId = futuresTrade(OkCoinAdapters.adaptSymbol(limitOrder.getCurrencyPair()),
        limitOrder.getType() == OrderType.BID || limitOrder.getType() == OrderType.EXIT_BID ? "3" : "4", limitOrder.getLimitPrice().toPlainString(),
        limitOrder.getOriginalAmount().toPlainString(), futuresContract, 0, leverRate).getOrderId();
    return String.valueOf(orderId);
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {

    boolean ret = false;
    long id = Long.valueOf(orderId);

    List<CurrencyPair> exchangeSymbols = exchange.getExchangeSymbols();
    List<FuturesContract> exchangeContracts = getExchangeContracts();

    for (int i = 0; i < exchangeSymbols.size(); i++) {
      CurrencyPair symbol = exchangeSymbols.get(i);
      for (FuturesContract futuresContract : exchangeContracts) {

        try {
          OkCoinTradeResult cancelResult = futuresCancelOrder(id, OkCoinAdapters.adaptSymbol(symbol), futuresContract);

          if (id == cancelResult.getOrderId()) {
            ret = true;
          }
          break;

        } catch (ExchangeException e) {
          if (e.getMessage().equals(OkCoinUtils.getErrorMessage(1009)) || e.getMessage().equals(OkCoinUtils.getErrorMessage(20015))) {
            // order not found.
            continue;
          }
        }
      }
    }
    return ret;
  }

  @Override
  public boolean cancelOrder(CancelOrderParams orderParams) throws IOException {
    if (orderParams instanceof CancelOrderByIdParams) {
      return cancelOrder(((CancelOrderByIdParams) orderParams).getOrderId());
    } else {
      return false;
    }
  }

  /**
   * Parameters: see {@link OkCoinFuturesTradeService.OkCoinFuturesTradeHistoryParams}
   */
  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {
    OkCoinFuturesTradeHistoryParams myParams = (OkCoinFuturesTradeHistoryParams) params;
    long orderId = myParams.getOrderId() != null ? Long.valueOf(myParams.getOrderId()) : -1;
    CurrencyPair currencyPair = myParams.getCurrencyPair();
    String page = myParams.getPageNumber().toString();
    String pageLength = myParams.getPageLength().toString();
    FuturesContract reqFuturesContract = myParams.futuresContract;

    OkCoinFuturesTradeHistoryResult[] orderHistory = getFuturesTradesHistory(OkCoinAdapters.adaptSymbol(currencyPair), Long.valueOf("86751191"),
        "2015-12-04");
    // orderHistory
    //(orderId, OkCoinAdapters.adaptSymbol(currencyPair), page, pageLength, reqFuturesContract);
    return OkCoinAdapters.adaptTradeHistory(orderHistory);
    //OkCoinAdapters.adaptTradesFutures(orderHistory);
  }

  public List<FuturesContract> getExchangeContracts() {
    return java.util.Arrays.asList(FuturesContract.values());
  }

  @Override
  public OkCoinFuturesTradeHistoryParams createTradeHistoryParams() {
    return new OkCoinFuturesTradeHistoryParams(50, 0, CurrencyPair.BTC_USD, futuresContract, null);
  }

  @Override
  public OpenOrdersParams createOpenOrdersParams() {
    return null;
  }

  // TODO if Futures ever get a generic interface, move this interface to xchange-core
  public interface TradeHistoryParamFuturesContract extends TradeHistoryParams {
    FuturesContract getFuturesContract();

    void setFuturesContract(FuturesContract futuresContract);

    String getOrderId();

    void setOrderId(String orderId);
  }

  final public static class OkCoinFuturesTradeHistoryParams extends DefaultTradeHistoryParamPaging implements TradeHistoryParamCurrencyPair,
      TradeHistoryParamFuturesContract {
    private CurrencyPair currencyPair;
    private FuturesContract futuresContract;
    private String orderId;

    public OkCoinFuturesTradeHistoryParams() {
    }

    public OkCoinFuturesTradeHistoryParams(Integer pageLength, Integer pageNumber, CurrencyPair currencyPair, FuturesContract futuresContract,
        String orderId) {
      super(pageLength, pageNumber);
      this.currencyPair = currencyPair;
      this.futuresContract = futuresContract;
      this.orderId = orderId;
    }

    @Override
    public void setCurrencyPair(CurrencyPair pair) {
      this.currencyPair = pair;
    }

    @Override
    public CurrencyPair getCurrencyPair() {
      return currencyPair;
    }

    @Override
    public FuturesContract getFuturesContract() {
      return futuresContract;
    }

    @Override
    public void setFuturesContract(FuturesContract futuresContract) {
      this.futuresContract = futuresContract;
    }

    @Override
    public String getOrderId() {
      return orderId;
    }

    @Override
    public void setOrderId(String orderId) {
      this.orderId = orderId;
    }
  }

  @Override
  public Collection<Order> getOrder(String... orderIds) throws IOException {
    List<CurrencyPair> exchangeSymbols = exchange.getExchangeSymbols();
    List<Order> openOrders = new ArrayList<>();
    List<OkCoinFuturesOrder> orderResults = new ArrayList<>(exchangeSymbols.size());
    List<String> orderIdsRequest = new ArrayList<>();
    Set<String> orderSet = new HashSet<>();

    Collections.addAll(orderSet, orderIds);

    for (int i = 0; i < exchangeSymbols.size(); i++) {
      CurrencyPair symbol = exchangeSymbols.get(i);
      log.debug("Getting order: {}", symbol);
      int count = 0;
      orderIdsRequest.clear();
      for (String order : orderSet) {
        orderIdsRequest.add(order);
        count++;
        if (count % batchSize == 0) {

          OkCoinFuturesOrderResult orderResult = getFuturesOrders(createDelimitedString(orderIdsRequest.toArray(new String[orderIdsRequest.size()])),
              OkCoinAdapters.adaptSymbol(symbol), futuresContract);
          orderIdsRequest.clear();
          if (orderResult.getOrders().length > 0) {
            orderResults.addAll(new ArrayList<>(Arrays.asList(orderResult.getOrders())));
          }

        }
      }
      OkCoinFuturesOrderResult orderResult;
      if (!orderIdsRequest.isEmpty()) {
        orderResult = getFuturesOrders(createDelimitedString(orderIdsRequest.toArray(new String[orderIdsRequest.size()])),
            OkCoinAdapters.adaptSymbol(symbol), futuresContract);
      } else {
        orderResult = getFuturesFilledOrder(-1, OkCoinAdapters.adaptSymbol(symbol), "0", "50", futuresContract);
      }

      if (orderResult.getOrders().length > 0) {
        for (int o = 0; o < orderResult.getOrders().length; o++) {
          OkCoinFuturesOrder singleOrder = orderResult.getOrders()[o];
          openOrders.add(OkCoinAdapters.adaptOpenOrderFutures(singleOrder));
        }
      }
    }
    return openOrders;
  }

}
