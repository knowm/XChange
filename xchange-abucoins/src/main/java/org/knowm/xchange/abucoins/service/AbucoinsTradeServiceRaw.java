package org.knowm.xchange.abucoins.service;

import static org.knowm.xchange.dto.Order.OrderType.BID;
import static org.knowm.xchange.utils.DateUtils.toUnixTimeNullSafe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.abucoins.AbucoinsAuthenticated;
import org.knowm.xchange.abucoins.dto.AbucoinsRequest;
import org.knowm.xchange.abucoins.dto.AbucoinsSingleIdRequest;
import org.knowm.xchange.abucoins.dto.AbucoinsSingleOrderIdRequest;
import org.knowm.xchange.abucoins.dto.ArchivedOrdersRequest;
import org.knowm.xchange.abucoins.dto.PlaceOrderRequest;
import org.knowm.xchange.abucoins.dto.trade.AbucoinsArchivedOrder;
import org.knowm.xchange.abucoins.dto.trade.AbucoinsOpenOrder;
import org.knowm.xchange.abucoins.dto.trade.AbucoinsOpenOrders;
import org.knowm.xchange.abucoins.dto.trade.AbucoinsOrder;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamLimit;
import org.knowm.xchange.service.trade.params.TradeHistoryParamPaging;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsTimeSpan;

import si.mazi.rescu.HttpStatusIOException;

public class AbucoinsTradeServiceRaw extends AbucoinsBaseService {

  public AbucoinsTradeServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public List<AbucoinsOrder> getAbucoinsOpenOrders(CurrencyPair currencyPair) throws IOException {

    List<AbucoinsOrder> AbucoinsOrderList = new ArrayList<>();

    String tradableIdentifier = currencyPair.base.getCurrencyCode();
    String transactionCurrency = currencyPair.counter.getCurrencyCode();

    AbucoinsOpenOrders openOrders = null;/*AbucoinsAuthenticated.getOpenOrders(
        signatureCreator,
        tradableIdentifier,
        transactionCurrency,
        new AbucoinsRequest()
    );*/

    for (AbucoinsOrder AbucoinsOrder : openOrders.getOpenOrders()) {
      AbucoinsOrder.setTradableIdentifier(tradableIdentifier);
      AbucoinsOrder.setTransactionCurrency(transactionCurrency);
      AbucoinsOrderList.add(AbucoinsOrder);
    }

    return AbucoinsOrderList;
  }

  public List<AbucoinsOrder> getAbucoinsOpenOrders() throws IOException {

    List<AbucoinsOrder> AbucoinsOrderList = new ArrayList<>();

    for (CurrencyPair currencyPair : exchange.getExchangeMetaData().getCurrencyPairs().keySet()) {
      AbucoinsOrderList.addAll(getAbucoinsOpenOrders(currencyPair));
    }
    return AbucoinsOrderList;
  }

  public AbucoinsOrder placeAbucoinsLimitOrder(LimitOrder limitOrder) throws IOException {

    AbucoinsOrder order = null;/*AbucoinsAuthenticated.placeOrder(
        signatureCreator,
        limitOrder.getCurrencyPair().base.getCurrencyCode(),
        limitOrder.getCurrencyPair().counter.getCurrencyCode(),
        new PlaceOrderRequest(
            (limitOrder.getType() == BID ? AbucoinsOrder.Type.buy : AbucoinsOrder.Type.sell),
            limitOrder.getLimitPrice(),
            limitOrder.getOriginalAmount()
        ));
    if (order.getErrorMessage() != null) {
      throw new ExchangeException(order.getErrorMessage());
    }*/
    return order;
  }

  public boolean cancelAbucoinsOrder(String orderId) throws IOException {
	  return false;
	  /*
    return AbucoinsAuthenticated
        .cancelOrder(signatureCreator, new AbucoinsSingleOrderIdRequest(orderId))
        .equals(true);
        */
  }

  public List<AbucoinsArchivedOrder> archivedOrders(TradeHistoryParams tradeHistoryParams) throws HttpStatusIOException {
    String baseCcy = null;
    String counterCcy = null;
    Integer limit = null;
    Long dateTo = null;
    Long dateFrom = null;
    Long lastTxDateTo = null;
    Long lastTxDateFrom = null;
    String status;

    if (tradeHistoryParams instanceof AbucoinsTradeHistoryParams) {
      AbucoinsTradeHistoryParams params = (AbucoinsTradeHistoryParams) tradeHistoryParams;

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

        lastTxDateFrom = toUnixTimeNullSafe(tradeHistoryParamsTimeSpan.getStartTime());
        lastTxDateTo = toUnixTimeNullSafe(tradeHistoryParamsTimeSpan.getEndTime());
//        dateFrom = toUnixTimeNullSafe(tradeHistoryParamsTimeSpan.getStartTime());
//        dateTo = toUnixTimeNullSafe(tradeHistoryParamsTimeSpan.getEndTime());
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

    ArchivedOrdersRequest request = new ArchivedOrdersRequest(
        limit,
        dateFrom,
        dateTo,
        lastTxDateFrom,
        lastTxDateTo,
        status
    );

    return null;//AbucoinsAuthenticated.archivedOrders(signatureCreator, baseCcy, counterCcy, request);
  }

  public AbucoinsOpenOrder getOrderDetail(String orderId) throws IOException {
    return null;//AbucoinsAuthenticated.getOrder(signatureCreator, new AbucoinsSingleOrderIdRequest(orderId));
  }

  public Map getOrderTransactions(String orderId) throws IOException {
    return null;//AbucoinsAuthenticated.getOrderTransactions(signatureCreator, new AbucoinsSingleIdRequest(orderId));
  }

  public static class AbucoinsTradeHistoryParams implements TradeHistoryParams, TradeHistoryParamCurrencyPair, TradeHistoryParamsTimeSpan, TradeHistoryParamLimit {

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

    public AbucoinsTradeHistoryParams(CurrencyPair currencyPair) {
      this(currencyPair, null, (Date) null, null, null, null, null);
    }

    public AbucoinsTradeHistoryParams(CurrencyPair currencyPair, Integer limit, Date dateFrom, Date dateTo, Date lastTxDateFrom, Date lastTxDateTo, String status) {
      this(currencyPair, limit, toUnixTimeNullSafe(dateFrom), toUnixTimeNullSafe(dateTo), toUnixTimeNullSafe(lastTxDateFrom), toUnixTimeNullSafe(lastTxDateTo), status);
    }

    public AbucoinsTradeHistoryParams(CurrencyPair currencyPair, Integer limit, Long dateFrom, Long dateTo, Long lastTxDateFrom, Long lastTxDateTo, String status) {
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
