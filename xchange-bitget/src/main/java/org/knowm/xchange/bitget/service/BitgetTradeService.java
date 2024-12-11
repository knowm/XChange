package org.knowm.xchange.bitget.service;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.Validate;
import org.knowm.xchange.bitget.BitgetAdapters;
import org.knowm.xchange.bitget.BitgetErrorAdapter;
import org.knowm.xchange.bitget.BitgetExchange;
import org.knowm.xchange.bitget.dto.BitgetException;
import org.knowm.xchange.bitget.dto.trade.BitgetOrderInfoDto;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.Trades.TradeSortType;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.orders.DefaultQueryOrderParam;
import org.knowm.xchange.service.trade.params.orders.OrderQueryParams;

public class BitgetTradeService extends BitgetTradeServiceRaw implements TradeService {

  public BitgetTradeService(BitgetExchange exchange) {
    super(exchange);
  }

  @Override
  public Collection<Order> getOrder(OrderQueryParams... orderQueryParams) throws IOException {
    Validate.validState(orderQueryParams.length == 1);
    Validate.isInstanceOf(DefaultQueryOrderParam.class, orderQueryParams[0]);
    DefaultQueryOrderParam params = (DefaultQueryOrderParam) orderQueryParams[0];

    try {
      BitgetOrderInfoDto orderStatus = bitgetOrderInfoDto(params.getOrderId());
      return Collections.singletonList(BitgetAdapters.toOrder(orderStatus));
    } catch (BitgetException e) {
      throw BitgetErrorAdapter.adapt(e);
    }
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {
    try {
      List<UserTrade> userTradeList =
          bitgetFills(params).stream()
              .map(BitgetAdapters::toUserTrade)
              .collect(Collectors.toList());
      return new UserTrades(userTradeList, TradeSortType.SortByID);
    } catch (BitgetException e) {
      throw BitgetErrorAdapter.adapt(e);
    }
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {
    try {
      return createOrder(BitgetAdapters.toBitgetPlaceOrderDto(marketOrder)).getOrderId();
    } catch (BitgetException e) {
      throw BitgetErrorAdapter.adapt(e);
    }
  }
}
