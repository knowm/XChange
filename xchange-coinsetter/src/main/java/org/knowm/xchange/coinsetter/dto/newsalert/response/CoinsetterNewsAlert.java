package org.knowm.xchange.coinsetter.dto.newsalert.response;

import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A new alert.
 */
public class CoinsetterNewsAlert {

  private final String message;
  private final UUID uuid;
  private final Date createDate;
  private final String messageType;

  /**
   * @param message The content of the news alert
   * @param uuid News alert UUID
   * @param createDate The date the message was created
   * @param messageType The type of news alert
   */
  public CoinsetterNewsAlert(@JsonProperty("message") String message, @JsonProperty("uuid") UUID uuid,
      @JsonProperty("createDate") @JsonFormat(pattern = "EEE, MMM dd, yyyy", timezone = "EST", locale = "us") Date createDate,
      @JsonProperty("messageType") String messageType) {

    this.message = message;
    this.uuid = uuid;
    this.createDate = createDate;
    this.messageType = messageType;
  }

  public String getMessage() {

    return message;
  }

  public UUID getUuid() {

    return uuid;
  }

  public Date getCreateDate() {

    return createDate;
  }

  public String getMessageType() {

    return messageType;
  }

  @Override
  public String toString() {

    return "CoinsetterNewsAlert [message=" + message + ", uuid=" + uuid + ", createDate=" + createDate + ", messageType=" + messageType + "]";
  }

}
