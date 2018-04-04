package org.knowm.xchange.bibox.service;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bibox.BiboxException;
import org.knowm.xchange.bibox.dto.BiboxAdapters;
import org.knowm.xchange.bibox.dto.BiboxCommand;
import org.knowm.xchange.bibox.dto.BiboxCommands;
import org.knowm.xchange.bibox.dto.BiboxMultipleResponses;
import org.knowm.xchange.bibox.dto.BiboxSingleResponse;
import org.knowm.xchange.bibox.dto.trade.BiboxAccountType;
import org.knowm.xchange.bibox.dto.trade.BiboxCancelTradeCommand;
import org.knowm.xchange.bibox.dto.trade.BiboxOrderHistoryCommand;
import org.knowm.xchange.bibox.dto.trade.BiboxOrderPendingListCommand;
import org.knowm.xchange.bibox.dto.trade.BiboxOrderPendingListCommandBody;
import org.knowm.xchange.bibox.dto.trade.BiboxOrderSide;
import org.knowm.xchange.bibox.dto.trade.BiboxOrderType;
import org.knowm.xchange.bibox.dto.trade.BiboxOrders;
import org.knowm.xchange.bibox.dto.trade.BiboxTradeCommand;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.exceptions.ExchangeException;

/** @author odrotleff */
public class BiboxTradeServiceRaw extends BiboxBaseService {

  protected BiboxTradeServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public Integer placeBiboxLimitOrder(LimitOrder limitOrder) {
    try {
      BiboxTradeCommand cmd =
          new BiboxTradeCommand(
              BiboxAdapters.toBiboxPair(limitOrder.getCurrencyPair()),
              BiboxAccountType.REGULAR.asInt(),
              BiboxOrderType.LIMIT_ORDER.asInt(),
              BiboxOrderSide.fromOrderType(limitOrder.getType()).asInt(),
              true,
              limitOrder.getLimitPrice(),
              limitOrder.getOriginalAmount(),
              null);
      BiboxSingleResponse<Integer> response =
          bibox.trade(BiboxCommands.of(cmd).json(), apiKey, signatureCreator);
      throwErrors(response);
      return response.get().getResult();
    } catch (BiboxException e) {
      throw new ExchangeException(e.getMessage());
    }
  }

  public Integer placeBiboxMarketOrder(MarketOrder marketOrder) {
    try {
      BiboxTradeCommand cmd =
          new BiboxTradeCommand(
              BiboxAdapters.toBiboxPair(marketOrder.getCurrencyPair()),
              BiboxAccountType.REGULAR.asInt(),
              BiboxOrderType.MARKET_ORDER.asInt(),
              BiboxOrderSide.fromOrderType(marketOrder.getType()).asInt(),
              true,
              null,
              marketOrder.getOriginalAmount(),
              null);
      BiboxSingleResponse<Integer> response =
          bibox.trade(BiboxCommands.of(cmd).json(), apiKey, signatureCreator);
      throwErrors(response);
      return response.get().getResult();
    } catch (BiboxException e) {
      throw new ExchangeException(e.getMessage());
    }
  }

  public void cancelBiboxOrder(String orderId) {
    try {
      BiboxCancelTradeCommand cmd = new BiboxCancelTradeCommand(new BigInteger(orderId));
      BiboxSingleResponse<String> response =
          bibox.cancelTrade(BiboxCommands.of(cmd).json(), apiKey, signatureCreator);
      throwErrors(response);
    } catch (BiboxException e) {
      throw new ExchangeException(e.getMessage());
    }
  }

  public BiboxOrders getBiboxOpenOrders() {
    try {
      BiboxOrderPendingListCommandBody body =
          new BiboxOrderPendingListCommandBody(
              1, Integer.MAX_VALUE); // wonder if this actually works
      BiboxOrderPendingListCommand cmd = new BiboxOrderPendingListCommand(body);
      BiboxSingleResponse<BiboxOrders> response =
          bibox.orderPendingList(BiboxCommands.of(cmd).json(), apiKey, signatureCreator);
      throwErrors(response);
      return response.get().getResult();
    } catch (BiboxException e) {
      throw new ExchangeException(e.getMessage());
    }
  }

  public BiboxOrders getBiboxOrderHistory() {
    try {
      BiboxOrderPendingListCommandBody body =
          new BiboxOrderPendingListCommandBody(
              1, Integer.MAX_VALUE); // wonder if this actually works
      BiboxOrderHistoryCommand cmd = new BiboxOrderHistoryCommand(body);
      BiboxSingleResponse<BiboxOrders> response =
          bibox.orderPendingList(BiboxCommands.of(cmd).json(), apiKey, signatureCreator);
      throwErrors(response);
      return response.get().getResult();
    } catch (BiboxException e) {
      throw new ExchangeException(e.getMessage());
    }
  }

  public void cancelBiboxOrders(List<String> orderIds) {
    try {
      List<BiboxCommand<?>> cmds =
          orderIds
              .stream()
              .map(BigInteger::new)
              .map(BiboxCancelTradeCommand::new)
              .collect(Collectors.toList());
      BiboxMultipleResponses<String> response =
          bibox.cancelTrades(BiboxCommands.of(cmds).json(), apiKey, signatureCreator);
      throwErrors(response);
    } catch (BiboxException e) {
      throw new ExchangeException(e.getMessage());
    }
  }
}
