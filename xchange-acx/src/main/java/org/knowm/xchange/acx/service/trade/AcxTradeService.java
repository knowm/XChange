package org.knowm.xchange.acx.service.trade;

import static org.knowm.xchange.acx.utils.AcxUtils.getAcxMarket;

import java.io.IOException;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.knowm.xchange.acx.AcxApi;
import org.knowm.xchange.acx.AcxMapper;
import org.knowm.xchange.acx.AcxSignatureCreator;
import org.knowm.xchange.acx.dto.AcxTrade;
import org.knowm.xchange.acx.dto.marketdata.AcxOrder;
import org.knowm.xchange.acx.utils.AcxUtils;
import org.knowm.xchange.acx.utils.ArgUtils;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.trade.*;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.*;
import org.knowm.xchange.service.trade.params.orders.DefaultOpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import si.mazi.rescu.SynchronizedValueFactory;

public class AcxTradeService implements TradeService {
  private static final Logger logger = LoggerFactory.getLogger(AcxTradeService.class);
  private final AcxApi api;
  private final AcxMapper mapper;
  private final AcxSignatureCreator signatureCreator;
  private final String accessKey;
  private final SynchronizedValueFactory<Long> nonceFactory;

  public AcxTradeService(
      SynchronizedValueFactory<Long> nonceFactory,
      AcxApi api,
      AcxMapper mapper,
      AcxSignatureCreator signatureCreator,
      String accessKey) {
    this.api = api;
    this.mapper = mapper;
    this.signatureCreator = signatureCreator;
    this.accessKey = accessKey;
    this.nonceFactory = nonceFactory;
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {
    return getOpenOrders(createOpenOrdersParams());
  }

  @Override
  public OpenOrders getOpenOrders(OpenOrdersParams params) throws IOException {
    long tonce = nonceFactory.createValue();
    OpenOrdersParamCurrencyPair param = ArgUtils.tryCast(params, OpenOrdersParamCurrencyPair.class);
    CurrencyPair currencyPair = param.getCurrencyPair();
    List<AcxOrder> orders =
        api.getOrders(accessKey, tonce, getAcxMarket(currencyPair), signatureCreator);
    return new OpenOrders(mapper.mapOrders(currencyPair, orders));
  }

  @Override
  public DefaultOpenOrdersParamCurrencyPair createOpenOrdersParams() {
    return new DefaultOpenOrdersParamCurrencyPair();
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {
    long tonce = nonceFactory.createValue();
    String market = getAcxMarket(limitOrder.getCurrencyPair());
    String side = mapper.getOrderType(limitOrder.getType());
    String volume = limitOrder.getOriginalAmount().setScale(2, RoundingMode.DOWN).toPlainString();
    String price = limitOrder.getLimitPrice().setScale(4, RoundingMode.DOWN).toPlainString();
    AcxOrder order =
        api.createOrder(accessKey, tonce, market, side, volume, price, "limit", signatureCreator);
    return order.id;
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {
    long tonce = nonceFactory.createValue();
    AcxOrder order = api.cancelOrder(accessKey, tonce, orderId, signatureCreator);
    return order != null;
  }

  @Override
  public Collection<Order> getOrder(String... orderIds) {
    return Stream.of(orderIds)
        .flatMap(
            orderId -> {
              try {
                long tonce = System.currentTimeMillis();
                return Stream.of(
                    api.getOrder(accessKey, tonce, Long.valueOf(orderId), signatureCreator));
              } catch (Exception e) {

                throw new RuntimeException("Could not retrieve ACX order with id " + orderId, e);
              }
            })
        .map(order -> mapper.mapOrder(AcxUtils.getCurrencyPair(order.market), order))
        .collect(Collectors.toList());
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {
    if (!(params instanceof AcxTradeHistoryParams)) {
      throw new IllegalArgumentException("Invalid tradehistoryparams given");
    }
    long tonce = nonceFactory.createValue();
    AcxTradeHistoryParams acxTradeHistoryParams = (AcxTradeHistoryParams) params;
    if (acxTradeHistoryParams.currencyPair == null) {
      throw new IllegalArgumentException("CurrencyPair cannot be null");
    }
    String timestamp = null;
    String startId = null;
    String endId = null;
    String limit = null;
    String order = null;
    if (acxTradeHistoryParams.endTime != null) {
      timestamp = String.valueOf(acxTradeHistoryParams.endTime.toInstant().getEpochSecond());
    }
    String market = getAcxMarket(acxTradeHistoryParams.currencyPair);
    if (acxTradeHistoryParams.startId != null) {
      startId = acxTradeHistoryParams.startId;
    }

    if (acxTradeHistoryParams.endId != null) {
      endId = acxTradeHistoryParams.endId;
    }
    if (acxTradeHistoryParams.order != null) {
      order = acxTradeHistoryParams.order.name();
    }
    if (acxTradeHistoryParams.limit != null) {
      limit = acxTradeHistoryParams.limit.toString();
    }

    List<AcxTrade> trades =
        api.getMyTrades(
            accessKey, tonce, signatureCreator, market, limit, order, startId, endId, timestamp);

    return new UserTrades(
        trades.stream().map(mapper::mapTrade).collect(Collectors.toList()),
        Trades.TradeSortType.SortByID);
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {
    return new AcxTradeHistoryParams();
  }

  @Override
  public void verifyOrder(LimitOrder limitOrder) {
    throw new NotAvailableFromExchangeException();
  }

  @Override
  public void verifyOrder(MarketOrder marketOrder) {
    throw new NotAvailableFromExchangeException();
  }

  public static class AcxTradeHistoryParams
      implements TradeHistoryParams,
          TradeHistoryParamLimit,
          TradeHistoryParamsTimeSpan,
          TradeHistoryParamsIdSpan,
          TradeHistoryParamsSorted,
          TradeHistoryParamCurrencyPair {
    private Integer limit = 50;
    private String startId;
    private String endId;
    private TradeHistoryParamsSorted.Order order = Order.desc;
    private Date startTime;
    private Date endTime;
    private CurrencyPair currencyPair;

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
      return startTime;
    }

    @Override
    public void setStartTime(Date startTime) {
      this.startTime = startTime;
    }

    @Override
    public Date getEndTime() {
      return endTime;
    }

    @Override
    public void setEndTime(Date endTime) {
      this.endTime = endTime;
    }

    @Override
    public Order getOrder() {
      return order;
    }

    @Override
    public void setOrder(Order order) {
      this.order = order;
    }

    @Override
    public String getStartId() {
      return startId;
    }

    public void setStartId(String startId) {
      this.startId = startId;
    }

    @Override
    public String getEndId() {
      return endId;
    }

    public void setEndId(String endId) {
      this.endId = endId;
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
