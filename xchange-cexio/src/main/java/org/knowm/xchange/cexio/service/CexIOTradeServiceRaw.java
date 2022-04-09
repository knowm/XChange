package org.knowm.xchange.cexio.service;

import static org.knowm.xchange.dto.Order.OrderType.BID;
import static org.knowm.xchange.utils.DateUtils.toUnixTimeNullSafe;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.cexio.dto.ArchivedOrdersRequest;
import org.knowm.xchange.cexio.dto.CexIOGetPositionRequest;
import org.knowm.xchange.cexio.dto.CexIOOpenPositionRequest;
import org.knowm.xchange.cexio.dto.CexIORequest;
import org.knowm.xchange.cexio.dto.CexioCancelReplaceOrderRequest;
import org.knowm.xchange.cexio.dto.CexioPlaceOrderRequest;
import org.knowm.xchange.cexio.dto.CexioSingleOrderIdRequest;
import org.knowm.xchange.cexio.dto.trade.CexIOArchivedOrder;
import org.knowm.xchange.cexio.dto.trade.CexIOCancelAllOrdersResponse;
import org.knowm.xchange.cexio.dto.trade.CexIOCancelReplaceOrderResponse;
import org.knowm.xchange.cexio.dto.trade.CexIOFullOrder;
import org.knowm.xchange.cexio.dto.trade.CexIOOpenOrder;
import org.knowm.xchange.cexio.dto.trade.CexIOOrder;
import org.knowm.xchange.cexio.dto.trade.CexIOOrderTransactionsResponse;
import org.knowm.xchange.cexio.dto.trade.CexIOOrderWithTransactions;
import org.knowm.xchange.cexio.dto.trade.CexioClosePosition;
import org.knowm.xchange.cexio.dto.trade.CexioClosePositionResponse;
import org.knowm.xchange.cexio.dto.trade.CexioOpenPosition;
import org.knowm.xchange.cexio.dto.trade.CexioOpenPositionResponse;
import org.knowm.xchange.cexio.dto.trade.CexioOpenPositionsResponse;
import org.knowm.xchange.cexio.dto.trade.CexioPosition;
import org.knowm.xchange.cexio.dto.trade.CexioPositionResponse;
import org.knowm.xchange.cexio.dto.trade.CexioPositionType;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.service.trade.params.*;

public class CexIOTradeServiceRaw extends CexIOBaseService {
  public CexIOTradeServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public List<CexIOOrder> getCexIOOpenOrders(CurrencyPair currencyPair) throws IOException {

    String tradableIdentifier = currencyPair.base.getCurrencyCode();
    String transactionCurrency = currencyPair.counter.getCurrencyCode();

    return cexIOAuthenticated
        .getOpenOrders(
            signatureCreator, tradableIdentifier, transactionCurrency, new CexIORequest())
        .getOpenOrders();
  }

  public List<CexIOOrder> getCexIOOpenOrders() throws IOException {
    return cexIOAuthenticated.getOpenOrders(signatureCreator, new CexIORequest()).getOpenOrders();
  }

  public CexIOOrder placeCexIOLimitOrder(LimitOrder limitOrder) throws IOException {

    CexIOOrder order =
        cexIOAuthenticated.placeOrder(
            signatureCreator,
            limitOrder.getCurrencyPair().base.getCurrencyCode(),
            limitOrder.getCurrencyPair().counter.getCurrencyCode(),
            new CexioPlaceOrderRequest(
                (limitOrder.getType() == BID ? CexIOOrder.Type.buy : CexIOOrder.Type.sell),
                limitOrder.getLimitPrice(),
                limitOrder.getOriginalAmount(),
                "limit"));
    if (order.getErrorMessage() != null) {
      throw new ExchangeException(order.getErrorMessage());
    }
    return order;
  }

