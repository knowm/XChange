package org.knowm.xchange.liqui.dto.marketdata.result;

import java.util.Map;

import org.knowm.xchange.liqui.dto.marketdata.LiquiPublicTrades;
import org.knowm.xchange.liqui.dto.marketdata.LiquiResult;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LiquiPublicTradesResult extends LiquiResult<Map<String, LiquiPublicTrades>> {

  @JsonCreator
  public LiquiPublicTradesResult(final Map<String, LiquiPublicTrades> trades) {
    super(trades, null);
  }
}
