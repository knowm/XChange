package org.knowm.xchange.cexio.service;

import static org.knowm.xchange.dto.Order.OrderType.BID;
import static org.knowm.xchange.utils.DateUtils.toUnixTimeNullSafe;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.cexio.dto.*;
import org.knowm.xchange.cexio.dto.CexioCancelReplaceOrderRequest;
import org.knowm.xchange.cexio.dto.trade.*;
import org.knowm.xchange.cexio.dto.trade.CexIOCancelReplaceOrderResponse;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.service.trade.params.*;

public class CexIOTradeServiceRaw extends CexIOBaseService {
  public CexIOTradeServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public List<CexIOOrder> getCexIOOpenOrders(CurrencyPair currencyPair) throws IOException {

    List<CexIOOrder> cexIOOrderList = new ArrayList<>();

    String tradableIdentifier = currencyPair.base.getCurrencyCode();
    String transactionCurrency = currencyPair.counter.getCurrencyCode();

    CexIOOpenOrders openOrders =
        cexIOAuthenticated.getOpenOrders(
            signatureCreator, tradableIdentifier, transactionCurrency, new CexIORequest());

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

    CexIOOrder order =
        cexIOAuthenticated.placeOrder(
            signatureCreator,
            limitOrder.getCurrencyPair().base.getCurrencyCode(),
            limitOrder.getCurrencyPair().counter.getCurrencyCode(),
            new PlaceOrderRequest(
                (limitOrder.getType() == BID ? CexIOOrder.Type.buy : CexIOOrder.Type.sell),
                limitOrder.getLimitPrice(),
                limitOrder.getOriginalAmount()));
    if (order.getErrorMessage() != null) {
      throw new ExchangeException(order.getErrorMessage());
    }
    return order;
  }

  public boolean cancelCexIOOrder(String orderId) throws IOException {
    return cexIOAuthenticated
        .cancelOrder(signatureCreator, new CexioSingleOrderIdRequest(orderId))
        .equals(true);
  }

  public CexIOCancelAllOrdersResponse cancelCexIOOrders(CurrencyPair currencyPair)
      throws IOException {
    return cexIOAuthenticated.cancelAllOrders(
        signatureCreator,
        currencyPair.base.getCurrencyCode(),
        currencyPair.counter.getCurrencyCode(),
        new CexIORequest());
  }

  public CexIOCancelReplaceOrderResponse cancelReplaceCexIOOrder(
      CurrencyPair currencyPair,
      Order.OrderType type,
      String orderId,
      BigDecimal amount,
      BigDecimal price)
      throws ExchangeException, IOException {

    String orderType;
    switch (type) {
      case BID:
        orderType = "buy";
        break;
      case ASK:
        orderType = "sell";
        break;
      default:
        throw new IllegalArgumentException(String.format("Unexpected order type '%s'", type));
    }

    CexIOCancelReplaceOrderResponse response =
        cexIOAuthenticated.cancelReplaceOrder(
            signatureCreator,
            currencyPair.base.getCurrencyCode(),
            currencyPair.counter.getCurrencyCode(),
            new CexioCancelReplaceOrderRequest(orderId, orderType, amount, price));

    if (response.getError() != null) {
      throw new ExchangeException(response.getError());
    }

    return response;
  }

