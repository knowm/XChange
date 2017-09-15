package org.knowm.xchange.btcchina.service.rest;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.btcchina.BTCChinaAdapters;
import org.knowm.xchange.btcchina.BTCChinaExchangeException;
import org.knowm.xchange.btcchina.dto.trade.request.BTCChinaGetOrdersRequest;
import org.knowm.xchange.btcchina.dto.trade.request.BTCChinaTransactionsRequest;
import org.knowm.xchange.btcchina.dto.trade.response.BTCChinaBooleanResponse;
import org.knowm.xchange.btcchina.dto.trade.response.BTCChinaGetOrdersResponse;
import org.knowm.xchange.btcchina.dto.trade.response.BTCChinaIntegerResponse;
import org.knowm.xchange.btcchina.dto.trade.response.BTCChinaTransactionsResponse;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderByIdParams;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.DefaultTradeHistoryParamPaging;
import org.knowm.xchange.service.trade.params.TradeHistoryParamPaging;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsIdSpan;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsTimeSpan;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;
import org.knowm.xchange.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of the trade service for BTCChina.
 * <ul>
 * <li>Provides access to trade functions</li>
 * </ul>
 *
 * @author ObsessiveOrange
 */
public class BTCChinaTradeService extends BTCChinaTradeServiceRaw implements TradeService {

  private final Logger log = LoggerFactory.getLogger(BTCChinaTradeService.class);

  /**
   * Constructor
   *
   * @param exchange
   */
  public BTCChinaTradeService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {
    return getOpenOrders(createOpenOrdersParams());
  }

  @Override
  public OpenOrders getOpenOrders(
      OpenOrdersParams params) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    final List<LimitOrder> limitOrders = new ArrayList<>();

    List<LimitOrder> page;
    do {
      BTCChinaGetOrdersResponse response = getBTCChinaOrders(true, BTCChinaGetOrdersRequest.ALL_MARKET, null, limitOrders.size());

      page = new ArrayList<>();
      page.addAll(BTCChinaAdapters.adaptOrders(response.getResult(), null));

      limitOrders.addAll(page);
    } while (page.size() >= BTCChinaGetOrdersRequest.DEFAULT_LIMIT);

