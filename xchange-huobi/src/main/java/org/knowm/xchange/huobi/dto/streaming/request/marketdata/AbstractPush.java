package org.knowm.xchange.huobi.dto.streaming.request.marketdata;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * The push subscription.
 */
public abstract class AbstractPush {

  private final String symbolId;
  private final PushType pushType;

  public AbstractPush(String symbolId, PushType pushType) {
    this.symbolId = symbolId;
    this.pushType = pushType;
  }

  public String getSymbolId() {
    return symbolId;
  }

  public PushType getPushType() {
    return pushType;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

}
