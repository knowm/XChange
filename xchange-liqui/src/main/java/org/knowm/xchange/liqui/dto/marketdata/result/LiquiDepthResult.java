package org.knowm.xchange.liqui.dto.marketdata.result;

import java.util.Map;

import org.knowm.xchange.liqui.dto.marketdata.LiquiDepth;
import org.knowm.xchange.liqui.dto.marketdata.LiquiResult;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LiquiDepthResult extends LiquiResult<Map<String, LiquiDepth>> {

  @JsonCreator
  public LiquiDepthResult(final Map<String, LiquiDepth> orders) {
    super(orders, null);
  }
}
