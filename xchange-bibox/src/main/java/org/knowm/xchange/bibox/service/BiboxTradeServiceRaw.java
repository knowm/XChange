package org.knowm.xchange.bibox.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bibox.dto.BiboxAdapters;
import org.knowm.xchange.bibox.dto.BiboxCommands;
import org.knowm.xchange.bibox.dto.trade.BiboxAccountType;
import org.knowm.xchange.bibox.dto.trade.BiboxOrderSide;
import org.knowm.xchange.bibox.dto.trade.BiboxOrderType;
import org.knowm.xchange.bibox.dto.trade.BiboxTradeCommand;
import org.knowm.xchange.dto.trade.LimitOrder;

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
}
