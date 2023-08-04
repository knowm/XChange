package info.bitrich.xchangestream.binancefuture.dto;

import java.util.Date;
import java.util.List;

public class BinanceFuturesAccountUpdateRaw {

  private final Date eventTime;
  private final long transaction;
  private final String eventReason;
  private final List<BinanceFuturesBalance> balances;
  private final List<BinanceFuturesPosition> positions;

  public BinanceFuturesAccountUpdateRaw(BinanceFuturesAccountUpdateTransaction transaction) {
    this.eventTime = transaction.getEventTime();
    this.transaction = transaction.getTransaction();
    this.eventReason = transaction.getUpdateData().getEventReasonType();
    this.balances = transaction.toBalanceList();
    this.positions = transaction.toPositionList();
  }

  public Date getEventTime() {
    return eventTime;
  }

  public long getTransaction() {
    return transaction;
  }

  public String getEventReason() {
    return eventReason;
  }

  public List<BinanceFuturesBalance> getBalances() {
    return balances;
  }

  public List<BinanceFuturesPosition> getPositions() {
    return positions;
  }

  @Override
  public String toString() {
    return "BinanceFuturesAccountUpdateRaw [eventTime=" + eventTime + ", transaction=" + transaction + ", eventReason="
        + eventReason + ", balances=" + balances + ", positions=" + positions + "]";
  }
}
