package com.xeiam.xchange.ripple.service.polling.params;

/**
 * Trade history queries (notifications and order detail) will continue until a trade with a hash matching this is found.
 */
public interface RippleTradeHistoryHashLimit {

  public void setHashLimit(final String value);

  public String getHashLimit();
}
