package org.knowm.xchange.abucoins.service;

import static org.knowm.xchange.utils.DateUtils.toUnixTimeNullSafe;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.abucoins.dto.AbucoinsOrderRequest;
import org.knowm.xchange.abucoins.dto.trade.AbucoinsOrder;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamLimit;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsTimeSpan;

public class AbucoinsTradeServiceRaw extends AbucoinsBaseService {

  public AbucoinsTradeServiceRaw(Exchange exchange) {
    super(exchange);
  }
  
  public AbucoinsOrder[] getAbucoinsOrders(AbucoinsOrderRequest request) throws IOException {
    AbucoinsOrder.Status status = null;
    String productID = null;
    if ( request != null ) {
      status = request.getStatus();
      productID = request.getProductID();
    }
          
    if ( status != null ) {
      switch (status) {
      default:
      case open:
      case done:
        break;
                          
      case pending:
      case rejected:
        throw new IllegalArgumentException("/orders only accepts status of 'open' or 'done' not " + status);
      }
    }
          
    AbucoinsOrder[] retVal = null;
    if ( status == null ) {
      if ( productID == null )
        retVal = abucoinsAuthenticated.getOrders(exchange.getExchangeSpecification().getApiKey(),
                                                 signatureCreator,
                                                 exchange.getExchangeSpecification().getPassword(),
                                                 signatureCreator.timestamp());
      else
        retVal = abucoinsAuthenticated.getOrdersByProductID(exchange.getExchangeSpecification().getApiKey(),
                                                            signatureCreator,
                                                            exchange.getExchangeSpecification().getPassword(),
                                                            signatureCreator.timestamp(),
                                                            productID);
    }
    else {
      if ( productID == null )
        retVal = abucoinsAuthenticated.getOrdersByStatus(exchange.getExchangeSpecification().getApiKey(),
                                                         signatureCreator,
                                                         exchange.getExchangeSpecification().getPassword(),
                                                         signatureCreator.timestamp(),
                                                         status.name());
      else
        retVal = abucoinsAuthenticated.getOrdersByStatusAndProductID(exchange.getExchangeSpecification().getApiKey(),
                                                                     signatureCreator,
                                                                     exchange.getExchangeSpecification().getPassword(),
                                                                     signatureCreator.timestamp(),
                                                                     status.name(),
                                                                     productID);                  
    }
    
    return retVal;
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
