package org.knowm.xchange.cexio.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.cexio.CexIOAuthenticated;
import org.knowm.xchange.cexio.dto.trade.CexIOArchivedOrder;
import org.knowm.xchange.cexio.dto.trade.CexIOOpenOrder;
import org.knowm.xchange.cexio.dto.trade.CexIOOpenOrders;
import org.knowm.xchange.cexio.dto.trade.CexIOOrder;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamLimit;
import org.knowm.xchange.service.trade.params.TradeHistoryParamPaging;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsTimeSpan;
import org.knowm.xchange.utils.DateUtils;
import si.mazi.rescu.HttpStatusIOException;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.knowm.xchange.dto.Order.OrderType.BID;
import static org.knowm.xchange.utils.DateUtils.toMillisNullSafe;
import static org.knowm.xchange.utils.DateUtils.toUnixTimeNullSafe;

/**
 * @author timmolter
 */

public class CexIOTradeServiceRaw extends CexIOBaseService {

  private final CexIOAuthenticated cexIOAuthenticated;
  private ParamsDigest signatureCreator;

  /**
   * Constructor
   *
   * @param exchange
   */
  public CexIOTradeServiceRaw(Exchange exchange) {

    super(exchange);
    cexIOAuthenticated = RestProxyFactory.createProxy(CexIOAuthenticated.class, exchange.getExchangeSpecification().getSslUri());
    signatureCreator = CexIODigest.createInstance(exchange.getExchangeSpecification().getSecretKey(),
        exchange.getExchangeSpecification().getUserName(), exchange.getExchangeSpecification().getApiKey());
  }

  public List<CexIOOrder> getCexIOOpenOrders(CurrencyPair currencyPair) throws IOException {

    List<CexIOOrder> cexIOOrderList = new ArrayList<>();

    String tradableIdentifier = currencyPair.base.getCurrencyCode();
    String transactionCurrency = currencyPair.counter.getCurrencyCode();

    CexIOOpenOrders openOrders = cexIOAuthenticated.getOpenOrders(tradableIdentifier, transactionCurrency,
        exchange.getExchangeSpecification().getApiKey(), signatureCreator, exchange.getNonceFactory());

    for (CexIOOrder cexIOOrder : openOrders.getOpenOrders()) {
      cexIOOrder.setTradableIdentifier(tradableIdentifier);
      cexIOOrder.setTransactionCurrency(transactionCurrency);
      cexIOOrderList.add(cexIOOrder);
    }

    return cexIOOrderList;
  }

  public List<CexIOOrder> getCexIOOpenOrders() throws IOException {

    List<CexIOOrder> cexIOOrderList = new ArrayList<>();

    for (CurrencyPair currencyPair : exchange.getExchangeMetaData().getCurrencyPairs().keySet()) {
      cexIOOrderList.addAll(getCexIOOpenOrders(currencyPair));
    }
    return cexIOOrderList;
  }

  public CexIOOrder placeCexIOLimitOrder(LimitOrder limitOrder) throws IOException {

    CexIOOrder order = cexIOAuthenticated.placeOrder(limitOrder.getCurrencyPair().base.getCurrencyCode(),
        limitOrder.getCurrencyPair().counter.getCurrencyCode(), exchange.getExchangeSpecification().getApiKey(), signatureCreator,
        exchange.getNonceFactory(), (limitOrder.getType() == BID ? CexIOOrder.Type.buy : CexIOOrder.Type.sell), limitOrder.getLimitPrice(),
        limitOrder.getTradableAmount());
    if (order.getErrorMessage() != null) {
      throw new ExchangeException(order.getErrorMessage());
    }
    return order;
  }

  public boolean cancelCexIOOrder(String orderId) throws IOException {

    return cexIOAuthenticated
        .cancelOrder(exchange.getExchangeSpecification().getApiKey(), signatureCreator, exchange.getNonceFactory(), Long.parseLong(orderId))
        .equals(true);
  }

