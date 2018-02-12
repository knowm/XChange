package org.knowm.xchange.bibox.dto.trade;

import org.knowm.xchange.bibox.dto.BiboxCommand;

/**
 * @author odrotleff
 */
public class BiboxOrderHistoryCommand extends BiboxCommand<BiboxOrderPendingListCommandBody> {

  public BiboxOrderHistoryCommand(BiboxOrderPendingListCommandBody body) {
    super("orderpending/orderHistoryList", body);
  }
}
