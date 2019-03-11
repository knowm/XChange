package org.knowm.xchange.dto.marketdata;

import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.knowm.xchange.dto.trade.FixedRateLoanOrder;
import org.knowm.xchange.dto.trade.FloatingRateLoanOrder;

/** DTO representing the exchange loan order book */
public final class LoanOrderBook implements Serializable {

  private final List<FixedRateLoanOrder> fixedRateAsks;
  private final List<FixedRateLoanOrder> fixedRateBids;
  private final List<FloatingRateLoanOrder> floatingRateAsks;
  private final List<FloatingRateLoanOrder> floatingRateBids;
  private Date timestamp;

  /**
   * Constructor
   *
   * @param timestamp
   * @param fixedRateAsks
   * @param fixedRateBids
   * @param floatingRateAsks
   * @param floatingRateBids
   */
  public LoanOrderBook(
      Date timestamp,
      List<FixedRateLoanOrder> fixedRateAsks,
      List<FixedRateLoanOrder> fixedRateBids,
      List<FloatingRateLoanOrder> floatingRateAsks,
      List<FloatingRateLoanOrder> floatingRateBids) {

    this.timestamp = timestamp;
    this.fixedRateAsks = fixedRateAsks;
    this.fixedRateBids = fixedRateBids;
    this.floatingRateAsks = floatingRateAsks;
    this.floatingRateBids = floatingRateBids;
  }

  public Date getTimestamp() {

    return timestamp;
  }

  public List<FixedRateLoanOrder> getFixedRateAsks() {

    return fixedRateAsks;
  }

  public List<FixedRateLoanOrder> getFixedRateBids() {

    return fixedRateBids;
  }

  public List<FloatingRateLoanOrder> getFloatingRateAsks() {

    return floatingRateAsks;
  }

  public List<FloatingRateLoanOrder> getFloatingRateBids() {

    return floatingRateBids;
  }

  public void update(FixedRateLoanOrder updatedLoanOrder) {

    Iterator<FixedRateLoanOrder> it;

    switch (updatedLoanOrder.getType()) {
      case ASK:
        it = fixedRateAsks.iterator();
        while (it.hasNext()) {
          FixedRateLoanOrder order = it.next();
          if (order.getRate().equals(updatedLoanOrder.getRate())
              && order.getDayPeriod() == updatedLoanOrder.getDayPeriod()) {
            it.remove();
            break;
          }
        }
        fixedRateAsks.add(updatedLoanOrder);
        Collections.sort(fixedRateAsks);
        break;
      case BID:
        it = fixedRateBids.iterator();
        while (it.hasNext()) {
          FixedRateLoanOrder order = it.next();
          if (order.getRate().equals(updatedLoanOrder.getRate())
              && order.getDayPeriod() == updatedLoanOrder.getDayPeriod()) {
            it.remove();
            break;
          }
        }
        fixedRateBids.add(updatedLoanOrder);
        Collections.sort(fixedRateBids);
        break;
      default:
        break;
    }

    updateTimestamp(updatedLoanOrder.getTimestamp());
  }

  public void update(FloatingRateLoanOrder updatedLoanOrder) {

    Iterator<FloatingRateLoanOrder> it;
    boolean rateChanged = false;

    switch (updatedLoanOrder.getType()) {
      case ASK:
        it = floatingRateAsks.iterator();
        while (it.hasNext()) {
          FloatingRateLoanOrder order = it.next();
          if (order.getDayPeriod() == updatedLoanOrder.getDayPeriod()) {
            it.remove();
          }
          // check if the rate has changed and whether we know if it has changed
          if (!order.getRate().equals(updatedLoanOrder.getRate()) && !rateChanged) {
            rateChanged = true;
          }
          break;
        }

        floatingRateAsks.add(updatedLoanOrder);
        Collections.sort(floatingRateAsks);
        break;
      case BID:
        it = floatingRateBids.iterator();
        while (it.hasNext()) {
          FloatingRateLoanOrder order = it.next();
          if (order.getDayPeriod() == updatedLoanOrder.getDayPeriod()) {
            it.remove();
          }
          // check if the rate has changed and whether we know if it has changed
          if (!order.getRate().equals(updatedLoanOrder.getRate()) && !rateChanged) {
            rateChanged = true;
          }
          break;
        }

        floatingRateBids.add(updatedLoanOrder);
        Collections.sort(fixedRateBids);
        break;
      default:
        break;
    }

    if (rateChanged) {
      for (FloatingRateLoanOrder order : floatingRateAsks) {
        order.setRate(updatedLoanOrder.getRate());
      }
      for (FloatingRateLoanOrder order : floatingRateBids) {
        order.setRate(updatedLoanOrder.getRate());
      }
    }

    updateTimestamp(updatedLoanOrder.getTimestamp());
  }

  private void updateTimestamp(Date timestamp) {

    this.timestamp = timestamp;
  }
}
