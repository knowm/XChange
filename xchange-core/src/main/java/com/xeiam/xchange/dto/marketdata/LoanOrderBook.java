/**
 * Copyright (C) 2012 - 2014 Xeiam LLC http://xeiam.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.xeiam.xchange.dto.marketdata;

import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.xeiam.xchange.dto.trade.FixedRateLoanOrder;
import com.xeiam.xchange.dto.trade.FloatingRateLoanOrder;

/**
 * DTO representing the exchange loan order book
 */
public final class LoanOrderBook {

  private Date timestamp;
  private final List<FixedRateLoanOrder> fixedRateAsks;
  private final List<FixedRateLoanOrder> fixedRateBids;
  private final List<FloatingRateLoanOrder> floatingRateAsks;
  private final List<FloatingRateLoanOrder> floatingRateBids;
  
  public LoanOrderBook(Date timestamp, List<FixedRateLoanOrder> fixedRateAsks, List<FixedRateLoanOrder> fixedRateBids, List<FloatingRateLoanOrder> floatingRateAsks, List<FloatingRateLoanOrder> floatingRateBids) {
    
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
        if (order.getRate().equals(updatedLoanOrder.getRate()) && order.getDayPeriod() == updatedLoanOrder.getDayPeriod()) {
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
        if (order.getRate().equals(updatedLoanOrder.getRate()) && order.getDayPeriod() == updatedLoanOrder.getDayPeriod()) {
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
