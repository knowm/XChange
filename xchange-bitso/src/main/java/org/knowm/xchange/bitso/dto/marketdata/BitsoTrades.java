package org.knowm.xchange.bitso.dto.marketdata;

import java.math.BigDecimal;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/** DTO for Bitso API v3 trades endpoint */
@Value
@Jacksonized
@Builder
public class BitsoTrades {

  private Boolean success;

  private List<BitsoTrade> payload;

  @Value
  @Jacksonized
  @Builder
  public static class BitsoTrade {

    private String book;

    private String createdAt;

    private BigDecimal amount;

    private String makerSide;

    private BigDecimal price;

    private Long tid;
  }
}
