package org.knowm.xchange.simulated;

import java.util.List;

import org.knowm.xchange.dto.trade.LimitOrder;

import lombok.Data;

@Data
public class Level3OrderBook {
  private final List<LimitOrder> asks;
  private final List<LimitOrder> bids;
}
