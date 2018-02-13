package org.knowm.xchange.bibox.dto.trade;

import org.knowm.xchange.bibox.dto.BiboxCommand;

/**
 * @author odrotleff
 */
public class BiboxOrderPendingListCommand extends BiboxCommand<BiboxOrderPendingListCommandBody> {

  public BiboxOrderPendingListCommand(BiboxOrderPendingListCommandBody body) {
    super("orderpending/orderPendingList", body);
  }
}
