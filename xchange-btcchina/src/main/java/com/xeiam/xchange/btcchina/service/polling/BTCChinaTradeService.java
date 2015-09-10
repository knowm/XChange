package com.xeiam.xchange.btcchina.service.polling;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.btcchina.BTCChinaAdapters;
import com.xeiam.xchange.btcchina.BTCChinaExchangeException;
import com.xeiam.xchange.btcchina.dto.trade.request.BTCChinaGetOrdersRequest;
import com.xeiam.xchange.btcchina.dto.trade.request.BTCChinaTransactionsRequest;
import com.xeiam.xchange.btcchina.dto.trade.response.BTCChinaBooleanResponse;
import com.xeiam.xchange.btcchina.dto.trade.response.BTCChinaGetOrdersResponse;
import com.xeiam.xchange.btcchina.dto.trade.response.BTCChinaIntegerResponse;
import com.xeiam.xchange.btcchina.dto.trade.response.BTCChinaTransactionsResponse;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.dto.trade.UserTrades;
import com.xeiam.xchange.exceptions.ExchangeException;
import com.xeiam.xchange.service.polling.trade.PollingTradeService;
import com.xeiam.xchange.service.polling.trade.params.DefaultTradeHistoryParamPaging;
import com.xeiam.xchange.service.polling.trade.params.TradeHistoryParamPaging;
import com.xeiam.xchange.service.polling.trade.params.TradeHistoryParams;
import com.xeiam.xchange.service.polling.trade.params.TradeHistoryParamsIdSpan;
import com.xeiam.xchange.service.polling.trade.params.TradeHistoryParamsTimeSpan;
import com.xeiam.xchange.utils.DateUtils;

/**
 * Implementation of the trade service for BTCChina.
 * <ul>
 * <li>Provides access to trade functions</li>
 * </ul>
 *
 * @author ObsessiveOrange
 */
public class BTCChinaTradeService extends BTCChinaTradeServiceRaw implements PollingTradeService {

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

    final List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();

    List<LimitOrder> page;
    do {
      BTCChinaGetOrdersResponse response = getBTCChinaOrders(true, BTCChinaGetOrdersRequest.ALL_MARKET, null, limitOrders.size());

      page = new ArrayList<LimitOrder>();
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

  /**
   * Gets trade history for user's account.
   *
   * @param args 2 optional arguments:
   *        <ol>
   *        <li>limit: limit the number of transactions, default value is 10 if null.</li>
   *        <li>offset: start index used for pagination, default value is 0 if null.</li>
   *        <li>since: to fetch the transactions from this point, which can either be an order id or a unix timestamp, default value is 0.</li>
   *        <li>sincetype: specify the type of 'since' parameter, can either be 'id' or 'time'. default value is 'time'.</li>
   *        </ol>
   */
  @Override
  public UserTrades getTradeHistory(Object... args) throws IOException {

    final String type = BTCChinaTransactionsRequest.TYPE_ALL;
    final Integer limit = args.length > 0 ? ((Number) args[0]).intValue() : null;
    final Integer offset = args.length > 1 ? ((Number) args[1]).intValue() : null;
    final Integer since = args.length > 2 ? ((Number) args[2]).intValue() : null;
    final String sincetype = args.length > 3 ? ((String) args[3]) : null;

    return getUserTrades(type, limit, offset, since, sincetype);
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

    return getUserTrades(type, limit, offset, since, sincetype);
  }

  @Override
  public com.xeiam.xchange.service.polling.trade.params.TradeHistoryParams createTradeHistoryParams() {

    return new BTCChinaTradeHistoryParams();
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
}
