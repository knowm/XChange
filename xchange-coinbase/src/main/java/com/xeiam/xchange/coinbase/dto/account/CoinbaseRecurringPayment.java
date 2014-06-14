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
package com.xeiam.xchange.coinbase.dto.account;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.xeiam.xchange.coinbase.dto.common.CoinbaseRecurringPaymentStatus;
import com.xeiam.xchange.coinbase.dto.common.CoinbaseRecurringPaymentType;
import com.xeiam.xchange.coinbase.dto.common.CoinbaseRepeat;
import com.xeiam.xchange.coinbase.dto.marketdata.CoinbaseMoney;
import com.xeiam.xchange.coinbase.dto.serialization.CoinbaseMoneyDeserializer;
import com.xeiam.xchange.utils.jackson.ISO8601DateDeserializer;

/**
 * @author jamespedwards42
 */
public class CoinbaseRecurringPayment {

  private final CoinbaseRecurringPaymentInfo recurringPayment;

  private CoinbaseRecurringPayment(@JsonProperty("recurring_payment") final CoinbaseRecurringPaymentInfo recurringPayment) {

    this.recurringPayment = recurringPayment;
  }

  public String getId() {

    return recurringPayment.getId();
  }

  public CoinbaseRecurringPaymentType getType() {

    return recurringPayment.getType();
  }

  public CoinbaseRecurringPaymentStatus getStatus() {

    return recurringPayment.getStatus();
  }

  public Date getCreatedAt() {

    return recurringPayment.getCreatedAt();
  }

  public String getTo() {

    return recurringPayment.getTo();
  }

  public String getFrom() {

    return recurringPayment.getFrom();
  }

  public String getStartType() {

    return recurringPayment.getStartType();
  }

  public int getTimes() {

    return recurringPayment.getTimes();
  }

  public int getTimesRun() {

    return recurringPayment.getTimesRun();
  }

  public CoinbaseRepeat getRepeat() {

    return recurringPayment.getRepeat();
  }

  public Date getLastRun() {

    return recurringPayment.getLastRun();
  }

  public Date getNextRun() {

    return recurringPayment.getNextRun();
  }

  public String getNotes() {

    return recurringPayment.getNotes();
  }

  public String getDescription() {

    return recurringPayment.getDescription();
  }

  public CoinbaseMoney getAmount() {

    return recurringPayment.getAmount();
  }

  @Override
  public String toString() {

    return "CoinbaseRecurringPayment [recurringPayment=" + recurringPayment + "]";
  }

  private static class CoinbaseRecurringPaymentInfo {

    private final String id;
    private final CoinbaseRecurringPaymentType type;
    private final CoinbaseRecurringPaymentStatus status;
    private final Date createdAt;
    private final String to;
    private final String from;
    private final String startType;
    private final int times;
    private final int timesRun;
    private final CoinbaseRepeat repeat;
    private final Date lastRun;
    private final Date nextRun;
    private final String notes;
    private final String description;
    private final CoinbaseMoney amount;

    private CoinbaseRecurringPaymentInfo(@JsonProperty("id") final String id, @JsonProperty("type") final CoinbaseRecurringPaymentType type,
        @JsonProperty("status") final CoinbaseRecurringPaymentStatus status, @JsonProperty("created_at") @JsonDeserialize(using = ISO8601DateDeserializer.class) final Date createdAt,
        @JsonProperty("to") final String to, @JsonProperty("from") final String from, @JsonProperty("start_type") final String startType, @JsonProperty("times") final int times,
        @JsonProperty("times_run") final int timesRun, @JsonProperty("repeat") final CoinbaseRepeat repeat, @JsonProperty("last_run") final Date lastRun, @JsonProperty("next_run") final Date nextRun,
        @JsonProperty("notes") final String notes, @JsonProperty("description") final String description,
        @JsonProperty("amount") @JsonDeserialize(using = CoinbaseMoneyDeserializer.class) final CoinbaseMoney amount) {

      this.id = id;
      this.type = type;
      this.status = status;
      this.createdAt = createdAt;
      this.to = to;
      this.from = from;
      this.startType = startType;
      this.times = times;
      this.timesRun = timesRun;
      this.repeat = repeat;
      this.lastRun = lastRun;
      this.nextRun = nextRun;
      this.notes = notes;
      this.description = description;
      this.amount = amount;
    }

    public String getId() {

      return id;
    }

    public CoinbaseRecurringPaymentType getType() {

      return type;
    }

    public CoinbaseRecurringPaymentStatus getStatus() {

      return status;
    }

    public Date getCreatedAt() {

      return createdAt;
    }

    public String getTo() {

      return to;
    }

    public String getFrom() {

      return from;
    }

    public String getStartType() {

      return startType;
    }

    public int getTimes() {

      return times;
    }

    public int getTimesRun() {

      return timesRun;
    }

    public CoinbaseRepeat getRepeat() {

      return repeat;
    }

    public Date getLastRun() {

      return lastRun;
    }

    public Date getNextRun() {

      return nextRun;
    }

    public String getNotes() {

      return notes;
    }

    public String getDescription() {

      return description;
    }

    public CoinbaseMoney getAmount() {

      return amount;
    }

    @Override
    public String toString() {

      return "CoinbaseRecurringPaymentInfo [id=" + id + ", type=" + type + ", status=" + status + ", createdAt=" + createdAt + ", to=" + to + ", from=" + from + ", startType=" + startType
          + ", times=" + times + ", timesRun=" + timesRun + ", repeat=" + repeat + ", lastRun=" + lastRun + ", nextRun=" + nextRun + ", notes=" + notes + ", description=" + description + ", amount="
          + amount + "]";
    }

  }
}
