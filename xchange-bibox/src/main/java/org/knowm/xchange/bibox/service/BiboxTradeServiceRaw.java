package org.knowm.xchange.bibox.service;

import java.math.BigInteger;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bibox.dto.BiboxAdapters;
import org.knowm.xchange.bibox.dto.BiboxCommands;
import org.knowm.xchange.bibox.dto.trade.BiboxAccountType;
import org.knowm.xchange.bibox.dto.trade.BiboxCancelTradeCommand;
import org.knowm.xchange.bibox.dto.trade.BiboxOrderHistoryCommand;
import org.knowm.xchange.bibox.dto.trade.BiboxOrders;
import org.knowm.xchange.bibox.dto.trade.BiboxOrderPendingListCommand;
import org.knowm.xchange.bibox.dto.trade.BiboxOrderPendingListCommandBody;
import org.knowm.xchange.bibox.dto.trade.BiboxOrderSide;
import org.knowm.xchange.bibox.dto.trade.BiboxOrderType;
import org.knowm.xchange.bibox.dto.trade.BiboxTradeCommand;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;

/**
 * @author odrotleff
 */
public class BiboxTradeServiceRaw extends BiboxBaseService {

  protected BiboxTradeServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public Integer placeBiboxLimitOrder(LimitOrder limitOrder) {
    BiboxTradeCommand cmd = new BiboxTradeCommand(
        BiboxAdapters.toBiboxPair(limitOrder.getCurrencyPair()),
        BiboxAccountType.REGULAR.asInt(),
        BiboxOrderType.LIMIT_ORDER.asInt(),
        BiboxOrderSide.fromOrderType(limitOrder.getType()).asInt(),
        true,
        limitOrder.getLimitPrice(),
        limitOrder.getOriginalAmount(),
        null);
    return bibox.trade(BiboxCommands.of(cmd).json(), apiKey, signatureCreator).get().getResult();
  }

  public Integer placeBiboxMarketOrder(MarketOrder marketOrder) {
    BiboxTradeCommand cmd = new BiboxTradeCommand(
        BiboxAdapters.toBiboxPair(marketOrder.getCurrencyPair()),
        BiboxAccountType.REGULAR.asInt(),
        BiboxOrderType.MARKET_ORDER.asInt(),
        BiboxOrderSide.fromOrderType(marketOrder.getType()).asInt(),
        true,
        null,
        marketOrder.getOriginalAmount(),
        null);
    return bibox.trade(BiboxCommands.of(cmd).json(), apiKey, signatureCreator).get().getResult();
  }

  public void cancelBiboxOrder(String orderId) {
    BiboxCancelTradeCommand cmd = new BiboxCancelTradeCommand(new BigInteger(orderId));
    bibox.cancelTrade(BiboxCommands.of(cmd).json(), apiKey, signatureCreator);
  }

  public BiboxOrders getBiboxOpenOrders() {
    BiboxOrderPendingListCommandBody body = new BiboxOrderPendingListCommandBody(1, Integer.MAX_VALUE); // wonder if this actually works
    BiboxOrderPendingListCommand cmd = new BiboxOrderPendingListCommand(body);
    return bibox.orderPendingList(BiboxCommands.of(cmd).json(), apiKey, signatureCreator).get().getResult();
  }

  public BiboxOrders getBiboxOrderHistory() {
    BiboxOrderPendingListCommandBody body = new BiboxOrderPendingListCommandBody(1, Integer.MAX_VALUE); // wonder if this actually works
    BiboxOrderHistoryCommand cmd = new BiboxOrderHistoryCommand(body);
    return bibox.orderPendingList(BiboxCommands.of(cmd).json(), apiKey, signatureCreator).get().getResult();
  }
}
