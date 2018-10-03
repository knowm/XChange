package org.knowm.xchange.ripple.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.knowm.xchange.ripple.dto.RippleCommon;

public class RippleNotifications extends RippleCommon {

  private List<RippleNotification> notifications = new ArrayList<>();

  public List<RippleNotification> getNotifications() {
    return notifications;
  }

  public void setNotifications(final List<RippleNotification> value) {
    notifications = value;
  }

  @Override
  public String toString() {
    return String.format(
        "%s [success=%b notifications=%s]", getClass().getSimpleName(), isSuccess(), notifications);
  }

  public static class RippleNotification extends RippleCommon {
    @JsonProperty("account")
    private String account;

    @JsonProperty("type")
    private String type;

    @JsonProperty("direction")
    private String direction;

    @JsonProperty("result")
    private String result;

    @JsonProperty("timestamp")
    private Date timestamp;

    @JsonProperty("transaction_url")
    private String transactionURL;

    @JsonProperty("previous_hash")
    private String previousHash;

    @JsonProperty("previous_notification_url")
    private String previousNotificationUrl;

    @JsonProperty("next_hash")
    private String nextHash;

    @JsonProperty("next_notification_url")
    private String nextNotificationUrl;

    public String getAccount() {
      return account;
    }

    public void setAccount(String type) {
      this.account = type;
    }

    public String getType() {
      return type;
    }

    public void setType(String type) {
      this.type = type;
    }

    public String getDirection() {
      return direction;
    }

    public void setDirection(String direction) {
      this.direction = direction;
    }

    public String getResult() {
      return result;
    }

    public void setResult(String result) {
      this.result = result;
    }

    public Date getTimestamp() {
      return timestamp;
    }

    public void setTimestamp(Date timestamp) {
      this.timestamp = timestamp;
    }

    public String getTransactionUrl() {
      return transactionURL;
    }

    public void setTransactionUrl(String transactionUrl) {
      this.transactionURL = transactionUrl;
    }

    public String getPreviousHash() {
      return previousHash;
    }

    public void setPreviousHash(String previousHash) {
      this.previousHash = previousHash;
    }

    public String getPreviousNotificationUrl() {
      return previousNotificationUrl;
    }

    public void setPreviousNotificationUrl(String previousNotificationUrl) {
      this.previousNotificationUrl = previousNotificationUrl;
    }

    public String getNextHash() {
      return nextHash;
    }

    public void setNextHash(String nextHash) {
      this.nextHash = nextHash;
    }

    public String getNextNotificationUrl() {
      return nextNotificationUrl;
    }

    public void setNextNotificationUrl(String nextNotificationUrl) {
      this.nextNotificationUrl = nextNotificationUrl;
    }

    @Override
    public String toString() {
      return String.format(
          "%s [account=%s, type=%s, direction=%s, state=%s, result=%s, ledger=%s, hash=%s, timestamp=%s, transactionURL=%s, previousHash=%s, previousNotificationUrl=%s, nextHash=%s, nextNotificationUrl=%s]",
          getClass().getSimpleName(),
          account,
          type,
          direction,
          state,
          result,
          ledger,
          hash,
          timestamp,
          transactionURL,
          previousHash,
          previousNotificationUrl,
          nextHash,
          nextNotificationUrl);
    }
  }
}
