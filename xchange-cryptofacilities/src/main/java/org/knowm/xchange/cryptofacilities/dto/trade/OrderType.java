package org.knowm.xchange.cryptofacilities.dto.trade;

/** either lmt for a limit order, post for a post-only limit order or stp for a stop order */
public enum OrderType {
  lmt,
  post,
  stp;
}