  public List<CexIOArchivedOrder> archivedOrders(TradeHistoryParams tradeHistoryParams)
      throws IOException {
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
      status = "d"; // done (fully executed)

      if (tradeHistoryParams instanceof TradeHistoryParamsTimeSpan) {
        TradeHistoryParamsTimeSpan tradeHistoryParamsTimeSpan =
            (TradeHistoryParamsTimeSpan) tradeHistoryParams;

        lastTxDateFrom = toUnixTimeNullSafe(tradeHistoryParamsTimeSpan.getStartTime());
        lastTxDateTo = toUnixTimeNullSafe(tradeHistoryParamsTimeSpan.getEndTime());
        //        dateFrom = toUnixTimeNullSafe(tradeHistoryParamsTimeSpan.getStartTime());
        //        dateTo = toUnixTimeNullSafe(tradeHistoryParamsTimeSpan.getEndTime());
      }

      if (tradeHistoryParams instanceof TradeHistoryParamCurrencyPair) {
        CurrencyPair currencyPair =
            ((TradeHistoryParamCurrencyPair) tradeHistoryParams).getCurrencyPair();

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

    ArchivedOrdersRequest request =
        new ArchivedOrdersRequest(limit, dateFrom, dateTo, lastTxDateFrom, lastTxDateTo, status);

    return cexIOAuthenticated.archivedOrders(signatureCreator, baseCcy, counterCcy, request);
  }

  public CexIOOpenOrder getOrderDetail(String orderId) throws IOException {
    return cexIOAuthenticated.getOrder(signatureCreator, new CexioSingleOrderIdRequest(orderId));
  }

  public CexIOFullOrder getOrderFullDetail(String orderId) throws IOException {
    Map orderRaw =
        cexIOAuthenticated.getOrderRaw(signatureCreator, new CexioSingleOrderIdRequest(orderId));
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    CexIOOpenOrder order = objectMapper.convertValue(orderRaw, CexIOOpenOrder.class);
    return new CexIOFullOrder(
        order.user,
        order.type,
        order.symbol1,
        order.symbol2,
        order.amount,
        order.remains,
        order.price,
        order.time,
        order.lastTxTime,
        order.tradingFeeStrategy,
        order.tradingFeeTaker,
        order.tradingFeeMaker,
        order.tradingFeeUserVolumeAmount,
        order.lastTx,
        order.status,
        order.orderId,
        order.id,
        (String) orderRaw.get("ta:" + order.symbol2),
        (String) orderRaw.get("tta:" + order.symbol2),
        (String) orderRaw.get("fa:" + order.symbol2),
        (String) orderRaw.get("tfa:" + order.symbol2));
  }

  public Map getOrderTransactions(String orderId) throws IOException {
    return cexIOAuthenticated.getOrderTransactions(
        signatureCreator, new CexioSingleIdRequest(orderId));
  }

  public static class CexIOTradeHistoryParams
      implements TradeHistoryParams,
          TradeHistoryParamCurrencyPair,
          TradeHistoryParamsTimeSpan,
          TradeHistoryParamLimit {

    /** end date for last change orders filtering (timestamp in seconds, 10 digits) */
    private final Long lastTxDateTo;
    /** start date for last change order filtering (timestamp in seconds, 10 digits) */
    private final Long lastTxDateFrom;
    /**
     * "d" — done (fully executed), "c" — canceled (not executed), "cd" — cancel-done (partially
     * executed)
     */
    private final String status; // todo: this should be an enum

    private CurrencyPair currencyPair;
    /** limit the number of entries in response (1 to 100) */
    private Integer limit;
    /** end date for open orders filtering (timestamp in seconds, 10 digits) */
    private Long dateTo;
    /** start date for open order filtering (timestamp in seconds, 10 digits) */
    private Long dateFrom;

    public CexIOTradeHistoryParams(CurrencyPair currencyPair) {
      this(currencyPair, null, (Date) null, null, null, null, null);
    }

    public CexIOTradeHistoryParams(
        CurrencyPair currencyPair,
        Integer limit,
        Date dateFrom,
        Date dateTo,
        Date lastTxDateFrom,
        Date lastTxDateTo,
        String status) {
      this(
          currencyPair,
          limit,
          toUnixTimeNullSafe(dateFrom),
          toUnixTimeNullSafe(dateTo),
          toUnixTimeNullSafe(lastTxDateFrom),
          toUnixTimeNullSafe(lastTxDateTo),
          status);
    }

    public CexIOTradeHistoryParams(
        CurrencyPair currencyPair,
        Integer limit,
        Long dateFrom,
        Long dateTo,
        Long lastTxDateFrom,
        Long lastTxDateTo,
        String status) {
      this.currencyPair = currencyPair;
      this.limit = limit;
      this.dateTo = dateTo;
      this.dateFrom = dateFrom;
      this.lastTxDateTo = lastTxDateTo;
      this.lastTxDateFrom = lastTxDateFrom;
      this.status = status;
    }

    @Override
    public CurrencyPair getCurrencyPair() {
      return currencyPair;
    }

    @Override
    public void setCurrencyPair(CurrencyPair currencyPair) {
      this.currencyPair = currencyPair;
    }

    @Override
    public Date getStartTime() {
      return this.dateFrom == null ? null : new Date(this.dateFrom);
    }

    @Override
    public void setStartTime(Date startTime) {
      this.dateFrom = startTime.getTime();
    }

    @Override
    public Date getEndTime() {
      return this.dateTo == null ? null : new Date(this.dateTo);
    }

    @Override
    public void setEndTime(Date endTime) {
      this.dateTo = endTime.getTime();
    }

    @Override
    public Integer getLimit() {
      return limit;
    }

    @Override
    public void setLimit(Integer limit) {
      this.limit = limit;
    }
  }
}
