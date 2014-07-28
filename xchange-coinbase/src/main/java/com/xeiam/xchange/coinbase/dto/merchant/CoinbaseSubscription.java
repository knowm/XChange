/**
 * The MIT License
 * Copyright (c) 2012 Xeiam LLC http://xeiam.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.xeiam.xchange.coinbase.dto.merchant;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.xeiam.xchange.coinbase.dto.common.CoinbaseRecurringPaymentStatus;
import com.xeiam.xchange.coinbase.dto.merchant.CoinbaseButton.CoinbaseButtonInfo;
import com.xeiam.xchange.utils.jackson.ISO8601DateDeserializer;

/**
 * @author jamespedwards42
 */
public class CoinbaseSubscription {

  private final CoinbaseSubscriptionInfo subscription;

  private CoinbaseSubscription(@JsonProperty("recurring_payment") final CoinbaseSubscriptionInfo subscription) {

    this.subscription = subscription;
  }

  public String getId() {

    return subscription.getId();
  }

  public Date getCreatedAt() {

    return subscription.getCreatedAt();
  }

  public CoinbaseRecurringPaymentStatus getStatus() {

    return subscription.getStatus();
  }

  public String getCustom() {

    return subscription.getCustom();
  }

  public CoinbaseButton getButton() {

    return subscription.getButton();
  }

  @Override
  public String toString() {

    return "CoinbaseSubscription [subscription=" + subscription + "]";
  }

  private static final class CoinbaseSubscriptionInfo {

    private final String id;
    private final Date createdAt;
    private final CoinbaseRecurringPaymentStatus status;
    private final String custom;
    private final CoinbaseButton button;

    private CoinbaseSubscriptionInfo(@JsonProperty("id") final String id, @JsonProperty("created_at") @JsonDeserialize(using = ISO8601DateDeserializer.class) final Date createdAt,
        @JsonProperty("status") final CoinbaseRecurringPaymentStatus status, @JsonProperty("custom") final String custom, @JsonProperty("button") final CoinbaseButtonInfo button) {

      this.id = id;
      this.createdAt = createdAt;
      this.status = status;
      this.custom = custom;
      this.button = new CoinbaseButton(button);
    }

    public String getId() {

      return id;
    }

    public Date getCreatedAt() {

      return createdAt;
    }

    public CoinbaseRecurringPaymentStatus getStatus() {

      return status;
    }

    public String getCustom() {

      return custom;
    }

    public CoinbaseButton getButton() {

      return button;
    }

    @Override
    public String toString() {

      return "CoinbaseSubscriptionInfo [id=" + id + ", createdAt=" + createdAt + ", status=" + status + ", custom=" + custom + ", button=" + button + "]";
    }
  }
}
