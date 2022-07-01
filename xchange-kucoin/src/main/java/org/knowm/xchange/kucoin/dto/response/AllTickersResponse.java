package org.knowm.xchange.kucoin.dto.response;

import lombok.Data;

@Data
public class AllTickersResponse {
  private AllTickersTickerResponse[] ticker;
}
