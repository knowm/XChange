package org.knowm.xchange.latoken.service;

import org.knowm.xchange.dto.Order.IOrderFlags;

public interface LatokenOrderFlags extends IOrderFlags {

  /** Used in fields {@code clientOrderId} */
  String getClientId();
}
