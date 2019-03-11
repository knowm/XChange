package org.knowm.xchange.anx.v2.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Arrays;
import org.knowm.xchange.anx.v2.dto.ANXValue;

/** Data object representing a ANX Wallet History Entry */
public final class ANXWalletHistoryEntry {

  private final int index;
  private final String date;
  private final String type;
  private final ANXValue value;
  private final ANXValue balance;
  private final String info;
  private final String[] link;
  private final ANXWalletHistoryEntryTrade trade;
  private final String transactionId;

  /**
   * Constructor
   *
   * @param index
   * @param date
   * @param type
   * @param value
   * @param balance
   * @param link
   * @param info
   * @param trade
   */
  public ANXWalletHistoryEntry(
      @JsonProperty("Index") int index,
      @JsonProperty("Date") String date,
      @JsonProperty("Type") String type,
      @JsonProperty("Info") String info,
      @JsonProperty("Link") String[] link,
      @JsonProperty("Value") ANXValue value,
      @JsonProperty("Balance") ANXValue balance,
      @JsonProperty("Trade") ANXWalletHistoryEntryTrade trade,
      @JsonProperty("TransactionId") String transactionId) {

    this.index = index;
    this.date = date;
    this.type = type;
    this.info = info;
    this.link = link;
    this.value = value;
    this.balance = balance;
    this.trade = trade;
    this.transactionId = transactionId;
  }

  public String getTransactionId() {
    return transactionId;
  }

  public int getIndex() {

    return index;
  }

  public String getDate() {

    return date;
  }

  public String getType() {

    return type;
  }

  public ANXValue getValue() {

    return value;
  }

  public ANXValue getBalance() {

    return balance;
  }

  public String getInfo() {

    return info;
  }

  public String[] getLink() {

    return link;
  }

  public ANXWalletHistoryEntryTrade getTrade() {

    return trade;
  }

  @Override
  public String toString() {

    return "ANXWalletHistoryEntry{"
        + "index="
        + index
        + ", date="
        + date
        + ", type="
        + type
        + ", value="
        + value
        + ", balance="
        + balance
        + ", info="
        + info
        + ", link="
        + Arrays.toString(link)
        + ", trade="
        + trade
        + '}';
  }

  public static class ANXWalletHistoryEntryTrade {

    private final String oid;
    private final String tid;
    private final String app;
    private final String properties;
    private final ANXValue amount;

    public ANXWalletHistoryEntryTrade(
        @JsonProperty("oid") String oid,
        @JsonProperty("tid") String tid,
        @JsonProperty("app") String app,
        @JsonProperty("Properties") String properties,
        @JsonProperty("Amount") ANXValue amount) {

      this.oid = oid;
      this.tid = tid;
      this.app = app;
      this.properties = properties;
      this.amount = amount;
    }

    public ANXValue getAmount() {

      return amount;
    }

    public String getProperties() {

      return properties;
    }

    public String getApp() {

      return app;
    }

    public String getOid() {

      return oid;
    }

    public String getTid() {

      return tid;
    }

    @Override
    public String toString() {

      return "ANXWalletHistoryEntryTrade{"
          + "oid="
          + oid
          + ", tid="
          + tid
          + ", app="
          + app
          + ", properties="
          + properties
          + ", amount="
          + amount
          + '}';
    }
  }
}
