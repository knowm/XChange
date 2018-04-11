package org.knowm.xchange.mercadobitcoin.dto.trade;

import java.util.HashMap;
import java.util.Map;

/** @author Felipe Micaroni Lalli */
public class MercadoBitcoinCancelOrderResult
    extends HashMap<String, MercadoBitcoinUserOrdersEntry> {
  public MercadoBitcoinCancelOrderResult(int initialCapacity, float loadFactor) {
    super(initialCapacity, loadFactor);
  }

  public MercadoBitcoinCancelOrderResult(int initialCapacity) {
    super(initialCapacity);
  }

  public MercadoBitcoinCancelOrderResult() {}

  public MercadoBitcoinCancelOrderResult(
      Map<? extends String, ? extends MercadoBitcoinUserOrdersEntry> m) {
    super(m);
  }
}
