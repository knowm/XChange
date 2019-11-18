package org.knowm.xchange.bibox.dto.trade;

import org.knowm.xchange.bibox.dto.BiboxCommand;

/** @author odrotleff */
public class BiboxCancelTradeCommand extends BiboxCommand<BiboxCancelTradeCommandBody> {

  public BiboxCancelTradeCommand(String orderId) {
    super("orderpending/cancelTrade", new BiboxCancelTradeCommandBody(orderId));
  }
}
