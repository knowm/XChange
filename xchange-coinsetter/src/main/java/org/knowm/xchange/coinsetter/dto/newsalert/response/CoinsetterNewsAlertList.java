package org.knowm.xchange.coinsetter.dto.newsalert.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A list of new alerts.
 */
public class CoinsetterNewsAlertList {

  private final CoinsetterNewsAlert[] messageList;

  public CoinsetterNewsAlertList(@JsonProperty("MessageList") CoinsetterNewsAlert[] messageList) {

    this.messageList = messageList;
  }

  public CoinsetterNewsAlert[] getMessageList() {

    return messageList;
  }

}
