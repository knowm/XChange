package org.knowm.xchange.huobi.dto.streaming.response.payload;

import org.apache.commons.lang3.builder.ToStringBuilder;

public abstract class AbstractPayload implements Payload {

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

}
