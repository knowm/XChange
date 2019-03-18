/** Copyright 2019 Mek Global Limited. */
package org.knowm.xchange.kucoin.dto.response;

import java.util.List;
import lombok.Data;

/** Created by chenshiwei on 2019/1/19. */
@Data
public class OrderBook {

  /** [price, size] for aggregated, [orderId, price, size] for atomic */
  private List<List<String>> asks;

  /** [price, size] for aggregated, [orderId, price, size] for atomic */
  private List<List<String>> bids;
}
