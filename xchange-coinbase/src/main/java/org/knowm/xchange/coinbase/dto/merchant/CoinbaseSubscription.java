package org.knowm.xchange.coinbase.dto.merchant;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.Date;
import org.knowm.xchange.coinbase.dto.common.CoinbaseRecurringPaymentStatus;
import org.knowm.xchange.coinbase.dto.merchant.CoinbaseButton.CoinbaseButtonInfo;
import org.knowm.xchange.utils.jackson.ISO8601DateDeserializer;

/** @author jamespedwards42 */
public class CoinbaseSubscription {

  private final CoinbaseSubscriptionInfo subscription;

  private CoinbaseSubscription(
      @JsonProperty("recurring_payment") final CoinbaseSubscriptionInfo subscription) {

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

    private CoinbaseSubscriptionInfo(
        @JsonProperty("id") final String id,
        @JsonProperty("created_at") @JsonDeserialize(using = ISO8601DateDeserializer.class)
            final Date createdAt,
        @JsonProperty("status") final CoinbaseRecurringPaymentStatus status,
        @JsonProperty("custom") final String custom,
        @JsonProperty("button") final CoinbaseButtonInfo button) {

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

      return "CoinbaseSubscriptionInfo [id="
          + id
          + ", createdAt="
          + createdAt
          + ", status="
          + status
          + ", custom="
          + custom
          + ", button="
          + button
          + "]";
    }
  }
}