    return new OpenOrders(limitOrders);
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {

    final BigDecimal amount = marketOrder.getTradableAmount();
    final String market = BTCChinaAdapters.adaptMarket(marketOrder.getCurrencyPair()).toUpperCase();
    final BTCChinaIntegerResponse response;

    if (marketOrder.getType() == OrderType.BID) {
      response = buy(null, amount, market);
    } else {
      response = sell(null, amount, market);
    }

    return response.getResult().toString();
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {

    final BigDecimal price = limitOrder.getLimitPrice();
    final BigDecimal amount = limitOrder.getTradableAmount();
    final String market = BTCChinaAdapters.adaptMarket(limitOrder.getCurrencyPair()).toUpperCase();
    final BTCChinaIntegerResponse response;

    if (limitOrder.getType() == OrderType.BID) {
      response = buy(price, amount, market);
    } else {
      response = sell(price, amount, market);
    }

    return response.getResult().toString();
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {

    boolean ret;

    try {
      BTCChinaBooleanResponse response = cancelBTCChinaOrder(Integer.parseInt(orderId));
      ret = response.getResult();
    } catch (BTCChinaExchangeException e) {
      if (e.getErrorCode() == -32026) {
        // Order already completed
        ret = false;
      } else {
        throw e;
      }
    }

    return ret;
  }

  @Override
  public boolean cancelOrder(CancelOrderParams orderParams) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    if (orderParams instanceof CancelOrderByIdParams) {
      cancelOrder(((CancelOrderByIdParams) orderParams).orderId);
    }
    return false;
  }

  private UserTrades getUserTrades(String type, Integer limit, Integer offset, Integer since, String sincetype) throws IOException {
    log.debug("type: {}, limit: {}, offset: {}, since: {}, sincetype: {}", type, limit, offset, since, sincetype);

    final BTCChinaTransactionsResponse response = getTransactions(type, limit, offset, since, sincetype);
    return BTCChinaAdapters.adaptTransactions(response.getResult().getTransactions());
  }

  /**
   * Supported parameters: {@link TradeHistoryParamPaging} {@link TradeHistoryParamsTimeSpan#getStartTime()}
   * {@link TradeHistoryParamsIdSpan#getStartId()} used only if startTime is not set
   */
  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws ExchangeException, IOException {

    String type = BTCChinaTransactionsRequest.TYPE_ALL;
    if (params instanceof BTCChinaTradeHistoryParams) {
      type = ((BTCChinaTradeHistoryParams) params).type;
    }

    Integer limit = null;
    Integer offset = null;
    if (params instanceof TradeHistoryParamPaging) {
      Integer pageNumber = ((TradeHistoryParamPaging) params).getPageNumber();
      limit = ((TradeHistoryParamPaging) params).getPageLength();
      offset = (limit == null || pageNumber == null) ? null : limit * pageNumber;
    }

    String sincetype = null;
    Integer since = null;

    if (params instanceof TradeHistoryParamsIdSpan) {
      String startId = ((TradeHistoryParamsIdSpan) params).getStartId();
      if (startId != null) {
        since = Integer.valueOf(startId);
        sincetype = BTCChinaTransactionsRequest.SINCE_ID;
      }
    }

    if (params instanceof TradeHistoryParamsTimeSpan) {
      Date startTime = ((TradeHistoryParamsTimeSpan) params).getStartTime();
      if (startTime != null) {
        since = (int) DateUtils.toUnixTime(startTime);
        sincetype = BTCChinaTransactionsRequest.SINCE_TIME;
      }
    }

    final BTCChinaGetOrdersResponse response = getBTCChinaOrders(false, BTCChinaGetOrdersRequest.ALL_MARKET, limit, offset, since, true);
    final UserTrades userTradesFromOrders = BTCChinaAdapters.adaptUserTradesFromOrders(response.getResult(), null);

    final List<UserTrade> tradeHistory = new ArrayList<UserTrade>();
    if (userTradesFromOrders != null) {
      tradeHistory.addAll(userTradesFromOrders.getUserTrades());
    }

    if (limit == null) {
      limit = BTCChinaGetOrdersRequest.DEFAULT_LIMIT;
    }

    /**
     * fetch archived record only if date is older than 1 week (or not provided) and trade size is still < limit
     */
    int currentTime = (int) DateUtils.toUnixTime(System.currentTimeMillis());
    boolean fetchArchivedRecords = ((since == null) || ((currentTime - since) > 604800)) &&
        (tradeHistory.size() < limit);

    UserTrades userTrades = userTradesFromOrders;
    if (fetchArchivedRecords) {
      if (tradeHistory.size() < limit) {
        final int remainingLimit = limit - tradeHistory.size();
        final BTCChinaGetOrdersResponse responseArchived = getBTCChinaArchivedOrders(BTCChinaGetOrdersRequest.ALL_MARKET, remainingLimit, null, true);
        final UserTrades userTradesFromArchivedOrders = BTCChinaAdapters.adaptUserTradesFromOrders(responseArchived.getResult(), null);
        tradeHistory.addAll(userTradesFromArchivedOrders.getUserTrades());
        userTrades = new UserTrades(tradeHistory, Trades.TradeSortType.SortByID);
      }
    }

    return userTrades;
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {

    return new BTCChinaTradeHistoryParams();
  }

  @Override
  public OpenOrdersParams createOpenOrdersParams() {
    return null;
  }

  public static class BTCChinaTradeHistoryParams extends DefaultTradeHistoryParamPaging
      implements TradeHistoryParamsTimeSpan, TradeHistoryParamsIdSpan {

    private String type = BTCChinaTransactionsRequest.TYPE_ALL;
    private Date startTime;
    private String startId;

    public BTCChinaTradeHistoryParams() {
    }

    public BTCChinaTradeHistoryParams(Integer pageLength, Integer pageNumber, String type, Date startTime, Integer startId) {

      super(pageLength, pageNumber);
      this.type = type;
      this.startTime = startTime;

      if (startId != null) {
        setStartId(startId.toString());
      }
    }

    @Override
    public void setStartTime(Date startTime) {

      this.startTime = startTime;
    }

    @Override
    public Date getStartTime() {

      return startTime;
    }

    @Override
    public void setEndTime(Date endTime) {

    }

    @Override
    public Date getEndTime() {

      return null;
    }

    @Override
    public void setStartId(String startId) {

      this.startId = startId;
    }

    @Override
    public String getStartId() {

      return startId;
    }

    @Override
    public void setEndId(String endId) {

    }

    @Override
    public String getEndId() {

      return null;
    }
  }

  @Override
  public Collection<Order> getOrder(
      String... orderIds) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    throw new NotYetImplementedForExchangeException();
  }

}
