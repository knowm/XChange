package org.knowm.xchange.mercadobitcoin.dto.trade;

import java.util.HashMap;
import java.util.Map;

/** @author Felipe Micaroni Lalli */
public class MercadoBitcoinPlaceLimitOrderResult
    extends HashMap<String, MercadoBitcoinUserOrdersEntry> {
  public MercadoBitcoinPlaceLimitOrderResult(int initialCapacity, float loadFactor) {
    super(initialCapacity, loadFactor);
  }

  public MercadoBitcoinPlaceLimitOrderResult(int initialCapacity) {
    super(initialCapacity);
  }

  public MercadoBitcoinPlaceLimitOrderResult() {}

  public MercadoBitcoinPlaceLimitOrderResult(
      Map<? extends String, ? extends MercadoBitcoinUserOrdersEntry> m) {
    super(m);
  }
}
