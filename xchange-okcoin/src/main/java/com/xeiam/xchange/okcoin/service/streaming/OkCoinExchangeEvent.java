package com.xeiam.xchange.okcoin.service.streaming;

import com.xeiam.xchange.service.streaming.ExchangeEvent;
import com.xeiam.xchange.service.streaming.ExchangeEventType;

class OkCoinExchangeEvent implements ExchangeEvent {
  private final String rawData;
  private final Object payload;
  private final ExchangeEventType exchangeEventType;

  public OkCoinExchangeEvent(ExchangeEventType exchangeEventType, Object payload) {
    this.rawData = null;
    this.payload = payload;
    this.exchangeEventType = exchangeEventType;
  }

  @Override
  public Object getPayload() {
    return payload;
  }

  @Override
  public String getData() {
    return rawData;
  }

  @Override
  public ExchangeEventType getEventType() {
    return exchangeEventType;
  }
}