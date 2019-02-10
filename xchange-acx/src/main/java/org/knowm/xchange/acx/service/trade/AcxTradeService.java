package org.knowm.xchange.acx.service.trade;

import static org.knowm.xchange.acx.utils.AcxUtils.getAcxMarket;

import java.io.IOException;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.knowm.xchange.acx.AcxApi;
import org.knowm.xchange.acx.AcxMapper;
import org.knowm.xchange.acx.AcxSignatureCreator;
import org.knowm.xchange.acx.dto.marketdata.AcxOrder;
import org.knowm.xchange.acx.utils.AcxUtils;
import org.knowm.xchange.acx.utils.ArgUtils;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.orders.DefaultOpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AcxTradeService implements TradeService {
  private static final Logger logger = LoggerFactory.getLogger(AcxTradeService.class);
  private final AcxApi api;
  private final AcxMapper mapper;
  private final AcxSignatureCreator signatureCreator;
  private final String accessKey;

  public AcxTradeService(
      AcxApi api, AcxMapper mapper, AcxSignatureCreator signatureCreator, String accessKey) {
    this.api = api;
    this.mapper = mapper;
    this.signatureCreator = signatureCreator;
    this.accessKey = accessKey;
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {
    return getOpenOrders(createOpenOrdersParams());
  }

  @Override
  public OpenOrders getOpenOrders(OpenOrdersParams params) throws IOException {
    long tonce = System.currentTimeMillis();
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
    long tonce = System.currentTimeMillis();
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
    long tonce = System.currentTimeMillis();
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
  public void verifyOrder(LimitOrder limitOrder) {
    throw new NotAvailableFromExchangeException();
  }

  @Override
  public void verifyOrder(MarketOrder marketOrder) {
    throw new NotAvailableFromExchangeException();
  }
}
