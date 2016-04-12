package org.knowm.xchange.huobi.dto.streaming.request.marketdata;

import java.util.HashSet;
import java.util.Set;

import org.knowm.xchange.huobi.dto.streaming.dto.Percent;
import org.knowm.xchange.huobi.dto.streaming.dto.Period;

/**
 * Message for subscribing to push.
 */
public class Message {

  private volatile Set<LastTimeLine> lastTimeLine;
  private volatile Set<LastKLine> lastKLine;
  private volatile Set<MarketDepthDiff> marketDepthDiff;
  private volatile Set<MarketDepthTopDiff> marketDepthTopDiff;
  private volatile Set<MarketDetail> marketDetail;
  private volatile Set<TradeDetail> tradeDetail;
  private volatile Set<MarketOverview> marketOverview;

  public Set<LastTimeLine> getLastTimeLine() {
    return lastTimeLine;
  }

  public void addLastTimeLine(LastTimeLine lastTimeLine) {
    if (this.lastTimeLine == null) {
      synchronized (this) {
        if (this.lastTimeLine == null) {
          this.lastTimeLine = new HashSet();
        }
      }
    }
    this.lastTimeLine.add(lastTimeLine);
  }

  public void addLastTimeLine(String symbolId, PushType pushType) {
    addLastTimeLine(new LastTimeLine(symbolId, pushType));
  }

  public Set<LastKLine> getLastKLine() {
    return lastKLine;
  }

  public void addLastKLine(LastKLine lastKLine) {
    if (this.lastKLine == null) {
      synchronized (this) {
        if (this.lastKLine == null) {
          this.lastKLine = new HashSet();
        }
      }
    }
    this.lastKLine.add(lastKLine);
  }

  public void addLastKLine(String symbolId, PushType pushType, Period period) {
    addLastKLine(new LastKLine(symbolId, pushType, period));
  }

  public Set<MarketDepthDiff> getMarketDepthDiff() {
    return marketDepthDiff;
  }

  public void addMarketDepthDiff(MarketDepthDiff marketDepthDiff) {
    if (this.marketDepthDiff == null) {
      synchronized (this) {
        if (this.marketDepthDiff == null) {
          this.marketDepthDiff = new HashSet();
        }
      }
    }
    this.marketDepthDiff.add(marketDepthDiff);
  }

  public void addMarketDepthDiff(String symbolId, PushType pushType, Percent percent) {
    addMarketDepthDiff(new MarketDepthDiff(symbolId, pushType, percent));
  }

  public Set<MarketDepthTopDiff> getMarketDepthTopDiff() {
    return marketDepthTopDiff;
  }

  public void addMarketDepthTopDiff(MarketDepthTopDiff marketDepthTopDiff) {
    if (this.marketDepthTopDiff == null) {
      synchronized (this) {
        if (this.marketDepthTopDiff == null) {
          this.marketDepthTopDiff = new HashSet();
        }
      }
    }
    this.marketDepthTopDiff.add(marketDepthTopDiff);
  }

  public void addMarketDepthTopDiff(String symbolId, PushType pushType) {
    addMarketDepthTopDiff(new MarketDepthTopDiff(symbolId, pushType));
  }

  public Set<MarketDetail> getMarketDetail() {
    return marketDetail;
  }

  public void addMarketDetail(MarketDetail marketDetail) {
    if (this.marketDetail == null) {
      synchronized (this) {
        if (this.marketDetail == null) {
          this.marketDetail = new HashSet();
        }
      }
    }
    this.marketDetail.add(marketDetail);
  }

  public void addMarketDetail(String symbolId, PushType pushType) {
    addMarketDetail(new MarketDetail(symbolId, pushType));
  }

  public Set<TradeDetail> getTradeDetail() {
    return tradeDetail;
  }

  public void addTradeDetail(TradeDetail tradeDetail) {
    if (this.tradeDetail == null) {
      synchronized (this) {
        if (this.tradeDetail == null) {
          this.tradeDetail = new HashSet();
        }
      }
    }
    this.tradeDetail.add(tradeDetail);
  }

  public void addTradeDetail(String symbolId, PushType pushType) {
    addTradeDetail(new TradeDetail(symbolId, pushType));
  }

  public Set<MarketOverview> getMarketOverview() {
    return marketOverview;
  }

  public void addMarketOverview(MarketOverview marketOverview) {
    if (this.marketOverview == null) {
      synchronized (this) {
        if (this.marketOverview == null) {
          this.marketOverview = new HashSet();
        }
      }
    }
    this.marketOverview.add(marketOverview);
  }

  public void addMarketOverview(String symbolId, PushType pushType) {
    addMarketOverview(new MarketOverview(symbolId, pushType));
  }

}
