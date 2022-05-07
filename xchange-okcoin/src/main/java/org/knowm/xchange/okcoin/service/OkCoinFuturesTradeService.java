package org.knowm.xchange.okcoin.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.StopOrder;
import org.knowm.xchange.dto.trade.UserTrades;
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
import org.knowm.xchange.service.trade.params.CancelOrderByCurrencyPair;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.DefaultCancelOrderParamId;
import org.knowm.xchange.service.trade.params.DefaultTradeHistoryParamPaging;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.orders.DefaultQueryOrderParam;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;
import org.knowm.xchange.service.trade.params.orders.OrderQueryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OrderQueryParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OkCoinFuturesTradeService extends OkCoinTradeServiceRaw implements TradeService {

  private static final OpenOrders noOpenOrders =
      new OpenOrders(Collections.<LimitOrder>emptyList());
  private final Logger log = LoggerFactory.getLogger(OkCoinFuturesTradeService.class);

  private final int leverRate;
  private final int batchSize = 50;
  private final FuturesContract futuresContract;

  /**
   * Constructor
   *
   * @param exchange
   */
  public OkCoinFuturesTradeService(
      Exchange exchange, FuturesContract futuresContract, int leverRate) {

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

      OkCoinFuturesOrderResult orderResult =
          getFuturesOrder(-1, OkCoinAdapters.adaptSymbol(symbol), "0", "50", futuresContract);
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
      orderId =
          futuresTrade(
                  OkCoinAdapters.adaptSymbol(marketOrder.getCurrencyPair()),
                  marketOrder.getType() == OrderType.BID ? "1" : "2",
                  "0",
                  marketOrder.getOriginalAmount().toPlainString(),
                  futuresContract,
                  1,
                  leverRate)
              .getOrderId();
      return String.valueOf(orderId);
    } else {
      return liquidateMarketOrder(marketOrder);
    }
  }

  /**
   * Liquidate long or short contract (depending on market order order type) using a market order
   */
  public String liquidateMarketOrder(MarketOrder marketOrder) throws IOException {

    long orderId =
        futuresTrade(
                OkCoinAdapters.adaptSymbol(marketOrder.getCurrencyPair()),
                marketOrder.getType() == OrderType.BID
                        || marketOrder.getType() == OrderType.EXIT_BID
                    ? "3"
                    : "4",
                "0",
                marketOrder.getOriginalAmount().toPlainString(),
                futuresContract,
                1,
                leverRate)
            .getOrderId();
    return String.valueOf(orderId);
  }

  /** Retrieves the max price from the okex imposed by the price limits */
  public OkCoinPriceLimit getPriceLimits(CurrencyPair currencyPair, Object... args)
      throws IOException {
    if (args != null && args.length > 0)
      return getFuturesPriceLimits(currencyPair, (FuturesContract) args[0]);
    else return getFuturesPriceLimits(currencyPair, futuresContract);
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {
    long orderId;
    if (limitOrder.getType() == OrderType.BID || limitOrder.getType() == OrderType.ASK) {
      orderId =
          futuresTrade(
                  OkCoinAdapters.adaptSymbol(limitOrder.getCurrencyPair()),
                  limitOrder.getType() == OrderType.BID ? "1" : "2",
                  limitOrder.getLimitPrice().toPlainString(),
                  limitOrder.getOriginalAmount().toPlainString(),
                  futuresContract,
                  0,
                  leverRate)
              .getOrderId();
      return String.valueOf(orderId);
    } else {
      return liquidateLimitOrder(limitOrder);
    }
  }

  @Override
  public String placeStopOrder(StopOrder stopOrder) throws IOException {
    throw new NotYetImplementedForExchangeException();
  }

  /** Liquidate long or short contract using a limit order */
  public String liquidateLimitOrder(LimitOrder limitOrder) throws IOException {

    long orderId =
        futuresTrade(
                OkCoinAdapters.adaptSymbol(limitOrder.getCurrencyPair()),
                limitOrder.getType() == OrderType.BID || limitOrder.getType() == OrderType.EXIT_BID
                    ? "3"
                    : "4",
                limitOrder.getLimitPrice().toPlainString(),
                limitOrder.getOriginalAmount().toPlainString(),
                futuresContract,
                0,
                leverRate)
            .getOrderId();
    return String.valueOf(orderId);
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {

    boolean ret = false;
    for (CurrencyPair symbol : exchange.getExchangeSymbols()) {
      for (FuturesContract futuresContract : getExchangeContracts()) {
        if (cancelOrder(new OkCoinFuturesCancelOrderParams(symbol, futuresContract, orderId))) {
          ret = true;
          break;
        }
      }
    }
    return ret;
  }

  @Override
  public boolean cancelOrder(CancelOrderParams orderParams) throws IOException {

    OkCoinFuturesCancelOrderParams myParams = (OkCoinFuturesCancelOrderParams) orderParams;

    CurrencyPair currencyPair = myParams.getCurrencyPair();
    FuturesContract reqFuturesContract = myParams.futuresContract;
    long orderId = myParams.getOrderId() != null ? Long.valueOf(myParams.getOrderId()) : -1;
    boolean ret = false;

    try {
      OkCoinTradeResult cancelResult =
          futuresCancelOrder(orderId, OkCoinAdapters.adaptSymbol(currencyPair), reqFuturesContract);

      if (orderId == cancelResult.getOrderId()) {
        ret = true;
      }

    } catch (ExchangeException e) {
      if (e.getMessage().equals(OkCoinUtils.getErrorMessage(1009))
          || e.getMessage().equals(OkCoinUtils.getErrorMessage(20015))) {
        // order not found.

      } else throw e;
    }

    return ret;
  }

  /** Parameters: see {@link OkCoinFuturesTradeService.OkCoinFuturesTradeHistoryParams} */
  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {
    OkCoinFuturesTradeHistoryParams myParams = (OkCoinFuturesTradeHistoryParams) params;
    long orderId = myParams.getOrderId() != null ? Long.valueOf(myParams.getOrderId()) : -1;
    CurrencyPair currencyPair = myParams.getCurrencyPair();
    String date = myParams.getDate();
    String page = myParams.getPageNumber().toString();
    String pageLength = myParams.getPageLength().toString();
    FuturesContract reqFuturesContract = myParams.futuresContract;

    OkCoinFuturesTradeHistoryResult[] orderHistory =
        getFuturesTradesHistory(OkCoinAdapters.adaptSymbol(currencyPair), orderId, date);

    return OkCoinAdapters.adaptTradeHistory(orderHistory);
  }

  public List<FuturesContract> getExchangeContracts() {
    return Arrays.asList(FuturesContract.values());
  }

  @Override
  public OkCoinFuturesTradeHistoryParams createTradeHistoryParams() {
    return new OkCoinFuturesTradeHistoryParams(
        50, 0, CurrencyPair.BTC_USD, futuresContract, null, "2018-08-10");
  }

  @Override
  public OpenOrdersParams createOpenOrdersParams() {
    return null;
  }

  @Override
  public Collection<Order> getOrder(OrderQueryParams... orderQueryParams) throws IOException {
    Map<CurrencyPair, Map<FuturesContract, Set<String>>> ordersToQuery = new HashMap<>();
    List<String> orderIdsRequest = new ArrayList<>();
    List<OkCoinFuturesOrder> orderResults = new ArrayList<>();
    List<Order> openOrders = new ArrayList<>();

    for (OrderQueryParams orderQueryParam : orderQueryParams) {
      OkCoinFuturesOrderQueryParams myParams = (OkCoinFuturesOrderQueryParams) orderQueryParam;
      CurrencyPair currencyPair = myParams.getCurrencyPair();
      FuturesContract reqFuturesContract = myParams.futuresContract;
      long orderId = myParams.getOrderId() != null ? Long.parseLong(myParams.getOrderId()) : -1;

      if (ordersToQuery.get(currencyPair) == null) {
        Set<String> orderSet = Collections.singleton(String.valueOf(orderId));
        HashMap<FuturesContract, Set<String>> futuresContractMap = new HashMap<>();
        futuresContractMap.put(reqFuturesContract, orderSet);
        ordersToQuery.put(currencyPair, futuresContractMap);

      } else if (ordersToQuery.get(currencyPair).get(reqFuturesContract) == null) {
        Set<String> orderSet = Collections.singleton(String.valueOf(orderId));
        ordersToQuery.get(currencyPair).put(reqFuturesContract, orderSet);
      } else {
        ordersToQuery.get(currencyPair).get(reqFuturesContract).add(String.valueOf(orderId));
      }
    }
    for (CurrencyPair pair : ordersToQuery.keySet()) {
      for (FuturesContract contract : ordersToQuery.get(pair).keySet()) {
        int count = 0;
        orderIdsRequest.clear();

        for (String order : ordersToQuery.get(pair).get(contract)) {
          orderIdsRequest.add(order);
          count++;
          if (count % batchSize == 0) {

            OkCoinFuturesOrderResult orderResult =
                getFuturesOrders(
                    createDelimitedString(
                        orderIdsRequest.toArray(new String[orderIdsRequest.size()])),
                    OkCoinAdapters.adaptSymbol(pair),
                    contract);
            orderIdsRequest.clear();
            if (orderResult.getOrders().length > 0) {
              orderResults.addAll(new ArrayList<>(Arrays.asList(orderResult.getOrders())));
            }
          }
        }
        OkCoinFuturesOrderResult orderResult;
        if (!orderIdsRequest.isEmpty()) {
          orderResult =
              getFuturesOrders(
                  createDelimitedString(
                      orderIdsRequest.toArray(new String[orderIdsRequest.size()])),
                  OkCoinAdapters.adaptSymbol(pair),
                  contract);
        } else {
          orderResult =
              getFuturesFilledOrder(-1, OkCoinAdapters.adaptSymbol(pair), "0", "50", contract);
        }

        if (orderResult.getOrders().length > 0) {
          for (int o = 0; o < orderResult.getOrders().length; o++) {
            OkCoinFuturesOrder singleOrder = orderResult.getOrders()[o];
            openOrders.add(OkCoinAdapters.adaptOpenOrderFutures(singleOrder));
          }
        }
      }
    }

    return openOrders;
  }

  @Override
  public Collection<Order> getOrder(String... orderIds) throws IOException {
    List<OkCoinFuturesOrderQueryParams> params = new ArrayList<>();

    for (CurrencyPair symbol : exchange.getExchangeSymbols()) {
      for (String orderId : orderIds) {
        params.add(new OkCoinFuturesOrderQueryParams(symbol, futuresContract, orderId));
      }
    }

    return getOrder(params.toArray(new OkCoinFuturesOrderQueryParams[params.size()]));
  }

  // TODO if Futures ever get a generic interface, move this interface to xchange-core
  public interface TradeHistoryParamFuturesContract extends TradeHistoryParams {
    FuturesContract getFuturesContract();

    void setFuturesContract(FuturesContract futuresContract);

    String getOrderId();

    void setOrderId(String orderId);
  }

  public interface OrderQueryParamFuturesContract extends OrderQueryParams {
    FuturesContract getFuturesContract();

    void setFuturesContract(FuturesContract futuresContract);

    String getOrderId();

    void setOrderId(String orderId);
  }

  public interface CancelOrderParamFuturesContract extends CancelOrderParams {
    FuturesContract getFuturesContract();

    void setFuturesContract(FuturesContract futuresContract);

    String getOrderId();

    void setOrderId(String orderId);
  }

  public static final class OkCoinFuturesTradeHistoryParams extends DefaultTradeHistoryParamPaging
      implements TradeHistoryParamCurrencyPair, TradeHistoryParamFuturesContract {
    private CurrencyPair currencyPair;
    private FuturesContract futuresContract;
    private String orderId;
    private String date; // "yyyy-MM-dd"

    public OkCoinFuturesTradeHistoryParams() {}

    public OkCoinFuturesTradeHistoryParams(
        Integer pageLength,
        Integer pageNumber,
        CurrencyPair currencyPair,
        FuturesContract futuresContract,
        String orderId,
        String date) {
      super(pageLength, pageNumber);
      this.currencyPair = currencyPair;
      this.futuresContract = futuresContract;
      this.orderId = orderId;
      this.date = date;
    }

    @Override
    public CurrencyPair getCurrencyPair() {
      return currencyPair;
    }

    @Override
    public void setCurrencyPair(CurrencyPair pair) {
      this.currencyPair = pair;
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

    public String getDate() {
      return date;
    }

    public void setDate(String date) {
      this.date = date;
    }
  }

  public static final class OkCoinFuturesOrderQueryParams extends DefaultQueryOrderParam
      implements OrderQueryParamCurrencyPair, OrderQueryParamFuturesContract {
    private CurrencyPair currencyPair;
    private FuturesContract futuresContract;
    private String orderId;

    public OkCoinFuturesOrderQueryParams() {}

    public OkCoinFuturesOrderQueryParams(
        CurrencyPair currencyPair, FuturesContract futuresContract, String orderId) {
      super(orderId);
      this.currencyPair = currencyPair;
      this.futuresContract = futuresContract;
      this.orderId = orderId;
    }

    @Override
    public CurrencyPair getCurrencyPair() {
      return currencyPair;
    }

    @Override
    public void setCurrencyPair(CurrencyPair pair) {
      this.currencyPair = pair;
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

  public static final class OkCoinFuturesCancelOrderParams extends DefaultCancelOrderParamId
      implements CancelOrderByCurrencyPair, CancelOrderParamFuturesContract {
    private CurrencyPair currencyPair;
    private FuturesContract futuresContract;
    private String orderId;

    public OkCoinFuturesCancelOrderParams() {}

    public OkCoinFuturesCancelOrderParams(
        CurrencyPair currencyPair, FuturesContract futuresContract, String orderId) {
      super(orderId);
      this.currencyPair = currencyPair;
      this.futuresContract = futuresContract;
      this.orderId = orderId;
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
}
