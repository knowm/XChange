package org.knowm.xchange.mercadobitcoin.dto.trade;

import java.util.HashMap;
import java.util.Map;

/** @author Felipe Micaroni Lalli */
public class MercadoBitcoinUserOrders extends HashMap<String, MercadoBitcoinUserOrdersEntry> {
  public MercadoBitcoinUserOrders(int initialCapacity, float loadFactor) {
    super(initialCapacity, loadFactor);
  }

  public MercadoBitcoinUserOrders(int initialCapacity) {
    super(initialCapacity);
  }

  public MercadoBitcoinUserOrders() {}

  public MercadoBitcoinUserOrders(
      Map<? extends String, ? extends MercadoBitcoinUserOrdersEntry> m) {
    super(m);
  }
}
