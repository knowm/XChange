package org.knowm.xchange.btcchina.service.streaming;

import org.json.JSONObject;

import org.knowm.xchange.service.streaming.DefaultExchangeEvent;
import org.knowm.xchange.service.streaming.ExchangeEventType;

public class BTCChinaExchangeEvent extends DefaultExchangeEvent {

  private final JSONObject jsonObject;

  public BTCChinaExchangeEvent(ExchangeEventType exchangeEventType) {

    super(exchangeEventType, null);
    this.jsonObject = null;
  }

  public BTCChinaExchangeEvent(ExchangeEventType exchangeEventType, JSONObject jsonObject, Object payload) {

    super(exchangeEventType, jsonObject.toString(), payload);
    this.jsonObject = jsonObject;
  }

  public JSONObject getJsonObject() {

    return jsonObject;
  }

}
