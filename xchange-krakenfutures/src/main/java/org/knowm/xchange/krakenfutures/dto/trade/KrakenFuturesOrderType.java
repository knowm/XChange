package org.knowm.xchange.krakenfutures.dto.trade;

/**
 * The order type:
 * * lmt - a limit order
 * * post - a post-only limit order
 * * mkt - an immediate-or-cancel order with 1% price protection
 * * stp - a stop order
 * * take_profit - a take profit order
 * * ioc - an immediate-or-cancel order
 * */
public enum KrakenFuturesOrderType {
  lmt,
  post,
  mkt,
  take_profit,
  ioc,
  stop,
  stp
}
