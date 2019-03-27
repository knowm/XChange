/** Copyright 2019 Mek Global Limited. */
package org.knowm.xchange.kucoin.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.math.BigDecimal;
import lombok.Data;

/**
 * @author yi.yang
 * @since 2018/12/26.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SymbolTickResponse {

  private String symbol;

  private BigDecimal changeRate;

  private BigDecimal changePrice;

  private BigDecimal open;

  private BigDecimal close;

  private BigDecimal high;

  private BigDecimal low;

  private BigDecimal vol;

  private BigDecimal volValue;

  private BigDecimal last;

  private BigDecimal buy;

  private BigDecimal sell;

  private long time;
}
