package info.bitrich.xchangestream.poloniex2.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import org.knowm.xchange.poloniex.dto.marketdata.PoloniexDepth;

/** Created by Lukas Zaoralek on 11.11.17. */
public class PoloniexOrderbook {
  public static BigDecimal zero = new BigDecimal(0);

  private SortedMap<BigDecimal, BigDecimal> asks;
  private SortedMap<BigDecimal, BigDecimal> bids;

  public PoloniexOrderbook(
      SortedMap<BigDecimal, BigDecimal> asks, SortedMap<BigDecimal, BigDecimal> bids) {
    this.asks = asks;
    this.bids = bids;
  }

  public void modify(OrderbookModifiedEvent modifiedEvent) {
    SortedMap<BigDecimal, BigDecimal> side = modifiedEvent.getType().equals("0") ? asks : bids;
    BigDecimal price = modifiedEvent.getPrice();
    BigDecimal volume = modifiedEvent.getVolume();

    side.remove(price);
    if (volume.compareTo(zero) != 0) {
      side.put(price, volume);
    }
  }

  private List<List<BigDecimal>> toPoloniexDepthLevels(SortedMap<BigDecimal, BigDecimal> side) {
    List<List<BigDecimal>> poloniexDepthSide = new ArrayList<>(side.size());
    for (Map.Entry<BigDecimal, BigDecimal> level : side.entrySet()) {
      List<BigDecimal> poloniexLevel = new ArrayList<>(2);
      poloniexLevel.add(level.getKey());
      poloniexLevel.add(level.getValue());
      poloniexDepthSide.add(poloniexLevel);
    }

    return poloniexDepthSide;
  }

  public PoloniexDepth toPoloniexDepth() {
    PoloniexDepth orderbook = new PoloniexDepth();

    List<List<BigDecimal>> poloniexDepthAsk = toPoloniexDepthLevels(asks);
    List<List<BigDecimal>> poloniexDepthBid = toPoloniexDepthLevels(bids);

    orderbook.setAsks(poloniexDepthAsk);
    orderbook.setBids(poloniexDepthBid);
    return orderbook;
  }
}
