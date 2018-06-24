package org.knowm.xchange.bibox.dto.trade;

import java.math.BigInteger;
import org.knowm.xchange.bibox.dto.BiboxCommand;

/** @author odrotleff */
public class BiboxCancelTradeCommand extends BiboxCommand<BiboxCancelTradeCommandBody> {

  public BiboxCancelTradeCommand(BigInteger orderId) {
    super("orderpending/cancelTrade", new BiboxCancelTradeCommandBody(orderId));
  }
}