  public List<CexIOArchivedOrder> archivedOrders(TradeHistoryParams tradeHistoryParams) throws HttpStatusIOException {
    String baseCcy = null;
    String counterCcy = null;
    Integer limit = null;
    Long dateTo = null;
    Long dateFrom = null;
    Long lastTxDateTo = null;
    Long lastTxDateFrom = null;
    String status;

    if (tradeHistoryParams instanceof CexIOTradeHistoryParams) {
      CexIOTradeHistoryParams params = (CexIOTradeHistoryParams) tradeHistoryParams;

      CurrencyPair currencyPair = params.currencyPair;
      baseCcy = currencyPair == null ? null : currencyPair.base.getCurrencyCode();
      counterCcy = currencyPair == null ? null : currencyPair.counter.getCurrencyCode();
      limit = params.limit;
      dateTo = params.dateTo;
      dateFrom = params.dateFrom;
      lastTxDateTo = params.lastTxDateTo;
      lastTxDateFrom = params.lastTxDateFrom;
      status = params.status;
    } else {
      status = "d";// done (fully executed)

      if (tradeHistoryParams instanceof TradeHistoryParamsTimeSpan) {
        TradeHistoryParamsTimeSpan tradeHistoryParamsTimeSpan = (TradeHistoryParamsTimeSpan) tradeHistoryParams;

//        lastTxDateFrom = toUnixTimeNullSafe(tradeHistoryParamsTimeSpan.getStartTime());
//        lastTxDateTo = toUnixTimeNullSafe(tradeHistoryParamsTimeSpan.getEndTime());
        dateFrom = toUnixTimeNullSafe(tradeHistoryParamsTimeSpan.getStartTime());
        dateTo = toUnixTimeNullSafe(tradeHistoryParamsTimeSpan.getEndTime());
      }

      if (tradeHistoryParams instanceof TradeHistoryParamCurrencyPair) {
        CurrencyPair currencyPair = ((TradeHistoryParamCurrencyPair) tradeHistoryParams).getCurrencyPair();

        baseCcy = currencyPair == null ? null : currencyPair.base.getCurrencyCode();
        counterCcy = currencyPair == null ? null : currencyPair.counter.getCurrencyCode();
      }

      if (tradeHistoryParams instanceof TradeHistoryParamLimit) {
        limit = ((TradeHistoryParamLimit) tradeHistoryParams).getLimit();
      }

      if (tradeHistoryParams instanceof TradeHistoryParamPaging) {
        limit = ((TradeHistoryParamPaging) tradeHistoryParams).getPageLength();
      }
    }

    //todo: get the date parameters working, they seem to be ignored

    return cexIOAuthenticated.archivedOrders(exchange.getExchangeSpecification().getApiKey(), signatureCreator, exchange.getNonceFactory(),
        baseCcy,
        counterCcy,
        limit,
        dateFrom,
        dateTo,
        lastTxDateFrom,
        lastTxDateTo,
        status
    );
  }

  public CexIOOpenOrder getOrderDetail(String orderId) throws IOException {
    return cexIOAuthenticated.getOrder(exchange.getExchangeSpecification().getApiKey(), signatureCreator, exchange.getNonceFactory(), orderId);
  }

  public Map getOrderTransactions(String orderId) throws IOException {
    return cexIOAuthenticated.getOrderTransactions(exchange.getExchangeSpecification().getApiKey(), signatureCreator, exchange.getNonceFactory(), orderId);
  }

  public static class CexIOTradeHistoryParams implements TradeHistoryParams, TradeHistoryParamCurrencyPair, TradeHistoryParamsTimeSpan, TradeHistoryParamLimit {

    private CurrencyPair currencyPair;

    /**
     * limit the number of entries in response (1 to 100)
     */
    private Integer limit;

    /**
     * end date for open orders filtering (timestamp in seconds, 10 digits)
     */
    private Long dateTo;

    /**
     * start date for open order filtering (timestamp in seconds, 10 digits)
     */
    private Long dateFrom;

    /**
     * end date for last change orders filtering (timestamp in seconds, 10 digits)
     */
    private final Long lastTxDateTo;

    /**
     * start date for last change order filtering (timestamp in seconds, 10 digits)
     */
    private final Long lastTxDateFrom;

    /**
     * "d" — done (fully executed), "c" — canceled (not executed), "cd" — cancel-done (partially executed)
     */
    private final String status;//todo: this should be an enum

    public CexIOTradeHistoryParams(CurrencyPair currencyPair) {
      this(currencyPair, null, (Date) null, null, null, null, null);
    }

    public CexIOTradeHistoryParams(CurrencyPair currencyPair, Integer limit, Date dateFrom, Date dateTo, Date lastTxDateFrom, Date lastTxDateTo, String status) {
      this(currencyPair, limit, toUnixTimeNullSafe(dateFrom), toUnixTimeNullSafe(dateTo), toUnixTimeNullSafe(lastTxDateFrom), toUnixTimeNullSafe(lastTxDateTo), status);
    }

    public CexIOTradeHistoryParams(CurrencyPair currencyPair, Integer limit, Long dateFrom, Long dateTo, Long lastTxDateFrom, Long lastTxDateTo, String status) {
      this.currencyPair = currencyPair;
      this.limit = limit;
      this.dateTo = dateTo;
      this.dateFrom = dateFrom;
      this.lastTxDateTo = lastTxDateTo;
      this.lastTxDateFrom = lastTxDateFrom;
      this.status = status;
    }

    @Override
    public void setCurrencyPair(CurrencyPair currencyPair) {
      this.currencyPair = currencyPair;
    }

    @Override
    public CurrencyPair getCurrencyPair() {
      return currencyPair;
    }

    @Override
    public void setStartTime(Date startTime) {
      this.dateFrom = startTime.getTime();
    }

    @Override
    public Date getStartTime() {
      return this.dateFrom == null ? null : new Date(this.dateFrom);
    }

    @Override
    public void setEndTime(Date endTime) {
      this.dateTo = endTime.getTime();
    }

    @Override
    public Date getEndTime() {
      return this.dateTo == null ? null : new Date(this.dateTo);
    }

    @Override
    public void setLimit(Integer limit) {
      this.limit = limit;
    }

    @Override
    public Integer getLimit() {
      return limit;
    }
  }
}