  /*
  Presently, the exchange is designed in such way that placing market order we have to use amount either in base,
  or counter currency depending on order type.

  Example:
      CurrencyPair.BCH_USD, Order.OrderType.ASK, Amount = 0.02 (BCH)
      CurrencyPair.BCH_USD, Order.OrderType.BID, Amount = 20 (USD)

  Because of this non-standard behaviour we cannot place this function into common interface
  */
  public CexIOOrder placeCexIOMarketOrder(MarketOrder marketOrder) throws IOException {
    CexIOOrder order =
        cexIOAuthenticated.placeOrder(
            signatureCreator,
            marketOrder.getCurrencyPair().base.getCurrencyCode(),
            marketOrder.getCurrencyPair().counter.getCurrencyCode(),
            new CexioPlaceOrderRequest(
                (marketOrder.getType() == BID ? CexIOOrder.Type.buy : CexIOOrder.Type.sell),
                null,
                marketOrder.getOriginalAmount(),
                "market"));
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

  public CexIOOrderWithTransactions getOrderTransactions(String orderId) throws IOException {
    CexIOOrderTransactionsResponse response =
        cexIOAuthenticated.getOrderTransactions(
            signatureCreator, new CexioSingleOrderIdRequest(orderId));

    if (response.getError() != null) {
      throw new ExchangeException(response.getError());
    }

    return response.getData();
  }

  public List<CexioPosition> getOpenPositions(CurrencyPair currencyPair) throws IOException {
    CexioOpenPositionsResponse response =
        cexIOAuthenticated.getOpenPositions(
            signatureCreator,
            currencyPair.base.getSymbol(),
            currencyPair.counter.getSymbol(),
            new CexIORequest());
    if (!"ok".equalsIgnoreCase(response.getStatus())) {
      throw new ExchangeException(response.getEventName() + " " + response.getStatus());
    }
    return response.getPositions();
  }

  public CexioPosition getPosition(String positionId) throws IOException {
    CexioPositionResponse response =
        cexIOAuthenticated.getPosition(signatureCreator, new CexIOGetPositionRequest(positionId));
    if (!"ok".equalsIgnoreCase(response.getStatus())) {
      throw new ExchangeException(response.getEventName() + " " + response.getStatus());
    }
    return response.getPosition();
  }

  /**
   * @param currencyPair
   * @param amount total amount of product to buy using borrowed funds and user's funds
   * @param collateral currency of user funds used, may be one of currencies in the pair, default is
   *     second currency in the pair
   * @param leverage leverage ratio of total funds (user's and borrowed) to user's funds; for
   *     example - leverage=3 means - ratio total/user's=3:1, margin=33.(3)%, 1/3 is users, 2/3 are
   *     borrowed; Note that in UI it will be presented as 1/3
   * @param type position type. long - buying product, profitable if product price grows; short -
   *     selling product, profitable if product price falls;
   * @param anySlippage allows to open position at changed price
   * @param estimatedOpenPrice allows to open position at changed price
   * @param stopLossPrice price near which your position will be closed automatically in case of
   *     unfavorable market conditions
   * @return CexioPosition
   * @throws IOException
   */
  public CexioOpenPosition openCexIOPosition(
      CurrencyPair currencyPair,
      BigDecimal amount,
      Currency collateral,
      Integer leverage,
      CexioPositionType type,
      Boolean anySlippage,
      BigDecimal estimatedOpenPrice,
      BigDecimal stopLossPrice)
      throws IOException {
    CexioOpenPositionResponse order =
        cexIOAuthenticated.openPosition(
            signatureCreator,
            currencyPair.base.getCurrencyCode(),
            currencyPair.counter.getCurrencyCode(),
            new CexIOOpenPositionRequest(
                currencyPair.base.getCurrencyCode(),
                amount,
                collateral.getCurrencyCode(),
                leverage,
                type,
                anySlippage,
                estimatedOpenPrice,
                stopLossPrice));
    if (order.getErrorMessage() != null) {
      throw new ExchangeException(order.getErrorMessage());
    }
    return order.getPosition();
  }

  public CexioClosePosition closePosition(CurrencyPair currencyPair, String id) throws IOException {
    CexioClosePositionResponse response =
        cexIOAuthenticated.closePosition(
            signatureCreator,
            currencyPair.base.getSymbol(),
            currencyPair.counter.getSymbol(),
            new CexIOGetPositionRequest(id));
    if (!"ok".equalsIgnoreCase(response.getStatus())) {
      throw new ExchangeException(response.getEventName() + " " + response.getStatus());
    }
    return response.getPosition();
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
